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
    public int getNumRows() {
        return this.numRows;
    }
    public int getNumCols() {
        return this.numCols;
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
}
