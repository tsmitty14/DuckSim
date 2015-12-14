package ducksim;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.Timer;

/**
 * DuckSimView is a JPanel that will serve as the content pane to the main
 * JFrame window.
 *
 * @author Dr. K
 */
@SuppressWarnings("serial")
public class DuckSimPaintedView extends JPanel implements ModelObserver {

	// ==========================================================
	// Private fields
	// ==========================================================

	private final DuckSimModel model;

	// Fields for popup menu
	private JPopupMenu popup;

	// Fields for quacking animation
	private final Timer quackTimer;
	private int quackCounter;

	// ==========================================================
	// Constructor
	// ==========================================================

	public DuckSimPaintedView(DuckSimModel model) {

		this.model = model;

		quackTimer = new Timer(100, e -> quackTimerUpdate());
		quackCounter = 0;
	}

	// ==========================================================
	// Public Methods
	// ==========================================================

	public Timer getQuackTimer() {
		return quackTimer;
	}

	public JPopupMenu getPopup() {
		return popup;
	}

	public void initializePopup() {
		popup = new JPopupMenu();
	}

	public boolean clickedNewDuckButton(MouseEvent e) {
		return isWithinRect(e.getX(), e.getY(), 30, 500, 50, 50);
	}

	public int getClickedDuck(MouseEvent e) {
		for (int i = 0; i < Math.min(model.currDuckCount(), 4); i++) {
			if (isWithinRect(e.getX(), e.getY(), 150 + i * 125, 325, 100, 100)) {
				return i;
			}
		}
		for (int i = 4; i < Math.min(model.currDuckCount(), 8); i++) {
			if (isWithinRect(e.getX(), e.getY(), 150 + (i - 4) * 125, 425, 100, 100)) {
				return i;
			}
		}
		return -1;
	}

	public boolean isWithinRect(int x, int y, int rX, int rY, int rW, int rH) {
		return (x >= rX && x <= rX + rW && y >= rY && y <= rY + rH);
	}

	// ==========================================================
	// Paint methods
	// ==========================================================

	public void paintQuack(Graphics g) {
		int currentDuck = model.getCurrentDuck();
		if (currentDuck == -1)
			return;
		if (quackTimer.isRunning()) {
			String quack = model.getDuck(currentDuck).getQuack().toString();
			g.setColor(Color.BLACK);
			g.setFont(new Font("Verdana", Font.BOLD, 36));
			FontMetrics fm = g.getFontMetrics();
			int totalWidth = fm.stringWidth(quack);
			g.drawString(quack, 400 - totalWidth / 2, 100);
		}
	}

	public void paintDuck(Graphics g, int pos) {
		int x = 0;
		int y = 0;

		if (!model.containsDuck(pos))
			return;

		// Get x, y values (top-left coords) for duck
		switch (pos) {
		case 0:
		case 1:
		case 2:
		case 3:
			x = 150 + (pos) * 125;
			y = 325;
			break;
		case 4:
		case 5:
		case 6:
		case 7:
			x = 150 + (pos - 4) * 125;
			y = 450;
			break;
		default:
			assert false;
		}

		// paint border cyan if quackTimer is running for this duck
		if (model.getCurrentDuck() == pos && quackTimer.isRunning()) {
			g.setColor(Color.CYAN);
		} else {
			g.setColor(Color.WHITE);
		}

		// paint gap between square and border
		g.fillRect(x, y, 100, 100);
		g.setColor(Color.GRAY);
		g.fillRect(x + 5, y + 5, 90, 90);

		// paint square
		g.setColor(Color.WHITE);
		g.fillRect(x + 10, y + 10, 80, 80);

		// paint count in upper left corner of square
		g.setColor(Color.BLACK);
		g.setFont(new Font("Verdana", Font.BOLD, 14));
		int count = 0;
		Duck duck = model.getDuck(pos);
		if(duck instanceof QuackCountingDuck){
			QuackCountingDuck qcDuck = (QuackCountingDuck) duck;
			count = qcDuck.getNumberOfQuacks();
		}
		
		g.drawString("" + count, x + 20, y + 30);

		// paint duck text in center of square
		String text = model.getDuck(pos).display();
		FontMetrics fm = g.getFontMetrics();
		int totalWidth = fm.stringWidth(text);
		g.drawString(text, x + 50 - totalWidth / 2, y + 55);

	}

	private void paintAddDuckButton(Graphics g) {
		int x = 30;
		int y = 500;

		g.setColor(Color.BLACK);
		g.fillOval(x, y, 50, 50);
		g.setColor(Color.GRAY);
		g.fillOval(x + 2, y + 2, 46, 46);
		g.setColor(Color.BLACK);
		g.fillOval(x + 5, y + 5, 40, 40);
		g.setColor(Color.GRAY);
		g.fillRect(x + 20, y + 10, 10, 30);
		g.fillRect(x + 10, y + 20, 30, 10);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		for (int i = 0; i < model.currDuckCount(); i++) {
			paintDuck(g, i);
		}

		paintAddDuckButton(g);
		paintQuack(g);
	}

	// ==========================================================
	// Update methods
	// ==========================================================

	public void quackTimerUpdate() {
		quackCounter = quackCounter + 100;

		if (quackCounter > 2000) {
			quackTimer.stop();
			for (Duck duck : model) {
				duck.stopQuack();
			}
			quackCounter = 0;
			repaint();
		}
	}
	
	public void update(){
		repaint();
	}
}
