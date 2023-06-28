package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	PremierLeagueDAO dao;
	Graph<Match,DefaultWeightedEdge> grafo;
	List<Match> vertici;
	List<Arco> archi;
	Map<Integer,Match> mappaPartite;
	double nMigliore;
	List<Match> migliore;
	
	public Model() {
		dao = new PremierLeagueDAO();
	}

	public void creaGrafo(int minuti, int mese) {
		grafo = new SimpleWeightedGraph<Match,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		vertici = dao.getAllVertici(mese);
		mappaPartite = new HashMap<Integer,Match>();
		for(Match m: vertici) {
			grafo.addVertex(m);
			mappaPartite.put(m.matchID, m);
		}
		archi = dao.getAllArchi(mappaPartite, minuti);
		for(Arco a: archi) {
			if(a != null && a.partenza != null && a.arrivo != null) {
				Graphs.addEdgeWithVertices(grafo, a.partenza, a.arrivo, a.peso);
			}
		}
	}

	public Graph<Match, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public List<Arco> connessioneMassima() {
		double max = 0;
		List<Arco> partite = new ArrayList<Arco>();
		for(Arco a: archi) {
			if(a.peso > max) {
				partite.clear();
				max = a.peso;
				partite.add(a);
			}
			else if(a.peso == max) {
				partite.add(a);
			}
		}
		return partite;
	}

	public List<Match> collegamento(Match m1, Match m2) {
		List<Match> parziale = new ArrayList<Match>();
		migliore = new ArrayList<Match>();
		nMigliore = 0;
		parziale.add(m1);
		ricorsione(parziale,m2);
		return migliore;
	}

	private void ricorsione(List<Match> parziale, Match m2) {
		Match current = parziale.get(parziale.size()-1);
		List<Match> adiacenti = Graphs.neighborListOf(grafo, current);
		if(current.equals(m2)) {
			if(peso(parziale) > nMigliore) {
				migliore = new ArrayList<Match>(parziale);
				nMigliore = peso(parziale);
			}
			return;
		}
		for(Match m: adiacenti) {
			if(!contiene(parziale,m)) {
				parziale.add(m);
				ricorsione(parziale,m2);
				parziale.remove(parziale.size()-1);
			}	
		}
		
	}

	private boolean contiene(List<Match> parziale, Match m) {
		boolean flag = false;
		if(parziale.contains(m))
			flag = true;
		for(Match mm: parziale) {
			if(mm.teamHomeID == m.teamHomeID && mm.teamAwayID == m.teamAwayID)
				flag = true;
			if(mm.teamHomeID == m.teamAwayID && mm.teamAwayID == m.teamHomeID)
				flag = true;
		}
		return flag;
	}

	private double peso(List<Match> parziale) {
		double somma = 0;
		for(DefaultWeightedEdge e: grafo.edgeSet()) {
			if(parziale.contains(grafo.getEdgeSource(e)) && parziale.contains(grafo.getEdgeTarget(e)))
				somma = somma + grafo.getEdgeWeight(e);
		}
		return somma;
	}

	public List<Match> getMigliore() {
		return migliore;
	}

	public double getnMigliore() {
		return nMigliore;
	}

}
