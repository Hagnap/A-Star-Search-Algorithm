public class Node 
{
	//TODO: Make these private and then create getters & setters
	public int x;
	public int y;
	public boolean goal;
	public String data; //O, -, X, or G
	public int blockNumber;
	
	public Node left;
	public Node right;
	public Node up;
	public Node down;
	
	public Node parent;
	public boolean head; //A node in the path
	public boolean tail; //Farthest node in path

	public int g; //Distance from start
	public int h; //Distance to the goal
	public int f;
	final public int cost = 10;
	
	
	public Node(int x1, int y1)
	{
		this.x = x1;
		this.y = y1;
		this.data = "O"; //All values are initially set to O - Will change when environment is created
		this.left = null;
		this.right = null;
		this.up = null;
		this.down = null;
	}
	
	//Used to create neighbor Node Objs in Driver, useless outside of that
	public Node()
	{
		
	}
	

	/**
	 * Calculates the distance from the current node from the start node
	 * @param a The agent inside the environments
	 * @return Integer value of the distance from the current node to the start node
	 */
	public int calculateG(Agent a)
	{
		this.g = (Math.abs(this.x - a.getStartX()) + (Math.abs(this.y - a.getStartY())));
		return this.g;
	}
	
	/**
	 * Calculates the distance from the goal node from the current node
	 * @param a The agent inside the environments
	 * @return Integer value of the distance from the goal node to the current node
	 */
	public int calculateH(Agent a)
	{	
		//Manhattan-Distance -- (x2 - x1) + (y2 - y1)
		this.h = (Math.abs(a.getGoalX() - this.x) + Math.abs(a.getGoalY() - this.y));
		return h;
	}
	
	/**
	 * Calculates the distance from the start node from the goal node
	 * @param a The agent inside the environments
	 * @return Integer value of the total distance from the start node to the goal node
	 */
	public int calculateF(Agent a)
	{
		//Distance to the goal + step cost (each step cost 1)
		return calculateH(a) + 1; //doing calculateH(a) + calculateG(a) caused bugs; The code used didn't cause bugs.. 
	}
	
	public boolean isGoal()
	{
		if(this.data.equals("G"))
			return true;
		else
			return false;
	}
	
	@Override
	public String toString()
	{
		return "Data: " + this.data + ", (" + this.x + "," + this.y + ")";
	}
	
	public boolean equalsNode(Node n)
	{
		if(this.x == n.x && this.y == n.y)
			return true;
		else if(n == null)
			return false;
		else
			return false;
	}
}
