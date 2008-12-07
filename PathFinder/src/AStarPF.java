import java.util.ArrayList;

public class AStarPF
{
	public AStarPF()
	{
		
	}
	
	public ArrayList<Node> findPath(int[][] grid, Node start, Node goal)
	{
		ArrayList<Node> closedList = new ArrayList<Node>(); //already evaluated
		ArrayList<Node> openList = new ArrayList<Node>(); //points to evaluate
		
		//Add and evaluate start node
		openList.add(start);
		start.gScore = 0;
		start.hScore = Math.abs(goal.x - start.x) + Math.abs(goal.x - start.x);
		start.fScore = start.gScore + start.hScore;
		
		while(openList.size() > 0) //while open list has nodes to evaluate
		{
			Node currentNode = getLowestFScore(openList);
			//System.out.println(currentNode.toString());
			if (currentNode.equalTo(goal))
			{
				return makePath(currentNode, start); //reconstruct path
			}
			//printOpenList(openList);
			removeNodeFromList(currentNode, openList);
			closedList.add(currentNode);
			
			ArrayList<Node> neighborNodes = new ArrayList<Node>();
			neighborNodes.add(new Node(currentNode.x, currentNode.y+1)); //up
			neighborNodes.add(new Node(currentNode.x, currentNode.y-1)); //down
			neighborNodes.add(new Node(currentNode.x-1, currentNode.y)); //left
			neighborNodes.add(new Node(currentNode.x+1, currentNode.y)); //right

			
			for (int i = 0; i < 4; i++)
			{
				Node n = neighborNodes.get(i);
				if (!nodeOutsideGrid(n, grid) && grid[n.y][n.x] != 1)
				{
					boolean tentativeIsBetter = false;
					int tentativeGScore = currentNode.gScore + 1;
				
					if (!isInList(n, openList))
					{	
						int dx1 = currentNode.x - goal.x;
						int dy1 = currentNode.y - goal.y;
						int dx2 = start.x - goal.x;
						int dy2 = start.y - goal.y;
						double cross = Math.abs(dx1*dy2 - dx2*dy1);
						n.hScore = Math.abs(dx2) + Math.abs(dy2);
						n.hScore += cross*10;
						tentativeIsBetter = true;
					}
				
					if (tentativeIsBetter)
					{
						n.cameFrom = currentNode;
						n.gScore = tentativeGScore;
						n.fScore = n.gScore + n.hScore;
						openList.add(n);
					}
				}
				else
				{
					closedList.add(n);
				}
			}
		}
		//no path
		return null;
	}
	
	private Node getLowestFScore(ArrayList<Node> openList)
	{
		Node lowest;
		int index = 0;
		double lowCost = 999999;
		for (int i = 0; i < openList.size(); i++)
		{
			if (openList.get(i).fScore < lowCost)
			{
				lowCost = openList.get(i).fScore;
				index = i;
			}
		}
		lowest = openList.get(index);
		return lowest;
	}
	
	private void removeNodeFromList(Node n, ArrayList<Node> list)
	{
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i).equalTo(n))
			{
				list.remove(i);
				break;
			}
		}
	}
	
	private boolean isInList(Node n, ArrayList<Node> list)
	{
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i).equalTo(n))
			return true; //is in list
		}
		return false; //not in list
	}
	
	private ArrayList<Node> makePath(Node goal, Node start)
	{
		ArrayList<Node> p = new ArrayList<Node>();
		Node g = goal;
		// make path backwards from goal
		while (g.cameFrom != null)
		{
			p.add(g);
			g = g.cameFrom;
		}
		p.add(start);
		
		ArrayList<Node> path = new ArrayList<Node>();
		//flip path
		for (int i = p.size()-1; i > 0; i--)
		{
			path.add(p.get(i));
		}
		path.add(goal);
		return path;
	}
	
	private boolean nodeOutsideGrid(Node n, int[][] grid)
	{
		if (n.x < 0)
			return true;
		if (n.y < 0)
			return true;
		if (n.y >= grid.length)
			return true;
		if (n.y >= grid.length)
			return true;
		return false;
	}
}
