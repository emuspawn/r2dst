package controller;

import ui.UICheckEngine;

public interface MapEditorController
{
	public void setMapName(String s);
	public void setMapWidth(int setter);
	public void setMapHeight(int setter);
	public void setEditType(int setter);
	public int getEditType();
	public UICheckEngine getUICheckEngine();
	public void saveNextIteration(); //saves a .txt file for editing purposes
	public void savePicNextIteration(); //saves a pic file to be used actually in game
	public void loadTxtNextIteration(); //loads a .txt file next iteration
}