package base;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Checker {
    void printStatus(final Class<?> aClass);

    default void printStatus() {
        printStatus(getClass());
    }

    ExtendedRandom getRandom();
}
