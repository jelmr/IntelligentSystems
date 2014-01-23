import static java.lang.System.identityHashCode;


/**
 * CURRENTLY NOT USING DISTANCE TO PLANETS
 * CURRENTLY NOT USING DISTANCE TO PLANETS
 * CURRENTLY NOT USING DISTANCE TO PLANETS
 * CURRENTLY NOT USING DISTANCE TO PLANETS
 * CURRENTLY NOT USING DISTANCE TO PLANETS
 */

public class SimulatedPlanetWarsParallel extends SimulatedPlanetWars implements Cloneable {

	boolean growNextTurn;
	Action queuedAction;


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
		Action currentPlayerAction = new Action(source, target);


		if(growNextTurn){
			double currentPlayerDistance = getDistance(currentPlayerAction);
			double queuedActionDistance = getDistance(queuedAction);

			if(currentPlayerDistance < queuedActionDistance){
				SimulateTurn(currentPlayerAction.source, currentPlayerAction.target, queuedAction.source, queuedAction.target);
			} else {
				SimulateTurn(queuedAction.source, queuedAction.target, currentPlayerAction.source, currentPlayerAction.target);
			}
			growNextTurn = false;
			super.SimulateGrowth();
		} else {
			growNextTurn = true;
			queuedAction = currentPlayerAction;
		}

	}


	private void SimulateTurn(Planet as, Planet at, Planet bs, Planet bt) {


//		String s = "\n\n===============================\n";
//
//		for (Planet planet : planets) {
//			s+= planet.toString()+"\n";
//		}
//		s += "\n\n";


//		s += String.format("Action a : %s -- %s (%.3f)\n", as, at, getDistance(as, at));
//		s += String.format("Action b : %s -- %s (%.3f)\n", bs, bt, getDistance(bs, bt));

		int a = as.Owner();
		int b = bs.Owner();


		int aPower = as.NumShips() / 2;
		Planet asTemp = new Planet(as.PlanetID(), as.Owner(), as.NumShips() - aPower, as.GrowthRate(), as.X(), as.Y());
		planets.set(as.PlanetID(), asTemp);

		int bPower = bs.NumShips() / 2;
		Planet bsTemp = new Planet(bs.PlanetID(), bs.Owner(), bs.NumShips() - bPower, bs.GrowthRate(), bs.X(), bs.Y());
		planets.set(bs.PlanetID(), bsTemp);

		Planet newAt = GetPlanet(at.PlanetID());

		if(at.Owner() == a){
			newAt = new Planet(newAt.PlanetID(), a, newAt.NumShips() + aPower, newAt.GrowthRate(), newAt.X(), newAt.Y());
		} else {
			if(newAt.NumShips() < aPower){ // A will conquer the planet
				newAt = new Planet(newAt.PlanetID(), a, Math.abs(newAt.NumShips() - aPower), newAt.GrowthRate(), newAt.X(), newAt.Y());
			} else {
				newAt = new Planet(newAt.PlanetID(), newAt.Owner(), newAt.NumShips() - aPower, newAt.GrowthRate(), newAt.X(), newAt.Y());
			}
		}

		planets.set(newAt.PlanetID(), newAt);

		Planet newBt = GetPlanet(bt.PlanetID());


		if(bt.Owner() == b){
			newBt = new Planet(newBt.PlanetID(), b, newBt.NumShips() + bPower, newBt.GrowthRate(), newBt.X(), newBt.Y());
		} else {
			if(newBt.NumShips() < bPower){ // B will conquer the planet
				newBt = new Planet(newBt.PlanetID(), b, Math.abs(newBt.NumShips() - bPower), newBt.GrowthRate(), newBt.X(), newBt.Y());
			} else {
				newBt = new Planet(newBt.PlanetID(), newBt.Owner(), newBt.NumShips() - bPower, newBt.GrowthRate(), newBt.X(), newBt.Y());
			}
		}
		planets.set(newBt.PlanetID(), newBt);




//		for (Planet planet : planets) {
//			s+= planet.toString()+"\n";
//		}
//		s += "\n\n";
//
//		s += "\n\n===============================\n";
//
//		System.out.println(s);
	}


	public void IssueOrder(Action action){
		IssueOrder(action.source, action.target);
	}

	public static double getDistance(Action action){
		return getDistance(action.source, action.target);
	}

	public static double getDistance(Planet a, Planet b){
		double dx = a.X() - b.X();
		double dy = a.Y() - b.Y();
		return Math.ceil(Math.sqrt(dx * dx + dy * dy));
	}
}
