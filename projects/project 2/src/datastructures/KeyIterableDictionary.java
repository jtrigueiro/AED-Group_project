package datastructures;

public interface KeyIterableDictionary<K, V> extends Dictionary<K, V> {
    Iterator<Entry<K, V>> iterator();
}
