public class World 
{
	private double nonAccessible;
	public int size;
	private int fWidth;
	private int fLength;
	
	
	public Node[][] world;
	
	public World()
	{
		world = new Node[this.size][this.size];
		
		int counter = 0;
		
		for(int cols = 0; cols < this.size; cols++)
		{
			for(int rows = 0; rows < this.size; rows++)
			{
				world[cols][rows] = new Node(cols, rows);
				world[cols][rows].blockNumber = counter;
			}
		}
	}
	
	public World(int s, int rNodes, int width, int length)
	{
		this.size = s;
		world = new Node[this.size][this.size];
		
		double fraction = ((double)rNodes/100);
		int totalSize = this.size*this.size;
		
		this.nonAccessible = Math.round(fraction * totalSize);

		this.fWidth = width;
		this.fLength = length;
		
		int counter = 0;
		
		for(int cols = 0; cols < this.size; cols++)
		{
			for(int rows = 0; rows < this.size; rows++)
			{
				world[cols][rows] = new Node(cols, rows);
				world[cols][rows].blockNumber = counter;
			}
		}
	}
	
	/**
	 * Displays the current state of the environment
	 */
	public void displayWorld()
	{
		int counter = 1;
		//Displays X-Coordinate #'s
		//System.out.print("     " + 0 + "  " + 1 + "  " + 2 + "  " + 3 + "  "+ 4 + "  "+ 5 + "  "+ 6 + "  "+ 7 + "  "+ 8 + "  "+ 9 + "  "
		//		+ 10 + " " + 11 + " "+ 12 + " "+ 13 + " "+ 14);
		System.out.print("\n     ");
		for(int i = 0; i < this.size; i++)
		{
			if(i < 10)
				System.out.print(i + "  ");
			else
				System.out.print(i + " ");
			
			counter++;
		}
		System.out.print("\n    ");
		for(int i = 0; i < counter; i++)
		{
			System.out.print("---");
		}
		System.out.println();
		
		for(int rows = 0; rows < this.size; rows++)
			
		{
			//Displays Y-Coordinate #'s
			if(rows < 10) //For spacing
				System.out.print(" " + rows + " | ");
			else
				System.out.print(rows+" | ");
			
			for(int cols = 0; cols < this.size; cols++)
			{
				System.out.print(world[cols][rows].data + "  ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	/**
	 * A recursive function that sets 10% nodes to a value of - (cannot be accessed by the agent).
	 * 
	 * @param counter Used as an index and is tested every method call to test with respect to the base case
	 */
	public void generateInaccessibleNodes()
	{
		if(this.nonAccessible > 0) //Will recurse if not 0, if 0 it returns
		{
			int randCol = (int)(Math.random()*this.size);
			int randRow = (int)(Math.random()*this.size);
			
			if(world[randCol][randRow].data == "O")
			{
				world[randCol][randRow].data = "-";
				this.nonAccessible--;
				generateInaccessibleNodes();
			}
			else
				generateInaccessibleNodes();		
		}
		
		if(this.nonAccessible == 0) //Base case
			return;
	}
	
	/**
	 * Iteratively goes through the 2D array and sets points to adjacent nodes. Also sets the parent for each node
	 * Positions (Left, Right, Up, Down) are relative to a vector (ie the smaller the Y-Value decreases when Agent moves up & increments when agent goes down.
	 */
	public void populateGraph()
	{
		for(int y = 0; y < this.size; y++)
		{
			for(int x = 0; x < this.size; x++)
			{
				if(!(world[x][y].x == 0))
				{
					world[x][y].left = world[x-1][y];
					//world[x-1][y].parent = world[x][y];
				}
					
				if(!(world[x][y].y == 0))
				{
					world[x][y].up = world[x][y-1]; //Relative to a vector (the smaller the # the more it goes up)
					//world[x][y-1].parent = world[x][y];
				}
						
				if(!(world[x][y].x == this.size-1))
				{
					world[x][y].right = world[x+1][y];
					//world[x+1][y].parent = world[x][y];
				}

				if(!(world[x][y].y == this.size-1))
				{
					world[x][y].down = world[x][y+1];
					//world[x][y+1].parent = world[x][y];
				}
					
			}
		}
	}
	
	//Getters & Setters
	public Node[][] getWorld()
	{
		return world;
	}
	public void setGoal(int x, int y)
	{
		world[x][y].data = "G";
	}

	public int getSize() 
	{
		return size;
	}

	public void setSize(int size) 
	{
		this.size = size;
	}
	
}