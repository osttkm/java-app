// 背景を動かして、キャラの動きを表現する
import java.awt.*;
import java.awt.event.*;

class Cha02 extends Frame {
  // ■ フィールド変数
  int s=32, x=100, y=100, vx, vy; // 飛行機のサイズ，位置，速度
  int cond1=1; // 飛行機のアフターバーナーの表示の有無
  int cond2=2; // 飛行機の向きを管理（0:上，1:右，2:下，3:左)
  boolean lf, rf, uf, df; // 飛行機の向きに関するフラグ (left flag, right flag, up flag, down flag)
  Image cimg; // 飛行機の画像はこちらで管理する

  // ■ メソッド（飛行機の画像を取得）
  void cGetImage (Anim04_Airplane ap){
    cimg = getToolkit().getImage("img/chara02.gif");
  }

  // ■ メソッド（向きに応じた飛行機の画像を切り取って描画）
  void cDraw (Graphics g){
    int imgx, imgy;
    if (cond1 == 1) imgx=0;
    else            imgx=s;
    imgy = cond2*s;
    g.drawImage(cimg, 150-s/2, 100-s/2, 150+s/2, 100+s/2, imgx, imgy, imgx+s, imgy+s, null);
    //	System.out.println("(x, y)=" + x + " " + y);
  }

  // ■ メソッド（飛行機の位置を更新）
  void cUpdate (){
    if (lf && !rf){
      vx=-1;
      cond2=3;
    }
    else if (rf && !lf){
      vx=1;
      cond2=1;
    }
    else vx=0;

    if (uf && !df){
      vy=-1;
      cond2=0;
    }
    else if (df && !uf){
      vy=1;
      cond2=2;
    }
    else vy=0;
    x=x+vx*5; // 飛行機の位置を更新
    y=y+vy*5; // 飛行機の位置を更新
    cond1 = cond1 * (-1);// 飛行機のアフターバーナーの表示の切り替え
    //	System.out.print("flags: " + lf + " " + rf + " " + uf + " " + df + "   ");
  }
}

public class Anim04_Airplane extends Frame implements Runnable, KeyListener {
  // ■ フィールド変数
  Thread   th;       // スレッドオブジェクトの宣言
  Image    img, mimg;// 仮の画用紙, マップイメージ
  Graphics img_gc;   // グラフィックスコンテキスト (gc) 
  Cha02    ch02 = new Cha02(); // 飛行機
  Dimension d; // 画面のサイズ取得用

  // ■ メインメソッド（スタート地点）
  public static void main(String [] args) {
    Anim04_Airplane anm04 = new Anim04_Airplane(); 
  }

  // ■ コンストラクタ（初期設定）
  Anim04_Airplane() {
    super("Animation 04");  // 親クラスのコンストラクタ呼び出し
    this.setSize(320, 240); // フレームの初期設定
    this.setVisible(true);  // フレームを可視化
    this.addKeyListener(this);   // (KeyListener)リスナ登録
    this.requestFocusInWindow(); // (KeyListener)フォーカスを得る
    mimg = this.getToolkit().getImage("img/map01.jpg");

    ch02 = new Cha02();     // Cha01 クラスのオブジェクトを作成
    ch02.cGetImage(this);   // ch01 についてキャラクタの画像を取得
    d = this.getSize();     // 画面の大きさ取得

    // スレッドの初期設定
    th = new Thread(this); // スレッドオブジェクトの新規作成
    th.start();            // スレッドの開始

  }

  public void run() {  // (thread) 20msec 毎に実行する
    try {
      while(true) {
	repaint();        // 再描画を要求する repaint() は update() を呼び出す
	Thread.sleep(30); // ウィンドウを更新する前に休止
      }
    }
    catch(Exception e) {
    }
  }

  public void update(Graphics g) { // repaint() に呼ばれる
    paint(g);
  }

  public void paint(Graphics g) { // 
    // 以下で，毎回
    // 1. 画面のサイズを取得
    // 2. buffer イメージの存在チェック
    // 3. buffer 用 gc の存在チェック
    // を行う．これは buffer が作られるタイミングの問題で
    // NullPointerException が生じるのを防ぐ為。
    // 毎回チェックするのが気持ち悪い人は
    // http://okwave.jp/qa/q1413312.html などを参照のこと
    if (img == null)
      img = createImage(d.width, d.height); // buffer を画面と同サイズで作成
    if (img_gc == null)
      img_gc = img.getGraphics();

    // img を描画する
    Dimension d = getSize();
    img_gc.setColor(Color.black);          // gc の色を黒に
    img_gc.fillRect(0,0,d.width,d.height); // gc を使って四角を描く
    img_gc.drawImage(mimg, 0, 0, d.width, d.height, ch02.x, ch02.y, ch02.x+d.width, ch02.y+d.width, null);
    ch02.cDraw(img_gc);
    // 表の画用紙に裏の画用紙 (img) の内容を貼り付ける
    g.drawImage(img, 0, 0, this);
    ch02.cUpdate();
  }

  public void keyTyped(KeyEvent ke) {}

  public void keyPressed(KeyEvent ke) {
    int cd = ke.getKeyCode();
    switch (cd) {
    case KeyEvent.VK_LEFT:  // [←]キーが押されたら
      ch02.lf = true;   // フラグを立てる
      //	    System.out.println(" [←] pressed");
      break;
    case KeyEvent.VK_RIGHT: // [→]キーが押されたら
      ch02.rf = true;   // フラグを立てる
      //	    System.out.println(" [→] pressed");
      break;
    case KeyEvent.VK_UP: // [↑]キーが押されたら
      ch02.uf = true;   // フラグを立てる
      //	    System.out.println(" [↑] pressed");
      break;
    case KeyEvent.VK_DOWN: // [↓]キーが押されたら
      ch02.df = true;   // フラグを立てる
      //	    System.out.println(" [↓] pressed");
      break;
    }
  }
  public void keyReleased(KeyEvent ke) {
    int cd = ke.getKeyCode();
    switch (cd) {
    case KeyEvent.VK_LEFT:  // [←]キーが離されたら
      ch02.lf = false;  // フラグを降ろす
      //	    System.out.println(" [←] released");
      break;
    case KeyEvent.VK_RIGHT: // [→]キーが離されたら
      ch02.rf = false;  // フラグを降ろす
      //	    System.out.println(" [→] released");
      break;
    case KeyEvent.VK_UP: // [↑]キーが離されたら
      ch02.uf = false;  // フラグを降ろす
      //	    System.out.println(" [↑] released");
      break;
    case KeyEvent.VK_DOWN: // [↓]キーが離されたら
      ch02.df = false;  // フラグを降ろす
      //	    System.out.println(" [↓] released");
      break;
    }

  }
}