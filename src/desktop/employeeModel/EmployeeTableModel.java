package desktop.employeeModel;

import javax.swing.table.AbstractTableModel;

import core.employee.Employee;
import core.employee.IEmployeeService;

public class EmployeeTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -2875341659571747028L;
	private static final String[] columns = new String[] {"Id", "Name", "Surname", "E-mail", "Password"};
	private IEmployeeService service;
	
	public EmployeeTableModel(IEmployeeService service) {
		this.service = service;
	}

	@Override
	public int getRowCount() {
		return service.getAll().size();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		return columns[columnIndex];
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 0) {
			return Integer.class;
		} else if (columnIndex == 1) {
			return String.class;
		} else if (columnIndex == 2) {
			return String.class;
		} else if (columnIndex == 3) {
			return String.class;
		} else if (columnIndex == 4) {
			return String.class;
		} else {
			return null;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Employee e = service.getAll().get(rowIndex);
		
		if (columnIndex == 0) {
			return e.getId();
		} else if (columnIndex == 1) {
			return e.getName();
		} else if (columnIndex == 2) {
			return e.getSurname();
		} else if (columnIndex == 3) {
			return e.getAccount().getEmail();
		} else if (columnIndex == 4) {
			return e.getAccount().getPassword();
		} else {
			return null;
		}
	}
}
