import java.awt.*;
import java.util.Random;
class EnemyD extends MovingObject {

  // コンストラクタ(初期値設定)
  EnemyD(int apWidth, int apHeight) {
    super(apWidth, apHeight); // スーパークラス(ObjBase)のコンストラクタの呼び出し
    w = 12;
    h = 12;
    hp = 0;  // 初期状態では全て死亡
    r=(int)(Math.random()*256);
    g=(int)(Math.random()*256);
    b=(int)(Math.random()*256);
    point = 5;
  }
  // ○を描き更新するメソッド
  void move(Graphics buf, int apWidth, int apHeight,int kill_point) {
    Color color = new Color(r,g,b);
    buf.setColor(color); //ランダムな色にする
    if (hp>0) { // もし生きていれば
      buf.fillRect(x - w, y - h, 2 * w, 2 * h); // gc を使って○を描く
      if(kill_point >= 100 && kill_point <=150){
        x = x + 2*dx; // 座標値の更新
        y = y + 2*dy; // 座標値の更新
      }
      else if(kill_point >=0 && kill_point < 100){
        x = x + dx; // 座標値の更新
        y = y + dy; // 座標値の更新
      }
      else{
        x = x + 3*dx; // 座標値の更新
        y = y + 3*dy; // 座標値の更新
      }
    }
      if (y>apHeight+h)
	hp=0;
    }
  
  void revive(int apWidth, int apHeight) { // 敵を新たに生成（再利用）
    x = (int)(Math.random()*(apWidth-2*w)+w);
    y = -h;
    dy = 1;
    if (x<apWidth/2)
      dx = (int)(Math.random()*2);
    else
      dx = -(int)(Math.random()*2);
    hp = 3;
  }
}
