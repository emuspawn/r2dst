package controller;

import tileSystem.TileSystem;
import utilities.Map;

public interface WorldEditorController extends Map
{
	public void setEditType(int setter);
	public int getEditType();
	public String getMapName();
	public TileSystem getTileSystem();
}
