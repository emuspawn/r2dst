import java.util.ArrayList;

//Node class, used by pathfinder

public class Node 
{
	public int x;
	public int y;
	public int gScore;
	public double hScore;
	public double fScore;
	public Node cameFrom = null;
	
	public Node(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public boolean equalTo(Node other)
	{
		if (this.x == other.x && this.y == other.y)
			return true;
		return false;
	}
	
	public String toString()
	{
		return "(" + x + "," + y + ")";
	}
	
	public ArrayList<Node> getNeighborNodes()
	{
		ArrayList<Node> neighborNodes = new ArrayList<Node>();
		//This is where you can make it have diagonals or even your own custom board/graph
		neighborNodes.add(new Node(x, y+1)); //up
		neighborNodes.add(new Node(x, y-1)); //down
		neighborNodes.add(new Node(x-1, y)); //left
		neighborNodes.add(new Node(x+1, y)); //right
		return neighborNodes;
	}
}
