// レイアウトのサンプルプログラム
// GridBag レイアウトの例
import java.awt.*;

public class SmpGridBagLayout extends Frame {
  Button bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt10;
  TextField txt1;  

  public static void main(String [] args) {
    SmpGridBagLayout sgl = new SmpGridBagLayout(); 
  }

  // ■ コンストラクタ
  SmpGridBagLayout() {
    super("GridBagLayout Test");
    this.setSize(300, 200); 
    // GridBagLayout マネージャーの生成
    GridBagLayout      gBag = new GridBagLayout();
    // 格納方法を指定するクラスの生成
    GridBagConstraints gCon = new GridBagConstraints();
    // GridBagLayout マネージャーの登録
    setLayout(gBag);

    // セルの領域よりも部品が小さいときにセル枠まで拡張するかどうかの指定
    // GridBagConstraints.NONE          ：サイズの変更無し
    // GridBagConstraints.HORIZONTAL    ：幅のみ拡張
    // GridBagConstraints.VERTICAL      ：高さのみ拡張
    // GridBagConstraints.BOTH          ：幅と高さの両方を拡張
    gCon.fill = GridBagConstraints.BOTH;  // 縦横を枠サイズに拡張する
     
    // セルとセルの間に入れる空白を指定 (top, left, bottom, right)
    gCon.insets = new Insets(2, 2, 2, 2); // セル間に 2pt ずつ空白を入れる

    // ====================================
    // ==== 1行目にボタンを4つ設定する ====
    // ====================================

    // 部品の配置位置（セルのアドレス）を指定
    gCon.gridx = 0; gCon.gridy = 0;
    // セルの大きさを指定（1, 1 なのでセルひとつ分）
    gCon.gridwidth = 1; gCon.gridheight = 1;
    // 部品の大きさを拡張するときの重み付け
    gCon.weightx = 1.0; gCon.weighty = 1.0;
    // ボタン１の作成と配置
    bt1 = new Button("1"); gBag.setConstraints(bt1, gCon); add(bt1);

    // 部品の配置位置を再設定
    gCon.gridx = 1; gCon.gridy = 0;
    // ボタン２の作成と配置
    bt2 = new Button("2"); gBag.setConstraints(bt2, gCon); add(bt2);

    gCon.gridx = 2; gCon.gridy = 0;
    bt3 = new Button("3"); gBag.setConstraints(bt3, gCon); add(bt3);

    gCon.gridx = 3; gCon.gridy = 0;
    bt4 = new Button("4"); gBag.setConstraints(bt4, gCon); add(bt4);

    // ====================================
    // ==== 2行目にボタンを3つ設定する ====
    // ====================================

    gCon.gridx = 0; gCon.gridy = 1;
    gCon.gridwidth = 2; gCon.gridheight = 1; // 横幅を２に設定
    bt5 = new Button("5"); gBag.setConstraints(bt5, gCon); add(bt5);

    gCon.gridx = 2; gCon.gridy = 1;
    gCon.gridwidth = 1; gCon.gridheight = 1;
    bt6 = new Button("6"); gBag.setConstraints(bt6, gCon); add(bt6);

    gCon.gridx = 3; gCon.gridy = 1;
    gCon.gridwidth = 1; gCon.gridheight = 1;
    bt7 = new Button("7"); gBag.setConstraints(bt7, gCon); add(bt7);

    // =============================================
    // ==== 3行目にテキストフィールドを設定する ====
    // =============================================

    // 横だけ枠サイズに拡張する
    gCon.fill = GridBagConstraints.HORIZONTAL;  // 拡張方法の指定
    gCon.gridx = 1; gCon.gridy = 2;             // 位置の指定
    gCon.gridwidth = 2; gCon.gridheight = 1;    // 大きさの指定
    txt1 = new TextField("TextField1");
    gBag.setConstraints(txt1, gCon); add(txt1);

    // 4行目にボタンを3つ設定する
    gCon.fill = GridBagConstraints.BOTH;     // 拡張方法の指定
    gCon.gridx = 0; gCon.gridy = 3;          // 位置の指定
    gCon.gridwidth = 2; gCon.gridheight = 2; // 大きさの指定
    bt8 = new Button("8"); gBag.setConstraints(bt8, gCon); add(bt8);

    gCon.gridx = 2; gCon.gridy = 3;          // 位置の指定
    gCon.gridwidth = 2; gCon.gridheight = 1; // 大きさの指定
    bt9 = new Button("9"); gBag.setConstraints(bt9, gCon); add(bt9);

    gCon.gridx = 2; gCon.gridy = 4;           // 位置の指定
    gCon.gridwidth = 2; gCon.gridheight = 1;  // 大きさの指定
    bt10 = new Button("10"); gBag.setConstraints(bt10, gCon); add(bt10);
    this.setVisible(true);
  }
}