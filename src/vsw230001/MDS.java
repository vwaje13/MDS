/** Starter code for P3
 *  @author
 */

// Change to your net id
package vsw230001;

// If you want to create additional classes, place them in this file as subclasses of MDS

import java.util.*;

public class MDS {
    // Add fields of MDS here
    HashMap<Integer, Item> idMap; //key, value. will have all key, value pairs
    //ID is key, Item is value
    HashMap<Integer, TreeSet<Item>> desMap; //map will have keys (descriptions) and a tree set of all values with that key
    //treeset will contain items
    // ID is description characteristic, treeset of items is value
    // Constructors
    class Item implements Comparable<Item>{
        int id;
        int price;
        List<Integer> description;

        public Item(int i, int p, List<Integer> d) {
            id = i;
            price = p;
            description = d;
        }

        @Override
        public int compareTo(Item o) {
            return this.price - o.price;
        }
    }
    public MDS() {
        idMap = new HashMap<Integer, Item>();
        desMap = new HashMap<Integer, TreeSet<Item>>();
    }

    // if you wanted to find an item based on its id, use the map!!!!!

    /* Public methods of MDS. Do not change their signatures.
       __________________________________________________________________
       a. Insert(id,price,list): insert a new item whose description is given
       in the list.  If an entry with the same id already exists, then its
       description and price are replaced by the new values, unless list
       is null or empty, in which case, just the price is updated. 
       Returns 1 if the item is new, and 0 otherwise.
    */
    public int insert(int id, int price, List<Integer> list) { //O(1) if list
        int n = 0;
        Item pointer;
        List<Integer> arr = new ArrayList<Integer>(list);
        if(idMap.get(id) == null) {
            pointer = new Item(id, price, arr);
            idMap.put(id, pointer);
            n = 1;
        }
        else {
            pointer = idMap.get(id);
            pointer.price = price;
            if(arr.size() > 0) {
                pointer.description = arr;
            }
        }

        for(int i : arr) {
            if(!desMap.containsKey(i)) {
                TreeSet<Item> set = new TreeSet<Item>();
                set.add(pointer);
                desMap.put(i, set);
            }
            else {
                desMap.get(i).add(pointer);
            }
        }
        return n;
    }

    // b. Find(id): return price of item with given id (or 0, if not found).
    public int find(int id) {
	    if(idMap.get(id) != null) {
	        return idMap.get(id).price;
        }
	    return 0; //not found
    }

    /* 
       c. Delete(id): delete item from storage.  Returns the sum of the
       ints that are in the description of the item deleted,
       or 0, if such an id did not exist.
    */
    public int delete(int id) {
        Item it = idMap.get(id);
        if(it == null) {
            return 0; //doesnt exist
        }
        int sum = 0;
        for(int i : it.description) { // for each description value, add to sum and delete item from that description
            TreeSet<Item> z = desMap.get(i);
            if(z.size() > 1) {
                z.remove(it);
            }
            else {
                desMap.remove(i);
            }
            sum += i;
        }
        idMap.remove(id);
        return sum;
    }

    /* 
       d. FindMinPrice(n): given an integer, find items whose description
       contains that number (exact match with one of the ints in the
       item's description), and return lowest price of those items.
       Return 0 if there is no such item.
    */
    public int findMinPrice(int n) {
	    return desMap.get(n).first().price;
    }

    /* 
       e. FindMaxPrice(n): given an integer, find items whose description
       contains that number, and return highest price of those items.
       Return 0 if there is no such item.
    */
    public int findMaxPrice(int n) {
	    return desMap.get(n).last().price;
    }

    /* 
       f. FindPriceRange(n,low,high): given int n, find the number
       of items whose description contains n, and in addition,
       their prices fall within the given range, [low, high].
    */
    public int findPriceRange(int n, int low, int high) {
        TreeSet<Item> s = desMap.get(n);
        int count = 0;
        for(Item i : s ) {
            if((i.price >= low) && (i.price <= high)) {
                count ++;
            }
        }
        return count;
    }

    /*
      g. RemoveNames(id, list): Remove elements of list from the description of id.
      It is possible that some of the items in the list are not in the
      id's description.  Return the sum of the numbers that are actually
      deleted from the description of id.  Return 0 if there is no such id.
    */
    public int removeNames(int id, java.util.List<Integer> list) {
        Item it = idMap.get(id);
        int sum = 0;
        if(it == null) {
            return sum; //no such id
        }

        //List<Integer> arr = new ArrayList<Integer>(it.description);

        List<Integer> toDel = new ArrayList<Integer>(list);
        for(int i : toDel) {
            if(it.description.contains(i)) {
                sum += i;
                it.description.remove(Integer.valueOf(i));
            }
        }
        //it.description = arr;
        return sum;
    }
}