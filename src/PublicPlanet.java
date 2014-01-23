public class PublicPlanet implements Cloneable {

	public int planetID;
	public int owner;
	public int numShips;
	public int growthRate;
	public double x, y;


	// Initializes a planet.
	public PublicPlanet(int planetID,
						int owner,
						int numShips,
						int growthRate,
						double x,
						double y) {
		this.planetID = planetID;
		this.owner = owner;
		this.numShips = numShips;
		this.growthRate = growthRate;
		this.x = x;
		this.y = y;
	}


	private PublicPlanet(PublicPlanet _p) {
		planetID = _p.planetID;
		owner = _p.owner;
		numShips = _p.numShips;
		growthRate = _p.growthRate;
		x = _p.x;
		y = _p.y;
	}


	// Accessors and simple modification functions. These should be mostly
	// self-explanatory.
	public int PlanetID() {
		return planetID;
	}


	public int Owner() {
		return owner;
	}

	// Not allowed for now
	/*
	public void NumShips(int newNumShips) {
	this.numShips = newNumShips;
    }
    */


	public int NumShips() {
		return numShips;
	}


	public int GrowthRate() {
		return growthRate;
	}


	// coordinates in the space
	public double X() {
		return x;
	}


	public double Y() {
		return y;
	}


	public void Owner(int newOwner) {
		this.owner = newOwner;
	}


	public void AddShips(int amount) {
		numShips += amount;
	}


	public void RemoveShips(int amount) {
		numShips -= amount;
	}


	public Object clone() {
		return new PublicPlanet(this);
	}


	@Override
	public String toString() {
		return "Planet{" +
				"ID=" + planetID +
				"owner=" + owner +
				", numShips=" + numShips +
				", growthRate=" + growthRate +
				'}';
	}
}
