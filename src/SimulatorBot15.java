/**
 * A bot that sends ships from its biggest planet (by shipcount) to an hostile planet with the most favorable GrowthRate / ships lost ratio.
 *
 * @author Jelmer Mulder
 * @author Sebastian Osterlund
 * @author Yoran Sturkenboom
 * Date: 11/01/2014
 */


public class SimulatorBot15 extends Bot15 {

	int turnsPlayed = 0;
	public Action15 getAction(PlanetWars15 pw) {

		turnsPlayed++;
		//Planet15 source = Heuristic15.select(pw.MyPlanets(), Heuristic15.MOST_SHIPS);
		//Planet15 target = Heuristic15.select(pw.EnemyPlanets(), Heuristic15.BEST_GENERATION_PER_SHIPS_LOST);

		Action15 action = getFastestWinningAction(new SimulatedPlanetWarsParallel15(pw));
		if (action == null) {
			action = new CompetitionBot15().getAction(pw);
		}
		return action;
	}

	public Action15 getFastestWinningAction(SimulatedPlanetWarsParallel15 pw) {
		Action15 bestAction = null;
		int bestScore = Integer.MAX_VALUE;

		for (Planet15 source: pw.MyPlanets()) {

			for (Planet15 target: pw.Planets()) {
				Action15 action = new Action15(source,target);
				SimulatedPlanetWarsParallel15 spw = new SimulatedPlanetWarsParallel15(pw);
				spw.IssueOrder(action);
				spw.player = spw.notPlayer();
				spw.IssueOrder(new CompetitionBot15().getAction(spw));
				spw.player = Bot15.FRIENDLY;
				int score = calculateScore(spw);
				if (score < bestScore) {
					bestAction = action;
					bestScore = score;
				}

			}

		}
		return bestAction;

	}


	int calculateScore(SimulatedPlanetWarsParallel15 pw) {
		for (int i = 0; i < 100-turnsPlayed; i++) {
			if (pw.Winner()== Bot15.FRIENDLY) {
				return i;
			} else if (pw.Winner() == Bot15.HOSTILE) {
				return 102;
			}
			pw.player = Bot15.FRIENDLY;
			pw.IssueOrder(new CompetitionBot15().getAction(pw));
			pw.player = Bot15.HOSTILE;
			pw.IssueOrder(new CompetitionBot15().getAction(pw));
		}
		return 101;
	}

	public static void main(String[] args) {
		Bot15 bot = new SimulatorBot15();
		Bot15.execute(bot);
	}


}