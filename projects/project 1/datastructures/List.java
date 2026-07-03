package datastructures;

public interface List<E> {
	
	boolean isEmpty();
	int size();
	Iterator<E> iterator();
	E getFirst();
	E getLast();
	void addFirst(E element);
	void addLast(E element);
	void add(int position, E element);
	// void addAfter(Iterator<E> it, E element);
	E removeFirst();
	E removeLast();
	E remove(int position);
	boolean remove(E element);
    E get(int pos);
    int find(E element);
}
