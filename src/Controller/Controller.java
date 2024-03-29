package Controller;

import java.util.Stack;
import Model.Model;
import Model.Classes.Maze;
import Model.Structs.Location;
import View.View;

public class Controller 
{
    private Model model;
    private View view;

    public Controller(Model modelParameter, View viewParameter)
    {
        this.model = modelParameter;
        this.view = viewParameter;

        this.view.addOnClickActionListenerToLoadFileButton(event -> {this.onLoadFileButtonClicked();});
        this.view.addOnClickActionListenerToStartButton(event -> {this.onStartButtonClicked();});
        this.view.setStartButtonEnabled(false);
    }

    private void onLoadFileButtonClicked()  
    {
        if(this.model.loadFile())
        {
            this.view.resetStagesTextArea();
            this.view.setStartButtonEnabled(true);
        }
    }

    private void onStartButtonClicked()
    { 
        this.view.resetStagesTextArea();
        this.model.start(this.view.getPopulationSizeSpinnerValue(), this.view.getDelaySpinnerValue(), this.view.isVisualizeCheckboxChecked());
    }

    public void setMazePanel(Maze mazeParameter)
    {
        this.view.setMazePanel(mazeParameter);
    }

    public void refreshMazePanel(Maze mazeParameter, Location[] individualsLocations)
    {
        this.view.refreshMazePanel(mazeParameter, individualsLocations);
    }

    public void showAlert(String stringParameter)
    {
        this.view.showAlert(stringParameter);
    }

    public void printStageInfo(String stringParameter)
    {
        this.view.printStageInfo(stringParameter);
    }

    public void showPath(Maze mazeParameter, Stack<Location> pathParameter)
    {
        this.view.showPath(mazeParameter, pathParameter);
    }


}
