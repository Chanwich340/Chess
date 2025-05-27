public class Piece {
    
    private int posX; //position of x
    private int posY; //position of y
    private boolean isWhite; //if it's white or black
    private String type; //what type of piece
    private boolean hasMoved = false; //checks if piece moved each move. "comparing positions" by default, no pieces have moved

    public Piece(int posX, int posY, boolean isWhite, String type)   {
        this.posX = posX;
        this.posY = posY;
        this.isWhite = isWhite;
        this.type = type;
    } 

    //getters
    public int getPosX()  {
        return posX;
    }
    
    public int getPosY()  {
        return posY;
    }

    public boolean isWhite()  {
        return isWhite;
    }

    public String getType()  {
        return type;
    }

    public boolean hasMoved()  {
        return hasMoved;
    }
    
    //setters

    public void setPosX(int posX)  {
        this.posX = posX;
    }

    public void setPosY(int posY)   {
        this.posY = posY;
    }

    public void setWhite(boolean isWhite)  {
        this.isWhite = isWhite;
    }

    public void setType(String type)  {
        this.type = type.toString();
    }

    public void setHasMoved(boolean hasMoved)  {
        this.hasMoved = hasMoved;
    }

    public void moveTo(int newX, int newY) {
        this.posX = newX;
        this.posY = newY;
        this.hasMoved = true; // Update hasMoved to true when the piece is moved
    }

    //methods

    //create default moveablePos method 



}
