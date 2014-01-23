import java.util.Arrays;


/**
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 22/01/14
 */
public class NeuralBot extends DarwinBot {

	public static final Bot[] BOTS = new Bot[]{new CustomBot(), new BullyBot(), new RandomBot(), new CarnageBot()};

	double[] weights;

	public NeuralBot() {
		weights= new double[55];

		for (int i = 0; i < weights.length; i++) {
			weights[i] = Math.random();
		}

	}

	public NeuralBot(double[] weights) {
		this.weights = weights;
	}


	public Bot getBot(NeuralNetwork network) {
		double bestValue = 0;
		Bot bestBot = null;

		double[] result = network.getValues();

		for (int i = 0; i < result.length; i++) {
			if (result[i] > bestValue) {
				bestValue = result[i];
				bestBot = BOTS[i];
			}
		}

		return bestBot;
	}


	public Action getAction(PlanetWars pw) {
		int friendlyPlanetCount = pw.MyPlanets().size();
		int hostilePlanetCount = pw.EnemyPlanets().size();
		int neutralPlanetCount = pw.NeutralPlanets().size();
		int friendlyShipCount = pw.NumShips(Bot.FRIENDLY);
		int hostileShipCount = pw.NumShips(Bot.HOSTILE);
		int friendlyGrowth = SimulatedPlanetWars.totalGrowth(pw, Bot.FRIENDLY);
		int hostileGrowth = SimulatedPlanetWars.totalGrowth(pw, Bot.HOSTILE);




		NeuralNetwork network = new NeuralNetwork(friendlyPlanetCount, hostilePlanetCount, neutralPlanetCount,
				friendlyShipCount, hostileShipCount, friendlyGrowth, hostileGrowth);

		network.setWeights(weights);

		Bot bot = getBot(network);

//		pw.log(bot.getClass().getSimpleName());

		return bot.getAction(pw);
	}


	@Override
	public DarwinBot getInstance(double... pars) {
		return new NeuralBot(pars);
	}


	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();

		s.append("{");
		for (double weight : weights) {
			s.append(String.format("%.4f, ",weight));
		}
		s.append("}\n");
		return s.toString();

	}


	@Override
	public void setPars(double... pars) {
		weights = pars;
	}


	@Override
	public double[] getPars() {
		return weights;
	}


	@Override
	public DarwinBot copy() {
		double[] copy = new double[weights.length];
		System.arraycopy(weights, 0, copy, 0, weights.length);
		return new NeuralBot(copy);
	}


	public static void main(String[] args) {
		Bot bot = new NeuralBot(new double[]{1,1,1,1,1,1,1,1,1,1, 0.9659, 0.1096, 0.8953, 0.0432, 0.2511, 0.5400, 0.5201, 0.2541, 0.2858, 0.2845, 0.6927, 0.0530, 0.7339, 0.5197, 0.5272, 0.8944, 0.2783, 0.6400, 0.8318, 0.5913, 0.6801, 0.2244, 0.9466, 0.7718, 0.0746, 0.6233, 0.1049, 0.6994, 0.3722, 0.8646, 0.4845, 0.8313, 0.2257, 0.7355, 0.0361, 0.6105, 0.2395, 0.9302, 0.9992, 0.9565, 0.5827, 0.9995, 0.4525, 0.8264, 0.6573});
		Bot.execute(bot);
	}
}
