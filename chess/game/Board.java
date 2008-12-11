package game;
import java.awt.Point;
import java.util.ArrayList;

public class Board
{
	final static int PAWN = 0;
	final static int KNIGHT = 1;
	final static int BISHOP = 2;
	final static int ROOK = 3;
	final static int QUEEN = 4;
	final static int KING = 5;
	final static int EMPTY = 6;
	final static int WHITE = 0;
	final static int BLACK = 1;

	final static int VALUE_PAWN = 100;
	final static int VALUE_KNIGHT = 300;
	final static int VALUE_BISHOP = 300;
	final static int VALUE_ROOK = 500;
	final static int VALUE_QUEEN = 5000;
	final static int VALUE_KING = 10000;

	int[][] piece =
	{{ ROOK, KNIGHT, BISHOP, QUEEN, KING, BISHOP, KNIGHT, ROOK },
	{ PAWN, PAWN, PAWN, PAWN, PAWN, PAWN, PAWN, PAWN },
	{ EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
	{ EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
	{ EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
	{ EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
	{ PAWN, PAWN, PAWN, PAWN, PAWN, PAWN, PAWN, PAWN },
	{ ROOK, KNIGHT, BISHOP, QUEEN, KING, BISHOP, KNIGHT, ROOK }};

	int[][] color =
	{{ BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK },
	{ BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK },
	{ EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
	{ EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
	{ EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
	{ EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY },
	{ WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE },
	{ WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE }};

	ArrayList<Move> moveHist = new ArrayList<Move>();
	int spacePiece, spaceColor, destPiece, destColor;

	public Board()
	{

	}

	public Board(Board b)
	{

		piece = b.getPieces();
		color = b.getColors();
	}

	public int[][] getPieces()
	{
		return piece;
	}
	
	public int getPieceCount(int side, int pieceToSearch)
	{
		int count = 0;
		for (int i = 0; i < 8; i++) 
		{
			for (int j = 0; j < 8; j++) 
			{
				if (piece[i][j] == pieceToSearch && color[i][j] == side)
					count++;
			}
		}
		return count;
	}
	
	public int getWinner()
	{
		int winner = -1;
		if (getPieceCount(WHITE, KING) == 0)
			winner = BLACK;
		else if (getPieceCount(BLACK, KING) == 0)
			winner = WHITE;
		return winner;
	}

	public int[][] getColors()
	{
		return color;
	}

	public void move(Move m)
	{
		Point space = m.getSpace();
		Point dest = m.getDest();
		m.spacePiece = piece[space.x][space.y];
		m.spaceCol = color[space.x][space.y];
		m.destPiece = piece[dest.x][dest.y];
		m.destCol = color[dest.x][dest.y];
		piece[dest.x][dest.y] = piece[space.x][space.y];
		color[dest.x][dest.y] = color[space.x][space.y];
		piece[space.x][space.y] = EMPTY;
		color[space.x][space.y] = EMPTY;
		if (m.isEnPasant)
		{
			piece[space.x][dest.y] = EMPTY;
			color[space.x][dest.y] = EMPTY;
		}
		
		moveHist.add(m); //promote to queen
		for (int i = 0; i < 8; i++)
		{
			if (piece[7][i] == PAWN)
			{
				piece[7][i] = QUEEN;
			}
		}
	}

	public boolean spaceIsEmpty(Point space)
	{
		if (piece[space.x][space.y] == EMPTY)
			return true;
		return false;
	}

	public ArrayList<Move> getMoves(int side)
	{
		ArrayList<Move> moveList = new ArrayList<Move>();
		for (int column = 0; column < 8; column++)
		{
			for (int row = 0; row < 8; row++)
			{
				if (color[row][column] == side)
				{
					switch (piece[row][column])
					{
					case PAWN:
						if (side == BLACK)
						{
							// 2 move out of start position
							if (row == 1 && color[row + 1][column] == EMPTY && color[row + 2][column] == EMPTY)
								moveList.add(new Move(new Point(row, column), new Point(row + 2, column)));
							// regular move forward
							if (row > 0 && color[row + 1][column] == EMPTY)
								moveList.add(new Move(new Point(row, column), new Point(row + 1, column)));
							// capture right
							if (column < 7 && row < 7 && color[row + 1][column + 1] == WHITE)
								moveList.add(new Move(new Point(row, column), new Point(row + 1, column + 1)));
							// capture left
							if (column > 0 && row < 7 && color[row + 1][column - 1] == WHITE)
								moveList.add(new Move(new Point(row, column), new Point(row + 1, column - 1)));
							// en pasant right
							if (row == 4 && column < 7 && color[row][column + 1] == WHITE && piece[row][column + 1] == PAWN && color[row + 1][column + 1] == EMPTY)
								moveList.add(new Move(new Point(row, column), new Point(row + 1, column + 1), true));
							// en pasant left
							if (row == 4 && column > 0 && color[row][column - 1] == WHITE && piece[row][column - 1] == PAWN && color[row + 1][column - 1] == EMPTY)
								moveList.add(new Move(new Point(row, column), new Point(row + 1, column - 1), true));
						}
						else
						{
							if (row == 6 && color[row - 1][column] == EMPTY && color[row - 2][column] == EMPTY)
								moveList.add(new Move(new Point(row, column), new Point(row - 2, column)));
							if (row > 0 && color[row - 1][column] == EMPTY)
								moveList.add(new Move(new Point(row, column), new Point(row - 1, column)));
							if (column < 7 && row > 0 && color[row - 1][column + 1] == BLACK)
								moveList.add(new Move(new Point(row, column), new Point(row - 1, column + 1)));
							if (column > 0 && row > 0 && color[row - 1][column - 1] == BLACK)
								moveList.add(new Move(new Point(row, column), new Point(row - 1, column - 1)));
							if (row == 3 && column < 7 && color[row][column + 1] == BLACK && piece[row][column + 1] == PAWN && color[row - 1][column + 1] == EMPTY)
								moveList.add(new Move(new Point(row, column), new Point(row - 1, column + 1), true));
							if (row == 3 && column > 0 && color[row][column - 1] == BLACK && piece[row][column - 1] == PAWN && color[row - 1][column - 1] == EMPTY)
								moveList.add(new Move(new Point(row, column), new Point(row - 1, column - 1), true));
						}
						break;

					case QUEEN:
					case BISHOP:
						// go left up
						for (int tempRow = row - 1, tempCol = column - 1; tempRow >= 0 && tempRow <= 7 && tempCol >= 0 && tempCol <= 7; tempRow--, tempCol--)
						{
							if (color[tempRow][tempCol] != side)
								moveList.add(new Move(new Point(row, column), new Point(tempRow, tempCol)));
							if (color[tempRow][tempCol] != EMPTY)
								break;
						}
						// go right up
						for (int tempRow = row - 1, tempCol = column + 1; tempRow >= 0 && tempRow <= 7 && tempCol >= 0 && tempCol <= 7; tempRow--, tempCol++)
						{
							if (color[tempRow][tempCol] != side)
								moveList.add(new Move(new Point(row, column), new Point(tempRow, tempCol)));
							if (color[tempRow][tempCol] != EMPTY)
    							break;
						}
						// go left down
						for (int tempRow = row + 1, tempCol = column - 1; tempRow >= 0 && tempRow <= 7 && tempCol >= 0 && tempCol <= 7; tempRow++, tempCol--)
						{
							if (color[tempRow][tempCol] != side)
								moveList.add(new Move(new Point(row, column), new Point(tempRow, tempCol)));
							if (color[tempRow][tempCol] != EMPTY)
								break;
						}
						// go right down
						for (int tempRow = row + 1, tempCol = column + 1; tempRow >= 0 && tempRow <= 7 && tempCol >= 0 && tempCol <= 7; tempRow++, tempCol++)
						{
							if (color[tempRow][tempCol] != side)
								moveList.add(new Move(new Point(row, column), new Point(tempRow, tempCol)));
							if (color[tempRow][tempCol] != EMPTY)
    							break;
						}
						if (piece[row][column] == BISHOP)
							break;

					case ROOK:
						// go left
						for (int tempRow = row - 1; tempRow >= 0 && tempRow <= 7; tempRow--)
						{
							if (color[tempRow][column] != side)
								moveList.add(new Move(new Point(row, column), new Point(tempRow, column)));
							if (color[tempRow][column] != EMPTY)
								break;
						}
						// go right
						for (int tempRow = row + 1; tempRow >= 0 && tempRow <= 7; tempRow++)
						{
							if (color[tempRow][column] != side)
								moveList.add(new Move(new Point(row, column), new Point(tempRow, column)));
							if (color[tempRow][column] != EMPTY)
								break;
						}
						// go up
						for (int tempCol = column - 1; tempCol >= 0 && tempCol <= 7; tempCol--)
						{
							if (color[row][tempCol] != side)
								moveList.add(new Move(new Point(row, column), new Point(row, tempCol)));
							if (color[row][tempCol] != EMPTY)
								break;
						}
						// go down
						for (int tempCol = column + 1; tempCol >= 0 && tempCol <= 7; tempCol++)
						{
							if (color[row][tempCol] != side)
								moveList.add(new Move(new Point(row, column), new Point(row, tempCol)));
							if (color[row][tempCol] != EMPTY)
								break;
						}
						break;

					case KNIGHT:
						// 2 up, 1 left
						if (row >= 2 && column >= 1 && color[row - 2][column - 1] != side)
							moveList.add(new Move(new Point(row, column), new Point(row - 2, column - 1)));
						// 2 up, 1 right
						if (row >= 2 && column <= 6 && color[row - 2][column + 1] != side)
							moveList.add(new Move(new Point(row, column), new Point(row - 2, column + 1)));
						// 2 down, 1 left
						if (row <= 5 && column >= 1 && color[row + 2][column - 1] != side)
							moveList.add(new Move(new Point(row, column), new Point(row + 2, column - 1)));
						// 2 down, 1 right
						if (row <= 5 && column <= 6 && color[row + 2][column + 1] != side)
							moveList.add(new Move(new Point(row, column), new Point(row + 2, column + 1)));
						// 1 up, 2 left
						if (row >= 1 && column >= 2 && color[row - 1][column - 2] != side)
							moveList.add(new Move(new Point(row, column), new Point(row - 1, column - 2)));
						// 1 up, 2 right
						if (row >= 1 && column <= 5 && color[row - 1][column + 2] != side)
							moveList.add(new Move(new Point(row, column), new Point(row - 1, column + 2)));
						// 1 down, 2 left
						if (row <= 6 && column >= 2 && color[row + 1][column - 2] != side)
							moveList.add(new Move(new Point(row, column), new Point(row + 1, column - 2)));
						// 1 down, 2 right
						if (row <= 6 && column <= 5 && color[row + 1][column + 2] != side)
							moveList.add(new Move(new Point(row, column), new Point(row + 1, column + 2)));
						break;

					case KING:
						// up
						if (row >= 1 && color[row - 1][column] != side)
							moveList.add(new Move(new Point(row, column), new Point(row - 1, column)));
						// down
						if (row <= 6 && color[row + 1][column] != side)
							moveList.add(new Move(new Point(row, column), new Point(row + 1, column)));
						// left
						if (column >= 1 && color[row][column - 1] != side)
							moveList.add(new Move(new Point(row, column), new Point(row, column - 1)));
						// right
						if (column <= 6 && color[row][column + 1] != side)
							moveList.add(new Move(new Point(row, column), new Point(row, column + 1)));
						// up left
						if (row >= 1 && column >= 1 && color[row - 1][column - 1] != side)
							moveList.add(new Move(new Point(row, column), new Point(row - 1, column - 1)));
						// up right
						if (row >= 1 && column <= 6 && color[row - 1][column + 1] != side)
							moveList.add(new Move(new Point(row, column), new Point(row - 1, column + 1)));
						// down left
						if (row <= 6 && column >= 1 && color[row + 1][column - 1] != side)
							moveList.add(new Move(new Point(row, column), new Point(row + 1, column - 1)));
						// down right
						if (row <= 6 && column <= 6 && color[row + 1][column + 1] != side)
							moveList.add(new Move(new Point(row, column), new Point(row + 1, column + 1)));
						break;
					}
				}
			}
		}
		return moveList;
	}

	//this function tells how many pieces of each type are attacking a position
	public int[] checkAttacked(Point position, int side)
	{
		int col = position.x;
		int row = position.y;
		int k, y, h;
		k = col + (row * 8);
		// converts 2d to 1d, because im too lazy to change all this crap now
		//if someone else wants to, go ahead
		int pieces[] = new int[64];
		for (int a = 0; a < 8; a++)
		{
			for (int b = 0; b < 8; b++)
			{
				pieces[b + (a * 8)] = color[a][b];
			}
		}
		int colors[] = new int[64];
		for (int a = 0; a < 8; a++)
		{
			for (int b = 0; b < 8; b++)
			{
				pieces[b + (a * 8)] = piece[a][b];
			}
		}
		int xside = (WHITE + BLACK) - side;
		int attacked[] =
		{ 0, 0, 0, 0, 0, 0 };
		if (col > 0 && row > 1 && colors[k - 17] == xside && pieces[k - 17] == KNIGHT)
			attacked[1] += 1;
		if (col < 7 && row > 1 && colors[k - 15] == xside && pieces[k - 15] == KNIGHT)
			attacked[1] += 1;
		if (col > 1 && row > 0 && colors[k - 10] == xside && pieces[k - 10] == KNIGHT)
			attacked[1] += 1;
		if (col < 6 && row > 0 && colors[k - 6] == xside && pieces[k - 6] == KNIGHT)
			attacked[1] += 1;
		if (col > 1 && row < 7 && colors[k + 6] == xside && pieces[k + 6] == KNIGHT)
			attacked[1] += 1;
		if (col < 6 && row < 7 && colors[k + 10] == xside && pieces[k + 10] == KNIGHT)
			attacked[1] += 1;
		if (col > 0 && row < 6 && colors[k + 15] == xside && pieces[k + 15] == KNIGHT)
			attacked[1] += 1;
		if (col < 7 && row < 6 && colors[k + 17] == xside && pieces[k + 17] == KNIGHT)
			attacked[1] += 1;
		y = k + 8;
		if (y < 64)
		{
			if (colors[y] == xside && (pieces[y] == KING || pieces[y] == QUEEN || pieces[y] == ROOK))
				attacked[pieces[y]] += 1;
			if (pieces[y] == EMPTY)
				for (y += 8; y < 64; y += 8)
				{
					if (colors[y] == xside && (pieces[y] == QUEEN || pieces[y] == ROOK))
						attacked[pieces[y]] += 1;
					if (pieces[y] != EMPTY)
						break;

				}
		}
		y = k - 1;
		h = k - col;
		if (y >= h)
		{
			if (colors[y] == xside && (pieces[y] == KING || pieces[y] == QUEEN || pieces[y] == ROOK))
				attacked[pieces[y]] += 1;
			if (pieces[y] == EMPTY)
				for (y--; y >= h; y--)
				{
					if (colors[y] == xside && (pieces[y] == QUEEN || pieces[y] == ROOK))
						attacked[pieces[y]] += 1;
					if (pieces[y] != EMPTY)
						break;
				}
		}
		y = k + 1;
		h = k - col + 7;
		if (y <= h)
		{
			if (colors[y] == xside && (pieces[y] == KING || pieces[y] == QUEEN || pieces[y] == ROOK))
				attacked[pieces[y]] += 1;
			if (pieces[y] == EMPTY)
				for (y++; y <= h; y++)
				{
					if (colors[y] == xside && (pieces[y] == QUEEN || pieces[y] == ROOK))
						attacked[pieces[y]] += 1;
					if (pieces[y] != EMPTY)
						break;
				}
		}
		y = k - 8;
		if (y >= 0)
		{
			if (colors[y] == xside && (pieces[y] == KING || pieces[y] == QUEEN || pieces[y] == ROOK))
				attacked[pieces[y]] += 1;
			if (pieces[y] == EMPTY)
				for (y -= 8; y >= 0; y -= 8)
				{
					if (colors[y] == xside && (pieces[y] == QUEEN || pieces[y] == ROOK))
						attacked[pieces[y]] += 1;
					if (pieces[y] != EMPTY)
						break;
				}
		}
		y = k + 9;
		if (y < 64 && COL(y) != 0)
		{
			if (colors[y] == xside)
			{
				if (pieces[y] == KING || pieces[y] == QUEEN || pieces[y] == BISHOP)
					attacked[pieces[y]] += 1;
				if (side == BLACK && pieces[y] == PAWN)
					attacked[pieces[y]] += 1;
			}
			if (pieces[y] == EMPTY)
				for (y += 9; y < 64 && COL(y) != 0; y += 9)
				{
					if (colors[y] == xside && (pieces[y] == QUEEN || pieces[y] == BISHOP))
						attacked[pieces[y]] += 1;
					if (pieces[y] != EMPTY)
						break;
				}
		}
		/* go left down */
		y = k + 7;
		if (y < 64 && COL(y) != 7)
		{
			if (colors[y] == xside)
			{
				if (pieces[y] == KING || pieces[y] == QUEEN || pieces[y] == BISHOP)
					attacked[pieces[y]] += 1;
				if (side == BLACK && pieces[y] == PAWN)
					attacked[pieces[y]] += 1;
			}
			if (pieces[y] == EMPTY)
				for (y += 7; y < 64 && COL(y) != 7; y += 7)
				{
					if (colors[y] == xside && (pieces[y] == QUEEN || pieces[y] == BISHOP))
						attacked[pieces[y]] += 1;
					if (pieces[y] != EMPTY)
						break;

				}
		}
		/* go left up */
		y = k - 9;
		if (y >= 0 && COL(y) != 7)
		{
			if (colors[y] == xside)
			{
				if (pieces[y] == KING || pieces[y] == QUEEN || pieces[y] == BISHOP)
					attacked[pieces[y]] += 1;
				if (side == WHITE && pieces[y] == PAWN)
					attacked[pieces[y]] += 1;
			}
			if (pieces[y] == EMPTY)
				for (y -= 9; y >= 0 && COL(y) != 7; y -= 9)
				{
					if (colors[y] == xside && (pieces[y] == QUEEN || pieces[y] == BISHOP))
						attacked[pieces[y]] += 1;
					if (pieces[y] != EMPTY)
						break;

				}
		}
		/* go right up */
		y = k - 7;
		if (y >= 0 && COL(y) != 0)
		{
			if (colors[y] == xside)
			{
				if (pieces[y] == KING || pieces[y] == QUEEN || pieces[y] == BISHOP)
					attacked[pieces[y]] += 1;
				if (side == WHITE && pieces[y] == PAWN)
					attacked[pieces[y]] += 1;
			}
			if (pieces[y] == EMPTY)
				for (y -= 7; y >= 0 && COL(y) != 0; y -= 7)
				{
					if (colors[y] == xside && (pieces[y] == QUEEN || pieces[y] == BISHOP))
						attacked[pieces[y]] += 1;
					if (pieces[y] != EMPTY)
						break;
				}
		}

		return attacked;
	}

	public int[] checkDefended(Point pos, int side)
	{
		return checkAttacked(pos, 1 - side);
	}

	public int getPieceAt(Point pos)
	{
		return piece[pos.y][pos.x];
	}

	public int getColorAt(Point pos)
	{
		return color[pos.y][pos.x];
	}

	private int COL(int pos)
	{
		return ((pos) & 7);
	}

	public void printBoard()
	{
		int pieces[] = new int[64];
		for (int a = 0; a < 8; a++)
		{
			for (int b = 0; b < 8; b++)
			{
				pieces[b + (a * 8)] = piece[a][b];
			}
		}
		int colors[] = new int[64];
		for (int a = 0; a < 8; a++)
		{
			for (int b = 0; b < 8; b++)
			{
				colors[b + (a * 8)] = color[a][b];
			}
		}

		char pieceName[] =
		{ 'P', 'N', 'B', 'R', 'Q', 'K', 'p', 'n', 'b', 'r', 'q', 'k' };

		int i;
		for (i = 0; i < 64; i++)
		{
			if ((i & 7) == 0)
			{
				System.out.printf("   +---+---+---+---+---+---+---+---+\n");
				if (i <= 56)
				{
					System.out.printf(" %d |", 8 - ((i) >> 3));
				}
			}
			if (pieces[i] == EMPTY)
				System.out.printf("   |");
			else
			{
				System.out.printf(" %c |", pieceName[pieces[i] + (colors[i] == WHITE ? 0 : 6)]);
			}
			if ((i & 7) == 7)
				System.out.printf("\n");
		}
		System.out.printf("   +---+---+---+---+---+---+---+---+\n     a   b   c   d   e   f   g   h\n\n\n");
	}

	public void undo()
	{
		if (moveHist.size() > 0)
		{
			Move m =  moveHist.get(moveHist.size() - 1);
			Point dest = m.getDest();
			Point space = m.getSpace();
			piece[space.x][space.y] = m.spacePiece;
			color[space.x][space.y] = m.spaceCol;
			piece[dest.x][dest.y] = m.destPiece;
			color[dest.x][dest.y] = m.destCol;
			if (m.isEnPasant)
			{
				piece[space.x][dest.y] = PAWN;
				color[space.x][dest.y] = 1 - m.spaceCol;
			}
			moveHist.remove(moveHist.size() - 1);
		}
	}
}
