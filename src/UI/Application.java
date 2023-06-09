package UI;

import java.sql.Connection;
import java.sql.DriverManager;

import DAO.PorudzbinaDAO;
import DAO.ProizvodDAO;
import DatabaseDAO.DatabasePorudzbinaDAO;
import DatabaseDAO.DatabaseProizvodDAO;
import Util.Meni;
import Util.Meni.FunkcionalnaStavkaMenija;
import Util.Meni.IzlaznaStavkaMenija;
import Util.Meni.StavkaMenija;

public class Application {

	private static void initDatabase() throws Exception {
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/porudzbine?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Europe/Belgrade",
				"root", "root");

		ProizvodDAO proizvodDAO = new DatabaseProizvodDAO(conn);
		PorudzbinaDAO porudzbinaDAO = new DatabasePorudzbinaDAO(conn, proizvodDAO);

		ProizvodUI.setProizvodDAO(proizvodDAO);
		PorudzbinaUI.setPorudzbinaDAO(porudzbinaDAO);
		IzvestajUI.setProizvodDAO(proizvodDAO);
		IzvestajUI.setPorudzbinaDAO(porudzbinaDAO);

	}

	static {
		try {
			initDatabase();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Greška pri povezivanju sa izvorom podataka!");

			System.exit(1);
		}
	}

	public static void main(String[] args) throws Exception {
		Meni.pokreni("Porudzbine", new StavkaMenija[] { new IzlaznaStavkaMenija("Izlaz"),
				new FunkcionalnaStavkaMenija("Prikaz svih proizvoda") {

					@Override
					public void izvrsi() {
						ProizvodUI.prikazSvihProizvoda();
					}

				}, new FunkcionalnaStavkaMenija("Prikaz svih porudzbina") {

					@Override
					public void izvrsi() {
						PorudzbinaUI.prikazSvihPorudzbina();
					}

				}, new FunkcionalnaStavkaMenija("Dodavanje porudzbine") {

					@Override
					public void izvrsi() {
						PorudzbinaUI.novaPorudzbina();
					}

				}, new FunkcionalnaStavkaMenija("Izvestaj") {

					@Override
					public void izvrsi() {
						IzvestajUI.izvestaj();
					}

				} });
	}

}
