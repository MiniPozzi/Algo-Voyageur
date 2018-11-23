import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;


public class Genetique {
	private Solution initialSolution;
	
	public Genetique(Instance instance)
	{
		this.initialSolution = new Solution(instance.getGrapheVille(),instance);
	}

	/**
	 *  Echange deux elements d'une solution de manière aléatoire
	 * Entrées :
	 *   inst : instance executée
	 *   NbreIteration : entier representant le nombre de permutation que l'on souhaite effectuer
	 *   TabInt: Tableau 2D contenant d'entiers
	 */
	public Solution melangeAleatElementGen(int NbreIteration)
	{
		Solution newSolution = initialSolution.clone();
		int indicerand1 = 0;
		int indicerand2 = 0;

		for (int indiceIter = 0; indiceIter < NbreIteration; indiceIter++)
		{
			// Mis en place de 2 random différents
			Random random = new Random();
			indicerand1 = random.nextInt(newSolution.getGrapheVille().length);
			indicerand2 = random.nextInt(newSolution.getGrapheVille().length);

			while(indicerand1 == indicerand2)
			{
				indicerand2 = random.nextInt(newSolution.getGrapheVille().length);
			}

			// Echange deux objet dans la liste
			newSolution.swap(indicerand1, indicerand2);

		}
		return newSolution;
	}

	/**
	 *  Initialisation de ParGen représentant les solutions au départ (tableau binaire donné par traitement du codage direct)
	 * Entrées :
	 *   inst : instance executée
	 *   NbreSol : entier representant le nombre de solutions demandé
	 *   ParGen: Tableau 2D contenant des entier. ParGen[i] est une solution ou i est un entier
	 */
	public void aleatSolDirect(Solution[] parGen, int nbreSol, int nbIteration)
	{
		for (int indiceParent = 0; indiceParent < nbreSol; indiceParent++)
		{
			for (int indiceObj = 0; indiceObj < initialSolution.getGrapheVille().length; indiceObj++)
			{
				
				parGen[indiceParent] = melangeAleatElementGen(nbIteration).clone();
			}
			parGen[indiceParent].cout();
		}
	}
	
	/**
	 * 
	 * @param solution
	 */
	public void doublon(Solution solution)
	{
		List<Integer> list = Arrays.asList(solution.getGrapheVille()); // transforme le tableau en liste
		Set<Integer> unset = new TreeSet<Integer>(list); // transforme la liste en Set 
		int doublon = solution.getGrapheVille().length - unset.size();
		
		if (doublon == 0)
		{
			solution.cout();
		}
		else
		{
			solution.setDistance(-1);
		}
	}
	
	/**
	* Selection Parents avec Methode "Tournoi"  pour codage direct
	* Entrées :
	*   inst : instance executée
	*   NbreSol : entier representant le nombre de solutions demandé
	*   ParGen: Tableau 2D contenant des entier. ParGen[i] est une solution ou i est un entier
	*   EnfGen: Tableau 2D contenant des entier. EnfGen[i] est une solution ou i est un entier
	*/
	public void selectPTournoiD(int NbreSol, Solution[] parGen, Solution[] enfGen)
	{
		Random random = new Random();
		Solution[] tmp = new Solution[NbreSol];
	    Solution parent1 = new Solution();
	    Solution parent2 = new Solution();
	    Solution enfant1 = new Solution();
	    Solution enfant2 = new Solution();
	    int rand1 = 0;
	    int rand2 = 0;
	    int rand3 = 0;
	    int rand4 = 0;
	    int decoupe = 0;
	    int IndiceTournoi = 0;

	    //tant que l'on a pas fait le bon nombre d'iteration
	     for (int indiceParent= 0; indiceParent < NbreSol/2; indiceParent++){

	        rand1 = random.nextInt(NbreSol);
	        rand2 = random.nextInt(NbreSol);
	        while(rand1 == rand2){
	            rand2 = random.nextInt(NbreSol);
	        }

	        rand3 = random.nextInt(NbreSol);
	        while(rand3 == rand2 || rand3 == rand1){
	            rand3 = random.nextInt(NbreSol);
	        }

	        rand4 = random.nextInt(NbreSol);
	        while(rand4 == rand2 || rand4 == rand1 || rand4 == rand3){
	            rand4 = random.nextInt(NbreSol);
	        }

	        //Tournoi entre deux solutions random et affectation aux parents
	        //Parent1
	        if (parGen[rand1].getDistance() >= parGen[rand2].getDistance()){
	        	parent1 = parGen[rand2].clone();
	        }else if (parGen[rand1].getDistance() < parGen[rand2].getDistance()){
	        	parent1 = parGen[rand1].clone();
	        }

	        //Parent2
	        if (parGen[rand3].getDistance() >= parGen[rand4].getDistance()){
	        	parent2 = parGen[rand4].clone();
	        }else if (parGen[rand3].getDistance() < parGen[rand4].getDistance()){
	        	parent2 = parGen[rand3].clone();
	        }

	        // Affectation des enfants, decoupe les parents suivant un indice random et switch elements si on depasse cette indice
	        decoupe = random.nextInt(initialSolution.getGrapheVille().length);
	        //System.out.println("découpage  \n");
	        enfant1 = parent1.clone();
	        enfant2 = parent2.clone();
	        
	        for (int indice = decoupe; indice < initialSolution.getGrapheVille().length; indice++)
	        {
	        	enfant1.getGrapheVille()[indice] = parent2.getGrapheVille()[indice];
	        	enfant2.getGrapheVille()[indice] = parent1.getGrapheVille()[indice];
	        }
	        doublon(enfant1);
	        doublon(enfant2);

	        // repare les enfants s'ils ne passent pas la fonction objectif
	        while (enfant1.getDistance() == -1){ // ou appelle de la fonction solution valide
	            reparerSolution(enfant1);
	            doublon(enfant1);
	        }
	        
	        while (enfant2.getDistance() == -1){   // ou appelle de la fonction solution valide
	            reparerSolution(enfant2);
	            doublon(enfant2);
	        }

	        //copie des Enfants 2 à 2 comme etant des nouveaux parents dans tmp
	        tmp[IndiceTournoi] = enfant1.clone();
	        tmp[IndiceTournoi+1] = enfant2.clone();
	     
	     	IndiceTournoi = IndiceTournoi +2;
	     }

	    // Affecte les nouvelles valeurs de la population d'enfant possible à SolGen
	    for (int indiceParent = 0; indiceParent < NbreSol; indiceParent++){
	           enfGen[indiceParent] = tmp[indiceParent].clone();
	    }
	}

	/**
	 * 
	 * @param solution
	 */
	public void reparerSolution(Solution solution) {
		List<Integer> list = Arrays.asList(solution.getGrapheVille());;
		List<Integer> listDoublon = new ArrayList<Integer>();
		List<Integer> listManquant = new ArrayList<Integer>();
		
		Collections.sort(list);
		//System.out.println(list.toString());
		for (int j = 0; j < solution.getGrapheVille().length; j++)
		{
			boolean manquant = false;
			
			if (j < solution.getGrapheVille().length-1)
			{
				boolean sortir = false;
				int i = 0;
				
				if (list.get(j).equals(list.get(j+1)))
				{
					listDoublon.add(list.get(j));
				}
				while (!sortir && !manquant)
				{
					if (list.get(i) == j)
					{
						sortir = true;
					}
					else if (i == solution.getGrapheVille().length-1) {
						manquant = true;
					} 
					i++;
				}	
			}
			else
			{
				if (list.indexOf(j) != j)
				{
					manquant = true;
				}
			}
			if (manquant)
			{
				listManquant.add(j);
			}
		}
		//System.out.println(listDoublon.toString());
		//System.out.println(listManquant.toString());
		int indice = 0;
		for (Integer doublon : listDoublon)
		{	
			int compteur = 0;
			for (int parcour = 0; parcour < solution.getGrapheVille().length; parcour++)
			{
				if (solution.getGrapheVille()[parcour] == doublon)
				{
					compteur++;
				}
				if (compteur == 2)
				{
					solution.getGrapheVille()[parcour] = listManquant.get(indice);
					indice++;
					compteur = 0;
				}
			}
		}
	}

	/**
	 * Mutation Direct
	 * Entrées :
	 *   inst : instance executée
	 *   NbreSol : entier representant le nombre de solutions demandé
	 *   probaMut : Probabilité qu'une mutation apparaisse
	 *   EnfGen: Tableau 2D contenant des entier. EnfGen[i] est une solution ou i est un entier
	 */
	public void mutation(int nbreSol, Solution[] enfGen, int probaMut){
		Random random = new Random();
		//Pour chaque enfant effectue mutation si necessaire
		for (int indiceParent = 0;indiceParent < nbreSol; indiceParent++){
			int declencheMut = random.nextInt(101);
			int indiceMutation = random.nextInt(initialSolution.getGrapheVille().length);

			// Test si on declenche la mutation en fonction de probaMut en entree
			if (declencheMut >= 100 - probaMut){
				enfGen[indiceParent].swap(indiceMutation, random.nextInt(initialSolution.getGrapheVille().length));
			}
		}
	}

	/**Obtenir un structure ordonée des solutions de la meilleure à la moins bonne pour l'algo direct
	 */
	/**
	 * Modification pop courante directe
	 * Entrées :
	 *   inst : instance executée
	 *   NbreSol : entier representant le nombre de solutions demandé
	 *   ParGen: Tableau 2D contenant des entier. ParGen[i] est une solution ou i est un entier
	 *   EnfGen: Tableau 2D contenant des entier. EnfGen[i] est une solution ou i est un entier
	 * Sortie :
	 *   TabTrie : Tableau 2D contenant des entier representant les solution triées par valeur de sac
	 */
	public Solution[] triBestSol(int nbreSol, Solution[] tabSolution){
		Solution[] solutionTri = new Solution[nbreSol];
		Solution tmp = initialSolution.clone();
		int tab_en_ordre = 0;

		for (int indiceParent = 0;indiceParent < nbreSol; indiceParent++){
				solutionTri[indiceParent] = tabSolution[indiceParent].clone();
		}
		while(tab_en_ordre == 0){
			tab_en_ordre = 1;
			for(int indiceParent = 0 ; indiceParent < nbreSol-1 ; indiceParent++){
				if(solutionTri[indiceParent].getDistance() >= solutionTri[indiceParent+1].getDistance()){
					tmp = tabSolution[indiceParent].clone();
					tabSolution[indiceParent] = tabSolution[indiceParent+1].clone();
					tabSolution[indiceParent+1] = tmp.clone();
					tab_en_ordre = 0;
				}
				nbreSol--;
			}
		}
		return solutionTri;
	}

	/**
	 * Obtenir la meilleure solution d'une liste de parents
	 * Entrées :
	 *   inst : instance executée
	 *   NbreSol : entier representant le nombre de solutions demandé
	 *   TabInt: Tableau 2D contenant des entier.
	 * Sortie :
	 *   indiceBestSol : entier represent l'indice de la meilleure solution
	 */
	public int getBestSolDirect(int nbreSol, Solution[] tabSolution){
		int indiceBestSol = 0;

		for (int indiceTab = 0;indiceTab < nbreSol; indiceTab++){ 
			//System.out.println(tabSolution[indiceTab].getDistance());// Fixe limite compteur à "99" car on a pas besoin de rentrer dans la boucle pour le dernier element
				if (tabSolution[indiceBestSol].getDistance() > tabSolution[indiceTab].getDistance()){
					indiceBestSol = indiceTab;
				}
        	}
		//System.out.println(tabSolution[indiceBestSol].getDistance());
		return indiceBestSol;
	}

	/**
	 * Modification pop courante directe
	 * Entrées :
	 *   inst : instance executée
	 *   NbreSol : entier representant le nombre de solutions demandé
	 *   ParGen: Tableau 2D contenant des entier. ParGen[i] est une solution ou i est un entier
	 *   EnfGen: Tableau 2D contenant des entier. EnfGen[i] est une solution ou i est un entier
	 */
	public void modifPopCour5050D(int NbreSol, Solution[] parGen, Solution[] enfGen){
		//Principe de prendre 50 parents et 50 enfants de maniere aleatoire
		Solution[] tmp = new Solution[NbreSol];
		     
		//Prendre les 50 meilleurs parents
		for (int indiceParent = 0; indiceParent < NbreSol/2; indiceParent++){
				tmp[indiceParent] = parGen[indiceParent].clone();
		}

		//Prendre les 50 meilleurs enfants
		for (int indiceParent = 0; indiceParent < NbreSol/2; indiceParent++){
				tmp[NbreSol/2+indiceParent] = enfGen[indiceParent].clone();
		}

		// Affectation de pop courante de Parents au valeurs prise dans tmp
		for (int indiceParent = 0; indiceParent < NbreSol; indiceParent++){
				parGen[indiceParent] = tmp[indiceParent].clone();
		}
	}
	
	/**
	 * 
	 * @param nbreIter
	 * @param nbreSol
	 * @param probaMut
	 * @return
	 */
	public Solution algoGenetique(int nbreIter, int nbreSol, int probaMut){
		int indiceBestSol = 0;
		Solution res = new Solution();
		//allocation des tableaux enfants et parents
		Solution[] parGen = new Solution[nbreSol];
		Solution[] enfGen = new Solution[nbreSol];

		//Obtenir les solutions Directes
		aleatSolDirect(parGen, nbreSol, nbreIter);
		
		for (int indiceGen = 0; indiceGen < nbreIter ; indiceGen++)
		{
            selectPTournoiD(nbreSol, parGen, enfGen);
            mutation(nbreSol, enfGen, probaMut);
            modifPopCour5050D(nbreSol, parGen, enfGen);
            indiceBestSol = getBestSolDirect(nbreSol, parGen);
            if (res.getDistance() > parGen[indiceBestSol].getDistance())
            {
            	res = parGen[indiceBestSol].clone();
            }
		}
		
		return res;
	}
}
