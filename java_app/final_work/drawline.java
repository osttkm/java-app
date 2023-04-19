import javax.swing.*;
import java.awt.*;

public class drawline extends JPanel{
    // BufferedImage bufferImage=null;
    // Graphics2D bufferGraphics=null;
    Dimension d;
    Color currentColor;

    public void setPenColor(Color newColor) {
		currentColor = newColor;
    }
    
    public void get_Size(Oekaki obj){
        d = obj.getSize();
    }
    private void createBuffer(int width, int height) {//ダブルバッファリング用のメソッド
         bufferImage = new BufferedImage(d.width, d.height,BufferedImage.TYPE_INT_BGR);
         bufferGraphics=bufferImage.createGraphics(); //getGraphicsと似ているが、戻り値がGraphics2D。
         bufferGraphics.setBackground(Color.white);
         bufferGraphics.clearRect(0, 0, width, height); //バッファクリア
     }

    public void drawLine(int x1, int y1, int x2, int y2){
        //if(null==bufferGraphics) this.createBuffer(this.getWidth(),this.getHeight());  //バッファをまだ作ってなければ作る
        Graphics g = this.getGraphics();
		g.setColor(currentColor);
        g.drawLine(x1, y1, x2, y2);
        /*bufferGraphics.drawLine(x1, y1, x2, y2); // バッファに描画する
		repaint();//再描画するためpaintComponentを呼び出す。*/
    }

    public void paintComponent(Graphics g) {
		super.paintComponent(g);//他に描画するものがあるかもしれないので親を呼んでおく
		if(null!=bufferImage) g.drawImage(bufferImage, 0,0,this);//バッファを表示する
	}
}
