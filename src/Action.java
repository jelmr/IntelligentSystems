/**
 * Contains the action; a combination of source and target planet. Purely for easy returning.
 *
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


	/**
	 * An action is valid if it contains both a source and a target planet.
	 * @return 	true: source and target are set.
	 * 			false: either source or target is null.
	 */
	public boolean isValid(){
		return source != null && target != null;
	}


}
