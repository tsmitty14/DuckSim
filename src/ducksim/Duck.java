
package ducksim;

import java.awt.Color;

public abstract class Duck {
    
    private Color color;
    private boolean quacking = false;
    
    // typical duck commands
    
    public Duck(Color colorArg) {
    		color = colorArg;
    }
    
    public void stopQuack() {
    		quacking = false;
    }
    
    public void quack() {
    		quacking = true;
    }
    
    public String getQuack() {
        return "Quack!";
    }

    	public boolean isQuacking() {
    		return quacking;
    	}
    
    public Color getColor() {
        return color;
    }
    
    public abstract String display();
    
}
