/**
 * A bot that sends ships from its biggest planet (by shipcount) to an hostile planet with the most favorable GrowthRate / ships lost ratio.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 * Date: 11/01/2014
 */


public class CustomBot15 extends Bot15 {

	public Action15 getAction(PlanetWars15 pw) {

		String s ="\n\n";
		for (Planet15 planet : pw.Planets()) {
			s += planet.toString()+"\n";
		}





		Planet15 source = Heuristic15.select(pw.MyPlanets(), Heuristic15.MOST_SHIPS);
		Planet15 target = Heuristic15.select(pw.EnemyPlanets(), Heuristic15.BEST_GENERATION_PER_SHIPS_LOST);
//		logger.info(String.format("%s\n%s - %s", s, source, target));
		return new Action15(source, target);
	}



	public static void main(String[] args) {
		Bot15 bot = new CustomBot15();
		Bot15.execute(bot);
	}


}
