package desktop.station;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import core.booth.BoothController;
import core.common.FieldEmptyException;
import core.station.IStationService;
import core.station.Station;
import core.station.StationController;
import core.station.exception.CodeAlreadyTakenException;
import core.station.location.ILocationService;
import core.station.location.Location;
import desktop.ITabbedPanel;
import net.miginfocom.swing.MigLayout;

public class StationDashboardView extends JPanel implements ITabbedPanel {
	public StationDashboardView(IStationService service, ILocationService locationService, StationController controller, BoothController boothController) {
		setLayout(new MigLayout("", "[grow]", "[grow][][][][]"));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "grow,flowy,cell 0 0");
		
		tableModel = new StationTableModel(service);
		table = new JTable(tableModel);
		table.setRowHeight(32);
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	if (table.getSelectedRow() >= 0 && table.getSelectedRowCount() == 1)
	        		tableSelectedRow(table.getSelectedRow());
	        	else
	        		tableDeselectRow();
	        }
	    });
		scrollPane.setViewportView(table);

		JLabel lblNewLabel = new JLabel("Code:");
		add(lblNewLabel, "flowx,cell 0 1");
		
		code = new JTextField();
		add(code, "cell 0 1,growx");
		code.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Type:");
		add(lblNewLabel_1, "flowx,cell 0 2");
		
		type = new JComboBox<Station.Type>(Station.Type.values());
		add(type, "cell 0 2,growx");
		
		JLabel lblNewLabel_2 = new JLabel("Location:");
		add(lblNewLabel_2, "flowx,cell 0 3");
		
		location = new JComboBox<Location>(locationService.getAll().toArray(new Location[0]));
		add(location, "cell 0 3,growx");
		
		JButton insert = new JButton("Insert");
		add(insert, "flowx,cell 0 4");
		insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert(controller);
			}
		});
		
		update = new JButton("Update");
		update.setEnabled(false);
		add(update, "cell 0 4");
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update(table.getSelectedRow(), controller);
			}
		});
		
		remove = new JButton("Remove");
		add(remove, "cell 0 4");
		remove.setEnabled(false);
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remove(table.getSelectedRow(), controller, boothController);
			}
		});
	}
	
	private void insert(StationController controller) {
		try {
			controller.add(code.getText(), (Station.Type)type.getSelectedItem(), (Location)location.getSelectedItem());
			tableModel.fireTableRowsInserted(0, 0);
		} catch (FieldEmptyException e) {
			JOptionPane.showMessageDialog(null, "Field cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (CodeAlreadyTakenException e) {
			JOptionPane.showMessageDialog(null, "Code already taken.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void update(int row, StationController controller) {
		Station station = tableModel.getStation(row);
		try {
			controller.update(station, code.getText(), (Station.Type)type.getSelectedItem(), (Location)location.getSelectedItem());
			tableModel.fireTableRowsUpdated(row, row);
		} catch (FieldEmptyException e) {
			JOptionPane.showMessageDialog(null, "Field cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (CodeAlreadyTakenException e) {
			JOptionPane.showMessageDialog(null, "Code already taken.", "Error", JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	private void remove(int row, StationController controller, BoothController boothController) {
		Station station = tableModel.getStation(row);
		try {
			
			int option = JOptionPane.showConfirmDialog(null, "This will remove all booths that belong to this station. Continue?", "Warning", JOptionPane.YES_NO_OPTION);
			
			if (option != JOptionPane.YES_OPTION) {
				return;
			}
			controller.remove(station, boothController);
			tableModel.fireTableRowsDeleted(0, 0);
		} catch (FieldEmptyException e) {
			JOptionPane.showMessageDialog(null, "Field cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private static final long serialVersionUID = 4488314020615833614L;
	private StationTableModel tableModel;
	private JTable table;
	private JTextField code;
	private JComboBox<Station.Type> type;
	private JComboBox<Location> location;
	private JButton update;
	private JButton remove;
	
	private void tableSelectedRow(int row) {
		code.setText((String) table.getValueAt(row, 1));
		type.setSelectedItem((Station.Type) table.getValueAt(row, 2));
		location.setSelectedItem(tableModel.getLocation(row));
		
		code.setEnabled(true);
		type.setEnabled(true);
		location.setEnabled(true);
		update.setEnabled(true);
		remove.setEnabled(true);
	}
	
	private void tableDeselectRow() {
		code.setText("");
		type.setSelectedItem(null);
		location.setSelectedItem(null);
		
		update.setEnabled(false);
		remove.setEnabled(false);
	}

	@Override
	public void onShow() {
		tableModel.fireTableDataChanged();
	}
}
