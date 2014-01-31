import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class SimulatedPlanetWars15 extends PlanetWars15 implements Cloneable {

	public static final int NEUTRAL = 0,
							FRIENDLY = 1,
							HOSTILE = 2,
							GROWTH_IMPORTANCE = 4;



	List<Planet15> planets = new ArrayList<Planet15>();

	public int player;

	public SimulatedPlanetWars15(String mapPath){
		planets = new ArrayList<Planet15>();
		LoadMapFromFile(mapPath);
		player = Bot15.FRIENDLY;
	}

	public SimulatedPlanetWars15(PlanetWars15 pw) {
		this(pw, FRIENDLY);
	}


	public SimulatedPlanetWars15(PlanetWars15 pw, int player) {
		this.player = player;
		for (Planet15 planet : pw.Planets()) {
			planets.add((Planet15) planet.clone());
		}
	}

	// Parses a game state from a string. On success, returns 1. On failure,
	// returns 0.
	private int ParseGameState(String s) {
		planets.clear();
		int planetID = 0;
		String[] lines = s.split("\n");
		for (int i = 0; i < lines.length; ++i) {
			String line = lines[i];
			int commentBegin = line.indexOf('#');
			if (commentBegin >= 0) {
				line = line.substring(0, commentBegin);
			}
			if (line.trim().length() == 0) {
				continue;
			}
			String[] tokens = line.split(" ");
			if (tokens.length == 0) {
				continue;
			}
			if (tokens[0].equals("P")) {
				if (tokens.length != 6) {
					return 0;
				}
				double x = Double.parseDouble(tokens[1]);
				double y = Double.parseDouble(tokens[2]);
				int owner = Integer.parseInt(tokens[3]);
				int numShips = Integer.parseInt(tokens[4]);
				int growthRate = Integer.parseInt(tokens[5]);
				Planet15 p = new Planet15(planetID++,
						owner,
						numShips,
						growthRate,
						x, y);
				planets.add(p);
			} else if (tokens[0].equals("F")) {
				if (tokens.length != 7) {
					return 0;
				}
			} else {
				return 0;
			}
		}
		return 1;
	}

	private int LoadMapFromFile(String mapFilename) {
		String s = "";
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(mapFilename));
			int c;
			while ((c = in.read()) >= 0) {
				s += (char) c;
			}
		} catch (Exception e) {
			return 0;
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				// Fucked.
			}
		}


		return ParseGameState(s);
	}


	public int notPlayer() {
		return player == FRIENDLY ? HOSTILE : FRIENDLY;
	}


	public void SimulateGrowth() {
		for (Planet15 p : planets) {

			if (p.Owner() == NEUTRAL) {
				continue;
			}

			Planet15 newp = new Planet15(p.PlanetID(), p.Owner(), p.NumShips() + p.GrowthRate(),
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
	public Planet15 GetPlanet(int planetID) {
		return planets.get(planetID);
	}


	public List<Planet15> Planets() {
		return planets;
	}


	// Return a list of all the planets owned by the current player. By
	// convention, the current player is always player number 1.
	public List<Planet15> MyPlanets() {
		List<Planet15> r = new ArrayList<Planet15>();
		for (Planet15 p : planets) {
			if (p.Owner() == player) {
				r.add(p);
			}
		}
		return r;
	}


	// Return a list of all neutral planets.
	public List<Planet15> NeutralPlanets() {
		List<Planet15> r = new ArrayList<Planet15>();
		for (Planet15 p : planets) {
			if (p.Owner() == NEUTRAL) {
				r.add(p);
			}
		}
		return r;
	}


	// Return a list of all the planets owned by rival players. This excludes
	// planets owned by the current player, as well as neutral planets.
	public List<Planet15> EnemyPlanets() {
		List<Planet15> r = new ArrayList<Planet15>();
		for (Planet15 p : planets) {
			if (p.Owner() == notPlayer()) {
				r.add(p);
			}
		}
		return r;
	}


	// Return a list of all the planets that are not owned by the current
	// player. This includes all enemy planets and neutral planets.
	public List<Planet15> NotMyPlanets() {
		List<Planet15> r = new ArrayList<Planet15>();
		for (Planet15 p : planets) {
			if (p.Owner() != player) {
				r.add(p);
			}
		}
		return r;
	}


	public void IssueOrder(Planet15 source, Planet15 dest) {
		SimulateAttack(this.player, source, dest);
		SimulateGrowth();
	}

	public void IssueOrder(Action15 action){
		IssueOrder(action.source, action.target);
	}


	// If the game is not yet over (ie: at least two players have planets or
	// fleets remaining), returns -1. If the game is over (ie: only one player
	// is left) then that player's number is returned. If there are no
	// remaining players, then the game is a draw and 0 is returned.
	public int Winner() {

//		boolean aliveA = false;
//		boolean aliveB = false;
//
//		for (Planet15 planet : planets) {
//			if(planet.Owner() == Bot15.FRIENDLY) {
//				aliveA = true;
//			} else if (planet.Owner() == Bot15.HOSTILE) {
//				aliveB = true;
//			}
//		}
//
//		if(!aliveA && !aliveB){
//			return 0;
//		} else if (!aliveA) {
//			return Bot15.HOSTILE;
//		} else if (!aliveB) {
//			return Bot15.FRIENDLY;
//		} else {
//			return -1;
//		}

		player = Bot15.FRIENDLY;
		int f = MyPlanets().size();
		int e = EnemyPlanets().size();

		if(e==0 && f==0){
			return 0;
		} else if(e==0){
			return Bot15.FRIENDLY;
		} else if(f==0){
			return Bot15.HOSTILE;
		} else {
			return -1;
		}




//		Set<Integer> remainingPlayers = new TreeSet<Integer>();
//		for (Planet15 p : planets) {
//			remainingPlayers.add(p.Owner());
//		}
//		switch (remainingPlayers.size()) {
//			case 0:
//				return 0;
//			case 1:
//				return ((Integer) remainingPlayers.toArray()[0]).intValue();
//			default:
//				return -1;
//		}
	}


	public int Growth(int playerID) {
		int growth = 0;
		for (Planet15 p : planets) {
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
		for (Planet15 p : planets) {
			if (p.Owner() == playerID) {
				numShips += p.NumShips();
			}
		}
		return numShips;
	}



	public void SimulateAttack(int player, Planet15 source, Planet15 dest) {



		if (source != null && source.Owner() != player) {
			System.err.print("Whoops...");

			return;
		}

		// Simulate attack
		if (source != null && dest != null) {

			Planet15 newSource = new Planet15(source.PlanetID(), source.Owner(), source.NumShips() / 2,
					source.GrowthRate(), source.X(), source.Y());
			Planet15 newDest = new Planet15(dest.PlanetID(), dest.Owner(), Math.abs(dest.NumShips() - source.NumShips() / 2),
					dest.GrowthRate(), dest.X(), dest.Y());

			if (dest.NumShips() < source.NumShips() / 2) {
				//change owner
				newDest.Owner(player);
			}

			planets.set(source.PlanetID(), newSource);
			planets.set(dest.PlanetID(), newDest);
		}


	}

	public static int totalGrowth(PlanetWars15 pw, int player) {
		List<Planet15> p;

		if (player == Bot15.FRIENDLY) {
			p = pw.MyPlanets();
		} else if (player == Bot15.HOSTILE) {
			p = pw.EnemyPlanets();
		} else {
			p = pw.NeutralPlanets();
		}

		int result = 0;

		for (Planet15 planet : p) {
			result += planet.GrowthRate();
		}

		return result;
	}


	public SimulatedPlanetWars15 clone() {
		return new SimulatedPlanetWars15(this);
	}
}
