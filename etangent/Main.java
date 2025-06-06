public class Main {
    public static void main(String[] args) {
        Player player1 = new Human(Side.WHITE);
        Player player2 = new Bot(Side.BLACK, 4);

        Player currPlayer = player1;

        Board board = new Board(Board.startBoard());

        board.print();

        while(true) {
            Player otherPlayer = currPlayer == player1 ? player2 : player1;

            board = board.movePiece(currPlayer.getInput(board), currPlayer.getSide());
            board.print();

            if(board.legalMoves(otherPlayer.getSide()).size() == 0 && board.isCheck(otherPlayer.getSide())) {
                System.out.println(currPlayer + " is the GOAT!");
                break;
            } else if(board.isCheck(otherPlayer.getSide())) {
                System.out.println("AAAAAAAAAAAAAAAAAAAAA CHEEEEEEEEEEECK!!!!!!!");
            } else if(board.legalMoves(otherPlayer.getSide()).size() == 0) {
                System.out.println("draw... womp womp");
                break;
            }
            
            currPlayer = otherPlayer;
        }
    }
}                                                    