package com.prasilabs.droidwizard;

/**
 * Created by Contus team on 11/7/17.
 */

public enum XY {
    X,
    Y;

    public XY switchPlayer() {
        if(this == X) {
            return Y;
        } else {
            return X;
        }
    }
}
