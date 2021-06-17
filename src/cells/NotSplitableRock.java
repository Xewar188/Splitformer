package cells;

import java.awt.Color;

public class NotSplitableRock extends CellBase {

	public NotSplitableRock()
	{
		tangible = true;
		main = Color.gray.darker();
		id = '4';
		splitable = false;
	}
}
