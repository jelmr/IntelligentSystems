/**
 * Dummy class for testing purposes, should be removed!
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 * Date: 11/01/2014
 */


public class Empty15 extends Bot15 {
	public static void main(String[] args) {
		SimulatedPlanetWars15 spw = new SimulatedPlanetWars15("tools/maps/8planets/map1.txt");

		for (Planet15 planet : spw.EnemyPlanets()) {
			System.out.printf(String.valueOf(planet));
		}
	}


	@Override
	public Action15 getAction(PlanetWars15 pw) {
		return new Action15(pw.MyPlanets().get(0), pw.EnemyPlanets().get(0));
	}
}
