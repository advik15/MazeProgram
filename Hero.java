public class Hero
{
	int r,c;
	String [][]maze;
	boolean[][] visitedLocs;
	String direction;
	int steps;
	public Hero(int r, int c,String direction,String[][] maze,boolean[][] visitedLocs, int steps)
	{
		this.r=r;
		this.c=c;
		this.maze=maze;
		this.direction = direction;
		this.visitedLocs=visitedLocs;
		this.steps = steps;
	}
	public int getSteps()
	{
		return steps;
	}
	public void move(int key)
	{

		if(key==39)//turn right
		{
			if(direction.equals("N"))
			{
				direction= "E";
			}
			else if(direction.equals("E"))
				{
					direction= "S";
				}
				else if(direction.equals("S"))
					{
						direction= "W";
					}
					else if(direction.equals("W"))
						{
							direction= "N";
						}

		}
		if(key==37)//turn left
		{
			if(direction.equals("N"))
			{
				direction= "W";
			}
			else if(direction.equals("W"))
			{
				direction= "S";
			}
			else if(direction.equals("S"))
			{
				direction= "E";
			}
			else if(direction.equals("E"))
			{
				direction= "N";
			}

		}
		if(key==38)//forward
		{

			if(direction.equals("N"))//move North
			{
				try
				{
					if(maze[r-1][c].equals(" "))
					{
						steps++;
						r--;
						visitedLocs[r][c]=true;
					}
				}catch(ArrayIndexOutOfBoundsException e){}
			}
			if(direction.equals("W"))//move West
			{
				if(maze[r][c-1].equals(" "))
				{											steps++;

					c--;
					visitedLocs[r][c]=true;
				}
			}
			if(direction.equals("E"))//move East
			{
				if(maze[r][c+1].equals(" "))
				{
											steps++;

					c++;
					visitedLocs[r][c]=true;
				}

			}
			if(direction.equals("S"))//move East
			{
				if(maze[r+1][c].equals(" "))
				{
											steps++;

					r++;
					visitedLocs[r][c]=true;
				}

			}
		}


	}
	public int getRow()
	{
		return r;
	}

	public int getCol()
	{
		return c;
	}
	public String getDirection()
	{
		return direction;
	}
}


