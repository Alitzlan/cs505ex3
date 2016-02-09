package ex3;

public interface IntSet {
	
	public boolean insert(int item) throws InterruptedException;

	public boolean remove(int item) throws InterruptedException;

	public boolean contain(int item) throws InterruptedException;
}
