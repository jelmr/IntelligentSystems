import java.util.ArrayList;

/**
 * Currently a copy of CustomBot. Should be changed later.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 * Date: 11/01/2014
 */

public class AlphaBetaBot extends Bot{

	@Override
	public Action getAction(PlanetWars pw) {
		return (new AlphaBetaPruning()).findBest(pw, PerformanceMeasure.MOST_SHIPS);
	}


	public static void main(String[] args) {
		Bot bot = new AlphaBetaBot();
		Bot.execute(bot);
	}


	static class AlphaBetaPruning extends Search {

		public static final int MAX_DEPTH = 4;

        private double alphabeta(SimulatedPlanetWarsParallel spw, PerformanceMeasure pm, int depth, double alpha, double beta) {


            int winner = spw.Winner();
            if (winner != -1) {
                return winner == FRIENDLY ? 1 : 0;
            }

            if (depth >= MAX_DEPTH) {
                //spw = new SimulatedPlanetWars(spw, Bot.FRIENDLY);
                return pm.calculateScore(spw);

            }

            if (spw.player == Bot.FRIENDLY) {

                ArrayList<State> permutations = State.getStatePermutations(new State(spw));
                for (State state : permutations) {
                    SimulatedPlanetWarsParallel newspw = (SimulatedPlanetWarsParallel)state.getPlanetWars();
                    newspw.player=newspw.notPlayer();
                    double score = alphabeta(newspw, pm, depth + 1, alpha, beta);
                    alpha = alpha > score ? alpha : score;

                    if (beta<=alpha) {

                        break;
                    }

                }
                return alpha;

            } else {

                ArrayList<State> permutations = State.getStatePermutations(new State(spw));
                for (State state : permutations) {
                    SimulatedPlanetWarsParallel newspw = (SimulatedPlanetWarsParallel)state.getPlanetWars();
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
            return new Action(bestSource, bestTarget);
        }




	}
}
