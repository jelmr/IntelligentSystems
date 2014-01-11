/**
 *  Bot that sends ships from its biggest planet (by shipcount) to the smallest (by shipcount) neutral or hostile planet.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 * Date: 11/01/2014
 */

public class BullyBot extends Bot{


	@Override
	public Planet getSourcePlanet(PlanetWars pw) {
		return Heuristic.select(pw.MyPlanets(), Heuristic.MOST_SHIPS);
	}

	@Override
	public Planet getTargetPlanet(PlanetWars pw) {
		return Heuristic.select(pw.NotMyPlanets(), Heuristic.FEWEST_SHIPS);
	}


	public static void main(String[] args) {
		Bot bot = new BullyBot();
		Bot.execute(bot);
	}


}
