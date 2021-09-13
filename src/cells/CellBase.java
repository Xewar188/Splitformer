package cells;

import java.awt.Color;
import java.util.Vector;
import java.util.function.Function;

public abstract class CellBase {

	protected boolean tangible = false;
	protected Color main = Color.black;
	protected char id = '-';
	protected boolean splitable = true;
	private static final Connector[] compedium =
		{
				new Connector('0',a->new Air()),
				new Connector('1',a->new Stone()),
				new Connector('2',a->new Goal()),
				new Connector('3',a->new SpawnPoint()),
				new Connector('4',a->new NotSplitableRock())
				};
	
	private static final Vector<Character> idCompedium= new Vector<>();
	
	private static class Connector
	{
		char id;
		Function<Integer,CellBase> create;
		public Connector(char c,Function<Integer,CellBase> cr)
		{
			id = c;
			create = cr;
		}
	}
	
	public CellBase() {}
	
	public static void fillCompedium() {
		for(Connector c : compedium)
			idCompedium.add(c.id);
	}
	
	public static char getIdFromCompedium(int pos) {
		return (idCompedium.get(pos));
	}
	
	public static int getCompediumLength() {
		return idCompedium.size();
	}
	
	public static CellBase getByID(char read) {
		
		
		for(Connector c : compedium)
		{
			if(c.id == read)
				return c.create.apply(0);
		}
		return new Air();
		
	}
	
	public char getId() {
		return id;
	}
	
	public boolean isTangible() {
		return tangible;
	}
	
	public boolean canBeSplit() {
		return splitable;
	}
	
	public Color getColor() {
		return main;
	}
}

