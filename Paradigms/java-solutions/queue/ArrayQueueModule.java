package queue;

public class ArrayQueueModule {
    private static Object[] elements = new Object[1];
    private static int head = 0;
    private static int size = 0;

    public static void enqueue(final Object element) {
        assert element != null;

        ensureCapacity();
        elements[(head + size) % elements.length] = element;
        size++;
    }

    private static void ensureCapacity() {
        if (elements.length == size) {
            Object[] elementsCopy = new Object[elements.length * 2];
            System.arraycopy(elements, head, elementsCopy, 0, elements.length - head);
            System.arraycopy(elements, 0, elementsCopy, elements.length - head, head);
            elements = elementsCopy;
            head = 0;
        }
    }

    public static Object element() {
        assert !isEmpty();

        return elements[head];
    }

    public static Object dequeue() {
        assert !isEmpty();

        Object element = element();
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;
        return element;
    }

    public static int size() {
        return size;
    }

    public static boolean isEmpty() {
        return size == 0;
    }

    public static void clear() {
        elements = new Object[1];
        head = size = 0;
    }

    public static int count(Object element) {
        assert element != null;

        int total = 0;
        for (Object elemInQueue : elements) {
            if (element.equals(elemInQueue)) {
                total++;
            }
        }
        return total;
    }
}