import java.util.*;

public class Board
{
    // represents the game board.
    
    // members
    private Cell[][] _the_cells;
    private int _rows, _cols;
    private int _cells_opened;
    private int _flags_placed;
    private int _num_mines;
    
    
    // methods

    /*
      name: constructor
      desc: asks the users for rows, cols, num mines and
      then sets up the board
      params: Scanner scan, Random gen
      return: n/a
      1) ask the user for the numbers of rows, cols, and mines
      2) allocate a 2d array of cells with the specified 
      number of cols and rows
      3) for each pointer in the 2d array allocate a new
      cell object. 
      (ex:
      for ( r = 0; r < _rows; r ++ )
      for ( c = 0; c < _cols; c ++ )
      _the_cells[c][r] = new Cell();
      )
      4) populate the board with mines (call populate method)
      5) set _flags_placed and _cells_opened to 0
    */
    public Board(Scanner scan, Random gen)
    {
	int r,c;

	System.out.print("Rows: ");
	_rows = scan.nextInt();
	System.out.print("Cols: ");
	_cols = scan.nextInt();
	System.out.print("Number of mines: ");
	_num_mines = scan.nextInt();

	_cells_opened = 0;
	_flags_placed = 0;

	_the_cells = new Cell[_cols][_rows];

	for ( r = 0; r < _rows; r ++ )
	    for ( c = 0; c < _cols; c ++ )
		_the_cells[c][r] = new Cell();

	populate(gen);
    }

    /*
    // toString
    name: toString
    desc: prints out the board as a giant string
    params: none
    return: String
    1) set a string called out to the empty string
    2) for each row, do steps 3-5
    3)   for each col, do step 4
    4)      add the return of _the_cells[c][r] toString method 
    to out
    5)   add the new line character to out
    6) return out
    */
    public String toString()
    {
	int r,c;
	String out = "";

	for ( c = 0; c < _cols; c++ )
	    out += c + " ";
	
        out += "\n";
	
        for ( r = 0; r < _rows; r ++ )
            {
                for ( c = 0; c < _cols; c ++ )
                    out += _the_cells[c][r] + " ";
		
                out += ") " + r + " \n";
            }

	return out;
    }

    /*
      name: open_cell
      desc:  tries to open a cell on the board
      params: int col, int row
      return: boolean - true if DEAD HAHAHAHAHA
      1) if col, row is not on board, return false ( use
      on_board method )
      2) if the cell at col, row is opened, return false
      3) call explore on the cell at col, row.  if 
      method returns 1, return true
      4) if the method returns 0, return false
      5) add one to _cells_opened
      6) return false
    */
    public boolean open_cell(int col, int row)
    {
	int result;
	if ( ! on_board(col, row) )
	    return false;
	if ( _the_cells[col][row].get_is_opened() )
	    return false;

	result = _the_cells[col][row].explore();
	
	if ( result == Cell.DEAD )
	    return true;
	
	if ( result == Cell.FLAGGED )
	    return false;

	_cells_opened++;
	
	return false;
    }

    /*
    // place a flag
    name: place_flag
    desc: tries to place a flag
    params: int col, int row
    return: void
    1) if col row not on board, return ( use the on_board 
    method )
    2) if _flags_placed is equal to _num_mines, return;
    3) call flag method on the cell at col, row
    4) if that method returns true, then _flags_placed++
    */
    public void place_flag(int col, int row)
    {
	if ( ! on_board( col, row ) )
	    return;

	if ( _flags_placed == _num_mines )
	    return;

	if ( _the_cells[col][row].flag() == false )
	    return;

	_flags_placed++;
	    
    }

    /*
    // remove a flag 
    name: remove_flag
    desc: tries to remove a flag
    params: int col, int row
    return: void
    1) if col, row is not on board, return ( use the on_board
    method )
    2) call unflag method on the cell at col, row
    3) if that method returns true, then _flags_placed --
    */
    public void remove_flag(int col, int row)
    {
	if ( ! on_board( col, row ) )
	    return;
	
	if ( _the_cells[col][row].unflag() )
	    _flags_placed --;
    }

    /*
    // game won
    name: game_won
    desc: returns true if all the non mine cells are opened
    params: none
    return: boolean - true if win
    1) if _cell_opened is equal to _rows*_cols-_num_mines,
    return true, else return false
    */

    public boolean game_won()
    {
	return _cells_opened == _rows*_cols - _num_mines;
    }

    /*
    // on board
    name: on_board
    desc: returns true if the given col and row is on the board
    params: int col, int row
    return: boolean - true if on board
    1) if col is less than zero or greater than or equal to
    _cols, return false
    2) if row is less than zero or greater than or equal to
    _rows, return false
    3) return true
    */
    private boolean on_board(int col, int row )
    {
	return col >= 0 && col < _cols && row >= 0 && row < _rows;
    }

    /*
    // populate
    name: populate
    desc: places the mines on the board
    params: Random gen
    return: void
    1) set mines_placed to 0
    2) while mines_placed is less than _num_mines, do steps
    3-5
    3)   generate a random col and a random row
    4)   call place mine on the cell at that col and row
    5)   if the method returns true, then call 
    add_one_to_adj_mines on all adjacent cells that
    are on the board  and add one to mines_placed
     */

    private void populate(Random gen)
    {
	int mines_placed = 0;
	int c, r;

	while ( mines_placed < _num_mines )
	    {
		c = Math.abs( gen.nextInt() ) % _cols;
		r = Math.abs( gen.nextInt() ) % _rows;

		if ( _the_cells[c][r].place_mine() )
		    {
			fix_adj(c,r);
			mines_placed++;
		    }
	    }
    }
    
    /*
      name: fix_adj
      desc: adds one to all cells adjacent to col, row if they 
      are on the board. the cells adjacent to col, row are 
      are (col-1, row-1), (col-1, row), (col-1, row+1),
      (col, row-1), (col, row+1),
      (col+1, row-1), (col+1, row), (col+1, row+1)
      params: int col, int row
      return: none
      1) for each column increment between -1 and 1 do steps 2
      2)  for each row increment between -1 and 1 do steps 3
      3)    if the cell at col+column increment, row+row increment
              is on the board, then call add_on_to_adj_mines 
	       on that cell
     */
    private void fix_adj(int col, int row)
    {
	int c, r;

	for ( c = -1; c <= 1; c++ )
	    for ( r = -1; r <= 1; r ++ )
		if ( on_board(col + c, row + r ) )
		    _the_cells[col+c][row+r].add_one_to_adj_mines();
    }
}