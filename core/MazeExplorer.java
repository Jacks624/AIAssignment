package maze.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

import maze.ai.core.BestFirstObject;

public class MazeExplorer implements BestFirstObject<MazeExplorer> {
	private Maze m;
	private MazeCell location;
	private TreeSet<MazeCell> treasureFound; 
	
	public MazeExplorer(Maze m, MazeCell location) {
		this.m = m;
		this.location = location;
		treasureFound = new TreeSet<MazeCell>();
	}
	
	public MazeCell getLocation() {return location;}

	public ArrayList<MazeExplorer> getSuccessors() {
	ArrayList<MazeExplorer> result = new ArrayList<MazeExplorer>();
	// TODO: It should add as a successor every adjacent, unblocked neighbour square
	if(this.m.isTreasure(location)) this.treasureFound.add(this.location);
       ArrayList<MazeCell> neighbours = new ArrayList<MazeCell>();
       neighbours.addAll(m.getNeighbors(location));
       for(MazeCell mc : neighbours ) {
    	   if(!m.blocked(location, mc)) { 
    		  MazeExplorer newExplorer = new MazeExplorer(m, mc);
    		  newExplorer.treasureFound = (TreeSet)this.treasureFound.clone();    		 
    		 result.add(newExplorer);
    	   }   	   
       }
		return result;
	}
	
	public void addTreasures(Collection<MazeCell> treasures) {
		treasureFound.addAll(treasures);
	}
	
	public String toString() {
		StringBuilder treasures = new StringBuilder();
		for (MazeCell t: treasureFound) {
			treasures.append(";");
			treasures.append(t.toString());
		}
		return "@" + location.toString() + treasures.toString();
	}
	
	@Override
	public int hashCode() {return toString().hashCode();}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof MazeExplorer) {
			return achieves((MazeExplorer)other);
		} else {
			return false;
		}
	}

	public boolean achieves(MazeExplorer goal) {
		return this.location.equals(goal.location) && this.treasureFound.equals(goal.treasureFound);
	}

}
