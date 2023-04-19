import javax.swing.*;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.event.*;


public class JColor_Select extends JFrame implements ChangeListener{

  JColorChooser colorchooser;
  JLabel colorLabel;
  JDialog dialog;
  Color color; //選択した色を保持しておく変数。

  public static void main(String[] args){
    new JColor_Select();
  } 
  JColor_Select(){
    colorLabel = new JLabel("選択した色が背景色として表示されます");
    color = colorchooser.showDialog(this,"色の選択", Color.white);
  
    //dialog = colorchooser.createDialog(this, "title", 1, this, okListener, cancelListener)

    colorLabel.setOpaque(true);
    colorchooser = new JColorChooser();
    colorchooser.getSelectionModel().addChangeListener(this);
    getContentPane().add(colorchooser, BorderLayout.CENTER);
    getContentPane().add(colorLabel, BorderLayout.PAGE_START);
    //JDialog dialog = getContentPane().createDialog(colorchooser, BorderLayout.CENTER);
  }

  public void stateChanged(ChangeEvent e) {
    colorchooser.getColor();
    colorLabel.setBackground(color);
  }
}