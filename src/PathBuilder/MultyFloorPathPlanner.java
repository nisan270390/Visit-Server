package PathBuilder;

import java.util.List;
import java.util.Vector;

import DataModel.Building.Building;
import DataModel.Building.Cell;
import DataModel.Building.IntrestPoint;


public class MultyFloorPathPlanner {
	Building map;
	PathPlanner pp;
	
	public MultyFloorPathPlanner(Building map)
	{
		this.map = map;
		this.pp = new PathPlanner(map);
	}
	
	public Vector<Cell>  FindPath( Cell startPoint,  Cell endPoint)
	{
		Vector<Cell> allPath = new Vector<Cell>();
		Cell currentPoint = startPoint;
		while (currentPoint.GetX() != endPoint.GetX())
		{
			System.out.println("current floor " + currentPoint.GetX() + "googing to floor " + endPoint.GetX());
			if (currentPoint.GetX() < endPoint.GetX())
			{
				List<IntrestPoint> up = map.GetIntrestPoints().get("up");
				IntrestPoint closest = null;
				int minSteps=Integer.MAX_VALUE;
				Vector<Cell> shortestPath = new Vector<>();
				if (up != null)
				{
					for (IntrestPoint ip:up)
					{
						if (ip.GetCurrCell().GetX() == currentPoint.GetX())
						{
							Vector<Cell> temp = pp.FindPath(currentPoint, ip.GetCurrCell());
							if (temp.size() < minSteps)
							{
								minSteps=temp.size();
								shortestPath = temp;
								closest = ip;
							}
						}
					}
				}
				if (closest != null)
				{
					allPath.addAll(shortestPath);
					currentPoint = closest.GetDestCell();	
				}
				else {
					System.out.println("no path from floor " + currentPoint.GetX() + "to floor " + endPoint.GetX());
					return new Vector<Cell>();
				}
			}
			else if (currentPoint.GetX() > endPoint.GetX())
			{
				List<IntrestPoint> down = map.GetIntrestPoints().get("down");
				IntrestPoint closest = null;
				int minSteps=Integer.MAX_VALUE;
				Vector<Cell> shortestPath = new Vector<>();
				if (down!=null)
				{
					for (IntrestPoint ip:down)
					{
						if (ip.GetCurrCell().GetX() == currentPoint.GetX())
						{
							Vector<Cell> temp = pp.FindPath(currentPoint, ip.GetCurrCell());
							if (temp.size() < minSteps)
							{
								minSteps=temp.size();
								shortestPath = temp;
								closest = ip;
							}
						}
					}
				}
				if (closest != null)
				{
					allPath.addAll(shortestPath);
					currentPoint = closest.GetDestCell();	
				}
				else {
					System.out.println("no path from floor " + currentPoint.GetX() + "to floor " + endPoint.GetX());
					return new Vector<Cell>();
				}
		
			}
			
		}
		if (currentPoint != endPoint)
		{
			Vector<Cell> temp = pp.FindPath(currentPoint, endPoint);
			allPath.addAll(temp);
		}
		return allPath;
	}

}
