

public class LocalSearch {
	private Solution initialSolution;
	
	public LocalSearch(Instance instance)
	{
		this.initialSolution = new Solution(instance.getGrapheVille(),instance);
	}
	
	public Solution localSearch(){
        // Create some basics solutions
    	Solution bestNeighbourSolution = initialSolution.clone();
    	Solution bestSolution = initialSolution.clone();
        boolean continu = true;
        
    	while (continu) {
			// Compare the objectives
    		for (int i = 0; i < (initialSolution.getGrapheVille().length); i++) {
    			for (int j = i+1; j < (initialSolution.getGrapheVille().length); j++) {
    				Solution neightbourSolution = initialSolution.clone();
    				neightbourSolution.setGrapheVille(neightbourSolution.swap(i,j));;
    				neightbourSolution.cout();

    				if (neightbourSolution.getDistance() < bestNeighbourSolution.getDistance()) {
    					bestNeighbourSolution = neightbourSolution.clone();
    				}
    			}
    		}
    		if (bestNeighbourSolution.getDistance() < bestSolution.getDistance())
    		{
    			bestSolution = bestNeighbourSolution.clone();
    		}
    		else
    		{
    			continu = false;
    		}
    	}
    	return bestSolution;   
	}
}