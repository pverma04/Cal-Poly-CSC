public final class Viewport
{
    private int row;
    private int col;
    private int numRows;
    private int numCols;

    public Viewport(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
    }

    public int getCol() {
        return this.col;
    }
    public void setCol(int col) {
        this.col = col;
    }
    public int getRow() {
        return this.row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public int getNumRows() {
        return this.numRows;
    }
    public int getNumCols() {
        return this.numCols;
    }

    public  void shift(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public  Point viewportToWorld( int col, int row) {
        return new Point(col + this.getCol(), row + this.getRow());
    }

    public  boolean contains(Point p) {
        return p.getY() >= this.getRow() && p.getY() < this.getRow() + numRows
                && p.getX() >= this.getCol() && p.getX() < this.getCol() + this.getNumCols();
    }
    public  Point worldToViewport(int col, int row) {
        return new Point(col - this.getCol(), row - this.getRow());
    }

}
