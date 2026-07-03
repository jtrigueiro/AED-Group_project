package datastructures;



/**
 * Chained Hash table implementation
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key, must extend comparable
 * @param <V> Generic Value 
 */

public class ChainedHashTable<K, V>
    extends AbstractHashTable<K,V>
{ 
	//The array of dictionaries.
    protected KeyIterableDictionary<K,V>[] table;


    /**
     * Constructor of an empty chained hash table,
     * with the specified initial capacity.
     * Each position of the array is initialized to a new ordered list
     * maxSize is initialized to the capacity.
     * @param capacity defines the table capacity.
     */
   
    public ChainedHashTable( int capacity )
    {
    	reset(capacity);
    }                                      


    public ChainedHashTable( )
    {
        this(DEFAULT_CAPACITY);
    }                                                                

    /**
     * Returns the hash value of the specified key.
     * @param key to be encoded
     * @return hash value of the specified key
     */
    protected int hash( K key )
    {
        return Math.abs( key.hashCode() ) % table.length;
    }

    @Override
    public V find( K key )
    {
    	 return table[hash(key)].find(key);
    }

    @Override
    public V put(K key, V value )
    {
    	if ( this.isFull() )
            this.rehash();
    	V temp = table[hash(key)].put(key, value);
    	if(temp == null) {
    		currentSize++;
    		return null;
    	}else
    		return temp;
    }

    @Override
    public V remove( K key )
    {
    	V temp = table[hash(key)].remove(key);
    	if(temp == null)
    		return null;
    	else {
    		currentSize--;
    		return temp;
    	}	
    }

    @Override
    public boolean hasKey(K key) {
        return find(key) != null;
    }

    @Override
    public Iterator<Entry<K,V>> iterator()
    {
    	if(isEmpty())
    		return null;
    	else {
    		return new ChainedHashTableIterator<>(table, currentSize);
    	}  
    }
    
    private void rehash() {
    	Iterator<Entry<K, V>> it = iterator();
    	int newsize = maxSize*2;
    	reset(newsize);
    	while(it.hasNext()) {
    		Entry<K, V> temp= it.next();
    		put(temp.getKey(), temp.getValue());
    	}
    }

    @SuppressWarnings("unchecked")
    private void reset(int capacity) {
    	 int arraySize = AbstractHashTable.nextPrime((int) (1.1 * capacity));
         // Compiler gives a warning.
         table = (KeyIterableDictionary<K,V>[]) new KeyIterableDictionary[arraySize];
         for ( int i = 0; i < arraySize; i++ )
             table[i] = new CollisionListClass<K,V>();
         maxSize = capacity;
         currentSize = 0;
    }
}


