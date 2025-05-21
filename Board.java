import java.util.ArrayList;

public class Board  {
    //set up the board, limitations/boundaries, set up 8x8 (columns are letters, rows are numbers)
    private ArrayList<ArrayList<Piece>> grid;

    public Board()  {
        initializeBoard();
    }

    private void initializeBoard()  {
        grid = new ArrayList<>(8);

        //creates row of board numbered 1-8
        for (int row = 0; row < 8; row++)    {
            ArrayList<String> currentRow = new ArrayList<>(8);

            //creates column of board lettered a-h
            for (int col = 0; col < 8; col++) {
                currentRow.add("");
            }

            // grid.add(currentRow);
        }
    }

    //make chess notation (like: a1, f4, g6, etc.) the chess pieces will use this to know which grid to go into.
}
    
