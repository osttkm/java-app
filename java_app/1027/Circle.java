import java.awt.*;
public class Circle extends Shape{
    ////***コンストラクタ部***////
    Circle(int x,int y,int radious,int dx,int dy){
        this.x = x;
        this.y = y;
        this.r = radious;
        this.dx = dx;
        this.dy = dy;
    }
    Circle(){
        this.x = 30;
        this.y = 30;
        this.r = 30;
        this.dx = this.dy = 10;
    }
    /*■■■ メソッド部 ■■■*/
  void draw(Graphics gc){ // 仮の画用紙 img の GC が引数
    gc.setColor(Color.red); // 色を設定
    gc.drawOval(x-r, y-r, 2*r, 2*r); // ○の描画
    gc.fillOval(x-r,y-r,2*y,2*r);
  }
  void update (int width, int height){ // オブジェクトのパラメータの更新
    if (x >= width)  dx=dx*(-1);     // 右端に当たったときの処理
    if (x <= 0)      dx=dx*(-1);     // 左端に当たったときの処理
    if (y >= height) dy=dy*(-1);     // 下端に当たったときの処理
    if (y <= 0)      dy=dy*(-1);     // 上端に当たったときの処理
    x=x+dx;                          // x 座標の更新
    y=y+dy;                          // y 座標の更新
  }
    
}