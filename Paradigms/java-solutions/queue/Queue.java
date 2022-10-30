package queue;

import java.util.function.Predicate;

public interface Queue {
//  Pred: element != null
//  Post: size' = size + 1 && elements'[size] = element && elements'[i] = elements[i] for i in [0..size - 1]
    void enqueue(final Object element);

//  Pred: !isEmpty()
//  Post: size' = size - 1 && result = elements'[0] && elements'[i] = elements[i + 1] for i in [0..size' - 1]
    Object dequeue();

//  Pred: !isEmpty()
//  Post: result = elements[0] && immutable
    Object element();

//  Pred: None
//  Post: size >= 0 && result = size && immutable
    int size();

//  Pred: None
//  Post: size == 0 ? true : false && immutable
    boolean isEmpty();

//  Pred: None
//  Post: size' = 0 && elements' = null
    void clear();

//  Pred: pred != null
//  Post: result = |{i : pred.test(element[i])}|
    int countIf(Predicate<Object> pred);
}
