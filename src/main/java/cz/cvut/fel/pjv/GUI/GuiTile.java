package cz.cvut.fel.pjv.GUI;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.board.moves.Move;
import cz.cvut.fel.pjv.engine.board.moves.MoveType;
import cz.cvut.fel.pjv.engine.pieces.Piece;
import cz.cvut.fel.pjv.engine.pieces.PieceType;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

class GuiTile extends JPanel {

    private final static Logger LOGGER = Logger.getLogger(GuiTile.class.getSimpleName());
    private static final Dimension TILE_DIMENSION = new Dimension(10, 10);
    private final int row;
    private final int column;
    private Board board;

    GuiTile(final GuiBoard guiBoard,
            final int row,
            final int column) {
        super(new GridBagLayout());
        this.row = row;
        this.column = column;
        setPreferredSize(TILE_DIMENSION);
        setTileColor();
        this.board = MainPanel.getBoard();
        setTilePieceIcon();

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (isRightMouseButton(mouseEvent)) {
//                    clears move initiation if right clicked
                    clearState();
                } else if (isLeftMouseButton(mouseEvent)) {
                    if (ClockSetup.getMode() != 0) Clock.start();
                    if (MainPanel.getSourceTile() == null) {
                        MainPanel.setSourceTile(getAsTileObject());
                        MainPanel.setMovedPiece(MainPanel.getSourceTile().getPiece());
                        if (MainPanel.getMovedPiece() == null) {
                            MainPanel.setSourceTile(null);
                        }
                    } else {
                        final Tile sourceTile = MainPanel.getSourceTile();
                        final Tile destinationTile = getAsTileObject();
                        Move move = board.getCurrentPlayer().findMove(sourceTile,
                                destinationTile);
//                        if move is promotion calls method to hande it and stops clock
                        if (move != null && move.getMoveType() == MoveType.PROMOTION) {
                            if (ClockSetup.getMode() != 0) Clock.stop();
                            move = handlePawnPromotionMove(sourceTile, destinationTile);
                            if (move == null) Clock.wakeUp();
                        }
//                        executes move
                        final boolean done =
                                board.getCurrentPlayer().executeMove(move);
                        if (done) {
                            MainPanel.getMoveLog().addMove(move);
                        }
                        clearState();
                    }
                }
                SwingUtilities.invokeLater(() -> {
//                    recalculates board, refreshes game history panel and taken pieces panel
                    board.recalculate();
                    if (MainPanel.getGameSetup().isAIPlayer(board.getCurrentPlayer())) {
//                        notifies observers that move has been made
                        MainPanel.get().moveMadeUpdate(GameSetup.PlayerType.HUMAN);
                    }
                    MainPanel.getGameHistoryPanel().redo(board, MainPanel.getMoveLog());
                    MainPanel.getTakenPiecesPanel().redo(MainPanel.getMoveLog());
                    guiBoard.drawBoard(MainPanel.getBoard());
                });
//                ends game if current player is in checkmate
                if (board.getCurrentPlayer().isInCheckMate()) {
                    if (ClockSetup.getMode() != 0) Clock.stop();
                    JOptionPane.showMessageDialog(guiBoard,
                            "Game Over: Player " +
                                    board.getCurrentPlayer().toString() +
                                    " is in checkmate!", "Game Over",
                            JOptionPane.INFORMATION_MESSAGE);
                }
//                ends game if current player is in stalemate
                if (board.getCurrentPlayer().isInStaleMate()) {
                    if (ClockSetup.getMode() != 0) Clock.stop();
                    JOptionPane.showMessageDialog(guiBoard,
                            "Game Over: Player " +
                                    board.getCurrentPlayer().toString() +
                                    " is in stalemate!", "Game Over",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            @Override
            public void mousePressed(MouseEvent mouseEvent) {}

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {}

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {}

            @Override
            public void mouseExited(MouseEvent mouseEvent) {}
        });
        validate();
    }

    private Move handlePawnPromotionMove(final Tile sourceTile,
                                         final Tile destinationTile) {
        Move promotionMove = null;
        PieceType pieceType;
        String[] options = {"Queen" , "Rook", "Bishop", "Knight"};
        String n = (String) JOptionPane.showInputDialog(MainPanel.get().getGameFrame(), "Choose which piece you want to promote to: ",
                "Promotion Pieces", JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);
//       saves piece type from input into pieceType
        if (n == null) {
            return null;
        } else if (n.equals("Rook")) {
            pieceType = PieceType.ROOK;
        } else if (n.equals("Bishop")) {
            pieceType = PieceType.BISHOP;
        } else if (n.equals("Knight")) {
            pieceType = PieceType.KNIGHT;
        } else {
            pieceType = PieceType.QUEEN;
        }
//        finds a promotion move, depending on user selected piece type, source and destination tile
        for (final Move pawnMove : board.getCurrentPlayer().getLegalMoves()) {
            if (destinationTile == pawnMove.getDestinationTile()
                    && sourceTile == pawnMove.getSourceTile()
                    && pieceType == pawnMove.getMovedPiece().getPieceType()) {
                promotionMove = pawnMove;
            }
        }
        return promotionMove;
    }


    private Tile getAsTileObject() {
        return Board.getTile(row, column);
    }

    private void clearState() {
        MainPanel.setSourceTile(null);
        MainPanel.setMovedPiece(null);
        MainPanel.getGuiBoard().drawBoard(MainPanel.getBoard());
    }

    private void setTileColor() {
        if ((row + column) % 2 == 0) {
            this.setBackground(new Color(255, 206, 158));
        } else {
            this.setBackground(new Color(209, 139, 71));
        }
    }
//  sets tile piece icon on a tile depending on what type of piece is placed there
    private void setTilePieceIcon() {
        Tile thisTile = Board.getTile(this.row, this.column);
        if (thisTile.isOccupied()) {
            try {
//                images are save in pattern- for instance white bishop = WB.png
                String pieceIconPath = "images/pieces/";
                final BufferedImage image
                        = ImageIO.read(new File(pieceIconPath
                        + thisTile.getPiece().getPieceColour().toString().substring(0, 1)
                        + thisTile.getPiece().getPieceType().toString().toUpperCase()
                        + ".png"));
                add(new JLabel(new ImageIcon(image)));
            } catch (IOException e) {
                LOGGER.log(Level.INFO, "Unexpected error while displaying " +
                        "piece icon!");
            }
        }
    }
//  redraws tile
    void drawTile(Board engineBoard) {
        this.removeAll();
        setTileColor();
        setTilePieceIcon();
        highlightLegalMoves(engineBoard);
        validate();
        repaint();
    }
//    for all destination tiles of legal moves of selected piece, a picture is added on top of them
    private void highlightLegalMoves(final Board board) {
        for (final Move move : pieceLegalMoves(board)) {
            if (move.getDestinationTile() == this.getAsTileObject()) {
                try {
                    add(new JLabel(new ImageIcon(ImageIO.read(
                            new File("images/other/green_square.png")))));
                } catch (final IOException e) {
                    LOGGER.log(Level.INFO, "Unexpected error while highlighting " +
                            "legal moves!");
                }
            }
        }
    }
//  calculates current piece legal moves, used for highlighting them on board
    private Collection<Move> pieceLegalMoves(final Board board) {
        final Piece movedPiece = MainPanel.getMovedPiece();
        if (movedPiece != null &&
                movedPiece.getPieceColour() == board.getCurrentPlayer().getColour()) {
            final Collection<Move> playersMoves
                    = board.getCurrentPlayer().getLegalMoves();
            Collection<Move> pieceMoves = new ArrayList<>();
            for (final Move move : playersMoves) {
                if(move.getMovedPiece() == movedPiece) {
                    pieceMoves.add(move);
                }
            }
            return pieceMoves;
        }
        return Collections.emptyList();
    }
}
