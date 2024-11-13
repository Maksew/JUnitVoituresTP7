package garages;

import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Représente un garage où les voitures peuvent être stationnées.
 */
@Setter
@Getter
@RequiredArgsConstructor
public class Garage {

	/**
	 * Le nom du garage.
	 * Ce champ ne peut pas être null.
	 */
	@NonNull
	private String name;

	/**
	 * Retourne une représentation sous forme de chaîne de caractères du garage.
	 *
	 * @return une chaîne de caractères représentant le garage, incluant uniquement son nom
	 */
	@Override
	public String toString() {
		return name;
	}
}
