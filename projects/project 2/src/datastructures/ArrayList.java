package datastructures;
import datastructures.exception.EmptyListException;
import datastructures.exception.InvalidPosition;



public class ArrayList<E> implements List<E>{
    private static final int DEFAULT_SIZE = 10;
    private static final int GROWTH = 2;
    private E[] array;
    private int counter;

    private static class ArrayIterator<E> implements Iterator<E> {
        private E[] array;
        private int numElements;
        private int pos;

        private ArrayIterator(E[] array, int counter) {
            this.array = array;
            this.numElements = counter;
            this.pos = 0;
        }
        
        public boolean hasNext() {
            return pos < numElements;
        }

        public E next() {
            return array[pos++];
        }
    }


    @SuppressWarnings("unchecked")
    public ArrayList(int size) {
        array = (E[]) new Object[size];
        this.counter = 0;
    }

    public ArrayList() {
        this(DEFAULT_SIZE);
    }

    public boolean isFull() {
        return counter == array.length;
    }

    public boolean isEmpty() {
        return counter == 0;
    }

    public int size() {
        return counter;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        E[] tmp = (E[]) new Object[GROWTH*counter];
        for (int i= 0; i < array.length; i++)
            tmp[i] = array[i];
        array = tmp;
    }


    public void add(int pos, E element) {
        if(pos < 0 || pos > counter)
            throw new InvalidPosition();
        if(isFull())
            resize();
        for (int i = counter-1; i >= pos; i--) {
            array[i+1] = array[i];
        }
        array[pos] = element;
        counter++;
    }

    public void addFirst(E element) {
        add(0, element);
    }

    public void addLast(E element) {
        add(counter, element);
    }

    public E removeLast() throws EmptyListException {
        if(isEmpty())
            throw new EmptyListException();
        return array[--counter];
    }

    public E remove(int pos) throws InvalidPosition {
        if (pos<0 || pos >= counter) {
            throw new InvalidPosition();
        }
        E element = array[pos];
        for(int i = pos; i<counter-1; i++) {
            array[i] = array[i + 1];
        }
        counter--;
        return element;
    }

    public boolean remove(E element) {
        int index = find(element);
        if (index == -1) {
            return false;
        } else {
            this.remove(index);
            return true;
        }
    }

    public E removeFirst() {
        if(isEmpty())
            throw new EmptyListException();
        return remove(0);
    }

    public Iterator<E> iterator() {
        return new ArrayIterator<E>(array, counter);
    }

    private void checkEmpty() {
        if (isEmpty()) {
            throw new EmptyListException();
        }
    }

    public E getFirst() {
        checkEmpty();
        return array[0];
    }

    public E getLast() {
        checkEmpty();
        return array[counter - 1];
    }

    public E get(int pos) {
        if (pos < 0 || pos >= counter)
            throw new InvalidPosition();
        return array[pos];
    }


    public int find(E element) {
        for(int i = 0; i < counter ; i++) {
            if(array[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

}
