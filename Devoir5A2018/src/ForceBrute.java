import java.util.Arrays;


public class ForceBrute {
	
	private Solution initialSolution;
	
	public ForceBrute(Instance instance)
	{
		this.initialSolution = new Solution(instance.getGrapheVille(),instance);
	}
	
	public Solution prochainePermutation(Solution solution)
	{
		int j = solution.getGrapheVille().length-2;
		int k = solution.getGrapheVille().length-1;
		
		while (solution.getGrapheVille()[j] > solution.getGrapheVille()[j+1] && j > 0) 
		{
			j = j-1;	
		}
			
		while (solution.getGrapheVille()[j] > solution.getGrapheVille()[k]) 
		{
			k = k-1;
		}
		
		solution.setGrapheVille(solution.swap(j, k));
		if (j == k)
		{
			k = solution.getGrapheVille().length-1;
			j = 0;
		}
		else
		{
			k = solution.getGrapheVille().length-1;
			j = j+1;
		}
		
		
		while (k > j) 
		{
			solution.setGrapheVille(solution.swap(j, k));
			k = k-1;
			j = j+1;
		}

		return solution;
	}
	
	public Solution forceBrute()
	{
		double min = Double.POSITIVE_INFINITY;
		double coutTemp = 0;
		
		Solution solutionOrigine = initialSolution.clone();
		Solution solutionTemp = solutionOrigine.clone();
 		Solution solutionFinal = null;
 
		do {
			solutionTemp.cout();
			coutTemp = solutionTemp.getDistance();
			
			if (coutTemp < min)
			{
				min = coutTemp;
				solutionFinal = solutionTemp.clone();
			}
			solutionTemp = prochainePermutation(solutionTemp);
		} while (!(Arrays.equals(solutionTemp.getGrapheVille(), solutionOrigine.getGrapheVille())));
		
		return solutionFinal;
	}
}
