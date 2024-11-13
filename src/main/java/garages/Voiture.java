package garages;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.PrintStream;
import java.util.*;

@RequiredArgsConstructor
@ToString
public class Voiture {

	@Getter
	@NonNull
	private final String immatriculation;
	@ToString.Exclude // On ne veut pas afficher les stationnements dans toString
	private final List<Stationnement> myStationnements = new LinkedList<>();

	/**
	 * Fait rentrer la voiture au garage
	 * Précondition : la voiture ne doit pas être déjà dans un garage
	 *
	 * @param g le garage où la voiture va stationner
	 * @throws IllegalStateException Si déjà dans un garage
	 */
	public void entreAuGarage(Garage g) throws IllegalStateException {
		// Vérifie si la voiture est déjà dans un garage
		if (estDansUnGarage()) {
			throw new IllegalStateException("La voiture est déjà dans un garage.");
		}

		// Crée un nouveau stationnement et l'ajoute à la liste des stationnements
		Stationnement s = new Stationnement(this, g);
		myStationnements.add(s);
	}

	/**
	 * Fait sortir la voiture du garage
	 * Précondition : la voiture doit être dans un garage
	 *
	 * @throws IllegalStateException si la voiture n'est pas dans un garage
	 */
	public void sortDuGarage() throws IllegalStateException {
		// Vérifie si la voiture est dans un garage
		if (!estDansUnGarage()) {
			throw new IllegalStateException("La voiture n'est pas actuellement dans un garage.");
		}

		// Trouve le dernier stationnement et termine-le
		Stationnement dernierStationnement = myStationnements.get(myStationnements.size() - 1);
		dernierStationnement.terminer();
	}


	/**
	 * Calcule l'ensemble des garages visités par cette voiture
	 * 
	 * @return l'ensemble des garages visités par cette voiture
	 */
	public Set<Garage> garagesVisites() {
		Set<Garage> garages = new HashSet<>();

		for (Stationnement s : myStationnements) {
			garages.add(s.getGarageVisite());
		}

		return garages;
	}


	/**
	 * Détermine si la voiture est actuellement dans un garage
	 * 
	 * @return vrai si la voiture est dans un garage, faux sinon
	 */
	public boolean estDansUnGarage() {
		// Si la liste est vide, la voiture n'est pas dans un garage
		if (myStationnements.isEmpty()) {
			return false;
		}
		// Récupère le dernier stationnement
		Stationnement dernierStationnement = myStationnements.get(myStationnements.size() - 1);
		// Retourne true si le dernier stationnement est en cours, sinon false
		return dernierStationnement.estEnCours();
	}



	/**
	 * Pour chaque garage visité, imprime le nom de ce garage suivi de la liste des
	 * dates d'entrée / sortie dans ce garage
	 * <br>
	 * Exemple :
	 * 
	 * <pre>
	 * Garage Castres:
	 *		Stationnement{ entree=28/01/2019, sortie=28/01/2019 }
	 *		Stationnement{ entree=28/01/2019, en cours }
	 *  Garage Albi:
	 *		Stationnement{ entree=28/01/2019, sortie=28/01/2019 }
	 * </pre>
	 *
	 * @param out l'endroit où imprimer (ex: System.out pour imprimer dans la
	 *            console)
	 */
	public void imprimeStationnements(PrintStream out) {
		Map<Garage, List<Stationnement>> stationnementsParGarage = new HashMap<>();

		for (Stationnement s : myStationnements) {
			stationnementsParGarage
					.computeIfAbsent(s.getGarageVisite(), k -> new ArrayList<>())
					.add(s);
		}

		for (Map.Entry<Garage, List<Stationnement>> entry : stationnementsParGarage.entrySet()) {
			Garage garage = entry.getKey();
			List<Stationnement> stationnements = entry.getValue();

			// Utilisation de `out.println` pour diriger la sortie vers le PrintStream spécifié
			out.println("Garage " + garage.getName() + ":");

			for (Stationnement s : stationnements) {
				out.println("\t" + s);
			}
		}
	}




}
