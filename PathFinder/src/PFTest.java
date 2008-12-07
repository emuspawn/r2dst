import java.util.ArrayList;

//Class to test the pathfinder
public class PFTest 
{
	public static void main(String[] args) 
	{
		AStarPF astar = new AStarPF();
		
		//Sample map to pass to pathfinder, 1's are unmovable spaces
		int[][] map = new int[][]
		{
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,1,1,1,1,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
			{0,1,1,1,1,1,1,1,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		};
		//start and end points
		//top left, is 0,0
		//x horizontal, y vertical
		Node start = new Node(2,4);
		Node goal = new Node(7,4);
		
		//Times the length of the operation
		long time = System.currentTimeMillis();
		ArrayList<Node> path = astar.findPath(map, start, goal);
		System.out.println(System.currentTimeMillis()-time);
		//prints path as a series of points
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
