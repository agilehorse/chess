package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.player.BlackPlayer;
import cz.cvut.fel.pjv.player.Player;
import cz.cvut.fel.pjv.player.WhitePlayer;

public enum Colour {
    WHITE {
        @Override
        public int getDirection() {
            return -1;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public Player getCurrentPlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            return whitePlayer;
        }
    },
    BLACK{
        @Override
        public int getDirection() {
            return 1;
        }

        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public Player getCurrentPlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            return blackPlayer;
        }
    };

    public abstract int getDirection();

    public abstract boolean isBlack();

    public abstract boolean isWhite();

    public abstract Player getCurrentPlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);
}
