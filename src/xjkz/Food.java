package xjkz;

public class Food {
	public static final int FRIED = 0;
	public static final int SOUP  = 1;
	public static final int PASTA = 2;
	private String name;
	private int type;
	private int price;
	private int time;
	
	public Food(String name, int type, int price, int time) {
		this.name = name;
		this.type = type;
		this.price = price;
		this.time = time;
	}

	public int type() {
		return type;
	}

	public int getEstimatedTime() {
		return time;
	}
	
	public String name() {
		return name;
	}
	
	public int price() {
		return price;
	}
	
	public String toString() {
		return name;
	}
}
