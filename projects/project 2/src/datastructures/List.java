package datastructures;

import datastructures.exception.EmptyListException;
import datastructures.exception.InvalidPosition;

/**
 * A sequence of elements that the user is the responsible to handle the position of elements
 */
public interface List<E> {

	/**
	 * Verify if the list is empty
	 * @return true if empty
	 */
	boolean isEmpty();

	/**
	 * Get the size of the list
	 * @return the number of elements of the list
	 */
	int size();

	/**
	 * Get the iterator of the list
	 * @return the iterator that gives access from the elements in order
	 */
	Iterator<E> iterator();

	/**
	 * Get the first element
	 * @return the first element
	 * @throws EmptyListException if the List is empty
	 */
	E getFirst();

	/**
	 * Get the last element
	 * @return the last element
	 * @throws EmptyListException if the List is empty
	 */
	E getLast();

	/**
	 * Add element in the beginning of the list
	 */
	void addFirst(E element);

	/**
	 * Add element in the end of the list
	 */
	void addLast(E element);

	/**
	 * Add element in the specified position of the list
	 * @throws InvalidPosition if the position is incoherent to the list
	 */
	void add(int position, E element);

	/**
	 * Remove the first element from list
	 * @return the removed element
	 * @throws EmptyListException if the List is empty
	 */
	E removeFirst();
	/**
	 * Remove the last element from list
	 * @return the removed element
	 * @throws EmptyListException if the List is empty
	 */
	E removeLast();

	/**
	 * Remove the element in the specified position of the list
	 * @return the removed element
	 * @throws InvalidPosition if the position is incoherent to the list
	 */
	E remove(int position);


	/**
	 * Find a element in the list and remove it
	 * note: The criteria to match the element the use of its method "equals()"
	 * @return the removed element
	 */
	boolean remove(E element);

	/**
	 * get the element with position
	 * @return the element with position
	 * @throws InvalidPosition if the position is incoherent to the list
	 */
    E get(int pos);

	/**
	 * Find a element in the list and get it
	 * note: The criteria to match the element the use of its method "equals()"
	 * @return the position of the element
	 */
    int find(E element);
}
