package core.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import core.account.Account;
import core.booth.Booth;
import core.employee.Employee;
import core.malfunction.Malfunction;
import core.payment.Payment;
import core.pricelist.Pricelist;
import core.pricelist.entry.PricelistEntry;
import core.station.Station;
import core.station.location.Location;
import core.ticket.Ticket;

public class MasterXMLRepo {
	@XStreamOmitField
	private String directory;
	@XStreamOmitField
	private String filename;
	@XStreamOmitField
	private String fullpath;
	@XStreamOmitField
	private XStream xstream;

	private List<Employee> employees = new ArrayList<Employee>();
	private List<Account> accounts = new ArrayList<Account>();
	private List<Location> locations = new ArrayList<Location>();
	private List<Station> stations = new ArrayList<Station>();
	private List<Booth> booths = new ArrayList<Booth>();
	private List<PricelistEntry> pricelistEntries = new ArrayList<PricelistEntry>();
	private List<Pricelist> pricelists = new ArrayList<Pricelist>();
	private List<Malfunction> malfunctions = new ArrayList<Malfunction>();
	private List<Ticket> tickets = new ArrayList<Ticket>();
	private List<Payment> payments = new ArrayList<Payment>();

	public MasterXMLRepo(String directory, String filename) {
		this.directory = directory;
		this.filename = filename;
		this.fullpath = this.directory + File.separator + this.filename;

		initXStream();
		load();
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public List<Station> getStations() {
		return stations;
	}
	
	public List<Booth> getBooths() {
		return booths;
	}

	public List<PricelistEntry> getPricelistEntries() {
		return pricelistEntries;
	}

	public List<Pricelist> getPricelists() {
		return pricelists;
	}

	public List<Malfunction> getMalfunctions() {
		return malfunctions;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void save() {
		try (Writer writer = new BufferedWriter(new FileWriter(new File(fullpath)))) {
			writer.write(xstream.toXML(this));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void load() {
		try {
			new File(this.directory).mkdirs();
			new File(this.fullpath).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (Reader reader = new BufferedReader(new FileReader(new File(fullpath)))) {
			xstream.fromXML(reader, this);
		} catch (XStreamException e) {
			// Do nothing, this object is already default-initialized.
			// The file will sort itself out on next save.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initXStream() {
		xstream = new XStream(new DomDriver());
		xstream.autodetectAnnotations(true);
		xstream.addPermission(AnyTypePermission.ANY);
	}
}
