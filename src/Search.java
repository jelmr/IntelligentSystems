/**
 * Interface for heuristics. An heuristic attaches a performance-score to a planet.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 11/01/2014
 */

public abstract class Search {

	public static final Search MIN_MAX = new MinMax();


	public abstract Action findBest(PlanetWars pw, PerformanceMeasure pm);


	static class MinMax extends Search {

		public static final int MAX_DEPTH = 4;


		@Override
		public Action findBest(PlanetWars pw, PerformanceMeasure pm) {
			Planet bestSource = null;
			Planet bestTarget = null;
			double maxScore = -Double.MAX_VALUE;

			for (Planet source : pw.MyPlanets()) {

				if (source.NumShips() <= 1) {continue;}

				for (Planet target : pw.Planets()) {
					SimulatedPlanetWars spw = new SimulatedPlanetWars(pw, Bot.FRIENDLY);
					spw.IssueOrder(source, target);

					spw = new SimulatedPlanetWars(spw, Bot.HOSTILE);

					double score = findBestScore(spw, pm, 1);

					if (score > maxScore) {
						bestSource = source;
						bestTarget = target;
						maxScore = score;
					}

				}
			}
			return new Action(bestSource, bestTarget);
		}


		private double findBestScore(SimulatedPlanetWars pw, PerformanceMeasure pm, int depth) {
			int winner = pw.Winner();
            
			if (winner != -1) {
				return winner == Bot.FRIENDLY ? 1 : 0;
			}
			if (depth > MAX_DEPTH) {
				return pm.calculateScore(pw);
			}

			double maxScore = -Double.MAX_VALUE;

			for (Planet source : pw.MyPlanets()) {


				if (source.NumShips() <= 1) {continue;}

				for (Planet target : pw.Planets()) {

					pw.IssueOrder(source, target);

					SimulatedPlanetWars spw = new SimulatedPlanetWars(pw, pw.notPlayer());
					double score = findBestScore(spw, pm, depth + 1);

                    if (depth % 2 != 0 && score > maxScore) {
                        // Friendly chooses maximal score
						maxScore = score;

                    } else if (depth % 2 == 0 && score < maxScore) {
                        // Enemy chooses minimal score
						maxScore = score;
					}
				}
            }
			

			return maxScore;
		}

	}
}
