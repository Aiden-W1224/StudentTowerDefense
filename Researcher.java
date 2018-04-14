/**
 * @author:  Athena Bolyos, Jacob Hazen, Daniel Orduz, and Aiden Wong.
 * About program:  Researcher is a subclass of Enemy. 
 */

public class Researcher extends Enemy
{
	public Researcher(Render render) 
	{
		super(render);
		set_appearance(4);
		set_health(20);
		get_doll().set_appearance(get_appearance());
	}
}
