import java.awt.*;
class Maru { 
  /*■■■ フィールド変数部 ■■■*/
  int color;   // 色
  int r;       // 半径
  int x, y;    // 位置
  int dx, dy;  // 速度

  /*■■■ コンストラクタ部 ■■■*/
  Maru () { // 引数が無い場合のデフォルト値
    r = 8;
    x = y = 200;
    dx = dy = 2;
  }
  Maru (int r, int x, int y, int dx, int dy){  // 引数がある場合
    this.r = r;
    this.x = x;
    this.y = y;
    this.dx = dx;
    this.dy = dy;
  }

  /*■■■ メソッド部 ■■■*/
  void draw(Graphics g){            // 仮の画用紙 img の GC である img_g が引数
    g.setColor(Color.red);         // 色を設定
    for(int i = 0;i<r+1;i++){
      g.drawOval(x-i, y-i, 2*i, 2*i); // ○の描画
    }
  }
  void update (int width, int height){ // オブジェクトのパラメータの更新
    if (x >= width-r)  dx=dx*(-1);     // 右端に当たったときの処理
    if (x <= r)        dx=dx*(-1);     // 左端に当たったときの処理
    if (y >= height-r) dy=dy*(-1);     // 下端に当たったときの処理
    if (y <= r)        dy=dy*(-1);     // 上端に当たったときの処理
    x=x+dx;                            // x 座標の更新
    y=y+dy;                            // y 座標の更新
  }
}