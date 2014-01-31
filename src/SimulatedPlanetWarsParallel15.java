/**
 * CURRENTLY NOT USING DISTANCE TO PLANETS
 * CURRENTLY NOT USING DISTANCE TO PLANETS
 * CURRENTLY NOT USING DISTANCE TO PLANETS
 * CURRENTLY NOT USING DISTANCE TO PLANETS
 * CURRENTLY NOT USING DISTANCE TO PLANETS
 */

public class SimulatedPlanetWarsParallel15 extends SimulatedPlanetWars15 implements Cloneable {

	boolean growNextTurn;
	Action15 queuedAction;


	public SimulatedPlanetWarsParallel15(String mapPath){
		super(mapPath);
	}

	public SimulatedPlanetWarsParallel15(PlanetWars15 pw) {
		super(pw);
		growNextTurn = false;
	}

	public SimulatedPlanetWarsParallel15(SimulatedPlanetWarsParallel15 pw) {
		super(pw);
		this.growNextTurn = pw.growNextTurn;
	}


	public SimulatedPlanetWarsParallel15(PlanetWars15 pw, int player) {
		super(pw, player);
		growNextTurn = false;
	}

	public void IssueOrder(Planet15 source, Planet15 target){
		Action15 currentPlayerAction = new Action15(source, target);


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


	private void SimulateTurn(Planet15 as, Planet15 at, Planet15 bs, Planet15 bt) {


//		String s = "\n\n===============================\n";
//
//		for (Planet15 planet : planets) {
//			s+= planet.toString()+"\n";
//		}
//		s += "\n\n";


//		s += String.format("Action15 a : %s -- %s (%.3f)\n", as, at, getDistance(as, at));
//		s += String.format("Action15 b : %s -- %s (%.3f)\n", bs, bt, getDistance(bs, bt));

		int a = as.Owner();
		int b = bs.Owner();


		int aPower = as.NumShips() / 2;
		Planet15 asTemp = new Planet15(as.PlanetID(), as.Owner(), as.NumShips() - aPower, as.GrowthRate(), as.X(), as.Y());
		planets.set(as.PlanetID(), asTemp);

		int bPower = bs.NumShips() / 2;
		Planet15 bsTemp = new Planet15(bs.PlanetID(), bs.Owner(), bs.NumShips() - bPower, bs.GrowthRate(), bs.X(), bs.Y());
		planets.set(bs.PlanetID(), bsTemp);

		Planet15 newAt = GetPlanet(at.PlanetID());

		if(at.Owner() == a){
			newAt = new Planet15(newAt.PlanetID(), a, newAt.NumShips() + aPower, newAt.GrowthRate(), newAt.X(), newAt.Y());
		} else {
			if(newAt.NumShips() < aPower){ // A will conquer the planet
				newAt = new Planet15(newAt.PlanetID(), a, Math.abs(newAt.NumShips() - aPower), newAt.GrowthRate(), newAt.X(), newAt.Y());
			} else {
				newAt = new Planet15(newAt.PlanetID(), newAt.Owner(), newAt.NumShips() - aPower, newAt.GrowthRate(), newAt.X(), newAt.Y());
			}
		}

		planets.set(newAt.PlanetID(), newAt);

		Planet15 newBt = GetPlanet(bt.PlanetID());


		if(bt.Owner() == b){
			newBt = new Planet15(newBt.PlanetID(), b, newBt.NumShips() + bPower, newBt.GrowthRate(), newBt.X(), newBt.Y());
		} else {
			if(newBt.NumShips() < bPower){ // B will conquer the planet
				newBt = new Planet15(newBt.PlanetID(), b, Math.abs(newBt.NumShips() - bPower), newBt.GrowthRate(), newBt.X(), newBt.Y());
			} else {
				newBt = new Planet15(newBt.PlanetID(), newBt.Owner(), newBt.NumShips() - bPower, newBt.GrowthRate(), newBt.X(), newBt.Y());
			}
		}
		planets.set(newBt.PlanetID(), newBt);




//		for (Planet15 planet : planets) {
//			s+= planet.toString()+"\n";
//		}
//		s += "\n\n";
//
//		s += "\n\n===============================\n";
//
//		System.out.println(s);
	}


	public void IssueOrder(Action15 action){
		IssueOrder(action.source, action.target);
	}

	public static double getDistance(Action15 action){
		return getDistance(action.source, action.target);
	}

	public static double getDistance(Planet15 a, Planet15 b){
		double dx = a.X() - b.X();
		double dy = a.Y() - b.Y();
		return Math.ceil(Math.sqrt(dx * dx + dy * dy));
	}
}
