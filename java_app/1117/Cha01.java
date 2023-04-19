import java.awt.*;
import java.awt.event.*;

/** 矢印キーで操作可能なキャラクタのクラス
    ・キャラクターの画像読み込み
    ・キャラクターの描画
    ・キャラクターの位置とパラメータの更新
    を行う

    キャラクターのパラメータ： 体の向きと足（左右のどちらを前に出しているか）
 */
class Cha01 extends Frame {
  // ■ フィールド変数
  // s: キャラクタのサイズ （画像の大きさ（ピクセル））
  // cond1: キャラの右足が前 (-1) か左足が前 (1) か
  // cond2: キャラの向き（４種類）
  // cw, cwMax:  足の入れ替え速度
  int s=32, x, y, vx, vy, cond1=1, cond2=2, cw=0, cwMax=5;
  boolean lf, rf, uf, df; // left, right, up, down
  Image cimg; // キャラクタ画像

  // ■ メソッド
  // 画像をファイルから cimg に読み込む
  void cGetImage (Anim03_Chara ac){
    cimg = getToolkit().getImage("img/chara01.gif");
  }

  // ■ メソッド
  // 画像を切り取り、描画する
  void cDraw (Graphics g){
    int imgx, imgy;
    if (cond1 == 1) imgx=0; // 画像の切り取り場所 (x座標)
    else            imgx=s; // 画像の切り取り場所 (x座標)
    imgy = cond2*s; // 画像の切り取り場所 (y座標)
    g.drawImage(cimg, x, y, x+s, y+s, imgx, imgy, imgx+s, imgy+s, null);
    //	System.out.println("(x, y)=" + x + " " + y);
  }

  // ■ メソッド
  // キャラクタの位置と状態を更新する
  // x, y: 座標
  // vx, vy: 速度
  // cond1: 右足が前か左足が前か
  // cond2: 
  void cUpdate (){
    if (lf && !rf){ // 左キーを押し，右キーを押していないなら
      vx=-1;        // x 方向の速度は -1
      cond2=3;      // キャラは左向き
    }
    else if (rf && !lf){ // 右キーを押し，左キーを押していないなら
      vx=1;         // x 方向の速度は +1
      cond2=1;      // キャラは右向き
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
    x=x+vx*5; // キャラの x 座標更新
    y=y+vy*5; // キャラの y 座標更新
    if (cw < cwMax) // 時間が経っていないなら
      cw++;   // 足の入替に関するカウントを１増やす
    else {
      cond1 = cond1 * (-1); // 時間が経てば足を左右入替えて
      cw = 0; // カウントをリセット
    }
    //	System.out.print("flags: " + lf + " " + rf + " " + uf + " " + df + "   ");
  }
}

public class Anim03_Chara extends Frame implements Runnable, KeyListener {
  Thread   th;     // スレッドオブジェクト
  Image    buf;    // 仮の画用紙
  Graphics buf_gc; // グラフィックスコンテキスト
  Cha01    ch01;   // 矢印キーで操作可能なキャラクタのクラス
  Dimension d;

  // ■ メインメソッド（スタート地点）
  public static void main(String [] args) {
    Anim03_Chara ac = new Anim03_Chara(); 
  }

  // ■ コンストラクタ（初期設定）
  Anim03_Chara() {
    super("Animation 03");  // 親クラスのコンストラクタ呼び出し
    this.setSize(320, 240); // フレームの初期設定
    this.setVisible(true);  // フレームを可視化
    this.addKeyListener(this);   // (KeyListener)リスナ登録
    this.requestFocusInWindow(); // (KeyListener)フォーカスを得る

    ch01 = new Cha01();     // Cha01 クラスのオブジェクトを作成
    ch01.cGetImage(this);   // ch01 についてキャラクタの画像を取得
    d = this.getSize();     // 画面の大きさ取得

    // スレッドの初期設定
    th = new Thread(this); // スレッドオブジェクトの新規作成
    th.start();            // スレッドの開始

  }

  public void run() {  // (thread) 40 msec 毎に実行する
    try {
      while(true) {
	repaint();        // 再描画を要求する repaint() は update() を呼び出す
	Thread.sleep(50); // ウィンドウを更新する前に休止 25fps=1000msec/40msec
      }
    }
    catch(Exception e) {
    }
  }

  // ■ メソッド
  // paint() は Frame クラスのメソッド
  // 中身を上書きする（オーバーライド）
  // paint() は thread により 40msec 毎に repaint() を介して呼ばれる
  public void paint(Graphics gc) { //
    // 以下で，毎回
    // 1. 画面のサイズを取得
    // 2. buffer イメージの存在チェック
    // 3. buffer 用 gc の存在チェック
    // を行う．これは buffer が作られるタイミングの問題で
    // NullPointerException が生じるのを防ぐ為。
    // 毎回チェックするのが気持ち悪い人は
    // http://okwave.jp/qa/q1413312.html などを参照のこと
    if (buf == null)
      buf = createImage(d.width, d.height); // buffer を画面と同サイズで作成
    if (buf_gc == null)
      buf_gc = buf.getGraphics();

    // buf （仮の画用紙）を描画する
    buf_gc.setColor(Color.black);          // gc の色を黒に
    buf_gc.fillRect(0,0,d.width,d.height); // gc を使って四角を描く
    ch01.cDraw(buf_gc); // 仮の画用紙の GC を Cha01 のオブジェクトに渡す
    ch01.cUpdate(); // キャラクタのパラメータ更新
    gc.drawImage(buf, 0, 0, this); // 表の画用紙に裏の画用紙 (buf) の内容を貼り付ける
  }

  // ■ メソッド
  // これを入れないとダブルバッファリングしてもちらつく
  // （repaintメソッドが呼ばれると，update が呼ばれ、いったん背景色で
  //  塗りつぶす作業が行われるため，updata メソッドをオーバーライドし，
  // そのままpaint を呼ぶようにする必要がある）
  public void update(Graphics gc){
    paint(gc);
  }

  public void keyTyped(KeyEvent ke) {}

  public void keyPressed(KeyEvent ke) {
    int cd = ke.getKeyCode();
    switch (cd) {
    case KeyEvent.VK_LEFT:  // [←]キーが押されたら
      ch01.lf = true;   // フラグを立てる
      //	    System.out.println(" [←] pressed");
      break;
    case KeyEvent.VK_RIGHT: // [→]キーが押されたら
      ch01.rf = true;   // フラグを立てる
      //	    System.out.println(" [→] pressed");
      break;
    case KeyEvent.VK_UP: // [↑]キーが押されたら
      ch01.uf = true;   // フラグを立てる
      //	    System.out.println(" [↑] pressed");
      break;
    case KeyEvent.VK_DOWN: // [↓]キーが押されたら
      ch01.df = true;   // フラグを立てる
      //	    System.out.println(" [↓] pressed");
      break;
    }
  }
  public void keyReleased(KeyEvent ke) {
    int cd = ke.getKeyCode();
    switch (cd) {
    case KeyEvent.VK_LEFT:  // [←]キーが離されたら
      ch01.lf = false;  // フラグを降ろす
      //	    System.out.println(" [←] released");
      break;
    case KeyEvent.VK_RIGHT: // [→]キーが離されたら
      ch01.rf = false;  // フラグを降ろす
      //	    System.out.println(" [→] released");
      break;
    case KeyEvent.VK_UP: // [↑]キーが離されたら
      ch01.uf = false;  // フラグを降ろす
      //	    System.out.println(" [↑] released");
      break;
    case KeyEvent.VK_DOWN: // [↓]キーが離されたら
      ch01.df = false;  // フラグを降ろす
      //	    System.out.println(" [↓] released");
      break;
    }
  }
}