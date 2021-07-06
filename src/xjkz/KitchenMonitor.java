package xjkz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.function.Consumer;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ui.ChefProgressBar;

public class KitchenMonitor extends JFrame {
	private Vector<ChefMonitor> chefMonitors;
	private Kitchen kitchen;
	private Hashtable<Chef, JLabel> cookingFoods;
	private JList madeList, remainList;

	public KitchenMonitor(Kitchen kitchen) {
		super("仙剑厨房");
		chefMonitors = new Vector<ChefMonitor>();
		cookingFoods = new Hashtable<Chef, JLabel>();
		
		this.kitchen = kitchen;
		
		setSize(800, 400);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS));
		
		for (Chef chef : kitchen.getChefs()) {
			JPanel chefPanel = new JPanel();
			chefPanel.setLayout(new BoxLayout(chefPanel, BoxLayout.Y_AXIS));
			
			chefMonitors.add(new ChefMonitor(chef, kitchen));
			ImageIcon img = new ImageIcon(chef.getImage());
			JLabel label = new JLabel();
			label.setIcon(img);
			JLabel foodLabel = new JLabel();
			foodLabel.setText("");
			cookingFoods.put(chef, foodLabel);

			chefPanel.add(label);
			chefPanel.add(new ChefProgressBar(chef));
			chefPanel.add(foodLabel);
			
			leftPanel.add(chefPanel);
		}
		
		panel.add(leftPanel);
		
		// List Control Panel 1
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
		
		remainList = new JList();
		remainList.setSize(200, 300);
		JScrollPane scrPane = new JScrollPane(remainList);
		controlPanel.add(scrPane);
		
		
		JButton btn = new JButton("加载");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Main.loadMenu(kitchen);
					List<Food> list = kitchen.getTodoList();
					remainList.setListData(list.toArray());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		});
		btn.setSize(200, 80);
		controlPanel.add(btn);		
		panel.add(controlPanel);
		
		// List Control Panel 2
		
		controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
		
		madeList = new JList();
		scrPane = new JScrollPane(madeList);
		controlPanel.add(scrPane);
		
		
		btn = new JButton("开始");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				start();
				Main.start(kitchen);
			}
			
		});

		controlPanel.add(btn);		
		panel.add(controlPanel);
		
		getContentPane().add(panel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setVisible(true);
	}

	

	public void start() {
		for (ChefMonitor cm : chefMonitors) {
			new Thread(cm).start();
		}
		
		new Thread(new Runnable() {
				
			public void run() {
				while (kitchen.getMadeList().size() < kitchen.getTodoList().size()) {
			
					try {
						Thread.sleep(300);
						madeList.setListData(kitchen.getMadeList().toArray());
						remainList.setListData(kitchen.getRemainList().toArray());
						for (Chef chef : kitchen.getChefs()) {
							JLabel cookingFood = cookingFoods.get(chef);
							Food food = chef.getCookingFood();
							if (food != null) {
								cookingFood.setText(food.name());
							}
						}
					} catch (InterruptedException e) {
					}
				}
				System.out.println("Done: ");
				String output = new String();
				for (Chef chef : kitchen.getChefs()) {
					System.out.println(chef);
					output += chef.toString();
					output += "\n";
				}
				JOptionPane.showMessageDialog(null,
					    output,
					    "仙剑客栈",
					    JOptionPane.PLAIN_MESSAGE);
			}
		}).start();
	}
}
