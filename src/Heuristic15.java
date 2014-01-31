import java.util.List;


/**
 * Interface for heuristics. An heuristic attaches a performance-score to a planet.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 * Date: 11/01/2014
 */

public abstract class Heuristic15 {


	public static Heuristic15 RANDOM = new Random(),
									FEWEST_SHIPS = new FewestShips(),
									MOST_SHIPS = new MostShips(),
									SMALLEST_GENERATION = new SmallestGeneration(),
									LARGEST_GENERATION = new LargestGeneration(),
									BEST_GENERATION_PER_SHIPS_LOST = new BestGenerationPerShipsLost(),
									TEST_HEURISTIC = new TestHeuristic();

	public static Heuristic15[] HEURISTICS = {FEWEST_SHIPS, MOST_SHIPS, SMALLEST_GENERATION, LARGEST_GENERATION, BEST_GENERATION_PER_SHIPS_LOST};

	public static final int NEUTRAL = 0,
							FRIENDLY = 1,
							ENEMY = 2;


	/**
	 * Selects the planet from the supplied list with the highest score using the supplied heuristic.
	 *
	 * @param planets The list from which to pick a planet.
	 * @param heuristic The heuristic to use to pick a planet.
	 * @return The best planet from the list based on the supplied heuristic.
	 */
	public static Planet15 select(List<Planet15> planets, Heuristic15 heuristic) {
		Planet15 maxPlanet = null;
		double maxScore = -Double.MAX_VALUE;

		for (Planet15 planet : planets) {
			double score = heuristic.calculateScore(planet);
			if (score > maxScore) {
				maxPlanet = planet;
				maxScore = score;
			}
		}
		return maxPlanet;
	}


	/**
	 * Calculate a score for a planet. A higher score means it is favorable to take over this planet.
	 *
	 * @param planet The planet for which to calculate a score.
	 * @return A score. A higher score means it is favorable to take over this planet.
	 */
	abstract double calculateScore(Planet15 planet);



	static class Random extends Heuristic15 {

		// Better done by selecting a random number in the getPlanet() method...
		@Override
		public double calculateScore(Planet15 planet) {
			return Math.random();
		}
	}



	static class FewestShips extends Heuristic15 {

		@Override
		public double calculateScore(Planet15 planet) {
			return 1. / planet.NumShips();
		}
	}



	static class MostShips extends Heuristic15 {

		@Override
		public double calculateScore(Planet15 planet) {
			return planet.NumShips();
		}
	}



	static class SmallestGeneration extends Heuristic15 {

		@Override
		public double calculateScore(Planet15 planet) {
			return 1. / planet.GrowthRate();
		}
	}



	static class LargestGeneration extends Heuristic15 {

		@Override
		public double calculateScore(Planet15 planet) {
			return planet.GrowthRate();
		}
	}



	static class BestGenerationPerShipsLost extends Heuristic15 {

		@Override
		public double calculateScore(Planet15 planet) {
			double baseScore = ((double) planet.GrowthRate()) / planet.NumShips();

			if (planet.Owner() == NEUTRAL) {
				return baseScore;
			} else { // if enemy
				return baseScore * 2.;
			}

		}
	}

	static class TestHeuristic extends Heuristic15 {

		@Override
		public double calculateScore(Planet15 planet) {
			double baseScore = ((double) planet.GrowthRate()) / planet.NumShips();

			if (planet.Owner() == NEUTRAL) {
				return baseScore;
			} else { // if enemy
				return baseScore * 30.;
			}

		}
	}



}
