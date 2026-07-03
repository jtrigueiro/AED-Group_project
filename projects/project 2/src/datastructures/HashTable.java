package datastructures;

public interface HashTable<K, V> extends Dictionary<K, V> {
    @Override
    boolean isEmpty();

    int size();

    @Override
    V find(K key);

    @Override
    V put(K key, V value);

    @Override
    V remove(K key);

    Iterator<Entry<K, V>> iterator();

    boolean isFull();
}
