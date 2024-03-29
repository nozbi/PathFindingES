package Model;

import javax.swing.Timer;
import Controller.Controller;
import Model.Classes.Maze;
import Model.Classes.MazeChooser;
import Model.Classes.Population;
import Model.Enums.Status;
import Model.Structs.Location;

public class Model 
{
    private Controller controller;
    private Maze maze;
    private Timer timer;
    private Population population;
    private int moveIndex;
    private int stageIndex;
    private int maxMoveIndex;
    private boolean visualize;

    public void setController(Controller controllerParameter)
    {
        this.controller = controllerParameter;
    }

    public boolean loadFile() 
    {
        try
        {
            Maze newMaze = MazeChooser.getChoosenMaze();

            if(newMaze != null)
            {
                if(this.timer != null)
                {
                    this.timer.stop();
                }
                this.maze = newMaze;
                this.controller.setMazePanel(this.maze);
                this.controller.refreshMazePanel(this.maze, new Location[0]);
                this.maxMoveIndex = (int)((this.maze.getSizeX() * this.maze.getSizeY()) * (2.0 / 3.0));
                return true;
            }
        }
        catch(Exception exception)
        {
            this.controller.showAlert("Incorrect file format!");
        } 
        return false;
    }

    public void start(int populationSizeParameter, int delayParameter, boolean visualizeParameter)
    { 
        if(this.timer != null)
        {
            this.timer.stop();
        }
        this.moveIndex = 0;
        this.stageIndex = 0;
        this.population = new Population(populationSizeParameter, this.maze);

        this.visualize = visualizeParameter;
        if(this.visualize)
        {
            this.controller.refreshMazePanel(this.maze, this.population.getIndividualsLocations());
        }
        else
        {
            this.controller.refreshMazePanel(this.maze, new Location[0]);
        }

        this.timer = new Timer(delayParameter, actionEvent -> 
        {
            this.nextMove();
        });
        this.timer.setRepeats(true);
		this.timer.setInitialDelay(delayParameter);
        this.timer.start();
    }

    private void nextMove()
    {
        Status status = this.population.moveIndividuals();

        if(this.visualize)
        {
            this.controller.refreshMazePanel(this.maze, this.population.getIndividualsLocations());
        }

        switch (status) 
        {
            case PATH_FOUND:
                this.timer.stop();
                this.controller.showPath(this.maze, this.population.getPath());
                this.controller.showAlert("Path found!");
                break;
            case NO_MORE_MOVES:
                this.nextStage();
                break;
            case MOVED:
                this.moveIndex++;
                if(this.moveIndex == this.maxMoveIndex)
                {
                    this.nextStage();
                }
                break;
        }
    }

    private void nextStage()
    {
        this.controller.printStageInfo("Stage: " + this.stageIndex);

        this.population = new Population(this.population);

        this.moveIndex = 0;
        this.stageIndex++;

        if(this.stageIndex == 10000)
        {
            this.timer.stop();
            this.controller.showAlert("Path not found!");
        }
        else
        {
            if(this.visualize)
            {
                this.controller.refreshMazePanel(this.maze, this.population.getIndividualsLocations());
            }
        }
    }
}
