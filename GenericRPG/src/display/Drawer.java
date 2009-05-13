package display;

import java.awt.Graphics;
import javax.swing.JPanel;
import player.Player;
import units.UnitEngine;
import weapons.ShotEngine;

public class Drawer extends JPanel
{
	Player p;
	UnitEngine ue;
	ShotEngine se;
	
	public Drawer (Player p, UnitEngine ue, ShotEngine se)
	{
		this.p = p;
		this.ue = ue;
		this.se = se;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		p.draw(g);
		ue.drawUnits(g);
		se.drawShots(g);
	}
}