package Modelisation;

import java.util.concurrent.atomic.AtomicInteger;

public class Client extends Area {
	private int need;
	
	//Constructors
	public Client(int id, int x, int y, int need)
	{
		super(id, x, y, true); 
		this.need = need;
	}
	
	public int getNeed() {
		return need;
	}

	public void setNeed(int need) {
		this.need = need;
	}
	
	@Override
	public String toString()
	{
		return("C" + super.getId() + "-" + this.getNeed());
	}
}
