/**
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 15/01/14
 */
public class Simulator {


	public static SimulatedPlanetWars simulate(SimulatedPlanetWarsParallel spw, Bot a, Bot b, int turns) {
		SimulatedPlanetWarsParallel result = spw;

		for (int i = 0; i < turns; i++) {
			result = simulateTurn(result, a, b);
		}

		return result;
	}

	public static SimulatedPlanetWarsParallel simulateTurn(SimulatedPlanetWarsParallel spw, Bot a, Bot b){
		SimulatedPlanetWarsParallel newSpw = new SimulatedPlanetWarsParallel(spw, Bot.FRIENDLY);

		Action aAction = a.getAction(spw);
		Action bAction = b.getAction(spw);

		int aDistance = getDistance(aAction);
		int bDistance = getDistance(bAction);

		if (aDistance > bDistance) {
			newSpw.IssueOrder(bAction);
			newSpw.IssueOrder(aAction);
		} else {
			newSpw.IssueOrder(aAction);
			newSpw.IssueOrder(bAction);
		}

		return newSpw;
	}





	public static int getDistance(Action action) {
		return getDistance(action.source, action.target);
	}

	public static int getDistance(Planet a, Planet b){
		double dx = a.X() - b.X();
		double dy = a.Y() - b.Y();
		return (int) Math.ceil(Math.sqrt(dx * dx + dy * dy));

	}
}
