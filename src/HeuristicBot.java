import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.util.List;


/**
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 20/01/14
 */
public class HeuristicBot extends Bot {

	int heuristicsA, heuristicsB;
	double p;

	public HeuristicBot(int heuristicsA, int heuristicsB, double p) {
		this.heuristicsA = heuristicsA;
		this.heuristicsB = heuristicsB;
		this.p = p;
	}

	public HeuristicBot(){
		this.heuristicsA = randomHeuristic();
		this.heuristicsB = randomHeuristic();
		this.p = Math.random();
	}


	public static int randomHeuristic() {
		return (int) (Math.random() * Heuristic.HEURISTICS.length);
	}


	@Override
	public Action getAction(PlanetWars pw) {

		List<Planet> targetPlanets;

		if (Math.random() < p) {
			targetPlanets = pw.NotMyPlanets();

		} else {
			targetPlanets = pw.EnemyPlanets();
		}

		Planet source = Heuristic.select(pw.MyPlanets(), Heuristic.HEURISTICS[heuristicsA]);
		Planet target = Heuristic.select(targetPlanets, Heuristic.HEURISTICS[heuristicsB]);

		return new Action(source, target);

	}


	public static void main(String[] args) {
		Bot bot = new HeuristicBot();
		Bot.execute(bot);
	}


	@Override
	public String toString() {
		return "{"+heuristicsA +
				"," + heuristicsB +
				"," + p +
				'}';
	}



}
