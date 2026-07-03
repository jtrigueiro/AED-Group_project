package datastructures;


import datastructures.exception.ElementDoesNotExists;

public class CollisionListClass<K, V> implements KeyIterableDictionary<K, V> {

    static class Node<K, V> {
        private Node<K, V> next;
        private Node<K, V> previous;
        private final Entry<K, V> ent;
        
        public Node(K key, V value) {
           ent = new EntryClass<>(key, value);
        }

        public Node<K, V> getNext() {
            return next;
        }

        public void setNext(Node<K, V> next) {
            this.next = next;
        }

        public Node<K, V> getPrevious() {
            return previous;
        }

        public void setPrevious(Node<K, V> previous) {
            this.previous = previous;
        }

        public Entry<K, V> getElement() {
            return ent;
        }
        
    }
    static class CollisionListIterator<K, V> implements Iterator<Entry<K, V>> {
        private Node<K, V> currentNode;

        private CollisionListIterator(Node<K, V> currentNode) {
            this.currentNode = currentNode;
        }


        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new ElementDoesNotExists();
            }
            final Entry<K, V> result = currentNode.getElement();
            currentNode = currentNode.getNext();
            return result;
        }
    }
    private Node<K, V> head;
    private Node<K, V> tail;
    private int size;

    public CollisionListClass() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

	@Override
    public Iterator<Entry<K, V>> iterator() {
		return new CollisionListIterator<>(head);
	}
    
    private Entry<K, V> removeFirst() {
        Entry<K, V> removed = this.head.getElement();
        Node<K,V> second = this.head.getNext();
        this.head = second;
        if (second != null) {
            second.setPrevious(null);
        } else {
            this.tail = null;
        }
        size--;
        return removed;
    }

    private  Entry<K, V> removeLast() {
    	Entry<K, V> removed = this.tail.getElement();
        Node<K, V> lastButOne = this.tail.getPrevious();
        this.tail = lastButOne;
        if (lastButOne != null) {
            lastButOne.setNext(null);
        } else {
            this.head = null;
        }
        size--;
        return removed;
    }
 
    private Node<K, V> findNode(K key) {
        Node<K, V> node = this.head;
        while (node != null) {
        	if(node.getElement().getKey().equals(key))
        		return node;
            node = node.getNext();
        }
        return null;
    }
    
	@Override
	public V find(K key) {
		Node<K, V> node = this.head;
		while(node != null) {
        	if(node.getElement().getKey().equals(key))
        		return node.getElement().getValue();
            node = node.getNext();
        }
		return null;
	}

	@Override
	public V put(K key, V value) {
		Node<K, V> tempn = findNode(key);
		if(tempn != null) {
			return tempn.getElement().setValue(value);
		}else {
			Node<K, V> node = new Node<>(key, value);
			if (!isEmpty()) {
				this.tail.setNext(node);
				node.setPrevious(this.tail);
			} else {
				this.head = node;
			}
			this.tail = node;
			size++;
			return null;
		}
	}

	@Override
	public V remove(K key) {
		 Node<K, V> node = findNode(key);
	        if (node == null) {
	            return null;
	        }
	        Node<K, V> previousNode = node.getPrevious();
	        Node<K, V> nextNode = node.getNext();
	        if (previousNode == null) {
	            removeFirst();
	        } else if (nextNode == null) {
	            removeLast();
	        } else {
	            previousNode.setNext(nextNode);
	            nextNode.setPrevious(previousNode);
	            size--;
	        }
	        return node.getElement().getValue();
	}

    @Override
    public boolean hasKey(K key) {
        return find(key) != null;
    }

}