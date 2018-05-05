package cz.cvut.fel.pjv.JavaFXGUI;

import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.moves.Move;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.pieces.Piece;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.Collection;
import java.util.Collections;

import static cz.cvut.fel.pjv.JavaFXGUI.Main.*;


class GUITile extends ToggleButton {

    private final Tile tile;
    private final Piece piece;
    private Board engineBoard;

    GUITile(final int row,
            final int column,
            final Board engineBoard) {
        this.tile = engineBoard.getTile(row, column);
        setStyles(row, column);
        this.piece = this.tile.getPiece();
        if (piece != null) {
            setPieceImage();
        }
        this.engineBoard = engineBoard;
        setEvent();
    }

    private void setStyles(int row, int column) {
        this.getStyleClass().add("dimensions");
        if ((row + column) % 2 == 0) {
            this.getStyleClass().add("light");
        } else {
            this.getStyleClass().add("dark");
        }
    }

    private void setPieceImage() {
        this.setGraphic(null);
        if (this.piece != null) {
            String pathToImage = "images/pieces/"
                    + this.piece.getPieceColour().toString().substring(0, 1)
                    + this.piece.getPieceType().toString().toUpperCase()
                    + ".png";
            this.setGraphic(new ImageView(new Image(pathToImage)));
        }
    }

    private void setEvent() {
        this.setOnMouseClicked(event -> {
            MouseButton button = event.getButton();
            if (button == MouseButton.SECONDARY) {
                setNullMoveInitiation();
            } else if (button == MouseButton.PRIMARY) {
                if (getSourceTile() == null) {
                    setSourceTile(getAsTileObject());
                    setMovedPiece(getPieceObject());
                    if (Main.getMovedPiece() == null) {
                        setSourceTile(null);
                    } else if (getMovedPiece().getPieceColour()
                            == engineBoard.getCurrentPlayer().getColour()) {
                        highlightLegalMoves(engineBoard, true);
                        GUIBoard.highlightActiveGUITile(true);
                    }
                } else {
                    setDestinationTile(GUITile.this.getAsTileObject());
                    Tile sourceTile = getSourceTile();
                    Tile destinationTile = getDestinationTile();
                    Piece destinationPiece = destinationTile.getPiece();
                    Piece sourcePiece = sourceTile.getPiece();
                    if (sourcePiece != null && destinationPiece != null && destinationPiece.getPieceColour() == sourcePiece.getPieceColour()) {
                        setNullMoveInitiation();
                    }
                    final Move move = engineBoard.getCurrentPlayer().findMove(sourceTile, destinationTile);
                    boolean done = engineBoard.getCurrentPlayer().initiateMove(move);
                    setNullMoveInitiation();
                    if (done) {
                        Main.redrawBoard(GUITile.this.engineBoard);
                    }
                }
            }
        });
    }

    private Tile getAsTileObject() {
        return tile;
    }

    private Piece getPieceObject() {
        return piece;
    }

    private void setNullMoveInitiation() {
        setEffect(null);
        setSourceTile(null);
        setDestinationTile(null);
        setMovedPiece(null);
        GUIBoard.highlightActiveGUITile(false);
        highlightLegalMoves(engineBoard, false);
    }

    private void highlightLegalMoves(final Board board, final boolean highlight) {
        for (final Move move : pieceLegalMoves(board)) {
            Tile destinationTile = move.getDestinationTile();
            GUITile guiTile = GUIBoard.getSpecificGUITile(destinationTile.getTileRow(), destinationTile.getTileColumn());
            String pathToImage = (highlight) ? "images/other/green_dot.png" : null;
                guiTile.setGraphic(new ImageView(new Image(pathToImage)));

        }
    }

    private Collection<Move> pieceLegalMoves(final Board board) {
        Piece movedPiece = Main.getMovedPiece();
        if (movedPiece != null && movedPiece.getPieceColour() == board.getCurrentPlayer().getColour()) {
            return movedPiece.calculateMoves(board);
        }
        return Collections.emptyList();
    }
}