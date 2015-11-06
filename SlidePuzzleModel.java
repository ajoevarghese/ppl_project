

/////////////////////////////////////////// class SlidePuzzleModel
public class SlidePuzzleModel {
    //============================================== instance vars
    private int _rows;
    private int _cols;
    private String _text;
    private Tile[][] _contents;  // All tiles.


    //================================================= constructor
    public SlidePuzzleModel(int rows, int cols, String text) {
        assert text.length() == rows * cols;
        _rows = rows;
        _cols = cols;
        _text = text;
        _contents = new Tile[_rows][_cols];
        initialize();
    }

    //================================================ initialize
    public void initialize() {
        for (int r=0; r<_rows; r++) {
            for (int c=0; c<_cols; c++) {
                int pos = (r * _cols) + c;
                String tileFace = _text.substring(pos, pos+1);
                _contents[r][c] = new Tile(r, c, tileFace);
            }
        }
    }

    //============================================= getNumberOfRows
    public int getNumberOfRows() {
        return _rows;
    }


    //============================================= getNumberOfCols
    public int getNumberOfCols() {
        return _cols;
    }


    //===================================================== getFace
    // Return the string to display at given row, col.
    public String getFace(int row, int col) {
        return _contents[row][col].getFace();
    }


    //===================================================== shuffle
    // Shuffle the tiles in random order.
    //   Note: This shuffle algorithm distributes the tiles
    //         almost completely randomly.   The extremely
    //         small deviation from random can be ignored.
    public void shuffle() {
        //-- Shuffle - Exchange each tile with random tile.
        for (int r=0; r<_rows; r++) {
            for (int c=0; c<_cols; c++) {
                exchangeTiles(r, c, (int)(Math.random()*_rows)
                                  , (int)(Math.random()*_cols));
            }
        }
    }


    //==================================================== moveTile
    // Move a tile to empty position beside it, if possible.
    // Return true if it was moved, false if not legal.
    // BAD BAD BAD - should move tile here, not in checkEmpty
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public boolean moveTile(int r, int c) {
        //--- It's a legal move if the empty cell is next to it.
        return checkEmpty(r, c, -1, 0) || checkEmpty(r, c, 1, 0)
            || checkEmpty(r, c, 0, -1) || checkEmpty(r, c, 0, 1);
    }


    //================================================== checkEmpty
    // Check to see if there is an empty position beside tile.
    // Return true and exchange if possible, else return false.
    private boolean checkEmpty(int r, int c, int rdelta, int cdelta) {
        int rNeighbor = r + rdelta;
        int cNeighbor = c + cdelta;
        //--- Check to see if this neighbor is on board and is empty.
        if (isLegalRowCol(rNeighbor, cNeighbor)
                  && _contents[rNeighbor][cNeighbor].isEmpty()) {
            exchangeTiles(r, c, rNeighbor, cNeighbor);
            return true;
        }
        return false;
    }


    //=============================================== isLegalRowCol
    // Check for legal row, col
    public boolean isLegalRowCol(int r, int c) {
        return r>=0 && r<_rows && c>=0 && c<_cols;
    }


    //=============================================== exchangeTiles
    // Exchange two tiles.
    private void exchangeTiles(int r1, int c1, int r2, int c2) {
        Tile temp = _contents[r1][c1];
        _contents[r1][c1] = _contents[r2][c2];
        _contents[r2][c2] = temp;
    }


    //=================================================== isGameOver
    public boolean isGameOver() {
        for (int r=0; r<_rows; r++) {
            for (int c=0; c<_rows; c++) {
                Tile trc = _contents[r][c];
                if (!trc.isInFinalPosition(r, c)) {
                    return false;
                }
            }
        }

        //--- Falling thru loop means nothing out of place.
        return true;
    }
}//end class SlidePuzzleModel
