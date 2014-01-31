import java.util.ArrayList;


/**
 * Created by sirmc on 18/01/14.
 */
public class State15 {

	private PlanetWars15 pw;
	private State15 previousState;
	private Action15 action;


	public State15(PlanetWars15 pw, State15 previousState, Action15 action) {
		this(pw, previousState);
		this.setAction(action);
		this.action = reconstructAction(previousState.pw, pw);
	}


	public static Action15 reconstructAction(PlanetWars15 initial, PlanetWars15 result) {
		for (int i = 0; i < initial.Planets().size(); i++) {
			Planet15 a = initial.Planets().get(i);
			Planet15 b = result.Planets().get(i);

			int aShipCount = a.NumShips() + a.GrowthRate();
			int bShipCount = b.NumShips();
			int difference = aShipCount - bShipCount;




		}

		return null;
	}


	public State15(PlanetWars15 pw, State15 previousState) {
		this(pw);
		this.previousState = previousState;
	}


	public State15(PlanetWars15 pw) {
		this.pw = pw;
	}


	public static ArrayList<State15> getStatePermutations(State15 state) {

		ArrayList<State15> result = new ArrayList<State15>();

		for (Planet15 source : state.getPlanetWars().MyPlanets()) {

			for (Planet15 target : state.getPlanetWars().Planets()) {
                SimulatedPlanetWarsParallel15 oldspw = (SimulatedPlanetWarsParallel15)state.getPlanetWars();
                SimulatedPlanetWarsParallel15 spw = new SimulatedPlanetWarsParallel15(oldspw, oldspw.player);
				spw.IssueOrder(source, target);
				Action15 action = new Action15(source, target);
				result.add(new State15(spw, state, action));
			}
		}

		return result;
	}


	public PlanetWars15 getPlanetWars() {
		return this.pw;
	}


	public Action15 getAction() {
		return action;
	}


	public void setAction(Action15 action) {
		this.action = action;
	}


	public State15 getPreviousState() {
		return this.previousState;
	}


	public boolean hasPreviousState() {
		return previousState != null;
	}
}
