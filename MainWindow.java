import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.lang.*;

public class MainWindow extends JFrame implements ComponentListener {

    public static void main(String args[])
    {        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run()  {
                new MainWindow();
            }
        });                   
    }
    
    MainWindow() {
        super.frameInit();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        setLocationRelativeTo(null);
        setSize(getPreferredSize());
        getContentPane().setBackground(new Color(10,10,10));
        addComponentListener(this);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(new TextEditor(),BorderLayout.CENTER);
        getContentPane().add(new ButtonPanel(),BorderLayout.NORTH);
        pack();
        setVisible(true);
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
        System.out.println(e.getComponent().getClass().getName() + " --- Hidden");
    }
    @Override
    public void componentMoved(ComponentEvent e) {
        System.out.println(e.getComponent().getClass().getName() + " --- Moved");
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
        System.out.println(e.getComponent().getClass().getName() + " --- Resized ");        
    }
    @Override
    public void componentShown(ComponentEvent e) {
        System.out.println(e.getComponent().getClass().getName() + " --- Shown");

    }
}