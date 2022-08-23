import java.util.Iterator;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class SinglyLinkedList<T> implements Iterable<T> {

    /** Class Entry holds a single node of the list */
    static class Entry<E> {
        E element;
        Entry<E> next;

        Entry(E x, Entry<E> nxt) {
            element = x;
            next = nxt;
        }	
    }

    // Dummy header is used.  tail stores reference of tail element of list
    Entry<T> head, tail;
    int size;

    public SinglyLinkedList() {
        head = new Entry<>(null, null);
        tail = head;
        size = 0;
    }

    public Iterator<T> iterator() { return new SLLIterator(); }

    protected class SLLIterator implements Iterator<T> {
	Entry<T> cursor, prev;
	boolean ready;  // is item ready to be removed?

	SLLIterator() {
	    cursor = head;
	    prev = null;
	    ready = false;
	}

	public boolean hasNext() {
	    return cursor.next != null;
	}
	
	// implement this method
	public T next() {	
		prev = cursor;
		cursor = cursor.next; 
		ready = true;
		return cursor.element;
		
	}

	// Removes the current element (retrieved by the most recent next())
	// Remove can be called only if next has been called and the element has not been removed
	public void remove() {
		
	    if(!ready) {
		throw new NoSuchElementException();
	    }
	    prev.next = cursor.next;
	    cursor = prev;
	    ready = false;
	    size --;
	    // complete the remaining part of the method
	}
    }  // end of class SLLIterator

    // Add new elements to the end of the list
    public void add(T x) {
	add(new Entry<>(x, null));
    }

    public void add(Entry<T> ent) {
	tail.next = ent;
	tail = tail.next;
	size++;
    }

    public void printList() {
	System.out.print(this.size + ": ");
	for(T item: this) {
	    System.out.print(item + " ");
	}

	System.out.println();
    }

    // optional
	// Rearrange the elements of the list by linking the elements at even index
    // followed by the elements at odd index. Implemented by rearranging pointers
    // of existing elements without allocating any new elements.
    public void unzip() {
	if(size < 3) {  // Too few elements.  No change.
	    return;
	}


    }

   /* public static void main(String[] args) throws NoSuchElementException {
        int n = 10;
        if(args.length > 0) {
            n = Integer.parseInt(args[0]);
        }

        SinglyLinkedList<Integer> lst = new SinglyLinkedList<>();
        for(int i=1; i<=n; i++) {
            lst.add(Integer.valueOf(i));
        }
        lst.printList();

	Iterator<Integer> it = lst.iterator();
	Scanner in = new Scanner(System.in);
	whileloop:
	while(in.hasNext()) {
	    int com = in.nextInt();
	    switch(com) {
	    case 1:  // Move to next element and print it
		if (it.hasNext()) {
		    System.out.println(it.next());
		} else {
		    break whileloop;
		}
		break;
	    case 2:  // Remove element
		it.remove();
		lst.printList();
		break;
	    default:  // Exit loop
		 break whileloop;
	    }
	}
	lst.printList();
	lst.unzip();
    lst.printList();
    }*/
}