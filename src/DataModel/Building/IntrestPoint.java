package DataModel.Building;

import db.IEntity;

public class IntrestPoint implements IEntity<Integer>
{
	private Integer id;
	
	boolean isUp;
	Cell currCell;
	Cell destCell;
	
	public Integer getId() {
        return id;
    }

	public void setId(Integer id) {
        this.id = id;
    }
	
	public IntrestPoint()
	{
		isUp=false;
		currCell = new Cell(0,0,0);
		destCell = new Cell(0,0,0);
	}
	public IntrestPoint(Cell currCell)
	{
		isUp=false;
		this.currCell = currCell;
		this.destCell = currCell;
	}
	
	public IntrestPoint(Cell currCell,Cell destCell)
	{
		isUp=true;
		this.currCell = currCell;
		this.destCell = destCell;
	}
	
	public IntrestPoint(Cell currCell,Cell destCell, boolean up)
	{
		isUp=up;
		this.currCell = currCell;
		this.destCell = destCell;
	}
	
	public boolean isUpOrDown()
	{
		return isUp;
	}
	
	public Cell GetCurrCell()
	{
		return this.currCell;
	}
	
	public Cell GetDestCell()
	{
		return this.destCell;
	}


}