public class Menu extends JFrame implements ActionListener,
		MouseListener, MouseMotionListener {
//　（略）
	private void addMenuItem
	(JMenu targetMenu, String itemName, String actionName, ActionListener listener) {
		JMenuItem menuItem = new JMenuItem(itemName);
		menuItem.setActionCommand(actionName);
		menuItem.addActionListener(listener);
		targetMenu.add(menuItem);
	}
	
	private void initMenu() {
		JMenuBar menubar=new JMenuBar();
		JMenu menuFile = new JMenu("File");
		this.addMenuItem(menuFile,"New","New",this);
		this.addMenuItem(menuFile,"Open...","Open",this);
		this.addMenuItem(menuFile,"Save...","Save",this);
		menubar.add(menuFile);
		this.setJMenuBar(menubar);
	}
//　（略）
}