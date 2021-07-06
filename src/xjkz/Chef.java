package xjkz;

import java.awt.image.BufferedImage;


public class Chef implements Runnable {
	private String name;
	private float factorFried;
	private float factorSoup;
	private float factorPasta;
	private int progress;
	private Food food;
	private int totalTime;
	private int totalPrice;
	private int totalFood;
	private BufferedImage image;

	public Chef(String name, float factorFried, float factorSoup, float factorPasta) {
		this.name = name;
		this.factorFried = factorFried;
		this.factorSoup = factorSoup;
		this.factorPasta = factorPasta;
		this.progress = 0;
		this.food = null;
		totalTime = totalPrice = totalFood = 0;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public String getName() {
		return name;
	}
	
	public Food getCookingFood() {
		return food;
	}
	
	public synchronized boolean make(Food food) {
		if (progress != 0) {
			return false;
		}
		
		this.food = food;
		
		System.out.println(name + "begins making " + food.name());
		
		progressIncrement();
		
		new Thread(this).start();
		return true;
	}
	
	public synchronized int progress() {
		return progress;
	}
	
	public synchronized Food take() {
		progress = 0;
		totalTime += food.getEstimatedTime();
		totalPrice += food.price();
		totalFood ++;
		return food;
	}

	@Override
	public void run() {
		while (progressIncrement() < 100) {
			try {
				Thread.sleep((long) (10L * getEstimatedTime() * getCurrentFactor()));
			} catch (InterruptedException e) {
			}
		}
		
		synchronized (this) {
			this.notifyAll();
		}
	}

	private synchronized int progressIncrement() {
		return ++progress;
	}

	private int getEstimatedTime() {
		return food.getEstimatedTime();
	}

	private float getCurrentFactor() {
		switch(food.type()) {
		case Food.FRIED: return factorFried;
		case Food.SOUP: return factorSoup;
		case Food.PASTA: return factorPasta;
		default:
			throw new IllegalArgumentException();
		}
	}
	
	public String toString() {
		return name + "Time: " + totalTime + "  Price: " + totalPrice + " Food: " + totalFood;
	}
}
