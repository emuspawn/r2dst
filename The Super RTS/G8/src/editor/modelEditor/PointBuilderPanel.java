package editor.modelEditor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import utilities.Location;

/**
 * adds points to the model
 * @author Jack
 *
 */
public final class PointBuilderPanel extends JPanel
{
	JTextField des = new JTextField();
	JTextField base = new JTextField(); //the point that the added point is dependent upon
	JTextField x = new JTextField(5);
	JTextField y = new JTextField(5);
	JTextField z = new JTextField(5);
	Model m;
	
	public PointBuilderPanel(Model model)
	{
		m = model;
		JPanel points = new JPanel();
		points.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		points.setLayout(new BoxLayout(points, BoxLayout.Y_AXIS));
		points.add(new JLabel("Point Builder Panel"));
		
		JPanel description = new JPanel();
		description.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		description.setLayout(new BoxLayout(description, BoxLayout.Y_AXIS));
		description.add(new JLabel("Description:"));
		description.add(des);
		points.add(description);
		
		JPanel basePoint = new JPanel();
		basePoint.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		basePoint.setLayout(new BoxLayout(basePoint, BoxLayout.Y_AXIS));
		basePoint.add(new JLabel("Base Point:"));
		basePoint.add(base);
		points.add(basePoint);
		
		
		points.add(createPanel("X:", x));
		points.add(createPanel("Y:", y));
		points.add(createPanel("Z:", z));
		JButton add = new JButton("Add Point");
		points.add(add);
		add(points);
		
		add.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					double xl = Double.parseDouble(x.getText());
					double yl = Double.parseDouble(y.getText());
					double zl = Double.parseDouble(z.getText());
					Location l = new Location(xl, yl, zl);
					boolean added = m.addVertex(des.getText(), l, base.getText());
					if(added)
					{
						System.out.println("point added, name: "+des.getText()+", loc: "+l+", relative to: "+base.getText());
					}
					else
					{
						System.out.println("point not added, errors exist in the parameters");
					}
				}
				catch(NumberFormatException a)
				{
					System.out.println("point not added, number format exception");
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
