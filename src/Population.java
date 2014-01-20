import java.util.ArrayList;
import java.util.List;


/**
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 20/01/14
 */
public class Population<T extends Bot> {

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


	Population() {
		bots = new ArrayList<T>();
	}

	public void add(T t){
		bots.add(t) ;
	}

	public void set(int i, T t){
		bots.set(i, t);
	}

	public T get(int i){
		return bots.get(i);
	}

	public T getFittest(){

		// Not optimal. In development...

		T best = null;
		int bestScore = Integer.MIN_VALUE;

		for (T a : bots) {

			int aScore = 0;

			for (T b : bots) {
				if(a != b){
					for (String map : MAP_SELECTION) {
						aScore += simulate(a,b,map);
					}

				}
			}

			if (aScore > bestScore) {
				bestScore = aScore;
				best = a;
			}

		}

		return (T) best;
	}

	// Returns 0 if b wins. 1 if draw. 2 if a wins.
	private int simulate(T a, T b, String map) {
		SimulatedPlanetWarsParallel spw = new SimulatedPlanetWarsParallel(map);

		int turnsLeft = MAX_TURNS;
		int winner;

		Bot temp = new CustomBot();

		while((winner = spw.Winner()) == -1 && turnsLeft-- > 0){
			spw.IssueOrder(a.getAction(spw));
			if((winner = spw.Winner()) == -1){
				spw.IssueOrder(temp.getAction(spw));
			}
		}

		if(winner == Bot.FRIENDLY){ // a wins
			return 2;
		} else if(winner == Bot.HOSTILE){
			return 0;
		} else  {
			return 0;
		}
	}


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
