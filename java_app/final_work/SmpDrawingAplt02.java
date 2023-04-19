import java.awt.*;
import java.awt.event.*;

public class DrawingApli01 extends Frame implements ActionListener, AdjustmentListener {
  // ■ フィールド変数
  Button bt1, bt2, bt3, bt4, bt5, bt6, bt101, bt102;
  Scrollbar sbar1, sbar2, sbar3;     // scroll bars for each color
  Label lb1, lb2, lb3;	             // labels for each scroll bar
  int red=0, green=0, blue=0;        // colors take 0-255 values as int type
  Panel   pnl1 = new Panel();        // panel for general buttons
  Panel   pnlR = new Panel();        // panel for Label and Scrollbar for R
  Panel   pnlG = new Panel();        // panel for Label and Scrollbar for G
  Panel   pnlB = new Panel();        // panel for Label and Scrollbar for G
  MyCanvas mc; // Region for Drawing

  // ■ main メソッド（スタート地点）
  public static void main(String [] args) {
    DrawingApli01 da = new DrawingApli01(); 
  }

  // ■ コンストラクタ
  DrawingApli01() {
    super("Drawing Appli 01"); // フレームのタイトル
    this.setSize(640, 480);    // VGAサイズで作成

    setLayout(new BorderLayout()); // まずボーダーレイアウトで描画領域とパネル領域を分ける
    mc  = new MyCanvas(this);
    add(mc,  BorderLayout.CENTER); // 描画領域を中央に配置
    add(pnl1, BorderLayout.WEST);  // パネル領域を左に配置
    pnl1.setLayout(new GridLayout(12,1)); // パネル領域をさらにグリッドレイアウトで分割
    bt1 = new Button("Free Hand");  bt1.addActionListener(this); pnl1.add(bt1);
    bt2 = new Button("Line");       bt2.addActionListener(this); pnl1.add(bt2);
    bt3 = new Button("Rectangle");  bt3.addActionListener(this); pnl1.add(bt3);
    bt4 = new Button("Fill Rect");  bt4.addActionListener(this); pnl1.add(bt4);
    bt5 = new Button("Oval");       bt5.addActionListener(this); pnl1.add(bt5);
    bt6 = new Button("Fill Oval");  bt6.addActionListener(this); pnl1.add(bt6);
    bt101 = new Button("Eraser");     bt101.addActionListener(this); pnl1.add(bt101);
    bt102 = new Button("Initialize"); bt102.addActionListener(this); pnl1.add(bt102);

    pnl1.add(pnlR); // パネル領域に，さらに「赤」用パネルを作成
    pnlR.setLayout(new GridLayout(2,1));
    lb1   = new Label("RED", Label.CENTER);
    lb1.setBackground(Color.red); pnlR.add(lb1);
    sbar1 = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 0, 256);
    sbar1.addAdjustmentListener(this); pnlR.add(sbar1);

    pnl1.add(pnlG); // パネル領域に，さらに「緑」用パネルを作成
    pnlG.setLayout(new GridLayout(2,1));
    lb2   = new Label("GREEN", Label.CENTER);
    lb2.setBackground(Color.green); pnlG.add(lb2);
    sbar2 = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 0, 256);
    sbar2.addAdjustmentListener(this); pnlG.add(sbar2);

    pnl1.add(pnlB); // パネル領域に，さらに「青」用パネルを作成
    pnlB.setLayout(new GridLayout(2,1));
    lb3   = new Label("BLUE", Label.CENTER);
    lb3.setBackground(Color.blue); pnlB.add(lb3);
    sbar3 = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 0, 256);
    sbar3.addAdjustmentListener(this); pnlB.add(sbar3);

    this.setVisible(true); //可視化
  }

  /* パネルのボタンがどれか押された時の処理   */
  public void actionPerformed(ActionEvent e){
    if (e.getSource() == bt1){
      mc.mode=1;// set mode to Free Hand
    } else if (e.getSource() == bt2){
      mc.mode=2;// set mode to Line
    } else if (e.getSource() == bt3){
      mc.mode=3;// Rectangle
    } else if (e.getSource() == bt4){
      mc.mode=4;// Fill Rect
    } else if (e.getSource() == bt5){
      mc.mode=5;// Oval
    } else if (e.getSource() == bt6){
      mc.mode=6;// Fill Oval
    } else if (e.getSource() == bt101){
      mc.mode=101;// Eraser
    } else if (e.getSource() == bt102){
      mc.mode=102;// Initialize
      mc.canvasClear();
    }
  }

  /* パネルのスクロールバーがどれか変更された時の処理   */
  public void adjustmentValueChanged(AdjustmentEvent e) {
    red   = sbar1.getValue();
    green = sbar2.getValue();
    blue  = sbar3.getValue();
    mc.cSetColor(red, green, blue);
  }
}

/**
 * Extended Canvas class for DrawingApli01
 * 
 * 
 * @author fukai
 */
class MyCanvas extends Canvas implements MouseListener, MouseMotionListener {
  private int x, y;
  private int px, py;		// preliminary point
  private int ow, oh;		// width and height of the object
  private int red, green, blue;	// color 
  private int tmp_x, tmp_y;
  int   mode;		        // draw mode associated as below
  Image offScreen = null;
  Graphics g_os   = null;
  Dimension d;
  // 1: free hand 
  // 2: draw line 
  // 3: rect
  // 4: fill rect 
  // 5: oval
  // 6: fill oval
  // 7: circle
  // 8: fill circle
  // 101: Eraser
  // 102: Initialize
  public MyCanvas(DrawingApli01 obj){ // constructor
    mode=0;
    d=obj.getSize();
    System.out.println(d.width + " " + d.height);
    this.setSize(d.width,d.height);				
    addMouseListener(this); 			
    addMouseMotionListener(this); 
  }

  public void canvasClear(){
    g_os.setColor(Color.white);	 // set gc color of off-screen white
    g_os.fillRect(0,0,d.width,d.height); // paint whole off-screen canvas white
    repaint();                   // apply the "clear" immediately
    g_os.setColor(new Color(red, green, blue)); // re-set the gc color of off-screen
  }

  public void cSetColor(int red, int green, int blue){ // Set Color of off-screen
    g_os.setColor(new Color(red, green, blue));
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  public void cSetLineWidth(int lw){
  }

  public void update(Graphics g) {
    paint(g);
  }
  // ■ メソッド paint()
  // mouseDragged, mouseReleased 時に呼ばれる
  // ・offScreen には常に決定した（それ以上消さない）内容が保存されている
  // ・途中経過は，offScreen ではなく，offScreen をコピーした表キャンバスに一時的に直接描く
  // 各モードによる描画：
  // 1. freehand mode
  //    途中経過は表示しないので offScreen に決定事項を描きこんだのちに offScreen をキャンバスに貼り付ける
  // 2. line mode
  //    まず offScreen の内容をキャンバスに張り付けた後に
  //    キャンバスに線を描く（この線は後に offScreen の貼り付けによって消される）
  // 3. rect mode
  //    まず offScreen の内容をキャンバスに張り付けた後に
  //    キャンバスに四角を描く（この四角は後に offScreen の貼り付けによって消される）
  public void paint(Graphics g) {// paint では随時、暫定的なもの（確定offScreen + 一時的な図）を描く
    g.setColor(new Color(red, green, blue)); // 毎回色を設定
    if (offScreen == null)                   
      offScreen = createImage(d.width, d.height);
    if (g_os == null)
      g_os = offScreen.getGraphics();

    switch (mode){
    case 1:// freehand
      g_os.drawLine(px, py, x, y);        // 裏に描いて確定して
      g.drawImage(offScreen, 0, 0, this); // 表にコピー
      break;
    case 2:// draw line
      g.drawImage(offScreen, 0, 0, this); // 表に裏をコピー
      g.drawLine(px, py, x, y);           // 表に線を描く
      break;
    case 3:// rect
      g.drawImage(offScreen, 0, 0, this);
      ow = x-px;
      oh = y-py;
      // System.out.println("x="+x+" px="+px+" y="+y+" py="+py+" ow="+ow+" oh"+oh);
      if (x<px){ // もし x が負の方向に動いたら
	tmp_x=x; // 始点を x（小さい方）に設定
      } else
	tmp_x=px;// 始点を px（小さい方）に設定
      if (y<py){ // もし y が負の方向に動いたら
	tmp_y=y; // 始点を y（小さい方）に設定
      } else
	tmp_y=py;// 始点を py（小さい方）に設定
      g.drawRect(tmp_x, tmp_y, Math.abs(ow), Math.abs(oh)); // 表に暫定的に描く
      break;
    case 4:// fill rect
      g.drawImage(offScreen, 0, 0, this);
      ow = x-px;
      oh = y-py;
      g.fillRect(px, py, ow, oh);
      break;
    case 5:// oval      
      g.drawImage(offScreen, 0, 0, this); // 表に裏の決定版をコピー
      ow = x-px;                      // 幅を計算
      oh = y-py;                      // 高さを計算
      g.drawOval(px, py, ow, oh);     // 表に暫定的に円を描く
      break;
    case 6:// fill oval
      g.drawImage(offScreen, 0, 0, this);
      ow = x-px;
      oh = y-py;
      g.fillOval(px, py, ow, oh);
      break;
    case 102:// clear all 
      g.drawImage(offScreen, 0, 0, this);
    }
  }
  /////////////////////////////////////////////////
  // 以下、マウス操作に対する動作
  public void mouseClicked(MouseEvent e){}
  public void mouseEntered(MouseEvent e){}
  public void mouseExited(MouseEvent e){}
  public void mousePressed(MouseEvent e){
    switch (mode){
    case 1:// freehand
    case 101:
      x = e.getX();
      y = e.getY();
      break;
    case 2:// draw line
    case 3:// rect
    case 4:// fill rect
    case 5:// oval
    case 6:// fill oval
      px = e.getX();
      py = e.getY();
    }
  }
  public void mouseReleased(MouseEvent e){ // fix the image
    switch (mode){
    case 2:// draw line
      g_os.drawLine(px, py, x, y);
      repaint();
      break;
    case 3:// rect
      x = e.getX();
      y = e.getY();
      ow = x-px;
      oh = y-py;
      if (x<px){ // もし x が負の方向に動いたら
	tmp_x=x; // 始点を x（小さい方）に設定
      } else
	tmp_x=px;// 始点を px（小さい方）に設定
      if (y<py){ // もし y が負の方向に動いたら
	tmp_y=y; // 始点を y（小さい方）に設定
      } else
	tmp_y=py;// 始点を py（小さい方）に設定
      g_os.drawRect(tmp_x, tmp_y, Math.abs(ow), Math.abs(oh)); // 表に暫定的に描く
      repaint();
      break;
    case 4:// fill rect
      x = e.getX();
      y = e.getY();
      ow = x-px;
      oh = y-py;
      g_os.fillRect(px, py, ow, oh); // offScrean に確定画像を描く
      repaint();
      break;
    case 5:// oval
      x = e.getX();
      y = e.getY();
      ow = x-px;
      oh = y-py;
      g_os.drawOval(px, py, ow, oh); // 裏に確定画像を描く
      repaint();
      break;
    case 6:// fill oval
      x = e.getX();
      y = e.getY();
      ow = x-px;
      oh = y-py;
      g_os.fillOval(px, py, ow, oh); // 裏に確定画像を描く
      repaint();
      break;
    case 101:// circle
      repaint();
    }
  }
  public void mouseDragged(MouseEvent e){
    switch (mode){
    case 1:  // free hand
    case 101:// eraser
      px = x;
      py = y;
      x = e.getX();
      y = e.getY();
      repaint();	// call paint(); every moving
      break;
    case 2:// draw line
    case 3:// rect
    case 4:// fill rect
    case 5:// oval
    case 6:// fill oval
      x = e.getX();
      y = e.getY();
      repaint();// call paint(); every moving
      break;
    }
  }
  public void mouseMoved(MouseEvent e){}// do nothing
}
