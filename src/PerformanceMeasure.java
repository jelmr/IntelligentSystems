import java.util.List;


/**
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 13/01/14
 */
public interface PerformanceMeasure {

	public static final PerformanceMeasure 	MOST_SHIPS = new MostShips(),
											MOST_GROWTH = new MostGrowth();

	public double calculateScore(PlanetWars pw);





	public static class MostShips implements PerformanceMeasure {

		@Override
		public double calculateScore(PlanetWars pw) {
			int friendlyNumShips = pw.NumShips(Bot.FRIENDLY);
			int totalNumShips = friendlyNumShips + pw.NumShips(Bot.HOSTILE);
			return ((double) friendlyNumShips) / totalNumShips;
		}
	}

	public static class MostGrowth implements PerformanceMeasure {

		@Override
		public double calculateScore(PlanetWars pw) {
			int friendlyGrowth = growth(pw.MyPlanets());
			int totalGrowth = friendlyGrowth + growth(pw.NotMyPlanets());
			return ((double) friendlyGrowth) / totalGrowth;

		}

		private static int growth(List<Planet> list){
			int growthSum = 0;
			for (Planet planet : list) {
				growthSum += planet.GrowthRate();
			}
			return  growthSum;
		}
	}

}
