public class Square extends GridTile {

    private Piece piece; // The piece on this square

    // Constructor
    public Square(int posX, int posY, String mark) {
        super(posX, posY, mark);
    }

    // Method to check if the square is empty
    public boolean isEmpty() {
        return this.getMark().equals(noMark);
    }

    // Method to clear the square
    public void clear() {
        this.setNewMark(noMark);
    }
    
}
