import javax.swing.JPanel;
import java.awt.*;

public class DrawPanel extends JPanel {
    Color currentColor = Color.black;
    BufferedImage bufferImage=null;
    Graphics2D bufferGraphics=null;
    
    private void createBuffer(int width, int height) {
        //バッファ用のImageとGraphicsを用意する
         bufferImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_BGR);
         bufferGraphics=bufferImage.createGraphics(); //getGraphicsと似ているが、戻り値がGraphics2D。
         bufferGraphics.setBackground(Color.white);
         bufferGraphics.clearRect(0, 0, width, height); //バッファクリア
     }

    public void setPenColor(Color newColor) {
		currentColor = newColor;
    }
	public void drawLine(int x1, int y1, int x2, int y2){
        Graphics g = this.getGraphics();
        g.setColor(currentColor);
		g.drawLine(x1, y1, x2, y2);
	}
 }