//package AMN170002;

import java.util.Comparator;
	
	public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
	    static class Entry<T> extends BinarySearchTree.Entry<T> {
	        int height;
	        Entry(T x, Entry<T> left, Entry<T> right) {
	            super(x, left, right);
	            height = 0;
	        }
	    }
	    
	    
	    AVLTree()
	    {
		super();
	    }
	
	    
		// TO DO
	    @Override
	    public boolean add(T x) 
	    {
	    	boolean a = super.add(x);
	    	updateHeight(root);
	    	rebalance(root);
	    	return a;
	    }
	    
	    //TO DO
	  	@Override
	  	public T remove(T x)
	  	{
	  		T y = super.remove(x);
	  		updateHeight(root);
	    	rebalance(root);
	    	return y;
	  	}
	  	
		private void updateHeight(BinarySearchTree.Entry<T> temp) 
		{
			int leftChildHeight = height(temp.left);
		    int rightChildHeight = height(temp.right);
		    temp.height = Math.max(leftChildHeight, rightChildHeight) + 1;
			
		}

		private int height(BinarySearchTree.Entry<T> temp) 
		{
			return temp != null ? temp.height : -1;
		}
		
		private int balanceFactor(BinarySearchTree.Entry<T> temp) 
		{
			return height(temp.right) - height(temp.left);
		}
		
		private BinarySearchTree.Entry<T> rotateLeft(BinarySearchTree.Entry<T> temp) 
		{
			BinarySearchTree.Entry<T> rightChild = temp.right;

			temp.right = rightChild.left;
			rightChild.left = temp;

			updateHeight(temp);
			updateHeight(rightChild);

			return rightChild;
		}
		
		private BinarySearchTree.Entry<T> rotateRight(BinarySearchTree.Entry<T> temp)
		{
			BinarySearchTree.Entry<T> leftChild = temp.left;

			temp.left = leftChild.right;
			leftChild.right = temp;

			updateHeight(temp);
			updateHeight(leftChild);

			return leftChild;
		}
		
		//rebalance the AVLTree according to the balance factor, and rotating it the appropriate manner.
		private BinarySearchTree.Entry<T> rebalance(BinarySearchTree.Entry<T> temp) {
			  int balanceFactor = balanceFactor(temp);

			  if (balanceFactor < -1) 
			  {
			    if (balanceFactor(temp.left) <= 0)
			      temp = rotateRight(temp);
			    else 
			    {                                
			      temp.left = rotateLeft((AVLTree.Entry<T>)temp.left);
			      temp = rotateRight(temp);
			    }
			  }

			  if (balanceFactor > 1) 
			  {
			    if (balanceFactor(temp.right) >= 0)
			    {    
			      temp = rotateLeft(temp);
			    } 
			    else 
			    {                                 
			      temp.right = rotateRight(temp.right);
			      temp = rotateLeft(temp);
			    }
			  }
			  return temp;
			}

		/** TO DO
		 *	verify if the tree is a valid AVL tree, that satisfies 
		 *	all conditions of BST, and the balancing conditions of AVL trees. 
		 *	In addition, do not trust the height value stored at the nodes, and
		 *	heights of nodes have to be verified to be correct.  Make your code
		 *  as efficient as possible. HINT: Look at the bottom-up solution to verify BST
		*/
		boolean verify()
		{
			return verify((AVLTree.Entry<T>)root);
		}
		
		boolean verify(AVLTree.Entry<T> temp) 
		{
			if(temp != null)										
			{
			   	if(temp.left == null && temp.right == null)															
		    		return true;										
		 		else if(temp.left != null || temp.right != null)		
			    {						
		 			
		 			if(temp.left != null)									
					{
		  				if(temp.left.element.compareTo(temp.element) >= 0)
							return false;	    							
		    		}
		    		else
		    		{
		    			if(temp.right.element.compareTo(temp.element) <= 0)
		    				return false;									
		    		}
		 				
		 			boolean v = verify(temp.left != null ? (AVLTree.Entry<T>)temp.left : (AVLTree.Entry<T>)temp.right);		
			   		if(!v || temp.height > 1)							
			   			return false;									
			   			
			    }
			    else				
			    {	
			    	if(temp.left.element.compareTo(temp.element) >= 0 || temp.right.element.compareTo(temp.element) <= 0)
			   		{
			    		return false;											
			   		}
			    	
			 		boolean vL = verify((AVLTree.Entry<T>)temp.left); 					
		    		if(vL)													
			   		{
		    			boolean vR = verify((AVLTree.Entry<T>)temp.right);		
		    			if(vR)											
		    			{
		    				if(Math.abs(((AVLTree.Entry<T>)temp.left).height - ((AVLTree.Entry<T>)temp.right).height) <= 1)
		    	   			{
		    	   				int max = Math.max(((AVLTree.Entry<T>)temp.left).height,((AVLTree.Entry<T>)temp.right).height);
		    	   				if(temp.height != max +1)							
		    	   					return false;									
		       				}
		        			else
		        				return false;									
		    			}
			   		}
			   		else
			   			return false;												
			   		
			    }
			}
		return true;
		}
		
}
			
	

