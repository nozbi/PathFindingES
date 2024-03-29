package View.Components;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Component;
import java.awt.Dimension;

public class StagesScrollPanel extends JScrollPane
{
    public StagesScrollPanel(Component componentParameter)
    {
        super(componentParameter);

        this.setPreferredSize(new Dimension(100, this.getHeight()));
        this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    }
}
