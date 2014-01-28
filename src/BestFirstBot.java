import java.util.Comparator;
import java.util.PriorityQueue;


/**
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 19/01/14
 */
public class BestFirstBot extends Bot {

	@Override
	public Action getAction(PlanetWars pw) {
		return Search.BEST_FIRST.findBest(pw, PerformanceMeasure.MOST_SHIPS);
	}


	public static void main(String[] args) {
		Bot bot = new BestFirstBot();
		Bot.execute(bot);
	}





	public static class BestFirst extends Search {

		@Override
		public Action findBest(PlanetWars pw, PerformanceMeasure pm) {
			Action thing = bestFirst(new State(pw), pm).getAction();
			logger.info(thing.source.toString()+ thing.target.toString());
			return thing;
		}


		private static State bestFirst(State state, final PerformanceMeasure pm) {

			long start = System.currentTimeMillis();

			// Performance measure: 1 is defined as win, 0 as loss.

			PriorityQueue<State> pq = new PriorityQueue<State>(100,
					new Comparator<State>() {
						public int compare(State a, State b) {
							double scoreA = pm.calculateScore(a.getPlanetWars());
							double scoreB = pm.calculateScore(b.getPlanetWars());
							if (scoreA < scoreB)
								return 1;
							if (scoreA == scoreB)
								return 0;
							return -1;
						}
					});

			// Initialize PQ
			pq.add(state);

			while (pq.peek().getPlanetWars().Winner() != Bot.FRIENDLY) {
				long time = System.currentTimeMillis() - start;

				if(time > 900 ){
					break;
				}

				State head = pq.poll();
				for (State newState : State.getStatePermutations(head)) {
					pq.add(newState);
				}
			}


			return pq.peek();
		}
	}
}
