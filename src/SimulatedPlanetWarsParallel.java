/**
 * CURRENTLY NOT USING DISTANCE TO PLANETS
 * CURRENTLY NOT USING DISTANCE TO PLANETS
 * CURRENTLY NOT USING DISTANCE TO PLANETS
 * CURRENTLY NOT USING DISTANCE TO PLANETS
 * CURRENTLY NOT USING DISTANCE TO PLANETS
 */

public class SimulatedPlanetWarsParallel extends SimulatedPlanetWars implements Cloneable {

	boolean growNextTurn;


	public SimulatedPlanetWarsParallel(String mapPath){
		super(mapPath);
	}

	public SimulatedPlanetWarsParallel(PlanetWars pw) {
		super(pw);
		growNextTurn = false;
	}


	public SimulatedPlanetWarsParallel(PlanetWars pw, int player) {
		super(pw, player);
		growNextTurn = false;
	}

	public void IssueOrder(Planet source, Planet target){
		super.SimulateAttack(player, source, target);

		if(growNextTurn){
			growNextTurn = false;
			super.SimulateGrowth();
		} else {
			growNextTurn = true;
		}

	}

	public void IssueOrder(Action action){
		IssueOrder(action.source, action.target);
	}
}
