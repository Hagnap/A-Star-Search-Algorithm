import java.util.Comparator;

public class NodeComparator implements Comparator<Node>
{
	private Agent a;

	public NodeComparator(Agent a)
	{
		this.a = a;
	}
	
	//Sorts in descending order to have the node with the lowest F value at the top of the P.Q.
	public int compare(Node n1, Node n2)
	{
		if(n1.calculateF(a) > n2.calculateF(a))
			return 1;
		
		else if(n1.calculateF(a) < n2.calculateF(a))
			return -1;
		
		else
			return 0;
	}
}
