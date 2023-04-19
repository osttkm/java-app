import java.awt.*;
import java.awt.image.*;

public class DrawBufImg01 extends Frame {
  // ■ フィールド変数
  BufferedImage  bufImg; // バッファ付きイメージ
  WritableRaster wr;     // 書き込み可能なラスター
  Graphics       gc;     // 文字列書き込み用 GC

  // ■ main メソッド
  public static void main(String [] args) {
    new DrawBufImg01();
  }

  // ■ コンストラクタ
  DrawBufImg01(){
    super("BufferdImage test"); // フレームのタイトル
    this.setSize(270,270); // フレームのサイズ
    this.setVisible(true); // 可視化

    // 256x256 の大きさ、BGR 各色 8bit で BufferdImage オブジェクト作成
    bufImg = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
    wr = bufImg.getRaster();      // imageBuffer から Raster を取得
    gc = bufImg.createGraphics(); // Graphics Context を新規作成
    for (int i=0;i<256;i++)       // 縦方向のピクセル位置 x
      for (int j=0;j<256;j++)     // 横方向のピクセル位置 y
	// setElem の引数は(int bank, int i, int val)
	// 赤には y座標、緑には x座標、青には x座標を代入
	// << はシフト演算子
        // getDataBuffer() メソッドは、Raster クラスのオブジェクトの DataBuffer を返す
        // setElem() メソッドは、DataBuffer クラスのオブジェクトの指定されたバンク内の
        // 指定された位置の値を設定する
        // 第１引数：バンク
        // 第２引数：書き込む位置（インデックス，画像を１次元配列にしたもの）
        // 第３引数：書き込む値（ここではRGBの値を合計24bitで表したもの）
	  wr.getDataBuffer().setElem(0, i*256+j, (j<<16)+(i<<8)+i);
    gc.drawString("Test",100,100); // 文字列の表示
  }

  // ■ メソッド（描画）
  public void paint(Graphics gc) {
    gc.drawImage(bufImg,0,0,this); // bufImg の内容を描画
  }
}