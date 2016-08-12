package DataModel.Building;

public class Floor {
	int hight;
	int width;
	
	public Floor(int width,int hight)
	{
		this.hight =hight;
		this.width =width;
	}
	
	public int Height()
	{
		return this.hight;
	}
	
	public int Width()
	{
		return this.width;
	}
}