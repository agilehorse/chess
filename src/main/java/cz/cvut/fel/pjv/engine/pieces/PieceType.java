package cz.cvut.fel.pjv.engine.pieces;

//    string feedback for printing pieces on board
public enum PieceType {

    PAWN("[p]"),
    KNIGHT("[n]"),
    BISHOP("[b]"),
    ROOK("[r]"),
    QUEEN("[q]"),
    KING("[k]");

    private String pieceName;

    PieceType(final String pieceName) {
        this.pieceName = pieceName;
    }

    @Override
    public String toString() {
        return this.pieceName;
    }
}