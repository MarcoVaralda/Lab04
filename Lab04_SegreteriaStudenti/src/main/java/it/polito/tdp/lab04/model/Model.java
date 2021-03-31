package it.polito.tdp.lab04.model;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;

public class Model {
	
	CorsoDAO dao = new CorsoDAO();
	
	public List<Corso> getTuttiICorsi() {
		List<Corso> elenco = new LinkedList<Corso>();
		Corso cc = new Corso("",null,"",null);
		elenco.add(cc);
		elenco.addAll(dao.getTuttiICorsi());
		return elenco;		
	}
	
	public Studente getStudente(int matricola) {
		return dao.getStudente(matricola);
	}
	
	public Corso getCorso(String codice) {
		for(Corso c : dao.getTuttiICorsi()) 
			if(c.getCodice().equals(codice))
				return c;
		return null;
	}
	
	public List<Studente> getStudentiIscritti() {
		return dao.getStudentiIscritti();
	}
	
	public List<Studente> getStudentiIscrittiAlCorso(String codice) {
		Corso c = new Corso(codice,null,null,null);
		return dao.getStudentiIscrittiAlCorso(c);
	}
	
	public List<Corso> getIscrizioniStudente(int matricola) {
		Studente s = new Studente(matricola,null,null,null);
		return dao.getIscrizioniStudente(s);
	}
	
	public boolean cercaIscrizione(int matricola, String codice) {
		Studente s = new Studente(matricola,null,null,null);
		Corso c = new Corso(codice,null,null,null);
		return dao.cercaIscrizione(s, c);
	}

}
