import java.util.List;
public class ModMaxBot extends Bot{

	@Override
	public Action getAction(PlanetWars pw) {
		return (new ModMax()).findBest(pw, PerformanceMeasure.MOST_SHIPS);
	}


	public static void main(String[] args) {
		Bot bot = new ModMaxBot();
		Bot.execute(bot);
	}
	static class ModMax extends Search {

		public static final int MAX_DEPTH = 3;


        public static Planet getLargestPlanet(List<Planet> planets) {
            if (planets.size() <= 0) {
                return null;
            }
            Planet result = planets.get(0);
            for (Planet planet : planets) {
                if (planet.NumShips() > result.NumShips()) {
                    result = planet;
                }
            }
            return result;
        }

		@Override
		public Action findBest(PlanetWars pw, PerformanceMeasure pm) {
			Planet bestTarget = null;
			double maxScore = -Double.MAX_VALUE;


            Planet source = getLargestPlanet(pw.MyPlanets());
            for (Planet target : pw.Planets()) {
                if (pw.EnemyPlanets().contains(target) && target.NumShips() > source.NumShips()/2.0) {
                    continue;
                }
                SimulatedPlanetWars spw = new SimulatedPlanetWars(pw, Bot.FRIENDLY);
                spw.IssueOrder(source, target);

                spw = new SimulatedPlanetWars(spw, Bot.HOSTILE);

                double score = findBestScore(spw, pm, 1);

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
                return winner == Bot.FRIENDLY ? 1 : 0;
            }
            if (depth > MAX_DEPTH) {
                return pm.calculateScore(pw);
            }
            if (pw.MyPlanets().size() <=0) {
                return 0;
            }

            double maxScore = -Double.MAX_VALUE;


            Planet source = getLargestPlanet(pw.MyPlanets());

            


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


            return maxScore;
        }

    }
}
