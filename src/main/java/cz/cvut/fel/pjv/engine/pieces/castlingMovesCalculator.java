package cz.cvut.fel.pjv.engine.pieces;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.moves.KingSideCastle;
import cz.cvut.fel.pjv.engine.board.moves.Move;
import cz.cvut.fel.pjv.engine.board.moves.QueenSideCastle;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.player.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class castlingMovesCalculator {

    public static Collection<Move> execute(Board board, Player player, int row, Collection<Move> playersMoves, Collection<Move> opponentMoves) {

        final List<Move> castleMoves = new ArrayList<>();
        if (player.getPlayersKing().isFirstMove() && !player.isInCheck() && !player.isCastled()) {
            if (!board.getTile(row, 5).isOccupied()
                    && !board.getTile(row, 6).isOccupied()) {
                final Tile rookTile = board.getTile(row, 7);
                if (rookTile.isOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateCheck(row, 5, opponentMoves).isEmpty()
                            && Player.calculateCheck(row, 6, opponentMoves).isEmpty()
                            && rookTile.getPiece().getPieceType() == PieceType.ROOK) {
                        castleMoves.add(new KingSideCastle(board,
                                player.getPlayersKing(),
                                row,
                                6,
                                (Rook) rookTile.getPiece(),
                                rookTile.getTileRow(),
                                rookTile.getTileColumn(),
                                row,
                                5));
                    }
                }
            }
            if (!board.getTile(row, 1).isOccupied()
                    && !board.getTile(row, 2).isOccupied()
                    && !board.getTile(row, 3).isOccupied()) {
                final Tile rookTile = board.getTile(row, 0);
                if (rookTile.isOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateCheck(row, 1, opponentMoves).isEmpty()
                            && Player.calculateCheck(row, 2, opponentMoves).isEmpty()
                            && Player.calculateCheck(row, 3, opponentMoves).isEmpty()
                            && rookTile.getPiece().getPieceType() == PieceType.ROOK) {
                        castleMoves.add(new QueenSideCastle(board,
                                player.getPlayersKing(),
                                row,
                                2, (Rook)
                                rookTile.getPiece(),
                                rookTile.getTileRow(),
                                rookTile.getTileColumn(),
                                row,
                                3));
                    }
                }
            }
        }
        return ImmutableList.copyOf(castleMoves);
    }
}
