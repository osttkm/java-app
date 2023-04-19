package application;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent ;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
/** 
*<p>画像を読み込むもしくはpngで保存し好きな色で自由な線が描けるプログラム</p>
*@author pyridoxin
*@version 1.0
*@see java.awt.* 
*@see java.io.*
*@see javax.swing.*
*@see javax.imageio.ImageIO
 */

public class MousePaint extends JFrame implements ActionListener,ChangeListener{
    private static final long serialVersionUID = 1L;
    private final JMenu menu;
    private BufferedImage img;
    private final MousePaintcore mpc ;
    private final JColorChooser colorchooser;
    private Color color;
	private final JDesktopPane desktop;
	private final JInternalFrame PaintSpace,ColorChooser,PenSize;
	private JSlider PenSizeSlider;
	private JSlider makeSlider(final int min,final int max,final int value){
		JSlider s =new JSlider(min,max,value) ;	
		s.setMajorTickSpacing(10);
		s.setMinorTickSpacing(1);
		s. setPaintTicks(true);
		return s;
	}
    private JDesktopPane makeDesktopPane(){
    	JDesktopPane dkp =new JDesktopPane();
    	dkp.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
    	return dkp;
    }
    private JMenu makeMenu(String s){
    	JMenu m =new JMenu(s);
    	return m;
    }
    private JInternalFrame makeInternalFrame(final String s,final JComponent j,final JDesktopPane dp){
		 JInternalFrame jif = new JInternalFrame(s);
		 jif.add(j);
		 jif.setVisible(true);
		 jif.setMaximizable(true);
		 jif.setIconifiable(true);
		 jif.setResizable(true);
		 dp.add(jif);
		 return jif;
	}
    private JMenuItem makeJMenuItem(final String s,final String Stroke,final JMenu m){
    	JMenuItem mi = new JMenuItem(s);
    	mi.setAccelerator(KeyStroke.getKeyStroke(Stroke));
    	mi.addActionListener(this);
    	mi.setActionCommand(s);
    	m.add(mi);
    	return mi;
    }
    private JMenuBar makeMenuBar(final JMenu m){
    	JMenuBar mb =new JMenuBar();
    	mb.add(m);
    	return mb;
    }
    private JColorChooser makeColorChooser(){
    	JColorChooser c=new JColorChooser();
    	c.getSelectionModel().addChangeListener(this);
    	return c;
    }
    public  MousePaint(){
    	super("PaintEditor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,600,600);
        setPreferredSize(new Dimension(715,700));
        setBackground(Color.white);
        //DesktopPane
        setContentPane(desktop =makeDesktopPane());
        PaintSpace= makeInternalFrame("PaintSpace",mpc = new MousePaintcore(),desktop);
        PaintSpace.setSize(300,300);
        PaintSpace.setBackground(Color.white);
        PaintSpace.setLocation(0, 0);
        ColorChooser = makeInternalFrame("ColorChooser",colorchooser = makeColorChooser(),desktop);
        ColorChooser.setSize(400, 300);
        ColorChooser.setLocation(300, 0);
        PenSize = makeInternalFrame("PenSize",PenSizeSlider=makeSlider(1,100,3),desktop);
        PenSize.setSize(300,100);
        PenSize.setLocation(0, 300);
        //Menu
        setJMenuBar(makeMenuBar( menu =makeMenu("Menu")));
        makeJMenuItem("newfile","ctrl N",menu);
        makeJMenuItem("openfile","ctrl O",menu);
        makeJMenuItem("savefile","ctrl S",menu);
        makeJMenuItem("editmode","ESCAPE",menu);
        pack();
		setVisible(true);
    }
    public void stateChanged(ChangeEvent e) {
		color = colorchooser.getColor();
		 mpc.setColor(color);
	}
    public void actionPerformed(ActionEvent e){
    	mpc.setPoint();
    	 JFileChooser filechooser = new JFileChooser(); 
    	 if ((e.getActionCommand().equals("newfile"))){
    		 img =null;
    		 mpc.setImage(img);
    		 mpc.repaint();
    		 mpc.setCanvas();
 		}else if ((e.getActionCommand().equals("openfile")))
    	 {
    		 if (filechooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
    			 File file = filechooser.getSelectedFile();
    			 try{
    				 img =null;
    				 img = ImageIO.read(file);
    				 mpc.setImage(img);
    				 mpc.setOpenfileflag();
    			 }catch(IOException ex){
 					ex.printStackTrace();
 				}
    		 }
    	 }else if (e.getActionCommand().equals("savefile")){
    		 FileFilter filter = new FileNameExtensionFilter("PNGファイル(*.png)", "png");
    		 filechooser.setFileFilter(filter);
    		 if (filechooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
    				File fChoosen = filechooser.getSelectedFile();
    		 try{
    		 ImageIO.write(mpc.getSavedcanvas(),"png",fChoosen);
    		 }catch(IOException ex){
					ex.printStackTrace();
				}
    		 }
 		}
    	 
    }
    /**
    *@param start mouseMoved位置を保持
    *@param end　　　mouseDragged位置を保持
    **@param canvas　 PhotoShopでいうところの背景(canvas)
    */
    private class MousePaintcore extends JPanel implements MouseInputListener{
        private static final long serialVersionUID = 1L;
        private final Point start;
        private final Point end;
        private final Line2D.Double line;
        private boolean openfileflag;
        private BufferedImage img ,canvas;
        private Color color;
        public MousePaintcore() {
        	super.setVisible(true);
        	color=null;
        	openfileflag=false;
        	line = new Line2D.Double();
        	start = new Point();
        	end   = new Point();
        	canvas= null;
        	setBackground(Color.white);
        	repaint();
            addMouseMotionListener(this);
            addMouseListener(this);
        }
        public void setOpenfileflag(){
        	openfileflag=true;
        }
        public void setImage (BufferedImage setImg){
        	img=setImg;
        }
        public void setCanvas (){
        	canvas=null;
        }
        public void setPoint (){
        	start.setLocation(0,0);
        	end.setLocation(0,0);
        }
        public void setColor (Color setcolor){
        	color=setcolor;
        }
        public void paintComponent(Graphics g){
        	Graphics2D g2 = (Graphics2D)g;
        	 if(canvas==null)canvas =(BufferedImage)super.createImage(getWidth(), getHeight()) ;
        	 if(openfileflag){
        		 canvas =(BufferedImage)super.createImage(img.getWidth(), img.getHeight()) ;
        		 openfileflag=false;
        	 }
        	 Graphics2D createG =canvas.createGraphics();
        	 createG.setBackground(Color.WHITE);
        	 createG.drawImage(img, 0, 0, null);
        	 createG.drawImage(canvas, 0, 0, null);
        	 createG.setStroke(new BasicStroke(PenSizeSlider.getValue())); 
        	 createG.setPaint(color);
        	 line.setLine(start,end);
        	 if(start.x!=0 && end.x!=0 &&start.y!=0&&end.y!=0)createG.draw(line); 
        	 start.setLocation(end); 
        	 g2.drawImage(canvas, 0, 0, null);
        	 img =null;
        	 createG.dispose();
        }
       public BufferedImage getSavedcanvas(){
    	   return canvas;
       }
       @Override
       public void mouseDragged(MouseEvent e) {
    	   end.setLocation(e.getPoint());
    	   if(end.x>canvas.getWidth() || end.x<0)end.x=0;
    	   if(end.y>canvas.getHeight() || end.y<0)end.y=0;
    	   repaint();
    	  
       }
       @Override
       public void mouseMoved(MouseEvent e) {
    	   start.setLocation(e.getPoint());
    	   if(start.x>canvas.getWidth() || start.x<0)start.x=0;
    	   if(start.y>canvas.getHeight() || start.y<0)start.y=0;
       }
       @Override
       public void mouseClicked(MouseEvent e) {
    	   
       }
       @Override
       public void mouseEntered(MouseEvent arg0) {
		
		
       }
       @Override
       public void mouseExited(MouseEvent arg0) {
		
		
       }
       @Override
       public void mousePressed(MouseEvent arg0) {
		
       }
       @Override
       public void mouseReleased(MouseEvent e) {
    	   start.x=0;
    	   start.y=0;
    	   end.x=0;
    	   end.y=0;
    	   
       }  
    	}
    public static void main(String args[]){
    	new  MousePaint();
    }
}