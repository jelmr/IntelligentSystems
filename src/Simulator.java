/**
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 15/01/14
 */
public class Simulator {

    static final long WAITING_TIME=4000;

    public class RunBot extends Thread {
        Bot bot;
        Action action;
        SimulatedPlanetWars spw;
        public RunBot(Bot bot, SimulatedPlanetWars spw) {
            this.bot = bot;
            this.action = null;
            this.spw = spw;
        }

        public void run() {

            action = bot.getAction(spw);
        }



    }

	public  SimulatedPlanetWars simulate(SimulatedPlanetWarsParallel spw, Bot a, Bot b, int turns) {
		SimulatedPlanetWarsParallel result = spw;

		for (int i = 0; i < turns; i++) {
			result = simulateTurn(result, a, b);
		}

		return result;
	}

	public  SimulatedPlanetWarsParallel simulateTurn(SimulatedPlanetWarsParallel spw, Bot a, Bot b){
		SimulatedPlanetWarsParallel newSpw = new SimulatedPlanetWarsParallel(spw, Bot.FRIENDLY);

        long start = System.currentTimeMillis();
        RunBot runA = new RunBot(a, spw);
        RunBot runB = new RunBot(b, spw);


        while (System.currentTimeMillis() - start < WAITING_TIME) {
            // Wait
            if (runA.action != null && runB.action!=null) {
                break;
            }
        }


        Action aAction = runA.action;
        Action bAction = runB.action;


		int aDistance = getDistance(aAction);
		int bDistance = getDistance(bAction);

		if (aDistance > bDistance) {
			newSpw.IssueOrder(bAction);
			newSpw.IssueOrder(aAction);
		} else {
			newSpw.IssueOrder(aAction);
			newSpw.IssueOrder(bAction);
		}

		return newSpw;
	}





	public  int getDistance(Action action) {
		return getDistance(action.source, action.target);
	}

	public  int getDistance(Planet a, Planet b){
		double dx = a.X() - b.X();
		double dy = a.Y() - b.Y();
		return (int) Math.ceil(Math.sqrt(dx * dx + dy * dy));

	}
}
