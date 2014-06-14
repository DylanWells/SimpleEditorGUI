import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.lang.*;

public class TextEditor extends JPanel implements ComponentListener {

    public static void main(String args[])
    {        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run()  {
                new TextEditor();
            }
        });                   
    }
    
    public TextEditor() {
        setLayout(new BorderLayout());
        add(new Scroller(),BorderLayout.CENTER);
        addComponentListener(this);
    }
    @Override
    public void componentHidden(ComponentEvent e) {
        System.out.println(e.getComponent().getClass().getName() + " --- Hidden");
    }
    @Override
    public void componentMoved(ComponentEvent e) {
        System.out.println(e.getComponent().getClass().getName() + " --- Moved");
    }
    @Override
    public void componentResized(ComponentEvent e) {
        System.out.println(e.getComponent().getClass().getName() + " --- Resized ");
        
    }
    @Override
    public void componentShown(ComponentEvent e) {
        System.out.println(e.getComponent().getClass().getName() + " --- Shown");
    }   
    
    private class Scroller extends JScrollPane {
    
        private Scroller() {
            super(new TextBody());
            setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            setBorder(null);
            setOpaque(false);
            getViewport().setOpaque(false);
      //      getVerticalScrollBar().setUnitIncrement(BASE_UNIT);
            getVerticalScrollBar().addAdjustmentListener(new
            AdjustmentListener() {
                @Override
                public void adjustmentValueChanged(AdjustmentEvent ae) {
                    System.out.println("Scrollbar");
                }
            });
            setRowHeader(new JViewport());
            getRowHeader().setView(new LineNumbers());
        }
    }
    
    private class LineNumbers extends JPanel {
    
        private LineNumbers() {
        
        }
        
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(50,getSize().height);
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(new Color(180,180,180));
            g.fillRect(0,0,getSize().width,getSize().height);
        }
    }
    
    private class TextBody extends JTextPane 
    implements DocumentListener,CaretListener {
    
        private TextBody() {
            setEditorKit(new TextWrapKit());
            getDocument().addDocumentListener(this);
            addCaretListener(this);
        }
        
        @Override
        public void caretUpdate(CaretEvent e) {
            System.out.println("Caret");
        }
        @Override
        public void insertUpdate(DocumentEvent e) {
            System.out.println("insert");
        }
        @Override
        public void removeUpdate(DocumentEvent e) {
            System.out.println("remove");
        }
        @Override
        public void changedUpdate(DocumentEvent e) {
            System.out.println("change");
        }    
    }
}