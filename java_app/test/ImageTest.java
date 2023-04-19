import java.lang.Package;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import com.sun.image.codec.jpeg.*;

public class ImageTest implements ActionListener, addChangeListener {

// Images to draw
private static final String[] IMG_PATHS = new String[] { "img1.png", "img2.png" };

public static void main(String[] args) {
new ImageTest();
}

public ImageTest() {

JFrame f = new JFrame("See Through Image");
f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

// Image panel
final SeeThroughComponent st = new SeeThroughComponent(IMG_PATHS);

// Control panel
JPanel controlPanel = new JPanel(new BorderLayout());
JSlider opacitySlider = new JSlider(0, 100);
opacitySlider.addChangeListener(new ChangeListener() {
public void stateChanged(ChangeEvent e) {
JSlider slider = (JSlider) e.getSource();
st.setOpacity(slider.getValue() / 100f);
st.repaint();
};
});
JButton saveButton = new JButton("Save");
saveButton.addActionListener(new ActionListener() {
@Override
public void actionPerformed(ActionEvent e) {
saveResultImage(st);
}
});
controlPanel.add(opacitySlider, BorderLayout.CENTER);
controlPanel.add(saveButton, BorderLayout.SOUTH);

f.add(st, BorderLayout.CENTER);
f.add(controlPanel, BorderLayout.EAST);
f.pack();
f.setVisible(true);
}

public void saveResultImage(Component comp) {
Dimension size = comp.getSize();
BufferedImage myImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
Graphics2D g2 = myImage.createGraphics();
comp.paint(g2);
try {
OutputStream out = new FileOutputStream(System.getProperty("user.dir") + "/result.jpg");
JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
encoder.encode(myImage);
out.close();
} catch (Exception e) {
e.printStackTrace();
}
}
}

class SeeThroughComponent extends Component {

private static final long serialVersionUID = 1L;

private BufferedImage[] images;
float[] scales = { 1f, 1f, 1f, 0.5f };
float[] offsets = new float[4];
RescaleOp rop;

public SeeThroughComponent(String[] imagePaths) {
try {
images = new BufferedImage[imagePaths.length];
for (int ii = 0; ii < imagePaths.length; ii++) {
images[ii] = drawImage(imagePaths[ii]);
}
} catch (IOException e) {
System.out.println("Image could not be read");
System.exit(1);
}
setOpacity(0.5f);
}

private BufferedImage drawImage(String imgSrc) throws IOException {
BufferedImage img1 = ImageIO.read(getClass().getResourceAsStream(imgSrc));
int w = img1.getWidth(null);
int h = img1.getHeight(null);
BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
Graphics g = image.getGraphics();
g.drawImage(img1, 0, 0, null);
return image;
}

public Dimension getPreferredSize() {
int width = 0;
int height = 0;
for (BufferedImage image : images) {
if (width < image.getWidth(null)) {
width = image.getWidth(null);
}
if (height < image.getHeight(null)) {
height = image.getHeight(null);
}
}
return new Dimension(images[0].getWidth(null), images[0].getHeight(null));
}

public void setOpacity(float opacity) {
scales[3] = opacity;
rop = new RescaleOp(scales, offsets, null);
}

public void paint(Graphics g) {
Graphics2D g2d = (Graphics2D) g;
g2d.setColor(Color.white);
g2d.fillRect(0, 0, getWidth(), getHeight());
for (BufferedImage image : images) {
g2d.drawImage(image, rop, 0, 0);
}
}
}