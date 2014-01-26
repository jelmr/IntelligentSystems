import java.util.Comparator;
import java.util.PriorityQueue;


/**
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 22/01/14
 */
public class BeamBot extends Bot {

	public static void main(String[] args) {
		Bot bot = new BeamBot();
		Bot.execute(bot);
	}


	@Override
	public Action getAction(PlanetWars pw) {
		return Search.BEAM.findBest(pw, PerformanceMeasure.MOST_GROWTH);
	}


	public static class Beam extends Search {

		public static final int BEAM_WIDTH = 3;
		public static final int MAX_DEPTH = 10;


		@Override
		public Action findBest(PlanetWars pw, PerformanceMeasure pm) {

			Planet source = Heuristic.select(pw.MyPlanets(), Heuristic.MOST_SHIPS);

			PriorityQueue<PlanetScore> pq = new PriorityQueue<PlanetScore>(pw.NotMyPlanets().size(),
					new Comparator<PlanetScore>() {
						public int compare(PlanetScore a, PlanetScore b) {
							return -Double.compare(a.score, b.score);
						}
					});

			for (Planet planet : pw.NotMyPlanets()) {
				double score = Heuristic.TEST_HEURISTIC.calculateScore(planet);
				PlanetScore planetScore = new PlanetScore(score, planet);
				pq.add(planetScore);
			}

			double bestScore = -Double.MAX_VALUE;
			Action bestAction = null;

			for (int i = 0; i < BEAM_WIDTH; i++) {
				Planet planet = pq.poll().planet;
				Action friendlyAction = new Action(source, planet);

				Bot bot = new CarnageBot();
				Action enemyAction = bot.getAction(pw);

				SimulatedPlanetWarsParallel spw = new SimulatedPlanetWarsParallel(pw, Bot.FRIENDLY);
				spw.IssueOrder(friendlyAction);
				if (spw.Winner() != -1) {
					spw.player = Bot.HOSTILE;
					spw.IssueOrder(enemyAction);
				}

				double stateScore = getScoreRecursive(spw, pm, 1);

				if (stateScore > bestScore) {
					bestScore = stateScore;
					bestAction = friendlyAction;
				}

			}

			return bestAction;
		}

		private double getScoreRecursive(SimulatedPlanetWarsParallel pw, PerformanceMeasure pm, int depth){
			if(depth > MAX_DEPTH){
				return pm.calculateScore(pw);
			}


			Planet source = Heuristic.select(pw.MyPlanets(), Heuristic.MOST_SHIPS);

			PriorityQueue<PlanetScore> pq = new PriorityQueue<PlanetScore>(pw.NotMyPlanets().size(),
					new Comparator<PlanetScore>() {
						public int compare(PlanetScore a, PlanetScore b) {
							return -Double.compare(a.score, b.score);
						}
					});

			for (Planet planet : pw.NotMyPlanets()) {
				double score = Heuristic.TEST_HEURISTIC.calculateScore(planet);
				PlanetScore planetScore = new PlanetScore(score, planet);
				pq.add(planetScore);
			}

			double bestScore = -Double.MAX_VALUE;
			SimulatedPlanetWarsParallel bestState = null;

			for (int i = 0; i < BEAM_WIDTH; i++) {
				Planet planet = pq.poll().planet;
				Action friendlyAction = new Action(source, planet);

				Bot bot = new CarnageBot();
				Action enemyAction = bot.getAction(pw);

				SimulatedPlanetWarsParallel spw = new SimulatedPlanetWarsParallel(pw, Bot.FRIENDLY);
				spw.IssueOrder(friendlyAction);
				if (spw.Winner() != -1) {
					spw.player = Bot.HOSTILE;
					spw.IssueOrder(enemyAction);
				}

				double stateScore = PerformanceMeasure.MOST_SHIPS.calculateScore(spw);

				if (stateScore > bestScore) {
					bestScore = stateScore;
					bestState = spw;
				}

			}

			return getScoreRecursive(bestState, pm, depth + 1);
		}

		private class PlanetScore {

			double score;
			Planet planet;


			private PlanetScore(double score, Planet planet) {
				this.score = score;
				this.planet = planet;
			}

		}

	}

}
