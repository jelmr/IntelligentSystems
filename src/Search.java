/**
 * Interface for heuristics. An heuristic attaches a performance-score to a planet.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 11/01/2014
 */

public abstract class Search {

	public static final Search 	MIN_MAX = new MinMaxBot.MinMax(),
								MOD_P_MAX = new ModPMaxBot.ModPMax(),
								MOD_MAX = new ModMaxBot.ModMax();


	public abstract Action findBest(PlanetWars pw, PerformanceMeasure pm);

}
