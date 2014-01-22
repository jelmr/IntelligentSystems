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

	public static final int NEUTRAL = 0,
							FRIENDLY = 1,
							HOSTILE = 2;

	static Logger logger;


	/**
	 * Function for selecting the planet from which to send ships.
	 *
	 *
	 * @param pw PlanetWars object from which to derive game state.
	 * @return The planet from which to send ships.
	 */
	public abstract Action getAction(PlanetWars pw);




	private static void DoTurn(PlanetWars pw,Action action) {

		if (action.isValid()){
			pw.IssueOrder(action.source, action.target);
		} else {
            System.err.println("Custombot");
			//Action customAction = (new CustomBot()).getAction(pw);
			//pw.IssueOrder(customAction.source, customAction.target);
		}

	}



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
						Action action = null;
						try {
							action = bot.getAction(pw);
						} catch (Exception e) {

							StringWriter sw = new StringWriter();
							PrintWriter pbw = new PrintWriter(sw);
							e.printStackTrace(pbw);
							pw.log(sw.toString());
							logger.info((sw.toString()));
						}
						DoTurn(pw, action);
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



	public String toString(){
		return "oops...";
	}
}




