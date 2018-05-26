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
    private static final Pattern PAWN_NORMAL_MOVE = Pattern.compile("^([a-h][0-8])(\\+)?(#)?$");
    private static final Pattern PAWN_ATTACK_MOVE = Pattern.compile("(^[a-h])(x)([a-h][0-8])(\\+)?(#)?$");
    private static final Pattern CLASSIC_NORMAL_MOVE = Pattern.compile("^(B|N|R|Q|K)([a-h]|[1-8])?([a-h][0-8])(\\+)?(#)?$");
    private static final Pattern CLASSIC_ATTACK_MOVE = Pattern.compile("^(B|N|R|Q|K)([a-h]|[1-8])?(x)([a-h][0-8])(\\+)?(#)?$");
    private static final Pattern PAWN_PROMOTION_NORMAL_MOVE = Pattern.compile("([a-h][0-8])=(B|N|R|Q)");
    private static final Pattern PAWN_PROMOTION_ATTACK_MOVE = Pattern.compile("(a-h][0-8])x([a-h][0-8])=(B|N|R|Q)");

    public static Board persistPGNFile(final File pgnFile) throws IOException {
        try (final BufferedReader br = new BufferedReader(new FileReader(pgnFile))) {
            String line;
            ArrayList<String> moveStringList = new ArrayList<>();
//            if there has a line not yet read, if it's not empty and it's not tag
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    if (!isTag(line)) {
//                        the line gets split up by spaces, only moves and ordinal numbers signifying rounds
                        String[] movesString = line.split(" ");
//                        every odd member of list was saved in array with ordinal number
//                        this iterates over the list and removes the ordinal numbers with point and rewrite the array item to just the move-string
                        for (int i = 0; i < movesString.length; i += 2) {
                            String move = movesString[i];
                            movesString[i] = move.substring(move.indexOf(".") + 1);
                        }
//                        removes notation of how match ended which is usually at the end of the file
                        movesString = popEnd(movesString);
                        moveStringList.addAll(Arrays.asList(movesString));
                    }
                }
            }
//            removes any null values from list
            moveStringList.removeAll(Arrays.asList("", null));
//            makes a new board where all moves will be executed
            Board loadedBoard = new Board();
//            for notation of move in the list, it's translated to actual move and made on the board
            for (final String moveString : moveStringList) {
                final Move move = createMoveFromString(loadedBoard, moveString);
                if (move != null) {
                    move.execute();
                    moveLog.addMove(move);
                }
                else {
                    break;
                }
                loadedBoard.recalculate();
            }
            return loadedBoard;
        }
    }
// removes match end notation from end of the string
    private static String[] popEnd(String[] movesString) {
        String lastValue = movesString[movesString.length - 1];
        if (lastValue.equals("1-0") || lastValue.equals("0-1")
                || lastValue.equals("1/2-1/2") || lastValue.equals("*")) {
            movesString = Arrays.copyOf(movesString, movesString.length - 1);
        }
        return movesString;
    }

    private static boolean isTag(final String line) {
        return line.startsWith("[") && line.endsWith("]");
    }

    private static Move createMoveFromString(final Board board,
                                             final String pgnText) {
//      every type of move has it's unique notation which can be decoded to a move, string from input will be matched depending on it
        final Matcher kingSideCastleMoveMatcher = KING_SIDE_CASTLE.matcher(pgnText);
        final Matcher queenSideCastleMoveMatcher = QUEEN_SIDE_CASTLE.matcher(pgnText);
        final Matcher normalPawnMoveMatcher = PAWN_NORMAL_MOVE.matcher(pgnText);
        final Matcher attackPawnMoveMatcher = PAWN_ATTACK_MOVE.matcher(pgnText);
        final Matcher pawnPromotionMoveMatcher = PAWN_PROMOTION_NORMAL_MOVE.matcher(pgnText);
        final Matcher attackPawnPromotionMoveMatcher = PAWN_PROMOTION_ATTACK_MOVE.matcher(pgnText);
        final Matcher classicNormalMoveMatcher = CLASSIC_NORMAL_MOVE.matcher(pgnText);
        final Matcher classicAttackMoveMatcher = CLASSIC_ATTACK_MOVE.matcher(pgnText);

        Tile sourceTile;
        Tile destinationTile;
//        if the pattern of move notation looked like castle move
        if (kingSideCastleMoveMatcher.matches()) {
            return extractCastleMove(board, "O-O");
        } else if (queenSideCastleMoveMatcher.matches()) {
            return extractCastleMove(board, "O-O-O");
            //        if the pattern of move notation looked like normal pawn move
        } else if (normalPawnMoveMatcher.matches()) {
//            gets destination tile
            final String destinationSquare = normalPawnMoveMatcher.group(1);
            destinationTile = BoardUtils.getCoordinateAtPosition(destinationSquare);
//            decodes from which source tile the move could be made with this piece to destination extracted got from string
            sourceTile = decode(board, "P", destinationTile, "");
            if (sourceTile != null) {
                return findMove(board, sourceTile.getPiece().getPieceColour(), sourceTile, destinationTile);
            } else {
                fileLoadError();
                return null;
            }
            //        if the pattern of move notation looked like pawn attack move
        } else if (attackPawnMoveMatcher.matches()) {
            //            gets destination tile
            final String destinationSquare = attackPawnMoveMatcher.group(3);
            destinationTile = BoardUtils.getCoordinateAtPosition(destinationSquare);
//            gets a column from which the move could be made if two pieces of same type could do the same move
            final String disambiguationFile = attackPawnMoveMatcher.group(1) != null ? attackPawnMoveMatcher.group(1) : "";
            //            decodes from which source tile the move could be made with this piece to destination extracted got from string
            sourceTile = decode(board, "P", destinationTile, disambiguationFile);
            if (sourceTile != null) {
                return findMove(board, sourceTile.getPiece().getPieceColour(), sourceTile, destinationTile);
            }
            else {
                fileLoadError();
                return null;
            }
            //        if the pattern of move notation looked like pawn promotion attack move
        } else if (attackPawnPromotionMoveMatcher.matches()) {
            //            gets destination tile
            final String destinationSquare = attackPawnPromotionMoveMatcher.group(2);
            destinationTile = BoardUtils.getCoordinateAtPosition(destinationSquare);
            //            gets a column from which the move could be made if two pieces of same type could do the same move
            final String disambiguationFile = attackPawnPromotionMoveMatcher.group(1) != null ? attackPawnPromotionMoveMatcher.group(1) : "";
            //            decodes from which source tile the move could be made with this piece to destination extracted got from string
            sourceTile = decode(board, pgnText.substring(pgnText.length() - 1), destinationTile, disambiguationFile);
            if (sourceTile != null) {
                return findPromotionMove(board, sourceTile.getPiece().getPieceColour(), sourceTile, destinationTile, pgnText.substring(pgnText.length() - 1));
            } else {
                fileLoadError();
                return null;
            }
            //        if the pattern of move notation looked like normal pawn promotion move
        } else if (pawnPromotionMoveMatcher.find()) {
            //            gets destination tile
            final String destinationSquare = pawnPromotionMoveMatcher.group(1);
            destinationTile = BoardUtils.getCoordinateAtPosition(destinationSquare);
            //            decodes from which source tile the move could be made with this piece to destination extracted got from string
            sourceTile = decode(board, pgnText.substring(pgnText.length() - 1), destinationTile, "");
            if (sourceTile != null) {
                return findPromotionMove(board, sourceTile.getPiece().getPieceColour(), sourceTile, destinationTile, pgnText.substring(pgnText.length() - 1));
            } else {
                fileLoadError();
                return null;
            }
            //        if the pattern of move notation looked like normal classical normal move
        } else if (classicNormalMoveMatcher.find()) {
            //            gets destination tile
            final String destinationSquare = classicNormalMoveMatcher.group(3);
            destinationTile = BoardUtils.getCoordinateAtPosition(destinationSquare);
            final String disambiguationFile = classicNormalMoveMatcher.group(2) != null ? classicNormalMoveMatcher.group(2) : "";
            //            decodes from which source tile the move could be made with this piece to destination extracted got from string
            sourceTile = decode(board, classicNormalMoveMatcher.group(1), destinationTile, disambiguationFile);
            if (sourceTile != null) {
                return findMove(board, sourceTile.getPiece().getPieceColour(), sourceTile, destinationTile);
            } else {
                fileLoadError();
                return null;
            }
            //        if the pattern of move notation looked like classic attack move
        } else if (classicAttackMoveMatcher.find()) {
            //            gets destination tile
            final String destinationSquare = classicAttackMoveMatcher.group(4);
            destinationTile = BoardUtils.getCoordinateAtPosition(destinationSquare);
            //            gets a column from which the move could be made if two pieces of same type could do the same move

            final String disambiguationFile = classicAttackMoveMatcher.group(2) != null ? classicAttackMoveMatcher.group(2) : "";
            //            decodes from which source tile the move could be made with this piece to destination extracted got from string
            sourceTile = decode(board, classicAttackMoveMatcher.group(1), destinationTile, disambiguationFile);
            if (sourceTile != null) {
                return findMove(board, sourceTile.getPiece().getPieceColour(), sourceTile, destinationTile);
            } else {
                fileLoadError();
                return null;
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

    private static Tile decode(final Board board,
                               final String movedPiece,
                               final Tile destinationTile,
                               final String disambiguationFile) throws RuntimeException {
        final List<Move> currentCandidates = new ArrayList<>();
        for (final Move move : board.getMovesByColour(board.getCurrentPlayer().getColour())) {
//            on current board finds every move which could be made on input destination tile, with input piece
            if (move.getDestinationTile().equals(destinationTile) && move.getMovedPiece().toString().equals(movedPiece)) {
                currentCandidates.add(move);
            }
        }
        if (currentCandidates.size() == 0) {
            return null;
        }
//        if it could find one move return it's source tile, else decodes more with disambiguationFile
        return currentCandidates.size() == 1
                ? currentCandidates.iterator().next().getSourceTile()
                : decodeMore(currentCandidates, disambiguationFile);

    }
//  decodes moves depending on disambiguationFile
    private static Tile decodeMore(final List<Move> candidateMoves,
                                   final String disambiguationFile) {
        final List<Move> betterCandidates = new ArrayList<>();
        for (final Move move : candidateMoves) {
//            gets notation of source tile of candidate move
            final String position = BoardUtils.getPositionAtCoordinate(move.getSourceTile().getTileRow(), move.getSourceTile().getTileColumn());
//            if the disambiguationFile is in the notation of source tile it's added to new candidates list
            if (position.contains(disambiguationFile)) {
                betterCandidates.add(move);
            }
        }
        if (betterCandidates.size() == 1) {
            return betterCandidates.iterator().next().getSourceTile();
        }
        return null;
    }
// finds move by colour, source tile and destination tile
    private static Move findMove(final Board board,
                                 final Colour colour,
                                 final Tile sourceTile,
                                 final Tile destinationTile) {
        for (final Move move : board.getMovesByColour(colour)) {
            if (move.getSourceTile() == sourceTile &&
                    move.getDestinationTile() == destinationTile) {
                return move;
            }
        }
        return null;
    }
// finds a promotion move the same way as previous method but also with promoted piece type notation
    private static Move findPromotionMove(final Board board,
                                          final Colour colour,
                                          final Tile sourceTile,
                                          final Tile destinationTile,
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