public class shape{
    int color;//図形の色、速さの基本構造
    int dx,dy;

class Shikaku extends shape{
    int x,y; //辺の座標
}
class Maru extends shape{
    Point2D center; //中心座標
    int r; //半径
}
}
