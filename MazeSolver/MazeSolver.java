import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// 1: north, 2:east, 3:south, 4:west
public class MazeSolver {
    public static char[][] readMazeGrid(String input) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(input));
        String line;
        int rows = 0;

        while((line = reader.readLine()) != null) {
            rows++;
        }

        reader.close();
        reader = new BufferedReader(new FileReader(input));

        char [][] mazeGrid = new char[rows][];

        int row = 0;
        while((line = reader.readLine()) != null) {
            mazeGrid[row] = line.toCharArray();
            row++;
        }
        reader.close();

        return mazeGrid;
    }
    public static int turn_left(int current_direction){
        if (current_direction==1){
            current_direction=4;
        }
        else if (current_direction==4) {
            current_direction=3;
        }
        else if(current_direction==3){
            current_direction=2;
        }
        else if(current_direction==2){
            current_direction=1;
        }
        return current_direction;
    }
    public static String move_forward(int direction, int x, int y){
        if (direction==1){
            x--;
        }
        else if(direction==2){
            y++;
        }
        else if(direction==3){
            x++;
        }
        else if(direction==4){
            y--;
        }
        return String.format("%d,%d",x,y);
    }
    public static boolean isAvailable(char[][] maze_grid,int current_direction ,int x, int y, boolean[][] visited){
        int liar_x= x;
        int liar_y=y;
        int row = maze_grid.length;
        int column = maze_grid[0].length;
        switch (current_direction) {
            case 1:
                liar_x--;
                break;
            case 2:
                liar_y++;
                break;
            case 3:
                liar_x++;
                break;
            case 4:
                liar_y--;
                break;
        }
        if (liar_x<0 || liar_x>row-1 || liar_y<0 || liar_y>column-1){

            return false;
        }
        if(visited[liar_x][liar_y])
            return false;
        return maze_grid[liar_x][liar_y] != '#';


    }
    public static int size_of_full_path_tracker(String [] path_tracker){
    int idx=0;
    for (String s: path_tracker){
        if(s.equals("-")){
            break;
        }
        idx++;
    }
    return idx;
}
    public static void main(String[] args) throws IOException {
        int count_steps =0;
        final int MAX_STEPS=1000;

        if (args.length < 3) {
        System.out.println("Please enter 3 arguments(x coordinate, y coordinate, maze.txt file path).");
        return;
        }
        String arg1 = args[0]; // starting x coordinate
        String arg2 = args[1]; //starting y coordinate
        String arg3 = args[2]; //txt file
        int x =Integer.parseInt(arg1);
        int y = Integer.parseInt(arg2);

        char[][] maze_grid= readMazeGrid(arg3);

        boolean[][] visited = new boolean[maze_grid.length][maze_grid[0].length];
        for(int i=0; i< maze_grid.length; i+=1){
            for(int j =0; j<maze_grid[0].length; j+=1){
                visited[i][j]=false;
            }
        }

        int row = maze_grid.length;
        int column = maze_grid[0].length;
        int maze_size =row*column;
        String [] path_tracker = new String[maze_grid.length*maze_grid[0].length];
        for (int i=0; i<maze_size; i++){
            path_tracker[i]="-";
        }
        
        visited[x][y] =true; // set the starting point as visited
        path_tracker[0] = String.format("%d,%d",x,y);

        while (maze_grid[x][y] != 'X'){
            int how_many_times_turned=0;
            int current_direction=2;
            if (count_steps>= MAX_STEPS){
                System.out.print("There is no solution!");
                return; //to exit from main function, to stop the program
            }

            // if initial direction is available , go in that way
            if(isAvailable(maze_grid,current_direction,x,y,visited)){
                String resultant = move_forward(current_direction, x, y);
                count_steps++;
                x = Integer.parseInt(resultant.split(",")[0]);
                y = Integer.parseInt(resultant.split(",")[1]);
                visited[x][y] =true;
                int index= size_of_full_path_tracker(path_tracker);
                path_tracker[index]=String.format("%d,%d",x,y);
            }
            else{
                while(!isAvailable(maze_grid,current_direction,x,y,visited)&& how_many_times_turned<4){
                    current_direction=turn_left(current_direction);
                    how_many_times_turned+=1;
                }
                // If have turned 4 times that means I have turned 1 full circle around myself and saw there is no available tile around me
                // after seeing that there is no available tile around me, I go back to the tile that I came from
                if(how_many_times_turned==4){
                    int index= size_of_full_path_tracker(path_tracker);

                    //if there is no any tile in the path_tracker the following code will give an error...
                    //...So I'm checking here that situation and end the program with "There is no solution!"
                    if(index-2<0){
                        System.out.println("There is no solution!");
                        return;
                    }
                    // going back to the previous tile
                    String resultant = path_tracker[index-2];
                    x = Integer.parseInt(resultant.split(",")[0]);
                    y = Integer.parseInt(resultant.split(",")[1]);
                    path_tracker[index-1]="-"; // Removing the tile we come from , from path_tracker
                }
                if(isAvailable(maze_grid,current_direction,x,y,visited)){
                    String resultant = move_forward(current_direction, x, y);
                    count_steps++;
                    x = Integer.parseInt(resultant.split(",")[0]);
                    y = Integer.parseInt(resultant.split(",")[1]);
                    visited[x][y] =true;
                    int where_i_put= size_of_full_path_tracker(path_tracker);
                    path_tracker[where_i_put]=String.format("%d,%d",x,y);
                }
            }
       }
        System.out.println("x:"+x+", y:"+y);
    }
}
