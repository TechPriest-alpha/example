package io.example.rps.game.model;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public enum ValidMove {
    ROCK, PAPER, SCISSORS;

    public static List<String> valueNames() {
        return Arrays.stream(values()).map(Enum::name).collect(toList());
    }
}
