import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

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


    public static State bestFirst(State state, PerformanceMeasure pm) {
        // Performance measure: 1 is defined as win, 0 as loss.


        PriorityQueue<State> pq = new PriorityQueue<State>(100,
                new Comparator<State>(){
            public int compare(State a, State b){
                if (PerformanceMeasure.MOST_PLANETS.calculateScore(a.getPlanetWars()) < PerformanceMeasure.MOST_PLANETS.calculateScore(b.getPlanetWars())) return 1;
                if (PerformanceMeasure.MOST_PLANETS.calculateScore(a.getPlanetWars()) == PerformanceMeasure.MOST_PLANETS.calculateScore(b.getPlanetWars())) return 0;
                return -1;
            }});


        // Initialize PQ
        pq.add(state);

        while (pq.peek().getPlanetWars().Winner() != Bot.FRIENDLY) {
            State head = pq.poll();
            for (State newState : getStatePermutations(head)) {
                pq.add(newState);
            }
        }

        return pq.peek();
    }

    public static ArrayList<State> getStatePermutations(State state) {


        ArrayList<State> result = new ArrayList<State>();

        for (Planet source : state.getPlanetWars().MyPlanets()) {

            for (Planet target : state.getPlanetWars().Planets()) {
                SimulatedPlanetWars spw = new SimulatedPlanetWars(state.getPlanetWars());
                spw.IssueOrder(source, target);
                result.add(new State(spw, state));
            }
        }
        return result;
    }

}
