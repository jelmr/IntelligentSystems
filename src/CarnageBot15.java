/**
 * A bot that sends ships from its biggest planet (by shipcount) to an hostile planet with the most favorable GrowthRate / ships lost ratio.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 * Date: 11/01/2014
 */


public class CarnageBot15 extends Bot15 {

	public Action15 getAction(PlanetWars15 pw) {

		String s ="";
		for (Planet15 planet : pw.MyPlanets()) {
			s += planet.toString()+"\n";
		}
		s+="Enemy Planets";
		for (Planet15 planet : pw.EnemyPlanets()) {
			s += planet.toString()+"\n";
		}

		Planet15 source = Heuristic15.select(pw.MyPlanets(), Heuristic15.MOST_SHIPS);
		Planet15 target = Heuristic15.select(pw.NotMyPlanets(), Heuristic15.TEST_HEURISTIC);

//		System.out.printf("\n\nMy Planets: "+s+"\n");
//		logger.info("\n\nMy Planets: "+s+"\n");
		return new Action15(source, target);
	}


	public static void main(String[] args) {
		Bot15 bot = new CarnageBot15();
		Bot15.execute(bot);
	}


}
