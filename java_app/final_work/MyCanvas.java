import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.*;
import javax.imageio.*;
import javax.security.sasl.RealmCallback;
import javax.swing.ImageIcon;

class MyCanvas extends Canvas implements MouseListener, MouseMotionListener {
	private int x, y;
	private int px, py;		// preliminary point
	private int ow, oh;		// width and height of the object 
	private int tmp_x, tmp_y;
	int   mode;		        // draw mode associated as below
	int alpha=255;
	
	public int[] xx = new int[100];
	public int[] yy = new int[100];
	public int q=0;
	boolean pr_flag=false; 

	String imgfilename = null;
	private int save_flag=0;
	int[] rgb = new int[5]; //ピクセルのrgbを受け取る配列

	Image offScreen1 = null;//描画用
	Image offScreen2 = null;//描画用
	Image offScreen3 = null;//描画用
	Image offScreen4 = null;//描画用
	Image offScreen = null;//描画用
	BufferedImage write_img = null;//保存用
	BufferedImage read_img = null;//開く用
	Graphics g_os1   = null;
	Graphics g_os2   = null;
	Graphics g_os3   = null;
	Graphics g_os4   = null;
	Graphics g_os   = null;
	Graphics write_pen   = null; //保存用のペン
	Graphics2D l1_pen = null;//開く用のペン
	Graphics2D l2_pen = null;//開く用のペン
	Graphics l3_pen = null;//開く用のペン
	Graphics l4_pen = null;//開く用のペン
	Dimension d;
	Color c =Color.black;
	JColor_Select jcs;
	File_Chooser fc;

	MyCanvas(Oekaki obj){ // constructor
	  //画面と描画可能領域は異アマは同じに設定している。
	  //レイアウトを変更して複雑な色についてはJColorChooserを貼り付けたい
	  d = obj.getSize(); 
	  System.out.println(d.width + " " + d.height);
	  this.setSize(d.width,d.height);		
	  this.setBackground(new Color(255,255,255,255));//透明度だけ設定		
	  addMouseListener(this); 			
	  addMouseMotionListener(this); 
	}

	public void update(Graphics g) {
	  paint(g); //paintComponentにすると色の変化が起きない！？なんで
	}
	
	public void paint(Graphics g) {// paint では随時、暫定的なもの（確定offScreen + 一時的な図）を描く

           ///***リアルタイムで描画される物のための作成***///
	  if (offScreen == null){                  
	    offScreen1 = createImage(d.width, d.height);
    	offScreen2 = createImage(d.width, d.height);
     	offScreen3 = createImage(d.width, d.height);
   		offScreen4 = createImage(d.width, d.height);
		offScreen = offScreen1; //初めはスクリーン１
		///下手にαチャンネルを持たせると良くない。bmp,jpgで読み込めなくなる
		write_img = new BufferedImage(d.width,d.height,BufferedImage.TYPE_3BYTE_BGR);
		read_img = new BufferedImage(d.width,d.height,BufferedImage.TYPE_3BYTE_BGR);
	  }
      if (g_os == null){
		g_os1 = offScreen1.getGraphics();//確定版の鉛筆 画面切り替えの際にnewしてもここでしても同じこと
		g_os2 = offScreen2.getGraphics();//確定版の鉛筆
		g_os3 = offScreen3.getGraphics();//確定版の鉛筆
		g_os4 = offScreen4.getGraphics();//確定版の鉛筆
		g_os = g_os1;
		write_pen = write_img.createGraphics();
	  }
	 
	  switch (mode){
	  case 0:
	  g.drawImage(offScreen, 0, 0, this); // 表にコピー
	  break;
	  case 1:// freehand
		g_os.setColor(c); //確定時にのみ色が変化してしまう！？！？！？！？
		Graphics2D g2=(Graphics2D)g_os;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g_os.drawLine(px, py, x, y);        // 裏に描いて確定して
		g.drawImage(offScreen, 0, 0, this); // 
		break;
	  case 2:// draw line
		g_os.setColor(c); //確定時
		g.setColor(c);//逐次描画用
		g.drawImage(offScreen, 0, 0, this); // 表に裏をコピー, これが余分なのを消してくれている
		g.drawLine(px, py, x, y);           // 表に線を描く*/
		break;
	  case 3:// rect
		g_os.setColor(c); 
		g.setColor(c);//逐次描画用
		g.drawImage(offScreen, 0, 0, this);
		ow = x-px;
		oh = y-py;
		if (x<px){ // もし x が負の方向に動いたら
	  tmp_x=x; // 始点を x（小さい方）に設定
		} else
	  tmp_x=px;// 始点を px（小さい方）に設定
		if (y<py){ // もし y が負の方向に動いたら
	  tmp_y=y; // 始点を y（小さい方）に設定
		} else
	  tmp_y=py;// 始点を py（小さい方）に設定
		g.drawRect(tmp_x, tmp_y, Math.abs(ow), Math.abs(oh)); // 表に暫定的に描く
		break;
	  case 4:// oval
		g_os.setColor(c); //確定時にのみ色が変化してしまう！？！？！？！？
		g.setColor(c);//逐次描画用
		g.drawImage(offScreen, 0, 0, this);
		ow = x-px;
		oh = y-py;
		g.drawOval(px, py, ow, oh);
		break;
	  case 5:// fill oval   
		g_os.setColor(c); //確定時にのみ色が変化してしまう！？！？！？！？
		g.setColor(c);//逐次描画用   
		g.drawImage(offScreen, 0, 0, this); // 表に裏の決定版をコピー
		ow = x-px;                      // 幅を計算
		oh = y-py;                      // 高さを計算
		g.fillOval(px, py, ow, oh);     // 表に暫定的に円を描く
		break;
	  case 6:// fill rect
		g_os.setColor(c); //確定時にのみ色が変化してしまう！？！？！？！？
		g.setColor(c);//逐次描画用
		g.drawImage(offScreen, 0, 0, this);
		ow = x-px;
		oh = y-py;
		g.fillRect(px, py, ow, oh);
		break;
	  case 8:
		g_os.setColor(Color.white);
		g_os.drawLine(px, py, x, y);        // 裏に描いて確定.offScreenには書かれている
		g.drawImage(offScreen, 0, 0, this); // それを表に写す。ただイメージがないためすぐに消える
		break;
	  case 9:
		offScreen = offScreen1; //裏の確定用スクリーンを切り替えて
		g_os = g_os1;//penも更新
		g.drawImage(offScreen,0,0,this);//切り替えたものを表にコピー
		repaint(0,0,d.width,d.height); //これで切り替えをわかりやすくするとめちゃ重くなる、実用性が怪しいレベル
		mode=0;
		break;
	  case 10:
	    offScreen = offScreen2;
		g_os = g_os2;
		g.drawImage(offScreen2,0,0,this);//鉛筆も更新
		repaint(0,0,d.width,d.height);
		mode=0;
		break;
	  case 11:
		offScreen = offScreen3;
		g_os = g_os3;
		g.drawImage(offScreen,0,0,this);//鉛筆も更新
		repaint();
		mode=0;
		break;
	  case 12:
		offScreen = offScreen4;
		g_os = g_os4;
		g.drawImage(offScreen,0,0,this);//鉛筆も更新
		repaint();
		mode=0;
		break;
	  case 13:
	    offScreen = offScreen1;
		g_os = g_os1;
	  //Rasterの設定
		BufferedImage layer1 = new BufferedImage(d.width,d.height,BufferedImage.TYPE_4BYTE_ABGR);
		l1_pen = layer1.createGraphics();
		l1_pen.drawImage(offScreen1,0,0,this);//layer1にオフスクリーン１を書き込む

		BufferedImage layer2 = new BufferedImage(d.width,d.height,BufferedImage.TYPE_4BYTE_ABGR);
		WritableRaster wr = layer2.getRaster(); //Rasterに書き込める
		l2_pen = layer2.createGraphics();
		l2_pen.drawImage(offScreen2,0,0,this);//laeyr2にオフスクリーン2を書き込む
		for(int x=0;x<d.width;x++){
			for(int y = 0;y<d.height;y++){
				wr.getPixel(x,y,rgb);
				if(rgb[0]==255&&rgb[1]==255&&rgb[3]==255){
					rgb[3] = 0; //α値を０にする
					wr.setPixel(x, y, rgb);//更新
				}
				else{ 
					rgb[3] = alpha;
					wr.setPixel(x, y, rgb);//更新
				}
			}
		}
		l1_pen.drawImage(layer2,0,0,this);
		g_os.drawImage(layer1,0,0,this);
		g.drawImage(layer1,0,0,this);
		repaint();
		mode=0;
		break;
	  case 14:
	  //Rasterの設定
	    offScreen = offScreen1;
		g_os = g_os1;

		BufferedImage layer11 = new BufferedImage(d.width,d.height,BufferedImage.TYPE_4BYTE_ABGR);
		l1_pen = layer11.createGraphics();
		l1_pen.drawImage(offScreen1,0,0,this);//layer1にオフスクリーン１を書き込む

		BufferedImage layer3 = new BufferedImage(d.width,d.height,BufferedImage.TYPE_4BYTE_ABGR);
		WritableRaster wr_3 = layer3.getRaster(); //Rasterに書き込める
		l3_pen = layer3.createGraphics();
		l3_pen.drawImage(offScreen3,0,0,this);
		for(int x=0;x<d.width;x++){
			for(int y = 0;y<d.height;y++){
				wr_3.getPixel(x,y,rgb);
				if(rgb[0]==255&&rgb[1]==255&&rgb[3]==255){
					rgb[3] = 0; //α値を０にする
					wr_3.setPixel(x, y, rgb);//更新
				}
				else{ //自分で設定した透過率にできるようにしたい
					rgb[3] = alpha;
					wr_3.setPixel(x, y, rgb);//更新
				}
			}
		}
		l1_pen.drawImage(layer3,0,0,this);
		g_os.drawImage(layer11,0,0,this);
		g.drawImage(layer11,0,0,this);
		repaint();
		mode=0;
		break;

		case 15:
	  //Rasterの設定
		offScreen = offScreen1;
		g_os = g_os1;
		BufferedImage layer111 = new BufferedImage(d.width,d.height,BufferedImage.TYPE_4BYTE_ABGR);
		l1_pen = layer111.createGraphics();
		l1_pen.drawImage(offScreen1,0,0,this);//layer1にオフスクリーン１を書き込む

		BufferedImage layer4 = new BufferedImage(d.width,d.height,BufferedImage.TYPE_4BYTE_ABGR);
		WritableRaster wr_4 = layer4.getRaster(); //Rasterに書き込める
		l4_pen = layer4.createGraphics();
		l4_pen.drawImage(offScreen4,0,0,this);
		for(int x=0;x<d.width;x++){
			for(int y = 0;y<d.height;y++){
				wr_4.getPixel(x,y,rgb);
				if(rgb[0]==255&&rgb[1]==255&&rgb[3]==255){
					rgb[3] = 0; //α値を０にする
					wr_4.setPixel(x, y, rgb);//更新
				}
				else{ //自分で設定した透過率にできるようにしたい
					rgb[3] = alpha;
					wr_4.setPixel(x, y, rgb);//更新
				}
			}
		}
		l1_pen.drawImage(layer4,0,0,this);
		g_os.drawImage(layer111,0,0,this);
		g.drawImage(layer111,0,0,this);
		repaint();
		mode=0;
		break;
	  case 16:
	    g.drawImage(offScreen,0,0,this);
	  case 17:
		g.drawImage(offScreen,0,0,this);
	  case 18:
	    g.drawImage(offScreen,0,0,this);
	  break;
      case 19: //open
		this.setBackground(Color.white);
		fc = new File_Chooser();
		try{
			read_img = ImageIO.read(fc.file);
		}
		catch(Exception e){
			fc.dispose();
		}
		g_os.drawImage(read_img,0,0,this);//offScreenにread_imgを描く
		g.drawImage(offScreen,0,0,this);//表に写す
		repaint(0,0,d.width,d.height); //処理がラグい。要改善
		mode=0;//この行の追加により高速化
		//すぐにほかのモードにいかないと抜けられなくなってしまい永遠にJFileChooserが開く
		break;
		
	  case 20: //save
	  //初回の一度目は名前がないので選択画面になる。しかし二回目以降はファイル選択画面が表示されない
		write_pen.drawImage(offScreen,0,0,this);//写したのであとはiowriteするのみ
		if(save_flag == 0) fc = new File_Chooser();
		try{
			if(fc.file.getName() == null){
				ImageIO.write(write_img, "png", fc.file);//ファイルを選んだらそのまま保存
				ImageIO.write(write_img, "bmp", new File(fc.file.getName()));
				ImageIO.write(write_img, "jpg", new File(fc.file.getName()));
				imgfilename = fc.file.getName();//名前を更新
				save_flag=1;
			}
		 	else if(fc.file.getName() != null){//もし選択せずに名前を付けたら
				ImageIO.write(write_img,"png",new File(fc.file.getName()));
				ImageIO.write(write_img, "bmp", new File(fc.file.getName()));
				ImageIO.write(write_img, "jpg", new File(fc.file.getName()));
				imgfilename = fc.file.getName();//名前を更新
				save_flag=1;
		   }
		   else{
			ImageIO.write(write_img, "bmp", new File(fc.file.getName()));
			ImageIO.write(write_img,"png",new File(fc.file.getName()));
			ImageIO.write(write_img, "jpg", new File(fc.file.getName()));
		   }
		}
		catch(Exception e) {
			fc.dispose();//windowを破棄
		}
		mode = 0;
		break;

	  case 21:  //save as
		write_pen.drawImage(offScreen,0,0,this);//写したのであとはiowriteするのみ
		fc = new File_Chooser();
		try{
			ImageIO.write(write_img, "bmp", new File(fc.file.getName()));
			ImageIO.write(write_img, "png", new File(fc.file.getName()));
			ImageIO.write(write_img, "jpg", new File(fc.file.getName()));
		   }
		catch(Exception e) {
			fc.dispose();
		   }
		mode=1;
		break;
	  case 22: //save as gif
		BufferedImage gif_screen = new BufferedImage(d.width,d.height,BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D gif_pen = gif_screen.createGraphics();
		WritableRaster gif_wr = gif_screen.getRaster();
		gif_pen.drawImage(offScreen,0,0,this);//layer1にオフスクリーン１を書き込む
		if(pr_flag == true){
			init(xx,yy);
			pr_flag=false;
		}
		for(int x=0;x<d.width;x++){//読み込む画像のピクセル全てを調べていく
			for(int y = 0;y<d.height;y++){
				//透過するためにある程度白っぽいところを判定する。そこは完全に透過。
				//一方で保護するところは前もって決めておく
				gif_wr.getPixel(x,y,rgb);
				if(rgb[0] <= 255 && rgb[0] >= 220 && rgb[1] <= 255 && rgb[2] >= 220 && rgb[3] <= 255 && rgb[3] >= 220){
					if(x < Math.max(xx[0],xx[1]) && x > Math.min(xx[0],xx[1]) && y < Math.max(yy[0],yy[1]) && y > Math.min(yy[0],yy[1])){}
					else if(x < Math.max(xx[2],xx[3]) && x > Math.min(xx[2],xx[3]) &&  y < Math.max(yy[2],yy[3]) && y > Math.min(yy[2],yy[3])){}
					else if(x < Math.max(xx[4],xx[5]) && x > Math.min(xx[4],xx[5]) &&  y < Math.max(yy[4],yy[5]) && y > Math.min(yy[4],yy[5])){}
					else if(x < Math.max(xx[6],xx[7]) && x > Math.min(xx[6],xx[7]) &&  y < Math.max(yy[6],yy[7]) && y > Math.min(yy[6],yy[7])){}
					else if(x < Math.max(xx[8],xx[9]) && x > Math.min(xx[8],xx[9]) &&  y < Math.max(yy[8],yy[9]) && y > Math.min(yy[8],yy[9])){}
					else if(x < Math.max(xx[10],xx[11]) && x > Math.min(xx[10],xx[11]) &&  y < Math.max(yy[10],yy[11]) && y > Math.min(yy[10],yy[11])){}
					else{
						rgb[3] = 0; //α値を０にする
						gif_wr.setPixel(x, y, rgb);//更新
					}
				}
			}
		}
		fc = new File_Chooser();
		try{
			ImageIO.write(gif_screen, "gif", new File(fc.file.getName()));
		   }
		catch(Exception e) {
			fc.dispose();
		   }
		mode=0;
		pr_flag = true;
		break;
	  case 23:
		g.setColor(Color.white);
		g.drawImage(offScreen, 0, 0, this);
		ow = x-px;
		oh = y-py;
		g.drawRect(px, py, ow, oh);
		g.setColor(c);
		break;
	}
	}
	

	/////////////////////////////////////////////////
	// 以下、マウス操作に対する動作
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){
	  switch (mode){
	  case 23:
		point_cal(e.getX(),e.getY());
		px = e.getX();
		py = e.getY();
		System.out.println("始点　(x,y)" + "("+ e.getX() + "," + e.getY() + ")" );
	    break;
	  case 1:// freehand
	  case 8:
		x = e.getX();
		y = e.getY();
		break;
	  case 2:// draw line
	  case 3:// rect
	  case 4:// fill rect
	  case 5:// oval
	  case 6:// fill oval
		px = e.getX();
		py = e.getY();
		break;
	case 16:
		x = e.getX();
		y = e.getY();
		BufferedImage icon_buf1 = new BufferedImage(d.width,d.height,BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D icon_gra1 = icon_buf1.createGraphics();
		ImageIcon imgicon1  = new ImageIcon("inu.png");
		Image image1 = imgicon1.getImage();
		Image newImage1 = image1.getScaledInstance(100,100, 1);
		imgicon1 = new ImageIcon(newImage1);
		g_os.drawImage(newImage1,x,y,this);
		repaint();
		break;
		
	case 17:
		x = e.getX();
		y = e.getY();
		BufferedImage icon_buf2 = new BufferedImage(d.width,d.height,BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D icon_gra2 = icon_buf2.createGraphics();
		ImageIcon imgicon2  = new ImageIcon("ha-to.png");
		Image image2 = imgicon2.getImage();
		Image newImage2 = image2.getScaledInstance(100,100, 1);
		imgicon2 = new ImageIcon(newImage2);
		g_os.drawImage(newImage2,x,y,this);
		repaint();
		break;
	case 18:
		x = e.getX();
		y = e.getY();
		BufferedImage icon_buf3 = new BufferedImage(d.width,d.height,BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D icon_gra3 = icon_buf3.createGraphics();
		ImageIcon imgicon3  = new ImageIcon("Pikachu.gif");
		Image image3 = imgicon3.getImage();
		Image newImage3 = image3.getScaledInstance(100,100, 8);
		imgicon3 = new ImageIcon(newImage3);
		g_os.drawImage(newImage3,x,y,this);
		repaint();
		break;
	  }
	}
	public void mouseReleased(MouseEvent e){ // fix the image
	switch (mode){
	case 23:
		point_cal(e.getX(),e.getY());
		x = e.getX();
		y = e.getY();
		ow = x-px;
		oh = y-py;
		repaint();
		System.out.println("終点　(x,y)" + "("+ e.getX() + "," + e.getY() + ")" );
	break;
	  case 2:// draw line
	  x = e.getX();
		y = e.getY();
		g_os.drawLine(px, py, x, y); //offScreenに確定させる
		repaint();
		break;
	  case 3:// rect
		x = e.getX();
		y = e.getY();
		ow = x-px;
		oh = y-py;
		if (x<px){ // もし x が負の方向に動いたら
	  tmp_x=x; // 始点を x（小さい方）に設定
		} else
	  tmp_x=px;// 始点を px（小さい方）に設定
		if (y<py){ // もし y が負の方向に動いたら
	  tmp_y=y; // 始点を y（小さい方）に設定
		} else
	  tmp_y=py;// 始点を py（小さい方）に設定
		g_os.drawRect(tmp_x, tmp_y, Math.abs(ow), Math.abs(oh)); // 表に暫定的に描く
		repaint();
		break;
	  case 4:// fill rect
		x = e.getX();
		y = e.getY();
		ow = x-px;
		oh = y-py;
		g_os.drawOval(px, py, ow, oh); // offScrean に確定画像を描く
		repaint();
		break;
	  case 5:// filloval
		x = e.getX();
		y = e.getY();
		ow = x-px;
		oh = y-py;
		g_os.fillOval(px, py, ow, oh); // 裏に確定画像を描く
		repaint();
		break;
	  case 6:// fill rect
		x = e.getX();
		y = e.getY();
		ow = x-px;
		oh = y-py;
		g_os.fillRect(px, py, ow, oh); // 裏に確定画像を描く
		repaint();
		break;
	
	  }
	}
	public void mouseDragged(MouseEvent e){
	  switch (mode){
	  case 1:  // free hand
	  case 8:// eraser
		px = x;
		py = y;
		x = e.getX();
		y = e.getY();
		repaint();	// call paint(); every moving
		break;
	  case 2:// draw line
	  case 3:// rect
	  case 4:
	  case 5:
	  case 6:
	  case 23:
		x = e.getX();
		y = e.getY();
		repaint();// call paint(); every moving
		break;
	  }
	}
	public void mouseMoved(MouseEvent e){}// do nothing

	public void clear(){
		g_os.setColor(Color.white);
		g_os.fillRect(0,0,d.width,d.height);
		repaint();
		g_os.setColor(c);
	}

	public void point_cal(int x,int y){
		 xx[q] = x;
		 yy[q] = y;
		 q++;
		if(q==99){
			for(int j = 0;j<100;j++){
				xx[j]=0; yy[j]=0;
				q=0;
			}
		}
	}
	public void init(int[] x, int[] y){
		for(int i=0;i<100;i++){
			x[i] = y[i] = 0;
		}
	}
}
  