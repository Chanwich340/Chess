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

        System.out.println(side + ": Make your move using Chess Notation");

        // Get chess notation from the user
        String chessAnswer = scanner.nextLine();

        // Variables to fill in to match ETangent notation
        String first2chars = "a2";
        String last2chars = "a3";

        // Variable to evaluate the current Pieces on the board
        //--> get the board object from the Board class, board.getPieces() returns a 2D array of Piece objects
        Piece[][] pieces = board.getPieces();

        // CASE 1: PAWN MOVE

        // 1a. Check if there are only 2 characters
        if (chessAnswer.length() == 2) {

            // 1b. If so, record the last 2 characters as last2chars
            last2chars = chessAnswer;
            // 1c. Find all the Pawns on the Board 

            //--> loop through the 2Darray to find the desired Piece object, if you call a Piece object's toString() method, the first character should be what you're looking for
            for(int r=0; r < 8; r++){
                for(int c=0; c<8; c++){
                    Piece p = pieces[r][c];
                    if (p != null && p.toString().charAt(0) == 'P' && p.getSide() == side) {

                        System.out.println("Found the correct Piece type, checking... " + r + "," + c + side);

                        // 1d. When you find the desired Piece (P), check if going from their current position to the end position is a Valid Move
                        // --> use isValid() method in this class
                        String startPosition = "" + (char)('a' + c) + (8 - r);
                        String endPosition = last2chars;
                        String eMove = startPosition + endPosition;

                        // 1e. If you find a validMove, store the startingPosition as first2chars 
                        if (board.isValid(moveOf(eMove), side)) {   //<--- use the isValid() method in the Board class, not Human class
                            System.out.println("Found a valid move! " + eMove);
                            first2chars = startPosition;
                            break;
                        }
                    }
                }
            }
        }       

        // CASE 2: FACE PIECE TO SQUARE MOVES (Example: Nf3, Knight moves to f3 OR Nxf3, Knight takes pawn at f3)
        else if(chessAnswer.length() == 3 
            || (chessAnswer.length() ==4 && chessAnswer.substring(1,2).equals("x"))){

            // Remove the 'x' if a capture
            if (chessAnswer.contains("x"))  {
                chessAnswer = chessAnswer.replace("x", "");
                System.out.println("Face captures pawn");
            }

            // 2a. Record the piece moving (example N) 
            // --> use substring on 1st character
            String pieceMoving = chessAnswer.substring(0, 1);
            System.out.println("CASE 2: moving Piece: " + pieceMoving);

            // 2b. Record the endLocation of the piece 
            // --> use substring on last 2 characters & save to last2chars variable
            last2chars = chessAnswer.substring(chessAnswer.length() - 2);
            System.out.println("C2: last2: " + last2chars);

            // 2c. Find all the Knight Pieces on the Board (g1 and b1) 
            //--> loop through the 2Darray to find the desired Piece object, if you call a Piece object's toString() method, the first character should be what you're looking for
            for(int r=0; r < 8; r++){
                for(int c=0; c<8; c++){
                    Piece p = pieces[r][c];
                    if (p != null && p.toString().charAt(0) == pieceMoving.charAt(0) && p.getSide() == side) {

                        System.out.println("Found the correct Piece type, checking... " + r + "," + c + side);

                        // 2d. When you find the desired Piece (N), check if going from their current position to the end position is a Valid Move
                        // --> use isValid() method in this class
                        String startPosition = "" + (char)('a' + c) + (8 - r);
                        String endPosition = last2chars;
                        String eMove = startPosition + endPosition;

                        // 2e. If you find a validMove, store the startingPosition as first2chars 
                        if (board.isValid(moveOf(eMove), side)) {   //<--- use the isValid() method in the Board class, not Human class
                            first2chars = startPosition;
                            break;
                        }
                    }
                }
            }
        }


        // CASE 3: CAPTURES (FACE TO FACE or PAWN TO PAWN)
        else if (chessAnswer.contains("x"))  {

            // 3a. Check if it's pawn to pawn

                chessAnswer = chessAnswer.replace("x", "");

                //3b. Set first2chars and last2chars easily

            // 3c. If not, then it's a Face to Face


                // 3d. Get the first piece letter

                // 3e. Get the captured piece letter

                // 3f. Loop through board to find all first pieces

                    // 3g. Loop through board to find all second pieces

                        // 3h. Check if move is valid

                            // 3i. if so, set first2chars and last2chars


        }



        // Setup the ETangent notation to be used
        String answer = first2chars + last2chars;
        System.out.println("Using move.... " + answer);

        while (true) {
            if (isValid(answer)) {
                if (board.isValid(moveOf(answer), side)) {
                    break;
                } else {
                    System.out.println("Illegal Move!");
                }
            } else {
                System.out.println("Incorrect notation!");
            }
            board.print();   
            answer = scanner.nextLine();           
        }
        
        return moveOf(answer);
    }

    public static boolean isValid(String answer) {

        if(answer.length() == 5) {
            return isValid(answer.substring(0,4)) && "QRBN".indexOf(answer.substring(4)) != -1;
        }

        if (answer.length() != 4) {
            return false;
        }
        if (answer.charAt(0) == answer.charAt(2) && answer.charAt(1) == answer.charAt(3)) {
            return false;
        }

        return answer.length() == 4 && "abcdefgh".indexOf(answer.charAt(0)) != -1 && "abcdefgh".indexOf(answer.charAt(2)) != -1 && "12345678".indexOf(answer.charAt(1)) != -1 && "12345678".indexOf(answer.charAt(3)) != -1;
    }

    //assumes str follows format of a String where [char, int, char, int] (isValid was called)
    public static Board.Move moveOf(String str) {
        if (str.length() == 5) {
            Board.Move subMove = moveOf(str.substring(0, 4));
            PieceSupplier promotionPiece;
            switch(str.substring(4)) {
                case "Q":
                    promotionPiece = (side) -> new Queen(side);
                    break;
                case "R":
                    promotionPiece = (side) -> new Rook(side);
                    break;
                case "B":
                    promotionPiece = (side) -> new Bishop(side);
                    break;
                case "N":
                    promotionPiece = (side) -> new Knight(side);
                    break;
                default:
                    throw new RuntimeException("is valid no worky");
            }
            
            return new Board.Promotion(subMove.startX, subMove.startY, subMove.endX, subMove.endY, promotionPiece);
        }

        return new Board.Move(letterToNumber(str.charAt(0)), 8 - Character.getNumericValue(str.charAt(1)), letterToNumber(str.charAt(2)), 8 - Character.getNumericValue(str.charAt(3)));
    }

    public static int letterToNumber(char character) {
        return character - 'a';
    }
}
// matthewsanceheeess
