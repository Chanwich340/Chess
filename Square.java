import processing.core.PApplet;

public class Square extends GridTile {

    private Piece piece; // The piece on this square
    private String noMark = " ";

    // Constructor
    public Square(PApplet p, int posX, int posY, String mark) {
        super(p, mark, new GridLocation(posX, posY));
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
