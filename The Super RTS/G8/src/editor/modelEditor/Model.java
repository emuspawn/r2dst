package editor.modelEditor;

import graphics.GLCamera;

import java.awt.Color;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL;

import com.sun.opengl.util.j2d.TextRenderer;

import utilities.Location;
import utilities.Prism;

/**
 * represents a model
 * @author Jack
 *
 */
public final class Model
{
	/**
	 * the name of the model
	 */
	private String name = new String("Untitled");
	/**
	 * a description of the model
	 */
	private String description = new String();
	/**
	 * stores the vertices of the model, key=name of vertex, value=location of vertex
	 */
	private HashMap<String, Location> v = new HashMap<String, Location>(); //vertices
	/**
	 * stores the manner in which vertices are dependent on other vertices,
	 * key=name of the vertex, value=dependent verteces, depedent vertices have
	 * their positions stored relative to the position of the vertex that they
	 * are dependent upon
	 */
	private HashMap<String, LinkedList<String>> dependencies = new HashMap<String, LinkedList<String>>();
	/**
	 * the triangles that the model is made up of
	 */
	private ArrayList<Triangle> t = new ArrayList<Triangle>();
	
	/**
	 * creates a model with the passed parameters
	 * @param name the name of the model
	 * @param description the description of the model
	 * @param v the vertices of the model
	 * @param d the dependencies of the vertices of the model
	 * @param t the triangles of the model
	 */
	public Model(String name, String description, HashMap<String, Location> v, HashMap<String, LinkedList<String>> d, ArrayList<Triangle> t)
	{
		this.name = name;
		this.description = description;
		this.v = v;
		this.dependencies = d;
		this.t = t;
	}
	/**
	 * creates a new model with one vertex at (0, 0, 0) named "origin"
	 */
	public Model()
	{
		v.put("origin", new Location(0, 0, 0));
	}
	/**
	 * adds a vertex to the model
	 * @param name the name of the vertex being added
	 * @param l the location of the vertex
	 * @param base the vertex that is the base of this vertex, the vertex that this
	 * vertex's position is calculated relative to
	 * @return returns true if the vertex was correctly added to the model, false otherwise
	 */
	public boolean addVertex(String name, Location l, String base)
	{
		if(!v.containsKey(name) && v.containsKey(base))
		{
			v.put(name, l);
			if(dependencies.get(base) != null)
			{
				dependencies.get(base).add(name);
			}
			else
			{
				LinkedList<String> list = new LinkedList<String>();
				list.add(name);
				dependencies.put(base, list);
			}
			return true;
		}
		return false;
	}
	/**
	 * modifies a vertex's location, recursively modifies dependent positions
	 * through use of a helper method
	 * @param vertex the name of the vertex being modified
	 * @param l the new location of the vertex
	 */
	public void modifyVertex(String vertex, Location l)
	{
		modifyVertexHelper(vertex, l);
	}
	/**
	 * goes down the tree formed by vertices and their dependent vertives and properly
	 * updates all positions
	 * @param vertex
	 * @param l
	 */
	private void modifyVertexHelper(String vertex, Location l)
	{
		if(dependencies.get(vertex) != null)
		{
			Location old = v.get(vertex);
			List<String> list = dependencies.get(vertex);
			Iterator<String> i = list.iterator();
			while(i.hasNext())
			{
				String name = i.next();
				Location temp = v.get(name);
				double xover = temp.x-old.x;
				double yover = temp.y-old.y;
				double zover = temp.z-old.z;
				Location newLoc = new Location(l.x+xover, l.y+yover, l.z+zover);
				modifyVertex(name, newLoc);
			}
		}
		v.put(vertex, l);
	}
	/**
	 * modifies the position of the origin of the model, no other verteces
	 * are modified by this method
	 * @param l the new location of the origin
	 */
	public void modifyOrigin(Location l)
	{
		v.put("origin", l);
	}
	/**
	 * removes the given vertex from the model and recursively
	 * deletes all dependent vertices
	 * @param name the name of the vertex to remove
	 */
	public void removeVertex(String name)
	{
		if(!name.equalsIgnoreCase("origin"))
		{
			v.remove(name);
			List<String> l = dependencies.get(name);
			if(l != null)
			{
				Iterator<String> i = l.iterator();
				while(i.hasNext())
				{
					removeVertex(i.next());
				}
				dependencies.remove(name);
			}
		}
	}
	/**
	 * creates a triange that is linked to the passed three vertices, if the
	 * position of the vertices changes the triange is still drawn connecting them
	 * @param vertex1
	 * @param vertex2
	 * @param vertex3
	 */
	public void formTriangle(String vertex1, String vertex2, String vertex3, Color c)
	{
		if(v.containsKey(vertex1) && v.containsKey(vertex2) && v.containsKey(vertex3))
		{
			t.add(new Triangle(vertex1, vertex2, vertex3, c, v));
		}
	}
	/**
	 * draws the model
	 * @param gl
	 */
	public void drawModel(GL gl)
	{
		gl.glBegin(GL.GL_TRIANGLES);
		Iterator<Triangle> i = t.iterator();
		while(i.hasNext())
		{
			i.next().drawTriangle(gl, v);
		}
		gl.glEnd();
	}
	/**
	 * draws the normal vectors of the triangles that make up the model
	 * @param gl
	 */
	public void drawNormalVectors(GL gl)
	{
		Iterator<Triangle> i = t.iterator();
		while(i.hasNext())
		{
			i.next().drawNormal(gl, v);
		}
	}
	/**
	 * draws the vertices of the model
	 * @param gl
	 * @param size the size the vertices are to be when drawn
	 * @param drawNames if true then the names of the vertices are drawn next to them
	 * @param maxDistance the maximum distance the vertices can be from the camera
	 * before the vertex name is not drawn
	 */
	public void drawVertices(GL gl, GLCamera c, double size, boolean drawNames, double maxDistance)
	{
		Font font = new Font("SansSerif", Font.PLAIN, 12);
        TextRenderer tr = new TextRenderer(font, true, false);
		Iterator<String> i = v.keySet().iterator();
		while(i.hasNext())
		{
			String name = i.next();
			gl.glColor3d(255, 0, 0);
			new Prism(v.get(name), size, size, size).drawPrism(gl);
			if(drawNames && c.getLocation().distanceTo(v.get(name)) <= maxDistance)
			{
				tr.setColor(Color.white);
				Location l = c.project(v.get(name));
				tr.beginRendering((int)c.getWidth(), (int)c.getHeight());
				tr.draw(name, (int)l.x, (int)l.y);
				tr.endRendering();
			}
		}
	}
	/**
	 * writes the model's information to the passed output stream
	 * @param dos
	 */
	public void writeModel(DataOutputStream dos)
	{
		ModelIO.writeModel(dos, name, description, v, dependencies, t);
	}
	/**
	 * reads the information from the model file specified by the
	 * input stream
	 * @param dis
	 */
	public void readModel(DataInputStream dis)
	{
		Model m = ModelIO.readModel(dis);
		name = m.name;
		description = m.description;
		v = m.v;
		dependencies = m.dependencies;
		t = m.t;
	}
}
/**
 * handles the IO functions for the model
 * @author Jack
 *
 */
final class ModelIO
{
	private static final int version = 1;
	/**
	 * writes the model to the passed output stream
	 * @param dos the output stream the model is to be written to
	 * @param v hashmap of vertices
	 * @param d dependent vertices
	 * @param t triangles
	 */
	public static void writeModel(DataOutputStream dos, String name, String description, HashMap<String, Location> v, 
			HashMap<String, LinkedList<String>> d, ArrayList<Triangle> t)
	{
		try
		{
			System.out.println("writing model...");
			
			dos.writeInt(version);
			System.out.println("version = "+version);
			
			System.out.print("writing name and description... ");
			dos.writeInt(name.length());
			dos.writeChars(name);
			dos.writeInt(description.length());
			dos.writeChars(description);
			System.out.println("done");
			
			System.out.println("writing "+v.keySet().size()+" vertices... ");
			dos.writeInt(v.keySet().size());
			Iterator<String> vi = v.keySet().iterator(); //vertex iterator
			while(vi.hasNext())
			{
				String key = vi.next();
				dos.writeInt(key.length());
				dos.writeChars(key);
				Location l = v.get(key);
				dos.writeDouble(l.x);
				dos.writeDouble(l.y);
				dos.writeDouble(l.z);
				System.out.println(key+" = "+l);
			}
			System.out.println("done");
			
			System.out.println("writing "+d.keySet().size()+" depedencies... ");
			dos.writeInt(d.keySet().size());
			vi = v.keySet().iterator(); //vertex iterator
			while(vi.hasNext())
			{
				String key = vi.next();
				if(d.keySet().contains(key))
				{
					Iterator<String> di = d.get(key).iterator(); //dependency iterator
					System.out.println(key+", "+d.get(key).size()+" dependents");
					
					dos.writeInt(key.length());
					dos.writeChars(key);
					dos.writeInt(d.get(key).size());
					
					while(di.hasNext())
					{
						String dependent = di.next();
						dos.writeInt(dependent.length());
						dos.writeChars(dependent);
					}
				}
			}
			System.out.println("done");
			
			System.out.println("writing "+t.size()+" triangles...");
			dos.writeInt(t.size());
			Iterator<Triangle> ti = t.iterator();
			while(ti.hasNext())
			{
				Triangle tri = ti.next();
				String[] vertices = tri.getVerteces();
				for(int i = 2; i >= 0; i--)
				{
					dos.writeInt(vertices[i].length());
					dos.writeChars(vertices[i]);
					System.out.print(vertices[i]+" ");
				}
				System.out.println();
				dos.writeInt(tri.getColor().getRed());
				dos.writeInt(tri.getColor().getGreen());
				dos.writeInt(tri.getColor().getBlue());
			}
			System.out.println("done");
			
			System.out.println("done");
		}
		catch(IOException e)
		{
			System.out.println("io exception in writing the model");
		}
	}
	public static Model readModel(DataInputStream dis)
	{
		try
		{
			int version = dis.readInt();
			if(version == 1)
			{
				return readModelV1(dis);
			}
		}
		catch(IOException e)
		{
			System.out.println("io exception in reading the model");
		}
		return null;
	}
	private static Model readModelV1(DataInputStream dis) throws IOException
	{
		//name and description
		String name = readString(dis);
		String description = readString(dis);
		
		//vertices
		HashMap<String, Location> v = new HashMap<String, Location>(); //vertices
		int length = dis.readInt();
		for(int i = 0; i < length; i++)
		{
			String vertexName = readString(dis);
			double x = dis.readDouble();
			double y = dis.readDouble();
			double z = dis.readDouble();
			v.put(vertexName, new Location(x, y, z));
		}
		
		//depedencies
		HashMap<String, LinkedList<String>> d = new HashMap<String, LinkedList<String>>(); //dependencies
		length = dis.readInt();
		for(int i = 0; i < length; i++)
		{
			LinkedList<String> dList = new LinkedList<String>();
			String dName = readString(dis);
			int dLength = dis.readInt();
			for(int a = 0; a < dLength; a++)
			{
				String n = readString(dis);
				dList.add(n);
			}
			d.put(dName, dList);
		}
		
		//triangles
		ArrayList<Triangle> t = new ArrayList<Triangle>(); //triangles
		length = dis.readInt();
		for(int i = 0; i < length; i++)
		{
			String v1 = readString(dis); //vertex 1
			String v2 = readString(dis);
			String v3 = readString(dis);
			System.out.println(v1+", "+v2+", "+v3);
			int r = dis.readInt();
			int g = dis.readInt();
			int b = dis.readInt();
			t.add(new Triangle(v1, v2, v3, new Color(r, g, b), v));
		}
		
		return new Model(name, description, v, d, t);
	}
	private static String readString(DataInputStream dis) throws IOException
	{
		int length = dis.readInt();
		String s = "";
		for(int i = 0; i  < length; i++)
		{
			s+=dis.readChar();
		}
		return s;
	}
}
final class Triangle
{
	private String p1;
	private String p2;
	private String p3;
	private Location normal;
	private Color c;
	
	/*
	 * the old locations of the vertices of the triangle, stored so
	 * that the triangle can determine when to recalculate the normal
	 * vector
	 */
	private Location old1;
	private Location old2;
	private Location old3;
	
	/**
	 * creates a triangle based off the passed points, also calculates
	 * the normal of the triangle
	 * @param vertex1
	 * @param vertex2
	 * @param vertex3
	 */
	public Triangle(String vertex1, String vertex2, String vertex3, Color c, HashMap<String, Location> v)
	{
		this.p1 = vertex1;
		this.p2 = vertex2;
		this.p3 = vertex3;
		this.c = c;
		
		determineNormal(v);
		updateOldLocations(v);
	}
	/**
	 * gets the color of this triangle
	 * @return
	 */
	public Color getColor()
	{
		return c;
	}
	/**
	 * creates an array of length 3 where the first index corresponds to the first
	 * vertex of the triangle, etc
	 * @return returns an array containing the triangle's vertices
	 */
	public String[] getVerteces()
	{
		String[] s = {p1, p2, p3};
		return s;
	}
	/**
	 * updates the stored old locations of the triangle
	 * @param v
	 */
	private void updateOldLocations(HashMap<String, Location> v)
	{
		Location l = v.get(p1);
		old1 = new Location(l.x, l.y, l.z);
		l = v.get(p2);
		old2 = new Location(l.x, l.y, l.z);
		l = v.get(p3);
		old3 = new Location(l.x, l.y, l.z);
	}
	/**
	 * compares the old locations to the new locations of the vertices of
	 * the triange, if they do not match a new normal is calculated
	 * @param l1
	 * @param l2
	 * @param l3
	 * @return returns true if all the locations have not changed, false otherwise
	 */
	private boolean locationsMatch(Location l1, Location l2, Location l3)
	{
		if(old1.compareTo(l1) != 0 || old2.compareTo(l2) != 0 || old3.compareTo(l3) != 0)
		{
			return false;
		}
		return true;
	}
	/**
	 * determines the normal of the surface defined by the three points, must be called
	 * each time the location of the vertices that make up this triangle are changed
	 */
	private void determineNormal(HashMap<String, Location> v)
	{
		Location p1 = v.get(this.p1);
		Location p2 = v.get(this.p2);
		Location p3 = v.get(this.p3);
		
		Location v1 = new Location(p2.x-p1.x, p2.y-p1.y, p2.z-p1.z); //vector 1
		Location v2 = new Location(p3.x-p1.x, p3.y-p1.y, p3.z-p1.z);
		normal = new Location(v1.y*v2.z-v2.y*v1.z, v2.x*v1.z-v1.x*v2.z, v1.x*v2.y-v2.x*v1.y);
		//System.out.println("v1 = "+v1);
		//System.out.println("v2 = "+v2);
		//System.out.println("normal = "+normal);
		double length = Math.sqrt(Math.pow(normal.x, 2)+Math.pow(normal.y, 2)+Math.pow(normal.z, 2));
		normal = new Location(normal.x/length, normal.y/length, normal.z/length);
		//System.out.println("normalized normal = "+normal);
	}
	/**
	 * draws the three points of the triangle, assumes that the GL is
	 * already in the proper draw mode, also adds normals for the points
	 * automatically
	 * @param gl
	 * @param v
	 */
	public void drawTriangle(GL gl, HashMap<String, Location> v)
	{
		Location p1 = v.get(this.p1);
		Location p2 = v.get(this.p2);
		Location p3 = v.get(this.p3);
		
		if(!locationsMatch(p1, p2, p3))
		{
			updateOldLocations(v);
			determineNormal(v);
		}
		
		gl.glColor3d(c.getRed(), c.getGreen(), c.getBlue());
		gl.glNormal3d(normal.x, normal.y, normal.z);
		gl.glVertex3d(p1.x, p1.y, p1.z);
		gl.glVertex3d(p2.x, p2.y, p2.z);
		gl.glVertex3d(p3.x, p3.y, p3.z);
	}
	/**
	 * draws the normal of this triangle
	 * @param gl
	 * @param v
	 */
	public void drawNormal(GL gl, HashMap<String, Location> v)
	{
		Location p1 = v.get(this.p1);
		Location p2 = v.get(this.p2);
		Location p3 = v.get(this.p3);
		gl.glPushMatrix();
		gl.glColor3d(255, 0, 0);
		Location center = new Location((p1.x+p2.x+p3.x)/3, (p1.y+p2.y+p3.y)/3, (p1.z+p2.z+p3.z)/3);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3d(center.x, center.y, center.z);
		gl.glVertex3d(center.x+normal.x, center.y+normal.y, center.z+normal.z);
		gl.glEnd();
		gl.glPopMatrix();
	}
}
