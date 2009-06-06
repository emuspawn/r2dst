package editor.modelEditor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * creates triangles for the model
 * @author Jack
 *
 */
public final class TriangleBuilderPanel extends JPanel
{
	JTextField point1 = new JTextField(5);
	JTextField point2 = new JTextField(5);
	JTextField point3 = new JTextField(5);
	
	JTextField red = new JTextField(5);
	JTextField green = new JTextField(5);
	JTextField blue = new JTextField(5);
	
	Model m;
	
	public TriangleBuilderPanel(Model model)
	{
		m = model;
		JPanel points = new JPanel();
		points.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		points.setLayout(new BoxLayout(points, BoxLayout.Y_AXIS));
		points.add(new JLabel("Triangle Builder Panel"));
		points.add(createPanel("Point 1:", point1));
		points.add(createPanel("Point 2:", point2));
		points.add(createPanel("Point 3:", point3));
		points.add(createPanel("Red:", red));
		points.add(createPanel("Green:", green));
		points.add(createPanel("Blue:", blue));
		JButton create = new JButton("Create Triangle");
		points.add(create);
		add(points);
		create.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					int r = Integer.parseInt(red.getText());
					int g = Integer.parseInt(green.getText());
					int b = Integer.parseInt(blue.getText());
					m.formTriangle(point1.getText(), point2.getText(), point3.getText(), new Color(r, g, b));
					
					System.out.println("triangle formed, p1="+point1.getText()+", p2="+point2.getText()+
							", p3="+point3.getText()+", c=("+r+", "+g+", "+b+")");
				}
				catch(NumberFormatException a)
				{
					System.out.println("triangle not formed, errors exist in the parameters");
				}
			}
		});
	}
	private JPanel createPanel(String label, Component c)
	{
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		p.add(new JLabel(label));
		p.add(c);
		return p;
	}
}
