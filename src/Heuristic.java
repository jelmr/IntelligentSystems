/**
 * Interface for heuristics. An heuristic attaches a performance-score to a planet.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 * Date: 11/01/2014
 */

public interface Heuristic {

	Heuristic 	RANDOM = new Random(),
				FEWEST_SHIPS = new FewestShips(),
				MOST_SHIPS = new MostShips(),
				SMALLEST_GENERATION = new SmallestGeneration(),
				LARGEST_GENERATION = new LargestGeneration(),
				BEST_GENERATION_PER_SHIPS_LOST = new BestGenerationPerShipsLost();

	public static final int NEUTRAL = 0,
							FRIENDLY = 1,
							ENEMY = 2;

	/**
	 * Calculate a score for a planet. A higher score means it is favorable to take over this planet.
	 *
	 * @param planet The planet for which to calculate a score.
	 * @return A score. A higher score means it is favorable to take over this planet.
	 */
	double calculateScore(Planet planet);



	class Random implements Heuristic {

		// Beter done by selecting a random number in the getPlanet() method...
		@Override
		public double calculateScore(Planet planet) {
			return Math.random();
		}
	}



	class FewestShips implements Heuristic {

		@Override
		public double calculateScore(Planet planet) {
			return 1. / planet.NumShips();
		}
	}



	class MostShips implements Heuristic {

		@Override
		public double calculateScore(Planet planet) {
			return planet.NumShips();
		}
	}



	class SmallestGeneration implements Heuristic {

		@Override
		public double calculateScore(Planet planet) {
			return 1. / planet.GrowthRate();
		}
	}



	class LargestGeneration implements Heuristic {

		@Override
		public double calculateScore(Planet planet) {
			return planet.GrowthRate();
		}
	}



	class BestGenerationPerShipsLost implements Heuristic {

		@Override
		public double calculateScore(Planet planet) {
			double baseScore = ((double) planet.GrowthRate()) / planet.NumShips();

			if (planet.Owner() == ENEMY) {
				return baseScore * 2.0;
			} else if (planet.Owner() == FRIENDLY) {
				return baseScore * 0.5;
			} else { // if neutral
				return baseScore * (double) 1;
			}

		}
	}

}
