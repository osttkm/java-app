// レイアウトのサンプルプログラム３
//
// グリッド（格子）レイアウトの例
// はじめに格子の数を指定する。
//
import java.awt.*;

public class SmpGridLayout extends Frame {
  Button bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, btA;
  
  public static void main(String [] args) {
    SmpGridLayout sgl = new SmpGridLayout(); 
  }

  // ■ コンストラクタ
  SmpGridLayout() {
    super("GridLayout Test");
    // 格子の数を 4 x 3 で作成
    this.setLayout(new GridLayout(4, 3));
    this.setSize(300, 200); 
    // 部品は、左上から順に配置される
    bt1 = new Button("button-1"); this.add(bt1);
    bt2 = new Button("button-2"); this.add(bt2);
    bt3 = new Button("button-3"); this.add(bt3);
    bt4 = new Button("button-4"); this.add(bt4);
    bt5 = new Button("button-5"); this.add(bt5);
    bt6 = new Button("button-6"); this.add(bt6);
    bt7 = new Button("button-7"); this.add(bt7);
    bt8 = new Button("button-8"); this.add(bt8);
    bt9 = new Button("button-9"); this.add(bt9);
    btA = new Button("button-A"); this.add(btA);
    this.setVisible(true);
  }
}