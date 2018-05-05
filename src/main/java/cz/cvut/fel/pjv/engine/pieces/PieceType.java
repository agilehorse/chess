package cz.cvut.fel.pjv.engine.pieces;

//    string feedback for printing pieces on board
public enum PieceType {

    PAWN("p", 100),
    KNIGHT("n", 300),
    BISHOP("b", 300),
    ROOK("r", 500),
    QUEEN("q", 900),
    KING("k", 10000);

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