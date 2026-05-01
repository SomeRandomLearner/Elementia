package utils;

import javax.swing.JButton;
import java.awt.Color;

public class CustomButton extends JButton {
    private Color defaultBackgroundColor;
    private Color defaultForegroundColor;
    private Color hoverColor;

    public CustomButton(){
        this("", Color.BLACK, Color.WHITE, Color.GRAY);
    }

    public CustomButton(String text, Color backgroundColor, Color foregroundColor, Color hoverColor){
        this.setText(text);
        this.defaultBackgroundColor = backgroundColor;
        this.defaultForegroundColor = foregroundColor;
        this.hoverColor = hoverColor;
        setBackgroundColorToDefault();
        setForegroundColorToDefault();
    }

    public void setBackgroundColorToDefault(){
        this.setBackground(defaultBackgroundColor);
    }

    public void setForegroundColorToDefault(){
        this.setForeground(defaultForegroundColor);
    }

    public void setDefaultBackgroundColor(Color backgroundColor) {
        this.defaultBackgroundColor = backgroundColor;
    }

    public void setDefaultForegroundColor(Color foregroundColor) {
        this.defaultForegroundColor = foregroundColor;
    }

    public Color getHoverColor() {
        return hoverColor;
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }
}
