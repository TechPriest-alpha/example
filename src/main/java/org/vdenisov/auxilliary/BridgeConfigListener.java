package org.vdenisov.auxilliary;


import ch.qos.logback.classic.jul.LevelChangePropagator;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * Класс используется для инициализации хендлера slf4jBridge при старте приложения
 *
 * <p><b>Использование:</b>
 * <p>1) - Добавить в WEB модуль зависимость от этой библиотеки.</p>
 * <pre>
 * &lt;dependency&gt;
 * &lt;groupId&gt;ru.glonass.common&lt;/groupId&gt;
 * &lt;artifactId&gt;logback-utils&lt;/artifactId&gt;
 * &lt;/dependency&gt;
 * </pre>
 *
 * <p>2) - Добавить в logback.xml настройку данного слушателя.</p>
 * <pre>
 * &lt;contextListener class="ru.glonass.era.logback.listener.BridgeConfigListener"&gt;
 * &lt;/contextListener&gt;
 * </pre>
 *
 * @author Наталья Максимова
 * @version 1.0 - 11.01.2017
 */
public class BridgeConfigListener extends LevelChangePropagator {

    public BridgeConfigListener() {
        setResetJUL(true);
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

}