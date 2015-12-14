
package ducksim;

import java.awt.Color;

public class RedheadDuck extends Duck {
    
    public RedheadDuck() {
        super(Color.RED);
    }
    
    @Override
    public String display() {
        return "Redhead";
    }
}
