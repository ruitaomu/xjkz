package xjkz;

public class ChefMonitor implements Runnable {

	private Chef chef;
	private Kitchen kitchen;

	public ChefMonitor(Chef chef, Kitchen kitchen) {
		this.chef = chef;
		this.kitchen = kitchen;
	}

	@Override
	public void run() {
		while (true) {
			boolean ready = false;
			Food foodReady = null;
			
			synchronized (chef) {
				if (chef.progress() < 100) {
					try {
						chef.wait();
					} catch (InterruptedException e) {
					}
				}
				
				if (chef.progress() == 100) {					
					foodReady = chef.take();
					System.out.println(chef.getName() + " made " + foodReady.name());
					ready = true;
				}
			}
			
			synchronized (kitchen) {
				kitchen.moveFromTodoToMade(foodReady);
				kitchen.notifyAll();
			}
		}
	}
}
