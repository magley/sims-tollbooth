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

import core.employeeModel.Employee;

public class MasterXMLRepo {
	@XStreamOmitField
	private String directory;
	@XStreamOmitField
	private String filename;
	@XStreamOmitField
	private String fullpath;
	@XStreamOmitField
	private XStream xstream;

	private List<Employee> employees;

	public MasterXMLRepo(String directory, String filename) {
		this.directory = directory;
		this.filename = filename;
		this.fullpath = this.directory + File.separator + this.filename;
		
		employees = new ArrayList<Employee>();
		
		initXStream();
		load();
	}
	
	public List<Employee> getEmployees() {
		return employees;
	}
	
	public void save() {
		try {
			Writer writer = new BufferedWriter(new FileWriter(new File(fullpath)));
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
		
		try {
			Reader reader = new BufferedReader(new FileReader(new File(fullpath)));
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
