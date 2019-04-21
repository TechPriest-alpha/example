package io.example.rps.game;

import io.example.rps.game.api.console.ConsoleGame;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(final String[] args) {
        new ConsoleGame().doPlay();
    }
}
