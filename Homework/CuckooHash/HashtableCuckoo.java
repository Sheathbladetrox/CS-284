// Andrew Chuah
// I pledge my honor that I have abided by the Stevens Honor System.
package CuckooHash;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HashtableCuckoo<K, V> implements KWHashMap<K, V> {

    private Entry<K, V>[] table1;
    private Entry<K, V>[] table2;
    private List<Entry<K, V>> overflow;
    private static final int START_CAPACITY = 100;
    private double LOAD_THRESHOLD = 0.6;
    private int tableSize = (int)(START_CAPACITY * LOAD_THRESHOLD);
    private int numKeys;

    /**
     * Initializes the Cuckoo structure with two tables and an overflow list
     */
    public HashtableCuckoo() {
	    overflow = new ArrayList<>();
	    table1 = new Entry[START_CAPACITY];
	    table2 = new Entry[START_CAPACITY];
    }
  
    /** Contains key-value pairs for a hash table. */
    public static class Entry<K, V> implements Map.Entry<K, V> {

        /** The key */
        private K key;
        /** The value */
        private V value;

        /**
         * Creates a new key-value pair.
         * @param key The key
         * @param value The value
         */
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Retrieves the key.
         * @return The key
         */
        @Override
        public K getKey() {
            return key;
        }

        /**
         * Retrieves the value.
         * @return The value
         */
        @Override
        public V getValue() {
            return value;
        }

        /**
         * Sets the value.
         * @param val The new value
         * @return The old value
         */
        @Override
        public V setValue(V val) {
            V oldVal = value;
            value = val;
            return oldVal;
        }

	    /**
         * Return a String representation of the Entry
         * @return a String representation of the Entry
         *         in the form key = value
         */
        @Override
        public String toString() {
            return key.toString() + "," + value.toString();
        }
        
    }

    /**
     * Returns the current size of the structure, including tables and stash
     *
     * @return the cuckoo structure size
     */
    @Override
    public int size() {
        return numKeys;
    }

    /**
     * Checks if the structure is currently empty, or numKeys == 0
     *
     * @return true if it is empty, false if it isn't
     */
    @Override
    public boolean isEmpty() {
        return numKeys == 0;
    }

    /**
     * Determines hash value of a given object using two hash functions
     *
     * @param o object in question
     * @param num function number to denote different hash functions
     * @return the hash value of the object
     */
    private int getHash(Object o, int num){
        int objectHash = o.hashCode();
        if(num == 0)
            return objectHash;
        else if(num == 1) {
            objectHash = objectHash << 16 | objectHash >>> 16;
            return objectHash;
        }
        else
            return -1;
    }

    /**
     * Finds and returns the value associated with the key.
     *
     * @param key the key
     * @return the value of the key, or null if it can't be found.
     */
    @Override
    public V get(Object key) {
        int index1 = getHash(key, 0) % tableSize;
        int index2 = getHash(key, 1) % tableSize;

        if(index1 < 0)
            index1 += tableSize;

        if(index2 < 0)
            index2 += tableSize;

        if(table1[index1].key.toString().equals(key.toString()))
            return table1[index1].value;

        else if(table2[index2].key.toString().equals(key.toString()))
            return table2[index2].value;

        else {
            for (Entry<K, V> nextItem : overflow) {
                if (nextItem.key.toString().equals(key.toString()))
                    return nextItem.value;
            }
        }
        return null;
    }

    /**
     * This method inserts an entry with a defined key and value. It will calculate the index at which
     * the key must go in the tables. Then, it tries to insert the key inside the tables using the
     * corresponding hash functions for each table. If the space is occupied by a different key-value
     * pair, then the first entry will replace the second entry, and bump the second entry into the
     * other table. The second entry will then have to find the index to place the entry in. This
     * keeps going until a cycle is detected, where an entry can't find a spot to insert itself
     * into the tables. In this case, the entry that can't find a spot is placed in the overflow list.
     *
     * @param key the key to be inserted
     * @param value the value to be inserted
     * @return either the old value that was replaced by the new value, or null if the value that was
     *         inserted had a corresponding unique key, or null if the key-value pair was inserted in
     *         the overflow list.
     */
    @Override
    public V put(K key, V value) {
        int index1 = getHash(key, 0) % tableSize;
        int index2 = getHash(key, 1) % tableSize;

        //Creates a 2D array filled with booleans to check if the index was checked before.
        boolean[][] check = new boolean[2][tableSize];
        for(int i = 0; i < check.length; i++){
            for(int j = 0; j < check[i].length; j++){
                check[i][j] = false;
            }
        }

        if(index1 < 0)
            index1 += tableSize;

        if(index2 < 0)
            index2 += tableSize;

       	if(table1[index1] != null && table1[index1].key.toString().equals(key.toString()))
       	    return table1[index1].setValue(value);

       	else if(table2[index2] != null && table2[index2].key.toString().equals(key.toString()))
       	    return table2[index2].setValue(value);

       	else if(!overflow.isEmpty()){
       	    for(Entry<K, V> nextItem : overflow){
       	        if(nextItem.key.toString().equals(key.toString()))
       	            return nextItem.setValue(value);
            }
        }

       	Entry<K, V> newEntry = new Entry<>(key, value);
       	int index = getHash(newEntry.key, 0) % tableSize;

       	if(index < 0)
       	    index += tableSize;

       	if(check[0][index])
       	    overflow.add(newEntry);
       	else {
            Entry<K, V> temp = table1[index];
            table1[index] = newEntry;
            check[0][index] = true;
            newEntry = temp;

            int count = 1;
            int x = 1;

            while(newEntry != null && count <= (numKeys + 1)){
                if(x == 0){
                    index = getHash(newEntry.key, 0) % tableSize;

                    if(index < 0)
                        index += tableSize;

                    if(check[0][index]) {
                        overflow.add(newEntry);
                        break;
                    }
                    else {
                        temp = table1[index];
                        table1[index] = newEntry;
                        newEntry = temp;
                        check[0][index] = true;
                    }

                }
                else{
                    index = getHash(newEntry.key, 1) % tableSize;

                    if(index < 0)
                        index += tableSize;


                    if(check[1][index]) {
                        overflow.add(newEntry);
                        break;
                    }
                    else{
                        temp = table2[index];
                        table2[index] = newEntry;
                        newEntry = temp;
                        check[1][index] = true;
                    }
                }
                x = 1 - x;
                count++;
            }
            if(count > (numKeys + 1))
                overflow.add(newEntry);
        }
       	numKeys++;
       	return null;
    }

    /**
     * First, it calculates the index at which the key should be in the table.
     * Then it checks if that index has the key. If so, delete it, and then find an
     * entry in the overflow list that can be put in that spot. If not, return null.
     *
     * @param key the key to be deleted
     * @return the value of the key that was deleted
     */
    @Override
    public V remove(Object key) {
	    int index1 = getHash(key, 0) % tableSize;
	    int index2 = getHash(key, 1) % tableSize;

        if(index1 < 0)
            index1 += tableSize;

        if(index2 < 0)
            index2 += tableSize;

        if(table1[index1] != null && table1[index1].key.toString().equals(key.toString())){
            V val = table1[index1].value;
            table1[index1] = null;
            numKeys--;

            List<Entry<K, V>> removal = new ArrayList<>();

            for(Entry<K, V> nextItem : overflow){
                int replaceIndex = getHash(nextItem.key, 0) % tableSize;
                if(replaceIndex < 0)
                    replaceIndex += tableSize;

                if(replaceIndex == index1) {
                    table1[replaceIndex] = nextItem;
                    removal.add(nextItem);
                    break;
                }
            }
            overflow.removeAll(removal);
            return val;
        }
        else if(table2[index2] != null && table2[index2].key.toString().equals(key.toString())){
            V val = table2[index2].value;
            table2[index2] = null;
            numKeys--;

            List<Entry<K, V>> removal = new ArrayList<>();

            for(Entry<K, V> nextItem : overflow){
                int replaceIndex = getHash(nextItem.key, 1) % tableSize;
                if(replaceIndex < 0)
                    replaceIndex += tableSize;

                if(replaceIndex == index2) {
                    table2[replaceIndex] = nextItem;
                    removal.add(nextItem);
                    break;
                }
            }
            overflow.removeAll(removal);
            return val;
        }
        else {
            for(Entry<K, V> nextItem : overflow){
                if(nextItem.key.toString().equals(key.toString())){
                    V val = nextItem.value;
                    overflow.remove(nextItem);
                    numKeys--;
                    return val;
                }
            }
        }
        return null;
    }

    /**
     * Puts the current Cuckoo structure into a string representation.
     * It includes any entry that is occupied in the two tables and the overflow list.
     *
     * @return the string representation of the Cuckoo structure
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for(Entry<K, V> nextItem : table1){
            if(nextItem != null){
                sb.append("[");
                sb.append(count);
                sb.append(",");
                sb.append(nextItem.toString());
                sb.append(",table1");
                sb.append("]");
                sb.append("\n");
            }
            count++;
        }

        count = 0;
        for(Entry<K, V> nextItem : table2){
            if(nextItem != null){
                sb.append("[");
                sb.append(count);
                sb.append(",");
                sb.append(nextItem.toString());
                sb.append(",table2");
                sb.append("]");
                sb.append("\n");
            }
            count++;
        }

        count = 0;
        if(!overflow.isEmpty()){
            for(Entry<K, V> nextItem : overflow){
                sb.append("[");
                sb.append(count);
                sb.append(",");
                sb.append(nextItem.toString());
                sb.append(",overflow");
                sb.append("]");
                sb.append("\n");
                count++;
            }
        }
	    return sb.toString();
    }
}
