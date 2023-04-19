// Panel クラスの使用例
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays; 

public class SmpPanel extends Frame implements ActionListener {
  // ■ フィールド変数
  Sign keisan = new Sign();
  Button bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9,bt10,bt11,bt12,bt13,bt14, btClr;
  
  TextField txt1;
  int cal=0,i=0;
  String ss = "";
  String ss1 = ""; //計算用に変換する文字列
  double num1,num2; //入力された文字を数字に変換して代入
  boolean num1_flag = false; //数値があるかないかを判断するための変数
  boolean num2_flag = false;

  //演算子が二個以上の数式であったとき　例えば 1+2+3のように演算子が二個以上来た時には二個目の演算子が入力された時点で初めの二数の計算を済ませておく
  //つまりは　1+2+3=3+3というような考え
  int cal_flag = 0;  // 
  int count=0;
  int rec_flag = 0;

  int mode; //演算方法を指定する変数
  
  public static void main(String [] args) {
    SmpPanel si = new SmpPanel(); 
  }

  // ■ コンストラクタ
  SmpPanel() {
    super("Layout Test");
    setSize(300, 200); 
    //============================================================
    // まず、BorderLayout を設定し、NORTH,CENTER,SOUTHだけを使って
    // ウィンドウをおおまかに３つに分ける
    //============================================================
    setLayout(new BorderLayout()); // 先頭に this. が省略されている

    // NORTHにテキストフィールドを設定する
    txt1 = new TextField();
    add(txt1, BorderLayout.NORTH);

    //====================================================================
    // パネルを一枚作成し、CENTER にパネルをはめこむ内部をGridLayoutにする
    // パネルを GridLayout にし、パネル内部にボタンを９コ配置する
    //====================================================================
    Panel p_center = new Panel();
    p_center.setLayout(new GridLayout(4, 3));

    bt0 = new Button("0");  p_center.add(bt0);
    bt1 = new Button("1");  p_center.add(bt1);  // ボタン設定
    bt2 = new Button("2");  p_center.add(bt2);
    bt3 = new Button("3");  p_center.add(bt3);
    bt4 = new Button("4");  p_center.add(bt4);
    bt5 = new Button("5");  p_center.add(bt5);
    bt6 = new Button("6");  p_center.add(bt6);
    bt7 = new Button("7");  p_center.add(bt7);
    bt8 = new Button("8");  p_center.add(bt8);
    bt9 = new Button("9");  p_center.add(bt9);
    bt10 = new Button("+"); p_center.add(bt10);
    bt11 = new Button("-"); p_center.add(bt11);
    bt12 = new Button("×"); p_center.add(bt12);
    bt13 = new Button("÷"); p_center.add(bt13);
    bt14 = new Button("="); p_center.add(bt14);
    bt0.addActionListener(this);
    bt1.addActionListener(this);                // リスナ登録
    bt2.addActionListener(this);
    bt3.addActionListener(this);
    bt4.addActionListener(this);
    bt5.addActionListener(this);
    bt6.addActionListener(this);
    bt7.addActionListener(this);
    bt8.addActionListener(this);
    bt9.addActionListener(this);
    bt10.addActionListener(this);
    bt11.addActionListener(this);
    bt12.addActionListener(this);
    bt13.addActionListener(this);
    bt14.addActionListener(this);

    add(p_center, BorderLayout.CENTER);         // パネルを CENTER に配置

    //=========================================================
    // パネルをもう一枚作成し、SOUTH にパネルをはめこむ
    // そのパネルを GridLayout にし、内部にボタンを２コ配置する
    //=========================================================
    Panel p_south = new Panel();
    p_south.setLayout(new GridLayout(1, 2));

    btClr = new Button("AC"); p_south.add(btClr);
    btClr.addActionListener(this);

    add(p_south, BorderLayout.SOUTH);           // パネルを SOUTH に配置

    setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {    // ボタンクリック処理
    Button btn = (Button)e.getSource();
    //クリアが押された時の処理
    if (btn == btClr){
       ss = "";
       ss1 = "";
       num1 = num2 = 0;
       num1_flag = num2_flag = false;
       mode = 0;
    }

    //何か演算子ではなくて数字が来た時の処理  それぞれ表示用の文字配列ss　　計算用文字配列ss1に蓄積
    else if(btn==bt0 || btn==bt1 || btn==bt2 || btn==bt3 || btn==bt4 || btn==bt5 || btn==bt6 || btn==bt7 || btn==bt8 ||btn==bt9 ){ 
      ss += btn.getLabel();
      ss1 += btn.getLabel();
    }

    else if(btn == bt10){
      ss += "+";
      count++; //演算子の個数を確かめる
      mode=1; //足し算モード

      if(count==1 && rec_flag == 0){
        cal_flag=1;
        num1 = Integer.parseInt(ss1); 
      }
      else if(count == 1&&rec_flag ==1){
        cal_flag=1;
        num2 = Integer.parseInt(ss1); 
      }
      else if(count==2){
        count=1;//カウントリセット
        num2 = Integer.parseInt(ss1);
        rec_flag = 1;
        cal_flag=1;
        num1 = keisan.ano_cal(cal_flag,num1,num2);
      }

      ss1 = ""; //リセット
    }
    else if(btn == bt11){
      ss += "-";
      mode=2;
      count++;

      if(count==1){
        cal_flag=2;
        num1 = Integer.parseInt(ss1); 
      }
      else if(count==2){
        count=1;//カウントリセット
        num2 = Integer.parseInt(ss1);
        cal_flag=2;
        num1 = keisan.ano_cal(cal_flag,num1,num2);
      }
      num2=0;
     
      ss1 = "";
    }
    else if(btn == bt12){
      ss += "×";
      mode=3;
      if(num1_flag) num2 = Integer.parseInt(ss1);
      else num1 = Integer.parseInt(ss1);
      ss1 = "";
    }
    else if(btn == bt13){
      ss += "÷";
      if(num1_flag) num2 = Integer.parseInt(ss1);
      else num1 = Integer.parseInt(ss1);
      mode=4;
      ss1 = "";
    }

    else if(btn == bt14){ //＝が押された時の処理
      if(num1_flag == true && num2_flag == true){
        num1_flag = false;
        num2_flag = false; 
        num1 = keisan.cal(mode,num1,num2);
        num1_flag = true;
      }
      ss = String.valueOf(num1);
    }
    update(this.getGraphics());                 // (注)
  }
  public void paint(Graphics g) {
    txt1.setText(ss);
  }
}