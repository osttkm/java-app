/** クラスに関するサンプルプログラム その１
    Teki クラスを利用する TestClass クラス
*/

class kadai02 {
    public static void main(String[] args) {
        Teki[] teki = new Teki[5]; //敵　クラス型で５つ配列として宣言
        for(int i = 0;i<5;i++){
            teki[i] = new Teki();
        }
  
    for(int j = 0;j < 10;j++){
      for (int i=0; i<5; i++) {
        System.out.print("敵"+(i+1)+"の位置： ");
        teki[i].dispXY();   // Teki の位置を確認
        teki[i].update();   // Teki の状態を更新
      }
      System.out.println(""); // 改行
    }
    }
  }