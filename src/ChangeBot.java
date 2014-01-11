/**
 * Currently a copy of CustomBot. Should be changed later.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 * Date: 11/01/2014
 */

public class ChangeBot extends Bot{



	@Override
	public Planet getSourcePlanet(PlanetWars pw) {
		return Heuristic.select(pw.MyPlanets(), Heuristic.MOST_SHIPS);
	}

	@Override
	public Planet getTargetPlanet(PlanetWars pw) {
		double score = -Double.MAX_VALUE;
		Planet source = null;
		Planet dest = null;

		Bot enemyBot = new CustomBot();

		// We try to simulate each possible action and its outcome after two turns
		// considering each of my planets as a possible source
		// and each enemy planet as a possible destination
		for (Planet myPlanet: pw.MyPlanets()){

			//avoid planets with only one ship
			if (myPlanet.NumShips() <= 1)
				continue;

			for (Planet notMyPlanet: pw.NotMyPlanets()){

				// Create simulation environment - need to create one for each simulation
				SimulatedPlanetWars simpw = new SimulatedPlanetWars(pw);

				// (1) simulate my turn with the current couple of source and destination
				simpw = simpw.SimulateAttack(myPlanet, notMyPlanet);
				// (2) simulate the growth of ships that happens in each turn
				simpw.SimulateGrowth();

				// (3) simulate the opponent's turn, assuming that the opponent is the BullyBot
				//     here you can add other opponents

				Planet enemySource = enemyBot.getSourcePlanet(simpw);
				Planet enemyTarget = enemyBot.getTargetPlanet(simpw);

				simpw = simpw.SimulateEnemyAttack(enemySource, enemyTarget);
				// (4) simulate the growth of ships that happens in each turn
				simpw.SimulateGrowth();


				// (5) evaluate how the current simulated state is
				//     here you can change how a state is evaluated as good
				double scoreMax = simpw.CalculateScore();

				// (6) find the planet with the maximum evaluated score
				//     this is the most promising future state
				if (scoreMax > score) {
					score = scoreMax;
					source = myPlanet;
					dest = notMyPlanet;

				}

			}
		}



		// Attack using the source and destinations that lead to the most promising state in the simulation
		if (source != null && dest != null) {
			pw.IssueOrder(source, dest);
		}

		return null;
	}


	public static void main(String[] args) {
		Bot bot = new ChangeBot();
		Bot.execute(bot);
	}


}
