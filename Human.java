import java.util.Scanner;

public class Human implements Player {
    public final Side side;
    private final Scanner scanner = new Scanner(System.in);

    public Human(Side side) {
        this.side = side;
    }

    @Override
    public String toString() {
        return side.toString();
    }

    @Override
    public Side getSide() {
        return side;
    }

    @Override
    public Board.Move getInput(Board board) {
        System.out.println(side + ": Make your move");
        board.print();

        while (true) {
            String answer = scanner.nextLine().trim().toLowerCase(); //moved this inside the loop to fix the bug where the first input was not checked

            if (isValid(answer)) {
                Board.Move move = moveOf(answer); // Convert the string to a move object
                if (board.isValid(move, side)) {// check if the move is legal
                    return move;
                } else {
                    System.out.println("Illegal Move!");
                }
            } else {
                System.out.println("Incorrect notation! Try using the format: a2a3, a7a8Q, etc."); //updated to help the user with notation if they don't know it
            }         
        }
    }

    public static boolean isValid(String move) {

        //for normal moves
        if (move.length() == 4) {
            return isValidSquare(move.substring(0,2)) && isValidSquare(move.substring(2,4)) && !move.substring(0,2).equals(move.substring(2,4));
        }

        // for promotion moves (special move)
        if (move.length() == 5) {
            return isValidSquare(move.substring(0,2)) && isValidSquare(move.substring(2,4)) && "QRBN".indexOf(move.charAt(4)) != -1 && !move.substring(0,2).equals(move.substring(2,4));
        }
        
        return false; //if the move is not 4 or 5 characters long, it is invalid
    }

    public static boolean isValidSquare(String square) {
        // Check if the square is exactly 2 characters long and contains valid characters such as 'a'-'h' for columns and '1'-'8' for rows
        if (square.length() == 2 && square.charAt(0) >= 'a' && square.charAt(0) <= 'h' && square.charAt(1) >= '1' && square.charAt(1) <= '8') {
            return true; // square must be exactly 2 characters long
        } else {
            return false;
        }
    }

    //assumes str follows format of a String where [char, int, char, int] (isValid was called)
    public static Board.Move moveOf(String moveStr) {

        //take start and end positions
        String start = moveStr.substring(0, 2);
        String end = moveStr.substring(2, 4);

        //convert to coords
        int startX = start.charAt(0) - 'a';
        int startY = 8 - start.charAt(1);
        int endX = end.charAt(0) - 'a';
        int endY = 8 - end.charAt(1);


        if (moveStr.length() == 5) {
            
            char promoChar = moveStr.charAt(4); // the promotion piece character
            PieceSupplier promotionPiece;
            switch(Character.toUpperCase(promoChar)) {
                case 'Q':
                    promotionPiece = (side) -> new Queen(side);
                    break;
                case 'R':
                    promotionPiece = (side) -> new Rook(side);
                    break;
                case 'B':
                    promotionPiece = (side) -> new Bishop(side);
                    break;
                case 'N':
                    promotionPiece = (side) -> new Knight(side);
                    break;
                default:
                    throw new RuntimeException("is valid no worky");
            }
            
            return new Board.Promotion(startX, startY, endX, endY, promotionPiece); //removed promotionPiece from the constructor bc changed it
        }
        return new Board.Move(startX, startY, endX, endY); //normal move
    }
}
