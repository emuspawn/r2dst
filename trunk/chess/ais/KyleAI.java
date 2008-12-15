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
		/* New, experimental board evaluation -- in development
		 * 
		 * -getting this to generate a good, accurate score is what
		 * will determine the strength of the ai
		 */
		int score = 0;
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				Point currentPoint = new Point(i, j);
				int currentPiece = b.getPieceAt(currentPoint);
				int currentColor = b.getColorAt(currentPoint);
				int currentPieceValue = values[currentPiece];
				int currentSpaceValue = spaceValues[currentPoint.x][currentPoint.y];
				//adds value of pieces on each side to the score
				//favors attacking and taking undefended pieces (in theory)
				if (currentColor == side)
				{
					score += sumStuff(b.checkAttacked(currentPoint, side), b.checkDefended(currentPoint, side), values);
					score += currentPieceValue;
				}
				else if (currentColor == 1 - side)
				{
					score -= sumStuff(b.checkAttacked(currentPoint, 1-side), b.checkDefended(currentPoint, 1-side), values);
					score -= currentPieceValue;
				}
				score += sumStuff(b.checkAttacked(currentPoint, side), b.checkDefended(currentPoint, side), values) - currentSpaceValue;
			}
		}
		return -score;
	}
	
	private int negaScout(Board boardToEvaluate, int side, int depth, Move bestMove, int alpha, int beta)
	{
		if (depth == 0)
			return evalBoard(boardToEvaluate, side); //get heuristic value of this position at the final depth
		boardToEvaluate.move(bestMove);
		
		int a;
		int b = beta;	/* initial window is (-beta, -alpha) */	
		
		ArrayList<Move> possibleMoves = boardToEvaluate.getMoves(side);
		for (int i = 0; i < possibleMoves.size(); i++)
		{
			//recursion stuff
			a = -negaScout(boardToEvaluate, 1-side, depth-1, possibleMoves.get(i), -b, -alpha);
			if (a > alpha)
				alpha = a;
			if (alpha >= beta)
			{
				boardToEvaluate.undo();
				return alpha; /* Beta cut-off */
			}
			if (alpha >= b) /* check if null-window failed high */
			{
				/* full re-search */
				alpha = -negaScout(boardToEvaluate, 1-side, depth-1, possibleMoves.get(i), -beta, -alpha);
				if (alpha >= beta)
				{
					boardToEvaluate.undo();
					return alpha; /* Beta cut-off */
				}
			}
			b = alpha+1; /* set new null window */
		}
		boardToEvaluate.undo(); //undo the move it made
		return alpha;
		
	}

	public Move think(Board b, int side)
	{
		int depth = 3; //depth to think ahead
		ArrayList<Move> possible = b.getMoves(side);
		Move best = possible.get(0);
		int score = Integer.MIN_VALUE;
		for (int i = 0; i < possible.size(); i++)
		{
			int tscore;
			//depth search!
			tscore = negaScout(b, side, depth-1, possible.get(i), Integer.MIN_VALUE, Integer.MAX_VALUE);

			//picks the "best of all possible moves" (not yet..)
			if (tscore >= score)
			{
				score = tscore;
				best = possible.get(i);
			}
		}
		return best;
	}

	//for adding up values of defending and attacking pieces, and the piece itself
	private int sumStuff(int[] atkArray, int[] defArray, int[] values)
	{
		int score = 0;
		int s1 = 0, s2 = 0;
		for (int i = 0; i < atkArray.length; i++)
		{
			s1 += atkArray[i] * values[i];
		}
		for (int i = 0; i < defArray.length; i++)
		{
			s2 -= defArray[i] * values[i];
		}
		if (s1 == 0 && s2 < 0)
			s2 = 0;
		if (s1 == 0 && s2 == 0)
			score = 0; //no credit for doing unneeded defense
		else
			score = s1 + s2;
		return score;
	}
}
