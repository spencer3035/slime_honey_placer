// Solves the placement of slime/honey for geodesy-fabric
// FileName : "SlimeHoneyPlacer.java".

// For random numbers
import java.lang.Math;
 
class SlimeHoneyPlacer
{
    static final int MAX_SEGMENT_SIZE = 12;
    static final int MIN_LINE_LENGTH = 3;

    // What values of the grid correspond to
    static final int CAN_COVER = 0;
    static final int NEED_TO_COVER = 1;
    static final int CANT_COVER = 2;
    static final int SLIME = 3;
    static final int HONEY = 4;

    public static void main(String args[])
    {
        // Gotten from ilmango video including 1 space around perimeter
        int h = 17;
        int w = 17;
        int[][] grid = genTestGrid(h,w);
        prettyPrintGrid(grid, h, w);
    }


    public static void prettyPrintGrid(int[][] grid, int h, int w) {
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
        for ( int i = 0 ; i < h ; i++ ) {
            for ( int j = 0 ; j < h ; j++ ) {
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

    public static int[][] genTestGrid(int h, int w) {
        int[][] grid = new int[h][w];
        double randVal;

        // 0: Doesn't matter
        // 1: Needs to be covered
        // 2: Can't be covered
        for ( int i = 0 ; i < h ; i++ ) {
            for ( int j = 0 ; j < h ; j++ ) {
                // Makes the shape more circular. Tuned to look kind of right
                if ( inSquare(i,j,h,w) ) {
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
    private static boolean inSquare(int i, int j, int h, int w) {
        // We do minus 2 here to ensure the boundary is always free to place
        double centerX = (w-2) / 2.0;
        double centerY = (h-2) / 2.0;
        double radius = Math.min(centerX, centerY);

        if ( Math.abs(i - centerX) + Math.abs(j - centerY) > radius ) {
            return false;
        } else {
            return true;
        }

    }
}
