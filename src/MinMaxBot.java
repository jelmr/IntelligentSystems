/**
 * Currently a copy of CustomBot. Should be changed later.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 * Date: 11/01/2014
 */

public class MinMaxBot extends Bot{

	@Override
	public Action getAction(PlanetWars pw) {



		String s ="\n\n";
		for (Planet planet : pw.Planets()) {
			s += planet.toString()+"\n";
		}



		Action best = Search.MIN_MAX.findBest(pw, PerformanceMeasure.MOST_PLANETS);
		logger.info(String.format("%s\n%s - %s", s, best.source, best.target));
		return best;
	}


	public static void main(String[] args) {
		Bot bot = new MinMaxBot();
		Bot.execute(bot);
	}


	static class MinMax extends Search {

		public static final int MAX_DEPTH = 3;


		@Override
		public Action findBest(PlanetWars pw, PerformanceMeasure pm) {
			Planet bestSource = null;
			Planet bestTarget = null;
			double maxScore = -Double.MAX_VALUE;

			for (Planet source : pw.MyPlanets()) {

				if (source.NumShips() <= 1) {continue;}

				for (Planet target : pw.Planets()) {
					SimulatedPlanetWarsParallel spw = new SimulatedPlanetWarsParallel(pw, FRIENDLY);
					spw.IssueOrder(source, target);

					spw = new SimulatedPlanetWarsParallel(spw, HOSTILE);

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


		private double findBestScore(SimulatedPlanetWarsParallel pw, PerformanceMeasure pm, int depth) {
			int winner = pw.Winner();

			if (winner != -1) {
				return winner == FRIENDLY ? 1 : 0;
			}
			if (depth > MAX_DEPTH) {
				return pm.calculateScore(pw);
			}

			double maxScore = -Double.MAX_VALUE;

			for (Planet source : pw.MyPlanets()) {


				if (source.NumShips() <= 1) {continue;}

				for (Planet target : pw.Planets()) {

					pw.IssueOrder(source, target);

					SimulatedPlanetWarsParallel spw = new SimulatedPlanetWarsParallel(pw, pw.notPlayer());
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
