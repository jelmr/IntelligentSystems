/**
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 20/01/14
 */
public class DummyBot extends Bot {

	@Override
	public Action getAction(PlanetWars pw) {
		return (new HeuristicBot(0,3,0.0871)).getAction(pw);
	}

	public static void main(String[] args) {
		Bot bot = new DummyBot();
		Bot.execute(bot);
	}

}
