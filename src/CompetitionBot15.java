import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A bot that sends ships from its biggest planet (by shipcount) to an hostile planet with the most favorable GrowthRate / ships lost ratio.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 * Date: 11/01/2014
 */


public class CompetitionBot15 extends Bot15 {

	private int turnsPlayed = 0;
	public Action15 getAction(PlanetWars15 pw) {

		int planetsCount = pw.Planets().size();
		if (planetsCount == 8 || (planetsCount >= 18 && planetsCount <= 22)) {
			return new CarnageBot15().getAction(pw);
		}

		Planet15 source = Heuristic15.select(pw.MyPlanets(), Heuristic15.MOST_SHIPS);
		ArrayList<Planet15> myPlanets = new ArrayList<Planet15>(pw.MyPlanets());

		Planet15 target = null;
		double maxScore = -Double.MAX_VALUE;
		Planet15 maxEnemyPlanet = getLargestEnemyPlanet(pw);

		// Get largest enemy planet, accounts for enemy ships in transit.
		for (Planet15 planet : pw.EnemyPlanets()) {
			int planetSize = planet.NumShips();
			if (planet.PlanetID() == maxEnemyPlanet.PlanetID()) {
				planetSize /= 2;
			}

			if ((double)planet.GrowthRate()/planetSize > maxScore) {
				maxScore = (double)planet.GrowthRate()/planetSize;
				target = planet;
			}

		}

		if (pw.NeutralPlanets().size() > 0) {
			Planet15 maxNeutral = pw.NeutralPlanets().get(0);
			double maxGrowth = -Double.MAX_VALUE;


			ArrayList<Planet15> neutralPlanets = new ArrayList<Planet15>(pw.NeutralPlanets());
			Collections.sort(neutralPlanets, new Comparator<Planet15>() {
				@Override
				public int compare(Planet15 a, Planet15 b) {
					return b.GrowthRate()-a.GrowthRate();
				}
			});

			ArrayList<Planet15> beneficialGrowth = new ArrayList<Planet15>();
			for (Planet15 planet : pw.NeutralPlanets()) {
				if (planet.GrowthRate() > maxGrowth) {
					maxNeutral = planet;
					maxGrowth = planet.GrowthRate();
				}

				int coef = (planetsCount < 4 || planetsCount >= 7 && planetsCount <= 10 ? 2 : 1);


				// If growth is higher than cost, conquer the planet
				if (planet.NumShips() <= planet.GrowthRate() * coef) {
					maxNeutral = planet;
					beneficialGrowth.add(planet);

				}


			}

			if (maxNeutral.NumShips() < 17 && maxNeutral.GrowthRate()>2*target.GrowthRate() && maxNeutral.NumShips() < source.NumShips()/2 ) {
				target = maxNeutral;
			}



			for (Planet15 planet : beneficialGrowth) {
				if (SimulatedPlanetWarsParallel15.getDistance(source, planet) >= SimulatedPlanetWarsParallel15.getDistance(maxEnemyPlanet, planet)) {

					return new Action15(source, planet);
				}
			}

		}

		Planet15 ownBest = getBestGrowthFriendlyPlanet(pw);
		if (pw.MyPlanets().size() > 1 && ownBest.NumShips() < getLargestEnemyPlanet(pw).NumShips()/2) {
			if (SimulatedPlanetWarsParallel15.getDistance(source, ownBest) < SimulatedPlanetWarsParallel15.getDistance(maxEnemyPlanet, ownBest)) {
				target = ownBest;
			}
		}


		Collections.sort(myPlanets, new Comparator<Planet15>() {
			@Override
			public int compare(Planet15 a, Planet15 b) {
				return b.NumShips()-a.NumShips();
			}
		});

		if (ownBest.PlanetID() == source.PlanetID() && myPlanets.size() > 1) {
			source = myPlanets.get(1);
		}



		if(planetsCount < 6 && Heuristic15.select(pw.Planets(), Heuristic15.BEST_GENERATION_PER_SHIPS_LOST).Owner() == Bot15.FRIENDLY) {

			//target = Heuristic15.select(pw.Planets(), Heuristic15.BEST_GENERATION_PER_SHIPS_LOST);
		}


		turnsPlayed++;
		return new Action15(source, target);
	}

	private Planet15 getLargestEnemyPlanet(PlanetWars15 pw) {
		int maxShips = Integer.MIN_VALUE;
		Planet15 result = null;
		for (Planet15 planet : pw.EnemyPlanets()) {
			if (planet.NumShips() > maxShips) {
				maxShips = planet.NumShips();
				result = planet;
			}
		}
		return result;

	}

	private Planet15 getBestGrowthFriendlyPlanet(PlanetWars15 pw) {
		int maxShips = Integer.MIN_VALUE;
		Planet15 result = null;
		for (Planet15 planet : pw.MyPlanets()) {
			if (planet.GrowthRate() > maxShips) {
				maxShips = planet.GrowthRate();
				result = planet;
			}
		}
		return result;

	}



	public static void main(String[] args) {
		Bot15 bot = new CompetitionBot15();
		Bot15.execute(bot);
	}


}