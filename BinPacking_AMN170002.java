import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class BinPacking_AMN170002 {
	
	/* This method runs in O(n) time, where each 
	 * instruction before the loop will take c time
	 * and those inside the for loop will run 
	 * n times for a time of cn. The degree of
	 * the run time equation therefore is 1, 
	 * therefore the running time is linear 
	 * time O(n).
	 */
	int worstFit(int[] size, int C)
	{
		int n = size.length;
		PriorityQueue <Integer> q = new PriorityQueue<>(n, Collections.reverseOrder());
		
		q.add(C);
		
		for(int i : size)
		{
				int emptiest = q.poll();
				
				if(i <= emptiest)
				{
					q.add(emptiest - i);
				}
				else
				{
					q.add(emptiest);
					q.add(C - i);
				}
			
		}
		
		return q.size();
	}
	
	public static void main(String [] args) 
	{
		int [] size = {3,4,2,3};
		int C = 6;
		BinPacking_AMN170002 a = new BinPacking_AMN170002();
		int numberOfBins = a.worstFit(size,C);
		//System.out.println(numberOfBins);
		PriorityQueue <Integer> q = new PriorityQueue<>(5, Collections.reverseOrder());
		q.add(5);
		q.add(4);
		q.add(3);
		TreeMap <Integer,Integer> m = new TreeMap<>();
		m.put(5, 1);
		m.put(5, 2);
		m.put(5, 1);
		for(Entry<Integer, Integer> e: m.entrySet())
			System.out.println(m.get(5));
	}
	
}
