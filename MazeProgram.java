import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;


public class MazeProgram extends JPanel implements KeyListener
{
	JFrame frame;
	String[][]maze;
	boolean[][] visitedLocs;
	int x =100,y=100;
	Hero hero;
	boolean is3D=false;
ArrayList<Wall> walls;

	public MazeProgram()
	{
		frame = new JFrame("A-mazing Program");
		frame.add(this);
		frame.setSize(1400,800);
		  walls = new ArrayList<Wall>();
		setMaze();
		if(is3D)
		{
			createWalls();
		}
		frame.addKeyListener(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		g.setColor(new Color(223,158,27));
		g.fillRect(0,0,frame.getWidth(),frame.getHeight());
		if(!is3D)
		{
		g.setColor(Color.BLACK);
	//	g.fillRect(100,100,500,500);
	//	g.setColor(Color.WHITE);
	//	g.drawRect(100,100,500,500);
	//	g.fillOval(100,100,500,500);

			for(int r = 0;r<maze.length;r++)
			{
				for(int c = 0;c<maze[r].length;c++)
				{
					if(maze[r][c].equals("@"))
					{

						g.fillRect(c*20,r*20,20,20);

					}
				}
				System.out.println();
			}
			g.setColor(Color.CYAN);
			g.fillOval(hero.getCol()*20,hero.getRow()*20,20,20);
			g.setColor(new Color(0,0,255));
		}
		else
		{
			for(Wall wall:walls)
			{
				//g.setColor(new Color(175,175,175));
				g.setColor(wall.getColor());
				g.fillPolygon(wall.getPoly());
				g.setColor(Color.WHITE);
				g.drawPolygon(wall.getPoly());

			}

		}
		g.setFont(new Font("Serif",Font.PLAIN,40));
					g.drawString("Steps Taken:", 850,700);
					g.setColor(new Color(0,0,255));
					g.setFont(new Font("Serif",Font.PLAIN,40));
					g.drawString(String.valueOf(hero.getSteps()),1070,700);



	}
	public void setMaze()
	{
		maze = new String[20][60];
		visitedLocs=new boolean[20][60];

		try
		{
			File name = new File("Maze.java");
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text;
			int r = 0;
			while((text=input.readLine())!=null)
			{
				maze[r]=text.split("");
				r++;

			}

		}catch(IOException e)
		{

		}
		int steps =0;
		visitedLocs[0][1]=true;
		hero = new Hero(0,1,"S",maze,visitedLocs,steps);

		/*for(int r = 0;r<maze.length;r++)
		{
			for(int c = 0;c<maze[r].length;c++)
			{
				System.out.print(maze[r][c]);
			}
			System.out.println();
		}*/
	}

	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode()==32)//spacebar
		{
			is3D=!is3D;
		}
		else
		{
			hero.move(e.getKeyCode());
		}
		if(is3D)
		{
			createWalls();
		}
		repaint();
	}
	public void keyReleased(KeyEvent e)
	{
	}
	public void keyTyped(KeyEvent e)
	{
	}
	public void createWalls()
	{
		walls = new ArrayList<Wall>();
		int r = hero.getRow();
		int c = hero.getCol();
		String di = hero.getDirection();
		for(int a = 0;a<5;a++)
		{

			walls.add(leftWall(a));
			walls.add(rightWall(a));
			walls.add(leftPath(a));
			walls.add(rightPath(a));
			walls.add(ceilingWall(a));
			walls.add(floorWall(a));

			switch(di)
			{
				case "S":

					try
					{
						Wall wall=floorWall(a);
						walls.add(wall);
						if(visitedLocs[r+a][c])
							wall.setColor(Color.GREEN);
						if(maze[r+a][c+1].equals("@"))
						{
							walls.add(leftWall(a));
						}

						if(maze[r+a][c-1].equals("@"))
						{
							walls.add(rightWall(a));
						}


					}catch(ArrayIndexOutOfBoundsException e){}
				break;
				case "N":
					try
					{
						Wall wall=floorWall(a);
						walls.add(wall);
						if(visitedLocs[r-a][c])
							wall.setColor(Color.GREEN);
						if(maze[r-a][c-1].equals("@"))
						{
							walls.add(leftWall(a));
						}
						if(maze[r-a][c+1].equals("@"))
						{
							walls.add(rightWall(a));
						}
					}catch(ArrayIndexOutOfBoundsException e){}
						break;
				case "W":

					try
					{
						Wall wall=floorWall(a);
						walls.add(wall);
						if(visitedLocs[r][c-a])
							wall.setColor(Color.GREEN);
						if(maze[r+1][c-a].equals("@"))
						{
							walls.add(leftWall(a));
						}
						if(maze[r-1][c-a].equals("@"))
						{
							walls.add(rightWall(a));
						}
					}catch(ArrayIndexOutOfBoundsException e){}
				break;

			case "E":
				try
				{
					Wall wall=floorWall(a);
					walls.add(wall);
					if(visitedLocs[r][c+a])
							wall.setColor(Color.GREEN);
					if(maze[r-1][c+a].equals("@"))
					{
						walls.add(leftWall(a));
					}
					if(maze[r+1][c+a].equals("@"))
					{
						walls.add(rightWall(a));
					}
				}catch(ArrayIndexOutOfBoundsException e){}
			break;


			}


		}
		for(int a = 5;a>=0;a--)
		{

			switch(di)
			{
				case "S":

					try
					{
						if(maze[r+a][c].equals("@"))
						{
							walls.add(wallInFront(a));
						}
						if(maze[r+a][c].equals("E"))
						{
							Wall wall=wallInFront(a);
							walls.add(wall);
							wall.setColor(Color.GREEN);
						}
						if(r==0&&c==1)
						{
						}
                       else if(maze[r+a][c].equals("!"))
						{
							Wall wall=wallInFront(a);
							walls.add(wall);
							wall.setColor(Color.BLACK);
					}
					}catch(ArrayIndexOutOfBoundsException e){}
				break;
				case "N":

					try
					{
						if(maze[r-a][c].equals("@"))
						{

							walls.add(wallInFront(a));
						}
						if(maze[r-a][c].equals("!"))
						{
							Wall wall=wallInFront(a);
							walls.add(wall);
							wall.setColor(Color.BLACK);
						}
					}catch(ArrayIndexOutOfBoundsException e){}
						break;
				case "W":

					try
					{
						if(maze[r][c-a].equals("@"))
						{
						walls.add(wallInFront(a));
						}
						if(maze[r][c-a].equals("!"))
						{
						Wall wall=wallInFront(a);
						walls.add(wall);
						wall.setColor(Color.BLACK);
						}
					}catch(ArrayIndexOutOfBoundsException e){}
				break;

			case "E":
				try
				{
					if(maze[r][c+a].equals("@"))
					{
						walls.add(wallInFront(a));
					}
					if(maze[r][c+a].equals("!"))
						{
						Wall wall=wallInFront(a);
						walls.add(wall);
						wall.setColor(Color.BLACK);
						}
				}catch(ArrayIndexOutOfBoundsException e){}
			break;


			}

		}



	}
	public Wall leftWall(int a)
	{
		int []x = {100+50*a,150+50*a,150+50*a,100+50*a};
		int []y = {100+50*a,150+50*a,650-50*a,700-50*a};
		Color color=new Color(145,245,123);
		return new Wall(x,y,color);
	}
	public Wall rightWall(int a)
	{
		int []x = {700-50*a,650-50*a,650-50*a,700-50*a};
		int []y = {100+50*a,150+50*a,650-50*a,700-50*a};
		Color color=new Color(123,213,212);
		return new Wall(x,y,color);
	}

	public Wall leftPath(int a)
	{
		int []x = {100+50*a,150+50*a,150+50*a,100+50*a};
		int []y = {150+50*a,150+50*a,650-50*a,650-50*a};
		Color color=new Color(100,150,200);
		return new Wall(x,y,color);
	}
	public Wall rightPath(int a)
	{
		int []x = {700-50*a,650-50*a,650-50*a,700-50*a};
		int []y = {150+50*a,150+50*a,650-50*a,650-50*a};
		Color color=new Color(100,150,200);
		return new Wall(x,y,color);
	}




	public Wall wallInFront(int a)
	{
		int []x = {100+50*a,700-50*a,700-50*a,100+50*a};
		int []y = {100+50*a,100+50*a,700-50*a,700-50*a};
		Color color=new Color(200,200,200);
		return new Wall(x,y,color);

	}

	public Wall ceilingWall(int a)
	{
	int []x = new int[]{150+50*a,650-50*a,700-50*a,100+50*a};
	int []y = {150+50*a,150+50*a,100+50*a,100+50*a};
	Color color=new Color(150,150,150);
	return new Wall(x,y,color);
	}
	public Wall floorWall(int a)
	{
	int []x = new int[]{150+50*a,650-50*a,700-50*a,100+50*a};
	int []y = {650-50*a,650-50*a,700-50*a,700-50*a};
	Color color=new Color(200,200,200);
	return new Wall(x,y,color);
	}
	public Wall floorWallCrumbs(int a)
	{
	int []x = new int[]{150+50*a,650-50*a,700-50*a,100+50*a};
	int []y = {650-50*a,650-50*a,700-50*a,700-50*a};
	Color color=new Color(100,150,100);
	return new Wall(x,y,color);
	}


	public static void main(String[]args)
	{
		MazeProgram app = new MazeProgram();
		System.out.println("hello");


	}

	}



