package ducksim;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

public class DuckSimController implements MouseListener {

	// ==========================================================
	// Private fields
	// ==========================================================

	private final DuckSimPaintedView view;
	private final DuckSimModel model;

	private final MouseListener popupListener;

	// ==========================================================
	// Inner class for creating pop-up menu
	// ==========================================================

	class PopupListener extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}

		private void maybeShowPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {

				// Decide if the mouse click is over a duck
				int d = view.getClickedDuck(e);
				model.setCurrentDuck(d);

				// Create all the menu items for the popup menus
				JMenuItem menuItem;
				view.initializePopup();

				menuItem = new JMenuItem("Quack");
				menuItem.addActionListener(e1 -> {
					model.getDuck(d).quack();
					view.getQuackTimer().start();
					view.update();
				});
				view.getPopup().add(menuItem);

				menuItem = new JMenuItem("Delete");
				menuItem.addActionListener(e1 -> {
					model.deleteDuck(d);
					model.setCurrentDuck(-1);
					model.notifyObservers();
				});
				view.getPopup().add(menuItem);

				view.getPopup().show(view, e.getX(), e.getY());
			}
		}
	}

	// ==========================================================
	// Constructor
	// ==========================================================

	public DuckSimController(DuckSimPaintedView view, DuckSimModel model) {

		this.view = view;
		this.model = model;

		popupListener = new PopupListener();
	}

	// ==========================================================
	// Public methods
	// ==========================================================

	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			int idx = view.getClickedDuck(e);
			if (idx == -1) { // no duck was clicked
				if (view.clickedNewDuckButton(e)) {
					MakeDuckDialog makeDuckDialog = new MakeDuckDialog(model, view);
					makeDuckDialog.setSize(300, 200);
					makeDuckDialog.setVisible(true);
				}
			}
			view.repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//
	}

	public MouseListener getPopupListener() {
		return popupListener;
	}

}
