package io.example;

import io.vertx.core.Launcher;

/**
 * @author Viktor
 * @since 2018-10-27
 */
public class Main extends Launcher {

    public static void main(final String[] args) {
        System.setProperty(Constants.CFG_PACKAGE, "io.example.cfg");
        new Main().dispatch(args);
    }
}
