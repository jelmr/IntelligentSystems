/**
 * Dummy class for testing purposes, should be removed!
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 * Date: 11/01/2014
 */


public class Empty extends Bot {
	public static void main(String[] args) {
		SimulatedPlanetWars spw = new SimulatedPlanetWars("tools/maps/8planets/map1.txt");

		for (Planet planet : spw.EnemyPlanets()) {
			System.out.printf(String.valueOf(planet));
		}
	}


	@Override
	public Action getAction(PlanetWars pw) {
		return new Action(pw.MyPlanets().get(0), pw.EnemyPlanets().get(0));
	}
}
