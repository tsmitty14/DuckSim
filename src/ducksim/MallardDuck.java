
package ducksim;

import java.awt.Color;

public class MallardDuck extends Duck {

    public MallardDuck() {
    		super(Color.GREEN);
    }
    
    @Override
    public String display() {
        return "Mallard";
    }
    
}
