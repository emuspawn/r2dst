package ais;
import java.awt.Point;
import java.util.ArrayList;
import main.*;
import game.*;

 /* Use this AI as an example or framework of what to do, but dont just copy,
  * they will get you if you do...
  * 
  * Most of this is probably confusing, so look at the think(), evalBoard(),
  * and getMoveScore() methods for ideas on what to do
  */
public class KyleAI implements ChessAI
{
	final static int VALUE_PAWN = 100;
	final static int VALUE_KNIGHT = 300;
	final static int VALUE_BISHOP = 300;
	final static int VALUE_ROOK = 500;
	final static int VALUE_QUEEN = 900;
	final static int VALUE_KING = 10000;

	public int mySide;

	final static int spaceValues[][] =
	{
	{2, 2, 2, 2, 2, 2, 2, 2},
	{2, 4, 4, 4, 4, 4, 4, 2},
	{2, 4, 6, 6, 6, 6, 4, 2},
	{2, 4, 6, 8, 8, 6, 4, 2},
	{2, 4, 6, 8, 8, 6, 4, 2},
	{2, 4, 6, 6, 6, 6, 4, 2},
	{2, 4, 4, 4, 4, 4, 4, 2},
	{2, 2, 2, 2, 2, 2, 2, 2,}
	};

	public KyleAI(int side)
	{
		mySide = side;
	}

	public int[] arrayFlip(int[] array)
	{
		int[] array2 = new int[array.length];
		for (int i = array.length - 1; i >= 0; i--)
		{
			array2[i] = array[i - 1 - array.length];
		}
		return array2;
	}

	public int evalBoard(Board b, int side)
	{
		int values[] =	{VALUE_PAWN, VALUE_KNIGHT, VALUE_BISHOP, VALUE_ROOK, VALUE_QUEEN, VALUE_KING, 0};
		int score = 0;
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				Point currentPoint = new Point(i, j);
				if (b.getColorAt(currentPoint) == side)
				{
					if (arraySum(b.checkAttacked(currentPoint, side)) > 0)
					{
						int defBalance = -sumStuff(b.checkAttacked(currentPoint, side), b.checkDefended(currentPoint, side), values);
						score -= values[b.getPieceAt(currentPoint)] - defBalance;
					}
				}
				else
					if (b.getColorAt(currentPoint) == 1 - side)
					{
						if (arraySum(b.checkAttacked(currentPoint, 1 - side)) > 0)
						{
							int defBalance = sumStuff(b.checkAttacked(currentPoint, 1 - side), b.checkDefended(currentPoint, 1 - side), values);
							score -= values[b.getPieceAt(currentPoint)] - defBalance;
						}
					}
					else
						if (b.getPieceAt(currentPoint) == EMPTY)
						{
							score -= (arraySum(b.checkAttacked(currentPoint, side)) - arraySum(b.checkDefended(currentPoint, side))) * spaceValues[i][j] * 4;
						}
			}
		}

		return score;
	}

	public int getMoveScore(Board b, int side, int depth, Move bestMove)
	{
		bestMove.setScore(Integer.MIN_VALUE);
		ArrayList<Move> moves = b.getMoves(side);
		Board tempBoard = new Board(b);
		Move tempMove;
		int value;
		tempBoard.move(bestMove);
		for (int i = 0; i < 1; i++)
		{
			tempMove = moves.get(i);
			if (depth - 1 > 0)
			{
				value = getMoveScore(tempBoard, 1 - side, depth - 1, tempMove);
			}
			else
			{
				value = evalBoard(tempBoard, side);
				tempMove.setScore(value);
			}
			if (tempMove.getScore() >= bestMove.getScore())
			{
				bestMove.setScore(tempMove.getScore());
				bestMove = tempMove;
			}
		}
		tempBoard.undo();
		return bestMove.getScore();
	}

	public Move think(Board b, int side)
	{
		int depth = 1; //depth to think ahead, more than 1 doesnt work right now
		ArrayList<Move> possible = b.getMoves(side);
		Move best = possible.get(0);
		int score = -999999;
		for (int i = 0; i < possible.size(); i++)
		{
			int tscore;
			tscore = getMoveScore(b, side, depth, possible.get(i));

			if (tscore > score)
			{
				score = tscore;
				best = possible.get(i);
			}
		}
		return best;
	}

	private int arraySum(int[] array)
	{
		int sum = 0;
		for (int i = 0; i < array.length; i++)
		{
			sum += array[i];
		}
		return sum;
	}

	private int sumStuff(int[] atkArray, int[] defArray, int[] values)
	{
		int score = 0;
		int s1 = 0, s2 = 0;
		for (int i = 0; i < atkArray.length; i++)
		{
			s1 += atkArray[i] * values[i] / 2;
		}
		for (int i = 0; i < defArray.length; i++)
		{
			s2 -= defArray[i] * values[i] / 2;
		}
		if (s1 == 0 && s2 < 0)
			s2 = 0;
		score = s1 + (s2 / 2);
		return score;
	}
}
