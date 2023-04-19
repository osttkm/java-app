import java.awt.*;
import java.util.Random;
class Mid_Boss extends MovingObject {

  // コンストラクタ(初期値設定)
  Mid_Boss(int apWidth, int apHeight) {
    super(apWidth, apHeight); // スーパークラス(ObjBase)のコンストラクタの呼び出し
    w = 40;
    h = 40;
    hp = 0;  // 初期状態では全て死亡
    point = 500;
  }
  
  // ○を描き更新するメソッド
  void move(Graphics buf, int apWidth, int apHeight,int kill_point) {
    r=(int)(Math.random()*256);
    g=(int)(Math.random()*256);
    b=(int)(Math.random()*256);
    Color color1 = new Color(r,g,b);
    Color color2 = new Color(r,g,0);
    Color color3 = new Color(r,0,0);

      if(hp<=100 && hp > 70){
        buf.setColor(color1); // gc の色はランダムに
        buf.fillOval(x - w, y - h, 2 * w, 2 * h); // gc を使って○を描く
        x = x + dx; // 座標値の更新
        y = y + dy; // 座標値の更新
      }
      else if(hp>10 && hp<=70){ //hpが七割を切ると二倍速
        buf.setColor(color2); // gc の色はランダムに
      buf.fillOval(x - w, y - h, 2 * w, 2 * h); // gc を使って○を描く
        x = x + 2*dx; // 座標値の更新
        y = y + 2*dy; // 座標値の更新
      }
      else{
        buf.setColor(color3); // gc の色はランダムに
        buf.fillOval(x - w, y - h, 2 * w, 2 * h); // gc を使って○を描く
        x = x + 2*dx; // 座標値の更新
        y = y + 2*dy; // 座標値の更新
      }
      //壁に衝突したときの判定
      if (x >= 640)  dx=dx*(-1);     // 右端に当たったときの処理
      if (x <= 0)      dx=dx*(-1);     // 左端に当たったときの処理
      if (y >= 480) dy=dy*(-1);     // 下端に当たったときの処理
      if (y <= 0)      dy=dy*(-1);     // 上端に当たったときの処理
    }
    void revive(int apWidth, int apHeight) { // 敵を新たに生成（再利用）
        hp = 100;
        this.x = x;
        this.y = y;
        this.dx = dx_Boss;
        this.dy = dy_Boss;
      }
    
}
  



