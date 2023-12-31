package Model.Classes;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import Model.Enums.LocationState;
import Model.Structs.Location;

public class MazeChooser 
{
    public static Maze getChoosenMaze() throws Exception
    {
        File file = MazeChooser.getChoosenFile();
        if(file == null)
        {
            return null;
        }
        else
        {
            int[][] matrix = MazeChooser.getMatrixFromFile(file);
            return MazeChooser.convertMatrixToMaze(matrix);
        }
    }

    private static File getChoosenFile()
    {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) 
        {
           return fileChooser.getSelectedFile();
        }
        else
        {
            return null;
        }
    }

    private static int[][] getMatrixFromFile(File fileParameter) throws Exception
    {
        Scanner scanner;

        ArrayList<Integer> values = new ArrayList<Integer>();
        scanner = new Scanner(fileParameter);
        while(scanner.hasNextInt())
        {
            values.add(scanner.nextInt());
        }
        scanner.close();

        int numberOfRows = 0;
        scanner = new Scanner(fileParameter);
        while(scanner.hasNextLine())
        {
            numberOfRows++;
            scanner.nextLine();
        }
        scanner.close();

        int numberOfColumns = values.size() / numberOfRows;

        int[][] matrix = new int[numberOfRows][numberOfColumns];
        for(int y = 0; y < matrix.length; y++)
        {
            for(int x = 0; x < matrix[0].length; x++)
            {
                matrix[y][x] = values.get((y * matrix[0].length) + x);
            }
        }

        return matrix;
    }

    private static Maze convertMatrixToMaze(int[][] matrixParameter) throws Exception
    {
        int sizeX = matrixParameter[0].length;
        int sizeY = matrixParameter.length;
        LocationState[][] matrix = new LocationState[sizeY][sizeX];
        Location startLocation = null;
        Location endLocation = null;

        for(int y = 0; y < sizeY; y++)
        {
            for(int x = 0; x < sizeX; x++)
            {
                int locationStateIndex = matrixParameter[y][x];
                LocationState locationState = LocationState.values()[locationStateIndex];
                switch(locationState)
                {
                    case BLOCKED: case NOT_BLOCKED:     
                        break;
                    case START:
                        startLocation = new Location(x, y);
                        locationState = LocationState.NOT_BLOCKED;
                        break;
                    case END:
                        endLocation = new Location(x, y);
                        locationState = LocationState.NOT_BLOCKED;
                        break;
                }
                matrix[y][x] = locationState;
            }
        }

        if((startLocation == null) || (endLocation == null))
        {
            throw new Exception("Start or End Location is 'null'");
        }

        return new Maze(matrix, startLocation, endLocation);
    }
}
