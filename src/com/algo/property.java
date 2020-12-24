package com.algo;

public enum property {

    TRUE {
        @Override
        public property and(property p) {
            switch (p) {
                case TRUE:
                    return TRUE;
                case FALSE:
                    return FALSE;
                case NOTASSIGN:
                    return NOTASSIGN;
                default:
                    return TRUE;
            }
        }

        @Override
        public property or(property p) {
            switch (p) {
                case TRUE:
                    return TRUE;
                case FALSE:
                    return TRUE;
                case NOTASSIGN:
                    return TRUE;
                default:
                    return TRUE;
            }
        }

        @Override
        public property not() {
            return property.FALSE;
        }
    },


    FALSE {
        @Override
        public property or(property p) {
            switch (p) {
                case TRUE:
                    return TRUE;
                case FALSE:
                    return FALSE;
                case NOTASSIGN:
                    return FALSE;
                default:
                    return FALSE;
            }
        }

        @Override
        public property not() {
            return property.TRUE;
        }

        @Override
        public property and(property p) {
            switch (p) {
                case TRUE:
                    return FALSE;
                case FALSE:
                    return FALSE;
                case NOTASSIGN:
                    return FALSE;
                default:
                    return FALSE;
            }
        }
    },


    NOTASSIGN {
        @Override
        public property or(property p) {
            switch (p) {
                case TRUE:
                    return TRUE;
                case FALSE:
                    return FALSE;
                case NOTASSIGN:
                    return NOTASSIGN;
                default:
                    return NOTASSIGN;
            }
        }

        @Override
        public property and(property p) {
            switch (p) {
                case TRUE:
                    return NOTASSIGN;
                case FALSE:
                    return FALSE;
                case NOTASSIGN:
                    return NOTASSIGN;
                default:
                    return NOTASSIGN;
            }
        }

        @Override
        public property not() {
            return property.NOTASSIGN;
        }
    };

    public abstract property not();

    public abstract property or(property p);

    public abstract property and(property p);
}

