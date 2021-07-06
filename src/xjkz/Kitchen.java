package xjkz;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Kitchen implements Runnable {
	private Chef[] chefs;
	private Hashtable<String, Food> foods;
	private List<Food> todoList;
	private List<Food> madeList;
	private List<Food> remainList;
	
	public Kitchen() {
		chefs = new Chef[3];
		foods = new Hashtable<String, Food>();
		todoList = new ArrayList<Food>();
		madeList = new ArrayList<Food>();
		remainList = new ArrayList<Food>();
	}
	
	public void addChef(Chef chef, int num) {
		chefs[num] = chef;
	}
	
	public void addFood(Food food) {
		foods.put(food.name(), food);
	}
	
	public void addFoodToMenu(String foodName) {
		Food food = foods.get(foodName);
		if (food != null) {
			todoList.add(food);
			remainList.add(food);
		} else {
			System.out.println("Unknown food: "+foodName);
		}
	}
	
	public synchronized void moveFromTodoToMade(Food food) {
		madeList.add(food);
		remainList.remove(food);
	}
	
	public synchronized List<Food> getMadeList() {
		return madeList;
	}
	
	public synchronized List<Food> getTodoList() {
		return todoList;
	}
	
	public synchronized List<Food> getRemainList() {
		return remainList;
	}
	
	public void run() {
		int dispatched = 1;
		int readin = 1;
		boolean isFoodDispatched = true;
		Food food = null;
		Iterator<Food> e = todoList.iterator();
		while (e.hasNext() || !isFoodDispatched) {
			if (isFoodDispatched) {
				System.out.println("Read next menu: "+readin);
				food = e.next();
				readin++;
				isFoodDispatched = false;
			}
			
			{
				System.out.println("Dispatching "+ dispatched + ": " +food);
				for (Chef chef : chefs) {
					if (chef.make(food)) {
						isFoodDispatched = true;
						dispatched++;
						break;
					}
				}
			}
			
			if (!isFoodDispatched) {
				//All chefs are busy now
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException ex) {
					}
				}
			}
		}
	}
	
	public Chef[] getChefs() {
		return chefs;
	}
}
