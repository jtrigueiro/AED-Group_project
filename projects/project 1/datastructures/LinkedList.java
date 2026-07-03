package datastructures;

import datastructures.exception.ElementDoesNotExists;
import datastructures.exception.EmptyListException;
import datastructures.exception.InvalidPosition;

public class LinkedList<T> implements List<T> {
    static class Node<T> {
        private Node<T> next;
        private Node<T> previous;
        private final T element;

        Node(T element) {
            this.element = element;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public Node<T> getPrevious() {
            return previous;
        }

        public void setPrevious(Node<T> previous) {
            this.previous = previous;
        }

        public T getElement() {
            return element;
        }
    }
    static class LinkedListIterator<T> implements Iterator<T> {
        private Node<T> currentNode;

        private LinkedListIterator(Node<T> currentNode) {
            this.currentNode = currentNode;
        }


        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new ElementDoesNotExists();
            }
            final T result = currentNode.getElement();
            currentNode = currentNode.getNext();
            return result;
        }
    }
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator<>(head);
    }

    private void exceptIfEmpty() {
        if (this.isEmpty()) {
            throw new EmptyListException();
        }
    }
    @Override
    public T getFirst() {
        exceptIfEmpty();
        return this.head.getElement();
    }

    @Override
    public T getLast() {
        exceptIfEmpty();
        return this.tail.getElement();
    }


    @Override
    public void addFirst(T element) {
        Node<T> node = new Node<>(element);
        if (!isEmpty()) {
            this.head.setPrevious(node);
            node.setNext(this.head);
        } else {
            this.tail = node;
        }
        this.head = node;
        size++;
    }

    @Override
    public void addLast(T element) {
        Node<T> node = new Node<>(element);
        if (!isEmpty()) {
            this.tail.setNext(node);
            node.setPrevious(this.tail);
        } else {
            this.head = node;
        }
        this.tail = node;
        size++;
    }

    @Override
    public void add(int position, T element) {
        if (position > size || position < 0) {
            throw new InvalidPosition();
        }
        if (position == 0) {
            addFirst(element);
        } else if (position == size) {
            addLast(element);
        } else {
            Node<T> previousNode = this.head;
            for (int i = 0; i < position-1; i++) {
                previousNode = previousNode.getNext();
            }
            Node<T> nextNode = previousNode.getNext();
            Node<T> newNode = new Node<>(element);

            previousNode.setNext(newNode);
            nextNode.setPrevious(newNode);
            newNode.setNext(nextNode);
            newNode.setPrevious(previousNode);
            size++;
        }

    }





    @Override
    public T removeFirst() {
        exceptIfEmpty();
        T removed = this.head.getElement();
        Node<T> second = this.head.getNext();
        this.head = second;
        if (second != null) {
            second.setPrevious(null);
        } else {
            this.tail = null;
        }
        size--;
        return removed;
    }

    @Override
    public T removeLast() {
        exceptIfEmpty();
        T removed = this.tail.getElement();
        Node<T> lastButOne = this.tail.getPrevious();
        this.tail = lastButOne;
        if (lastButOne != null) {
            lastButOne.setNext(null);
        } else {
            this.head = null;
        }
        size--;
        return removed;
    }




    @Override
    public T remove(int position) {
        if (position >= size || position < 0) {
            throw new InvalidPosition();
        }
        exceptIfEmpty();
        if (position == 0) {
            return removeFirst();
        } else if (position == size - 1) {
            return removeLast();
        } else {
            Node<T> previousNode = this.head;
            // TODO: resolver pelo caminho mais curto
            for (int i = 0; i < position-1; i++) {
                previousNode = previousNode.getNext();
            }
            Node<T> nextNode = previousNode.getNext().getNext();
            T removed = previousNode.getNext().getElement();
            previousNode.setNext(nextNode);
            nextNode.setPrevious(previousNode);
            size--;
            return removed;
        }
    }

    private Node<T> findNode(T element) {
        Node<T> node = this.head;
        while (node != null && !node.getElement().equals(element)) {
            node = node.getNext();
        }
        return node;
    }

    @Override
    public boolean remove(T element) {
        Node<T> node = findNode(element);
        if (node == null) {
            return false;
        }
        Node<T> previousNode = node.getPrevious();
        Node<T> nextNode = node.getNext();
        if (previousNode == null) {
            removeFirst();
        } else if (nextNode == null) {
            removeLast();
        } else {
            previousNode.setNext(nextNode);
            nextNode.setPrevious(previousNode);
            size--;
        }
        return true;
    }

    @Override
    public T get(int pos) {
        // TODO: resolver pelo caminho mais curto
        if (pos >= size || pos < 0) {
            throw new InvalidPosition();
        }
        Node<T> node = this.head;
        for (int i = 0; i < pos; i++) {
            node = node.getNext();
        }
        return node.getElement();
    }

    @Override
    public int find(T element) {
        Node<T> node = this.head;
        int i;
        for (i = 0; node != null && !node.getElement().equals(element); i++) {
            node = node.getNext();
        }
        if (node == null) {
            return -1;
        } else {
            return i;
        }
    }
}