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

	@Override
	public Planet getSourcePlanet(PlanetWars pw) {
		List<Planet> myPlanets = pw.MyPlanets();
		int randomPlanet = ((int) (Math.random() * myPlanets.size()));
		return myPlanets.get(randomPlanet);
	}

	@Override
	public Planet getTargetPlanet(PlanetWars pw) {
		List<Planet> notMyPlanets = pw.NotMyPlanets();
		int randomPlanet = ((int) (Math.random() * notMyPlanets.size()));
		return notMyPlanets.get(randomPlanet);
	}


	public static void main(String[] args) {
		Bot bot = new RandomBot();
		Bot.execute(bot);
	}


}
