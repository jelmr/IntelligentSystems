/**
 * Keeps one specific game state.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 * Date: 11/01/2014
 */

public class GameState {

	int growthRate, shipCount;


	public GameState(int growthRate, int shipCount) {
		this.growthRate = growthRate;
		this.shipCount = shipCount;
	}

	// Copy constructor
	public GameState(GameState state){
		this.growthRate = state.growthRate;
		this.shipCount = state.shipCount;
	}


	public void update(Planet planet){
		growthRate += planet.GrowthRate();
		shipCount -= planet.NumShips();
	}

	public int getGrowthRate() {
		return growthRate;
	}


	public void setGrowthRate(int growthRate) {
		this.growthRate = growthRate;
	}


	public int getShipCount() {
		return shipCount;
	}


	public void setShipCount(int shipCount) {
		this.shipCount = shipCount;
	}
}
