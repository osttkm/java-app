//  画像を取り込むサンプルプログラム
//  Image クラスは java.awt.Image をインポートする必要がある
import java.awt.*;

public class SmpDrawImg extends Frame {
  // ■ フィールド変数
  // Image クラスのオブジェクトに画像を取り込む
  // 実行ファイルのあるディレクトリ内の img ディレクトリにある画像を指定
  Image img = this.getToolkit().getImage("fileName.png");

  // ■ main メソッド（プログラムのスタート地点）
  public static void main(String[] args) {
    SmpDrawImg sdi = new SmpDrawImg(); //自分自身のオブジェクトを作成
    sdi.setSize(300,200); //サイズ指定
    sdi.setVisible(true); //可視化
  }

  // ■ メソッド
  public void paint(Graphics gc) { //画像描画メソッド
    gc.drawImage(img, 10, 30, this);          // 表示する場所を指定して描画
    gc.drawImage(img, 150, 50, 30, 30, this); // 大きさも指定して（変更して）描画
    gc.drawImage(img, 200, 30, 260, 90, 10, 10, 70, 70, this); // 原画の領域指定で（切り取り）描画
    gc.drawString("aaa", 10, 130);
  }
}