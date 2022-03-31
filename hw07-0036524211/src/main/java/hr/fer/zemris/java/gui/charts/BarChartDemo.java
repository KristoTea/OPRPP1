package hr.fer.zemris.java.gui.charts;

import java.awt.Container;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Glavni program koji kroz argument prima put do podataka 
 * potrebnih za iscrtavanje grafa, čita ih, pravilno parsira 
 * te delegira crtanje grafa BarChartComponent objektu.
 * @author teakr
 *
 */
public class BarChartDemo extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BarChartDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("BarChartDemo");
		setSize(700, 550);
		initGUI();
		//pack();
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setBackground(Color.WHITE);
	}

	public static void main(String[] args) {
		//src/main/resources/BarChart/graphInfo.txt
		
		List<String> list = new ArrayList<>();
		try {
			list = Files.readAllLines(Paths.get(args[0]), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Something went wrong!");
			System.exit(-1);
		}
		
		String[] lines = new String[6];
		for(int i = 0; i < 6; i++) {
			lines[i] = list.get(i);
		}
		
		String[] values = lines[2].replaceAll("\\\s+", " ").split(" ");
		List<XYValue> xyValues = new ArrayList<>();
		for(String value : values) {
			xyValues.add(new XYValue(Integer.parseInt(value.split(",")[0]), Integer.parseInt(value.split(",")[1])));
		}
		
		BarChart bc = new BarChart(xyValues, lines[0], lines[1], Integer.parseInt(lines[3]), Integer.parseInt(lines[4]), Integer.parseInt(lines[5]));
		BarChartComponent bcc = new BarChartComponent(bc);
		BarChartDemo bcd = new BarChartDemo();
		bcd.getContentPane().add(bcc);
		
		SwingUtilities.invokeLater(()->{
			bcd.setVisible(true);
		});
	}

}
