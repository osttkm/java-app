import java.awt.*;
    class Square extends Shape{
    
        Square(int x,int y,int tate,int yoko){
            this.x = x; this.y = y;
            this.tate = tate; this.yoko = yoko;
        }
        Square(){
            this.x = 50; 
            this.y = 50; 
            this.dx = this.dy = 4;
            this.tate = this.yoko =  40; 
        }
        void draw(Graphics gc){
            gc.setColor(Color.red);
            gc.drawRect(x,y,yoko,tate);
            
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

