package queue;

import java.util.function.Predicate;

// Inv: elements[0] .. elements[size - 1], size >= 0
// elements[i] != null for i in [0..size - 1]
public abstract class AbstractQueue implements Queue {
    protected int size = 0;

    protected abstract void add(Object element);
    protected abstract Object take();
    protected abstract void delElem();
    public abstract void clear();

    public void enqueue(Object element) {
        assert element != null;
        add(element);
        size++;
    }

    public Object element() {
        assert !isEmpty();
        return take();
    }

    public Object dequeue() {
        Object res = element();
        delElem();
        size--;
        return res;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int countIf(Predicate<Object> pred) {
        int count = 0;
        for (int i = 0; i < size; i++, enqueue(dequeue())) {
            if (pred.test(element())) {
                count++;
            }
        }
        return count;
    }
}
