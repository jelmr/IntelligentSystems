import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;


/**
 * Applies a genetic algorithm to find some of the best parameters for a certain bot.
 *
 * The algorithm starts with a population of bots, which will be improved through Darwin's evolution for multiple
 * generations.
 *
 * - A population of {@code POP_SIZE} bots will be randomly initialized.
 *
 * - During a generation each bot will be replaced in the following way:
 * 		1) {@code TOURNAMENT_SIZE} bots will be randomly selected. These robot will be put in a tournament population.
 * 		2) All robots in the tournament population fight each other on multiple maps. The robot with the most wins
 * 				will be considered the winner.
 * 		3) Repeat 1) and 2).
 * 		4) The two winners from the tournaments will 'mate'. Their genes will be mixed, hopefully selecting the best
 * 				of both worlds. The {@code UNIFORM_RATE} determines how many genes come from each bot.
 * 		5) With probability {@code MUTATION_RATE} a gene is replaced with a random value.
 *
 *	- {@code GENERATIONS_WITH_MUTATION} generations will be evolved.
 *
 *	- To prevent too much randomness, {@code GENERATIONS_WITHOUT_MUTATION} generations are evolved without mutations.
 *
 *	- All bots of the final population fight each other. In a same fashion as the tournaments, the best bot is selected.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 20/01/14
 */
public class GeneticAlgorithmNeural {


	public static final int POP_SIZE = 40,
							TOURNAMENT_SIZE = 7,
							GENERATIONS_WITH_MUTATION = 100,
							GENERATIONS_WITHOUT_MUTATION = 5;
	public static final double 	UNIFORM_RATE = 0.7,
								MUTATION_RATE = 0.10,
								BREEDING_RATE = 0.2;


	/**
	 * Runs the algorithm.
	 */
	public void start() {
		Population<DarwinBot> pop = fillWithNeuralBots(POP_SIZE);

//		System.out.println("Start:" + pop.toString());

		Population[] generations = new Population[GENERATIONS_WITH_MUTATION+GENERATIONS_WITHOUT_MUTATION];



		for (int i = 0; i < GENERATIONS_WITH_MUTATION; i++) {
			evolvePopulation(pop, true);

			generations[i] = pop.copy();
			System.out.println("Generation " + i + ": "+pop.getFittest().toString());


		}
		for (int i = 0; i < GENERATIONS_WITHOUT_MUTATION; i++) {
			evolvePopulation(pop, false);

			generations[GENERATIONS_WITH_MUTATION + i] = pop.copy();

			System.out.println("Generation " + i + ": "+pop.getFittest().toString());
		}

		System.out.println("Generation: " + pop.getFittest().toString());

		//reformatData(generations);

	}


	private void reformatData(Population[] generations) {
		double[][] experiment = new double[GENERATIONS_WITH_MUTATION+GENERATIONS_WITHOUT_MUTATION][55];

		//for (Population generation : generations) { // loop through generations
		for (int k = 0; k < generations.length; k++) {
			Population generation = generations[k];


			double[] generationResult = new double[55];

			for (int i = 0; i < 55; i++) { // loop through vars

				int[] bins = new int[20];

				for (int j = 0; j < generation.size(); j++) { // loop through bots
					int bin = (int) (generation.get(j).getPars()[i] / 0.05);
					bins[bin] = bins[bin] + 1;


				}

				int highestIndex = 0;
				int highestCount = 0;

				for (int j = 0; j < bins.length; j++) {
					if(bins[j] > highestCount){
						highestCount = bins[j];
						highestIndex = j;
					}
				}

				generationResult[i] = highestIndex * 0.05;

			}

			experiment[k] = generationResult;
		}
		StringBuilder s = new StringBuilder();
		s.append("\"a\",\"b\"\n");
		for (int i = 0; i < 55; i++) {

			for (int j = 0; j < experiment.length; j++) {

				s.append(String.format("\"%d\",%f,%d\n", experiment.length*i+j+1, experiment[j][i], j+1));
			}
		}

		System.out.print(s.toString());
	}


	/**
	 * Evolves the population exactly one generation.
	 * @param pop The population to evolve.
	 * @param mutate Whether or not population should mutate.
	 */
	public void evolvePopulation(Population pop, boolean mutate) {

		PriorityQueue<BotScore> pq = new PriorityQueue<BotScore>(POP_SIZE, new Comparator<BotScore>() {
			@Override
			public int compare(BotScore o1, BotScore o2) {
				return Integer.compare(o1.score, o2.score);
			}
		});

		for (int i = 0; i < pop.size(); i++) {
			DarwinBot bot = pop.get(i);
			int score = Population.getFitness(bot);
			BotScore bs = new BotScore(bot, score);
			pq.add(bs);
		}

		for (int i = 0; i < pq.size(); i++) {
			pop.set(i, pq.poll().bot);
			pop.set(i, pq.poll().bot);
		}


		for (int i = 0; i < BREEDING_RATE * POP_SIZE; i++) {
			DarwinBot a = pop.get(POP_SIZE - randomRankProportionateIndex(pop));
			DarwinBot b = pop.get(POP_SIZE - randomRankProportionateIndex(pop));
			DarwinBot newBot = mutate(crossover(a, b));
			int index = randomRankProportionateIndex(pop);
			pop.set(index, newBot);
		}
	}


	private int randomRankProportionateIndex(Population pop) {
		double[] cumulative = new double[pop.size()-1];
		cumulative[0] = 1;
		int sum = 1;
		for (int i = 1; i < cumulative.length; i++) {
			cumulative[i] = cumulative[i-1] + i + 1;
			sum += cumulative[i];
		}

		int index = Arrays.binarySearch(cumulative, Math.random()*sum);

		if (index < 0) {
			index = Math.abs(index+1);
		}

		if(index >= pop.size()){
//			System.out.println("Invalid index: "+index+"\n");
			index = pop.size() - 1;
		}

		return index;
	}


	/**
	 * Applies a mutation to the bot with a certain probability.
	 * @param bot The bot to mutate.
	 * @return The mutated bot.
	 */
	private static DarwinBot mutate(DarwinBot bot) {
		double[] pars = bot.getPars();

		for (int i = 0; i < pars.length; i++) {
			pars[i] = (Math.random() > MUTATION_RATE ? pars[i] : Math.random());
		}

		bot.setPars(pars);

		return bot;
	}


	/**
	 * Combines two bots into one bot. Genes will be selected from both bots.
	 *
	 * @param a The first parent.
	 * @param b The second parent.
	 * @return The newly created bot.
	 */
	private static DarwinBot crossover(DarwinBot a, DarwinBot b) {
		double[] aPars = a.getPars();
		double[] bPars = b.getPars();
		double[] newPars = new double[aPars.length];

		for (int i = 0; i < newPars.length; i++) {
			newPars[i] = (Math.random() < UNIFORM_RATE ? aPars[i] : bPars[i]);
		}

		return a.getInstance(newPars);
	}


	/**
	 * Selects a certain amount of bots randomly and selects the best bot of this population.
	 *
	 * @param pop The base population.
	 * @return The best bot of the randomly selected population.
	 */
	private static DarwinBot tournamentSelect(Population pop) {
		Population<DarwinBot> tournament = new Population<DarwinBot>();

		for (int i = 0; i < TOURNAMENT_SIZE; i++) {
			DarwinBot random = pop.get((int) (Math.random()*pop.size()));
			tournament.add(random);
		}

		return tournament.getFittest();
	}


	/**
	 * Initializes the population with random HeuristicBots.
	 * @param popSize The amount of HeuristicBots in the new population.
	 * @return The new population.
	 */
	private Population<DarwinBot> fillWithHeuristicBots(int popSize) {
		Population<DarwinBot> pop = new Population<DarwinBot>();

		for (int i = 0; i < popSize; i++) {
			pop.add(new HeuristicBot());
		}

		return pop;
	}


	private Population<DarwinBot> fillWithNeuralBots(int popSize) {
		Population<DarwinBot> pop = new Population<DarwinBot>();

		for (int i = 0; i < popSize; i++) {
			pop.add(new NeuralBot());
		}
		return pop;
	}


	public static void main(String[] args) {
		new GeneticAlgorithmNeural().start();
	}

	private class BotScore {

		int score;
		DarwinBot bot;


		public BotScore(DarwinBot bot, int score) {
			this.score = score;
			this.bot = bot;
		}

	}

}
