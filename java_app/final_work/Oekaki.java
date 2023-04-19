//基本的にこちらでは描写はしない。モード値を与えたり、ファイル保存、メニュー設定のみ


import java.awt.event.*;
import java.awt.*;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JFrame;

public class Oekaki extends JFrame implements ActionListener{
	int lastx, lasty, newx, newy;
	MyCanvas mc;
	JColor_Select jcs;
	int mode;
	public int x=500,y=500;
	public static void main(String[] args) {
		Oekaki frame = new Oekaki();
		frame.initMenu();
	}

	///コンストラクタ
	Oekaki(){
		///***title***///
		super(" Oekaki ");
		this.setSize(x, y);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//×を押すとアプリケーション自体が終了される
		this.setLocationRelativeTo(null); //windowを中心に表示する
		mc = new MyCanvas(this);///キャンバスの作成
		add(mc,  BorderLayout.CENTER); // 描画領域を中央に配置
		
	}

         ////****メニューバーの設定に関するメソッド ****////
private void addMenuItem(JMenu targetMenu,JMenuItem menuItem, String itemName, String actionName, ActionListener listener) {
	menuItem.setActionCommand(actionName);
	menuItem.addActionListener(listener);
	targetMenu.add(menuItem);
}
private void addMenuItem(JMenu targetMenu, String itemName, String actionName, ActionListener listener) {
	JMenuItem menuItem = new JMenuItem(itemName);
	menuItem.setActionCommand(actionName);
	menuItem.addActionListener(listener);
	targetMenu.add(menuItem);
}
private void initMenu() {
	JMenuBar menubar = new JMenuBar();  //メニューの作成
		JMenu menuFile = new JMenu("File");//メニューからさらに派生する項目を設定
		JMenuItem mi1 = new JMenuItem("Open");
		JMenuItem mi2 = new JMenuItem("Save");
		JMenuItem mi3 = new JMenuItem("Save as");
		JMenuItem mi4 = new JMenuItem("Save as transparent gif");
		this.addMenuItem(menuFile,mi1,"Open","Open",this);//メニューバーに項目を追加
		this.addMenuItem(menuFile,mi2,"Save","Save",this);
		this.addMenuItem(menuFile,mi3,"Save as","Save as",this);
		this.addMenuItem(menuFile,mi4,"Save as transparent gif","Save as transparent gif",this);
		menuFile.setMnemonic(KeyEvent.VK_F);//ニーモニックの追加、これでショートカットキーの設定ができる
		mi1.setMnemonic(KeyEvent.VK_O);
		mi2.setMnemonic(KeyEvent.VK_S);
		mi3.setMnemonic(KeyEvent.VK_F12);
		mi4.setMnemonic(KeyEvent.VK_G);

	JMenu menuColor = new JMenu("Color");
		this.addMenuItem(menuColor, "black", "black", this);
		this.addMenuItem(menuColor, "red", "red", this);
		this.addMenuItem(menuColor, "blue", "blue", this);
		this.addMenuItem(menuColor, "green", "green", this);
		this.addMenuItem(menuColor, "yellow", "yellow", this);
		this.addMenuItem(menuColor, "more...", "more...", this);
	
		JMenu menuPen = new JMenu("Pen");
		this.addMenuItem(menuPen, "eraser","eraser",this);
		this.addMenuItem(menuPen, "clear","clear",this);
		this.addMenuItem(menuPen, "protect","protect",this);

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

		JMenu menulayer = new JMenu("Layer");
		this.addMenuItem(menulayer,"layer1","layer1",this);
		this.addMenuItem(menulayer,"layer2","layer2",this);
		this.addMenuItem(menulayer,"layer3","layer3",this);
		this.addMenuItem(menulayer,"layer4","layer4",this);

		JMenu menuaddlayer = new JMenu("Add Layer");
		this.addMenuItem(menuaddlayer,"layer1+layer2","layer1+layer2",this);
		this.addMenuItem(menuaddlayer,"layer1+layer3","layer1+layer3",this);
		this.addMenuItem(menuaddlayer,"layer1+layer4","layer1+layer4",this);

		JMenu menuicon = new JMenu("icon");
		this.addMenuItem(menuicon,"icon1","icon1",this);
		this.addMenuItem(menuicon,"icon2","icon2",this);
		this.addMenuItem(menuicon,"icon3","icon3",this);

		JMenu menualpha = new JMenu("Alpha");
		this.addMenuItem(menualpha,"alpha=10","alpha=10",this);
		this.addMenuItem(menualpha,"alpha=50","alpha=50",this);
		this.addMenuItem(menualpha,"alpha=150","alpha=150",this);
	
		menuPen.add(menuWidth);//メニューに項目を追加
		menuPen.add(menuColor);
		menubar.add(menuFile);
		menubar.add(menuShape);
		menubar.add(menuPen);
		menubar.add(menulayer);
		menubar.add(menuaddlayer);
		menubar.add(menuicon);
		menubar.add(menualpha);
		this.setJMenuBar(menubar);
		this.setVisible(true);
}

  ///****メニューバーが押された時の設定。色、線の太さ等のモード値はここで設定する****////
  
public void actionPerformed(ActionEvent e) { //プリセット

	///***描画関係の設定 ***///
	if(e.getActionCommand().equals("free hand"))mc.mode=1;
	else if(e.getActionCommand().equals("line")) mc.mode=2;
	else if(e.getActionCommand().equals("Rect")) mc.mode=3;
	else if(e.getActionCommand().equals("Oval")) mc.mode=4;
	else if(e.getActionCommand().equals("fill Oval")) mc.mode=5;
	else if(e.getActionCommand().equals("fill Rect")) mc.mode=6;
	else if(e.getActionCommand().equals("width1")){
		Graphics2D g2 = (Graphics2D)mc.g_os;
		BasicStroke bs1 = new BasicStroke(1);
		g2.setStroke(bs1);
	}
	else if(e.getActionCommand().equals("width5")){
		Graphics2D g3 = (Graphics2D)mc.g_os;
		BasicStroke bs2 = new BasicStroke(10);

		g3.setStroke(bs2);
	}
    else if(e.getActionCommand().equals("width10")){
		Graphics2D g4 = (Graphics2D)mc.g_os;
		BasicStroke bs2 = new BasicStroke(10);
		g4.setStroke(bs2);
	}
	else if(e.getActionCommand().equals("width20")){
		Graphics2D g5 = (Graphics2D)mc.g_os;
		BasicStroke bs3 = new BasicStroke(20);
		g5.setStroke(bs3);
	}
	else if(e.getActionCommand().equals("select")) mc.mode=7;
	else if(e.getActionCommand().equals("red"))mc.c = Color.red;
	else if(e.getActionCommand().equals("blue")) mc.c = Color.blue;
	else if(e.getActionCommand().equals("green")) mc.c = Color.green;
	else if(e.getActionCommand().equals("yellow")) mc.c = Color.yellow;
	else if(e.getActionCommand().equals("black")) mc.c = Color.black;
	else if(e.getActionCommand().equals("more...")){
		jcs = new JColor_Select();
		mc.c = jcs.color;
	}
	else if(e.getActionCommand().equals("eraser")) mc.mode=8;
	else if(e.getActionCommand().equals("clear")){
		mc.clear();
	}
	else if(e.getActionCommand().equals("alpha=10"))mc.alpha=10;
	else if(e.getActionCommand().equals("alpha=50"))mc.alpha=50;
	else if(e.getActionCommand().equals("alpha=150"))mc.alpha=150;
	else if(e.getActionCommand().equals("layer1"))mc.mode = 9;
	else if(e.getActionCommand().equals("layer2"))mc.mode = 10;
	else if(e.getActionCommand().equals("layer3"))mc.mode = 11;
	else if(e.getActionCommand().equals("layer4"))mc.mode = 12;
	else if(e.getActionCommand().equals("layer1+layer2"))mc.mode = 13;
	else if(e.getActionCommand().equals("layer1+layer3"))mc.mode = 14;
	else if(e.getActionCommand().equals("layer1+layer4"))mc.mode = 15;
	else if(e.getActionCommand().equals("icon1"))mc.mode = 16;
	else if(e.getActionCommand().equals("icon2"))mc.mode = 17;
	else if(e.getActionCommand().equals("icon3"))mc.mode = 18;
	else if(e.getActionCommand().equals("protect"))mc.mode = 23;

	///*** ファイルの保存関係の設定 ***///
	else if(e.getActionCommand().equals("Open"))mc.mode = 19;
	else if(e.getActionCommand().equals("Save"))mc.mode = 20;
	else if(e.getActionCommand().equals("Save as"))mc.mode = 21;
	else if(e.getActionCommand().equals("Save as transparent gif"))mc.mode = 22;
	}
}










  