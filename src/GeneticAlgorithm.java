/**
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 20/01/14
 */
public class GeneticAlgorithm {

	public static final int POP_SIZE = 50;
	public static final int TOURNAMENT_SIZE = 5;
	public static final double UNIFORM_RATE = 0.5;
	public static final double MUTATION_RATE = 0.075;


	public void start() {
		Population<HeuristicBot> pop = initPopulation();

		System.out.println("Start:"+pop.toString());

		for (int i = 0; i < 10; i++) {
			evolvePopulation(pop);
			System.out.println("Generation "+i+": "+pop.toString());
		}

		System.out.println("Generation: "+pop.getFittest().toString());

	}


	public static void evolvePopulation(Population pop) {

		for (int i = 0; i < pop.size(); i++) {
			HeuristicBot a = tournamentSelect(pop);
			HeuristicBot b = tournamentSelect(pop);
			HeuristicBot newBot = mutate(crossover(a, b));
			pop.set(i, newBot);
		}


	}


	private static HeuristicBot mutate(HeuristicBot bot) {
		bot.heuristicsA = (Math.random() > MUTATION_RATE ? bot.heuristicsA : HeuristicBot.randomHeuristic());
		bot.heuristicsB = (Math.random() > MUTATION_RATE ? bot.heuristicsB : HeuristicBot.randomHeuristic());
		bot.p = (Math.random() > MUTATION_RATE ? bot.p : Math.random());
		return bot;
	}


	private static HeuristicBot crossover(HeuristicBot a, HeuristicBot b) {
		int heuristicA, heuristicB;
		double p;

		heuristicA = Math.random() > UNIFORM_RATE ? a.heuristicsA : b.heuristicsA;
		heuristicB = Math.random() > UNIFORM_RATE ? a.heuristicsB : b.heuristicsB;
		p = Math.random() > UNIFORM_RATE ? a.p : b.p;

		return new HeuristicBot(heuristicA, heuristicB, p);
	}




	private static HeuristicBot tournamentSelect(Population pop) {
		Population tournament = new Population();

		for (int i = 0; i < TOURNAMENT_SIZE; i++) {
			HeuristicBot random = (HeuristicBot) pop.get((int) (Math.random()*pop.size()));
			tournament.add(random);
		}

		return (HeuristicBot) tournament.getFittest();
	}



	private Population initPopulation() {
		Population pop = new Population();

		for (int i = 0; i < POP_SIZE; i++) {
			pop.add(new HeuristicBot());
		}

		return pop;
	}


	public static void main(String[] args) {
		new GeneticAlgorithm().start();
	}
}
