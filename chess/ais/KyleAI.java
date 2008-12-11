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
	private final static int VALUE_PAWN = 100;
	private final static int VALUE_KNIGHT = 300;
	private final static int VALUE_BISHOP = 300;
	private final static int VALUE_ROOK = 500;
	private final static int VALUE_QUEEN = 900;
	private final static int VALUE_KING = 10000;
	
	int values[] =	
	{VALUE_PAWN, VALUE_KNIGHT, VALUE_BISHOP, VALUE_ROOK, VALUE_QUEEN, VALUE_KING, 0};

	public int mySide;

	//center is more important
	private final static int spaceValues[][] =
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

	private int evalBoard(Board b, int side)
	{
		//function to evaluate just the position
		//sucks right now, need to clean up and improve
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
						int defBalance = sumStuff(b.checkAttacked(currentPoint, side), b.checkDefended(currentPoint, side), values);
						score -= values[b.getPieceAt(currentPoint)] - defBalance;
					}
				}
				else if (b.getColorAt(currentPoint) == 1 - side)
				{
					if (arraySum(b.checkAttacked(currentPoint, 1 - side)) > 0)
					{
						int defBalance = sumStuff(b.checkAttacked(currentPoint, 1 - side), b.checkDefended(currentPoint, 1 - side), values);
						score -= values[b.getPieceAt(currentPoint)] - defBalance;
					}
				}
				else if (b.getPieceAt(currentPoint) == EMPTY)
				{
					score -= (arraySum(b.checkAttacked(currentPoint, side)) - arraySum(b.checkDefended(currentPoint, side))) * spaceValues[i][j] * 4;
				}
			}
		}
		return score;
	}
	
	//need to make this a negascout search
	private int alphaBeta(Board b, int side, int depth, Move bestMove, int alpha, int beta)
	{
		/* beta represents previous player best choice - doesn't want it if alpha would worsen it */
		if (depth == 0)
			return -evalBoard(b, side); //get heuristic value of this position at the final depth
		b.move(bestMove);
		ArrayList<Move> possibleMoves = b.getMoves(side);
		for (int i = 0; i < possibleMoves.size(); i++)
		{
			//recursion stuff
			alpha = Math.max(alpha, -alphaBeta(b, 1-side, depth-1, possibleMoves.get(i), -beta, -alpha));
			/* use symmetry, -beta becomes subsequently pruned alpha */
			if (beta <= alpha) /* Beta cut-off */
				break;
		}
		b.undo(); //undo the move it made
		return alpha;
		
	}

	public Move think(Board b, int side)
	{
		int depth = 4; //depth to think ahead
		ArrayList<Move> possible = b.getMoves(side);
		Move best = possible.get(0);
		int score = Integer.MIN_VALUE;
		for (int i = 0; i < possible.size(); i++)
		{
			int tscore;
			//depth search!
			tscore = -alphaBeta(b, side, depth-1, possible.get(i), Integer.MIN_VALUE, Integer.MAX_VALUE);

			//picks the "best of all possible moves" (not yet..)
			if (tscore > score)
			{
				score = tscore;
				best = possible.get(i);
			}
		}
		return best;
	}

	//this should eventually be removed, ugly
	private int arraySum(int[] array)
	{
		int sum = 0;
		for (int i = 0; i < array.length; i++)
		{
			sum += array[i];
		}
		return sum;
	}

	//should remove this, too
	private int sumStuff(int[] atkArray, int[] defArray, int[] values)
	{
		int score = 0;
		int s1 = 0, s2 = 0;
		for (int i = 0; i < atkArray.length; i++)
		{
			s1 -= atkArray[i] * values[i] / 4;
		}
		for (int i = 0; i < defArray.length; i++)
		{
			s2 += defArray[i] * values[i] / 4;
		}
		if (s1 == 0 && s2 > 0)
			s2 = 0;
		score = s1 + (s2 / 2);
		return -score;
	}
}
