# Iterator Questions

a) Do you think making the Graph Iterable makes semantic sense? Discuss briefly, and think of both sides.
An iterable just refers to a collection of objects which can be iterated over. A graph is a collection of vertices that can be traversed through connections, so I think it does make semantic sense. Although since a graph is not linear and there are many ways to iterate over one graph, perhaps it may not make sense to be refered to as iterable.

b) Is a Graph an iterator or an iterable in this case?
The graph is the iterable

c) What would the .iterator method return in this case?

```java
public Iterator<E> iterator() {
    Iterator<Map.Entry<Integer, String>> iterator = graph.entrySet().iterator()

    Map.Entry<N, List<N>> actualValue = iterator.next();
    BreadthFirstGraphIterator<N> iterator = BreadthFirstGraphIterator<N>(this, actualValue.getKey());

    return (Iterator<E>) iterator;
}
```

d) What is the problem with this approach?
Its not useful for the iterator to start at the same node and traverse the same way for the graphs purposes.