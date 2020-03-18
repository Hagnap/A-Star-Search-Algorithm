import java.awt.*;
import javax.swing.*;

public class GUI_Controller 
{
	public static JButton buttons[][];
	public static MyButton myButtons[][];
	public static int clickerCounter = 0;
	public static World world;
	public static Agent agent;
	public int startX, startY, goalX, goalY;
	
	public static JFrame frame;
	
	public GUI_Controller(int startX, int startY, int goalX, int goalY, World w, Agent a)
	{
		buttons = new JButton[w.getSize()][w.getSize()];
		myButtons = new MyButton[w.getSize()][w.getSize()];
		
		//Sets up attributes with the values passed in through the args
		this.startX = startX;
		this.startY = startY;
		this.goalX = goalX;
		this.goalY = goalY;
		world = w;
		agent = a;
		
		int counter = 0;
		
		//Creates a Frame
		frame = new JFrame("A* Pathfinding A.I.");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Creates a panel with a box 
		JPanel panel = new JPanel(); 
		panel.setLayout(new GridLayout(w.getSize(), w.getSize()));
		panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
		panel.setBackground(Color.black);
		
		//Sets up the Frame to make open spaces white and blocked ones black
		for(int y = 0; y < w.getSize(); y++)
		{
			for(int x = 0; x < w.getSize(); x++)
			{
				myButtons[x][y] = new MyButton(x,y,counter);

				buttons[x][y] = new JButton();
				panel.add(buttons[x][y]);
				
				if(world.world[x][y].data == "O")
					buttons[x][y].setBackground(Color.white);
				
				if(world.world[x][y].data == "-")
					buttons[x][y].setBackground(Color.black);
				
				if(world.world[x][y].data == "X")
					buttons[x][y].setBackground(Color.blue);
				
				if(world.world[x][y].data == "G")
					buttons[x][y].setBackground(Color.red);
				
				counter++;
			}
		}
		
		//Adds buttons to the frame and makes it visible
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(false);
	}
	
	/**
	 * Creates a World for the agent in two steps.
	 * First it creates a World Obj.
	 * Second, it generates random inaccessible nodes
	 * @return A World Object with some nodes set to inaccessible
	 */
	public static World createWorld()
	{
		World w = new World();
		w.generateInaccessibleNodes();
		
		return w;
	}
	
	/**
	 * Used to move the agent during the path search. 
	 * Sets nodes that the agent visits to green.
	 * 
	 * @param n The node the agent is currently at.
	 * @param w The world that the agent is searching in.
	 */

	public void moveTo(Node n, World w)
	{
		if(buttons[n.x][n.y].getBackground() != Color.BLUE && buttons[n.x][n.y].getBackground() != Color.RED)
		buttons[n.x][n.y].setBackground(Color.green);
		frame.repaint();
	}
	
	/**
	 * Used to start the path search. 
	 * The main difference between this & moveTo is that moveToPath
	 * sets the node to Cyan to indicate the start node, moveTo sets 
	 * nodes to the color green.
	 * 
	 * @param n The node the agent is currently at.
	 * @param w The world that the agent is searching in.
	 */
	public void moveToPath(Node n, World w)
	{
		if(buttons[n.x][n.y].getBackground() != Color.BLUE && buttons[n.x][n.y].getBackground() != Color.RED)
		buttons[n.x][n.y].setBackground(Color.cyan);
		frame.repaint();
	}

	@SuppressWarnings("serial")
	/**
	 * Used to represent nodes on the GUI. 
	 * Is made of a x,y, and blockNumber and the JButton class
	 * @author Jake
	 *
	 */
	public static class MyButton extends JButton
	{
		public static int blockNumber; //Helps track the buttons 
		public int x, y;
		
		public MyButton(int x, int y, int i)
		{
			super();
			this.x = x;
			this.y = y;
			blockNumber = i;
		}
		
		public int getBlockNumber()
		{
			return blockNumber;
		}	
		
	} //End MyButton
	
} //End Environment