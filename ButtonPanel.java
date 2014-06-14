import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ButtonPanel extends JPanel {

    private static final String dirPath = System.getProperty("user.dir");
    private static final String iconPath = dirPath+"/img/whitemed.png";

    private static final Color[] colorStatesA = 
    {new Color(100,100,100),new Color(75,75,75),new Color(125,125,125)};
    
    private static final Color[] colorStatesB = 
    {new Color(140,140,140),new Color(115,115,115),new Color(165,165,165)};
    
    public ButtonPanel() {
        GridBagLayout panelGrid = new GridBagLayout();
        GridBagConstraints panelSet = new GridBagConstraints();
        setLayout(panelGrid);
        
        panelSet.weightx = 1;
        panelSet.weighty = 1;
        panelSet.gridheight = 1;
        panelSet.gridwidth = 1;
        panelSet.gridy = 0; 
        panelSet.fill = GridBagConstraints.BOTH;
        
        //StandardButton buttonA = new StandardButton(colorStatesA);
        //addActionListener(new ActionAdapter());
        AnimatedButton impButton = new AnimatedButton(iconPath);
        panelSet.gridx = 0;               
        panelGrid.setConstraints(impButton,panelSet);
        add(impButton);
        
        //StandardButton buttonB = new StandardButton(colorStatesB);
        AnimatedButton expButton = new AnimatedButton(iconPath);
        panelSet.gridx = 1;       
        panelGrid.setConstraints(expButton,panelSet);
        add(expButton);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getSize().width,50);    
    }
    
    private class StandardButton extends JButton
    implements MouseListener {
        private Color neutralColor;
        private Color hoverColor;
        private Color pressColor;      
        private boolean isHover;
        private boolean isPress;
        
        private StandardButton(Color[] color) {
            addMouseListener(this);            
            neutralColor = new Color(color[0].getRGB());
            hoverColor = new Color(color[1].getRGB());
            pressColor = new Color(color[2].getRGB());
        }
        
        @Override
        protected void paintComponent(Graphics g) {  
            if(isPress)
                g.setColor(pressColor);
            else if(isHover)
                g.setColor(hoverColor);
            else
                g.setColor(neutralColor);            
            g.fillRect(0,0,getSize().width,getSize().height);
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            isPress = false;
            this.repaint();
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            isHover = true;
            this.repaint();
        }
        @Override
        public void mouseExited(MouseEvent e) {
            isHover = false;
            isPress = false;
            this.repaint();
        }
        @Override
        public void mousePressed(MouseEvent e) {
            isHover = false;
            isPress = true;
            this.repaint();
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            isPress = false;
            isHover = true;
            this.repaint();
        }
    }
}