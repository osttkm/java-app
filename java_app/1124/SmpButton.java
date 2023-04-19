/* GUI のベースとなるウィンドウを作成・表示する */
// AWT を利用するためには、パッケージのインポートが必要です
// ワイルドカード * を使って、とりあえず AWT の関連パッケージを全てインポートします
import java.awt.*; 
                   
// ■ SmpButton という名前のクラス
// extends キーワードを使って Frame クラスを継承します
// 継承とは、機能の引き継ぎ（拡張）をすることで、Frame クラスは java.awt.Frame で定義されています
public class SmpButton extends Frame {
  // ■ mainメソッド
  // main メソッドは SmpButton クラスに必須ではないが
  // プログラム全体のスタート地点としてここに挿入
  public static void main(String [] args) {
  // SmpButton クラス（自分自身）のオブジェクトを作成
    SmpButton sb = new SmpButton(); 
  }
  // ■ コンストラクタ
  SmpButton() {
    // 子クラスのコンストラクタ実行時に、まず super キーワードで親クラスのコンストラクタを実行
    // 引数の文字列はフレームのタイトル
    super("Button Test");
    // 自分自身 (this=SmpButton のオブジェクト) のサイズを指定（setSize は Frame クラスのメソッド）
    this.setSize(200, 100); 
    // フレーム上にウィジェット（オブジェクト）を配置する方法を指定（後述）
    this.setLayout(new FlowLayout());
    // Button クラスで b1 という名のオブジェクトを作成．引数はボタンのラベル．
    Button b1 = new Button("OK");
    // Button オブジェクト b1 を this (FrameTest のオブジェクト) に追加
    this.add(b1);    
    // 自分自身 (this=SmpButton のオブジェクト) を可視化（setVisible は Frame クラスのメソッド）
    this.setVisible(true); 
  }
}