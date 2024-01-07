package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Stack;
import javax.swing.JOptionPane;
import Model.Classes.Maze;
import Model.Structs.Location;
import View.Components.Button;
import View.Components.CheckBox;
import View.Components.Frame;
import View.Components.Label;
import View.Components.LocationPanel;
import View.Components.MainPanel;
import View.Components.MarkerPanel;
import View.Components.MenuBar;
import View.Components.MazePanel;
import View.Components.PopulationSizeSpinner;
import View.Components.StagesScrollPanel;
import View.Components.StagesTextArea;
import View.Components.DelaySpinner;

public class View 
{
    private MazePanel mazePanel;
    private Button loadFileButton;
    private Button startButton;
    private DelaySpinner delaySpinner;
    private PopulationSizeSpinner populationSizeSpinner;
    private CheckBox visualizeCheckBox;
    private StagesTextArea stagesTextArea;

    public View()
    {
        //FRAME
        Frame frame = new Frame();

            //MENU BAR
            MenuBar menuBar = new MenuBar();

                this.loadFileButton = new Button("Load maze");
                menuBar.add(loadFileButton);

                this.startButton = new Button("Run");
                menuBar.add(this.startButton);

                menuBar.add(new Label("Delay:"));
                this.delaySpinner = new DelaySpinner();
                menuBar.add(this.delaySpinner);

                menuBar.add(new Label("Population size:"));
                this.populationSizeSpinner = new PopulationSizeSpinner();
                menuBar.add(this.populationSizeSpinner);

                this.visualizeCheckBox = new CheckBox("Visualize");
                menuBar.add(this.visualizeCheckBox);

            frame.setJMenuBar(menuBar);

            //MAIN PANEL
            MainPanel mainPanel = new MainPanel();
            frame.add(mainPanel);

                //MAZE PANEL
                this.mazePanel = new MazePanel();
                mainPanel.add(this.mazePanel, BorderLayout.CENTER);

               
                //SCROLL PANEL WITH STAGES TEXT AREA
                this.stagesTextArea = new StagesTextArea();
                StagesScrollPanel stagesScrollPanel = new StagesScrollPanel(stagesTextArea);
                mainPanel.add(stagesScrollPanel, BorderLayout.EAST);

        frame.setVisible(true);
    }

    public void setMazePanel(Maze mazeParameter)
    {
        int sizeX = mazeParameter.getSizeX();
        int sizeY = mazeParameter.getSizeY();
        this.mazePanel.removeAll();
        this.mazePanel.setLayout(new GridLayout(sizeY, sizeX));

        for(int i = 0; i < sizeX * sizeY; i++)
        {
            this.mazePanel.add(new LocationPanel());
        }

        int startPanelIndex = (mazeParameter.getStartLocationCopy().getY() * mazeParameter.getSizeX()) + mazeParameter.getStartLocationCopy().getX();
        ((LocationPanel)this.mazePanel.getComponent(startPanelIndex)).add(new MarkerPanel(Color.GREEN));

        int targetPanelIndex = (mazeParameter.getEndLocationCopy().getY() * mazeParameter.getSizeX()) + mazeParameter.getEndLocationCopy().getX();
        ((LocationPanel)this.mazePanel.getComponent(targetPanelIndex)).add(new MarkerPanel(Color.RED));

        this.mazePanel.revalidate();
        this.mazePanel.repaint();
    }

    public void refreshMazePanel(Maze mazeParameter, Location[] individualsLocationsParameter)
    {
        Component[] locationPanels = this.mazePanel.getComponents();
        for(int y = 0; y < mazeParameter.getSizeY(); y++)
        {
            for(int x = 0; x < mazeParameter.getSizeX(); x++)
            {
                int index = (y * mazeParameter.getSizeX()) + x;
                if(mazeParameter.isLocationNotBlocked(new Location(x, y)))
                {
                    locationPanels[index].setBackground(Color.WHITE);
                }
                else
                {
                    locationPanels[index].setBackground(Color.BLACK);
                }
            }
        }

        for(int i = 0; i < individualsLocationsParameter.length; i++)
        {
            Location location = individualsLocationsParameter[i];
            int index =  (location.getY() * mazeParameter.getSizeX()) + location.getX();
            locationPanels[index].setBackground(Color.ORANGE);
        }

        this.mazePanel.repaint();
    }

    public void showAlert(String stringParameter)
    {
        JOptionPane.showMessageDialog(null, stringParameter);
    }

    public void printStageInfo(String stringParameter)
    {
        this.stagesTextArea.append(stringParameter + "\n");
    }

    public void addOnClickActionListenerToLoadFileButton(ActionListener actionListenerParameter)
    {
        this.loadFileButton.addActionListener(actionListenerParameter);
    }

    public void addOnClickActionListenerToStartButton(ActionListener actionListenerParameter)
    {
        this.startButton.addActionListener(actionListenerParameter);
    }

    public void setStartButtonEnabled(boolean enabledParameter)
    {
        this.startButton.setEnabled(enabledParameter);
        this.delaySpinner.setEnabled(enabledParameter);
        this.populationSizeSpinner.setEnabled(enabledParameter);
        this.visualizeCheckBox.setEnabled(enabledParameter);
    }

    public int getDelaySpinnerValue()
    {
        return (int)(double)this.delaySpinner.getValue();
    }

    public int getPopulationSizeSpinnerValue()
    {
        return (int)(double)this.populationSizeSpinner.getValue();
    }

    public void showPath(Maze mazeParameter, Stack<Location> pathParameter)
    {
        this.refreshMazePanel(mazeParameter, new Location[0]);

        Component[] locationPanels = this.mazePanel.getComponents();
        for(int i = 0; i < pathParameter.size(); i++)
        {
            Location location = pathParameter.get(i);
            int index =  (location.getY() * mazeParameter.getSizeX()) + location.getX();
            locationPanels[index].setBackground(Color.YELLOW);
        }
        this.mazePanel.repaint();
    }

    public boolean isVisualizeCheckboxChecked()
    {
        return this.visualizeCheckBox.isSelected();
    }

    public void resetStagesTextArea()
    {
        this.stagesTextArea.setText(null);
    }
}
