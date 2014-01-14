/**
 *  A bot that sends ships from his biggest planet (by shipcount) to an hostile planet with the most favorable GrowthRate / ships lost ratio.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 * Date: 11/01/2014
 */

public class AggresiveBot extends Bot{


	public Planet getSourcePlanet(PlanetWars pw) {
		return Heuristic.select(pw.MyPlanets(), Heuristic.MOST_SHIPS);
	}


	public Planet getTargetPlanet(PlanetWars pw) {
		return Heuristic.select(pw.EnemyPlanets(), Heuristic.BEST_GENERATION_PER_SHIPS_LOST);
	}


	public static void main(String[] args) {
		Bot bot = new AggresiveBot();
		Bot.execute(bot);
	}


	public Action getAction(PlanetWars pw) {
		return new Action(getSourcePlanet(pw), getTargetPlanet(pw));
	}
}
