import javax.swing.*;

//import org.graalvm.compiler.nodes.memory.address.OffsetAddressNode;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import java.awt.BorderLayout;
import java.awt.event.*;

public class JFileChooserTest3 extends JFrame implements ActionListener{

  JLabel label;
  BufferedImage offScreen;
  /*public static void main(String[] args){
    JFileChooserTest3 frame = new JFileChooserTest3();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBounds(10, 10, 300, 200);
    frame.setTitle("タイトル");
    frame.setVisible(true);
  }*/

  JFileChooserTest3(BufferedImage img){

    JButton button = new JButton("file select");
    button.addActionListener(this);

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(button);

    label = new JLabel();

    JPanel labelPanel = new JPanel();
    labelPanel.add(label);

    getContentPane().add(labelPanel, BorderLayout.CENTER);
    getContentPane().add(buttonPanel, BorderLayout.PAGE_END);

    this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    this.setLocationRelativeTo(null); //windowを中心に表示する
    
    this.setBounds(10, 10, 300, 200);
    this.setTitle("ファイル");
    this.setVisible(true);
  }

  public void actionPerformed(ActionEvent e){
    File dir = new File("/mnt/c/Users/takus/Documents/pro.c/プログラム実践");
    JFileChooser filechooser = new JFileChooser(dir);//初期ディレクトリの設定

    int selected = filechooser.showSaveDialog(this);
    if (selected == JFileChooser.APPROVE_OPTION){
      File file = filechooser.getSelectedFile(); //このファイルに保存するように設定する
      saveFile(file);
      label.setText(file.getName() + "に保存されました");//file.getName()は自分で打ち込んで決めることもできる
      
    }else if (selected == JFileChooser.CANCEL_OPTION){
      label.setText("キャンセルされました");
    }else if (selected == JFileChooser.ERROR_OPTION){
      label.setText("エラー又は取消しがありました");
    }
  }

  public void saveFile(File file) {
		try{
      ImageIO.write(this.img, "PNG", file);
     }
     catch(Exception e) {
        e.printStackTrace();
     }
	}
}