package app;

import javax.swing.JFrame;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import core.Entity;
import net.miginfocom.swing.MigLayout;

public class Main {
	public static void main(String[] args) {
		JFrame frm = new JFrame();
		frm.setLayout(new MigLayout());
		frm.setSize(640, 480);
		frm.setVisible(true);
		
		Entity e = new Entity();
		e.setId(10);
		XStream xstream = new XStream(new DomDriver());
		xstream.addPermission(AnyTypePermission.ANY);
		System.out.println(xstream.toXML(e));
	}
}
