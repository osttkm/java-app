import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImageSave extends JFrame implements ActionListener {

	BufferedImage img;
	MyPanel mypane;
	int h;
	int w;

	//画像を開く
	public void OpenImage () {
		try {
		File openFile = new File ("開く画像ファイル");
		img = ImageIO.read(openFile);
		h = img.getHeight();
		w = img.getWidth();
		} catch (IOException ex) {
			System.out.println("Miss");
		}

		//画像をフレームに貼る
		JFrame imgFrame = new JFrame("Open Image");
		imgFrame.setBounds (300, 100, 520, 410);
		imgFrame.setVisible(true);

		mypane = new MyPanel (520, 410);
		imgFrame.getContentPane().add(mypane, BorderLayout.CENTER);
	}

	//画像を貼る用のツール
	public class MyPanel extends JPanel {
		public MyPanel (int width, int height) {
			setSize (width, height);
		}

		public void paintComponent (Graphics g) {
			g.drawImage(img, 0, 0, this);
		}
	}

	//画像を保存する
	public void SaveImage () {
		BufferedImage saveImg = new BufferedImage (w, h, BufferedImage.TYPE_INT_RGB);
		Graphics saveg = saveImg.getGraphics();
		saveg.drawImage(img, 0, 0, null);
		saveg.dispose();
		try {
			FileOutputStream fo = new FileOutputStream (this.writeFile());
			ImageIO.write(img, "png", fo);
		} catch (IOException ex) {
			System.out.println("Miss");
		}
	}

	//出力画像の名前書き
	String writeFile () {
		FileDialog fd = new FileDialog (new Frame (), "保存", FileDialog.SAVE);
		fd.setVisible(true);
		String fullpath = fd.getDirectory() + fd.getFile();
		fd.dispose();
		return fullpath;
	}

	public void actionPerformed (ActionEvent e) {
		String cmd = e.getActionCommand();

		if (cmd.equals("open")) {
			OpenImage();
		} else if (cmd.equals ("save")) {
			SaveImage();
		}
	}

	ImageSave (String title) {
		setTitle(title);
		setBounds (10, 10, 240, 80);
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

		JButton button = new JButton ("Open");
		JButton button1 = new JButton ("Save");
		button.addActionListener (this);
		button1.addActionListener (this);
		button.setActionCommand("open");
		button1.setActionCommand("save");
		button.setBounds(70, 10, 100, 30);
		button1.setBounds (70, 50, 100, 30);
		JPanel pane = new JPanel ();
		pane.add(button);
		pane.add(button1);
		getContentPane().add(pane, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		ImageSave frame = new ImageSave("Image Save");
		frame.setVisible (true);
	}
}