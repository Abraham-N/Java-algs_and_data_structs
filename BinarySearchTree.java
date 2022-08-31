/** @author 
 * 	Abraham Nofal
 * 	CS 5343
 * 	Dr. Sridhar Alagar
 * 	March 27, 2022
 *  Binary search tree (starter code)
 **/
 
// replace package name with your netid
//package AMN170002;


import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
    static class Entry<T> {
        T element;
        Entry<T> left, right;
		public int height;
        
        public Entry(T x, Entry<T> left, Entry<T> right) {
        	this.element = x;
        	this.left = left;
        	this.right = right;
        	this.height = 0;
        }
    }
    
    Entry<T> root;
    int size;
    ArrayDeque <Entry<T>> treeStack = new ArrayDeque<>();
    
    public BinarySearchTree() 
    {
	root = null;
	size = 0;
    }

    //Added helper method find, standard find procedure for binary tree used, used stack for remove method.
    public Entry <T> find(Entry <T> root, T x)
    {
    	Entry <T> temp = root;
    	if(temp == null || temp.element.compareTo(x) == 0)
    		return temp;
    	else
    	{
    		while(true)
    		{
    			if(temp.element.compareTo(x) < 0)
    			{
    				if(temp.right == null)
    					return temp;
    				else
    					treeStack.push(temp);
    					temp = temp.right;
    			}
    			else if(temp.element.compareTo(x) > 0)
    			{
    				if(temp.left == null)
    					return temp;
    				else
    					treeStack.push(temp);
    					temp = temp.left;	
    			}
    			else
    				return temp;
    		}
    	}
    }

    /** TO DO: Is x contained in tree?
     */
    //If find method returns an entry with element equal to x, returns true.
    public boolean contains(T x) {
    	Entry <T> temp = find(root, x);
    	if(temp == null || temp.element.compareTo(x) != 0)
    		return false;
    	else
    		return true;
    }

  
    /** TO DO: Add x to tree. 
     *  If tree contains a node with same key, replace element by x.
     *  Returns true if x is a new element added to tree.
     */
    //If root is null, create the root with element x, otherwise, find x in BST and place in appropriate location depending on value. Also increment size variable.
    public boolean add(T x) {
    	if(root == null)
    	{
    		root = new Entry<T>(x, null, null);
    		this.size++;
    		return true;
    	}
    	else 
    	{
    		Entry <T> temp = find(root, x);
    		if(temp.element.compareTo(x) == 0)
    			temp.element = x;
    		else if(temp.element.compareTo(x) < 0)
    		{
    			temp.right = new Entry<T>(x, null, null);
    			this.size++;
    		}	
    		else if(temp.element.compareTo(x) > 0)
    		{
    			temp.left = new Entry<T>(x, null, null);
    			this.size++;
    		}
    	}
    	return true;
    }

    /** TO DO: Remove x from tree. 
     *  Return x if found, otherwise return null
     */
    //if find function does not give us x, return null, otherwise, use helper method splice to remove and decrement size.
    public T remove(T x) 
    {
    	Entry <T> temp = find(root, x);
    	
    	if(temp == null || temp.element.compareTo(x) != 0)
    		return null;
    	

    	
    	if(temp.left == null || temp.right == null)
    		splice(temp);
    	else
    	{
    		treeStack.push(temp);
    		Entry<T> minRight = find(temp.right, x);
    		temp.element = minRight.element;
    		splice(minRight);
    	}
    	
    	this.size--;
    	return x;
    }
    
    //implemented splice as given in slides:
    //use stack created in find method to create parent entry and child will be whichever child of temp is not null.
    //replace temp node with child node for the parent.
    private void splice(Entry<T> temp) 
    {
		Entry <T> parent = treeStack.peek();
		Entry<T> child = temp.left == null ? temp.right: temp.left;
		if(parent == null)
			root = child;
		else if(parent.left == temp)
			parent.left = child;
		else
			parent.right = child;
	}
    
    
    //search left subtree until null is reached, then return the element.
	public T min() {
		Entry <T> temp = root;
		if(temp == null)
			return null;
		while(temp.left != null)
			temp = temp.left;
		return temp.element;
    }

	//search right subtrees until null is reached, then return the element.
    public T max() {
    	Entry <T> temp = root;
		if(temp == null)
			return null;
		while(temp.right != null)
			temp = temp.right;
		return temp.element;
    }

    // TODO: Create an array with the elements using in-order traversal of tree
    public Comparable[] toArray() {
    	
    	Comparable[] arr = new Comparable[size];
	
    	if(root == null)
    		return arr;
    	
    	ArrayDeque <Entry<T>> tempstack = new ArrayDeque<>();
    	Entry <T> temp = root;
    	int idx = 0;
    	
    	while(temp != null || tempstack.size() > 0)
    	{
    		while(temp != null)
    		{
    			tempstack.push(temp);
    			temp = temp.left;
    		}
    		
    		temp = tempstack.pop();
    		arr[idx] = temp.element;
    		idx ++;
    		temp = temp.right;
    	}
	
	
	return arr;
    }


// Start of Optional problem 2

    /** Optional problem 2: Iterate elements in sorted order of keys
	Solve this problem without creating an array using in-order traversal (toArray()).
     */
    public Iterator<T> iterator() {
	return null;
    }

    // Optional problem 2.  Find largest key that is no bigger than x.  Return null if there is no such key.
    public T floor(T x) {
        return null;
    }

    // Optional problem 2.  Find smallest key that is no smaller than x.  Return null if there is no such key.
    public T ceiling(T x) {
        return null;
    }

    // Optional problem 2.  Find predecessor of x.  If x is not in the tree, return floor(x).  Return null if there is no such key.
    public T predecessor(T x) {
        return null;
    }

    // Optional problem 2.  Find successor of x.  If x is not in the tree, return ceiling(x).  Return null if there is no such key.
    public T successor(T x) {
        return null;
    }

// End of Optional problem 2
    public static void main(String[] args) {
	BinarySearchTree<Integer> t = new BinarySearchTree<>();
        Scanner in = new Scanner(System.in);
        while(in.hasNext()) {
            int x = in.nextInt();
            if(x > 0) {
                System.out.print("Add " + x + " : ");
                t.add(x);
                t.printTree();
            } else if(x < 0) {
                System.out.print("Remove " + x + " : ");
                t.remove(-x);
                t.printTree();
            } else {
                Comparable[] arr = t.toArray();
                System.out.print("Final: ");
                for(int i=0; i<t.size; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();
                return;
            }           
        }
    }

    public void printTree() {
	System.out.print("[" + size + "]");
	printTree(root);
	System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
	if(node != null) {
	    printTree(node.left);
	    System.out.print(" " + node.element);
	    printTree(node.right);
	}
    }

}
/*
Sample input:
1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0

Output:
Add 1 : [1] 1
Add 3 : [2] 1 3
Add 5 : [3] 1 3 5
Add 7 : [4] 1 3 5 7
Add 9 : [5] 1 3 5 7 9
Add 2 : [6] 1 2 3 5 7 9
Add 4 : [7] 1 2 3 4 5 7 9
Add 6 : [8] 1 2 3 4 5 6 7 9
Add 8 : [9] 1 2 3 4 5 6 7 8 9
Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
Remove -3 : [9] 1 2 4 5 6 7 8 9 10
Remove -6 : [8] 1 2 4 5 7 8 9 10
Remove -3 : [8] 1 2 4 5 7 8 9 10
Final: 1 2 4 5 7 8 9 10 

*/
