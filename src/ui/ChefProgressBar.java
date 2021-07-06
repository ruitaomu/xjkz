package ui;

import javax.swing.JProgressBar;

import xjkz.Chef;

public class ChefProgressBar extends JProgressBar implements Runnable {
	private Chef chef;

	public ChefProgressBar(Chef chef) {
		super(0, 100);
		
		setSize(100, 50);
		
		this.chef = chef;
		
		new Thread(this).start();
		setVisible(true);
	}

	@Override
	public void run() {
		while (true) {
			try {
				setValue(chef.progress());
				Thread.sleep(300);
			} catch (InterruptedException e) {
			}
		}
	}
}
