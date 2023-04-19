import java.awt.*;
import java.awt.event.*;
class Picture extends Frame{
    Picture(){}
    Image cimg; // キャラクタ画像
    // ■ メソッド
  // 画像をファイルから cimg に読み込む
    void cGetImage1 (Graphics buf_gc){
      cimg = getToolkit().getImage("img/emergency.jpg");
    }
    void cGetImage2 (Graphics buf_gc){
        cimg = getToolkit().getImage("img/space.jpg");
      }
    void cGetImage3 (Graphics buf_gc){
      cimg = getToolkit().getImage("img/fighter.jpg");
    }
    void cGetImage4 (Graphics buf_gc){
      cimg = getToolkit().getImage("img/bakuhatu.jpg");
    }
  // ■ メソッド
  // 画像を切り取り、描画する
  void cDraw (Graphics g){
    int imgx, imgy;
    imgx = 640;
    imgy = 480; // 画像の切り取り場所 (y座標)
    g.drawImage(cimg,0,0,640,480,this); 
  }
  void cDraw2(Graphics g,int x,int y){
      g.drawImage(cimg,x,y,30,30,this);
  }
  } 