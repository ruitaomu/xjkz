package xjkz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

	public static void main(String[] args) throws IOException {
		Kitchen kitchen = new Kitchen();
		Chef chef1 = new Chef("’‘¡È∂˘", 0.8f, 1.2f, 1.0f );
		chef1.setImage(ImageIO.read(new File("res/1.jpg")));
		Chef chef2 = new Chef("¡÷‘¬»Á", 1.0f, 0.8f, 1.2f );
		chef2.setImage(ImageIO.read(new File("res/2.jpg")));
		Chef chef3 = new Chef("∞¢≈´",   1.2f, 1.0f, 0.8f );
		chef3.setImage(ImageIO.read(new File("res/3.jpg")));
		kitchen.addChef(chef1, 0);
		kitchen.addChef(chef2, 1);
		kitchen.addChef(chef3, 2);
		
		kitchen.addFood(new Food("≈£»‚√Ê", Food.PASTA, 5, 5));
		kitchen.addFood(new Food("ªÿπ¯»‚", Food.FRIED, 10, 9));
		kitchen.addFood(new Food("ºÂµ∞Ã¿", Food.SOUP, 7, 10));
		kitchen.addFood(new Food("µ£µ£√Ê", Food.PASTA, 5, 5));
		kitchen.addFood(new Food("º¶Àø¡π√Ê", Food.PASTA, 3, 2));
		kitchen.addFood(new Food("≥¥√Ê∆¨", Food.PASTA, 6, 8));
		kitchen.addFood(new Food("ªπªÍÃ¿", Food.SOUP, 15, 18));
		kitchen.addFood(new Food("–°≥¥»‚", Food.FRIED, 12, 10));
		kitchen.addFood(new Food("√‘ªÍÃ¿", Food.SOUP, 25, 30));
		kitchen.addFood(new Food("∫ÏÏÀ—Ú»‚", Food.FRIED, 18, 22));
		kitchen.addFood(new Food("π¨±£º¶∂°", Food.FRIED, 4, 4));
		
		
		new KitchenMonitor(kitchen);
	}
	
	public static void loadMenu(Kitchen kitchen) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("res/menu.txt"));
		String line;
		while ((line = reader.readLine()) != null) {
			kitchen.addFoodToMenu(line);
		}
	}
	
	public static void start(Kitchen kitchen) {
		new Thread(kitchen).start();
	}
}
