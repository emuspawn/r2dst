import java.util.ArrayList;


public class PFTest 
{
	public static void main(String[] args) 
	{
		AStarPF astar = new AStarPF();
		int[][] map = new int[][]
		{
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,1,1,1,1,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
			{0,1,1,1,1,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		};
		Node start = new Node(2,4);
		Node goal = new Node(6,4);
		long time = System.currentTimeMillis();
		ArrayList<Node> path = astar.findPath(map, start, goal);
		System.out.println(System.currentTimeMillis()-time);
		printPath(path);
	}
	
	public static void printPath(ArrayList<Node> path)
	{
		for (int i = 0; i < path.size(); i++)
		{
			System.out.println(path.get(i));
		}
	}
}
