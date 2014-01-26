import java.util.Arrays;
import java.util.List;


/**
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 22/01/14
 */
public class NeuralBot extends DarwinBot {

	public static final Bot[] BOTS = new Bot[]{new Empty(), new BullyBot(), new Empty(), new Empty(), new CustomBot()};

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
		Bot bot = new NeuralBot(new double[]{0.9961, 0.0300, 0.9059, 0.7872, 0.4339, 0.0945, 0.6586, 0.6265, 0.0886, 0.9833, 0.1598, 0.7338, 0.8572, 0.9115, 0.1347, 0.2708, 0.9250, 0.4037, 0.6642, 0.4799, 0.0338, 0.3025, 0.1848, 0.7274, 0.2315, 0.6817, 0.6211, 0.2298, 0.5599, 0.7259, 0.8959, 0.2467, 0.1002, 0.5270, 0.8404, 0.2225, 0.9746, 0.4265, 0.9781, 0.6161, 0.9297, 0.2685, 0.3532, 0.4618, 0.7918, 0.8042, 0.3784, 0.4803, 0.8190, 0.2590, 0.7122, 0.7529, 0.4888, 0.8169, 0.0769});
		Bot.execute(bot);
	}
}
