
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
