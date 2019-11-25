package betterCallZuul;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Inventory {
		private Map<String, Item> items;
		private int totalWeight;
		
		public Inventory() {
			items = new HashMap<String, Item>();
			totalWeight = 0;
		}
		
		public void addItem(String desc, Item item) {
			items.put(desc, item);
		}
		
		public Item removeItem(String name) {
			return items.remove(name);
			
		}
		
		public void removeWeight(int weight) {
			totalWeight -= weight;
		}
		
		public void addWeight(int weight) {
			totalWeight += weight;
		}
		
		public boolean hasItem(String desc) { return items.containsKey(desc); }
		
		
		
		public boolean tooHeavyToPickUp(int itemWeight, int maxWeight) {
			return itemWeight + totalWeight > maxWeight;
		}
		
		public boolean isItemInInventory(String desc) {
			return items.containsKey(desc);
		}
		
		public List<String> getItemsString() {
			return items.keySet().stream()
	            	.map((desc) -> desc + '(' + items.get(desc).getWeight() + ") ")
	            	.collect(Collectors.toList());
		}
}
