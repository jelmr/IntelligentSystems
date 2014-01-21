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
public class GeneticAlgorithm {


	public static final int POP_SIZE = 50,
							TOURNAMENT_SIZE = 8,
							GENERATIONS_WITH_MUTATION = 20,
							GENERATIONS_WITHOUT_MUTATION = 4;
	public static final double 	UNIFORM_RATE = 0.5,
								MUTATION_RATE = 0.20;


	/**
	 * Runs the algorithm.
	 */
	public void start() {
		Population<DarwinBot> pop = fillWithHeuristicBots(POP_SIZE);

		System.out.println("Start:" + pop.toString());

		for (int i = 0; i < GENERATIONS_WITH_MUTATION; i++) {
			evolvePopulation(pop, true);
			System.out.println("Generation " + i + ": " + pop.toString());
		}
		for (int i = 0; i < GENERATIONS_WITHOUT_MUTATION; i++) {
			evolvePopulation(pop, false);
			System.out.println("Generation " + i + ": " + pop.toString());
		}

		System.out.println("Generation: " + pop.getFittest().toString());

	}


	/**
	 * Evolves the population exactly one generation.
	 * @param pop The population to evolve.
	 * @param mutate Whether or not population should mutate.
	 */
	public static void evolvePopulation(Population pop, boolean mutate) {

		for (int i = 0; i < pop.size(); i++) {
			DarwinBot a = tournamentSelect(pop);
			DarwinBot b = tournamentSelect(pop);
			DarwinBot newBot = crossover(a, b);
			if(mutate){
				newBot = mutate(newBot);
			}
			pop.set(i, newBot);
		}
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
			newPars[i] = (Math.random() > UNIFORM_RATE ? aPars[i] : bPars[i]);
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
			HeuristicBot random = (HeuristicBot) pop.get((int) (Math.random()*pop.size()));
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


	public static void main(String[] args) {
		new GeneticAlgorithm().start();
	}
}
