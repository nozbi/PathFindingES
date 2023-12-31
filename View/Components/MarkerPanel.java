package View.Components;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridLayout;

public class MarkerPanel extends JPanel
{
    public MarkerPanel(Color colorParameter)
    {
        super();

        this.setOpaque(false);
        this.setLayout(new GridLayout(3, 3));
        this.add(new TransparentPanel());
        this.add(new TransparentPanel());
        this.add(new TransparentPanel());
        this.add(new TransparentPanel());
        JPanel panel = new JPanel();
        panel.setBackground(colorParameter);
        this.add(panel);
        this.add(new TransparentPanel());
        this.add(new TransparentPanel());
        this.add(new TransparentPanel());
        this.add(new TransparentPanel());
    }
}
