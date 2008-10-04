package tileSystem;

public class TileTypeRegistry
{
	TileType[] t = new TileType[1];
	
	public TileTypeRegistry(){}
	public void registerTileType(TileType type)
	{
		boolean added = false;
		for(int i = 0; i < t.length; i++)
		{
			if(t[i] == null)
			{
				t[i] = type;
				added = true;
				break;
			}
		}
		if(!added)
		{
			enlargeTileTypeArray();
			registerTileType(type);
		}
	}
	public TileType[] getTileTypes()
	{
		return t;
	}
	public TileType getTileType(int type)
	{
		for(int i = 0; i < t.length; i++)
		{
			if(t[i] != null)
			{
				if(t[i].getType() == type)
				{
					return t[i];
				}
			}
		}
		return null;
	}
	private void enlargeTileTypeArray()
	{
		TileType[] temp = new TileType[t.length+1];
		for(int i = 0; i < t.length; i++)
		{
			temp[i] = t[i];
		}
		t = temp;
	}
}
