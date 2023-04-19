/**
 * Canvas 内の動く物体を全て統括して制御するクラス
 *
 * @author fukai
 */
import java.awt.*;
class GameMaster extends Canvas{
  // ■ フィールド変数
  Image buf;       // 仮の画用紙（バッファ）
  Graphics buf_gc; // 仮の画用紙の GC (graphics context)
  Dimension d;     // キャンバスの大きさ取得用
  int imgW, imgH;
  int r,x, y, dx, dy; // 飛び回るオブジェクトのパラメータ（GameMaster から指定する場合）
  int tate,yoko;  //ブロックの辺の長さのパラメータ
  Shape[] shapes = new Shape[15]; // どんな図形でも統一して shapes で管理できる
  
  // ■ コンストラクタ
  GameMaster(int imgW, int imgH){    // Canvas を置く親 Frame の大きさを取得
    this.imgW=imgW; // キャンバスの幅を指定
    this.imgH=imgH; // キャンバスの高さを指定
    setSize(imgW, imgH); // キャンバスのサイズを設定
    System.out.println(this.imgW + " " + this.imgH);

//////////*****図形の表示に用いる変数の生成******///////////////////
    x  = (int) (Math.random()*(imgW-2*r)+r);  // 初期値を画面内でランダムに設定
    y  = (int) (Math.random()*(imgH-2*r)+r);  // 初期値を画面内でランダムに設定
    r = (int) Math.round(Math.random()*30);
    dx = (int) Math.round(Math.random()*20-10);// 初期値を-10～10 内でランダムに設定
    dy = (int) Math.round(Math.random()*20-10);// 初期値を-10～10 内でランダムに設定
    tate =(int) (Math.random()*40 + 40);//40~80に設定
    yoko = (int) (Math.random()*40 + 40);

    /////////////////**** 図形の描写******///////////////////   
    shapes[0] = new Square();
    shapes[1] = new Color_Square(x,y,tate,yoko,dx,dy);
    shapes[2] = new Circle();
    shapes[3] = new Color_Circle(x,y,r,dx,dy);
    shapes[4] = new Color_Square(x,y,tate,yoko,dx,dy);
  }

  
  // ■ メソッド
  // コンストラクタ内で createImage を行うと peer の関連で 
  // nullpointer exception が返ってくる問題を回避するために必要
  public void addNotify(){
    super.addNotify();
    buf = createImage(imgW, imgH); // バッファ (Image クラス) を画面と同サイズで作成 (new)
    buf_gc = buf.getGraphics(); // buf の GC (Graphics クラス）を取得
  }

  // ■ メソッド
  public void paint(Graphics gc){
    d = getSize(); // Frame のサイズを取得

    // 1. まず仮の画用紙 img に全て描画する
    buf_gc.setColor(Color.black);     // gc の色を白に
    buf_gc.fillRect(0,0,d.width,d.height); // gc を使って白の四角を全体に描いて初期化

    shapes[0].draw(buf_gc);         // Square クラスの draw メソッドで画用紙に描画
    shapes[1].draw(buf_gc);         //以下同様  
    shapes[2].draw(buf_gc);
    shapes[3].draw(buf_gc);
    shapes[4].draw(buf_gc);
    // 2. 主の画用紙 (this) に描き終わった仮の画用紙 (buf) の内容を貼り付ける
    // 注意）主の画用紙は，Canvas クラスを継承している GameMaster クラスのオブジェクトが
    //       作成された時点でとして作成されている．this キーワードでアクセス
    gc.drawImage(buf, 0, 0, this);

    // 3. 最後に各パラメータの更新処理
    shapes[0].update(d.width, d.height); // 各オブジェクトに共通のメソッドを使う
    shapes[1].update(d.width, d.height); //以下同様
    shapes[2].update(d.width, d.height);
    shapes[3].update(d.width, d.height);
    shapes[4].update(d.width, d.height);
  
  }
  }

  
    