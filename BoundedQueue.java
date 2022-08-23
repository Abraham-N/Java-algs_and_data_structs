public class BoundedQueue <T> 
{
	private int front,rear;
	private T[] arr;
	
	public BoundedQueue(int size)
	{
		this.front = this.rear = -1;
		this.arr = (T[]) new Object[size];
	}
	
	public boolean offer(T x)
	{
		if(isFull())
			return false;
		else if(isEmpty())
			front++;
		rear = (rear + 1) % arr.length;
		arr[rear] = x;
		return true;
	}
	
	public T poll() 
	{
		if(isEmpty())
			return null;
		T temp = arr[front];
		if(front == rear)
			front = rear = -1;
		else
			front = (front+1) % arr.length;
		return temp;
	}
	
	public T peek() 
	{
		if(isEmpty())
			return null;
		return arr[front];
			
	}
	
	public int size()
	{
		return rear + 1 - front;
	}
	
	private boolean isEmpty()
	{
		return front == -1;
	}
	
	private boolean isFull()
	{
		return (rear+1) % arr.length == front;
	}
	/*
	public static void main(String [] args)
	{
		BoundedQueue <Integer> Q = new BoundedQueue<>(5);
		
	}*/    
}

