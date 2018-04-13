
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
