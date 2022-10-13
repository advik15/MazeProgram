import java.awt.*;
public class Wall
{
	private int[] x;
	private int[] y;
	Color color;
	public Wall(int[]x,int[]y,Color color)
	{
		this.y=y;
		this.x=x;
		this.color=color;
	}
	public Polygon getPoly()
	{
		return new Polygon(x,y,y.length);

	}
	public Color getColor()
	{
		return color;
	}
	public void setColor(Color color)
	{
		this.color=color;
	}


}


