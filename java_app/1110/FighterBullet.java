/**
 *  自機から発射する弾のクラス
 *
 * @author fukai
 */

import java.awt.*;
class FighterBullet extends MovingObject {
  /** コンストラクタ **/
  FighterBullet() {
    w = h = 3; // 弾の半径
    dx = 0; dy = -6;
    hp = 0;    // 初期値は全て非アクティブ
  }
  /** メソッド **/
  void move(Graphics buf, int apWidth, int apHeight,int kill_point) {
    if (hp>0) {
      buf.setColor(Color.white) ;        // gc の色を黒に
      buf.fillOval(x-w, y-h, 2*w, 2*h); // gc を使って・を描く
      if (y > 0 && y < apHeight && x > 0 && x < apWidth) // もし弾が画面内なら
	y = y + dy; // 弾の位置を更新
      else
	hp = 0;     // 画面の外に出たら hp をゼロに
    }
    if(kill_point>300){
      w = h = 10;
    }
    else if(kill_point>400){
      w = h = 20;
    }
  }
  void revive(int x, int y) { // x, y はFighterの位置
    this.x = x;
    this.y = y;
    hp = 1;         // 発射したら弾をアクティブにする
  }
  
}

