package datastructures;

public class SortedSequence<E extends Comparable<E>> implements Sequence<E>{

	private final List<E> elementList;

	public SortedSequence() {
		this.elementList = new LinkedList<E>();
	}

	public boolean isEmpty() {
		return elementList.isEmpty();
	}

	public int size() {
		return elementList.size();
	}

	public Iterator<E> iterator() {
		return elementList.iterator();
	}

	public int find(E element) {
		return elementList.find(element);
	}


	public boolean remove(E element) {
		return elementList.remove(element);
	}

	public void add(E element) {
		Iterator<E> it = elementList.iterator();
		int i;
		for (i = 0; it.hasNext(); i++) {
			if (it.next().compareTo(element) > 0) {
				break;
			}
		}

		// TODO: criar um metodo que permita adicionar a partir da posicao do iterador
		// O(1)
		// elementList.addBefore(it, element);

		// O(n)
		elementList.add(i, element);

	}
	
}
