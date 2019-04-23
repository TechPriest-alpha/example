package io.example.rps.game.strategies;

import io.example.rps.game.strategies.basic.BasicRandomStrategy;
import io.example.rps.game.strategies.basic.CheatingStrategy;
import io.example.rps.game.strategies.basic.FixedRockStrategy;
import io.example.rps.game.strategies.custom.ScienceStrategy;
import io.example.rps.game.strategies.stats.PredictingStrategy;
import io.example.rps.game.strategies.stats.StatsStrategy;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@UtilityClass
public class StrategiesRegistry {
    private static final ConcurrentMap<String, GameStrategy> STRATEGIES = new ConcurrentHashMap<>();

    static {
        register(new CheatingStrategy());
        register(new BasicRandomStrategy());
        register(new FixedRockStrategy());
        register(new ScienceStrategy());
        register(new StatsStrategy());
        register(new PredictingStrategy());
    }

    private static void register(final GameStrategy gameStrategy) {
        STRATEGIES.put(gameStrategy.name(), gameStrategy);
    }

    public static List<String> available() {
        return new ArrayList<>(STRATEGIES.keySet());
    }

    public static GameStrategy byName(final String strategyName) {
        return STRATEGIES.getOrDefault(strategyName, getDefault()).get();
    }

    public static GameStrategy getDefault() {
        return new FixedRockStrategy();
    }
}
