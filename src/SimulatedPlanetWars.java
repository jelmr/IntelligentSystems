import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class SimulatedPlanetWars extends PlanetWars implements Cloneable {

	public static final int NEUTRAL = 0,
							FRIENDLY = 1,
	HOSTILE = 2,
							GROWTH_IMPORTANCE = 4;

	List<Planet> planets = new ArrayList<Planet>();

	private int player;


	public SimulatedPlanetWars(PlanetWars pw) {
		this(pw, FRIENDLY);
	}


	public SimulatedPlanetWars(PlanetWars pw, int player) {
		this.player = player;
		for (Planet planet : pw.Planets()) {
			planets.add((Planet) planet.clone());
		}
	}



	public int notPlayer() {
		return player == FRIENDLY ? HOSTILE : FRIENDLY;
	}


	public void SimulateGrowth() {
		for (Planet p : planets) {

			if (p.Owner() == NEUTRAL) {
				continue;
			}

			Planet newp = new Planet(p.PlanetID(), p.Owner(), p.NumShips() + p.GrowthRate(),
					p.GrowthRate(), p.X(), p.Y());

			planets.set(p.PlanetID(), newp);
		}
	}


	// Returns the number of planets. Planets are numbered starting with 0.
	public int NumPlanets() {
		return planets.size();
	}


	// Returns the planet with the given planet_id. There are NumPlanets()
	// planets. They are numbered starting at 0.
	public Planet GetPlanet(int planetID) {
		return planets.get(planetID);
	}


	public List<Planet> Planets() {
		return planets;
	}


	// Return a list of all the planets owned by the current player. By
	// convention, the current player is always player number 1.
	public List<Planet> MyPlanets() {
		List<Planet> r = new ArrayList<Planet>();
		for (Planet p : planets) {
			if (p.Owner() == player) {
				r.add(p);
			}
		}
		return r;
	}


	// Return a list of all neutral planets.
	public List<Planet> NeutralPlanets() {
		List<Planet> r = new ArrayList<Planet>();
		for (Planet p : planets) {
			if (p.Owner() == NEUTRAL) {
				r.add(p);
			}
		}
		return r;
	}


	// Return a list of all the planets owned by rival players. This excludes
	// planets owned by the current player, as well as neutral planets.
	public List<Planet> EnemyPlanets() {
		List<Planet> r = new ArrayList<Planet>();
		for (Planet p : planets) {
			if (p.Owner() >= notPlayer()) {
				r.add(p);
			}
		}
		return r;
	}


	// Return a list of all the planets that are not owned by the current
	// player. This includes all enemy planets and neutral planets.
	public List<Planet> NotMyPlanets() {
		List<Planet> r = new ArrayList<Planet>();
		for (Planet p : planets) {
			if (p.Owner() != player) {
				r.add(p);
			}
		}
		return r;
	}


	public void IssueOrder(Planet source, Planet dest) {
		SimulateAttack(this.player, source, dest);
	}


	// If the game is not yet over (ie: at least two players have planets or
	// fleets remaining), returns -1. If the game is over (ie: only one player
	// is left) then that player's number is returned. If there are no
	// remaining players, then the game is a draw and 0 is returned.
	public int Winner() {
		Set<Integer> remainingPlayers = new TreeSet<Integer>();
		for (Planet p : planets) {
			remainingPlayers.add(p.Owner());
		}
		switch (remainingPlayers.size()) {
			case 0:
				return 0;
			case 1:
				return ((Integer) remainingPlayers.toArray()[0]).intValue();
			default:
				return -1;
		}
	}


	public int Growth(int playerID) {
		int growth = 0;
		for (Planet p : planets) {
			if (p.Owner() == playerID) {
				growth += p.GrowthRate();
			}
		}
		return growth;
	}


	// Returns the number of ships that the current player has, either located
	// on planets or in flight.
	public int NumShips(int playerID) {
		int numShips = 0;
		for (Planet p : planets) {
			if (p.Owner() == playerID) {
				numShips += p.NumShips();
			}
		}
		return numShips;
	}



	private void SimulateAttack(int player, Planet source, Planet dest) {

		if (source.Owner() != player) {
			return;
		}

		// Simulate attack
		if (source != null && dest != null) {

			Planet newSource = new Planet(source.PlanetID(), source.Owner(), source.NumShips() / 2,
					source.GrowthRate(), source.X(), source.Y());
			Planet newDest = new Planet(dest.PlanetID(), dest.Owner(), Math.abs(dest.NumShips() - source.NumShips() / 2),
					dest.GrowthRate(), dest.X(), dest.Y());

			if (dest.NumShips() < source.NumShips() / 2) {
				//change owner
				newDest.Owner(player);
			}

			planets.set(source.PlanetID(), newSource);
			planets.set(dest.PlanetID(), newDest);
		}

		SimulateGrowth();
	}




	public SimulatedPlanetWars clone() {
		SimulatedPlanetWars result = new SimulatedPlanetWars(this);
		return result;
	}
}