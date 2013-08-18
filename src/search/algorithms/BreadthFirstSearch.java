package search.algorithms;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import search.QueueEntry;
import search.State;

/**
 * An implementation of a BFS that does not revisit states, 
 * using a LinkedList as a queue for holding the states to visit.
 * @author lackofcheese
 */
public class BreadthFirstSearch extends AbstractSearchAlgorithm {
	/**
	 * Constructs a BFS with the given parameters.
	 * @param root the root state of the search.
	 * @param goal the goal state of the search.
	 */
	public BreadthFirstSearch(State root, State goal) {
		super(root, goal);
	}

	/** 
	 * A representation of the search tree; remembers which states 
	 * were expanded, and the predecessor for each.
	 */
	protected Map<State, State> predMap;
	/** A queue holding the states to be searched. */
	Queue<QueueEntry<Object>> queue;
	/** The queue entry currently being processed. */
	protected QueueEntry<Object> currentEntry;
	/** True if a goal has been found, and false otherwise. */
	protected boolean goalFound = false;
	
	/**
	 * A basic implementation of a queue-based BFS.
	 * The core is a loop that searches the foremost entry in the queue
	 * until either a goal is found, or the queue is empty.
	 */
	public void search() {
		predMap = new HashMap<State, State>();
		queue = new LinkedList<QueueEntry<Object>>();
		queue.add(new QueueEntry<Object>(getRoot(), null, 0, 0.0, getHeuristic().estimate(getRoot()), null));
		goalFound = false;
		while(!queue.isEmpty()) {
			currentEntry = queue.remove();
			if (goalFound = processCurrentEntry()) {
				return;
			}
		}
	}
	
	/**
	 * Processes the current entry from the search queue, and returns
	 * true if it was the goal, and false otherwise.
	 * @return whether the current state was the goal state.
	 */
	public boolean processCurrentEntry() {
		State currentState = currentEntry.getState();
		if (predMap.containsKey(currentState)) {
			return false;
		}
		// Now that we've expanded the current state, we record its predecessor.
		predMap.put(currentState, currentEntry.getPred());
		
		// If it's the goal, we're done.
		if (currentState.equals(getGoal())) {
			return true;
		}
		
		// Retrieve and process the successors.
		List<State> successors = currentState.getSuccessors();
		for (State s2 : successors) {
			if (!predMap.containsKey(s2)) {
				queue.add(new QueueEntry<Object>(
						s2,
						currentState,
						currentEntry.getDepth() + 1, 
						currentEntry.getTotalCost() + currentState.getCost(s2),
						getHeuristic().estimate(s2),
						null)
				);
			}
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
		List<State> path = new ArrayList<State>();
		State s = currentEntry.getState();
		path.add(s);
		while ((s = predMap.get(s)) != null) {
			path.add(s);
		}
		Collections.reverse(path);
		return path;
	}
}
