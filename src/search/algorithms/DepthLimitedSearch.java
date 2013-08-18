package search.algorithms;

import search.QueueEntry;
import search.State;

/**
 * A depth-limited search, implemented as a modification of the DFS
 * in which states are not enqueued if they are past the depth limit.
 * @author lackofcheese
 */
public class DepthLimitedSearch extends DepthFirstSearch {
	/** The depth limit for the search. */
	private int depthLimit;
	
	/**
	 * Constructs a depth-limited search with the given parameters.
	 * @param depthLimit the depth limit.
	 * @param root the root state of the search.
	 * @param goal the goal state of the search.
	 */
	public DepthLimitedSearch(int depthLimit, State root, State goal) {
		super(root, goal);
		this.depthLimit = depthLimit;
	}
	
	/**
	 * Returns the depth limit for this search.
	 * @return the depth limit for this search.
	 */
	public int getDepthLimit() {
		return depthLimit;
	}

	/**
	 * {@inheritDoc}
	 * As a modification, the state is not enqueued if it is beyond the
	 * depth limit.
	 */
	@Override
	protected void enqueue(QueueEntry<Boolean> qe) {
		if (qe.getDepth() <= depthLimit) {
			queue.push(qe);
		}
	}
}
