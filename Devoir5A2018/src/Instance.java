
import java.util.Random;


public class Instance {

	private double[][] matriceAdj;
	private Integer[] grapheVille;
	private final int MAX = 9000;
	private final double PAS_DE_CHEMIN = Double.POSITIVE_INFINITY;
	
	public Instance()
	{
		Random random = new Random();
		int taille = random.nextInt(MAX)+2;
		matriceAdj = new double [taille][taille];
		grapheVille = new Integer[taille];
		
		for (int ligne = 0; ligne < taille; ligne++)
		{
			for (int colonne = 0; colonne <= ligne; colonne++)
			{
				if (ligne == colonne)
				{
					matriceAdj[ligne][colonne] = PAS_DE_CHEMIN;
				}
				else
				{
					matriceAdj[ligne][colonne] = random.nextInt(10);
					matriceAdj[colonne][ligne] = matriceAdj[ligne][colonne];
					int aléatoire = random.nextInt(10);
					
					if (aléatoire == 0)
					{
						matriceAdj[colonne][ligne] = PAS_DE_CHEMIN;
						matriceAdj[ligne][colonne] = PAS_DE_CHEMIN;
					}
				}
			}
			grapheVille[ligne] = ligne;
		}
	}
	
	public double[][] getMatriceAdj() {
		return matriceAdj;
	}
	
	public Integer[] getGrapheVille() {
		return grapheVille;
	}
	
	@Override
	public String toString()
	{
		String affichage = new String();
		for (int ligne =0; ligne < grapheVille.length; ligne++)
		{
			affichage += "\t \t"+ligne;
		}
		affichage += "\n \n";
		for (int ligne = 0; ligne < grapheVille.length ; ligne++)
		{
			affichage += ligne+" | ";
			for (int colonne = 0; colonne < grapheVille.length; colonne++)
			{
				affichage += "\t \t" + matriceAdj[ligne][colonne];
			}
			affichage += " \n ";
		}
		return affichage;
	}
	
	public static void main(String[] args)
	{
		int n = 10;
		int i = 0;
		Solution solutionF = new Solution();
		
		System.out.println("LocalSearch \t Génétique \t ForceBrute");
		for (int boucle = 0; boucle < 10; boucle ++)
		{
			Instance instance = new Instance();
			System.out.println("nombre de ville : "+instance.getGrapheVille().length);
			/*System.out.println(instance.toString());
			System.out.println(instance.getGrapheVille().length);*/
			
			long startL = System.nanoTime();
			LocalSearch localSearch = new LocalSearch(instance);
			Solution solutionL = localSearch.localSearch();
			long endL = System.nanoTime();
			long traitementL = endL - startL;
			startL = System.nanoTime();
			i = 0;
			
			while(i < n)
			{
				i++;
			}
			endL = System.nanoTime();
			long servitudeL = startL - endL;
			long tempL = (traitementL-servitudeL)/n;
			//System.out.println(solutionL.toString());
			
			long startG = System.nanoTime();
			Genetique genetique = new Genetique(instance);
			Solution solutionG = genetique.algoGenetique(2, 50, 70);
			long endG = System.nanoTime();
			long traitementG = endG - startG;
			startG = System.nanoTime();
			i = 0;
			
			while(i < n)
			{
				i++;
			}
			endG = System.nanoTime();
			long servitudeG = startG - endG;
			long tempG = (traitementG-servitudeG)/n;
			//System.out.println(solutionG.toString());
			long tempF = 0;
			if (instance.getGrapheVille().length <= 12)
			{
				long startF = System.nanoTime();
				ForceBrute forceBrute = new ForceBrute(instance);
				solutionF = forceBrute.forceBrute();
				long endF = System.nanoTime();
				long traitementF = endF-startF;
				startF = System.nanoTime();
				i = 0;
				while(i < n)
				{
					i++;
				}
				endF = System.nanoTime();
				long servitudeF = startF - endF;
				tempF = (traitementF-servitudeF)/n;
			}
			System.out.println("Distance : "+ solutionL.getDistance() + " \t " + solutionG.getDistance() + " \t " + solutionF.getDistance());
			System.out.println("Temps : "+ tempL + " \t " + tempG + " \t " + tempF);
		}
		
	}
	
	
}
