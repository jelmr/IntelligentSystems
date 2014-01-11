/**
 * Currently a copy of CustomBot. Should be changed later.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 * Date: 11/01/2014
 */

public class ChangeBot extends Bot{

	@Override
	public Planet getSourcePlanet(PlanetWars pw) {
		return select(pw.MyPlanets(), Heuristic.MOST_SHIPS);
	}

	@Override
	public Planet getTargetPlanet(PlanetWars pw) {
		return select(pw.NotMyPlanets(), Heuristic.BEST_GENERATION_PER_SHIPS_LOST);
	}


	public static void main(String[] args) {
		Bot bot = new ChangeBot();
		Bot.execute(bot);
	}


}
