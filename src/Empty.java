/**
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 * Date: 11/01/2014
 */


public class Empty {
	public static void main(String[] args) {
		SimulatedPlanetWars spw = new SimulatedPlanetWars("tools/maps/8planets/map1.txt");

		for (Planet planet : spw.EnemyPlanets()) {
			System.out.printf(String.valueOf(planet));
		}
	}

}
