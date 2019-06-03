package example.io;

@FunctionalInterface
public interface FuncInter {

    void doSmthng();

    default void doOther() {

    }
}
