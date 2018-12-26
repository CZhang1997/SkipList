
package cxz173430;
import java.util.Iterator;
import java.util.Random;
import java.util.NoSuchElementException;

/**
 * @author 		Churong Zhang 
 * 				cxz173430
 * 				October 20 2018
 * 				Dr. Raghavachari
 * 				This class is for Project 2
 * 				Skip List
 */
public class SkipList<T extends Comparable<? super T>> {
    static final int PossibleLevels = 33;
    Entry<T> head, tail;	// dummy head and tail
    int size, maxLevel;		// size and the maximum of levels
    Entry<T>[] last;		// last is use by find(x)
    Random random;			// use by choose level
    
    static class Entry<E> {
	E element;				// whatever is store in the skip list
	Entry<E>[] next;		// an array of entry that the element connect to
	Entry<E> prev;			// the real previous element of this 
	/**
	 * Constructor of Entry
	 * @param x the element of the entry
	 * @param lev the number of level this entry has 
	 */
	public Entry(E x, int lev) {
		    element = x;
		    next = new Entry[lev];
		    // add more code as needed
		}
	/**
	 * get the element from the entry
	 * @return the element in this entry
	 */
	public E getElement() {
		    return element;
		}
    }

    /**
     *  Default Constructor
     */
    public SkipList() {
    	head = new Entry<>(null, PossibleLevels);
    	tail = new Entry<>(null, PossibleLevels);
    	last = new Entry[PossibleLevels];
    	for(int index = 0; index < PossibleLevels; index++)
    	{
    		head.next[index] = tail;
    		last[index] = head;
    	}
    	tail.prev = head;
    	size = 0;
    	maxLevel = 1;
    	random = new Random();
    }
    /**
     * find the find the previous element of x
     * @param x the element that is looking for
     */
    public void find(T x)
    {
   		Entry<T> p = head;
       	for(int i = maxLevel-1; i >=0; i--)
       	{        		
       		while(p.next[i].element !=null && p.next[i].element.compareTo(x) < 0)
       		{
       			p = p.next[i];
        	}
       		last[i] = p;
       	}
    }
   
    /**
     * randomly get a level
     * @return a random number
     */
    public int chooseLevel()
    {
    	int lev = 1 + Integer.numberOfTrailingZeros(random.nextInt());
    	if(lev > maxLevel)
    		maxLevel = lev;
    	return lev;
    }
   
    /**
     * add an element to the list, 
     * if the element is already exist, 
     * ignore it and return false
     * @param x the element that we want to add
     * @return true if add is successful 
     */
    public boolean add(T x) {
    if(contains(x))
    	{
    	// if we want to replace the element
    	//last[0].next[0].element = x;
    	return false;
    	}
    else
    	{
    	int lev = chooseLevel();
    	Entry<T> ent = new Entry(x, lev);
    	ent.prev = last[0];
    	for(int i = 0; i < lev; i++)
    	{
    		ent.next[i] = last[i].next[i];
    		last[i].next[i] = ent;
    	}
    	ent.next[0].prev = ent;
    	size ++;
    	return true;
    	}
    }

    /**
     * Find smallest element that is greater or equal to x
     * @param x the reference element
     * @return the smallest element that is greater or equal to x
     */
    public T ceiling(T x) {
    	if(size == 0)
    		throw new NoSuchElementException("The list is empty.");
    	find(x);
    	if(last[0].next[0].element == null)
    		throw new NoSuchElementException("There is no ceiling element for " + x);
	return last[0].next[0].element;
    }

    /**
     * check if the element is inside the list
     * @param x the reference element
     * @return true if the element is inside the list
     */
    public boolean contains(T x) {
    	find(x);
    	if(last[0].next[0].element == null)
    		return false;
    	return last[0].next[0].element.compareTo(x) == 0;
    	// == cannot be use when the value(long) is greater than 127
    }

    /**
     * Return first element of list
     * @return the first element of the list
     */
    public T first() {
    	if(size == 0)
    		throw new NoSuchElementException("The list is empty. ");
	return head.next[0].element;
    }

    /**
     * Find largest element that is less than or equal to x
     * @param x the reference element
     * @return the largest element that is less than or equal to x
     */
    public T floor(T x) {
    	if(size == 0)
    		throw new NoSuchElementException("The list is empty. ");
    	find(x);
    	if(last[0].next[0].element.compareTo(x) == 0)
    		return last[0].next[0].element;
    	if(last[0].element == null)
    		throw new NoSuchElementException("There is no floor element for " + x);
	return last[0].element;
    }

  
    /**
     * Return element at index n of list.  First element is at index 0
     * @param n the index of the element 
     * @return the element at index n
     */
    public T get(int n) {
    	if(n < 0 || n > size - 1)
    	{
    		throw new IndexOutOfBoundsException("There is no element in index:" + n);
    	}
                        	  
	    int half = size /2;
	
	   half = size % 2 == 0 ? half : half + 1;
	    if(half > n)
	    { // if n is in the first half of the list
	    	Entry<T> p = head;
	    	for(int i = 0; i <= n; i++)
	    	{
	    		p = p.next[0];
	    	}
	    	return p.element;
	    }
	    else
	    {// if n is in the second half of the list
	    	Entry<T> p = tail;
	    	for(int i = size;i > n; i--)
	    	{
	    		p = p.prev;
	    	}
	    	return p.element;
	    }
   }

 
    /**
     * check if the list is empty
     * @return true if the list is empty
     */
    public boolean isEmpty() {
	return size == 0;
    }

  
    /**
     * create a iterator to loop through the list
     * @return the SkipListIterator
     */
    public Iterator<T> iterator() {
	return new SkipListIterator();
    }
    /**
     * SkipListIterator is for user to use iterator
     * to loop through the list
     * @author Rain
     *
     */
    protected class SkipListIterator implements Iterator<T>
    {
    	Entry<T> current; // the current entry;
    	public SkipListIterator()
    	{
    		current = head;
    	}
		/**
		 * check if there is a next element
		 * @return true if there is a next element
		 */
		public boolean hasNext() {
			return current.next[0].element != null;
		}

		/**
		 * get the next element after current
		 * @return the next element 
		 */
		public T next() {
			current = current.next[0];
			return current.element;
		}
    	
    }
    /**
     * Return last element of list
     * @return the Return last element of list
     */
    public T last() {
    	if(size == 0)
    		throw new NoSuchElementException("The list is empty. ");
	return tail.prev.element;
    }

 
    /**
     * Remove x from list.  Removed element is returned. Return null if x not in list
     * @param x the element to remove
     * @return the element that got remove
     */
    public T remove(T x) {
    	if(!contains(x))
    	{
    		return null;
    	}
    	Entry<T> ent = last[0].next[0];
    	
    	ent.next[0].prev = last[0];
    	for(int i = 0; i < ent.next.length; i++)
    	{
    		last[i].next[i] = ent.next[i];
    	}
    	size--;
    	
	return ent.element;
    }
    /**
     * get the string version of the list
     * @return the list
     */
    public String toString()
    {
    	Entry<T> p = head.next[0];
    	String s = "Size:[" + size + "]: ";
    	
		for(int a = 0; a < size; a++)
    	{
    		s = s+ p.element + " ";
    		p = p.next[0];
    	}
	//	s = s + '\n';
		return s;
    }
    
    /**
     * Return the number of elements in the list
     * @return the size of the list
     */
    public int size() {
	return size;
    }
}