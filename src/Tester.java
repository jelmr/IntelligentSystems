/**
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 23/01/14
 */
public class Tester {

	private void start() {

		Bot bot = new CarnageBot();

		String[] maps = new String[1];

		maps[0] = "tools/maps/8planets/map1.txt";

		for (String map : maps) {
			System.out.println("Starting...");
			System.out.println(Population.simulate(bot, new BullyBot(), map));
		}
//		for (String map : maps) { System.out.println(Population.simulate(bot, new CarnageBot(), map));}
//		for (String map : maps) { System.out.println(Population.simulate(bot, new BullyBot(), map));}

	}



	public static void main(String[] args) {
		new Tester().start();
	}
}
