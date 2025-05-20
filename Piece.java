public class Piece {
    
    private int posX; //position of x
    private int posY; //position of y
    private boolean isWhite; //if it's white or black
    private String type; //what type of piece

    public Piece(int posX, int posY, boolean isWhite, String type)   {
        this.posX = posX;
        this.posY = posY;
        this.isWhite = isWhite;
        this.type = type;
    }



}
