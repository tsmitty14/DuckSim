package ducksim;

import java.awt.Color;

public class GooseDuck extends Duck{

	private Goose goose;
	
	public GooseDuck(Goose newGoose) {
		super(newGoose.getColor());
		goose = newGoose;
	}

	@Override
	public String getQuack() {
		return goose.getHonk();
	}
	
	@Override
	public String display(){
		return goose.getName();
	}
	
}
