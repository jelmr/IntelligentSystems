/**
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 13/01/14
 */
public class Action {

	Planet source, target;


	public Action(Planet source, Planet target) {
		this.source = source;
		this.target = target;
	}

	public boolean isValid(){
		return source != null && target != null;
	}


}
