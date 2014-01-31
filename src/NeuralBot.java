import java.util.Arrays;
import java.util.List;


/**
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 22/01/14
 */
public class NeuralBot extends DarwinBot {

	public static final Bot[] BOTS = new Bot[]{new CompetitionBot(), new BullyBot(), new CarnageBot(), new Empty(), new CustomBot()};

	double[] weights;

	public NeuralBot() {
		int inputSize = 7;
		int hiddenSize = 9;
		int outputSize = 2*Heuristic.HEURISTICS.length+2;


		int size = inputSize*hiddenSize + hiddenSize*outputSize;
		weights= new double[size];

		for (int i = 0; i < weights.length; i++) {
//			weights[i] = Math.random();
			weights[i] = 0.0001;
		}

	}

	public NeuralBot(double[] weights) {
		this.weights = weights;
	}


	public Action getAction(PlanetWars pw) {
		int friendlyPlanetCount = pw.MyPlanets().size();
		int hostilePlanetCount = pw.EnemyPlanets().size();
		int neutralPlanetCount = pw.NeutralPlanets().size();
		int friendlyShipCount = pw.NumShips(Bot.FRIENDLY);
		int hostileShipCount = pw.NumShips(Bot.HOSTILE);
		int friendlyGrowth = SimulatedPlanetWars.totalGrowth(pw, Bot.FRIENDLY);
		int hostileGrowth = SimulatedPlanetWars.totalGrowth(pw, Bot.HOSTILE);

		int heuristicCount = Heuristic.HEURISTICS.length;

		NeuralNetwork network = new NeuralNetwork(9, heuristicCount *2+2,friendlyPlanetCount, hostilePlanetCount, neutralPlanetCount,
				friendlyShipCount, hostileShipCount, friendlyGrowth, hostileGrowth);

		network.setWeights(weights);

		double bestValue = 0;
		Heuristic bestSource = null;

		double[] result = network.getValues();

		for (int i = 0; i < heuristicCount; i++) {
			if (result[i] > bestValue) {
				bestValue = result[i];
				bestSource = Heuristic.HEURISTICS[i];
			}
		}



		bestValue = 0;
		Heuristic bestTarget = null;

		for (int i = 0; i < heuristicCount; i++) {
			if (result[i+heuristicCount] > bestValue) {
				bestValue = result[i+heuristicCount];
				bestTarget = Heuristic.HEURISTICS[i];
			}
		}

		List<Planet> planets = null;

		if(result[heuristicCount *2] > result[heuristicCount *2 + 1]){
			planets = pw.EnemyPlanets();
		} else {
			planets = pw.NeutralPlanets();
		}

		Planet source = Heuristic.select(pw.MyPlanets(), bestSource);
		Planet target = Heuristic.select(planets, bestTarget);

		return new Action(source, target);
	}


	@Override
	public DarwinBot getInstance(double... pars) {
		return new NeuralBot(pars);
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
		Bot bot = new NeuralBot(new double[]{0.0001, 0.8292, 0.2878, 0.1700, 0.9223, 0.3534, 0.6238, 0.8842, 0.0514, 0.4409, 0.6419, 0.0001, 0.4749, 0.0370, 0.6164, 0.0001, 0.3312, 0.5098, 0.1843, 0.7513, 0.0024, 0.6263, 0.7090, 0.0236, 0.8050, 0.6372, 0.6552, 0.0129, 0.5011, 0.6545, 0.3188, 0.0759, 0.2845, 0.8128, 0.1569, 0.2490, 0.2809, 0.7445, 0.2860, 0.4274, 0.5198, 0.7047, 0.8838, 0.1720, 0.2834, 0.1763, 0.7750, 0.4687, 0.3481, 0.2865, 0.0001, 0.0275, 0.7649, 0.1248, 0.1661, 0.7092, 0.6214, 0.8770, 0.9323, 0.9685, 0.0437, 0.4134, 0.5578, 0.2914, 0.0001, 0.1578, 0.0001, 0.9905, 0.9739, 0.9476, 0.8501, 0.3754, 0.5208, 0.6927, 0.6280, 0.6777, 0.2808, 0.4607, 0.9520, 0.9923, 0.9860, 0.0001, 0.0194, 0.4315, 0.5588, 0.8782, 0.8798, 0.6555, 0.9380, 0.0046, 0.9388, 0.2472, 0.2676, 0.4772, 0.2180, 0.2129, 0.8327, 0.7043, 0.4569, 0.0001, 0.5859, 0.9444, 0.0095, 0.4042, 0.9940, 0.1763, 0.2085, 0.5967, 0.8247, 0.6364, 0.6023, 0.0001, 0.1635, 0.2080, 0.8632, 0.3444, 0.2479, 0.7381, 0.4691, 0.1444, 0.7746, 0.5185, 0.4536, 0.7965, 0.0001, 0.8464, 0.4303, 0.7437, 0.1315, 0.2345, 0.0001, 0.7250, 0.4911, 0.0001, 0.9927, 0.8520, 0.3010, 0.5772, 0.6834, 0.9482, 0.8096, 0.8980, 0.1668, 0.9816, 0.2799, 0.7405, 0.0001, 0.0001, 0.1871, 0.0001, 0.0259, 0.8495, 0.6483, 0.9213, 0.0596, 0.9034, 0.0985, 0.4422, 0.3016, 0.5490, 0.2584, 0.7172, 0.3443, 0.0001, 0.0563, 0.0001, 0.8996, 0.6032, 0.4251, 0.2798, 0.0001});
		Bot.execute(bot);
	}
}
