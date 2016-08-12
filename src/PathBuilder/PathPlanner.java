package PathBuilder;

import java.util.Vector;

import DataModel.Building.Building;
import DataModel.Building.Cell;


public class PathPlanner {
	Building building;
	int horizontalCost = 10;
	int verticalCost =10;
	int diagnalCost = 14;
	
	Vector<Node> openNodes;
	Vector<Node> ClosedNodes;
	Node[][] cameFrom;

	Cell startPoint;
	Cell endPoint;
	
	

	public PathPlanner(Building map)
	{
		this.building = map;
		openNodes = new Vector<Node>();
		ClosedNodes = new Vector<Node>();
	}
	
	//Run a* algorithm to find path
	public Vector<Cell>  FindPath( Cell startPoint,  Cell endPoint)
	{
		//or ---- why sometimes 1 and sometimes 0 >????????????????????????
		int mapwidth = building.GetFloorWidth(startPoint.GetX());
		int maphight = building.GetFloorHeight(startPoint.GetX());
		int floor = startPoint.GetX();
		System.out.println("findPath");
		ClosedNodes.clear();
		openNodes.clear();
	
		//grid of nodes
		cameFrom = new Node[mapwidth][maphight];
		
	
		//initialization
		for (int i=0;i<mapwidth;i++)
		{
			for (int j=0;j<maphight;j++)
			{
				cameFrom[i][j] = new Node(new Cell(-1,-1,-1),-1,9999999);
			}
		}
		//cameFrom[startPoint.GetY()][startPoint.GetZ()] = new Node(startPoint,0,CalcDistToEnd(startPoint,endPoint));
		Node temp = new Node(startPoint,0,10*CalcDistToEnd(startPoint,endPoint));
		openNodes.add(temp);
		while (!openNodes.isEmpty())
		{
			Node minOpenNode = new Node(startPoint,0,Integer.MAX_VALUE);
			int index =0;
			for (int i =0; i< openNodes.size();i++)
			{
				if (openNodes.get(i).GetPriority() < minOpenNode.GetPriority())
				{
					minOpenNode = openNodes.get(i);
					index = i;
				}
			}
			//closedsetMap[minOpenNode] =true;
	
			if (minOpenNode.GetCell().GetY() == endPoint.GetY() && minOpenNode.GetCell().GetZ() == endPoint.GetZ())
			{
				return this.ReconstractPath(endPoint,startPoint);
			}
	
			openNodes.remove(index);
			Node target = new Node(minOpenNode.GetCell(),minOpenNode.GetLevel(),minOpenNode.GetPriority());
			ClosedNodes.add(target);
	
			//get cell valid neigbors
			Vector<Node> neigbours = GetNeighbours(minOpenNode,endPoint,mapwidth,maphight,floor);
	
			boolean isInClosed = false;
			boolean isInOpen = false;
	
			//run on al neigbors
			for (int i=0; i< neigbours.size();i++)
			{
				//run on closed
				for (int j=0; j<ClosedNodes.size();j++)
				{
					//if cell in closed  inform and skip
					if ((neigbours.get(i).GetCell().GetY() == ClosedNodes.get(j).GetCell().GetY()) &&
						(neigbours.get(i).GetCell().GetZ() == ClosedNodes.get(j).GetCell().GetZ()))
						{
							isInClosed = true;
							break;
						}
				}
				//if not in closed list
				if (!isInClosed)
				{
	
							if(cameFrom[neigbours.get(i).GetCell().GetY()][neigbours.get(i).GetCell().GetZ()].GetLevel() !=-1)
							{
								isInOpen = true;
								if(cameFrom[neigbours.get(i).GetCell().GetY()][neigbours.get(i).GetCell().GetZ()].GetPriority() > neigbours.get(i).GetPriority())
								{
									cameFrom[neigbours.get(i).GetCell().GetY()][neigbours.get(i).GetCell().GetZ()] = minOpenNode;
								}
							}
	
	
					//if not in open
					if (!isInOpen)
					{
						// add to open and to camefrom
						Node target1 = new Node(neigbours.get(i).GetCell(),neigbours.get(i).GetLevel(),neigbours.get(i).GetPriority());
						openNodes.add(target1);
						cameFrom[neigbours.get(i).GetCell().GetY()][neigbours.get(i).GetCell().GetZ()] = minOpenNode;
					}
				}
	
				isInClosed = false;
				isInOpen = false;
			}
		}
		Vector<Cell> err = new Vector<>();
		err.clear();
	
		return err;
	}
	
	
	//Build path according to a* results
	public Vector<Cell> ReconstractPath(Cell endPoint,Cell startPoint )
	{
		System.out.println("reconstractPath");
		Cell currCell = new Cell(endPoint.GetX(),endPoint.GetY(),endPoint.GetZ());
			Vector <Cell> fullPath = new Vector<>();
			//enter end cell
			fullPath.add(currCell);
	
			//run on all cells
			while ((currCell.GetY() != startPoint.GetY()) ||  (currCell.GetZ() != startPoint.GetZ()))
			{
				//add cell
				Cell temp = cameFrom[currCell.GetY()][currCell.GetZ()].GetCell();
				currCell = new Cell(temp.GetX(), temp.GetY(),temp.GetZ());
				fullPath.add(currCell);
			}
			Object[] tempArr =  fullPath.toArray();
			Vector<Cell> crossFullPath = new Vector<>();
			for (int i=tempArr.length-1;i>=0;i--)
			{
				crossFullPath.add((Cell)tempArr[i]);
			}
			
			
		return crossFullPath;
	}
	
	
	public int CalcDistToEnd(Cell curr,Cell endPoint)
	{
		//System.out.println("Calc dist");
		 return Math.abs(curr.GetY() - endPoint.GetY()) + Math.abs(curr.GetZ() - endPoint.GetZ());
	}
	
	//find up to 8 neighbors for each cell
	public Vector<Node> GetNeighbours(Node currNode,Cell endPoint,int mapwidth, int maphight,int floor)
	{
		//System.out.println("Get neigbours");
		Vector<Node> neigbours = new Vector<>();
		for (int i=-1; i<2;i++)
		{
			for(int j=-1;j<2;j++)
			{
				int y = currNode.GetCell().GetY() +i;
				int z = currNode.GetCell().GetZ()+j;
	
				//if neighbors in grid
				if (y >= 0 && y < mapwidth && z>=0 && z <maphight)
				{
					//if not current point
					if (building.GetFloor(floor)[z][y] == 0 && (i!=0 || j!=0))
					{
							int neighborLevel;
							int neighborPriority;
							int step = CalcDistToEnd(currNode.GetCell(),new Cell(floor,y,z));
							//calc level according to way to it.
							if (step%2 == 0)
							{
								neighborLevel = currNode.GetLevel() + diagnalCost;
							}
							else
							{
								neighborLevel = currNode.GetLevel() + horizontalCost;
							}
							//priority according to minimal distance
							neighborPriority = neighborLevel + horizontalCost*CalcDistToEnd(new Cell(floor,y,z),endPoint);
							Node neighbor = new Node(new Cell(floor,y,z),neighborLevel,neighborPriority);
							neigbours.add(neighbor);
	
					}
				}
			 }
		}
		return neigbours;
	}
}
