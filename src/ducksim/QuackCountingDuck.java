package ducksim;

import java.awt.Color;

public class QuackCountingDuck extends Duck {

	private Duck duck;
	private int numOfQuacks = 0;
	
	public QuackCountingDuck(Duck newDuck) {
		super(newDuck.getColor());
		duck = newDuck;
	}
	
	@Override 
	public void quack(){
		numOfQuacks++;
		duck.quack();
		
	}

	@Override
	public String display() {
		return duck.getQuack();
	}

	public int getNumberOfQuacks(){
		return numOfQuacks;
	}
}
