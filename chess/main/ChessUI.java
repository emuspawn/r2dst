package main;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;
import ais.*;
import game.*;

//This file is the user interface, you can play against
//my AI but dont expect to lose to it yet :(

public class ChessUI implements Runnable
{
	final static int WHITE = 0;
	final static int BLACK = 1;
	
	public ChessUI()
	{
		
	}

	public static void main(String[] args)
	{
		ChessUI ui = new ChessUI();
		Thread t = new Thread(ui);
		t.start();
	}
	
	public void run()
	{
		Board b = new Board();
		KyleAI whiteAI = new KyleAI(WHITE);//set up your ais here
		Move m;
		b.printBoard();
		while (b.getWinner() == -1)
		{
			//call this once every time through to pick a move
			m = whiteAI.think(b, WHITE);
			b.move(m);
			try
			{
				Thread.sleep(1000); //wait so the user can see what the comp did
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			b.printBoard();
			//this can all be commented out and replaced by another AI
			//for AI vs AI
			m = promptMove();
			while (!isLegal(b, m))
			{
				System.out.println("That move is not legal.");
				m = promptMove();
			}
			b.move(m);
			b.printBoard();
		}
		if (b.getWinner() == WHITE)
		{
			System.out.println("White wins!");
		}
		else
		{
			System.out.println("Black wins!");
		}
	}

	private static Move promptMove() //returns move from format b3f6, for example
	{
		Scanner sc = new Scanner(System.in);
		String str = sc.next();
		char[] s = str.toCharArray();
		int from = s[0] - 'a';
		from += 8 * (8 - (s[1] - '0'));
		int dest = s[2] - 'a';
		dest += 8 * (8 - (s[3] - '0'));
		Move m = new Move(new Point(from / 8, from % 8), new Point(dest / 8,
				dest % 8));
		return m;
	}

	private static boolean isLegal(Board b, Move m)
	{
		ArrayList<Move> moveList = b.getMoves(BLACK);
		for (int i = 0; i < moveList.size(); i++)
		{
			if (moveList.get(i).identical(m))
				return true;
		}
		return false;
	}
}
