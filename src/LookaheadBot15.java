import java.util.*;

/** Another smarter kind of bot, which implements a minimax algorithm with look-ahead of two turns.
 * It simulates the opponent using the BullyBot15 strategy and simulates the possible outcomes for any
 * choice of source and destination planets in the attack. The simulated outcome states are ranked by
 * the evaluation function, which returns the most promising one.
 * 
 * Try to improve this bot. For example, you can try to answer some of this questions. 
 * Can you come up with smarter heuristics/scores for the evaluation function? 
 * What happens if you run this bot against your bot from week1? 
 * How can you change this bot to beat your week1 bot? 
 * Can you extend the bot to look ahead more than two turns? How many turns do you want to look ahead?
 * Is there a smart way to make this more efficient?
 */

public class LookaheadBot15 extends Bot15 {


	
	/**
	 * This function evaluates how promising a simulated state is.
	 * You can change it to anything that makes sense, using combinations 
	 * of number of planets, ships or growth rate.
	 * @param  pw
	 * @return score of the final state of the simulation
	 */
	public static double evaluateState(LookaheadBot15.SimulatedPlanetWars pw){
		
		// CHANGE HERE
		
		double enemyShips = 1.0;
		double myShips = 1.0;
		
		for (Planet15 planet: pw.EnemyPlanets()){
			enemyShips += planet.NumShips();
		}
		
		for (Planet15 planet: pw.MyPlanets()){
			myShips += planet.NumShips();
		}
		
		return myShips/enemyShips;
	}
	

	
	// don't change this
	public static void main(String[] args) {
		
		Bot15 bot = new LookaheadBot15();
		Bot15.execute(bot);
	}
	
	/**
	 * Create the simulation environment. Returns a SimulatedPlanetWars15 instance.
	 * Call every time you want a new simulation environment.

	 * @return SimulatedPlanetWars15 instance on which to simulate your attacks. Create a new one everytime you want to try alternative simulations.
	 */
	public static LookaheadBot15.SimulatedPlanetWars createSimulation(PlanetWars15 pw){
		return dummyBot.new SimulatedPlanetWars(pw);
	}
	
	
	/**
	 * Static LookaheadBot15, used only to access SimulatedPlanetWars15 (DON'T CHANGE)
	 */
	static LookaheadBot15 dummyBot = new LookaheadBot15();


	@Override
	public Action15 getAction(PlanetWars15 pw) {
		double score = Double.MIN_VALUE;
		Planet15 source = null;
		Planet15 dest = null;


		// We try to simulate each possible action and its outcome after two turns
		// considering each of my planets as a possible source
		// and each enemy planet as a possible destination
		for (Planet15 myPlanet: pw.MyPlanets()){

			//avoid planets with only one ship
			if (myPlanet.NumShips() <= 1)
				continue;

			for (Planet15 notMyPlanet: pw.NotMyPlanets()){

				// Create simulation environment - need to create one for each simulation
				SimulatedPlanetWars simpw = createSimulation(pw);

				// (1) simulate my turn with the current couple of source and destination
				simpw.simulateAttack(myPlanet, notMyPlanet);
				// (2) simulate the growth of ships that happens in each turn
				simpw.simulateGrowth();

				// (3) simulate the opponent's turn, assuming that the opponent is the BullyBot15
				//     here you can add other opponents
				simpw.simulateBullyBotAttack();
				// (4) simulate the growth of ships that happens in each turn
				simpw.simulateGrowth();


				// (5) evaluate how the current simulated state is
				//     here you can change how a state is evaluated as good
				double scoreMax = evaluateState(simpw);

				// (6) find the planet with the maximum evaluated score
				//     this is the most promising future state
				if (scoreMax > score) {
					score = scoreMax;
					source = myPlanet;
					dest = notMyPlanet;

				}

			}
		}

		return new Action15(source, dest);
	}


	/**
	 * Class which provide the simulation environment, has same interface as PlanetWars15
	 * (except for Fleets, that are not used).
	 *
	 */
	public class SimulatedPlanetWars{

		List<Planet15> planets = new ArrayList<Planet15>();
		
		public SimulatedPlanetWars(PlanetWars15 pw) {

			for (Planet15 planet: pw.Planets()){
				planets.add(planet);
			}
		}
		
		public void simulateGrowth() {
			for (Planet15 p: planets){
				
				if(p.Owner() == 0)
					continue;
				
				Planet15 newp = new Planet15(p.PlanetID(), p.Owner(), p.NumShips()+p.GrowthRate() ,
						p.GrowthRate(), p.X(), p.Y());
				
				planets.set(p.PlanetID(), newp);
			}
		}
		
		public void simulateAttack( int player, Planet15 source, Planet15 dest){
			
			if (source.Owner() != player){
				return;
			}
			
			
			// Simulate attack
			if (source != null && dest != null) {
						
				Planet15 newSource = new Planet15(source.PlanetID(), source.Owner(), source.NumShips()/2 ,
						source.GrowthRate(), source.X(), source.Y());
				Planet15 newDest = new Planet15(dest.PlanetID(), dest.Owner(), Math.abs(dest.NumShips()-source.NumShips()/2 ),
						dest.GrowthRate(), dest.X(), dest.Y());
				
				if(dest.NumShips()< source.NumShips()/2){
					//change owner
					newDest.Owner(player);
				}
				
				planets.set(source.PlanetID(), newSource);
				planets.set(dest.PlanetID(), newDest);
			}


		}
		
		public void simulateAttack( Planet15 source, Planet15 dest){
			simulateAttack(1, source, dest);
		}
		
		
		public void simulateBullyBotAttack(){
			Planet15 source = null;
			Planet15 dest = null;

			
			// (1) Apply your strategy
			double sourceScore = Double.MIN_VALUE;
			double destScore = Double.MAX_VALUE;
			
			for (Planet15 planet : planets) {
				if(planet.Owner() == 2) {// skip planets with only one ship
					if (planet.NumShips() <= 1)
						continue;
					
					//This score is one way of defining how 'good' my planet is. 
					double scoreMax = (double) planet.NumShips();
					
					if (scoreMax > sourceScore) {
						//we want to maximize the score, so store the planet with the best score
						sourceScore = scoreMax;
						source = planet;
					}
				}	
				
				// (2) Find the weakest enemy or neutral planet.
				if(planet.Owner() != 2){
					double scoreMin = (double) (planet.NumShips());
					//if you want to debug how the score is computed, decomment the System.err.instructions
		//			System.err.println("Planet15: " +notMyPlanet.PlanetID()+ " Score: "+ score);
		//			System.err.flush();
					if (scoreMin < destScore) {
						//The way the score is defined, is that the weaker a planet is, the higher the score. 
						//So again, we want to select the planet with the best score
						destScore = scoreMin;
						dest = planet;
					}
				}
				
			}
			
			// (3) Simulate attack
			if (source != null && dest != null) {
				simulateAttack(2, source, dest);
			}

		}
		
		public List<Planet15> Planets(){
			return planets;
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
	    
	    // Return a list of all the planets owned by the current player. By
	    // convention, the current player is always player number 1.
	    public List<Planet15> MyPlanets() {
			List<Planet15> r = new ArrayList<Planet15>();
			for (Planet15 p : planets) {
			    if (p.Owner() == 1) {
				r.add(p);
			    }
			}
			return r;
	    }
	    
	    // Return a list of all neutral planets.
	    public List<Planet15> NeutralPlanets() {
		List<Planet15> r = new ArrayList<Planet15>();
		for (Planet15 p : planets) {
		    if (p.Owner() == 0) {
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
		    if (p.Owner() >= 2) {
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
		    if (p.Owner() != 1) {
			r.add(p);
		    }
		}
		return r;
	    }
	    
	    // Returns the distance between two planets, rounded up to the next highest
	    // integer. This is the number of discrete time steps it takes to get
	    // between the two planets.
		public int Distance(int sourcePlanet, int destinationPlanet) {
			Planet15 source = planets.get(sourcePlanet);
			Planet15 destination = planets.get(destinationPlanet);
			double dx = source.X() - destination.X();
			double dy = source.Y() - destination.Y();
			return (int) Math.ceil(Math.sqrt(dx * dx + dy * dy));
		}
	    
	    // If the game is not yet over (ie: at least two players have planets or
	    // fleets remaining), returns -1. If the game is over (ie: only one player
	    // is left) then that player's number is returned. If there are no
	    // remaining players, then the game is a draw and 0 is returned.
		public int Winner() {
			Set<Integer> remainingPlayers = new TreeSet<Integer>();
			for (Planet15 p : planets) {
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

	    

	    public void IssueOrder(Planet15 source, Planet15 dest) {
	    	simulateAttack(source,dest);
	    }
	    
	
	}
}
