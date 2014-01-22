/**
 * A bot with is usable by the genetic algorithm.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 21/01/14
 */

public abstract class DarwinBot extends Bot{

	/**
	 * Generates a new instance of itself with the provided parameters.
	 *
	 * @param pars Parameters to initialize itself with.
	 * @return a new instance of itself with the provided parameters.
	 */
	public abstract DarwinBot getInstance(double... pars);

	/**
	 * Replaces the parameters of this bot by the provided parameters.
	 *
	 * @param pars The new parameters.
	 */
	public abstract void setPars(double... pars);

	/**
	 * Get the current parameters of the bot.
	 *
	 * @return the current parameters of the bot.
	 */
	public abstract double[] getPars();



}
