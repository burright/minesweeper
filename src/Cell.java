public class Cell
{
 // represents a single cell on the board
    
 //constants
    public static final int DEAD = 1;
    public static final int FLAGGED = 0;
    public static final int SAFE = -1;

 // members
 private boolean _is_mine;
 private boolean _is_flagged;
 private boolean _is_opened;
 private int _num_adj_mines;

 // methods

    /*
      name: constructor
      desc: inits the members 
      params: none
      return: n/a
      1) set all booleans to false
      2) set _num_adj_mines to 0
    */
    public Cell()
    {
	_is_mine = _is_flagged = _is_opened = false;
	_num_adj_mines = 0;
    }

    /*
      name: toString
      desc: returns a string indicating to the user
      what the status of the cells is
      params: none
      return: String
      1) if flagged, return "F"
      2) if not opened, return "-"
      3) return _num_adj_mines
    */
    public String toString()
    {
	if ( _is_flagged )
	    return "F";
	if ( ! _is_opened )
	    return "-";
	
	return "" + _num_adj_mines;
    }

    /*
      name: flag
      desc: flags a cell
      params: none
      return: boolean - true if operation is successful
      1) if cell is unflagged and not opened, 
      set _is_flagged to true and return true
      2) return false
    */
    public boolean flag()
    {
	if ( ! _is_flagged && ! _is_opened )
	    {
		_is_flagged = true;
		return true;
	    }

	return false;
    }
 
    /*
      name: unflag
      desc: unflags a cell
      params: none
      return: boolean - true if operation is successful
      1) if cell is flagged, set _is_flagged to false
      and return true
      2) return false 
    */
    public boolean unflag()
    {
	if ( _is_flagged )
	    {
		_is_flagged = false;
		return true;
	    }

	return false;
    }

    /*
      name: explore
      desc: opens a cell
      params: none
      return: int - 1 for dead, 0 for flagged, -1 for safe
      1) if flagged, return 0
      2) set _is_opened to true
      3) if _is_mine, return 1 else return -1
    */
    public int explore()
    {
	if ( _is_flagged )
	    return FLAGGED;
	
	_is_opened = true;

	if ( _is_mine )
	    return DEAD;

	return SAFE;
    }
    
    /*
      name: get_is_opened
      desc: returns _is_opened
      params: none
      return: boolean
      1) return _is_opened
    */
    public boolean get_is_opened()
    {
	return _is_opened;
    }

    /*
      name: place_mine
      desc: sets _is_mine to true
      params: none
      return: boolean - true if the operation succeeded
      1) if _is_mine is true, return false
      2) _is_mine = true;
      3) return true
    */
    public boolean place_mine()
    {
	if ( _is_mine )
	    return false;

	_is_mine = true;

	return true;
    }

    /*
      name: add_one_to_adj_mines 
      desc: adds one to _num_adj_mines
      params: none
      return: void
      1) _num_adj_mines++;
    */

    public void add_one_to_adj_mines()
    {
	_num_adj_mines++;
    }
}