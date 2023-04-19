import java.awt.*;
    class Color_Square extends Shape{
        Color_Square(int x,int y,int tate,int yoko,int dx,int dy){
            this.x = x; this.y = y;
            this.tate = tate; this.yoko = yoko;
            this.dx = dx; this.dy = dy;
        }
        Color_Square(){
            this.x = 50; 
            this.y = 50; 
            this.dx = this.dy = 4;
            this.tate = this.yoko =  40; 
        }
        void draw(Graphics gc){
            gc.setColor(Color.blue);
            gc.drawRect(x,y,yoko,tate);
            gc.fillRect(x,y,yoko,tate); //塗りつぶしの機能
        }
        void update(int widht,int height){
            if(x<=0)        dx=dx*(-1);
            if(x>=widht)    dx=dx*(-1);
            if(y<=0)        dy=dy*(-1);
            if(y>=height)   dy=dy*(-1);
            x = x + dx;
            y = y + dy;
        }
    }

