import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.CloseAction;
import java.io.File;
import java.awt.BorderLayout;
import java.awt.event.*;

public class File_Chooser extends JFrame implements ActionListener{

  JLabel label;
  File file = null;
  
  File_Chooser(){
    File dir = new File("/mnt/c/Users/takus/Documents/pro.c/プログラム実践/mywork");//カレントディレククトリを指定
    JFileChooser filechooser = new JFileChooser(dir);
    int selected = filechooser.showOpenDialog(this);//1,0,-1で動作分け

    if (selected == JFileChooser.APPROVE_OPTION){
      file = filechooser.getSelectedFile();
     } //ここで取得したファイルにbufferedimageを保存
    else if(selected == JFileChooser.CANCEL_OPTION){
      this.dispose();
    }
  }

  public void actionPerformed(ActionEvent e){}
}