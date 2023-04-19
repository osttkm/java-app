import java.awt.*;
abstract class Shape{
    int dx,dy;//速さ
    int x,y,r; //図形の中心座標と半径 
    int yoko,tate;//長方形の辺の長さ
    int widht,height;
    //表示に関するメソッド
    abstract void draw(Graphics gc); //abstracyで引数がある場合は書かなければならな
    //壁に当たった時の更新メソッド
    abstract void update(int width,int height);
    //色の塗りつぶしに関するメソッド
    //abstract void paint();
}
