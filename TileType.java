
public class TileType {
	
	private String tileType = "";
	
	public int xcoord = 0;
	public int ycoord = 0;

	public int getXcoord() {
		return xcoord;
	}

	public void setXcoord(int xcoord) {
		this.xcoord = xcoord;
	}

	public int getYcoord() {
		return ycoord;
	}

	public void setYcoord(int ycoord) {
		this.ycoord = ycoord;
	}

	public TileType(String string) {
		setTileType(string);
	}

	public TileType(String string, int j, int i) {
		setTileType(string);
		this.xcoord = j;
		this.ycoord = i;
		
		
	}

	public void setTileType(String type) {
		this.tileType = type;
	}
	
	public String getTileType() { return this.tileType;}
}
