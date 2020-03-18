public class Agent 
{
	private int y; 
	private int x;
	private int goalY;
	private int goalX;
	
	private int startX;
	private int startY;
	private int moveCounter;
	
	public Agent(int y, int x)
	{
		this.y = y;
		this.x = x;
		this.moveCounter = 0;
	}
	
	//Sets the initial position of the agent -- just like moveTo but doesn't increment moveCounter
	public void establishAgent(Node[][] w, int x, int y)
	{
		this.y = y;
		this.x = x;
		this.startX = x;
		this.startY = y;
		w[this.x][this.y].data = "X";
	}
	
	/**
	 * The agent will move to a given node and set it's value/data to 'X'
	 * @param w the desired Node Object the agent will visit
	 * @param x the x coordinate of the desired Node Object
	 * @param y the y coordinate of the desired Node Object
	 */
	public void moveTo(Node n, int x, int y)
	{
		this.y = y;
		this.x = x;
		n.data = "X";
		this.moveCounter++;
	}
	
	public void moveTo(Node n, int x, int y, String s)
	{
		this.y = y;
		this.x = x;
		if(n.data != "X")
			n.data = s;
		this.moveCounter++;
	}
	
	/**
	 * Used for calculating the distance before the search
	 * @return the distance from the start to the goal
	 */
	public int calculateDistance()
	{	
		//Manhattan-Distance -- (x2 - x1) + (y2 - y1)
		return (Math.abs(this.goalX-this.x) + Math.abs(this.goalY-this.y));
	}
	
	//Getter&Setters
	/**
	 * used to keep track of the distance from the starting point
	 * @return the distance from the agent's current location from its starting point
	 */
	public int getMoveCounter()
	{
		return this.moveCounter;
	}
	public int getStartX()
	{
		return this.startX;
	}
	public int getStartY()
	{
		return this.startY;
	}
	public void setGoal(int x, int y)
	{
		this.goalX = x;
		this.goalY = y;
	}
	public int getY()
	{
		return this.y;
	}
	public void setY(int y)
	{
		this.y = y;
	}
	public int getX()
	{
		return this.x;
	}
	public void setX(int x)
	{
		this.x = x;
	}
	public int getGoalY()
	{
		return this.goalY;
	}
	public int getGoalX()
	{
		return this.goalX;
	}
}