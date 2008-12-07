
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
}
