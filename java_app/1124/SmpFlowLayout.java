// レイアウトのサンプルプログラム その１
// FlowLayout
// ウィンドウの大きさを変更すると自動的に再配置が行われることを確認する

import java.awt.*;

public class SmpFlowLayout extends Frame {
  // 配置するボタンのオブジェクトを宣言
  Button bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9;
  
  public static void main(String [] args) {
    SmpFlowLayout ft = new SmpFlowLayout(); 
  }
  // ■ コンストラクタ
  SmpFlowLayout() {
    super("FrameTest");
    this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 15));
    this.setSize(600, 400); 
    bt1 = new Button("button-1"); this.add(bt1);
    bt2 = new Button("button-22"); this.add(bt2);
    bt3 = new Button("button-333"); this.add(bt3);
    bt4 = new Button("button-4444"); this.add(bt4);
    bt5 = new Button("button-55555"); this.add(bt5);
    bt6 = new Button("button-666666"); this.add(bt6);
    bt7 = new Button("button-7777777"); this.add(bt7);
    bt8 = new Button("button-88888888"); this.add(bt8);
    bt9 = new Button("button-999999999"); this.add(bt9);
    this.setVisible(true);

  }
}