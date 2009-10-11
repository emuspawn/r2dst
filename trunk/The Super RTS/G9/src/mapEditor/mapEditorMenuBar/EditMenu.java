package mapEditor.mapEditorMenuBar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import mapEditor.Map;

public final class EditMenu extends Menu
{
	Map m;
	public EditMenu(Map m)
	{
		super("Edit");
		this.m = m;
		
		MenuItem clearPoly = new MenuItem("Clear Polygons");
		clearPoly.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				getMap().clearPolygons();
			}
		});
		add(clearPoly);
	}
	private Map getMap()
	{
		return m;
	}
}
