package game;
import java.awt.Point;

public class Move
{
	public int score, spacePiece, spaceCol, destPiece, destCol;
	public Point space, destination;

	public Move(Point s, Point d)
	{
		space = s;
		destination = d;
	}

	public boolean identical(Move m)
	{
		if (this.space.equals(m.space) && this.destination.equals(destination))
			return true;
		return false;
	}

	public void setScore(int newScore)
	{
		score = newScore;
	}

	public int getScore()
	{
		return score;
	}

	public Point getSpace()
	{
		return space;
	}

	public Point getDest()
	{
		return destination;
	}

	public String toString()
	{
		String s = "" + (char) (space.x + 97) + (8 - space.x);
		s += "" + (char) (destination.x + 97) + (8 - destination.x);
		return s;
	}
}
