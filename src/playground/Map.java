package playground;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import cells.CellBase;

public class Map {

	public static final int COLUMNS=40,ROWS=20;
	public CellBase[][] bluePrint=new CellBase[COLUMNS][ROWS];
	public Map()
	{
		
		try {
			load("main0");
			save("main0");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	void load(String string) throws IOException {
		File targ= new File("maps\\"+string+".txt");
		@SuppressWarnings("resource")
		BufferedReader reader= new BufferedReader(new FileReader(targ));
		for(int j=0; j<ROWS;j++)
			for(int i=0;i<COLUMNS;i++)
			{
				
				bluePrint[i][j]=CellBase.getByID((char)reader.read());
			}
		
		
	}
	void save(String string) throws IOException
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
