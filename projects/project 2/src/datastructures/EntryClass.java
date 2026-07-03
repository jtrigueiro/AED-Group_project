package datastructures;

public class EntryClass<K,V> implements Entry<K,V> {
	
	private K key;
	private V value;
	
	public EntryClass(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}
	
	public V setValue(V value) {
		V temp = this.value;
		this.value = value;
		return temp;
	}
	
}
