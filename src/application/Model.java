package application;

import java.util.LinkedList;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import bean.Connessione;
import bean.Fermata;
import db.Dao;

public class Model {
	
	private Dao dao = new Dao();
	private UndirectedGraph<Fermata, DefaultEdge> grafo ;
	
	public List<Fermata> getAllFermate(){
		List<Fermata> allf= new LinkedList<>();
		allf= dao.getAllf();
		return allf;
	}

	public List<Connessione> getAllConn(){
		List<Connessione> allc = dao.getAllC();
		return allc;
	}
	
	
	public void buildGraph(){  //parte
		grafo = new SimpleGraph <Fermata, DefaultEdge> (DefaultEdge.class);
		Graphs.addAllVertices(grafo,  getAllFermate());
		for(Connessione c : getAllConn()){
			for(Fermata v1 : grafo.vertexSet()){
				for(Fermata v2 : grafo.vertexSet()){
			    	if(c.getIdStazA()==v1.getIdFermata() && c.getIdStazP()== v2.getIdFermata()){
			    		grafo.addEdge(v1,  v2);
			       	}
				}
			}
			
		}
		System.out.println(grafo.toString());		
	}
	
	
	public List<Fermata> getCamminoMinimo(Fermata start, Fermata end){   //vedi fermate intermedie   //ok
		if(start!=null && end!=null){
			if(!start.equals(end)){
				DijkstraShortestPath<Fermata, DefaultEdge> di = new DijkstraShortestPath<Fermata, DefaultEdge>(grafo, start, end);
				GraphPath path= di.getPath();
				if(path==null){
					System.out.println("null");
					return null;
				}
				System.out.println(Graphs.getPathVertexList(path));
				return Graphs.getPathVertexList(path);
			}
		}
		System.out.println("null");
		return null;
	}
	
	
	
	public static void main (String [] args){
		Model m = new Model();
		m.buildGraph();
	}
}
