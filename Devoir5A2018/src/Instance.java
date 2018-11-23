import java.util.Random;

public class Instance {

	private double[][] matriceAdj;
	private Integer[] grapheVille;
	private final int MAX = 1000;
	private final double PAS_DE_CHEMIN = Double.POSITIVE_INFINITY;
	
	public Instance()
	{
		Random random = new Random();
		int taille = MAX;
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
					/*int aléatoire = random.nextInt(10);
					
					if (aléatoire == 0)
					{
						matriceAdj[colonne][ligne] = PAS_DE_CHEMIN;
						matriceAdj[ligne][colonne] = PAS_DE_CHEMIN;
					}*/
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
		Instance instance = new Instance();
		/*System.out.println(instance.toString());
		System.out.println(instance.getGrapheVille().length);*/
		if (instance.getGrapheVille().length < 12)
		{
			ForceBrute forceBrute = new ForceBrute(instance);
			Solution solutionF = forceBrute.forceBrute();
			System.out.println(solutionF.toString());
		}
		System.out.println("ici");
		LocalSearch localSearch = new LocalSearch(instance);
		Solution solutionL = localSearch.localSearch();
		//System.out.println(solutionL.toString());
		System.out.println("ici deux");
		Genetique genetique = new Genetique(instance);
		Solution solutionG = genetique.algoGenetique(4, 100, 40);
		//System.out.println(solutionG.toString());
	}
	
	
}
