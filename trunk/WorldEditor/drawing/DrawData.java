package drawing;

public class DrawData
{
	boolean drawGrid = true;
	int gridSize = 30;
	
	public DrawData(){}
	public void setDrawGrid(boolean setter)
	{
		drawGrid = setter;
	}
	public boolean getDrawGrid()
	{
		return drawGrid;
	}
	public void setGridSize(int setter)
	{
		gridSize = setter;
	}
	public int getGridSize()
	{
		return gridSize;
	}
}
