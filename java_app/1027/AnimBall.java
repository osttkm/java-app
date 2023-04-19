/**
 * Canvas 内をボールが反射しながら飛び回るプログラム。Maru クラスを別に用意。
 * このクラスでは、
 * 1. フレームの作成
 * 2. スレッドの逐次進行
 * を行う。描画内容の詳細は MyCanvas クラス内で行う
 *
 * @author fukai
 */

import java.awt.*;

public class AnimBall extends Frame implements Runnable {
  // ■ フィールド変数
  Thread   th; // スレッドオブジェクトの宣言
  GameMaster mc; // 描画領域オブジェクトの宣言
  // ■ main メソッド（スタート地点）
  public static void main(String[] args) {
    AnimBall ab = new AnimBall(); // 自分自身のオブジェクトを作成
  }

  // ■ コンストラクタ
  AnimBall(){
    super("Ball Animation"); // 親クラスのコンストラクタを呼び出す
    int cW=1000, cH=1000;      // キャンバスのサイズ
    this.setSize(cW, cH);    // フレームのサイズ指定
    this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // レイアウト指定
    
    mc = new GameMaster(cW,cH);// キャンバスのオブジェクトを作成
    this.add(mc);            // キャンバスをフレームに配置
    this.setVisible(true);   // 可視化

    th = new Thread(this); // スレッドのオブジェクトの作成
    th.start();            // スレッドを start メソッドで開始
  }

  // ■ メソッド（Runnable)
  public void run() {  // スレッドにより実行される
    try {
      while(true) {   // 無限ループ
	th.sleep(60); // 再描画する前に一定時間休止
	mc.repaint(); // 再描画を要求する repaint() は update() を呼び出す
      }
    }
    catch(Exception e) { // 例外処理
      System.err.println( "エラーが発生しました: " + e );
    }
  }
}