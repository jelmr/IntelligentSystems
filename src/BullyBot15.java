/**
 *  Bot15 that sends ships from its biggest planet (by shipcount) to the smallest (by shipcount) neutral or hostile planet.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 * Date: 11/01/2014
 */

public class BullyBot15 extends Bot15 {

	public Action15 getAction(PlanetWars15 pw) {
		Planet15 source = Heuristic15.select(pw.MyPlanets(), Heuristic15.MOST_SHIPS);
		Planet15 target = Heuristic15.select(pw.NotMyPlanets(), Heuristic15.FEWEST_SHIPS);
		return new Action15(source, target);
	}


	public static void main(String[] args) {
		Bot15 bot = new BullyBot15();
		Bot15.execute(bot);
	}
}
