package search.algorithms;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import search.QueueEntry;
import search.State;

/**
 * An implementation of a DFS using the QueueSearch class with a Stack as the queue.
 * 
 * In order to remember the path taken to the current state, expanded states are put
 * back on the stack under their successors, with a boolean flag so that they
 * are known to have been expanded.
 * 
 * This search avoids cycles, but can still search the same state multiple times if
 * it reaches that state via different paths.
 * 
 * @author lackofcheese
 */
public class DepthFirstSearch extends AbstractSearchAlgorithm {
	/**
	 * Constructs a depth first search with the given parameters.
	 * @param root the root state of the search.
	 * @param goal the goal state of the search.
	 */
	public DepthFirstSearch(State root, State goal) {
		super(root, goal);
	}
	
	/** A stack to store the path taken to reach the current state. */
	protected Stack<State> pathStack; 
	/** The set of nodes in the current path. */
	protected Set<State> pathSet;
	/** The stack of states to be processed. */
	protected Stack<QueueEntry<Boolean>> queue;
	
	/** The queue entry currently being processed. */
	protected QueueEntry<Boolean> currentEntry;
	/** True if a goal has been found, and false otherwise. */
	protected boolean goalFound = false;
	
	
	/**
	 * A basic implementation of a DFS.
	 * The search loop processes states from the stack until either
	 * the goal is found or the queue is empty.
	 */
	@Override
	public void search() {
		queue = new Stack<QueueEntry<Boolean>>();
		pathStack = new Stack<State>();
		pathSet = new HashSet<State>();
		goalFound = false;
		this.enqueue(new QueueEntry<Boolean>(getRoot(), null, 0, 0.0, 0.0, false));
		while(!queue.isEmpty()) {
			currentEntry = queue.pop();
			if (goalFound = processCurrentEntry()) {
				return;
			}
		}
	}
	
	/**
	 * Adds an entry to the stack for searching.
	 * @param qe the entry to add to the stack.
	 */
	protected void enqueue(QueueEntry<Boolean> qe) {
		queue.push(qe);
	}

	/**
	 * Processes the current entry, and returns true if it is the goal state.
	 * @return true if the current entry is the goal state, and false otherwise.
	 */
	public boolean processCurrentEntry() {
		/* If the current entry was already expanded, we are backtracking;
		 * this means it cannot be the goal, and is no longer on the path. */
		if (currentEntry.getData()) {
			State state = pathStack.pop();
			pathSet.remove(state);
			return false;
		}
		
		State currentState = currentEntry.getState();
		// If this is the goal state, we add it to the path and conclude.
		if (currentState.equals(getGoal())) {
			pathStack.push(currentState);
			pathSet.add(currentState);
			return true;
		}
		
		// It's not the goal state, so we expand it.
		int currentDepth = currentEntry.getDepth();
		double currentCost = currentEntry.getTotalCost();
		// To remember the path, we re-queue the state under its successors, and add it to the path.
		this.enqueue(new QueueEntry<Boolean>(currentState, currentEntry.getPred(), currentDepth,
				currentCost, 0.0, true));
		pathStack.push(currentState);
		pathSet.add(currentState);
		
		// Retrieve and process the successors.
		List<State> successors = currentState.getSuccessors();
		for (State s2 : successors) {
			if (pathSet.contains(s2)) {
				continue;
			}
			// Enqueue the successors.
			this.enqueue(new QueueEntry<Boolean>(s2,	currentState, currentDepth + 1, 
					currentCost + currentState.getCost(s2),	0.0, false));
		}
		return false;
	}

	@Override
	public boolean goalFound() {
		return goalFound;
	}
	@Override
	public int getGoalDepth() {
		return currentEntry.getDepth();
	}
	@Override
	public double getGoalCost() {
		return currentEntry.getTotalCost();
	}
	@Override
	public List<State> getGoalPath() {
		return new ArrayList<State>(pathStack);
	}
}
