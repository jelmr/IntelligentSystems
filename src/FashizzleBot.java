public class FashizzleBot extends Bot{

	@Override
	public Action getAction(PlanetWars pw) {
		return Search.MOD_MAX.findBest(pw, PerformanceMeasure.MOST_SHIPS);
	}


	public static void main(String[] args) {
		Bot bot = new FashizzleBot();
		Bot.execute(bot);
	}


	static class ModMax extends Search {

		public static final int MAX_DEPTH = 3;
		public static final int STARTING_DEPTH = 1;
		public static final int MAX_SCORE = 1;
		public static final int MIN_SCORE = 0;


		@Override
		public Action findBest(PlanetWars pw, PerformanceMeasure pm) {
			Planet bestTarget = null;
			double maxScore = -Double.MAX_VALUE;

			Planet source = Heuristic.select(pw.MyPlanets(), Heuristic.MOST_SHIPS);
			for (Planet target : pw.Planets()) {
				if (pw.EnemyPlanets().contains(target) && target.NumShips() > source.NumShips()/2.0) {
					continue;
				}
				SimulatedPlanetWars spw = new SimulatedPlanetWars(pw, FRIENDLY);
				spw.IssueOrder(source, target);
				spw.player = HOSTILE;
				spw = new SimulatedPlanetWars(spw, HOSTILE);
				double score = findBestScore(spw, pm, STARTING_DEPTH);

				if (score > maxScore) {
					bestTarget = target;
					maxScore = score;
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
				return pm.calculateScore(pw);
			}
			if (pw.MyPlanets().size() <=0) {
				return MIN_SCORE;
			}

			double maxScore = -Double.MAX_VALUE;

			Planet source = Heuristic.select(pw.MyPlanets(), Heuristic.MOST_SHIPS);


			for (Planet target : pw.NotMyPlanets()) {
				pw.player = HOSTILE;
				Bot newBot = new CarnageBot();
				pw.IssueOrder(newBot.getAction(pw));
				pw.player = FRIENDLY;
				pw.IssueOrder(source, target);
				SimulatedPlanetWars spw = new SimulatedPlanetWars(pw, pw.notPlayer());
				double score = findBestScore(spw, pm, depth + 1);

				if (pw.player == FRIENDLY && score > maxScore) {
					maxScore = score;
				}
			}
			return maxScore;
		}
	}
}
