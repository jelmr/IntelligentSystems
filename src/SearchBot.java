import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/*
 RandomBot - an example bot that picks up one of his planets and send half of the ships
 from that planet to a random target planet.

 Not a very clever bot, but showcases the functions that can be used.
 Overcommented for educational purposes.
 f`*/
public class SearchBot {

	/**
	 * Piece of shit...
	 */



	public static final int NEUTRAL = 1,
			FRIENDLY = 1,
			ENEMY = 2;

	public static final double ENEMY_RELATIVE_IMPORTANCE = 2.5, // tweak with neural network?
			FRIEND_RELATIVE_IMPORTANCE = 0.5,
			NEUTRAL_RELATIVE_IMPORTANCE = 1;

	public static final int MAX_TREE_DEPTH = 6;
	public static final double RELATIVE_GROWTH_IMPORTANCE = 25;

	private static Logger logger;

	static Planet lastAttackedPlanet;


	static {
		logger = Logger.getLogger("MyLog");
		FileHandler fh;

		try {
			// This block configure the logger with handler and formatter
			fh = new FileHandler("logs/log-" + String.valueOf(System.currentTimeMillis()) + ".txt");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
			// the following statement is used to log any messages

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	/*)
	 * Function that gets called every turn. This is where to implement the strategies.
	 */
	public static void DoTurn(PlanetWars pw) {
		try {
			Planet sourcePlanet = getSourcePlanet(pw);
			Planet targetPlanet = getTargetPlanet(pw);



			if (targetPlanet != null && sourcePlanet != null) {
				logger.info("1::" + targetPlanet.toString() + "   :   " + sourcePlanet.toString());
				pw.IssueOrder(sourcePlanet, targetPlanet);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}




	private static GameState getCurrentState(PlanetWars pw) {
		int growthRate = 0;
		int shipCount = 0;

		for (Planet planet : pw.MyPlanets()) {
			growthRate += planet.GrowthRate();
			shipCount += planet.NumShips();
		}

		return new GameState(growthRate, shipCount);
	}


	private static Planet getTargetPlanet(PlanetWars pw) {
		GameState currentState = getCurrentState(pw);

		double maxScore = -Double.MAX_VALUE;
		List<Planet> notMyPlanets = pw.NotMyPlanets();
		Planet maxPlanet = notMyPlanets.get(0);

		for (Planet planet : notMyPlanets) {
			GameState newState = calculateNewState(currentState, planet);
			double score = getBestPlanetScore(pw.Planets(), planet,  1);

			if (score > maxScore) {
				maxScore = score;
				maxPlanet = planet;
			}
		}
		logger.info("RESULT MAX: "+maxScore);
		return maxPlanet;
	}

	private static Planet getSourcePlanet(PlanetWars pw) {
		List<Planet> planets = pw.MyPlanets();
		Planet source = planets.get(0);

		for (Planet planet : planets) {
			if (planet.NumShips() > source.NumShips()) {
				source = planet;
			}
		}

		return source;
	}

	private static double getBestPlanetScore(List<Planet> planets, Planet currentPlanet, int depth) {
		if (depth > MAX_TREE_DEPTH) {
			return calculateScore(currentPlanet);
		}

		double maxScore = -Double.MAX_VALUE;

		for (Planet planet : planets) {

			List<Planet> newList = clonePlanetList(planets, planet);

			double score = getBestPlanetScore(planets, planet, depth + 1);

			if (score > maxScore) {
				maxScore = score;
			}
		}

		return maxScore;

	}


	private static List<Planet> clonePlanetList(List<Planet> planets, Planet planet) {
		ArrayList<Planet> newList = new ArrayList<Planet>();



		for (Planet planet1 : planets) {
			if(planet1 == planet){
			}
			newList.add((Planet) planet.clone());
		}
		return newList;
	}


	private static GameState calculateNewState(GameState currentState, Planet planet) {
		int newShipCount = currentState.shipCount - planet.NumShips();
		int newGrowthRate = currentState.growthRate + planet.GrowthRate();
		return new GameState(newGrowthRate, newShipCount);
	}


	private static double calculateScore(Planet target) {
		if(lastAttackedPlanet != null && lastAttackedPlanet.PlanetID() == target.PlanetID()){
			return -Double.MAX_VALUE;
		} else {
			lastAttackedPlanet = target;
		}

		double baseScore = ((double) target.GrowthRate()) / target.NumShips();

		if (target.Owner() == ENEMY) {
			return baseScore * ENEMY_RELATIVE_IMPORTANCE;
		} else  if (target.Owner() == FRIENDLY){
			return baseScore * FRIEND_RELATIVE_IMPORTANCE;
		} else { // if neutral
			return baseScore * NEUTRAL_RELATIVE_IMPORTANCE;
		}
	}




	public static void main(String[] args) {
		String line = "";
		String message = "";
		int c;
		try {
			while ((c = System.in.read()) >= 0) {
				switch (c) {
					case '\n':
						if (line.equals("execute")) {
							PlanetWars pw = new PlanetWars(message);
							DoTurn(pw);
							pw.FinishTurn();
							message = "";
						} else {
							message += line + "\n";
						}
						line = "";
						break;
					default:
						line += (char) c;
						break;
				}
			}
		} catch (Exception e) {
			StringWriter writer = new StringWriter();
			e.printStackTrace(new PrintWriter(writer));
			String stackTrace = writer.toString();
			System.err.println(stackTrace);
			System.exit(1); //just stop now. we've got a problem
		}
	}
}
