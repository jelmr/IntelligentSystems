import java.util.List;


/**
 * A bot that takes three parameters to determine its playing style. Both the heuristics used as the aggressiveness
 * of the bot are determined by these parameters. This bot can be fine-tuned by a genetic algorithm very well.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 20/01/14
 */
public class HeuristicBot extends DarwinBot {

	double heuristicsA, heuristicsB, p;


	/**
	 * Create a new bot with certain tactics and aggressiveness.
	 *
	 * @param heuristicsA Tactic to select source planet.
	 * @param heuristicsB Tactic to select target planet.
	 * @param p Probability to attack hostile planet vs neutral planet.
	 */
	public HeuristicBot(double heuristicsA, double heuristicsB, double p) {
		this.heuristicsA = heuristicsA;
		this.heuristicsB = heuristicsB;
		this.p = p;
	}


	/**
	 * Create a new bot with random characteristics.
	 */
	public HeuristicBot(){
		this.heuristicsA = Math.random();
		this.heuristicsB = Math.random();
		this.p = Math.random();
	}


	@Override
	public Action getAction(PlanetWars pw) {

		List<Planet> targetPlanets;

		if (Math.random() < p) {
			targetPlanets = pw.NeutralPlanets();

		} else {
			targetPlanets = pw.EnemyPlanets();
		}

		int heuristicCount = Heuristic.HEURISTICS.length;
		Planet source = Heuristic.select(pw.MyPlanets(), Heuristic.HEURISTICS[((int) (heuristicsA * heuristicCount))]);
		Planet target = Heuristic.select(targetPlanets, Heuristic.HEURISTICS[((int) (heuristicsB * heuristicCount))]);

		return new Action(source, target);

	}


	@Override
	public String toString() {
		return String.format("%d, %d, %.2f", ((int) (heuristicsA * Heuristic.HEURISTICS.length)), ((int) (heuristicsB * Heuristic.HEURISTICS.length)), p);
	}


	@Override
	public DarwinBot getInstance(double... pars) {
		return new HeuristicBot(pars[0], pars[1], pars[2]);
	}


	@Override
	public void setPars(double... pars) {
		heuristicsA = pars[0];
		heuristicsB = pars[1];
		p = pars[2];
	}


	@Override
	public double[] getPars() {
		return new double[]{heuristicsA, heuristicsB,p};
	}


	public static void main(String[] args) {
		Bot bot = new HeuristicBot();
		Bot.execute(bot);
	}
}
