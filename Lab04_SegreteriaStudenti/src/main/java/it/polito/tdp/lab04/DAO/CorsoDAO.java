package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * "
				+ "FROM corso "
				+ "ORDER BY nome ASC";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				Corso c = new Corso(codins,numeroCrediti,nome,periodoDidattico);
				corsi.add(c);
			}

			rs.close();
			st.close();
			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore nella lettura del corso", e);
		}
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		
		String sql = "SELECT s.matricola, s.cognome, s.nome, s.CDS "
				+ "FROM studente s, iscrizione i "
				+ "WHERE s.matricola = i.matricola "
				+ "AND i.codins = ?";
		
		List<Studente> result = new LinkedList<Studente>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodice());
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Studente s = new Studente(rs.getInt("matricola"), rs.getString("nome"), 
						rs.getString("cognome"), rs.getString("CDS"));
				result.add(s);
			}

			rs.close();
			st.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		
		final String sql= "INSERT INTO iscrizione (matricola,codins) "
				+ "					VALUES (?,?);";
		
		try {
			 Connection conn = ConnectDB.getConnection();
			 PreparedStatement st = conn.prepareStatement(sql);
			 st.setInt(1,studente.getMatricola());
			 st.setString(2,corso.getCodice()); 
			 ResultSet rs= st.executeQuery();

			 rs.close();
			 st.close();
			 conn.close();
			 return true;
		}
		catch(Exception e) {
			throw new RuntimeException("Errore nell'iscrizione dello studente!",e);
		}
	}
	
	public Studente getStudente(int matricola) {
		
		final String sql = "SELECT * "
				+ "FROM studente "
				+ "WHERE matricola=?";
		
		Studente s=null;
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet rs = st.executeQuery();
			rs.next();
			
			Integer matr = rs.getInt("matricola");
			String cognome = rs.getString("cognome");
			String nome = rs.getString("nome");
			String cds = rs.getString("CDS");
			
			s = new Studente(matr,nome,cognome,cds);
			
			rs.close();
			st.close();
			conn.close();
			
			return s;
		}
		catch(SQLException se) {
			throw new RuntimeException("Errore nella lettura dello studente",se);
		}
	}
	
	public List<Studente> getStudentiIscritti() {
		final String sql = "SELECT DISTINCT s.matricola, s.cognome, s.nome,s.CDS "
				+ "FROM studente s, iscrizione i "
				+ "WHERE s.matricola=i.matricola";
		
		List<Studente> elenco = new LinkedList<Studente>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Integer matricola = rs.getInt("matricola");
				String cognome = rs.getString("cognome");
				String nome = rs.getString("nome");
				String cds = rs.getString("CDS");
				
				Studente s = new Studente(matricola,nome,cognome,cds);
				elenco.add(s);
			}

			rs.close();
			st.close();
			conn.close();
			
			return elenco;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore nella lettura degli studenti", e);
		}
	}
	
	public List<Corso> getIscrizioniStudente(Studente s) {
		final String sql = "SELECT c.codins, c.crediti, c.nome, c.pd "
				+ "FROM studente s, iscrizione i, corso c "
				+ "WHERE s.matricola=i.matricola AND i.codins=c.codins AND s.matricola=?";
		
		List<Corso> elenco = new LinkedList<Corso>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, s.getMatricola());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				Corso c = new Corso(codins,numeroCrediti,nome,periodoDidattico);
				elenco.add(c);
			}
		}
		catch(SQLException e) {
			throw new RuntimeException("Errore nella lettura dei corsi dello studente",e);
		}
		return elenco;
	}
	
	public boolean cercaIscrizione(Studente s, Corso c) {
		final String sql = "SELECT i.matricola, i.codins "
				+ "FROM iscrizione i "
				+ "WHERE i.matricola=? AND i.codins=?";		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, s.getMatricola());
			st.setString(2, c.getCodice());
			ResultSet rs = st.executeQuery();
			
			if(rs.next())
				return true;
			else 
				return false;
		}
		catch(SQLException e) {
			throw new RuntimeException("Errore nella ricerca dei corsi dello studente",e);
		}
	}

}
