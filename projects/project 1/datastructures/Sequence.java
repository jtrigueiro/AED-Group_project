package datastructures;

public interface Sequence<E> {
	boolean isEmpty();
	int size();
	Iterator<E> iterator();
	int find(E element);
	void add(E element);
	boolean remove(E element);
}
