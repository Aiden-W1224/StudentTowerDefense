
public class Researcher extends Enemy
{
	public Researcher() 
	{
		super();
		set_appearance(4);
		set_health(20);
		get_doll().set_appearance(get_appearance());
	}
}