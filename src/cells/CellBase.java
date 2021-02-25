package cells;

import java.awt.Color;
import java.util.function.Function;

public abstract class CellBase {

	public boolean tangible=false;
	public Color main=Color.black;
	public char id='-';
	public char typeId='-';
	public boolean splitable = true;
	public CellBase()
	{
		
	}
	static Connector[] compedium= {new Connector('0',a->new Air()),new Connector('1',a->new Stone()),new Connector('2',a->new Goal()),new Connector('3',a->new SpawnPoint())};
	public static char[] idCompedium= {'0','1','2','3'};
	
	public static CellBase getByID(char read) {
		
		
		for(Connector c:compedium)
		{
			if(c.id==read)
				return c.create.apply(0);
		}
		return new Air();
		
	}
	
}
class Connector
{
	char id;
	Function<Integer,CellBase> create;
	public Connector(char c,Function<Integer,CellBase> cr)
	{
		id=c;
		create=cr;
	}
}