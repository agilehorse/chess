package cz.cvut.fel.pjv.GUI;

import cz.cvut.fel.pjv.JavaFXGUI.Main;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.board.moves.Move;
import cz.cvut.fel.pjv.engine.pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

class GuiTile extends JPanel {

    private static final Dimension TILE_DIMENSION = new Dimension(10, 10);
    private String pieceIconPath = "images/pieces/";
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
        setTilePieceIcon(this.board);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (isRightMouseButton(mouseEvent)) {
                    clearState();
                } else if (isLeftMouseButton(mouseEvent)) {
                    if (MainPanel.getSourceTile() == null) {
                        MainPanel.setSourceTile(getAsTileObject());
                        MainPanel.setMovedPiece(MainPanel.getSourceTile().getPiece());
                        if (MainPanel.getMovedPiece() == null) {
                            MainPanel.setSourceTile(null);
                        }
                    } else {
                        final Tile sourceTile = MainPanel.getSourceTile();
                        final Tile destinationTile = getAsTileObject();
                        final Piece movedPiece = MainPanel.getMovedPiece();
                        final Move move = board.getCurrentPlayer().findMove(sourceTile, destinationTile);
                        final boolean done = board.getCurrentPlayer().initiateMove(move);
                        if (done) {
                            MainPanel.getMoveLog().addMove(move);
                        }
                        clearState();

                    }
                    SwingUtilities.invokeLater(() -> {
                        MainPanel.getGameHistoryPanel().redo(board, MainPanel.getMoveLog());
                        MainPanel.getTakenPiecesPanel().redo(MainPanel.getMoveLog());
                        guiBoard.drawBoard(MainPanel.getBoard());
                    });
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
        validate();
    }

    private Tile getAsTileObject() {
        return this.board.getTile(row, column);
    }

    private void clearState() {
        MainPanel.setSourceTile(null);
        MainPanel.setMovedPiece(null);
    }

    private void setTileColor() {
        if ((row + column) % 2 == 0) {
            this.setBackground(new Color(255, 206, 158));
        } else {
            this.setBackground(new Color(209, 139, 71));
        }
    }

    private void setTilePieceIcon(final Board board) {
        Tile thisTile = board.getTile(this.row, this.column);
        if (thisTile.isOccupied()) {
            try {
                final BufferedImage image
                        = ImageIO.read(new File(pieceIconPath
                        + thisTile.getPiece().getPieceColour().toString().substring(0, 1)
                        + thisTile.getPiece().getPieceType().toString().toUpperCase()
                        + ".png"));
                add(new JLabel(new ImageIcon(image)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void drawTile(Board engineBoard) {
        this.removeAll();
        setTileColor();
        setTilePieceIcon(engineBoard);
        highlightLegalMoves(engineBoard);
        validate();
        repaint();
    }

    private void highlightLegalMoves(final Board board) {
        for (final Move move : pieceLegalMoves(board)) {
            if (move.getDestinationTile() == this.getAsTileObject()) {
                try {
                    add(new JLabel(new ImageIcon(ImageIO.read(new File("images/other/green_square.png")))));
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Collection<Move> pieceLegalMoves(final Board board) {
        Piece movedPiece = MainPanel.getMovedPiece();
        if (movedPiece != null
                && movedPiece.getPieceColour()
                == board.getCurrentPlayer().getColour()) {
            return movedPiece.calculateMoves(board);
        }
        return Collections.emptyList();
    }
}
