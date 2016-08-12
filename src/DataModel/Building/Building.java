package DataModel.Building;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.imageio.ImageIO;

import db.IEntity;



public class Building implements IEntity<Integer> {
	private Integer id;
	String buildingName;
	
	int[][][] floors;
	Hashtable<String, List <IntrestPoint>> intrestPoints; 
	BufferedImage img = null;
	HashMap<Integer,Floor> floorsSize;
	
	
	public Building (int buildId, int floors)
	{
		this.id = buildId;
		this.floors = new int[floors][][];
		this.intrestPoints = new Hashtable<>();
		this.floorsSize =  new HashMap<Integer, Floor>();
		
		// Add new map to DB with unique id
	}
	
	public Building ()
	{
		buildingName = "";
		this.intrestPoints = new Hashtable<>();
		this.floorsSize =  new HashMap<Integer, Floor>();
	}
	
	public Integer getId() {
        return id;
    }

	public void setId(Integer id) {
        this.id = id;
    }
	
	public String getBuildingName() {
        return buildingName;
    }

	public void setBuildingName(String name) {
        this.buildingName = name;
    }
	
	public void initFloorGrid(int floors) {
		this.floors = new int[floors][][];
    }
	
	public int[][][] GetFloors()
	{
		return floors;
	}
	
	public void InitDebugMap()
	{
	/*	map[0][0][0] = 0;
		map[0][0][1] = 1;
		map[0][0][2] = 0;
		map[0][1][0] = 0;
		map[0][1][1] = 0;
		map[0][1][2] = 0;
		map[0][2][0] = 0;
		map[0][2][1] = 0;
		map[0][2][2] = 0;
		map[1][0][0] = 0;
		map[1][0][1] = 0;
		map[1][0][2] = 0;
		map[1][1][0] = 1;
		map[1][1][1] = 1;
		map[1][1][2] = 0;
		map[1][2][0] = 0;
		map[1][2][1] = 0;
		map[1][2][2] = 0;
		/*map[2][0][0] = 0;
		map[2][0][1] = 0;
		map[2][0][2] = 0;
		map[2][1][0] = 0;
		map[2][1][1] = 0;
		map[2][1][2] = 0;
		map[2][2][0] = 0;
		map[2][2][1] = 0;
		map[2][2][2] = 0;
		
		Vector up = new Vector<IntrestPoint>();
		up.add(new IntrestPoint(new Cell(0,0,2),new Cell(1,0,2)));
		//up.add(new IntrestPoint(new Cell(1,2,0),new Cell(2,0,0)));
		intrestPoints.put("up",up);
		*/
		
	}
	
	public Hashtable<String, List<IntrestPoint>> GetIntrestPoints()
	{
		return intrestPoints;
	}
	
	public void setIntrestPoint(List <IntrestPoint> points)
	{
		this.intrestPoints.put("up",new ArrayList<IntrestPoint>());
		this.intrestPoints.put("down",new ArrayList<IntrestPoint>());

		for (int p = 0; p < points.size(); p++) {
			if (points.get(p).isUpOrDown() == true)
			{
				this.intrestPoints.get("up").add(points.get(p));
			}
			else
			{
				this.intrestPoints.get("down").add(points.get(p));
			}
		}	
	
	}
	
    public Boolean addFloorMap(String mapPath, int floor)
	{ 	
    	try
    	{
    		URL url = new URL(mapPath);
    		img = ImageIO.read(url); // eventually C:\\ImageTest\\pic2.jpg
    	    
    	    int[][] matrix = createImageGrid(img);
    	    
    	    floors[floor - 1] = matrix;
    	    
    	    // add new floor sizes
    	    floorsSize.put(floor - 1, new Floor(img.getWidth(), img.getHeight()));
    	    
    	    // Save map to DB
    	    // Save image to DB
    	    
    	    return true;
    	}
		catch (IOException e) 
		{
		    e.printStackTrace();
		    return false;
		}
	}
    
    public Boolean removeFloorMap (int floor)
    {
    	try
    	{
    		this.floors[floor - 1] = null;
    		this.floorsSize.remove(floor);
    		
    		// delete map floor from DB
    		
    		return true;
    	}
    	catch (Exception e)
    	{
    		return false;
    	}
    }
    
    public void printFloorMap(int floor)
    {
		for (int i = 0; i < floors[floor - 1].length; i++) {
		    for (int j = 0; j < floors[floor - 1][0].length; j++) {
		        System.out.print(floors[floor - 1][i][j] + " ");
		    }
		    System.out.print("\n");
		}
    }
    
    public void writeFileFloorMap(int floor)
    {
    	try
    	{
    	PrintWriter w = new PrintWriter("r.txt");//("r.txt");
		for (int i = 0; i < floors[floor -1].length; i++) {
		    for (int j = 0; j < floors[floor - 1][0].length; j++) {
		        w.print(floors[floor - 1][i][j] + " ");
		    }
		    w.println();
		}
		w.close();
		}
    	catch(Exception e)
    	{}
    	}
    
    public void printArea(int floor,int y, int z)
    {
		for (int i = y-1; i <= y+1; i++) {
		    for (int j = z-1; j <= z+1 ; j++) {
		    	if (i>0 && j>0)
		        System.out.print(floors[floor - 1][i][j] + " ");
		    }
		    System.out.print("\n");
		}
    }
    
	public int [][] GetFloor(int floor)
	{
		return floors[floor - 1];
	}
    
    public int GetFloorHeight(int floor){
    	return floorsSize.get(floor - 1).Height();
    }
    
    public int GetFloorWidth(int floor){
    	return floorsSize.get(floor - 1).Width();
    }
    
    public int GetFloorNumber()
    {
    	return floorsSize.size();
    }
       
    /*private int[][] createImageGrid(BufferedImage img) {

	    int [][] rtnMatrix = new int [img.getWidth()][img.getHeight()];
		
		for (int width = 0; width < img.getWidth(); width++){
			 
			for (int hight = 0; hight < img.getHeight(); hight++) {
					
			Color col = new Color(img.getRGB(width,hight),true);
				
				if (col.getRGB() ==  Color.black.getRGB()){
					rtnMatrix[width][hight] = 1;
				}
				else {
					rtnMatrix[width][hight] = 0;
				}
			}
		}
		
		return rtnMatrix;
    }*/
    
    private int[][] createImageGrid(BufferedImage img) {

	    int [][] rtnMatrix = new int [img.getHeight()][img.getWidth()];
		
		for (int height = 0; height < img.getHeight(); height++){
			 
			for (int width = 0; width < img.getWidth(); width++) {
					
			Color col = new Color(img.getRGB(width,height),true);
				
				if (col.getRGB() ==  Color.black.getRGB()){
					rtnMatrix[height][width] = 1;
				}
				else if (col.getRGB() ==  Color.white.getRGB()){
					rtnMatrix[height][width] = 1;
				}
				else {
					rtnMatrix[height][width] = 0;
				}
			}
		}
		
		return rtnMatrix;
    }
}