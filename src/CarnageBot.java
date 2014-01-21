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
		Planet source = Heuristic.select(pw.MyPlanets(), Heuristic.MOST_SHIPS);
        Planet target = targetPlanet(pw);
		return new Action(source, target);
	}

    Planet targetPlanet(PlanetWars pw) {
        return Heuristic.select(pw.NotMyPlanets(), Heuristic.TEST_HEURISTIC);

    }


	public static void main(String[] args) {
		Bot bot = new CarnageBot();
		Bot.execute(bot);
	}


}
