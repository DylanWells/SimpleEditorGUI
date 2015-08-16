import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

import java.io.File;

import java.awt.Font;
import java.awt.font.LineMetrics;
import java.awt.FontMetrics;
import java.awt.font.FontRenderContext;

import java.awt.FontFormatException;
import java.io.IOException;
import java.lang.NullPointerException;
import javax.swing.text.BadLocationException;

public class MyFont {
    private Font theFont;
    private FontMetrics myFontMetric;
    private HashMap<String,Integer> metricMap;
    private float myFontSize;
    
    private Integer lineHeight;
    private Integer fontAscent;
    private Integer fontDescent;
    private Integer fontLeading;
    private Integer charWidth;
    
    MyFont(File source,float size,int type) {
        try { try{
        theFont = 
        Font.createFont(Font.TRUETYPE_FONT,source).deriveFont(type,size);} 
        catch(IOException ioe) { ioe.printStackTrace(); }} 
        catch(FontFormatException ffe) { ffe.printStackTrace(); } 

        myFontSize = size;
        metricMap = new HashMap<String,Integer>();
        buildMetrics();
    }
    
    public Font thisFont() {
        return theFont;
    }

    public int getMetric(String metric) {
        return metricMap.get(metric);
    } 

    private void buildMetrics() {
        BufferedImage bi = 
        new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics g = bi.getGraphics();
        myFontMetric = g.getFontMetrics(theFont);
        g.dispose();
        bi = null;
        lineHeight = new Integer(myFontMetric.getHeight());
        fontAscent = new Integer(myFontMetric.getMaxAscent());
        fontDescent = new Integer(myFontMetric.getMaxDescent());
        fontLeading = new Integer(myFontMetric.getLeading());
        charWidth = new Integer(myFontMetric.charWidth('X'));
        metricMap.put("height",lineHeight);
        metricMap.put("ascent",fontAscent);
        metricMap.put("descent",fontDescent);
        metricMap.put("leading",fontLeading);
        metricMap.put("char",charWidth);
    }
}