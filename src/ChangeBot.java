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
	public Action getAction(PlanetWars pw) {
		return Search.MIN_MAX.findBest(pw, PerformanceMeasure.MOST_SHIPS);
	}


	public static void main(String[] args) {
		Bot bot = new ChangeBot();
		Bot.execute(bot);
	}
}
