/**
 * A bot that sends ships from its biggest planet (by shipcount) to an hostile planet with the most favorable GrowthRate / ships lost ratio.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 * Date: 11/01/2014
 */


public class CarnageBot extends Bot{

	public Action getAction(PlanetWars pw) {
		String s ="\n\n";
		for (Planet planet : pw.Planets()) {
			s += planet.toString()+"\n";
		}

		Planet source = Heuristic.select(pw.MyPlanets(), Heuristic.MOST_SHIPS);
		Planet target = Heuristic.select(pw.EnemyPlanets(), Heuristic.TEST_HEURISTIC);

//		logger.info(String.format("%s\n%s - %s", s, source, target));

		return new Action(source, target);
	}


	public static void main(String[] args) {
		Bot bot = new CarnageBot();
		Bot.execute(bot);
	}


}
