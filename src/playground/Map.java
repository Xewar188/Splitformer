package playground;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import cells.CellBase;
import cells.Goal;
import cells.SpawnPoint;

public class Map {

	public static final int COLUMNS=40,ROWS=20;
	public CellBase[][] bluePrint=new CellBase[COLUMNS][ROWS];

	public Point startLocation;
	public Point endLocation;
	
	public Map()
	{
		
		try {
			load("main0");
			save("main0");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setCell(Point a,char id)
	{
		setCell(a.x,a.y,id);
	}
	public void setCell(int x, int y, char id)
	{
		bluePrint[x][y]=CellBase.getByID(id);
		if(bluePrint[x][y] instanceof Goal&&( x!=endLocation.x|| y!=endLocation.y))
		{
			setCell(endLocation,'0');
			endLocation=new Point(x,y);
		}
		else if(bluePrint[x][y] instanceof SpawnPoint&&( x!=startLocation.x|| y!=startLocation.y))
		{
			setCell(startLocation,'0');
			startLocation=new Point(x,y);
		}
	}
	public void load(String string) throws IOException {
		File targ= new File("maps\\"+string+".txt");
		@SuppressWarnings("resource")
		BufferedReader reader= new BufferedReader(new FileReader(targ));
		for(int j=0; j<ROWS;j++)
			for(int i=0;i<COLUMNS;i++)
			{
					bluePrint[i][j]=CellBase.getByID((char)reader.read());
					if(bluePrint[i][j] instanceof Goal)
					{
						endLocation=new Point(i,j);
					}
					else if(bluePrint[i][j] instanceof SpawnPoint)
					{
						startLocation=new Point(i,j);
					}
			}
		
		
	}
	public void save(String string) throws IOException
	{
		File targ= new File("maps\\"+string+".txt");
		BufferedWriter writer= new BufferedWriter(new FileWriter(targ));
		
		for(int j=0; j<ROWS;j++)
		{
			for(int i=0;i<COLUMNS;i++)
			{
			writer.append(bluePrint[i][j].id);
			}
		}
		writer.close();
	}
}
