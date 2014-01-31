import java.util.ArrayList;

/**
 * Currently a copy of CustomBot15. Should be changed later.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 * Date: 11/01/2014
 */

public class AlphaBetaBot15 extends Bot15 {

	@Override
	public Action15 getAction(PlanetWars15 pw) {
		return (new AlphaBetaPruning()).findBest(pw, PerformanceMeasure15.MOST_SHIPS);
	}


	public static void main(String[] args) {
		Bot15 bot = new AlphaBetaBot15();
		Bot15.execute(bot);
	}


	static class AlphaBetaPruning extends Search15 {

		public static final int MAX_DEPTH = 3;

        private double alphabeta(SimulatedPlanetWarsParallel15 spw, PerformanceMeasure15 pm, int depth, double alpha, double beta) {


            int winner = spw.Winner();
            if (winner != -1) {
                return winner == FRIENDLY ? 1 : 0;
            }

            if (depth >= MAX_DEPTH) {
                //spw = new SimulatedPlanetWars15(spw, Bot15.FRIENDLY);
                return pm.calculateScore(spw);

            }

            if (spw.player == Bot15.FRIENDLY) {

                ArrayList<State15> permutations = State15.getStatePermutations(new State15(spw));
                for (State15 state : permutations) {
                    SimulatedPlanetWarsParallel15 newspw = (SimulatedPlanetWarsParallel15)state.getPlanetWars();
                    newspw.player=newspw.notPlayer();
                    double score = alphabeta(newspw, pm, depth + 1, alpha, beta);
                    alpha = alpha > score ? alpha : score;

                    if (beta<=alpha) {

                        break;
                    }

                }
                return alpha;

            } else {

                ArrayList<State15> permutations = State15.getStatePermutations(new State15(spw));
                for (State15 state : permutations) {
                    SimulatedPlanetWarsParallel15 newspw = (SimulatedPlanetWarsParallel15)state.getPlanetWars();
                    newspw.player=newspw.notPlayer();
                    double score = alphabeta(newspw, pm, depth + 1, alpha, beta);
                    beta = beta < score ? beta : score;

                    if (beta<=alpha) {

                        break;
                    }

                }
                return beta;

            }
        }

        @Override
        public Action15 findBest(PlanetWars15 pw, PerformanceMeasure15 pm) {
            Planet15 bestSource = null;
            Planet15 bestTarget = null;
            double maxScore = -Double.MAX_VALUE;

            for (Planet15 source : pw.MyPlanets()) {

                if (source.NumShips() <= 1) {continue;}

                for (Planet15 target : pw.Planets()) {
                    SimulatedPlanetWarsParallel15 spw = new SimulatedPlanetWarsParallel15(pw, FRIENDLY);
                    spw.IssueOrder(source, target);

                    spw = new SimulatedPlanetWarsParallel15(spw, HOSTILE);

                    double score = alphabeta(spw, pm, 1, -Double.MAX_VALUE, Double.MAX_VALUE);
//                    pw.log(score);

                    if (score > maxScore) {
                        bestSource = source;
                        bestTarget = target;
                        maxScore = score;
                    }

                }
            }
//            pw.log("Max: ");
//            pw.log(maxScore);
            return new Action15(bestSource, bestTarget);
        }




	}
}
