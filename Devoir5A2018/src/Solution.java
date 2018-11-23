

public class Solution {
	
	private double distance;
	private Integer[] grapheVille;
	private Instance instanceInitiale;
	
	public Solution()
	{
		distance = Double.MAX_VALUE;
	}
	
	public Solution(int nbVille) {
		grapheVille = new Integer[nbVille];
	}
	
	public Solution(Integer[] grapheVille,Instance instanceInitiale) {
		this.grapheVille = grapheVille.clone();
		this.instanceInitiale = instanceInitiale;
		cout();
		
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Integer[] getGrapheVille() {
		return grapheVille;
	}

	public void setGrapheVille(Integer[] grapheVille) {
		this.grapheVille = grapheVille.clone();
	}
	
	public void cout()
	{
		double distance = 0;
		
		for (int arret = 0; arret <= grapheVille.length-2; arret++)
		{
			distance += instanceInitiale.getMatriceAdj()[grapheVille[arret]][grapheVille[arret+1]];
			
		}
		this.distance = distance;
	}
	
	public Integer[] swap(int i, int j){
		int temp = grapheVille[i];
		grapheVille[i] = grapheVille[j];
		grapheVille[j] = temp;
		return grapheVille;
	}
	
	@Override
	public Solution clone() {
		Solution clone = new Solution(grapheVille,instanceInitiale);
		return clone;
	}
	
	@Override
	public String toString() {
		String affichage = "Voici le meilleur chemin : \n";
		for (int i = 0; i < grapheVille.length; i++)
		{
			affichage += "\t "+grapheVille[i];
		}
		affichage += "\n la distance est de : "+distance;
		return affichage;
	}
	
	
	
}
