import java.util.List;


/**
 * A bot that sends ships from a random friendly planet to a random neutral or hostile planet.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 * Date: 11/01/2014
 */

public class RandomBot extends Bot{

	public Action getAction(PlanetWars pw) {
		List<Planet> myPlanets = pw.MyPlanets();
		int randomPlanet = ((int) (Math.random() * myPlanets.size()));
		Planet source = myPlanets.get(randomPlanet);

		List<Planet> notMyPlanets = pw.NotMyPlanets();
		int randomPlanet1 = ((int) (Math.random() * notMyPlanets.size()));
		Planet target = notMyPlanets.get(randomPlanet1);

		return new Action(source, target);
	}


	public static void main(String[] args) {
		Bot bot = new RandomBot();
		Bot.execute(bot);
	}
}
