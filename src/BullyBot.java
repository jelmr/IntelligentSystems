/**
 *  Bot that sends ships from its biggest planet (by shipcount) to the smallest (by shipcount) neutral or hostile planet.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 * Date: 11/01/2014
 */

public class BullyBot extends Bot{

	public Action getAction(PlanetWars pw) {
		Planet source = Heuristic.select(pw.MyPlanets(), Heuristic.MOST_SHIPS);
		Planet target = Heuristic.select(pw.NotMyPlanets(), Heuristic.FEWEST_SHIPS);
		return new Action(source, target);
	}


	public static void main(String[] args) {
		Bot bot = new BullyBot();
		Bot.execute(bot);
	}
}
