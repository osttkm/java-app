import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
/*メインのclass
 *
 */
public class Oekaki {
    public static void main(String[] args)
    {
        JFrame.setDefaultLookAndFeelDecorated(true);//ロックアンドフィール設定
        final Jframe body = new Jframe();
        body.setVisible(true);//可視化
        body.setBackground(new Color(0,0,0,1));//透明度だけ設定
    }
}
/*本体のclass
 *
 */

class Jframe extends JFrame implements ActionListener{
    private final GPanel evPanel;
    public Jframe(){
        /*基本設定*/
        super("お絵かき");
        this.setSize(700, 600);//フレーム作り
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//終了設定
        evPanel= new GPanel();
        this.add( evPanel, BorderLayout.CENTER);//絵を書くイベントを真ん中に配置
        create_button();//ボタンを右側に配置。
        this.setBackground(new Color(0,0,0,1));//後ろを透明に
    }

    /*パネル作り*/
    void create_button(){
        final JPanel right_panel = new JPanel();//右の空間作り
        final JButton all_delete = new JButton("消す");//消去ボタン
        final JButton red_button = new JButton("赤");
        final JButton break_button = new JButton("黒");
        final JButton white_button = new JButton("白");
        all_delete.addActionListener(this);//消去イベント追加
        all_delete.setActionCommand("all_delete");//イベントに渡すための名前
        red_button.addActionListener(this);//赤にするイベント追加
        red_button.setActionCommand("red");//イベントに渡すための名前
        break_button.addActionListener(this);//赤にするイベント追加
        break_button.setActionCommand("break");//イベントに渡すための名前
        white_button.addActionListener(this);//赤にするイベント追加
        white_button.setActionCommand("white");//イベントに渡すための名前
        right_panel.setLayout(new GridLayout(4,1));//レイアウトマネージャー設定
        //すべてのボタンの配置　
        right_panel.add(all_delete);//消すボタン配置
        right_panel.add(red_button);//赤のボタンを配置
        right_panel.add(break_button);//黒のボタンを配置
        right_panel.add(white_button);//白のボタンを配置
        this.getContentPane().add(right_panel,BorderLayout.EAST);//パネルを右に配置
    }
    /*ボタンイベント作成*/
    public void actionPerformed(ActionEvent e)
    {
        //System.out.println("ボタン押されたよ");//確認用
        evPanel.ovarall_x(getSize().width);//フレームのサイズを取得
        evPanel.ovarall_y(getSize().height);
        String event_name = e.getActionCommand();//飛んできたイベントのオブジェクトを取得

        if(event_name.equals("all_delete"))//飛んできた名前のオブジェクトと比較
        {
            evPanel.if_root();//flagをfalseにする
        }
        if(event_name.equals("red"))//飛んできた名前のオブジェクトと比較
        {               evPanel.color_change_red();//色を赤にする
        }
        if(event_name.equals("break"))//飛んできた名前のオブジェクトと比較
        {
            evPanel.color_change_break();//色を黒にする
        }
        if(event_name.equals("white"))//飛んできた名前のオブジェクトと比較
        {
            evPanel.color_change_white();//色を白にする
        }
        evPanel.repaint();//リロード
        repaint();//リロード
    }
}

/*イベントを管理してるclass
 *
 */
class  GPanel extends  JPanel implements MouseListener, MouseMotionListener{
    private int move_x = -100;//移動距離
    private int move_y = -100;
    private int start_x = -100;//スタート地点座標
    private int start_y = -100;
    static boolean flag = false;
    BasicStroke BStroke = new BasicStroke(5.0f);//筆の大きさ設定
    private int framex = 1000;//基礎の大きさ
    private int framey = 1000;
    private int iroiro = 0;//色を変更する番号
    BufferedImage seve_field = new BufferedImage(framex,framey, BufferedImage.TYPE_4BYTE_ABGR);//メモリの確保
    public GPanel()
    {
        addMouseListener(this);//イベントの具現化
        addMouseMotionListener(this);//イベントの具現化

    }
    /*グラフィック描写*/
    @Override
    public void paintComponent(Graphics g)
    {
        if(flag){

            seve_field = new BufferedImage(framex, framey, BufferedImage.TYPE_4BYTE_ABGR);//メモリ張替え
            return ;

        }else{
            Graphics2D g2= (Graphics2D)g;//仮線
            g2.setStroke(BStroke);//筆の大きさを設定
            g2.setColor(Color.BLACK);//色設定
            Graphics seve = seve_field.createGraphics();//メモリへの窓口
            Graphics2D seve2= (Graphics2D)seve;//メモリ加工
            seve2.setStroke(BStroke);//筆の大きさを設定
            seve2.setColor(Color.BLACK);//色設定
            if(iroiro ==1){
                g2.setColor(Color.RED);//色設定
                seve2.setColor(Color.RED);//色設定
            }
            if(iroiro ==2){
                g2.setColor(Color.WHITE);//色設定
                seve2.setColor(Color.WHITE);//色設定
            }
            if(start_x==-100||start_y== -100||move_x==-100||move_y==-100)
            {
                move_x  = move_y = start_x = start_y = -100;
            }
            g.drawImage(seve_field,0,0,this);//g2の中身をメモリの大きさだけゲット
            seve2.drawLine(start_x, start_y, move_x, move_y);//線を引く
            g2.drawLine(start_x, start_y, move_x, move_y);
            start_x = move_x;
            start_y = move_y;
        }
    }


    /*if判定の関数*/
    public boolean if_root(){
        move_x  = move_y = start_x = start_y = -100;
        flag = true;
        return flag;
    }
    /*色変更関数*/
    //赤色に変更
    public void color_change_red(){
        iroiro=1;
    }
    //黒色に変更
    public void color_change_break(){
        iroiro=0;
    }
    //白色に変更
    public void color_change_white(){
        iroiro=2;
    }
    public void  ovarall_x(int x){//フレームの大きさを取得
        framex = x;
    }
    public void  ovarall_y(int y){
        framey = y;
    }
    @Override
    /*押してる間*/
    public void mousePressed(MouseEvent e)
    {
        /*初期位置の保存*/
        start_x = move_x = e.getX();
        start_y = move_y = e.getY();
        flag = false;
    }
    @Override
    /*離した時*/
    public void mouseReleased(MouseEvent e)
    {

        move_x  = move_y = start_x = start_y = -100;
    }
    @Override
    /*ドラックされたとき*/
    public void mouseDragged(MouseEvent e)
    {
        move_x = e.getX();
        move_y = e.getY();
        repaint();
    }
    @Override
    /*クリックされたとき(二つで一つ）*/
    public void mouseClicked(MouseEvent e)
    {
    }
    @Override
    /*マウスが入ったとき*/
    public void mouseEntered(MouseEvent e)
    {
    }
    @Override
    /*マウスが出たとき*/
    public void mouseExited(MouseEvent e)
    {
    }
    @Override
    /*ボタンを押さずに乗せたとき*/
    public void mouseMoved(MouseEvent e)
    {
    }
}