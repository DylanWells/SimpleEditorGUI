import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.io.IOException;


public class TextEditor extends JPanel 
implements ComponentListener {
  
    public TextEditor() {}
    public TextEditor(JFrame window, int unit, int asc, int des, File fontFile) {
        super();
        BASE_UNIT = unit;
        WINDOW = new Dimension(window.getPreferredSize());
        FONT_ASCENT = asc;
        FONT_DESCENT = des;

        textFontInit = new MyFont(fontFile,20f,Font.PLAIN);
        lineFontInit = new MyFont(fontFile,20f,Font.PLAIN);
        
        textEdit = new TextBody();
        scrollEdit = new Scroller();
  
        setLayout(new BorderLayout());
        add(scrollEdit,BorderLayout.CENTER);

        addComponentListener(this);
        window.addWindowListener(new WindowAdapt()); 

        setEnabled(false);
        setStyle();
        setVisible(true);   
    }

  ////////////////////////////////////////////////////////////////    
 ////////////////////////////////////////////////////////////////   
      ////////////////////////////////
     /////////VIEW CONSTANTS/////////
    ////////////////////////////////
    private static Dimension WINDOW;
    private static int BASE_UNIT;
    private static Point VIEW_POINT;
    private static Dimension VIEW_SIZE;
    private static Rectangle MODEL_VIEW;
    private static int VIEW_MODEL;
    private static int SCROLL_VIEW_TOP;
    private static int SCROLL_VIEW_X;
    private static int SCROLL_VIEW_Y;
    private static int SCROLLED;
    private static int SCROLL_HEIGHT;
    private static double HEIGHT_IN_UNITS;
    private static int CARET_POS;
    private static int CARET_POS_PREV;
    private static int ACTIVE_ROW;
    private static int FONT_ASCENT;
    private static int FONT_DESCENT;
      ////////////////////////////////
     ////////COLOR CONSTANTS/////////
    ////////////////////////////////
    private static final Color LIGHT_GREY = new Color(245,245,245);
    private static final Color MEDIUM_GREY = new Color(220,220,220);
    private static final Color DARK_GREY = new Color(180,180,180);
    private static final Color EDITOR_GREY = new Color(50,50,50);  
    private static final Color LINE_NUM_BG = new Color(120,120,120);
    private static final Color LINE_NUM_TXT = new Color(200,200,200);  
      ///////////////////////////////
     /////////DRAW CONSTANTS////////
    ///////////////////////////////
    public static boolean DRAW_GRID = true;
    public static boolean DRAW_LINE_NUMS = true;
  ///////////////////////////////////////////////////////////////
 ///////////////////////////////////////////////////////////////    
///////////////////////////////////////////////////////////////

    public static TextBody textEdit;
    public Scroller scrollEdit;
    private LineNumbers numPane;
    //private TextBackground textBack;

    private MyFont lineFontInit;
    private MyFont textFontInit;
    private Font lineCountFont;
    private Font textEditorFont;

    private StyledDocument scriptDocument;
    private StyleContext scriptStyleContext;
    private Style scriptStyle;
    private Style fontStyle;
    public Document textBody;

    private int leftIndent;   

    //public boolean readText = false;

  ///////////////////////////////////////////////////////////////
 ///////////////////////////////////////////////////////////////    
///////////////////////////////////////////////////////////////   

    public void updateViewConstants() {
          
            VIEW_POINT = scrollEdit.getViewport().getViewPosition();
            VIEW_SIZE = scrollEdit.getViewport().getViewSize();      

            VIEW_MODEL = textEdit.viewToModel(VIEW_POINT);

            try { MODEL_VIEW = textEdit.modelToView(VIEW_MODEL);} 
            catch (BadLocationException ble) { ble.printStackTrace();}

            SCROLL_VIEW_TOP = VIEW_POINT.y;
            SCROLL_VIEW_X = VIEW_SIZE.width; 
            SCROLL_VIEW_Y = VIEW_SIZE.height;
            SCROLLED = SCROLL_VIEW_Y - VIEW_POINT.y;
            SCROLL_HEIGHT = SCROLLED + SCROLL_VIEW_Y;
            
            HEIGHT_IN_UNITS = (double)SCROLL_HEIGHT/(double)BASE_UNIT;
            
            DRAW_GRID = true;
            DRAW_LINE_NUMS = true;
            numPane.repaint();
            repaint();
    }

///////////////////////////////////////////////////////////////   

    private void setStyle() {        
        leftIndent =  BASE_UNIT;    
        scriptStyleContext = new StyleContext();
        scriptDocument = new DefaultStyledDocument(scriptStyleContext);
        scriptStyle = scriptStyleContext.getStyle(StyleContext.DEFAULT_STYLE);

        StyleConstants.setLeftIndent(scriptStyle,leftIndent);
        StyleConstants.setSpaceBelow(scriptStyle,FONT_DESCENT-3);

        textEdit.setStyledDocument(scriptDocument);
        updateViewConstants();
    }

///////////////////////////////////////////////////////////////
/*
    public void fileToTextBody(String in) {
        //writeToBody(in);
        TextBody.setText(in);
    }
   */
///////////////////////////////////////////////////////////////
    @Override
    public void componentHidden(ComponentEvent e) {
        //System.out.println(e.getComponent().getClass().getName() + " --- Hidden");
    }
    @Override
    public void componentMoved(ComponentEvent e) {
        //System.out.println(e.getComponent().getClass().getName() + " --- Moved");
    }
    @Override
    public void componentResized(ComponentEvent e) {
        //System.out.println(e.getComponent().getClass().getName() + " --- Resized ");      
    }
    @Override
    public void componentShown(ComponentEvent e) {
        //System.out.println(e.getComponent().getClass().getName() + " --- Shown");
    }

///////////////////////////////////////////////////////////////       
    
    private class WindowAdapt extends WindowAdapter {
        public void windowOpened(WindowEvent e) {
            if(MainWindow.initComplete)
                updateViewConstants();
        }
        public void windowActivated(WindowEvent e) {
            if(MainWindow.initComplete)
                updateViewConstants();
        }
        public void windowDeactivated(WindowEvent e) {
            if(MainWindow.initComplete)
                updateViewConstants();
        }
    }

///////////////////////////////////////////////////////////////

    private class Scroller extends JScrollPane {   
        private Scroller() {
            super(textEdit);

            setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);         

            setBorder(null);
            setOpaque(false);
            getViewport().setOpaque(false);
            
            setRowHeader(new JViewport());
            numPane = new LineNumbers();
            getRowHeader().setView(new LineNumbers());

            getVerticalScrollBar().setUnitIncrement(BASE_UNIT);
            getVerticalScrollBar().addAdjustmentListener(new
            AdjustmentListener() {
                @Override
                public void adjustmentValueChanged(AdjustmentEvent ae) {
                    if(MainWindow.initComplete)
                        updateViewConstants();
                }
            });     
        }
    }
    
///////////////////////////////////////////////////////////////

    private class LineNumbers extends JPanel {
        private LineNumbers() {
            setLayout(new GridLayout());
            lineCount = new JTextPane();
            lineCount.setOpaque(false);
            lineCount.setEditable(false);

            //lineCount.setMargin(new Insets(0,0,0,0));
            lineCount.setFont(lineFontInit.thisFont());
            lineCount.setForeground(LINE_NUM_TXT);

            lineStyleContext = new StyleContext();
            lineDocument = new DefaultStyledDocument(lineStyleContext);
            lineStyle = lineStyleContext.getStyle(StyleContext.DEFAULT_STYLE);
            
            StyleConstants.setSpaceBelow(lineStyle,FONT_DESCENT-1);
            StyleConstants.setRightIndent(lineStyle,3);

            lineCount.setStyledDocument(lineDocument);
      
            add(lineCount);
            applyComponentOrientation(alignRight);

            setOpaque(false);
        }

        private StyledDocument lineDocument;
        private StyleContext lineStyleContext;
        private Style lineStyle;
        private Style lineFontStyle;
        private Document lineTextBody;

        
        private JTextPane lineCount;
        private Locale alignR = new Locale("ar", "KW");
        private ComponentOrientation alignRight =
        ComponentOrientation.getOrientation(alignR);

        private int topPad;
        private int botPad;        
        private int oldNum;
        
        Integer intString = new Integer(0);
        String lNum;
        
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(50,scrollEdit.getViewport().getViewSize().height);
        }
        
        public void setTheLineMargin(int tPad, int bPad) {
            topPad = tPad;
            botPad = bPad;
            lineCount.setMargin(new Insets
                (topPad+1,0,0,4));
        }
        
        private void insertLineNum() {
            StringBuilder lines = new StringBuilder();
            for(int i=1;i<=(int)Math.ceil(HEIGHT_IN_UNITS);i++) {
                if(((i*BASE_UNIT)+BASE_UNIT)>=SCROLL_VIEW_TOP && 
                    ((i*BASE_UNIT)-BASE_UNIT)<SCROLL_VIEW_Y)
                    lines.append(i);
                    lines.append("\n");
            }
            try {
                    lineCount.setText("");
                    lineCount.getStyledDocument().insertString
                    (0,lines.toString(),lineFontStyle);
                } 
            catch(BadLocationException ble) { ble.printStackTrace(); }            
            lines=null;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            g.setColor(new Color(85,85,85));
            g.fillRect(0,0,this.getWidth(),this.getHeight());
            g.setColor(new Color(70,70,70));     
            for(int i=0;i<SCROLL_HEIGHT;i+=BASE_UNIT*2)
                if(i>=SCROLL_VIEW_TOP && i<SCROLL_HEIGHT)
                        g.fillRect(0,i,getWidth(),BASE_UNIT);
            if(DRAW_LINE_NUMS) {               
                insertLineNum();
                numPane.repaint();               
            }
            DRAW_LINE_NUMS = false;  
        }
    }

///////////////////////////////////////////////////////////////

    public class TextBody extends JTextPane 
    implements DocumentListener,CaretListener { 
        //public FileInputStream fileOpen;
        private DefaultHighlighter hiLite;
        private LinePainter hiLine;
        private Color hiLineColor = new Color(20,20,20,10);

        public TextBody() {
            super();
            setOpaque(false);
            setEditorKit(new TextWrapKit());
            setBackground(new Color(255, 255, 255, 0)); 
            setForeground(EDITOR_GREY);
            setFont(textFontInit.thisFont());
            //this.getComponent(0).

            hiLite = new DefaultHighlighter();
            setHighlighter(hiLite);
            hiLite.setDrawsLayeredHighlights(false);
            hiLine = new LinePainter(this,hiLineColor);
            
            

            DefaultCaret myCaret = (DefaultCaret)this.getCaret();
            myCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
            setSelectionColor(new Color(30,30,30,100));

            getDocument().addDocumentListener(this);
            addCaretListener(this);

            //
            //
            
            this.addKeyListener(new KeyListener() {     
                @Override 
                public void keyPressed(KeyEvent e) {
                    DRAW_GRID = true;
                    //setHighlighter(null);
                    repaint();
                }
                @Override
                public void keyReleased(KeyEvent e) {
                    DRAW_GRID = true;
                    //setHighlighter(hiLite);
                    repaint();
                }
                @Override
                public void keyTyped(KeyEvent e) {
                }
            });

        }
   
        @Override
        public void caretUpdate(CaretEvent e) {
            //super.repaint();
            if(MainWindow.initComplete)
                updateViewConstants();
            //repaint();
        }
        @Override
        public void insertUpdate(DocumentEvent e) {
            //super.repaint();
            if(MainWindow.initComplete)
                updateViewConstants();
            //repaint();
        }
        @Override
        public void removeUpdate(DocumentEvent e) {
            //super.repaint();
            if(MainWindow.initComplete)
                updateViewConstants();
            //repaint();
        }
        @Override
        public void changedUpdate(DocumentEvent e) {
            //super.repaint();
            if(MainWindow.initComplete)
                updateViewConstants();
            //repaint();
        }  

    }
 
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if(DRAW_GRID) {
        DRAW_LINE_NUMS = true;
    
        g.setColor(LIGHT_GREY);
        for(int y=BASE_UNIT/2;y<SCROLL_HEIGHT;y+=BASE_UNIT/2)                 
                if(y>=SCROLL_VIEW_TOP && y<SCROLL_HEIGHT)
                    g.drawLine(0,y-SCROLL_VIEW_TOP,SCROLL_VIEW_X+50,y-SCROLL_VIEW_TOP);                
        for(int x=BASE_UNIT/2; x<SCROLL_VIEW_X; x+=BASE_UNIT/2)
                if(x<=SCROLL_VIEW_X)
                    g.drawLine(x+50,0,x+50,SCROLL_VIEW_X+50);

        g.setColor(MEDIUM_GREY);
        for(int y=BASE_UNIT;y<SCROLL_HEIGHT;y+=BASE_UNIT)                 
                if(y>=SCROLL_VIEW_TOP && y<SCROLL_HEIGHT)
                    g.drawLine(0,y-SCROLL_VIEW_TOP,SCROLL_VIEW_X+50,y-SCROLL_VIEW_TOP);                
        for(int x=BASE_UNIT; x<SCROLL_VIEW_X; x+=BASE_UNIT)
                if(x<=SCROLL_VIEW_X)
                    g.drawLine(x+50,0,x+50,SCROLL_VIEW_X+50);

        g.setColor(DARK_GREY);
        for(int y=BASE_UNIT*4;y<SCROLL_HEIGHT;y+=BASE_UNIT*4)                 
                if(y>=SCROLL_VIEW_TOP && y<SCROLL_HEIGHT)
                    g.drawLine(0,y-SCROLL_VIEW_TOP,SCROLL_VIEW_X+50,y-SCROLL_VIEW_TOP);                
        for(int x=BASE_UNIT*4; x<SCROLL_VIEW_X; x+=BASE_UNIT*4)
                if(x<=SCROLL_VIEW_X)
                    g.drawLine(x+50,0,x+50,SCROLL_VIEW_X+50);

        DRAW_GRID = false;       
        }
    
    }
}