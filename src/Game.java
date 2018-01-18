import java.util.*;

public class Game
{

    // members
    private Board _the_board;
    private Scanner _scan;
    private Random _gen;

    /*
    // constructor
    name: constructor
    desc: allocates the board, saves the
    scanner and generator
    params: Scanner scan, Random gen
    return: n/a
    1) store scan in _scan and gen in _gen
    2) allocate a board object and store its address
    in _the_board
    */

    public Game(Scanner scan, Random gen)
    {
	_scan = scan;
	_gen = gen;

	_the_board = new Board(scan, gen);
    }

    /*
    // play_game
    name: play_game
    desc: plays one game of minesweeper
    params: none
    return: void
    1) while the user has not won ( 
    call the board's game_won method ) do steps 2-7
    2)  print the board ( board's toString method )
    3)  ask the user for what col and row they would like
    4)  ask the user if the want to explore, flag, or unflag
    the cell at col row
    5)  if the user wants to explore, call the board's 
    open_cell method.  if it returns true, 
    game over. return. 
    6)  if the user wants to flag a cell, call the board's
    place_flag method.
    7)  if the user wants to unflag a cell, call the board's
    remove_flag method
    8)  print that the user has won
    */
    public void play_game()
    {
	int r,c;
	String action;

	while ( _the_board.game_won() == false )
	    {
		System.out.println( _the_board );

		System.out.print("col: ");
		c = _scan.nextInt();
		System.out.print("row: ");
		r = _scan.nextInt();
		System.out.print("explore, flag, unflag: ");
		action = _scan.next();

		if ( action.equals("explore") && 
		     _the_board.open_cell(c, r) )
		    {
			System.out.println("BOOOOOOMMMMM!!!!!!!!!!");
			return;
		    }

		if ( action.equals("flag") )
		    _the_board.place_flag(c, r);
		
		if ( action.equals("unflag") )
		    _the_board.remove_flag(c, r);
	    }

	System.out.println("You win!!!");
    }
}