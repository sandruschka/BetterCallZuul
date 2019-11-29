/*
 * An Item that can be placed in rooms, carried by players etc
 */
package betterCallZuul;

/**
 * A simple class for items
 * @author rej
 */
public class Item {
    private String description;
    private int weight;
    
    /**
     * Constructor for an Item
     * @param desc The item's description
     * @param w the item's weight
     */
    public Item (String desc, int w) {
        description = desc;
        weight = w;
    }

    public void setWeight(Integer weight) {
    	this.weight = weight;
    }
    
    public void setDesc(String desc) {
    	this.description = desc;
    }
    /**
     * Get a description of an item
     * @return the description
     */
    public String getDescription() { return description; }

    /**
     * Get the weight of an item
     * @return the weight
     */
    public int getWeight() { return weight; }
    
    /**
     *  Make the item change state in some way.
     *  Default: do nothing
     */
    public void execute() {}
}
