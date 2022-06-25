package desktop.booth;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import core.AppContext;
import core.booth.Booth;
import core.booth.BoothController;
import core.common.FieldEmptyException;
import core.station.Station;
import core.station.StationController;
import core.station.exception.CodeAlreadyTakenException;
import core.station.location.Location;

import javax.swing.JButton;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.naming.Context;
import javax.swing.AbstractListModel;

public class BoothDashboardView extends JPanel {
	private static final long serialVersionUID = -8777392419260987575L;
	private JTable boothTable;
	private JTextField code;
	private BoothTableModel tableModel;
	private JList<Station> stationList;

	public BoothDashboardView(AppContext ctx) {
		setLayout(new MigLayout("", "[191.00,grow][grow]", "[grow][][]"));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 0,grow");
		
		tableModel = new BoothTableModel(ctx.getBoothService());
		boothTable = new JTable(tableModel);
		boothTable.getTableHeader().setReorderingAllowed(false);
		boothTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		boothTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	if (boothTable.getSelectedRow() >= 0 && boothTable.getSelectedRowCount() == 1)
	        		tableSelectedRow(boothTable.getSelectedRow());
	        	else
	        		tableDeselectRow();
	        }
	    });
		scrollPane.setViewportView(boothTable);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		add(scrollPane_1, "cell 1 0,grow");
		
		stationList = new JList<Station>(ctx.getStationService().getAll().toArray(new Station[0]));
		stationList.setCellRenderer(new StationListRenderer());
		scrollPane_1.setViewportView(stationList);
		
		JLabel lblNewLabel = new JLabel("Code:");
		add(lblNewLabel, "flowx,cell 0 1");
		
		code = new JTextField();
		add(code, "cell 0 1");
		code.setColumns(10);
		
		JButton btnAdd = new JButton("Insert");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				add(ctx.getBoothController());
			}
		});
		add(btnAdd, "flowx,cell 0 2");
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				update(boothTable.getSelectedRow(), ctx.getBoothController());
			}
		});
		add(btnUpdate, "cell 0 2");
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				remove(boothTable.getSelectedRow(), ctx.getBoothController());
			}
		});
		add(btnRemove, "cell 0 2");
	}

	protected void remove(int selectedRow, BoothController boothController) {
		Booth station = tableModel.getBooth(selectedRow);
		try {
			boothController.remove(station);
			tableModel.fireTableRowsDeleted(0, 0);
		} catch (FieldEmptyException e) {
			JOptionPane.showMessageDialog(null, "Field cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void tableSelectedRow(int selectedRow) {
		Booth b = tableModel.getBooth(selectedRow);
		stationList.setSelectedValue(b.getStation(), true);
		code.setText(b.getCode());
	}

	private void tableDeselectRow() {
		stationList.clearSelection();
		code.setText("");
	}
	
	private void add(BoothController controller) {
		try {
			controller.add(code.getText(), stationList.getSelectedValue());
			tableModel.fireTableRowsInserted(0, 0);
		} catch (FieldEmptyException e) {
			JOptionPane.showMessageDialog(null, "Field cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (CodeAlreadyTakenException e) {
			JOptionPane.showMessageDialog(null, "Code already taken.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void update(int row, BoothController controller) {
		Booth booth = tableModel.getBooth(row);
		try {
			controller.update(booth, code.getText(), stationList.getSelectedValue());
			tableModel.fireTableRowsUpdated(row, row);
		} catch (FieldEmptyException e) {
			JOptionPane.showMessageDialog(null, "Field cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (CodeAlreadyTakenException e) {
			JOptionPane.showMessageDialog(null, "Code already taken.", "Error", JOptionPane.ERROR_MESSAGE);
		}	
	}
}
