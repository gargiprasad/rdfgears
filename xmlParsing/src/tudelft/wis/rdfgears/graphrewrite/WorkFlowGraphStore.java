package tudelft.wis.rdfgears.graphrewrite;

public class WorkFlowGraphStore {
	WorkFlowGraph wfg;
	WorkFlowGraphStore(String tag, String val){
		if (tag == "rdfgears"){
			wfg= new WorkFlowGraph();
		}
		System.out.println(val);
		if (tag == "metadata"){
			wfg= new WorkFlowGraph();
		}
		System.out.println(val);
	}
}
