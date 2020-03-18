/**
 * The World class consist of a SizexSize (value changes due to user input) 2D-Array of Node objects.
 * Each element will have a value of -, O, X, or G
 * O means that the element(node) can be can be accessed by the agent
 * - means that the element(node) can't be accessed by the agent
 * X means that the element (node) is the start
 * * is the agent's path
 * G means that the element (node) is the goal 
 * Sets a random 10% of the elements(nodes) to '-' to serve as an obstruction
 * 
 * @author Jacob Haggard
 */

import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.awt.Color;
import java.util.ArrayList;

public class Driver 
{
	public static GUI_Controller gui;
	
	public static void main(String[] args) throws InterruptedException
	{	
		int worldSize = 15, randomNodes = 23, frameWidth = 1250, frameLength = 800;
		
		//Gets input for world size and percentage of obstacles
		Scanner scnr = new Scanner(System.in);
		System.out.print("Enter a number for the size of the cube-shaped grid , size*size, (15 is default): ");
		worldSize = scnr.nextInt();
		if(worldSize == 0)
			worldSize = 15;
		System.out.print("Enter a percentage of blocks for the grid (10% is default): ");
		randomNodes = scnr.nextInt();
		if(randomNodes == 0)
			randomNodes = 10;

		//Creates a World Obj using the input from the user
		World world = new World(worldSize, randomNodes, frameWidth, frameLength);
		world.generateInaccessibleNodes();
		world.displayWorld();
		
		//Creates an agent and puts its into the environment and puts a goal in the environment; Done via user input
		Agent agent = intializeEnvironment(world);
		
		//PriorityQueue for Open List of nodes to choose from using the NodeComparator class
		PriorityQueue<Node> queue = new PriorityQueue<>(new NodeComparator(agent));
		//Lists of visited nodes - can't repeat steps
		LinkedList<Node> visited = new LinkedList<>();
		//List of nodes to store the correct path
		LinkedList<Node> path = new LinkedList<>();
		//Used to store the neighbors of the current node
		ArrayList<Node> neighbors = new ArrayList<>();
		//Values used to store distances from goal; uses nodes adjacent to the current node (up, down, left, right)
		int lDistance = 0, rDistance = 0, uDistance = 0, dDistance = 0;
		boolean isFound = false;
		
		
		
		System.out.println("The agent must travel " + agent.calculateDistance() + " space(s) to reach the goal.");
		System.out.println("The agent must get to the coordinate (" + agent.getGoalX() + "," + agent.getGoalY() + ").\n");
		
		//If the agent's initial starting spot is the goal then the program terminates. No need to go farther
		if(world.world[agent.getX()][agent.getY()].isGoal())
		{
			System.out.println("Goal has been found!");
			System.exit(0); 
		}
			
		//Establishes pointer relations for the nodes in the World (ie "tree")
		world.populateGraph();
		System.out.println();
		
		//Creates the gui
		gui = new GUI_Controller(agent.getStartX(), agent.getStartY(), agent.getGoalX(), agent.getGoalX(), world, agent);
		gui.frame.setVisible(true);
		
		//Adds the starting node into the Priority Queue to begin the search -- Source of the path
		queue.add(world.world[agent.getStartX()][agent.getStartY()]);
		
		//Sets the G to 0 (has yet to move from start) & then calculates the F (H is calculated inside calculateF)
		world.world[agent.getStartX()][agent.getStartY()].g = 0;
		world.world[agent.getStartX()][agent.getStartY()].calculateF(agent);
		
		/*
		USED FOR DEBUGGING COMPARATOR -- Ignore this useless code
		--------------------------------------------------------------
		world.world[agent.getGoalX()-1][agent.getGoalY()+1].data = "T";
		world.displayWorld();
		queue.add(world.world[agent.getGoalX()-1][agent.getGoalY()+1]);
		System.out.println(world.world[agent.getX()][agent.getY()].calculateF(agent));
		System.out.println(world.world[agent.getGoalX()-1][agent.getGoalY()+1].calculateF(agent));
		
		while(!queue.isEmpty())
		{
			System.out.println(queue.poll());
		}
		*/
		
		//Pause before it starts -- To let me open the GUI in time
		Thread.sleep(2000);
		
		//Begins the search
		Node goal = AStar(world, queue, visited, neighbors, path, agent, isFound);
		if(goal != null)
			System.out.println(goal);
		else
		{
			System.out.println("No path found...");
			System.exit(0);
		}
		
		world.displayWorld();
		
	}//End of main
	
	public static Node AStar(World world, PriorityQueue<Node> queue, LinkedList<Node> visited, ArrayList<Node> neighbors, LinkedList<Node> path, Agent agent, Boolean isFound) throws InterruptedException 
	{
		while(!queue.isEmpty() && !isFound)
		{
			Node current = queue.poll();
			Thread.sleep(100);
			
			if(current.data == "G")
			{
				System.out.println("Found the path!");
				isFound = true;
				return world.world[current.x][current.y];
			}
			
			//Removes the current node from the Priority Queue -- Not a result
			queue.remove(current);
			//Adds it to the closedList -- To avoid traversing it again
			visited.add(current);
			
			//Store the neighbors of the current node into an array
			neighbors = getNeighbors(current, agent);
			
			neighbors.sort(new NodeComparator(agent));
			
			Node n = new Node();
			
			while(!neighbors.isEmpty())
			{
				//Retrieves the neighbor with the lowest F value
				n = neighbors.remove(0);
				
				//Sets parent of the neighbor node to be the current node 
				n.parent = current;
				
				n.calculateF(agent);
				
				//If not in closedList -- IE if it has yet to be traversed
				if(!visited.contains(n))
				{
					// DEBUGGING -- System.out.println(n.x + "," + n.y); 
					
					//If not in queue, add it to queue-- Makes it a potential Node to search
					if(!queue.contains(n))
					{
						if(current.data != "*")
						{
							System.out.println("Searching at (" + current.x + "," + current.y +")....");
							agent.moveTo(current, current.x, current.y, "*");
							gui.moveTo(current, world);
							world.displayWorld();
						}
						
						queue.add(n);
					}
				}
			}
		} 
		
		return null;
		
	}//End of A* Search
	
	public static ArrayList<Node> getNeighbors(Node n, Agent a)
	{
		ArrayList<Node> neighbors = new ArrayList<>();
		
		if(n.left != null &&  n.left.data != "-")
		{
			// DEBUGGING -- System.out.println(n.left.x + "," + n.left.y + " ---- " + n.left.calculateF(a));
			neighbors.add(n.left);
		}
		
		if(n.right != null && n.right.data != "-")
		{
			// DEBUGGING -- System.out.println(n.right.x + "," + n.right.y + " ---- " + n.right.calculateF(a));
			neighbors.add(n.right);
		}
		
		
		if(n.up != null && n.up.data != "-")
		{
			// DEBUGGING -- System.out.println(n.up.x + "," + n.up.y + " ---- " + n.up.calculateF(a));
			neighbors.add(n.up);
		}
			
		
		if(n.down != null && n.down.data != "-")
		{
			// DEBUGGING -- System.out.println(n.down.x + "," + n.down.y + " ---- " + n.down.calculateF(a));
			neighbors.add(n.down);
		}
		
		for(Node index : neighbors)
		{
			index.g = n.g+1;
			index.parent = n;
		}
		
		return neighbors;
	} // End of getNeighbors
	
	/**
	 * Used to setup the environment using user-input & creates an agent
	 * @param m A Map Object that serves as the agent's environment
	 * @return agent Returns the agent that was created
	 */
	public static Agent intializeEnvironment(World w)
	{
		int y,x;
		Scanner scan = new Scanner(System.in);
		
		//Prompts user for input for agent's starting spot & validates input
		System.out.print("Enter a X-Coordinate in the range of 0-" + (w.size-1) +  " for the agent's starting spot: ");
		x = scan.nextInt();
		validateInput(x, w);
		System.out.print("Enter a Y-Coordinate in the range of 0-" + (w.size-1) +  " for the agent's starting spot: ");
		y = scan.nextInt();
		validateInput(y, w);
		System.out.println();
		
		//Uses input to create an Agent
		Agent agent = new Agent(x,y);
				
		//Updates map with Agent at the user's desired spot & then displays the updated environment
		agent.establishAgent(w.getWorld(), x, y);
		w.displayWorld();
				
				
		//Prompts user for input for agent's goal & validates input
		System.out.print("Enter a X-Coordinate in the range of 0-" + (w.size-1) +  " for the agent's goal: ");
		x = scan.nextInt();
		validateInput(x, w);
		System.out.print("Enter a Y-Coordinate in the range of 0-" + (w.size-1) +  " for the agent's goal: ");
		y = scan.nextInt();
		validateInput(y, w);	
		
		//Updates map with Agent at the user's desired spot & then displays the updated environment
		agent.setGoal(x, y);
		w.setGoal(x, y);
		w.displayWorld();
		System.out.println("Environment has been fully created.\n");
		
		return agent;
	} //End of initializeEnvironment
	
	/**
	 * Ensures user uses proper input. Program terminates if input is
	 * deemed invalid
	 */
	public static void validateInput(int input, World w)
	{
		if(!(input >= 0) && input <= (w.size-1))
		{
			System.err.println("Invalid input, terminating program.");
			System.exit(0);
		}
	}
}
