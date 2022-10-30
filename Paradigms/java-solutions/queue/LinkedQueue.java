package queue;

public class LinkedQueue extends AbstractQueue {
    private Node head;
    private Node tail;

    {
        head = tail = new Node(0, null);
    }

    protected void add(final Object element) {
        tail.next = new Node(element, null);
        tail = tail.next;
        if (isEmpty()) {
            head = tail;
        }
    }

    protected void delElem() {
        head = head.next;
    }

    protected Object take() {
        return head.element;
    }

    public void clear() {
        while (!isEmpty()) {
            dequeue();
        }
    }

    private class Node {
        private final Object element;
        private Node next;

        public Node(Object element, Node next) {
            assert element != null;

            this.element = element;
            this.next = next;
        }
    }
}
