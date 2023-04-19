import java.awt.*;
import java.util.Random;
class EnemyC extends MovingObject {

  // コンストラクタ(初期値設定)
  EnemyC(int apWidth, int apHeight) {
    super(apWidth, apHeight); // スーパークラス(ObjBase)のコンストラクタの呼び出し
    w = 12;
    h = 12;
    hp = 0;  // 初期状態では全て死亡
    r=(int)(Math.random()*256);
    g=(int)(Math.random()*256);
    b=(int)(Math.random()*256);
    point = 50;
  }
  // ○を描き更新するメソッド
  void move(Graphics buf, int apWidth, int apHeight,int kill_point) {
    
    Color color = new Color(r,g,b);
    buf.setColor(color); //ランダムな色にする
    if (hp>0) { // もし生きていれば
      buf.fillOval(x - w, y - h, 2 * w, 2 * h); // gc を使って○を描く
      if(kill_point > 150){
        y = y + 4*dy; // 座標値の更新 落下してくるタイプ ほぼ倒せない
      }
      else{
        
        y = y + 3*dy; // 座標値の更新
      }
      if (y>apHeight+h)
	hp=0;
    }
  }
  void revive(int apWidth, int apHeight) { // 敵を新たに生成（再利用）
    x = (int)(Math.random()*(apWidth-2*w)+w);
    y = -h;
    dy = 1;
    if (x<apWidth/2)
      dx = (int)(Math.random()*2);
    else
      dx = -(int)(Math.random()*2);
    hp = 100;
  }
}


