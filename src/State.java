import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by sirmc on 18/01/14.
 */
public class State {

	private PlanetWars pw;
	private State previousState;
	private Action action;


	public State(PlanetWars pw, State previousState, Action action) {
		this(pw, previousState);
		this.setAction(action);
		this.action = reconstructAction(previousState.pw, pw);
	}


	public static Action reconstructAction(PlanetWars initial, PlanetWars result) {
		for (int i = 0; i < initial.Planets().size(); i++) {
			Planet a = initial.Planets().get(i);
			Planet b = result.Planets().get(i);

			int aShipCount = a.NumShips() + a.GrowthRate();
			int bShipCount = b.NumShips();
			int difference = aShipCount - bShipCount;




		}

		return null;
	}


	public State(PlanetWars pw, State previousState) {
		this(pw);
		this.previousState = previousState;
	}


	public State(PlanetWars pw) {
		this.pw = pw;
	}


	public static ArrayList<State> getStatePermutations(State state) {

		ArrayList<State> result = new ArrayList<State>();

		for (Planet source : state.getPlanetWars().MyPlanets()) {

			for (Planet target : state.getPlanetWars().Planets()) {
				SimulatedPlanetWars spw = new SimulatedPlanetWars(state.getPlanetWars());
				spw.IssueOrder(source, target);
				Action action = new Action(source, target);
				result.add(new State(spw, state, action));
			}
		}

		return result;
	}


	public PlanetWars getPlanetWars() {
		return this.pw;
	}


	public Action getAction() {
		return action;
	}


	public void setAction(Action action) {
		this.action = action;
	}


	public State getPreviousState() {
		return this.previousState;
	}


	public boolean hasPreviousState() {
		return previousState != null;
	}
}
