public class ModPMaxBot extends Bot{

	@Override
	public Action getAction(PlanetWars pw) {
		return Search.MOD_P_MAX.findBest(pw, PerformanceMeasure.MOST_SHIPS);
	}


	public static void main(String[] args) {
		Bot bot = new ModPMaxBot();
		Bot.execute(bot);
	}


	static class ModPMax extends Search {

		public static final int MAX_DEPTH = 2;
		public static final int STARTING_DEPTH = 1;
		public static final int MAX_SCORE = 1;
		public static final int MIN_SCORE = 0;


		@Override
		public Action findBest(PlanetWars pw, PerformanceMeasure pm) {
			Planet bestTarget = null;
			double maxScore = -Double.MAX_VALUE;

			Planet source = Heuristic.select(pw.MyPlanets(), Heuristic.MOST_SHIPS);
			for (Planet target : pw.Planets()) {

				SimulatedPlanetWars enemySpw = new SimulatedPlanetWars(pw, HOSTILE);
				Planet enemySource = Heuristic.select(enemySpw.MyPlanets(), Heuristic.MOST_SHIPS);

				for (Planet enemyTarget : enemySpw.NotMyPlanets()) {

					int ourDistance = Simulator.getDistance(source, target);
					int enemyDistance = Simulator.getDistance(enemySource, enemyTarget);

					double score;

					if (ourDistance >= enemyDistance) {
						enemySpw.IssueOrder(enemySource, enemyTarget);
						SimulatedPlanetWars newSpw = new SimulatedPlanetWars(enemySpw, FRIENDLY);
						newSpw.IssueOrder(source, target);
						score = findBestScore(newSpw, pm, STARTING_DEPTH);
					} else {
						SimulatedPlanetWars newSpw = new SimulatedPlanetWars(enemySpw, FRIENDLY);
						newSpw.IssueOrder(source, target);
						enemySpw = new SimulatedPlanetWars(newSpw, HOSTILE);
						enemySpw.IssueOrder(enemySource, enemyTarget);
						score = findBestScore(newSpw, pm, STARTING_DEPTH);
					}

					if (score > maxScore) {
						bestTarget = target;
						maxScore = score;
					}
				}
            }
            return new Action(source, bestTarget);
        }


        private double findBestScore(SimulatedPlanetWars pw, PerformanceMeasure pm, int depth) {
            int winner = pw.Winner();

            if (winner != -1) {
                return winner == FRIENDLY ? MAX_SCORE : MIN_SCORE;
            }
            if (depth > MAX_DEPTH) {
				pw.log("STOPPING");
                return pm.calculateScore(pw);
            }
            if (pw.MyPlanets().size() <=0) {
                return MIN_SCORE;
            }

            double maxScore = -Double.MAX_VALUE;

			Planet source = Heuristic.select(pw.MyPlanets(), Heuristic.MOST_SHIPS);

			for (Planet target : pw.Planets()) {

				SimulatedPlanetWars enemySpw = new SimulatedPlanetWars(pw, HOSTILE);
				Planet enemySource = Heuristic.select(enemySpw.MyPlanets(), Heuristic.MOST_SHIPS);

				for (Planet enemyTarget : enemySpw.NotMyPlanets()) {

					int ourDistance = Simulator.getDistance(source, target);
					int enemyDistance = Simulator.getDistance(enemySource, enemyTarget);

					double score;

					if (ourDistance >= enemyDistance) {
						enemySpw.IssueOrder(enemySource, enemyTarget);
						SimulatedPlanetWars newSpw = new SimulatedPlanetWars(enemySpw, FRIENDLY);
						newSpw.IssueOrder(source, target);
						score = findBestScore(newSpw, pm, depth + 1);
					} else {
						SimulatedPlanetWars newSpw = new SimulatedPlanetWars(enemySpw, FRIENDLY);
						newSpw.IssueOrder(source, target);
						enemySpw = new SimulatedPlanetWars(newSpw, HOSTILE);
						enemySpw.IssueOrder(enemySource, enemyTarget);
						score = findBestScore(newSpw, pm, depth + 1);
					}

					if (score > maxScore) {
						maxScore = score;
					}
				}
			}
            return maxScore;
        }
    }
}
