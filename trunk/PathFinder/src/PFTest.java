import java.util.ArrayList;

//Class to test the pathfinder
public class PFTest 
{
	public static void main(String[] args) 
	{
		AStarPF astar = new AStarPF();
		
		//Sample map to pass to pathfinder, 1's are unmovable spaces
		byte[][] map = new byte[][]
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
		
		
		//a large test map, to test it moving in large straight paths
		byte[][] map2 = new byte[200][200];
		for (int i = 0; i < map2.length; i++)
		{
			for (int j = 0; j < map2[i].length; j++)
			{
				map2[i][j] = 0;
			}
		}
		//start and end positions for map2
		Node start2 = new Node(0,0);
		Node goal2 = new Node(150,150);
		
		//Times the length of the operation
		long time = System.currentTimeMillis();
		ArrayList<Node> path = astar.findPath(map, start, goal);
		System.out.println("time: "+(System.currentTimeMillis()-time)+"ms");
		//prints path as grid
		printPath(map, path, start, goal);
	}
	
	public static void printPath(byte[][] grid, ArrayList<Node> path, Node start, Node goal)
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
