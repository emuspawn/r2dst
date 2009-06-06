package editor.modelEditor;

import javax.swing.*;

/**
 * the panel that adds points to the model and connects them, displayed
 * to the right of the main viewing canvas
 * @author Jack
 *
 */
public final class BuilderPanel extends JPanel
{
	public BuilderPanel(Model m)
	{
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(new JLabel("Builder"));
		p.add(new PointBuilderPanel(m));
		p.add(new TriangleBuilderPanel(m));
		add(p);
	}
}
