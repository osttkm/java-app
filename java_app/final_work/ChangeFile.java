import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ChangeFile{

  public static void main(String[] args) {

 

    // 入力ファイル
    String inputname = "入力ファイル.bmp";

    // 出力ファイル
    String outputname = "出力ファイル.png";

    try {

      //入力ファイルから画像データを読み込み
      //BufferedImageオブジェクトとする
      BufferedImage bImage = ImageIO.read(new File(inputname));

      //BufferedImageオブジェクトを出力ファイルにpng形式で書き出し
      ImageIO.write(bImage, "png", new File(outputname));

    } catch (Exception e) {

      // ファイルの入出力でエラーになった場合
      e.printStackTrace();

    }

  }

}