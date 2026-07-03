package datastructures;

public interface SortedDictionary<K extends Comparable<K>, V> extends Dictionary<K, V> {
    Iterator<V> values();
}
