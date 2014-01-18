/**
 * Created by sirmc on 18/01/14.
 */
public class State {

    private PlanetWars pw;
    private State previousState;

    public State(PlanetWars pw, State previousState) {
        this.pw = pw;
        this.previousState = previousState;
    }

    public State(PlanetWars pw) {
        this.pw = pw;
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
}
