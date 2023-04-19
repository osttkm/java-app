/**
 * サイコロころがしゲーム
 * java 非オブジェクト指向ヴァージョン
 *
 * 二人のプレイヤーがそれぞれサイコロを振り
 * 大きい目が出た方が勝ち，同じだと引き分け
 * 全部で３回勝負
 */
public class DiceGameNonObjective {
    public static void main(String[] args) {
      int p1WinCount = 0; // プレイヤー１の勝ち数
      int p2WinCount = 0; // プレイヤー２の勝ち数
      int p1DiceSpot = 0; // プレイヤー１が出す目
      int p2DiceSpot = 0; // プレイヤー２が出す目
        
      System.out.println("【サイコロ転がしゲーム開始】\n"); // プログラムが開始したことを表示する
  
      /*■■■ サイコロ転がしを３回実施する ■■■*/
      for (int cnt = 0; cnt < 3; cnt++) { // cnt は勝負の回数
        /*■■■ 何回戦目かを表示する ■■■*/
        System.out.println("【" + (cnt + 1) + "回戦目】");
  
        /*■■■ プレイヤー１がサイコロを振る ■■■*/
        p1DiceSpot = (int)Math.ceil(Math.random()*6);
        System.out.print(p1DiceSpot); // プレイヤー１の目を表示する
  
        System.out.print(" vs. ");
  
        /*■■■ プレイヤー２がサイコロを振る ■■■*/
        p2DiceSpot = (int)Math.ceil(Math.random()*6);
        System.out.print(p2DiceSpot); // プレイヤー１の目を表示する
  
        /*■■■ どちらが勝ちかを判定し、結果を表示する ■■■*/
        // プレイヤー１が勝つ場合
        if (p1DiceSpot > p2DiceSpot){
          System.out.println("　プレイヤー１が勝ちました\n"); // 勝負の結果を表示する
          p1WinCount++; // プレイヤー１の勝った回数を加算する
        }
        // プレイヤー２が勝つ場合
        else if (p1DiceSpot < p2DiceSpot){
          System.out.println("　プレイヤー２が勝ちました\n"); // 勝負の結果を表示する
          p2WinCount++; // プレイヤー２の勝った回数を加算する
        }
        // 引き分けの場合
        else {
          System.out.println("　引き分けです\n");// 勝負の結果を表示する
        }
      }
  
      /*■■■ 最終的な勝者を判定し、画面に表示する ■■■*/
      System.out.println("【サイコロころがしゲーム終了】");
        
      if (p1WinCount > p2WinCount) { // プレイヤー１の勝ち数が多い時
        System.out.println(p1WinCount + "対" + p2WinCount   + "でプレイヤー１の勝ち\n");
      }
      else if (p1WinCount < p2WinCount) { // プレイヤー２の勝ち数が多い時
        System.out.println(p1WinCount + "対" + p2WinCount   + "でプレイヤー２の勝ち\n");
      }
      else if (p1WinCount == p2WinCount) { // プレイヤー１と２の勝ち数が同じ時
        System.out.println(p1WinCount + "対" + p2WinCount   + "で引き分け\n");
      }
    }
  }