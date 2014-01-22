/**
 * Abstract class for Search algorithms.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 11/01/2014
 */

public abstract class Search {

	public static final Search 	MIN_MAX = new MinMaxBot.MinMax(),
								MOD_P_MAX = new ModPMaxBot.ModPMax(),
								MOD_MAX = new ModMaxBot.ModMax(),
								BEST_FIRST = new BestFirstBot.BestFirst(),
								BEAM = new BeamBot.Beam();


	/**
	 * Applies a search algorithm to find the best possible action to take.
	 *
	 * @param pw The base state
	 * @param pm The PerformanceMeasure to evaluate potential states with.
	 * @return The best action to take.
	 */
	public abstract Action findBest(PlanetWars pw, final PerformanceMeasure pm);

}
