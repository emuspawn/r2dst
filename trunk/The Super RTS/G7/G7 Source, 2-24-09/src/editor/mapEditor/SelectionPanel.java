package editor.mapEditor;

import javax.swing.*;
import java.awt.*;

/**
 * the panel that selects the element that will be added to the world by
 * dragging the mouse in the editor
 * @author Jack
 *
 */
public final class SelectionPanel extends JPanel
{
	JTabbedPane tp = new JTabbedPane();
	JList terrain;
	JList resources;
	
	/**
	 * constructs a new SelectionPanel
	 */
	public SelectionPanel()
	{
		setupTerrainList();
		//JScrollPane t = new JScrollPane(terrain);
		
		setupResourceList();
		//JScrollPane r = new JScrollPane(resources);
		
		tp.addTab("Terrain", terrain);
		tp.addTab("Resources", resources);
		add(tp);
	}
	public String getDisplayedTabName()
	{
		if(tp.getSelectedComponent() == terrain)
		{
			return "terrain";
		}
		else if(tp.getSelectedComponent() == resources)
		{
			return "resource";
		}
		return null;
	}
	public JList getDisplayedJList()
	{
		return (JList)tp.getSelectedComponent();
	}
	private void setupTerrainList()
	{
		DefaultListModel dlm = new DefaultListModel();
		dlm.addElement("Hard Rock");
		dlm.addElement("Water");
		
		terrain = new JList(dlm);
		terrain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	private void setupResourceList()
	{
		DefaultListModel dlm = new DefaultListModel();
		dlm.addElement("Tree");
		
		resources = new JList(dlm);
		resources.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
}
