// Solves the placement of slime/honey for geodesy-fabric
// FileName : "SlimeHoneyPlacer.java".

// For random numbers
import java.lang.Math;
import java.util.ArrayList;
 
class SlimeHoneyPlacer
{
    class Coord {
        int x;
        int y;
    }

    class LineInfo {
        boolean leftRight;
        Coord topLeftCoord;
        Coord bottomRightCoord;
    }
    
    static final int MAX_SEGMENT_SIZE = 12;
    static final int MIN_LINE_LENGTH = 3;

    // Gotten from ilmango video including 1 space around perimeter
    static final int HEIGHT = 17;
    static final int WIDTH = 17;

    // What values of the grid correspond to. 
    // TODO: Change these to enumes or something else.
    static final int CAN_COVER = 0;
    static final int NEED_TO_COVER = 1;
    static final int CANT_COVER = 2;
    static final int SLIME = 3;
    static final int HONEY = 4;
    static final int NOT_COVERED = 5;

    public static void main(String args[])
    {
        int HEIGHT = 17;
        int WIDTH = 17;
        int[][] grid = genTestGrid();
        prettyPrintGrid(grid);
    }

    public int[][] genCover(int[][] grid) {
        int[][] slimeHoneyGrid = new int[HEIGHT][WIDTH];

        // Init to NOT_COVERED
        for ( int i = 0 ; i < HEIGHT ; i++ ) {
            for ( int j = 0 ; j < WIDTH ; j++ ) {
                slimeHoneyGrid[i][j] = NOT_COVERED;
            }
        }

        // IDEA: For each block that isn't covered that should be, try to find
        // the direction that is most locally most lengthy. Then fill in the
        // direction of that.


        // We should randomly seed this a few times before doing the brute force
        // search to increase odds of finding a good solution.
        for ( int i = 0 ; i < HEIGHT ; i++ ) {
            for ( int j = 0 ; j < WIDTH ; j++ ) {
                if ( slimeHoneyGrid[i][j] == NOT_COVERED && grid[i][j] == NEED_TO_COVER) {
                    slimeHoneyGrid = greedyLocalCover(i,j,grid,slimeHoneyGrid);
                }
            }
        }

        return slimeHoneyGrid;
    }

    // Returns the new slimeGrid array.
    public int[][] greedyLocalCover(Coord pos, int[][] grid, int[][] slimeGrid) {
        // Try to find MIN_LINE_LENGTH segment horizontally and then virtically
        // then choose the one that is "better" by some metric. 

        int x = pos.x;
        int y = pos.y;
        slimeGrid[x][y] = SLIME;
        int totalCovered = 1;
        boolean breakCondition = false;

        // Get max length up-down segment 
        while ( totalCovered <= 12 && !breakCondition ) {
            if ( inBounds(x + 1,y) ) {

            } else if ( inBounds(x - 1,y) ) {

            }
        }

        // Get max length left-right segment 
        while ( totalCovered <= 12 && !breakCondition ) {
            if ( inBounds(x + 1,y) ) {

            } else if ( inBounds(x - 1,y) ) {

            }
        }

        return slimeGrid;
    }

    public int getLineInfo(Coord pos, int[][] grid, int[][] slimeGrid, boolean leftRight) {
        int x = pos.x;
        int y = pos.y;
        // How many neutral squares to consider before quitting
        int allowedNeutral = 2;
        int count
    }

    public boolean inBounds(int x, int y) {
        return (x >=0 && x < WIDTH && y >= 0 && y < HEIGHT);
    }


    public static void prettyPrintGrid(int[][] grid) {
        String ESC = "\u001b[";
        String block = "██";
        String black    = ESC + "0;30m";
        String red      = ESC + "0;31m";
        String green    = ESC + "0;32m";
        String yellow   = ESC + "0;33m";
        String purple   = ESC + "0;35m";
        String reset    = ESC + "0m";

        String colorGood = purple + block + reset;
        String colorBad = black + block + reset;
        String colorNeutral = reset + block + reset;
        String colorSlime = green + block + reset;
        String colorHoney = yellow + block + reset;
        for ( int i = 0 ; i < HEIGHT ; i++ ) {
            for ( int j = 0 ; j < WIDTH ; j++ ) {
                switch(grid[i][j]) {
                    case CAN_COVER:
                        System.out.print(colorNeutral);
                        break;
                    case NEED_TO_COVER:
                        System.out.print(colorGood);
                        break;
                    case CANT_COVER:
                        System.out.print(colorBad);
                        break;
                    case SLIME:
                        System.out.print(colorSlime);
                        break;
                    case HONEY:
                        System.out.print(colorHoney);
                        break;
                    default:
                        System.out.print("!!");
                }
            }
            System.out.println("");
        }
    }

    public static int[][] genTestGrid() {
        int[][] grid = new int[HEIGHT][WIDTH];
        double randVal;

        // 0: Doesn't matter
        // 1: Needs to be covered
        // 2: Can't be covered
        for ( int i = 0 ; i < HEIGHT ; i++ ) {
            for ( int j = 0 ; j < WIDTH ; j++ ) {
                // Makes the shape more circular. Tuned to look kind of right
                if ( inSquare(i,j) ) {
                    randVal = Math.random();
                    if ( randVal < 0.1 ) {
                        // Doesn't matter
                        grid[i][j] = CAN_COVER;
                    } else if ( randVal < 0.4 )  {
                        // Can't be covered
                        grid[i][j] = CANT_COVER;
                    } else {
                        // Needs to be covered
                        grid[i][j] = NEED_TO_COVER;
                    }
                } else { 
                    // Outside of square set free
                    grid[i][j] = CAN_COVER;
                }
            } // Loop over second index
        } // Loop over first index

        return grid;
    }

    // Takes in i and j and returns false if they are outside square center
    private static boolean inSquare(int i, int j) {
        // We do minus 2 here to ensure the boundary is always free to place
        double centerX = (WIDTH-2) / 2.0;
        double centerY = (HEIGHT-2) / 2.0;
        double radius = Math.min(centerX, centerY);

        if ( Math.abs(i - centerX) + Math.abs(j - centerY) > radius ) {
            return false;
        } else {
            return true;
        }

    }
}
