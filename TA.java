
public class TA extends Enemy
{
	public TA() 
	{
		super();
		set_appearance(5);
		set_health(10);
		get_doll().set_appearance(get_appearance());
	}
}
