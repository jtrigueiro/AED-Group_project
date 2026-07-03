package datastructures;


public class ChainedHashTableIterator<K, V> implements Iterator<Entry<K, V>> {
	
	private KeyIterableDictionary<K,V>[] table;
	private int eleft; //elements left to be returned
	private int currentl; //table current index
	private Iterator<Entry<K, V>> l;
	
	public ChainedHashTableIterator(KeyIterableDictionary<K,V>[] table, int currentSize) {
		this.table = table;
		this.eleft = currentSize;
		currentl = -1;
		for(int i = 0; currentl==-1 ;i++) {
			if(!table[i].isEmpty()) {
				currentl=i;
				l = table[i].iterator();
			}	
		}
	}
	
	@Override
	public boolean hasNext() {
		if(eleft == 0)
			return false;
		else{
			return true;
		}
	}
	
	@Override
	public Entry<K, V> next() {
		if(l.hasNext()) {
			eleft--;
			return l.next();
		}else {
			int oldcurrentl = currentl;
			for(int i = currentl+1; oldcurrentl == currentl && i< table.length ;i++) {
				if(!table[i].isEmpty()) {
					currentl=i;
					l = table[i].iterator();
				}
			}
			eleft--;
			return l.next();
		}		
	}
	
}
