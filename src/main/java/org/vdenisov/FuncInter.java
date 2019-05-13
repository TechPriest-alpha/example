package org.vdenisov;

@FunctionalInterface
public interface FuncInter {

    void doSmthng();

    default void doOther() {

    }
}
