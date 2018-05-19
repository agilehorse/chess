package cz.cvut.fel.pjv.engine.player;

public enum MoveState {
    VALID{
        @Override
        public boolean done() {
            return true;
        }
    },
    LEAVES_PLAYER_IN_CHECK{
        @Override
        public boolean done() {
            return true;
        }
    },
    INVALID{
        @Override
        public boolean done() {
            return false;
        }
    };

    public abstract boolean done();
}
