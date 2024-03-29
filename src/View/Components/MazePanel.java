package View.Components;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class MazePanel extends JPanel
{
    public MazePanel()
    {
        super();

        this.setBackground(Color.BLACK);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
    }
}
