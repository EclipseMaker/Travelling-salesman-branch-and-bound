package Modelisation;

import java.util.concurrent.atomic.AtomicInteger;

public class Client extends Road {
	private static AtomicInteger count = new AtomicInteger(-1);
	private int id; 
	private int need;
	
	public Client(int x, int y, int need)
	{
		super(x, y, true); 
		this.need = need;
		this.id = count.incrementAndGet();
	}
	
	public int getNeed() {
		return need;
	}

	public void setNeed(int need) {
		this.need = need;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString()
	{
		return("C" + this.getId() + "-" + this.getNeed());
	}
}
