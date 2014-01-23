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

		String s ="";
		for (Planet planet : pw.MyPlanets()) {
			s += planet.toString()+"\n";
		}
		s+="Enemy Planets";
		for (Planet planet : pw.EnemyPlanets()) {
			s += planet.toString()+"\n";
		}

		Planet source = Heuristic.select(pw.MyPlanets(), Heuristic.MOST_SHIPS);
		Planet target = Heuristic.select(pw.NotMyPlanets(), Heuristic.TEST_HEURISTIC);

//		System.out.printf("\n\nMy Planets: "+s+"\n");
//		logger.info("\n\nMy Planets: "+s+"\n");
		return new Action(source, target);
	}


	public static void main(String[] args) {
		Bot bot = new CarnageBot();
		Bot.execute(bot);
	}


}
