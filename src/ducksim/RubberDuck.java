
package ducksim;

import java.awt.Color;

public class RubberDuck extends Duck {
    
    public RubberDuck() {
        super(Color.YELLOW);
    }
    
    @Override
    public String getQuack() {
        return "Squeek!";
    }
    
    @Override
    public String display() {
        return "Rubber";
    }
}
