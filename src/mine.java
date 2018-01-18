
import java.util.*;

public class mine
{
    /*
      1) allocate a game object
      2) call play_game on the game object
    */

    public static void main(String[] args)
    {
	Random gen = new Random();
	Scanner scan = new Scanner(System.in);
	Game g = new Game(scan, gen);

	g.play_game();
	
    }
}