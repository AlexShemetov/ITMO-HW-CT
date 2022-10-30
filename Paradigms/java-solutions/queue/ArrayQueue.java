package queue;

public class ArrayQueue extends AbstractQueue {
    private Object[] elements = new Object[1];
    private int head = 0;

    protected void add(final Object element) {
        ensureCapacity();
        elements[(head + size) % elements.length] = element;
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            Object[] elementsCopy = new Object[elements.length * 2];
            System.arraycopy(elements, head, elementsCopy, 0, elements.length - head);
            System.arraycopy(elements, 0, elementsCopy, elements.length - head, head);
            elements = elementsCopy;
            head = 0;
        }
    }

    protected Object take() {
        return elements[head];
    }

    protected void delElem() {
        elements[head] = null;
        head = (head + 1) % elements.length;
    }

    public void clear() {
        elements = new Object[1];
        head = size = 0;
    }

    public int count(Object element) {
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
