/**
 * Contains the action; a combination of source and target planet. Purely for easy returning.
 *
 * @author Jelmer Mulder
 * @author Sebastian Österlund
 * @author Yoran Sturkenboom
 *         Date: 13/01/14
 */
public class Action15 {

	Planet15 source, target;


	public Action15(Planet15 source, Planet15 target) {
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


	@Override
	public String toString() {
		return (source == null || target == null ? "Game over" : String.format("{%s,%s}", source.PlanetID(), target.PlanetID()));
	}


	Action15 getAction() {
		return new Action15((Planet15) source.clone(),(Planet15) target.clone());
	}
}
