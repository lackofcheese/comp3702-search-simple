package search.algorithms;

import java.util.ArrayList;
import java.util.List;

import search.State;

/**
 * An Iterative Deepening Search, implemented by successive depth-limited
 * searches with increasing depth.
 * @author lackofcheese
 */
public class IterativeDeepeningSearch extends AbstractSearchAlgorithm {
	/** 
	 * Constructs an IDS with the given parameters.
	 * @param root the root state of the search.
	 * @param goal the goal state of the search.
	 */
	public IterativeDeepeningSearch(State root, State goal) {
		super(root, goal);
	}

	/** True if the goal was found, and false otherwise. */
	private boolean goalFound;
	/** The depth of the goal state (if found). */
	private int goalDepth;
	/** The final cost to reach the goal state (if found). */
	private double goalCost;
	/** The path taken to reach the goal state (if found). */
	private List<State> goalPath;
	
	@Override
	public void search() {
		this.goalFound = false;
		
		for (int maxDepth = 0; ; maxDepth++) {
			System.out.println("Depth: " + maxDepth);
			DepthLimitedSearch dls = new DepthLimitedSearch(maxDepth, getRoot(), getGoal());
			dls.search();
			if (dls.goalFound()) {
				this.goalFound = true;
				this.goalCost = dls.getGoalCost();
				this.goalDepth = dls.getGoalDepth();
				this.goalPath = dls.getGoalPath();
				return;
			}
		}
	}

	@Override
	public List<State> getGoalPath() {
		return new ArrayList<State>(this.goalPath);
	}

	@Override
	public boolean goalFound() {
		return goalFound;
	}

	@Override
	public int getGoalDepth() {
		return goalDepth;
	}

	@Override
	public double getGoalCost() {
		return goalCost;
	}
}
