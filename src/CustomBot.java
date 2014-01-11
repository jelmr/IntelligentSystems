/**
 * A bot that sends ships from its biggest planet (by shipcount) to an hostile planet with the most favorable GrowthRate / ships lost ratio.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 * Date: 11/01/2014
 */


public class CustomBot extends Bot{

	@Override
	public Planet getSourcePlanet(PlanetWars pw) {
		return select(pw.MyPlanets(), Heuristic.MOST_SHIPS);
	}

	@Override
	public Planet getTargetPlanet(PlanetWars pw) {
		return select(pw.NotMyPlanets(), Heuristic.BEST_GENERATION_PER_SHIPS_LOST);
	}


	public static void main(String[] args) {
		Bot bot = new CustomBot();
		Bot.execute(bot);
	}


}
