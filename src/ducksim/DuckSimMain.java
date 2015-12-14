
package ducksim;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * Main class for the DuckSim application
 * 
 * @author Dr. K
 */
public class DuckSimMain {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(DuckSimMain::createAndShowGUI);
	}

	private static void createAndShowGUI() {
		DuckSimModel model = new DuckSimModel();
		
		// set up first view
		DuckSimPaintedView view = new DuckSimPaintedView(model);
		DuckSimController controller = new DuckSimController(view, model);
		view.addMouseListener(controller);
		view.addMouseListener(controller.getPopupListener());
		JFrame frame = new JFrame("DuckSim");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JComponent content = view;
		content.setOpaque(true);
		frame.setContentPane(content);
		frame.setSize(800, 600);
		frame.setVisible(true);
		model.registerObserver(view);
		
		// set up second view
		DuckSimButtonView view2 = new DuckSimButtonView(model);
		JFrame frame2 = new JFrame("DuckSim2");
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JComponent content2 = view2;
		content2.setOpaque(true);
		frame2.setContentPane(content2);
		frame2.pack();
		frame2.setVisible(true);
		model.registerObserver(view2);

	}

}
