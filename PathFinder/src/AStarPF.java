import java.util.ArrayList;

//Pathfinder, uses A* algorithm
//It evaluates too many nodes, and seems to be reevaluating 
//some closed ones. This is a bug not yet pinned down
public class AStarPF
{
	public AStarPF()
	{
		
	}
	
	public ArrayList<Node> findPath(byte[][] grid, Node start, Node goal)
	{
		//node lists
		ArrayList<Node> closedList = new ArrayList<Node>(); //already evaluated
		ArrayList<Node> openList = new ArrayList<Node>(); //points to evaluate
		
		//Add and evaluate start node
		openList.add(start);
		start.gScore = 0;
		start.hScore = Math.abs(goal.x - start.x) + Math.abs(goal.x - start.x);
		start.fScore = start.hScore;
		int tries = 0;
		while(openList.size() > 0) //while open list has nodes to evaluate
		{
			//pick best f-score node in list
			Node currentNode = getLowestFScore(openList);
			tries = closedList.size();
			if (currentNode.equalTo(goal))
			{
				System.out.println("closed list size: " + tries); //prints number of nodes in closed list at end
				return makePath(currentNode, start); //reconstruct path
			}
			
			//move current node from open list to closed list
			removeNodeFromList(currentNode, openList);
			if (!isInList(currentNode, closedList))
				closedList.add(currentNode);
			
			//gets adjacent nodes
			ArrayList<Node> neighborNodes = currentNode.getNeighborNodes();

			//For each neightbor node
			for (int i = 0; i < neighborNodes.size(); i++)
			{
				Node n = neighborNodes.get(i);
		        boolean isOpen = isInList(n, openList);
		        boolean isClosed = isInList(n, closedList);		        

				if (!nodeOutsideGrid(n, grid) && grid[n.y][n.x] != 1)
				{
					boolean tentativeIsBetter = false;
					//This node's g-score
					int tentativeGScore = currentNode.gScore + 1;
				
					if (!isOpen && !isClosed)
					{	
						openList.add(n);
						//estimated distance from evaluated node to goal node
						int dx1 = currentNode.x - goal.x;
						int dy1 = currentNode.y - goal.y;
						int dx2 = start.x - goal.x;
						int dy2 = start.y - goal.y;
						double cross = Math.abs(dx1*dy2 - dx2*dy1);
						n.hScore = Math.abs(dx2) + Math.abs(dy2);
						//heavily favors paths in a straight line, speeds things up 
						//by breaking ties
						double straightFactor = 1;
						n.hScore += cross*straightFactor;
						tentativeIsBetter = true;
					}
					else if (tentativeGScore < n.gScore)
					{
						tentativeIsBetter = true;
					}
				
					if (tentativeIsBetter)
					{
						n.cameFrom = currentNode;
						n.gScore = tentativeGScore;
						n.fScore = n.gScore + n.hScore;
					}
				}
				else //do not evaluate squares it can't move to
				{
					if (!isInList(n, closedList))
						closedList.add(n);
				}
			}
		}
		//no path
		return null;
	}
	
	private Node getLowestFScore(ArrayList<Node> list)
	{
		Node lowest;
		int index = 0;
		double lowCost = list.get(0).fScore;
		for (int i = 0; i < list.size(); i++)
		{
			Node n = list.get(i);
			if (n.fScore < lowCost)
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
			if (list.get(i).equalTo(n))
			return true; //is in list
		}
		return false; //not in list
	}
	
	private ArrayList<Node> makePath(Node goal, Node start)
	{
		ArrayList<Node> p = new ArrayList<Node>();
		Node n = goal;
		// make path backwards from goal
		while (n.cameFrom != null)
		{
			p.add(n);
			n = n.cameFrom;
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
	
	private boolean nodeOutsideGrid(Node n, byte[][] grid)
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
