package unsw.collections;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
* @param <E> The type of elements contained within this set.
* @invariant All e in elements occur only once
*/
public class ArrayListSet<E> implements Set<E> {
    private List<E> arrayList;

    public ArrayListSet() {
        arrayList = new ArrayList<E>();
    }

    /**
     * Add an element to the set. Set is unchanged if it already contains the
     * element.
     *
     * @param e The element to add
     * @post contains(e)
     */
    public void add(E e) {
        if (!arrayList.contains(e)) arrayList.add(e);
    }

    /**
     * Remove an element from the set.
     *
     * @param e The element to remove
     * @post !contains(e)
     */
    public void remove(E e) {
        arrayList.remove(e);
    }

    /**
     * Determine if the given element is in the set.
     *
     * @param e The element to test against
     * @return true - if the element is in the set, false otherwise
     */
    public boolean contains(Object e) {
        return arrayList.contains(e);
    }

    /**
     * Get the number of elements in the set.
     * @return size - the number of elements in the set
     * @post size >= 0
     */
    public int size() {
        return arrayList.size();
    }

    /**
     * Determine if this set is a subset of another set.
     *
     * @param other The possible super set.
     * @return subset - Whether or not the subset relation holds.
     * @post subset if and only if (forall e. contains(e) => other.contains(e))
     */
    public boolean subsetOf(Set<?> other) {
        for (E element : arrayList) {
            if (!other.contains(element)) return false;
        }
        return true;
    }

    /**
     * Return a new set that is the union of this set and the given set
     *
     * @param other The other set operand.
     * @return result - A new set that is the union of these two sets.
     * @post for all e in result, contains(e) or other.contains(e)
     */
    public Set<E> union(Set<? extends E> other) {
        Set<E> unionSet = new ArrayListSet<E>();
        for (E element : arrayList) {
            unionSet.add(element);
        }
        for (E element : other) {
            unionSet.add(element);
        }

        return unionSet;
    }

    /**
     * Return a new set that is the intersection of this set and the given set
     *
     * @param other The other set operand.
     * @return result - A new set that is the intersection of these two sets.
     * @post for all e in result, contains(e) and other.contains(e)
     */
    public Set<E> intersection(Set<? extends E> other) {
        Set<E> intersectionSet = new ArrayListSet<E>();
        for (E element : arrayList) {
            if (other.contains(element)) intersectionSet.add(element);
        }

        return intersectionSet;
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size() && arrayList.get(currentIndex) != null;
            }

            @Override
            public E next() {
                return arrayList.get(currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

    @Override
    public boolean equals(Object e) {
        if (e == null) return false;
        if (e == this) return true;
        if (!(e instanceof ArrayListSet)) return false;

        ArrayListSet<?> eList = (ArrayListSet<?>) e;
        if (eList.size() != this.size()) return false;

        for (E element : arrayList) {
            if (!eList.contains(element)) return false;
        }

        return true;
    }
}
