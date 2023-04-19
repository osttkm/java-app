/**
 * サイコロころがしゲーム
 * C言語，非オブジェクト指向ヴァージョン
 *
 * 二人のプレイヤーがそれぞれサイコロを振り
 * 大きい目が出た方が勝ち，同じだと引き分け
 * 全部で３回勝負
 */
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main() {
  int p1WinCount = 0; // プレイヤー１の勝ち数
  int p2WinCount = 0; // プレイヤー２の勝ち数
  int p1Value    = 0; // プレイヤー１が出す目
  int p2Value    = 0; // プレイヤー２が出す目
  
  int cnt = 0; // 試合の回数
  
  srand((unsigned)time(NULL)); // 乱数の初期化
      
  printf("【サイコロ転がしゲーム開始】\n"); // プログラムが開始したことを表示する

  /*■■■ サイコロ転がしを３回実施する ■■■*/
  for (cnt = 0; cnt < 3; cnt++) { // cnt は勝負の回数
    /*■■■ 何回戦目かを表示する ■■■*/
    printf("【%d回戦目】",(cnt+1));

    /*■■■ プレイヤー１がサイコロを振る ■■■*/
    p1Value = (int)((double)rand()/(RAND_MAX+1.0)*6+1); // 1 から 6 の目を乱数で生成
    printf("プレイヤー１：%d", p1Value); // プレイヤー１の目を表示する

    printf(" vs. ");

    /*■■■ プレイヤー２がサイコロを振る ■■■*/
    p2Value = (int)((double)rand()/(RAND_MAX+1.0)*6+1); // 1 から 6 の目を乱数で生成
    printf("プレイヤー２：%d", p2Value); // プレイヤー１の目を表示する

    /*■■■ どちらが勝ちかを判定し、結果を表示する ■■■*/
    // プレイヤー１が勝つ場合
    if (p1Value > p2Value){
      printf("　プレイヤー１が勝ちました\n"); // 勝負の結果を表示する
      p1WinCount++; // プレイヤー１の勝った回数を加算する
    }
    // プレイヤー２が勝つ場合
    else if (p1Value < p2Value){
      printf("　プレイヤー２が勝ちました\n"); // 勝負の結果を表示する
      p2WinCount++; // プレイヤー２の勝った回数を加算する
    }
    // 引き分けの場合
    else {
      printf("　引き分けです\n");
    }
  }

  /*■■■ 最終的な勝者を判定し、画面に表示する ■■■*/
  printf("\n【サイコロころがしゲーム終了】\n");

  if (p1WinCount > p2WinCount) {  // プレイヤー１の勝ち数が多い時
    printf("%d 対 %d でプレイヤー１の勝ち\n", p1WinCount, p2WinCount);
  }
  else if (p1WinCount < p2WinCount) { // プレイヤー２の勝ち数が多い時
    printf("%d 対 %d でプレイヤー２の勝ち\n", p1WinCount, p2WinCount);
  }
  else if (p1WinCount == p2WinCount) { // プレイヤー１と２の勝ち数が同じ時
    printf("%d 対 %d で引き分け\n", p1WinCount, p2WinCount);
  }
}