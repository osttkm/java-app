/**
 * ゲームの進行自体を取り仕切るクラス
 * ・キーボード入力
 * ・ゲーム内の各オブジェクトの管理
 * ・ゲーム画面の描画
 * 
 * @author fukai
 */
import java.awt.*;
import java.awt.event.*;

public class GameMaster extends Canvas implements KeyListener {
  // ■ フィールド変数
  Image        buf;   // 仮の画面としての buffer に使うオブジェクト(Image クラス)
  Graphics     buf_gc;// buffer のグラフィックスコンテキスト (gc) 用オブジェクト
  Dimension    d;     // アプレットの大きさを管理するオブジェクト
  private int  imgW, imgH; // キャンバスの大きさ

  private int enmyAnum  = 20; // 敵Ａの数
  private int enmyBnum  = 20; // 敵Ｂの数
  private int enmynum = 20;
  private int ftrBltNum = 10; // 自機の弾の数
  private int ftrBlt_rNum = 10; // 自機の弾の数
  private int mode      = -1; // -1: タイトル画面，-2: ゲームオーバー，1〜 ゲームステージ
  private int i, j;
  public int kill_point = 0; //敵の撃破数 弾で撃破した場合のみカウント
  private int boss_flag = -1; //reviveを使うための変数
  private int mode4_flag = 0; //一度だけ画面が表示されるための変数
  private int revive_flag = 0; //一度だけ復活するための変数
  Fighter       ftr;  // 自機
  FighterBullet ftrBlt[] = new FighterBullet[ftrBltNum]; // 自機の弾
  MovingObject object[] = new MovingObject[enmynum];
  MovingObject mid_boss;

  
  // ■ コンストラクタ
  /**
   * ゲームの初期設定
   * ・描画領域(Image)とGC(Graphics)の作成
   * ・敵，自機，弾オブジェクトの作成
   */
  Picture picture = new Picture();

  GameMaster(int imgW, int imgH) { // コンストラクタ （アプレット本体が引数． ゲームの初期化を行う）
    this.imgW = imgW; // 引数として取得した描画領域のサイズをローカルなプライベート変数に代入
    this.imgH = imgH; // 引数として取得した描画領域のサイズをローカルなプライベート変数に代入
    setSize(imgW, imgH); // 描画領域のサイズを設定

    addKeyListener(this);

    
    ftr = new Fighter(imgW, imgH); // 自機のオブジェクトを実際に作成
    for (i = 0; i < ftrBltNum; i++)       // 自機弾のオブジェクトを実際に作成
      ftrBlt[i] = new FighterBullet();
    for(i = 0; i< enmynum; i++)
      if(i % 4 == 0){
        object[i] = new EnemyA(imgW, imgH);
      }
      else if(i % 4 == 1){
        object[i] = new EnemyB(imgW, imgH);
      }
      else if(i % 4 == 2){
        object[i] = new EnemyC(imgW,imgH);
      }
      else{
        object[i] = new EnemyD(imgW,imgH);
      }

      mid_boss = new Mid_Boss(imgW,imgH);
      
  }

  // ■ メソッド
  // コンストラクタ内で createImage を行うと peer の関連で 
  // nullpointer exception が返ってくる問題を回避するために必要
  public void addNotify(){
    super.addNotify();
    buf = createImage(imgW, imgH); // buffer を画面と同サイズで作成
    buf_gc = buf.getGraphics();
  }

  // ■ メソッド (Canvas)
  public void paint(Graphics g) {
    buf_gc.setColor(Color.white);      // gc の色を白に
    buf_gc.fillRect(0, 0, imgW, imgH); // gc を使って白の四角を描く（背景の初期化）
    switch (mode) {
    case -3: //ゲームクリア
    buf_gc.setColor(Color.black); 
    buf_gc.drawString("      == Game Clear ==      ", imgW/2-80, imgH/2-20);
    buf_gc.drawString("　　　総合撃破得点:" + kill_point, imgW/2-80, imgH/2+20);
    break;
    case -2: // 
      buf_gc.setColor(Color.black); // ゲームオーバー画面を描く
      buf_gc.drawString("      == Game over ==      ", imgW/2-80, imgH/2-20);
      buf_gc.drawString("       Hit SPACE key       ", imgW/2-80, imgH/2+20);
      buf_gc.drawString("　　　　総合撃破得点　　　　　" + kill_point,imgW/2-80, imgH/2+30);
      break;

    case -1: // タイトル画面（スペースキーを押されたらゲーム開始）
      buf_gc.setColor(Color.black); // タイトル画面を描く
      buf_gc.drawString(" == Shooting Game Title == ", imgW/2-80, imgH/2-20);
      buf_gc.drawString("Hit SPACE bar to start game", imgW/2-80, imgH/2+20);
      break;
    
    case -4: //bossmode
      //buf_gc.setColor(Color.black); // タイトル画面を描く
      picture.cGetImage1(buf_gc);
      picture.cDraw(buf_gc);
      buf_gc.drawString(" == Boss is coming. Are you ready? == ", imgW/2-80, imgH/2-20);
      buf_gc.drawString(" Hit SPACE bar for last battle", imgW/2-80, imgH/2+20);
      break;

    default: //ゲーム中
    

      

                //**普通の戦闘 **//
    

      // *** ランダムに敵を生成 *** 
      makeEnmy: if (Math.random() < 0.2) { // １０％の確率で一匹生成
	      for (i = 0; i < enmynum; i++) {
	        if (object[i].hp == 0) {
	          object[i].revive(imgW, imgH);
	          break makeEnmy;
	        } 
        }  
      }
      if(mid_boss.hp == 0 && revive_flag != 1){
        mid_boss.revive(imgW,imgH);
        revive_flag = 1;
      }
      
      if(kill_point >= 200 && mode4_flag != 1){
        mode = -4;
        mode4_flag = 1; //一度だけモード１の画面を表示
      }

      // *** 自分の弾を発射 ***
      if (ftr.sflag == true && ftr.delaytime == 0) { // もしスペースキーが押されていて＆待ち時間がゼロなら
	for (i = 0; i < ftrBltNum; i++) {      // 全部の弾に関して前から探査して
	  if (ftrBlt[i].hp == 0) {             // 非アクティブの（死んでいる）弾があれば
      ftrBlt[i].revive(ftr.x, ftr.y);    // 自機から弾を発射して，
	    ftr.delaytime = 5;                 // 自機の弾発射待ち時間を元に戻して，
	    break;                             // for loop を抜ける
	  }
	}
      } else if (ftr.delaytime > 0)            // 弾を発射しない(出来ない)場合は
	ftr.delaytime--;                       // 待ち時間を１減らす

      // *** 各オブジェクト間の衝突チェック ***/
  for (i = 0; i < enmynum; i++)           // すべての敵に関し，
	  if (object[i].hp > 0) {                 // 敵が生きていたら
	    ftr.collisionCheck(object[i]);        // 自機と衝突チェック
	    for (j = 0; j < ftrBltNum; j++)      // 全ての自弾に関して
	      if (ftrBlt[j].hp > 0)              // 自弾が生きていたら
          ftrBlt[j].collisionCheck(object[i]); // 自弾との衝突チェック
          //picture.cGetImage4(buf_gc);
          //picture.cDraw2(buf_gc,ftrBlt[j].x,ftrBlt[j].y);
        if(object[i].hp == 0){
          kill_point += object[i].point;
        }
  }
   //ボスに関する判定
   if(kill_point >= 200){
    if(mid_boss.hp >= 0){
      ftr.collisionCheck(mid_boss); //自機との衝突チェック
      for (j = 0; j < ftrBltNum; j++)      // 全ての自弾に関して
        if (ftrBlt[j].hp > 0)              // 自弾が生きていたら
          ftrBlt[j].collisionCheck(mid_boss); // 自弾との衝突チェック
          if(mid_boss.hp == 0){
          boss_flag++;
          }
    }
  }

    //**勝利判定**//
    if(boss_flag == 1){
      mode = -3;
    }            
      // *** 自機の生死を判断 ***
      if (ftr.hp < 1)
        mode = -2; // ゲーム終了
        
      // *** オブジェクトの描画＆移動 ***
      buf_gc.setColor(Color.white); // タイトル画面を描く
        picture.cGetImage2(buf_gc); //背景の描画の描画
        picture.cDraw(buf_gc);
        for(j = 0;i<ftrBltNum;j++){
        picture.cGetImage4(buf_gc);
        picture.cDraw2(buf_gc,ftrBlt[j].x,ftrBlt[j].y);
        }
        buf_gc.drawString("Hit Point" + ftr.hp, imgW - 150 , imgH - 100);
        buf_gc.drawString("サークル:1point", imgW - 150 , imgH - 110);
        buf_gc.drawString("カラーサークル:100point", imgW - 150 , imgH - 120);
        buf_gc.drawString("スクエア:1point", imgW - 150 , imgH - 130);
        buf_gc.drawString("カラースクエア:5point", imgW - 150 , imgH - 140);
        buf_gc.drawString("Now SCORE" + kill_point, imgW - 150 , imgH - 150);
        buf_gc.drawString("SCORE 300up???  400up???", imgW - 150 , imgH - 160);
        if(kill_point<200){
        buf_gc.drawString("Dabger:????", imgW - 150 , imgH - 170);
        }
        if(kill_point >200){ //ボス戦以降HPを表示する
          buf_gc.drawString("ボスHP" + mid_boss.hp, imgW - 150 , imgH - 170);
        }


      for (i = 0; i < ftrBltNum; i++)
        ftrBlt[i].move(buf_gc, imgW, imgH,kill_point);
        ftr.move(buf_gc, imgW, imgH,kill_point);
      for (i = 0; i < enmynum; i++)
        object[i].move(buf_gc, imgW, imgH,kill_point);

       //ボスの移動
       if(kill_point >= 200){
       mid_boss.move(buf_gc, imgW, imgH,kill_point);
       }
   

      // 状態チェック
      for (i = 0; i < enmynum; i++) {
  System.out.print(object[i].hp + " ");
      }
      System.out.println("");
    }
    g.drawImage(buf, 0, 0, this); // 表の画用紙に裏の画用紙 (buffer) の内容を貼り付ける
  }

  // ■ メソッド (Canvas)
  public void update(Graphics gc) { // repaint() に呼ばれる
    paint(gc);
  }

  // ■ メソッド (KeyListener)
  public void keyTyped(KeyEvent ke) {
  } // 今回は使わないが実装は必要

  public void keyPressed(KeyEvent ke) {
    int cd = ke.getKeyCode();
    switch (cd) {
    case KeyEvent.VK_LEFT: // [←]キーが押されたら
      ftr.lflag = true; // フラグを立てる
      break;
    case KeyEvent.VK_RIGHT: // [→]キーが押されたら
      ftr.rflag = true; // フラグを立てる
      break;
    case KeyEvent.VK_UP: // [↑]キーが押されたら
      ftr.uflag = true; // フラグを立てる
      break;
    case KeyEvent.VK_DOWN: // [↓]キーが押されたら
      ftr.dflag = true; // フラグを立てる
      break;
    case KeyEvent.VK_SPACE: // スペースキーが押されたら
      ftr.sflag = true; // フラグを立てる
      if(this.mode == -3){
        this.mode = -1;
      }
      if(this.mode == -2){
        this.mode = -1;
      }
      if(this.mode == -1){
        this.mode = 0;
      }
      if(this.mode == -4){
        this.mode = 0;
      }
      ftr.hp = 10;
      break;
    }
  }

  // ■ メソッド (KeyListener)
  public void keyReleased(KeyEvent ke) {
    int cd = ke.getKeyCode();
    switch (cd) {
    case KeyEvent.VK_LEFT: // [←]キーが離されたら
      ftr.lflag = false; // フラグを降ろす
      break;
    case KeyEvent.VK_RIGHT: // [→]キーが離されたら
      ftr.rflag = false; // フラグを降ろす
      break;
    case KeyEvent.VK_UP: // [↑]キーが離されたら
      ftr.uflag = false; // フラグを降ろす
      break;
    case KeyEvent.VK_DOWN: // [↓]キーが離されたら
      ftr.dflag = false; // フラグを降ろす
      break;
    case KeyEvent.VK_SPACE: // スペースキーが離されたら
      ftr.sflag = false; // フラグを降ろす
      break;
    }
  }
}

