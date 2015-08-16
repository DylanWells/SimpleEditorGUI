import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.lang.*;
import java.io.File;

public class MainWindow extends JFrame 
implements ComponentListener, MouseListener {

    public static boolean initComplete = false;
    public TextEditor textWindow;
    private ButtonPanel buttonPan;


    private MainWindow() {
        super.frameInit();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
               
        setLocationRelativeTo(null);
        setSize(getPreferredSize());       

        setUnit = new MyFont(mainFontFile,20f,Font.PLAIN);
        int sendAscent = setUnit.getMetric("ascent");
        int sendDescent = setUnit.getMetric("descent");

        textWindow = new TextEditor(this,23,sendAscent,sendDescent,mainFontFile);
        buttonPan = new ButtonPanel();
     
        getContentPane().setBackground(new Color(0,0,0,0));
        getContentPane().setBackground(new Color(10,10,10));  
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(textWindow,BorderLayout.CENTER);
        getContentPane().add(buttonPan,BorderLayout.NORTH);

     //   getContentPane().add(TextFileManager.textFileChooser);

        //UIManager.getLookAndFeelDefaults().put("TextField.caretBackground",null); 

        initComplete = true;

        addComponentListener(this);
        addMouseListener(this);

        pack();
        setVisible(true);
    }

    private static final String local = System.getProperty("user.dir");
    private static final String testPath = local+"/assets/Inconsolata.ttf";
    private static final File mainFontFile = new File(testPath);
    private MyFont setUnit;  
    

    public static void writeToBody(String in) {
        if(initComplete)
            TextEditor.textEdit.setText(in);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(550,500);    
    }
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(300,250);    
    }
    @Override
    public Dimension getMaximumSize() {
        return new Dimension(750,1000);
    }
    
    @Override
    public void componentHidden(ComponentEvent e) {
        if(MainWindow.initComplete)
                textWindow.updateViewConstants();
    }
    @Override
    public void componentMoved(ComponentEvent e) {
        if(MainWindow.initComplete)
                textWindow.updateViewConstants();
    }
    @Override
    public void componentResized(ComponentEvent e) {
        Dimension currentSize = new Dimension(getSize());
        if(currentSize.width < getMinimumSize().width)
            currentSize.width = getMinimumSize().width;
        if(currentSize.height < getMinimumSize().height)
            currentSize.height = getMinimumSize().height;
        if(currentSize.width > getMaximumSize().width)
            currentSize.width = getMaximumSize().width;
        if(currentSize.height > getMaximumSize().height)
            currentSize.height = getMaximumSize().height;
        setSize(currentSize);
        if(MainWindow.initComplete)
                textWindow.updateViewConstants();        
    }
    @Override
    public void componentShown(ComponentEvent e) {
        if(MainWindow.initComplete)
                textWindow.updateViewConstants();
    }
@Override
    public void mouseClicked(MouseEvent e) {
        if(MainWindow.initComplete)
                textWindow.updateViewConstants();        
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        if(MainWindow.initComplete)
                textWindow.updateViewConstants();
    }
    @Override
    public void mouseExited(MouseEvent e) {
        if(MainWindow.initComplete)
                textWindow.updateViewConstants();
    }
    @Override
    public void mousePressed(MouseEvent e){
        if(MainWindow.initComplete)
                textWindow.updateViewConstants();
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if(MainWindow.initComplete)
                textWindow.updateViewConstants();
    }
    public static void main(String args[]) {        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run()  {
                new MainWindow();
            }
        });                   
    }    
}