import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 *  Framework for making bots.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 * Date: 11/01/2014
 */

public abstract class Bot {

	static Logger logger;


	/**
	 * Function for selecting the planet from which to send ships.
	 *
	 * @param pw PlanetWars object from which to derive game state.
	 * @return The planet from which to send ships.
	 */
	public abstract Planet getSourcePlanet(PlanetWars pw);

	/**
	 * Function for selecting the planet to which to send ships.
	 *
	 * @param pw PlanetWars object from which to derive game state.
	 * @return The planet to which to send ships.
	 */
	public abstract Planet getTargetPlanet(PlanetWars pw);

	public static void DoTurn(PlanetWars pw, Bot bot) {
		Planet source = bot.getSourcePlanet(pw);
		Planet target = bot.getTargetPlanet(pw);

		if (source != null && target != null) {
			pw.IssueOrder(source, target);
		}

	}


	/**
	 * Used to interact with the Engine.
	 *
	 * @param bot The bot to use to make decisions.
	 */
	public static void execute(Bot bot) {
		logger = getLogger(bot);
		String line = "";
		String message = "";
		int c;
		try {
			while ((c = System.in.read()) >= 0) {
				switch (c) {
					case '\n':
						if (line.equals("go")) {
							PlanetWars pw = new PlanetWars(message);
							DoTurn(pw, bot);
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


	/**
	 * Returns a logger which can be used to write to a log file.
	 *
	 * @return the logger.
	 */
	public static Logger getLogger(Bot bot){

		Logger logger = Logger.getLogger("MyLog");
		FileHandler fh;

		try {
			// This block configure the logger with handler and formatter
			fh = new FileHandler(System.getProperty("user.dir")+"/../logs/"+ bot.getClass().getSimpleName()+"-" + String.valueOf(System.currentTimeMillis()) + ".txt");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
			// the following statement is used to log any messages

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return logger;
	}



}




