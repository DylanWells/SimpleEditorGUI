/*


ATTENTION: I (Dylan Wells) did not write this. 
    It is the standard way to fix JTextPane 
    text wrapping behaviour that changed in
    Java 7.

*/

import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.LabelView;
import javax.swing.text.Element;
import javax.swing.text.AbstractDocument;
import javax.swing.text.IconView;
import javax.swing.text.BoxView;
import javax.swing.text.ParagraphView;
import javax.swing.text.ComponentView;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;

public class TextWrapKit extends StyledEditorKit {
        ViewFactory defaultFactory=new TextWrapFactory();
        public ViewFactory getViewFactory() {
            return defaultFactory;
        }
    
    private class TextWrapFactory implements ViewFactory {
        public View create(Element elem) {
            String kind = elem.getName();
            if (kind != null) {
                if (kind.equals(AbstractDocument.ContentElementName)) {
                    return new TextWrapView(elem);
                } else if (kind.equals(AbstractDocument.ParagraphElementName)) {
                    return new ParagraphView(elem);
                } else if (kind.equals(AbstractDocument.SectionElementName)) {
                    return new BoxView(elem, View.Y_AXIS);
                } else if (kind.equals(StyleConstants.ComponentElementName)) {
                    return new ComponentView(elem);
                } else if (kind.equals(StyleConstants.IconElementName)) {
                    return new IconView(elem);
                }
            }
            // default to text display
            return new LabelView(elem);
        }
    }

    private class TextWrapView extends LabelView {
        public TextWrapView(Element elem) {
            super(elem);
        }

        public float getMinimumSpan(int axis) {
            switch (axis) {
                case View.X_AXIS:
                    return 0;
                case View.Y_AXIS:
                    return super.getMinimumSpan(axis);
                default:
                    throw new IllegalArgumentException("Invalid axis: " + axis);
            }
        }

    }
}