import java.awt.*;
import java.awt.image.*;
import java.awt.Toolkit;
import javax.imageio.ImageIO;
import java.io.File;

public class DrawBufImg03 extends Frame {
  // ■ フィールド変数
  BufferedImage bufImg; // 読み込んだ画像を格納する
  int width, height;
  int[] rgb = new int[5]; // value of RGB

  // ■ main メソッド（プログラムのスタート地点）
  public static void main(String[] args) {
    new DrawBufImg03();
  }

  // ■ コンストラクタ
  DrawBufImg03() {
    setSize(520,360); // フレームのサイズ指定
    try {
      bufImg = ImageIO.read(new File("img1.jpg"));
    } catch (Exception e) {
      throw new RuntimeException(e);
      //this.dispose();
    }
    WritableRaster wr = bufImg.getRaster();      // BufferdImage の Raster を取得
    //ColorModel     cm = bufImg.getColorModel();  // BufferdImage の ColorModel を取得
    Graphics2D     g2 = bufImg.createGraphics(); // BufferdImage の Graphics contents を取得
    width  = wr.getWidth();                      // 横幅のピクセル値を取得
    height = wr.getHeight();                     // 縦幅のピクセル値を取得
    //System.out.println("ColorModel.toString(): " + cm.toString()); // ColorModel の情報を表示
    //System.out.println("ColorModel.getPixelSize(): " + cm.getPixelSize() + " bit per pixel");
    for (int x=0; x<width;x++){// 横方向
      for (int y=0; y<height;y++){// 縦方向
	      wr.getPixel(x, y, rgb);     // WritableRaster から(x,y)のピクセル値を配列 rgb に取得
	//	System.out.println("R:" + rgb[0] + " G:" + rgb[1] + " B:" +rgb[2]); // 確認用
        if (rgb[2]>100){         // もし青色が濃ければ
          rgb[2]=0;              // 青色成分をゼロに
          //rgb[3] = 0;
          wr.setPixel(x,y,rgb);  // ピクセルの色情報を更新
        }
      }
    }
    g2.drawString("Test",10,10);

    setVisible(true); // 可視化
  }

  public void paint(Graphics g) {
    g.drawImage(bufImg,10,10,this);
  }
}