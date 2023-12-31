package View.Components;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class PopulationSizeSpinner extends JSpinner
{
    public PopulationSizeSpinner()
    {
        super();

        this.setModel(new SpinnerNumberModel(10, 10, 10000, 1.0));
    }
}
