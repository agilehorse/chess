package cz.cvut.fel.pjv.engine.pieces;

//    string feedback for printing pieces on board
public enum PieceType {

    PAWN("p", 1),
    KNIGHT("n", 3),
    BISHOP("b", 3),
    ROOK("r", 5),
    QUEEN("q", 9),
    KING("k", 100);

    private String pieceName;
    private int pieceValue;

    PieceType(final String pieceName, final int pieceValue) {
        this.pieceName = pieceName;
        this.pieceValue = pieceValue;
    }

    @Override
    public String toString() {
        return this.pieceName;
    }

    public int getPieceValue() {
        return this.pieceValue;
    }
}