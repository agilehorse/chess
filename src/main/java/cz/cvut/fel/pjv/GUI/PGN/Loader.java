package cz.cvut.fel.pjv.GUI.PGN;

import cz.cvut.fel.pjv.GUI.MainPanel;
import cz.cvut.fel.pjv.GUI.MoveLog;
import cz.cvut.fel.pjv.engine.Colour;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.BoardUtils;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.board.moves.Move;
import cz.cvut.fel.pjv.engine.board.moves.MoveType;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("RegExpSingleCharAlternation")
public class Loader {

    private static MoveLog moveLog = new MoveLog();
    private static final Pattern KING_SIDE_CASTLE = Pattern.compile("O-O#?\\+?");
    private static final Pattern QUEEN_SIDE_CASTLE = Pattern.compile("O-O-O#?\\+?");
    private static final Pattern PLAIN_PAWN_MOVE = Pattern.compile("^([a-h][0-8])(\\+)?(#)?$");
    private static final Pattern PAWN_ATTACK_MOVE = Pattern.compile("(^[a-h])(x)([a-h][0-8])(\\+)?(#)?$");
    private static final Pattern PLAIN_MAJOR_MOVE = Pattern.compile("^(B|N|R|Q|K)([a-h]|[1-8])?([a-h][0-8])(\\+)?(#)?$");
    private static final Pattern MAJOR_ATTACK_MOVE = Pattern.compile("^(B|N|R|Q|K)([a-h]|[1-8])?(x)([a-h][0-8])(\\+)?(#)?$");
    private static final Pattern PLAIN_PAWN_PROMOTION_MOVE = Pattern.compile("([a-h][0-8])=(B|N|R|Q)");
    private static final Pattern ATTACK_PAWN_PROMOTION_MOVE = Pattern.compile("(a-h][0-8])x([a-h][0-8])=(B|N|R|Q)");

    public static Board persistPGNFile(final File pgnFile) throws IOException {
        try (final BufferedReader br = new BufferedReader(new FileReader(pgnFile))) {
            String line;
            ArrayList<String> moveStringList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    if (!isTag(line)) {
                        String[] movesString = line.split(" ");
                        for (int i = 0; i < movesString.length; i += 2) {
                            String move = movesString[i];
                            movesString[i] = move.substring(move.indexOf(".") + 1);
                        }
                        movesString = popEnd(movesString);
                        moveStringList.addAll(Arrays.asList(movesString));
                    }
                }
            }
            moveStringList.removeAll(Arrays.asList("", null));
            Board loadedBoard = new Board();
            for (final String moveString : moveStringList) {
                final Move move = createMove(loadedBoard, moveString);
                if (move != null) {
                    move.execute();
                    moveLog.addMove(move);
                }
                loadedBoard.recalculate(true);
            }
            return loadedBoard;
        }
    }

    private static String[] popEnd(String[] movesString) {
        String lastValue = movesString[movesString.length - 1];
        if (lastValue.equals("1-0") || lastValue.equals("0-1")
                || lastValue.equals("1/2-1/2") || lastValue.equals("*")) {
            movesString = Arrays.copyOf(movesString, movesString.length - 1);
        }
        return movesString;
    }

    private static boolean isTag(String line) {
        return line.startsWith("[") && line.endsWith("]");
    }


    private static Move createMove(final Board board,
                                   final String pgnText) {

        final Matcher kingSideCastleMatcher = KING_SIDE_CASTLE.matcher(pgnText);
        final Matcher queenSideCastleMatcher = QUEEN_SIDE_CASTLE.matcher(pgnText);
        final Matcher plainPawnMatcher = PLAIN_PAWN_MOVE.matcher(pgnText);
        final Matcher attackPawnMatcher = PAWN_ATTACK_MOVE.matcher(pgnText);
        final Matcher pawnPromotionMatcher = PLAIN_PAWN_PROMOTION_MOVE.matcher(pgnText);
        final Matcher attackPawnPromotionMatcher = ATTACK_PAWN_PROMOTION_MOVE.matcher(pgnText);
        final Matcher plainMajorMatcher = PLAIN_MAJOR_MOVE.matcher(pgnText);
        final Matcher attackMajorMatcher = MAJOR_ATTACK_MOVE.matcher(pgnText);

        Tile sourceTile;
        Tile destinationTile;
            if (kingSideCastleMatcher.matches()) {
                return extractCastleMove(board, "O-O");
            } else if (queenSideCastleMatcher.matches()) {
                return extractCastleMove(board, "O-O-O");
            } else if (plainPawnMatcher.matches()) {
                final String destinationSquare = plainPawnMatcher.group(1);
                final List<Integer> coordsList = BoardUtils.getCoordinateAtPosition(destinationSquare);
                destinationTile = board.getTile(coordsList.get(0), coordsList.get(1));
                sourceTile = deriveCurrentCoordinate(board, "P", destinationTile, "");
                if (sourceTile != null) {
                    return findMove(board, sourceTile.getPiece().getPieceColour(), sourceTile, destinationTile);
                } else {
                    fileLoadError();
                }
            } else if (attackPawnMatcher.matches()) {
                final String destinationSquare = attackPawnMatcher.group(3);
                final List<Integer> coordsList = BoardUtils.getCoordinateAtPosition(destinationSquare);
                destinationTile = board.getTile(coordsList.get(0), coordsList.get(1));
                final String disambiguationFile = attackPawnMatcher.group(1) != null ? attackPawnMatcher.group(1) : "";
                sourceTile = deriveCurrentCoordinate(board, "P", destinationTile, disambiguationFile);
                if (sourceTile != null) {
                    return findMove(board, sourceTile.getPiece().getPieceColour(), sourceTile, destinationTile);
                } else {
                    fileLoadError();
                }
            } else if (attackPawnPromotionMatcher.matches()) {
                final String destinationSquare = attackPawnPromotionMatcher.group(2);
                final String disambiguationFile = attackPawnPromotionMatcher.group(1) != null ? attackPawnPromotionMatcher.group(1) : "";
                final List<Integer> coordsList = BoardUtils.getCoordinateAtPosition(destinationSquare);
                destinationTile = board.getTile(coordsList.get(0), coordsList.get(1));
                sourceTile = deriveCurrentCoordinate(board, pgnText.substring(pgnText.length()-1), destinationTile, disambiguationFile);
                if (sourceTile != null) {
                    return findMove(board, sourceTile.getPiece().getPieceColour(), sourceTile, destinationTile, pgnText.substring(pgnText.length()-1));
                } else {
                    fileLoadError();
                }
            } else if (pawnPromotionMatcher.find()) {
                final String destinationSquare = pawnPromotionMatcher.group(1);
                final List<Integer> coordsList = BoardUtils.getCoordinateAtPosition(destinationSquare);
                destinationTile = board.getTile(coordsList.get(0), coordsList.get(1));
                sourceTile = deriveCurrentCoordinate(board, pgnText.substring(pgnText.length()-1), destinationTile, "");
                if (sourceTile != null) {
                    return findMove(board, sourceTile.getPiece().getPieceColour(), sourceTile, destinationTile, pgnText.substring(pgnText.length()-1));
                } else {
                    fileLoadError();
                }
            } else if (plainMajorMatcher.find()) {
                final String destinationSquare = plainMajorMatcher.group(3);
                final List<Integer> coordsList = BoardUtils.getCoordinateAtPosition(destinationSquare);
                destinationTile = board.getTile(coordsList.get(0), coordsList.get(1));
                final String disambiguationFile = plainMajorMatcher.group(2) != null ? plainMajorMatcher.group(2) : "";
                sourceTile = deriveCurrentCoordinate(board, plainMajorMatcher.group(1), destinationTile, disambiguationFile);
                if (sourceTile != null) {
                    return findMove(board, sourceTile.getPiece().getPieceColour(), sourceTile, destinationTile);
                } else {
                    fileLoadError();
                }
            } else if (attackMajorMatcher.find()) {
                final String destinationSquare = attackMajorMatcher.group(4);
                final List<Integer> coordsList = BoardUtils.getCoordinateAtPosition(destinationSquare);
                destinationTile = board.getTile(coordsList.get(0), coordsList.get(1));
                final String disambiguationFile = attackMajorMatcher.group(2) != null ? attackMajorMatcher.group(2) : "";
                sourceTile = deriveCurrentCoordinate(board, attackMajorMatcher.group(1), destinationTile, disambiguationFile);
                if (sourceTile != null) {
                    return findMove(board, sourceTile.getPiece().getPieceColour(), sourceTile, destinationTile);
                } else {
                    fileLoadError();
                }
            }
        return null;
    }

    private static void fileLoadError() {
        JOptionPane.showMessageDialog(MainPanel.getGuiBoard(),
                "Invalid file!", "Input file error",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private static Move extractCastleMove(final Board board,
                                          final String castleMove) {
        for (final Move move : board.getCurrentPlayer().getLegalMoves()) {
            if (move.getMoveType().equals(MoveType.CASTLE) && move.toString().equals(castleMove)) {
                return move;
            }
        }
        return null;
    }

    private static Tile deriveCurrentCoordinate(final Board board,
                                                final String movedPiece,
                                                final Tile destinationTile,
                                                final String disambiguationFile) throws RuntimeException {
        final List<Move> currentCandidates = new ArrayList<>();
        for (final Move move : board.getMovesByColour(board.getCurrentPlayer().getColour())) {
            if (move.getDestinationTile().equals(destinationTile) && move.getMovedPiece().toString().equals(movedPiece)) {
                currentCandidates.add(move);
            }
        }
        if (currentCandidates.size() == 0) {
            return null;
        }
        return currentCandidates.size() == 1
                ? currentCandidates.iterator().next().getSourceTile()
                : extractFurther(currentCandidates, movedPiece, disambiguationFile);

    }

    private static Tile extractFurther(final List<Move> candidateMoves,
                                       final String movedPiece,
                                       final String disambiguationFile) {
        final List<Move> currentCandidates = new ArrayList<>();
        for (final Move move : candidateMoves) {
            if (move.getMovedPiece().getPieceType().toString().equals(movedPiece)) {
                currentCandidates.add(move);
            }
        }
        if (currentCandidates.size() == 1) {
            return currentCandidates.iterator().next().getSourceTile();
        }
        final List<Move> candidatesRefined = new ArrayList<>();
        for (final Move move : currentCandidates) {
            final String pos = BoardUtils.getPositionAtCoordinate(move.getSourceTile().getTileRow(), move.getSourceTile().getTileColumn());
            if (pos.contains(disambiguationFile)) {
                candidatesRefined.add(move);
            }
        }
        if (candidatesRefined.size() == 1) {
            return candidatesRefined.iterator().next().getSourceTile();
        }
        return null;
    }

    private static Move findMove(final Board board, final Colour colour,
                                 final Tile sourceTile, final Tile destinationTile) {
        for (final Move move : board.getMovesByColour(colour)) {
            if (move.getSourceTile() == sourceTile &&
                    move.getDestinationTile() == destinationTile) {
                return move;
            }
        }
        return null;
    }

    private static Move findMove(final Board board, final Colour colour,
                                 final Tile sourceTile, final Tile destinationTile,
                                 final String newPiece) {
        for (final Move move : board.getMovesByColour(colour)) {
            if (move.getSourceTile() == sourceTile &&
                    move.getDestinationTile() == destinationTile && move.getMovedPiece().toString().equals(newPiece)) {
                return move;
            }
        }
        return null;
    }

    public static MoveLog getMoveLog() {
        return moveLog;
    }

}