package cz.cvut.fel.pjv.engine.board.moves;

public enum MoveType {
    ATTACK {
        boolean isAttacking() {
            return true;
        }

        boolean isCastling() {
            return false;
        }
    },
    CASTLE {
        boolean isAttacking() {
            return false;
        }

        boolean isCastling() {
            return true;
        }
    },
    ILLEGAL {
        boolean isAttacking() {
            return false;
        }

        boolean isCastling() {
            return false;
        }
    },
    NORMAL {
        boolean isAttacking() {
            return false;
        }

        boolean isCastling() {
            return false;
        }
    };

    abstract boolean isAttacking();

    abstract boolean isCastling();
}
