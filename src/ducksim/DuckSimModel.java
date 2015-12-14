package ducksim;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * DuckSimModel is the model portion of the DuckSim MVC pattern.
 *
 * @author Dr. K
 */
public class DuckSimModel implements Iterable<Duck> {

    // ==========================================================
    // Private fields
    // ==========================================================
	
    private final int MAX = 8;
    private int currentDuck = -1;
    private final List<Duck> ducks = new ArrayList<>();
    private final List<ModelObserver> observers = new ArrayList<>();

    // ==========================================================
    // Public methods
    // ==========================================================
    
    /* Returns the maximum number of ducks permitted */
    public int maxDuckCount() {
        return MAX;
    }
    
    /* Returns the current number of ducks */
    public int currDuckCount() {
        return ducks.size();
    }
    
    /* Returns the index of the current duck. */
    public int getCurrentDuck() {
        return currentDuck;
    }

    /* Sets the current duck index to the specified integer. */
    public void setCurrentDuck(int i) {
        assert containsDuck(i);
        this.currentDuck = i;
    }

    /* Returns true if a duck is at the specified index. */
    public boolean containsDuck(int i) {
        return 0 <= i && i < ducks.size();
    }

    /* Returns true if there is room to add a new duck. */
    public boolean canAddNewDuck() {
        return ducks.size() < MAX;
    }

    /* Adds a new duck to the model. */
    public void addNewDuck(Duck d) {
        assert canAddNewDuck();
        ducks.add(d);
    }

    /* Deletes the duck at the specified index. */
    public void deleteDuck(int i) {
        assert containsDuck(i);
        ducks.remove(i);
    }

    /* Returns a handle to the duck at the specified index. */
    public Duck getDuck(int i) {
        assert containsDuck(i);
        return ducks.get(i);
    }

    @Override
    public Iterator<Duck> iterator() {
        return ducks.iterator();
    }
    
    public void registerObserver(ModelObserver observer){
		observers.add(observer);
	}
	
	public void notifyObservers(){
		for(ModelObserver observer: observers){
			observer.update();
	}
}
}
