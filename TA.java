/**
 * @author:  Athena Bolyos, Jacob Hazen, Daniel Orduz, and Aiden Wong.
 * About program:  TA is a subclass of Enemy.
 */

public class TA extends Enemy
{
	public TA(Render render) 
	{
		super(render);
		set_appearance(5);
		set_health(10);
		get_doll().set_appearance(get_appearance());
	}
}
