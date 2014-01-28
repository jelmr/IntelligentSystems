/**
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 26/01/14
 */
public class Benchmark {

	public static final Bot[] BOTS = {new LookaheadBot(), new BeamBot(), new BullyBot(), new CarnageBot(), new CompetitionBot() ,
			new NeuralBot(new double[]{0.0001, 0.8292, 0.2878, 0.1700, 0.9223, 0.3534, 0.6238, 0.8842, 0.0514, 0.4409, 0.6419, 0.0001, 0.4749, 0.0370, 0.6164, 0.0001, 0.3312, 0.5098, 0.1843, 0.7513, 0.0024, 0.6263, 0.7090, 0.0236, 0.8050, 0.6372, 0.6552, 0.0129, 0.5011, 0.6545, 0.3188, 0.0759, 0.2845, 0.8128, 0.1569, 0.2490, 0.2809, 0.7445, 0.2860, 0.4274, 0.5198, 0.7047, 0.8838, 0.1720, 0.2834, 0.1763, 0.7750, 0.4687, 0.3481, 0.2865, 0.0001, 0.0275, 0.7649, 0.1248, 0.1661, 0.7092, 0.6214, 0.8770, 0.9323, 0.9685, 0.0437, 0.4134, 0.5578, 0.2914, 0.0001, 0.1578, 0.0001, 0.9905, 0.9739, 0.9476, 0.8501, 0.3754, 0.5208, 0.6927, 0.6280, 0.6777, 0.2808, 0.4607, 0.9520, 0.9923, 0.9860, 0.0001, 0.0194, 0.4315, 0.5588, 0.8782, 0.8798, 0.6555, 0.9380, 0.0046, 0.9388, 0.2472, 0.2676, 0.4772, 0.2180, 0.2129, 0.8327, 0.7043, 0.4569, 0.0001, 0.5859, 0.9444, 0.0095, 0.4042, 0.9940, 0.1763, 0.2085, 0.5967, 0.8247, 0.6364, 0.6023, 0.0001, 0.1635, 0.2080, 0.8632, 0.3444, 0.2479, 0.7381, 0.4691, 0.1444, 0.7746, 0.5185, 0.4536, 0.7965, 0.0001, 0.8464, 0.4303, 0.7437, 0.1315, 0.2345, 0.0001, 0.7250, 0.4911, 0.0001, 0.9927, 0.8520, 0.3010, 0.5772, 0.6834, 0.9482, 0.8096, 0.8980, 0.1668, 0.9816, 0.2799, 0.7405, 0.0001, 0.0001, 0.1871, 0.0001, 0.0259, 0.8495, 0.6483, 0.9213, 0.0596, 0.9034, 0.0985, 0.4422, 0.3016, 0.5490, 0.2584, 0.7172, 0.3443, 0.0001, 0.0563, 0.0001, 0.8996, 0.6032, 0.4251, 0.2798, 0.0001})};
	public static final String[] MAPS = {
			"tools/maps/3planets/map1.txt",
			"tools/maps/3planets/map2.txt",
			"tools/maps/3planets/map3.txt",
			"tools/maps/4planets/map1.txt",
			"tools/maps/4planets/map2.txt",
			"tools/maps/4planets/map3.txt",
			"tools/maps/5planets/map1.txt",
			"tools/maps/5planets/map2.txt",
			"tools/maps/5planets/map3.txt",
			"tools/maps/6planets/map1.txt",
			"tools/maps/6planets/map2.txt",
			"tools/maps/6planets/map3.txt",
			"tools/maps/7planets/map1.txt",
			"tools/maps/7planets/map2.txt",
			"tools/maps/7planets/map3.txt",
			"tools/maps/8planets/map1.txt",
			"tools/maps/8planets/map2.txt",
			"tools/maps/8planets/map3.txt",
			"tools/maps/larger/map1.txt",
			"tools/maps/larger/map10.txt",
			"tools/maps/larger/map11.txt",
			"tools/maps/larger/map12.txt",
			"tools/maps/larger/map2.txt",
			"tools/maps/larger/map3.txt",
			"tools/maps/larger/map4.txt",
			"tools/maps/larger/map5.txt",
			"tools/maps/larger/map6.txt",
			"tools/maps/larger/map7.txt",
			"tools/maps/larger/map8.txt",
			"tools/maps/larger/map9.txt"
	};


	public static void main(String[] args) {
		new Benchmark().start();
	}


	private void start() {
		int totalCounter = 0;

		System.out.printf("\"mapsize\",\"Bot\",\"Wins\",\"Draws\",\"Loses\"\n");

		for (int i = 0; i < BOTS.length; i++) {
			int[] mapSizeTotal = new int[100];
			int[] mapSizeWins= new int[100];
			int[] mapSizeDraws = new int[100];
			int[] mapSizeLoses = new int[100];

			int winCount = 0;
			int drawCount = 0;
			int lossCount = 0;

			for (int j = 0; j < MAPS.length; j++) {
				SimulatedPlanetWarsParallel spw = new SimulatedPlanetWarsParallel(MAPS[j]);
				for (int k = 0; k < BOTS.length; k++) {
					if (i == k) { continue;}
					SimulatedPlanetWarsParallel newspw = new SimulatedPlanetWarsParallel(spw);
					int score1 = simulate(BOTS[i], BOTS[k], newspw);

					if(score1 == 0){
						lossCount ++;
					} else if(score1 == 1){
						drawCount ++;
					} else {
						winCount++;
					}

					newspw = new SimulatedPlanetWarsParallel(spw);
					int score2 = 2 - simulate(BOTS[k], BOTS[i], newspw);

					if(score2 == 0){
						lossCount ++;
					} else if(score2 == 1){
						drawCount ++;
					} else {
						winCount++;
					}

						mapSizeTotal[newspw.Planets().size()] = mapSizeTotal[newspw.Planets().size()] + 2;

				}
				mapSizeWins[spw.Planets().size()] += winCount;
				mapSizeDraws[spw.Planets().size()] += drawCount;
				mapSizeLoses[spw.Planets().size()] += lossCount;

			}

			double totalMatches = MAPS.length * (BOTS.length - 1) * 2;
			double wins = winCount / totalMatches;
			double draws = drawCount / totalMatches;
			double loses = lossCount / totalMatches;
			System.out.printf("\"%d\",%s,%.2f, %.2f, %.2f\n", totalCounter++, BOTS[i].getClass().getSimpleName(), wins, draws, loses);


		}

	}


	public static int simulate(Bot a, Bot b, SimulatedPlanetWarsParallel spw) {

		int turnsLeft = 100;
		int winner = -1;

		while (winner == -1 && turnsLeft-- > 0) {

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

			return 2;
		} else if (winner == Bot.HOSTILE) {
			return 0;
		} else {
			return 1;
		}
	}

}
