public class BST1{
	
	class Tree {
		int element; 
		Tree left, right;
		Tree(int x, Tree l, Tree r) {
			element = x;
			left = l;
			right = r;
		}
		
		Tree(int x)
		{
			element = x;
		}
	}

	Tree root;  // root of binary search tree
	int size;   // number of elements in the BST

	BST1() {  // constructor
		root = null;
		size = 0;
	}

	BST1(Tree t, int s) {  // constructor
		root = t;
		size = s;
	}
	
	BST1 arrayToBST(int[] arr)
	{
		this.root = arrayToTree(arr,0,arr.length-1);
		return this;
		
	}
	
	/*arrayToTree will run O(n) due to master method:
	 * We have T(n) = 2T(n/2) + O(1)
	 * a = 2 because we have 2 subproblems in the recursion
	 * b = 2 because the size of each subproblem is n/2
	 * and RT of code outside of the recursion is O(1). 
	 * We have f(n) = O(n^log(base b)a-e), where e = 1
	 * therefore T(n) = O(n^log(baseb)a) = O(n^log2) = O(n)
	 * T(n) = O(n)
	 */
	Tree arrayToTree(int[] arr, int p, int r)
	{
		if(p>r)
			return null;
		int m = (p+r)/2;
		Tree tree = new Tree(arr[m]);
		tree.left = arrayToTree(arr,p,m-1);
		tree.right = arrayToTree(arr,m+1,r);
		return tree;
	}
	
    /*public static void main(String[] args) 
    {
        BinarySearchTree tree = new BinarySearchTree();

        int arr[] = new int[]{1, 2, 3, 4, 5, 6, 7};

        tree.root = tree.arrayToTree(arr, 0, arr.length - 1);
        
        System.out.println(tree.root.element);
        System.out.println(tree.root.left.element);
        System.out.println(tree.root.left.left.element);
        System.out.println(tree.root.left.right.element);
        System.out.println(tree.root.right.element);
        System.out.println(tree.root.right.left.element);
        System.out.println(tree.root.right.right.element);
    }
	*/
   }
