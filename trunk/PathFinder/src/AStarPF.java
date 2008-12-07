import java.util.ArrayList;

//Pathfinder, uses A* algorithm
public class AStarPF
{
	public AStarPF()
	{
		
	}
	
	public ArrayList<Node> findPath(int[][] grid, Node start, Node goal)
	{
		//node lists
		ArrayList<Node> closedList = new ArrayList<Node>(); //already evaluated
		ArrayList<Node> openList = new ArrayList<Node>(); //points to evaluate
		
		//Add and evaluate start node
		openList.add(start);
		start.gScore = 0;
		start.hScore = Math.abs(goal.x - start.x) + Math.abs(goal.x - start.x);
		start.fScore = start.gScore + start.hScore;
		int tries = 0;
		while(openList.size() > 0) //while open list has nodes to evaluate
		{
			//pick best f-score node in list
			Node currentNode = getLowestFScore(openList);
			tries++;
			if (currentNode.equalTo(goal))
			{
				System.out.println(tries);
				return makePath(currentNode, start); //reconstruct path
			}
			
			//gets adjacent nodes
			ArrayList<Node> neighborNodes = new ArrayList<Node>();
			neighborNodes.add(new Node(currentNode.x, currentNode.y+1)); //up
			neighborNodes.add(new Node(currentNode.x, currentNode.y-1)); //down
			neighborNodes.add(new Node(currentNode.x-1, currentNode.y)); //left
			neighborNodes.add(new Node(currentNode.x+1, currentNode.y)); //right

			//For each neightbor node
			for (int i = 0; i < 4; i++)
			{
				Node n = neighborNodes.get(i);
		        boolean isClosed = isInList(n, closedList);

				if (!nodeOutsideGrid(n, grid) && grid[n.y][n.x] != 1)
				{
					boolean tentativeIsBetter = false;
					//This node's g-score
					int tentativeGScore = currentNode.gScore + 1;
				
					if (!isClosed)
					{	
						//estimated distance from evaluated node to goal node
						int dx1 = currentNode.x - goal.x;
						int dy1 = currentNode.y - goal.y;
						int dx2 = start.x - goal.x;
						int dy2 = start.y - goal.y;
						double cross = Math.abs(dx1*dy2 - dx2*dy1);
						n.hScore = Math.abs(dx2) + Math.abs(dy2);
						//heavily favors paths in a straight line, speeds things up 
						//by breaking ties
						double straightFactor = 0.1;
						n.hScore += cross*straightFactor;
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
				else //do not evaluate squares it can't move to
				{
					closedList.add(n);
				}
			}
			//move current node from open list to closed list
			removeNodeFromList(currentNode, openList);
			closedList.add(currentNode);
		}
		//no path
		return null;
	}
	
	private Node getLowestFScore(ArrayList<Node> list)
	{
		Node lowest;
		int index = 0;
		double lowCost = 999999;
		for (int i = 0; i < list.size(); i++)
		{
			Node n = list.get(i);
			if (n.fScore <= lowCost)
			{
				lowCost = n.fScore;
				index = i;
			}
		}
		lowest = list.get(index);
		return lowest;
	}
	
	private void removeNodeFromList(Node n, ArrayList<Node> list)
	{
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i).equalTo(n)) //find node
			{
				list.remove(i); //remove node
				break;
			}
		}
	}
	
	private boolean isInList(Node n, ArrayList<Node> list)
	{
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i).x == n.x && list.get(i).y == n.y)
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
		//Split up to improve speed
		if (n.x < 0)
			return true;
		if (n.y < 0)
			return true;
		if (n.x >= grid[0].length)
			return true;
		if (n.y >= grid.length)
			return true;
		return false;
	}
}
