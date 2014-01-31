import java.util.List;


/**
 * A class for storing Performance Measures: methods to evaluate a state.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 13/01/14
 */


public interface PerformanceMeasure15 {

	public static final PerformanceMeasure15 MOST_SHIPS = new MostShips(),
											MOST_PLANETS = new MostPlanets(),
											MOST_GROWTH = new MostGrowth(),
											BEST_GROWTH_PER_SHIP = new BestGrowthPerShip();

	/**
	 * Assign a score to a certain state.
	 *
	 * @param pw The state to evaluate.
	 * @return The score.
	 */
	public double calculateScore(PlanetWars15 pw);



    public static class EnemyShips implements PerformanceMeasure15 {

        @Override
        public double calculateScore(PlanetWars15 pw) {
            int friendlyNumShips = pw.NumShips(Bot15.FRIENDLY);
            int hostileShips = pw.NumShips(Bot15.HOSTILE);
            int totalNumShips = friendlyNumShips + hostileShips;
            return 1.0 - ((double) (hostileShips) / totalNumShips);
        }
    }

	public static class BestGrowthPerShip implements PerformanceMeasure15 {

		@Override
		public double calculateScore(PlanetWars15 pw) {
			int friendlyNumShips = pw.NumShips(Bot15.FRIENDLY);
			return ((double) growth(pw.MyPlanets())) / friendlyNumShips;
		}
		private static int growth(List<Planet15> list){
			int growthSum = 0;
			for (Planet15 planet : list) {
				growthSum += planet.GrowthRate();
			}
			return  growthSum;
		}
    }

	/**
	 * More planets correspond with a higher score.
	 */
	public static class MostPlanets implements PerformanceMeasure15 {

		@Override
		public double calculateScore(PlanetWars15 pw) {
			int friendlyNumPlanets = pw.MyPlanets().size();
            int hostileNumPlanets = pw.EnemyPlanets().size();


			return ((double) friendlyNumPlanets) / hostileNumPlanets;
		}
	}

	/**
	 * More ships correspond with a higher score.
	 */
	public static class MostShips implements PerformanceMeasure15 {

		@Override
		public double calculateScore(PlanetWars15 pw) {
			int friendlyNumShips = pw.NumShips(Bot15.FRIENDLY);
			int totalNumShips = friendlyNumShips + pw.NumShips(Bot15.HOSTILE);
			return ((double) friendlyNumShips) / totalNumShips;
		}
	}

	/**
	 * Higher percentual total growth rate corresponds with a higher score.
	 */
	public static class MostGrowth implements PerformanceMeasure15 {

		@Override
		public double calculateScore(PlanetWars15 pw) {
			int friendlyGrowth = growth(pw.MyPlanets());
			int totalGrowth = friendlyGrowth + growth(pw.NotMyPlanets());
			return ((double) friendlyGrowth) / totalGrowth;

		}

		private static int growth(List<Planet15> list){
			int growthSum = 0;
			for (Planet15 planet : list) {
				growthSum += planet.GrowthRate();
			}
			return  growthSum;
		}
	}

}
