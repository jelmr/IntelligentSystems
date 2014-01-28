/**
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 27/01/14
 */
public class BeamSearchBot extends Bot {

	public static final int BEAM_WIDTH = 1;
	public static final int MAX_DEPTH = 2;


	public static int simulate(SimulatedPlanetWarsParallel spw, int depth) {

		int turnsLeft = 100 - depth;
		int winner = -1;

		Bot a = new CompetitionBot();
		Bot b = new CompetitionBot();

		int turns = 0;

		while (winner == -1 && turnsLeft-- > 0) {
			turns++;

			spw.player = Bot.FRIENDLY;
			Action aAction = a.getAction(spw);
			spw.player = Bot.HOSTILE;
			Action bAction = b.getAction(spw);

			if (aAction != null && aAction.isValid()) {
				spw.player = Bot.FRIENDLY;
				spw.IssueOrder(aAction);
			}

			if (bAction != null && bAction.isValid()) {
				spw.player = Bot.HOSTILE;
				spw.IssueOrder(bAction);
			}

			winner = spw.Winner();
		}

		if (winner == Bot.FRIENDLY) { // a wins

			return turns;
		} else if (winner == Bot.HOSTILE) {
			return 102;
		} else {
			return 101;
		}
	}


	@Override
	public Action getAction(PlanetWars pw) {

		SimulatedPlanetWarsParallel spw = new SimulatedPlanetWarsParallel(pw);
		spw.player = Bot.HOSTILE;
		Action enemyAction = new CompetitionBot().getAction(spw);

		spw.player = Bot.FRIENDLY;

		Action[] actions = new Action[BEAM_WIDTH];

//		System.out.printf("%s\n", new CompetitionBot().getAction(spw).toString());

		for (int i = 0; i < BEAM_WIDTH; i++) {
			SimulatedPlanetWarsParallel newspw = new SimulatedPlanetWarsParallel(spw);
			CompetitionBot friendlyBot = new CompetitionBot();
			Action action = friendlyBot.getAction(newspw);
			action.target.AddShips(10000);
			actions[i] = action;
		}

		for (Action action : actions) {
			action.target.RemoveShips(10000);
		}

		int bestScore = Integer.MAX_VALUE;
		Action bestAction = null;


		for (Action action : actions) {
//			System.out.printf("Actions: %s\n", action.toString());
			SimulatedPlanetWarsParallel newspw = new SimulatedPlanetWarsParallel(spw);
			newspw.player = Bot.FRIENDLY;
			newspw.IssueOrder(action.getAction());
			newspw.player = Bot.HOSTILE;
			newspw.IssueOrder(enemyAction.getAction());
			newspw.player = Bot.FRIENDLY;

			int score = recursiveBeamSearch(newspw, 0);

			if(score < bestScore){
				bestScore = score;
				bestAction = action.getAction();
			}

		}

		return bestAction;
	}


	// 102 als geen win
	// 101 als draw
	// anders aantal turns tot win
	private int recursiveBeamSearch(SimulatedPlanetWarsParallel pw, int depth) {

		int winner = pw.Winner();
		if(winner == 0){
			return 101;
		} else if (winner == 1){
			return depth;
		} else if (winner == 2) {
			return 102;
		}

		if (depth > MAX_DEPTH) {

			return simulate(pw, depth);

		}

		SimulatedPlanetWarsParallel spw = new SimulatedPlanetWarsParallel(pw);
		CompetitionBot competitionBot = new CompetitionBot();
		Action enemyAction = competitionBot.getAction(spw);

		Action[] actions = new Action[BEAM_WIDTH];
		int actionCounter = 0;

		for (int i = 0; i < BEAM_WIDTH; i++) {
			Action action = competitionBot.getAction(spw);
			actions[actionCounter++] = action;
			action.target.AddShips(10000);
		}

		for (int i = 0; i < actions.length; i++) {
			actions[i].target.AddShips(-10000);
		}

		int bestScore = Integer.MAX_VALUE;
		Action bestAction = null;

		for (int i = 0; i < actions.length; i++) {
			SimulatedPlanetWarsParallel newspw = new SimulatedPlanetWarsParallel(spw);
			Action action = actions[i];
			newspw.player = Bot.FRIENDLY;
			newspw.IssueOrder(action);
			newspw.player = Bot.HOSTILE;
			newspw.IssueOrder(enemyAction);

			int score = recursiveBeamSearch(newspw, depth + 1);
			if (score < bestScore) {
				bestScore = score;
			}
		}

		return bestScore;
	}


	public static void main(String[] args) {
		Bot bot = new BeamSearchBot();
		Bot.execute(bot);
	}

}
