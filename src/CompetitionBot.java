import java.util.ArrayList;
import java.util.Arrays;
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


public class CompetitionBot extends Bot{

	private int turnsPlayed = 0;
	public Action getAction(PlanetWars pw) {

		int planetsCount = pw.Planets().size();
		if (planetsCount == 8 || (planetsCount >= 18 && planetsCount <= 22)) {
			return new CarnageBot().getAction(pw);
		}

		Planet source = Heuristic.select(pw.MyPlanets(), Heuristic.MOST_SHIPS);
		ArrayList<Planet> myPlanets = new ArrayList<Planet>(pw.MyPlanets());

		Planet target = null;
		double maxScore = -Double.MAX_VALUE;
		Planet maxEnemyPlanet = getLargestEnemyPlanet(pw);

		// Get largest enemy planet, accounts for enemy ships in transit.
		for (Planet planet : pw.EnemyPlanets()) {
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
			Planet maxNeutral = pw.NeutralPlanets().get(0);
			double maxGrowth = -Double.MAX_VALUE;


			ArrayList<Planet> neutralPlanets = new ArrayList<Planet>(pw.NeutralPlanets());
			Collections.sort(neutralPlanets, new Comparator<Planet>() {
				@Override
				public int compare(Planet a, Planet b) {
					return b.GrowthRate()-a.GrowthRate();
				}
			});

			ArrayList<Planet> beneficialGrowth = new ArrayList<Planet>();
			for (Planet planet : pw.NeutralPlanets()) {
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



			for (Planet planet : beneficialGrowth) {
				if (SimulatedPlanetWarsParallel.getDistance(source, planet) >= SimulatedPlanetWarsParallel.getDistance(maxEnemyPlanet, planet)) {

					return new Action(source, planet);
				}
			}

		}

		Planet ownBest = getBestGrowthFriendlyPlanet(pw);
		if (pw.MyPlanets().size() > 1 && ownBest.NumShips() < getLargestEnemyPlanet(pw).NumShips()/2) {
			if (SimulatedPlanetWarsParallel.getDistance(source, ownBest) < SimulatedPlanetWarsParallel.getDistance(maxEnemyPlanet, ownBest)) {
				target = ownBest;
			}
		}


		Collections.sort(myPlanets, new Comparator<Planet>() {
			@Override
			public int compare(Planet a, Planet b) {
				return b.NumShips()-a.NumShips();
			}
		});

		if (ownBest.PlanetID() == source.PlanetID() && myPlanets.size() > 1) {
			source = myPlanets.get(1);
		}



		if(planetsCount < 6 && Heuristic.select(pw.Planets(), Heuristic.BEST_GENERATION_PER_SHIPS_LOST).Owner() == Bot.FRIENDLY) {

			//target = Heuristic.select(pw.Planets(), Heuristic.BEST_GENERATION_PER_SHIPS_LOST);
		}


		turnsPlayed++;
		return new Action(source, target);
	}

	private Planet getLargestEnemyPlanet(PlanetWars pw) {
		int maxShips = Integer.MIN_VALUE;
		Planet result = null;
		for (Planet planet : pw.EnemyPlanets()) {
			if (planet.NumShips() > maxShips) {
				maxShips = planet.NumShips();
				result = planet;
			}
		}
		return result;

	}

	private Planet getBestGrowthFriendlyPlanet(PlanetWars pw) {
		int maxShips = Integer.MIN_VALUE;
		Planet result = null;
		for (Planet planet : pw.MyPlanets()) {
			if (planet.GrowthRate() > maxShips) {
				maxShips = planet.GrowthRate();
				result = planet;
			}
		}
		return result;

	}



	public static void main(String[] args) {
		Bot bot = new CompetitionBot();
		Bot.execute(bot);
	}


}