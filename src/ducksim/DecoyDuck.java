package ducksim;

import java.awt.Color;

public class DecoyDuck extends Duck {
	public DecoyDuck() {
		super(Color.ORANGE);
	}
	
	@Override
	public String display(){
		return "Decoy";
	}
	
	@Override
	public void quack(){}
	
	public String getQuack(){
		return "";
	}
}
