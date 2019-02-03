package io.example.server;

import io.example.auxiliary.Constants;
import io.vertx.core.Launcher;

public class Main extends Launcher {

    public static void main(final String[] args) {
        System.setProperty(Constants.CFG_PACKAGE_CONFIG_NAME, "io.example.server.config");
        new Main().dispatch(args);
    }
}
