import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ButtonPanel extends JPanel {

    public TextFileManager fileBoss;

    private static final String dirPath = System.getProperty("user.dir");
    private static final String saveIconPath = dirPath+"/assets/saveIcon.png";
    private static final String openIconPath = dirPath+"/assets/openIcon.png";

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

        fileBoss = new TextFileManager();
        TextFileManager.TextFileInput fileINP = fileBoss.new TextFileInput();
        TextFileManager.TextFileSaver fileSAV = fileBoss.new TextFileSaver();
 //       TextFileManager.TextFileExporter fileOUT = fileBoss.new TextFileExporter();
        
        AnimatedButton impButton = new AnimatedButton();
        impButton.setAnimatedButton("open",openIconPath);
      //  impButton.addActionListener(fileBoss.new TextFileInput());
        panelSet.gridx = 0;               
        panelGrid.setConstraints(impButton,panelSet);
        add(impButton);

        AnimatedButton saveButton = new AnimatedButton();
        saveButton.setAnimatedButton("save",saveIconPath);
      //  saveButton.addActionListener(fileBoss.new TextFileSaver());
        panelSet.gridx = 1;       
        panelGrid.setConstraints(saveButton,panelSet);
        add(saveButton);
  /*
        AnimatedButton expButton = new AnimatedButton(iconPath);
        expButton.addActionListener(fileBoss.new TextFileExporter());
        panelSet.gridx = 1;       
        panelGrid.setConstraints(expButton,panelSet);
        add(expButton);
*/
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getSize().width,50);    
    }
/*    
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
*/
}