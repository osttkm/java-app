import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;
public class DrawingApli01 extends JFrame implements ActionListener,MouseListener, MouseMotionListener {
 

	int lastx, lasty, newx, newy;
	DrawPanel panel;
	public void actionPerformed(ActionEvent arg0) {
	}

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
  }
	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	
	 	lastx=arg0.getX();
	 	lasty=arg0.getY();
	}
	public void mouseReleased(MouseEvent arg0) {
	}
	public void mouseDragged(MouseEvent arg0) {
		newx=arg0.getX();
		newy=arg0.getY();
		panel.drawLine(lastx,lasty,newx,newy);
		lastx=newx;
		lasty=newy;
	}

 

	public void mouseMoved(MouseEvent arg0) {
	}

	private void init() {
		this.setTitle("Simple Draw");
		this.setSize(300, 200);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		panel=new DrawPanel();
		this.getContentPane().add(panel);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	DrawingApli01(){
    JMenuBar menubar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		this.addMenuItem(menuFile,"New","New",this);
		this.addMenuItem(menuFile,"Open...","Open",this);
		this.addMenuItem(menuFile,"Save...","Save",this);
		menubar.add(menuFile);
		
		JMenu menuColor = new JMenu("Color");
		this.addMenuItem(menuColor, "red", "red", this);
		this.addMenuItem(menuColor, "blue", "blue", this);
		this.addMenuItem(menuColor, "green", "green", this);
		this.addMenuItem(menuColor, "yellow", "yellow", this);
		this.addMenuItem(menuColor, "more...", "more", this);

		JMenu menuPen = new JMenu("Pen");
		this.addMenuItem(menuPen, "eraser","eraser",this);
		this.addMenuItem(menuPen, "clear","clear",this);


		JMenu menuWidth = new JMenu("Width");
		this.addMenuItem(menuWidth, "width1", "width1", this);
		this.addMenuItem(menuWidth, "width5", "width5", this);
		this.addMenuItem(menuWidth, "width10", "width10", this);
		this.addMenuItem(menuWidth, "width20", "width20", this);
		this.addMenuItem(menuWidth, "select", "select", this);

		JMenu menuShape = new JMenu("Shape");
		this.addMenuItem(menuShape,"free hand","free hand",this);
		this.addMenuItem(menuShape,"line","line",this);
		this.addMenuItem(menuShape,"Rect","Rect",this);
		this.addMenuItem(menuShape,"Oval","Oval",this);
		this.addMenuItem(menuShape,"fill Oval","fill Oval",this);
		this.addMenuItem(menuShape,"fill Rect","fill Rect",this);

		menuPen.add(menuWidth);//メニューに項目を追加
		menuPen.add(menuColor);
		menubar.add(menuShape);
		menubar.add(menuPen);
		this.setJMenuBar(menubar);
		this.setVisible(true);
  }
  private void addMenuItem(JMenu targetMenu, String itemName, String actionName, ActionListener listener) {
    JMenuItem menuItem = new JMenuItem(itemName);
    menuItem.setActionCommand(actionName);
    menuItem.addActionListener(listener);
    targetMenu.add(menuItem);
  }
	public static void main(String[] args) {
		DrawingApli01 frame=new DrawingApli01();
		frame.init();
  }
}
