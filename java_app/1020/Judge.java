/**
 * サイコロゲームの審判のクラス
 *   startDiceGame： ゲームの開始、進行、終了と結果の表示を行うメソッド
 *   judgeDiceRoll： サイコロ振りを１回行う毎に勝負の結果を判定するメソッド
 */
import java.util.Arrays; //Arrays classのsortを使うため

public class Judge {
    /*■■■ メンバー変数部 ■■■*/
    /*■■■ コンストラクタ部 ■■■*/  
    /*■■■ メソッド部 ■■■*/  
    /**
     * サイコロゲームを進行するメソッド
     * 引数は player1 と player2
     * 
     * @param player1 判定対象プレイヤー1
     * @param player2 判定対象プレイヤー2
     * @param player3 判定対象プレイヤー3
     * @param player4 判定対象プレイヤー4
     * @param player5 判定対象プレイヤー5
     * @return （無し）
     */
    //********コマンドラインから設定を加える。***********/
    public void startDiceGame(Player player1, Player player2,Player player3, Player player4, Player player5
    ,int times,int dice_num){
      System.out.println("【サイコロ振りゲーム開始】");

     // ★ サイコロ振りをn回戦行う
     int cnt = 0;
      while(true){
        System.out.println("【" + (cnt + 1) + "回戦目】"); // 何回戦目か表示
        cnt++;
      
        // ★ プレイヤーの手を見て、どちらが勝ちかを判定する
        // Player クラスタイプで winner という変数を定義し，
        // 自分自身のクラス (this) に所属する judgeDiceRoll メソッドの戻り値を winner に入れる
       Player winner = this.judgeDiceRoll(player1, player2,player3,player4,player5,dice_num);

        if (winner != null) { // どちらかが勝った場合
          System.out.println("\n" + winner.getName() + "が勝ちました\n"); // 勝者を表示
          winner.notifyResult(true); // 勝ったプレイヤーへ結果を伝える
        } else {
          // 引き分けの場合
          System.out.println("\n引き分けです\n");
        }
        Player finalWinner = this.judgeFinalWinner(player1, player2,player3,player4,player5,times);
          if(finalWinner != null && finalWinner.getWinCount() == times){
            break;
          }
      }
  
      // ★ ジャンケンの終了を宣言する
      System.out.println("【サイコロ振りゲーム終了】");
  
       // ★ 最終的な勝者を判定する
      Player finalWinner = this.judgeFinalWinner(player1, player2,player3,player4,player5,times);
  
      // ★ 結果を表示する
      System.out.print(player1.getWinCount()+"対"+ player2.getWinCount()+"対"+player3.getWinCount()+"対"+player4.getWinCount()+"対"+player5.getWinCount()+"で");
      if (finalWinner != null) {
          System.out.println(finalWinner.getName() + "の勝ちです\n");
      }
      else {
          System.out.println("引き分けです\n");
      }
    }

///*********************初期設定*****************///
    public void startDiceGame(Player player1, Player player2,Player player3, Player player4, Player player5
    ,int times,int dice_num,int def){
      System.out.println("【サイコロ振りゲーム開始】");

     // ★ サイコロ振りをn回戦行う
     int cnt = 0;
      while(true){
        System.out.println("【" + (cnt + 1) + "回戦目】"); // 何回戦目か表示
        cnt++;
      
        // ★ プレイヤーの手を見て、どちらが勝ちかを判定する
        // Player クラスタイプで winner という変数を定義し，
        // 自分自身のクラス (this) に所属する judgeDiceRoll メソッドの戻り値を winner に入れる
       Player winner = this.judgeDiceRoll(player1, player2,player3,player4,player5,12);

        if (winner != null) { // どちらかが勝った場合
          System.out.println("\n" + winner.getName() + "が勝ちました\n"); // 勝者を表示
          winner.notifyResult(true); // 勝ったプレイヤーへ結果を伝える
        } else {
          // 引き分けの場合
          System.out.println("\n引き分けです\n");
        }
        Player finalWinner = this.judgeFinalWinner(player1, player2,player3,player4,player5,3);
          if(finalWinner != null && finalWinner.getWinCount() == 3){
            break;
          }
      }
  
      // ★ ジャンケンの終了を宣言する
      System.out.println("【サイコロ振りゲーム終了】");
  
       // ★ 最終的な勝者を判定する
      Player finalWinner = this.judgeFinalWinner(player1, player2,player3,player4,player5,3);
  
      // ★ 結果を表示する
      System.out.print(player1.getWinCount()+"対"+ player2.getWinCount()+"対"+player3.getWinCount()+"対"+player4.getWinCount()+"対"+player5.getWinCount()+"で");
      if (finalWinner != null) {
          System.out.println(finalWinner.getName() + "の勝ちです\n");
      }
      else {
          System.out.println("引き分けです\n");
      }
    }


    /**
     * 各プレイヤーにサイコロを振らせ、それぞれの手を見てどちらが勝ちかを判定するメソッド
     * 
     * @param player1 判定対象プレイヤー1
     * @param player2 判定対象プレイヤー2
     * @param player3 判定対象プレイヤー3
     * @param player4 判定対象プレイヤー4
     * @param player5 判定対象プレイヤー5
     * @return 勝ったプレイヤー。引き分けの場合は null を返す。
     */
    private Player judgeDiceRoll(Player player1, Player player2,Player player3, Player player4, Player player5,int dice_num){
      int p1DiceSpot = player1.rollTheDice(dice_num); // プレイヤー１の手を出す
      int p2DiceSpot = player2.rollTheDice(dice_num); // プレイヤー２の手を出す
      int p3DiceSpot = player3.rollTheDice(dice_num); // プレイヤー３の手を出す
      int p4DiceSpot = player4.rollTheDice(dice_num); // プレイヤー４の手を出す
      int p5DiceSpot = player5.rollTheDice(dice_num); // プレイヤー５の手を出す
      Player [] player = new Player [5];
      player[0] = player1;
      player[1] = player2;
      player[2] = player3;
      player[3] = player4;
      player[4] = player5;
      int [] point = new int [5];
      point[0] = p1DiceSpot;
      point[1] = p2DiceSpot;
      point[2] = p3DiceSpot;
      point[3] = p4DiceSpot;
      point[4] = p5DiceSpot; 

      // それぞれの手を表示する
      System.out.print(p1DiceSpot);
      System.out.print(" vs. ");
      System.out.print(p2DiceSpot);
      System.out.print(" vs. ");
      System.out.print(p3DiceSpot);
      System.out.print(" vs. ");
      System.out.print(p4DiceSpot);
      System.out.print(" vs. ");
      System.out.print(p5DiceSpot);
      Arrays.sort(point);//昇順に得点を並び替える。
      
      if(point[4] != point[3]){//複数最高得点者がいた場合はnullを返す
        for(int i=0;i<5;i++){
          if(player[i].HearPoint() == point[4]){ //最高得点者が誰なのかを特定する
            return player[i]; //勝者を返す。
          }
        }
      }
        return null;//１位が複数いる場合の処理
    }
  
      

  
  
    /**
     * 最終的な勝者を判定するメソッド
     * 
     * @param player1 判定対象プレイヤー1
     * @param player2 判定対象プレイヤー2
     * @param player3 判定対象プレイヤー3
     * @param player4 判定対象プレイヤー4
     * @param player5 判定対象プレイヤー5
     * @return 勝ったプレイヤー。引き分けの場合は null を返す。
     */
    private Player judgeFinalWinner(Player player1, Player player2,Player player3,Player player4,Player player5
    ,int times){
      Player winner = null; //初期化しておく！
      int player1WinCount = player1.getWinCount(); // Player1から勝ち数を聞く
      int player2WinCount = player2.getWinCount(); // Player2から勝ち数を聞く
      int player3WinCount = player3.getWinCount(); // Player3から勝ち数を聞く
      int player4WinCount = player4.getWinCount(); // Player4から勝ち数を聞く
      int player5WinCount = player5.getWinCount(); // Player5から勝ち数を聞く
   
      
      if (player1WinCount == times) {
        winner = player1; // プレイヤー1の勝ち
      } else if (player2WinCount == times) {
        winner = player2; // プレイヤー2の勝ち
      } else if (player3WinCount == times) {
        winner = player3; // プレイヤー3の勝ち
      } else if (player4WinCount == times) {
        winner = player4; // プレイヤー4の勝ち
      } else if (player5WinCount == times) {
        winner = player5; // プレイヤー5の勝ち
      } 
      
      return winner;
    }
  }