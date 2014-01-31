/**
 * A bot that sends ships from its biggest planet (by shipcount) to an hostile planet with the most favorable GrowthRate / ships lost ratio.
 *
 * @author Jelmer Mulder
 * @author Sebastian Osterlund
 * @author Yoran Sturkenboom
 * Date: 11/01/2014
 */


public class Beam extends Bot {

	int turnsPlayed = 0;
	public Action getAction(PlanetWars pw) {

		turnsPlayed++;
		//Planet source = Heuristic.select(pw.MyPlanets(), Heuristic.MOST_SHIPS);
		//Planet target = Heuristic.select(pw.EnemyPlanets(), Heuristic.BEST_GENERATION_PER_SHIPS_LOST);

		Action action = getFastestWinningAction(new SimulatedPlanetWarsParallel(pw));
		if (action == null) {
			action = new CompetitionBot().getAction(pw);
		}
		return action;
	}

	public Action getFastestWinningAction(SimulatedPlanetWarsParallel pw) {
		Action bestAction = null;
		int bestScore = Integer.MAX_VALUE;

		for (Planet source: pw.MyPlanets()) {

			for (Planet target: pw.Planets()) {
				Action action = new Action(source,target);
				SimulatedPlanetWarsParallel spw = new SimulatedPlanetWarsParallel(pw);
				spw.IssueOrder(action);
				spw.player = spw.notPlayer();
				spw.IssueOrder(new CompetitionBot().getAction(spw));
				spw.player = Bot.FRIENDLY;
				int score = calculateScore(spw);
				if (score < bestScore) {
					bestAction = action;
					bestScore = score;
				}

			}

		}
		return bestAction;

	}


	int calculateScore(SimulatedPlanetWarsParallel pw) {
		for (int i = 0; i < 100-turnsPlayed; i++) {
			if (pw.Winner()== Bot.FRIENDLY) {
				return i;
			} else if (pw.Winner() == Bot.HOSTILE) {
				return 102;
			}
			pw.player = Bot.FRIENDLY;
			pw.IssueOrder(new CompetitionBot().getAction(pw));
			pw.player = Bot.HOSTILE;
			pw.IssueOrder(new CompetitionBot().getAction(pw));
		}
		return 101;
	}

	public static void main(String[] args) {
		Bot bot = new Beam();
		Bot.execute(bot);
	}


}