package base;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
@SuppressWarnings("UseOfSystemOutOrSystemErr")
public class MainChecker implements Checker {
    public static final String ENCODING = "utf8";
    private final Method method;
    protected final TestCounter counter = new TestCounter();
    protected final ExtendedRandom random = new ExtendedRandom();

    public MainChecker(final String className) {
        try {
            final URL url = new File(".").toURI().toURL();
            final Class<?> clazz = new URLClassLoader(new URL[]{url}).loadClass(className);
//            clazz.newInstance();
            method = clazz.getMethod("main", String[].class);
        } catch (final Exception e) {
            throw Asserts.error("Could not find main(String[]) in class %s", className, e);
        }
    }

    public List<String> run(final String... input) {
        return runComment(Stream.of(input).collect(Collectors.joining("\" \"", "\"", "\"")), input);
    }

    public List<String> runComment(final String comment, final String... input) {
        counter.format("Running test %02d: java %s %s\n", counter.getTestNo(), method.getDeclaringClass().getName(), comment);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final PrintStream oldOut = System.out;
        try {
            System.setOut(new PrintStream(out, false, ENCODING));
            method.invoke(null, new Object[]{input});
            final BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(out.toByteArray()), ENCODING));
            final List<String> result = new ArrayList<>();
            while (true) {
                final String line = reader.readLine();
                if (line == null) {
                    if (result.isEmpty()) {
                        result.add("");
                    }
                    return result;
                }
                result.add(line.trim());
            }
        } catch (final InvocationTargetException e) {
            final Throwable cause = e.getCause();
            throw Asserts.error("main thrown exception %s: %s", cause.getClass().getSimpleName(), cause.getMessage(), cause);
        } catch (final Exception e) {
            throw Asserts.error("Cannot invoke main: %s: %s", e.getClass().getSimpleName(), e.getMessage(), e);
        } finally {
            System.setOut(oldOut);
        }
    }

    protected void testEquals(final List<String> expected, final Supplier<List<String>> runner) {
        counter.test(() -> {
            final List<String> actual = runner.get();
            for (int i = 0; i < Math.min(expected.size(), actual.size()); i++) {
                final String exp = expected.get(i);
                final String act = actual.get(i);
                if (!exp.equalsIgnoreCase(act)) {
                    Asserts.assertEquals("Line " + (i + 1), exp, act);
                    return;
                }
            }
            Asserts.assertEquals("Number of lines", expected.size(), actual.size());
        });
    }

    @Override
    public void printStatus(final Class<?> aClass) {
        counter.printStatus(aClass);
    }

    @Override
    public ExtendedRandom getRandom() {
        return random;
    }
}
