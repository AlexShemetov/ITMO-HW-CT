package queue;

public class ArrayQueueADT {
    private Object[] elements = new Object[1];
    private int head = 0;
    private int size = 0;

    public static void enqueue(ArrayQueueADT que, final Object element) {
        assert element != null;

        ensureCapacity(que);
        que.elements[(que.head + que.size) % que.elements.length] = element;
        que.size++;
    }

    private static void ensureCapacity(ArrayQueueADT que) {
        if (que.elements.length == que.size) {
            Object[] elementsCopy = new Object[que.elements.length * 2];
            System.arraycopy(que.elements, que.head, elementsCopy, 0, que.elements.length - que.head);
            System.arraycopy(que.elements, 0, elementsCopy, que.elements.length - que.head, que.head);
            que.elements = elementsCopy;
            que.head = 0;
        }
    }

    public static Object element(ArrayQueueADT que) {
        assert !isEmpty(que);

        return que.elements[que.head];
    }

    public static Object dequeue(ArrayQueueADT que) {
        assert !isEmpty(que);

        Object element = element(que);
        que.elements[que.head] = null;
        que.head = (que.head + 1) % que.elements.length;
        que.size--;
        return element;
    }

    public static int size(ArrayQueueADT que) {
        return que.size;
    }

    public static boolean isEmpty(ArrayQueueADT que) {
        return que.size == 0;
    }

    public static void clear(ArrayQueueADT que) {
        que.elements = new Object[1];
        que.head = que.size = 0;
    }

    public static int count(ArrayQueueADT que, Object element) {
        assert element != null;

        int total = 0;
        for (Object elemInQueue : que.elements) {
            if (element.equals(elemInQueue)) {
                total++;
            }
        }
        return total;
    }
}
