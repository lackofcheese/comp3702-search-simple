package search;


/**
 * A class representing a single entry in a search queue; this includes
 * all information likely to be relevant to the search.
 * 
 * Also, the default comparator for this class compares based on the total
 * value of the cost so far and the heuristic - this means that
 * a PriorityQueue-based implementation of A* will function naturally.
 * @author lackofcheese
 */
public class QueueEntry<Data> implements Comparable<QueueEntry<Data>> {
	/**
	 * The state itself.
	 */
	private State state;
	/**
	 * The predecessor of that state in the search tree.
	 */
	private State pred;
	/**
	 * The depth in the search tree.
	 */
	private int depth;
	/**
	 * The total cost so far to reach the state.
	 */
	private double totalCost;
	/**
	 * A heuristic estimate of the remaining cost to the goal; this will
	 * be zero if no heuristic is being used.
	 */
	private double heuristicEstimate;
	
	/**
	 * If applicable, this holds any additional data needed for specific
	 * search algorithms to function more conveniently.
	 */
	private Data data;
	
	/**
	 * Constructs a queue entry with the given parameters.
	 * @param s the state.
	 * @param pred the state's predecessor in the search tree.
	 * @param depth the depth in the search tree. 
	 * @param totalCost the total cost so far to reach the state.
	 * @param heuristicEstimate an estimate of the cost to the goal;
	 * this should be zero if no heuristic is used.
	 * @param data if applicable, any additional data required.
	 */
	public QueueEntry(State s, State pred, int depth, double totalCost, double heuristicEstimate, Data data) {
		this.state = s;
		this.pred = pred;
		this.depth = depth;
		this.totalCost = totalCost;
		this.heuristicEstimate = heuristicEstimate;
		this.data = data;
	}

	/**
	 * Returns the state this queue entry is for.
	 * @return the state this queue entry is for.
	 */
	public State getState() {
		return state;
	}
	
	/**
	 * Returns the predecessor of this state.
	 * @return the predecessor of this state.
	 */
	public State getPred() {
		return pred;
	}
	
	/**
	 * Returns the depth in the search tree.
	 * @return the depth in the search tree.
	 */
	public int getDepth() {
		return depth;
	}
	
	/**
	 * Returns the total cost so far.
	 * @return the total cost so far.
	 */
	public double getTotalCost() {
		return totalCost;
	}
	
	/**
	 * Returns a heuristic estimate of the remaining cost (zero if N/A).
	 * @return a heuristic estimate of the remaining cost (zero if N/A).
	 */
	public double getHeuristicEstimate() {
		return heuristicEstimate;
	}
	
	/**
	 * Returns any additional data stored.
	 * @return any additional data stored.
	 */
	public Data getData() {
		return data;
	}

	/**
	 * Implements a comparison between queue entries, based on the
	 * sum of the total cost so far and the heuristic estimate.
	 * This makes A* and UCS function naturally with a PriorityQueue.
	 */
	@Override
	public int compareTo(QueueEntry<Data> arg0) {
		return Double.compare(this.totalCost + this.heuristicEstimate, arg0.totalCost + arg0.heuristicEstimate);
	}
}
