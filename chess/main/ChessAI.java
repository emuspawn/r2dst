package main;
import game.*;
//Interface that has to be extended by all chess AIs
public interface ChessAI
{
	//Constants used by Board class
	int PAWN = 0;
	int KNIGHT = 1;
	int BISHOP = 2;
	int ROOK = 3;
	int QUEEN = 4;
	int KING = 5;
	int EMPTY = 6;
	int WHITE = 0;
	int BLACK = 1;
	//Must return a move for side when given Board b
	public Move think(Board b, int side); 
}
