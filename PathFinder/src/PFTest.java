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
			{0,0,0,1,1,1,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
			{0,0,0,1,1,1,0,0,0,0,0,0,0,0,0},
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
		System.out.println("time: "+(System.currentTimeMillis()-time)+"ms");
		//prints path as grid
		printPath(map, path, start, goal);
	}
	
	public static void printPath(int[][] grid, ArrayList<Node> path, Node start, Node goal)
	{
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[0].length; j++)
			{
				int pathSquare = 0;
				for (int k = 0; k < path.size(); k++)
				{
					if (path.get(k).equalTo(new Node(j,i)))
						pathSquare = 1;
				}
				if (start.equalTo(new Node(j,i)))
					System.out.print(" S"); //show S for start
				else if (goal.equalTo(new Node(j,i)))
					System.out.print(" G"); //show G for goal
				else if (pathSquare == 1)
					System.out.print(" X");
				else
					System.out.print(" " + grid[i][j]);
			}
			System.out.println();
		}
	}
}
