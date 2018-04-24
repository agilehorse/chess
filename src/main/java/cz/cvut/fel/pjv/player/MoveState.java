package cz.cvut.fel.pjv.player;

public enum MoveState {
    VALID{
        @Override
        boolean done() {
            return true;
        }
    },
    LEAVES_PLAYER_IN_CHECK{
        @Override
        boolean done() {
            return true;
        }
    },
    INVALID{
        @Override
        boolean done() {
            return false;
        }
    };

    abstract boolean done();
}
