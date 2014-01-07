import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/*
 RandomBot - an example bot that picks up one of his planets and send half of the ships
 from that planet to a random target planet.

 Not a very clever bot, but showcases the functions that can be used.
 Overcommented for educational purposes.
 */
public class AggresiveBot {

	public static final int FRIENDLY = 1;
	public static final int ENEMY = 2;
	public static final double ENEMY_RELATIVE_IMPORTANCE = 1.5;
	private static Logger logger;


	static {
		logger = Logger.getLogger("MyLog");
		FileHandler fh;

		try {
			// This block configure the logger with handler and formatter
			fh = new FileHandler("/Users/jelmer/testLog.txt");
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


	/*
	 * Function that gets called every turn. This is where to implement the strategies.
	 */
	public static void DoTurn(PlanetWars pw) {
		try {
			Planet sourcePlanet = getSourcePlanet(pw);
			Planet targetPlanet = getTargetPlanet(pw);

			if (targetPlanet != null && sourcePlanet != null) {
				pw.IssueOrder(sourcePlanet, targetPlanet);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	private static Planet getTargetPlanet(PlanetWars pw) {
		List<Planet> notMyPlanets = pw.EnemyPlanets();
		if (notMyPlanets.size() > 0) {
			double highestScore = -Double.MAX_VALUE;
			Planet highestScorePlanet = null;
			for (Planet notMyPlanet : notMyPlanets) {
				double score = calculateTargetScore(notMyPlanet);
				if (score > highestScore) {
					highestScore = score;
					highestScorePlanet = notMyPlanet;
				}
			}
			return highestScorePlanet;
		}
		return null;
	}


	private static Planet getSourcePlanet(PlanetWars pw) {
		List<Planet> myPlanets = pw.MyPlanets();
		if (myPlanets.size() > 0) {
			double highestScore = Integer.MIN_VALUE;
			Planet highestScorePlanet = null;
			for (Planet myPlanet : myPlanets) {
				double score = calculateSourceScore(myPlanet);
				if (score > highestScore) {
					highestScore = score;
					highestScorePlanet = myPlanet;
				}
			}
			return highestScorePlanet;
		}
		return null;
	}


	private static double calculateSourceScore(Planet myPlanet) {
		return myPlanet.NumShips();
	}


	private static double calculateTargetScore(Planet notMyPlanet) {
		double baseScore = ((double) notMyPlanet.GrowthRate()) / notMyPlanet.NumShips();

		if (notMyPlanet.Owner() == ENEMY) {
			return baseScore * ENEMY_RELATIVE_IMPORTANCE;
		} else { // if neutral
			return baseScore;
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
						if (line.equals("go")) {
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
