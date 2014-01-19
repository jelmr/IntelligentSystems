import java.util.ArrayList;

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
    }

    public State(PlanetWars pw, State previousState) {
        this.pw = pw;
        this.previousState = previousState;
    }

    public State(PlanetWars pw) {
        this.pw = pw;
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



    public PlanetWars getPlanetWars() {
        return this.pw;
    }

    public boolean hasPreviousState() {
        if (previousState != null) {
            return true;
        }
        return false;
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
}
