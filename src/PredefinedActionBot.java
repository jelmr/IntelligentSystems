/**
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 23/01/14
 */
public class PredefinedActionBot extends Bot {

	@Override
	public Action getAction(PlanetWars pw) {

		Planet source = pw.MyPlanets().get(0);
		Planet target = pw.EnemyPlanets().get(0);

		String s ="\n\n";
		for (Planet planet : pw.Planets()) {
			s += planet.toString()+"\n";
		}

//		logger.info(String.format("%s\n%s - %s", s, source, target));

		return new Action(source, target);
	}


	public static void main(String[] args) {
		Bot bot = new PredefinedActionBot();
		Bot.execute(bot);
	}
}
