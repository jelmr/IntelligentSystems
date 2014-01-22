import java.util.ArrayList;
import java.util.List;


/**
 * Class which contains a Population of bots. Used for the genetic algorithm.
 *
 * @param <T> Type of the bots in the population.
 *
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 20/01/14
 */
public class Population<T extends DarwinBot> {

	/**
	 * The maps on which the robots are tested.
	 */
	public static final String[] MAP_SELECTION = {	"tools/maps/5planets/map1.txt",
													"tools/maps/5planets/map2.txt",
													"tools/maps/6planets/map1.txt",
													"tools/maps/6planets/map3.txt",
													"tools/maps/7planets/map2.txt",
													"tools/maps/7planets/map3.txt",
													"tools/maps/8planets/map1.txt",
													"tools/maps/8planets/map3.txt",
													"tools/maps/larger/map1.txt",
													"tools/maps/larger/map3.txt",
													"tools/maps/larger/map5.txt",
													"tools/maps/larger/map7.txt",
													"tools/maps/larger/map9.txt"
													};


	public static final int DEFAULT_MAX_BOTS = 20;
	public static final int MAX_TURNS = 30;


	private List<T> bots;


	/**
	 * Creates a new empty population.
	 */
	Population() {
		bots = new ArrayList<T>();
	}


	/**
	 * Adds a bot to the population.
	 *
	 * @param t The bot to add to the population.
	 */
	public void add(T t){
		bots.add(t) ;
	}


	/**
	 * Replace the bot in position i with t.
	 *
	 * @param i The position of the bot to replace.
	 * @param t The bot to replace the bot on position i with.
	 */
	public void set(int i, T t){
		bots.set(i, t);
	}


	/**
	 * Returns the bot on position i.
	 *
	 * @param i The position of the bot to return.
	 * @return The bot on position i.
	 */
	public T get(int i){
		return bots.get(i);
	}


	/**
	 * Gets the best bot of a population. Every bot is matched with every other bot on multiple maps.
	 * The bot with most total wins will be crowned winner.
	 *
	 * @return The best bot of the population.
	 */
	public T getFittest(){

		// Not optimal. In development...

		T best = null;
		int bestScore = Integer.MIN_VALUE;

		for (T a : bots) {

			int aScore = 0;

//			for (T b : bots) {
//				if(a != b){
//					for (String map : MAP_SELECTION) {
//						aScore += simulate(a,b,map);
//					}
//
//				}
//			}
			for (String map : MAP_SELECTION) {aScore += simulate(a,new CustomBot(),map);}
			for (String map : MAP_SELECTION) {aScore += simulate(a,new BullyBot(),map);}
			for (String map : MAP_SELECTION) {aScore += simulate(a,new CarnageBot(),map);}



			if (aScore > bestScore) {
				bestScore = aScore;
				best = a;
			}

		}

		return best;
	}


	/**
	 * Simulates a match between bot a and bot b on map map. Parallel game type.
	 *
	 * @param a The first bot.
	 * @param b The second bot.
	 * @param map The map to play the match on.
	 * @return 	2: bot a won.
	 * 			1: draw
	 * 			0: bot a lost.
	 */
	private int simulate(Bot a, Bot b, String map) {
		SimulatedPlanetWarsParallel spw = new SimulatedPlanetWarsParallel(map);

		int turnsLeft = MAX_TURNS;
		int winner;


		while((winner = spw.Winner()) == -1 && turnsLeft-- > 0){
			Action actionA = a.getAction(spw);
			Action actionB = b.getAction(spw);

			spw.IssueOrder(actionA);
			winner = spw.Winner();
			if(winner == -1){
				spw.IssueOrder(actionB);
			}
		}

		if(winner == Bot.FRIENDLY){ // a wins
			return 2;
		} else if(winner == Bot.HOSTILE){
			return 0;
		} else  {
			return 1;
		}
	}


	/**
	 * Returns the amount of robots in the population.
	 *
	 * @return The amount of robots in the population.
	 */
	public int size() {
		return bots.size();
	}



	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();

		for (T bot : bots) {
			s.append(bot.toString()).append("\n");
		}

		return s.toString();

	}
}
