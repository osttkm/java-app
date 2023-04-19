// レイアウトのサンプルプログラム その２
// BorderLayout

import java.awt.*;

public class SmpBorderLayout extends Frame {
  // 配置するボタンのオブジェクトを宣言
  Button bt1, bt2, bt3, bt4, bt5;
  
  public static void main(String [] args) {
    SmpBorderLayout ft = new SmpBorderLayout(); 
  }
  // ■ コンストラクタ
  SmpBorderLayout() {
    super("Border Layout");
    // BorderLayout を使用　左右、上下の間隔を数値で指定
    this.setLayout(new BorderLayout(5, 5));
    this.setSize(600, 400); 
    // North と South の縦幅は、部品の縦幅で決まる
    // West と East の横幅は部品の大きさ（文字列の長さ）で決まる
    // Center の横幅は、West と East の残り幅
    // BorderLayout.WEST の代わりに文字列 "West" で指定も可
    bt1 = new Button("button-North");  this.add(bt1, BorderLayout.NORTH);
    bt2 = new Button("button-South");  this.add(bt2, BorderLayout.SOUTH);
    bt3 = new Button("button-East");   this.add(bt3, BorderLayout.EAST);
    bt4 = new Button("button-West");   this.add(bt4, "West");  
    bt5 = new Button("button-Center"); this.add(bt5, "Center");
    this.setVisible(true);
  }
}