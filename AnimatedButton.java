import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.*;
import java.awt.geom.Rectangle2D;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class AnimatedButton extends JButton
implements MouseListener {

    public AnimatedButton(Action a) {
           this();
           setAction(a);
    }
    public AnimatedButton(){
        hoverTimer = new Timer(20,hoverListener);
        pressTimer = new Timer(15,pressListener);
        addMouseListener(this);
           
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        
        hoverTimer.start();
        pressTimer.start();
        repaint();
    }

    private static final float MIN_ALPHA = 0f;
    private static final float MAX_ALPHA = 1f;
    private float hoverAlpha = MIN_ALPHA;
    private float pressAlpha = MIN_ALPHA;
    
    private boolean isHovered = false;
    private boolean isPressed = false;
    
    private final Timer hoverTimer;
    private final Timer pressTimer;

    private Color neutralColor = new Color(85,85,85);
    private Color hoverColor = new Color(50,50,50);
    private Color pressColor = new Color(225,145,0);
    
    private BufferedImage source;
    private BufferedImage neutral;
    private BufferedImage icon;

    public String buttonName;
/*
    private final String dirPath = System.getProperty("user.dir");
*/
    private String iconPath;// = dirPath+"/assets/whitemed.png";
   
    private ImageIcon iconImage;
    
    private static final RescaleOp neutralFilter = 
        new RescaleOp(new float[]{0.235f,0.235f,0.235f,1f},new float[4],null);
    private static final RescaleOp hoverFilter = 
        new RescaleOp(new float[]{0.5f,0.5f,0.5f,1f},new float[4],null);
    private static final RescaleOp pressFilter = 
        new RescaleOp(new float[]{0.25f,0.25f,0.25f,1f},new float[4],null);
    
    private AffineTransform reduceScale = new AffineTransform();
    private AffineTransformOp scaleIcon;
                                                                          

    public void setAnimatedButton(String name,String iconLink) {
       // refresh = new TextEditor();
        setEnabled(false);
        //iconImage = new ImageIcon(iconLink);
        iconPath = iconLink;
        buttonName = name;
        loadImage();
        reduceScale.scale(0.8, 0.8);
        scaleIcon = new AffineTransformOp(reduceScale,AffineTransformOp.TYPE_BICUBIC);          
    }
  
    private void loadImage() {
        source = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);        
        try {
            source = ImageIO.read(new File(iconPath));
        } catch(IOException ioe) { ioe.printStackTrace(); }
    }    

    @Override
    protected void paintComponent(Graphics g) {
       
       Graphics2D g2d = (Graphics2D) g;
       
       int sourceWidth = source.getWidth()/2;
       int sourceHeight = source.getHeight()/2;
       int xPlace = this.getWidth()/2-25;
       int yPlace = 0;
      // System.out.println(yPlace);
/*
       int xPlace = this.getWidth()-sourceWidth;
       int yPlace = this.getHeight()-sourceHeight;
  */     
       g2d.setPaint(neutralColor);
       g2d.fillRect(0,0,getSize().width,getSize().height);
       g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
       RenderingHints.VALUE_INTERPOLATION_BICUBIC);
      // g2d.scale(.5,.5);
       g2d.drawImage(source,neutralFilter,xPlace,yPlace);
       
       g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,hoverAlpha));
       g2d.setPaint(hoverColor);
       g2d.scale(2,2);
       g2d.fillRect(0,0,getSize().width,getSize().height);
       g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
       RenderingHints.VALUE_INTERPOLATION_BICUBIC);
       g2d.scale(.5,.5);
       g2d.drawImage(source,hoverFilter,xPlace,yPlace);

       g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,pressAlpha));
       g2d.setPaint(pressColor);
       g2d.scale(2,2);
       g2d.fillRect(0,0,getSize().width,getSize().height);     
       g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
       RenderingHints.VALUE_INTERPOLATION_BICUBIC);
       g2d.scale(.5,.5);
       g2d.drawImage(source,pressFilter,xPlace,yPlace);

       g2d.dispose();
       super.paintComponent(g2d);      
    }


    private final ActionListener hoverListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            hoverAlpha += (isHovered ? 1 : -1) * 0.1f;

            hoverAlpha = isHovered ?  
            Math.min(hoverAlpha, MAX_ALPHA) : 
            Math.max(hoverAlpha, MIN_ALPHA);

            if (hoverAlpha == MIN_ALPHA || hoverAlpha == MAX_ALPHA) {
                hoverTimer.stop();
            }
            TextEditor.DRAW_GRID = true;
            TextEditor.DRAW_LINE_NUMS = true;
            repaint();
        }
    };
    
    private final ActionListener pressListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            pressAlpha += (isPressed ? 1 : -1) * 0.1f;
            pressAlpha = isPressed ?  
            Math.min(pressAlpha, MAX_ALPHA) : Math.max(pressAlpha, MIN_ALPHA);
            if (pressAlpha == MIN_ALPHA || pressAlpha == MAX_ALPHA) {
                pressTimer.stop();
            }
            TextEditor.DRAW_GRID = true;
            TextEditor.DRAW_LINE_NUMS = true;
            repaint();
            System.out.println(e.getSource());
        }
        
    };


    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        isHovered = true;
        //System.out.println("Entered");
        if (!hoverTimer.isRunning()) {
		      hoverTimer.restart();
		}
    }
    @Override
    public void mouseExited(MouseEvent e) {
        isHovered = false;
        //System.out.println("Exited");
        if (!hoverTimer.isRunning()) {
          hoverTimer.restart();
		}
    }
    @Override
    public void mousePressed(MouseEvent e){
        isPressed = true;
        //System.out.println("Pressed");
        if (!pressTimer.isRunning()) {
		      pressTimer.restart();
		}
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        isPressed = false;
        //System.out.println("Released");
        //System.out.println("HEY");
        if (!pressTimer.isRunning()) {
		      pressTimer.restart();
		}

        System.out.println(e.getComponent().getName());
        
        //JFileChooser jc = new JFileChooser();
        //int userInp = jc.showOpenDialog(null);
    }
}