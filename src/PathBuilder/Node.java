package PathBuilder;

import DataModel.Building.Cell;

public class Node {
	Cell cell;
	int level;
	int priority;
	
	//initialize defaults
	public Node()
	{
		this.cell = new Cell(-1,-1,-1);
		this.level = -1;
		this.priority = 99999;
	}
	
	//initialize by parameters
	public Node(Cell cell,int level,int priority)
	{
		this.cell = cell;
		this.level = level;
		this.priority=priority;
	}
	public Cell GetCell()
	{
		return this.cell;
	}
	
	public int GetLevel()
	{
		return this.level;
	}
	
	public int GetPriority()
	{
		return this.priority;
	}
	
	public void setLevel(int level)
	{
		this.level = level;
	}
	
	public void setPriority(int priority)
	{
		this.priority=priority;
	}


}
