package ducksim;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * DuckSimButtonView is a JPanel that will serve as the content pane to the main
 * JFrame window.
 *
 * @author Dr. K
 */
@SuppressWarnings("serial")
public class DuckSimButtonView extends JPanel implements ModelObserver {

	// ==========================================================
	// Private fields
	// ==========================================================

	private final DuckSimModel model;

	private final String quackCountPrefix;
	private final int maxDucks;
	private int quackCount;

	private final Timer quackTimer;
	private int quackCounter;

	private final Box box;
	private final JPanel header;
	private final JPanel duckrow1;
	private final JPanel duckrow2;
	private final JButton[] duckButtonArray;
	private final JButton createButton;
	private final JLabel quackCountLabel;

	public DuckSimButtonView(DuckSimModel modelArg) {
		model = modelArg;

		maxDucks = model.maxDuckCount();
		quackCountPrefix = "NUMBER OF QUACKS: ";
		quackCount = 0;

		quackTimer = new Timer(100, e -> quackTimerUpdate());
		quackCounter = 0;

		box = new Box(BoxLayout.Y_AXIS);
		header = new JPanel();
		duckrow1 = new JPanel();
		duckrow2 = new JPanel();
		duckButtonArray = new JButton[maxDucks];
		for (int i = 0; i < duckButtonArray.length; i++) {
			duckButtonArray[i] = new JButton();
			duckButtonArray[i].setIcon(new ImageIcon("assets/duck.png"));
			duckButtonArray[i].setEnabled(false);
			duckButtonArray[i].setBorderPainted(false);
			int index = i;
			duckButtonArray[i].addActionListener(e -> {
				JButton button = (JButton) e.getSource();
				quackCounter = 0;
				quackTimer.start();
				model.getDuck(index).quack();
				button.setFont(new Font("Verdana", Font.BOLD, 24));
				button.setForeground(Color.WHITE);
				button.setText(e.getActionCommand());
				button.setHorizontalTextPosition(JButton.CENTER);
				button.setVerticalTextPosition(JButton.CENTER);
				model.notifyObservers();
			});
		}
		createButton = new JButton("CREATE NEW DUCK");
		quackCountLabel = new JLabel(quackCountPrefix + quackCount);

		this.add(box);
		box.add(header);
		box.add(duckrow1);
		box.add(duckrow2);

		// add button and label to header
		header.add(createButton);
		createButton.addActionListener(e -> {
			MakeDuckDialog makeDuckDialog = new MakeDuckDialog(model, this);
			makeDuckDialog.setSize(300, 200);
			makeDuckDialog.setVisible(true);
		});
		header.add(quackCountLabel);

		// add buttons to duckrow1
		for (int i = 0; i < maxDucks / 2; i++) {
			duckrow1.add(duckButtonArray[i]);
		}

		// add buttons to duckrow2
		for (int i = maxDucks / 2; i < maxDucks; i++) {
			duckrow2.add(duckButtonArray[i]);
		}
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
			for (JButton button : duckButtonArray) {
				button.setText("");
			}
			quackCounter = 0;
		}

	}

	public void updateButtons() {
		// put total quacks in corner
		
		quackCount = 0;
		// setup buttons
		for (int i = 0; i < maxDucks; i++) {
			if (i < model.currDuckCount()) {
				Duck d = model.getDuck(i);
				if(d instanceof QuackCountingDuck){
					QuackCountingDuck qcDuck = (QuackCountingDuck) d;
					quackCount += qcDuck.getNumberOfQuacks();
				}
				duckButtonArray[i].setEnabled(true);
				duckButtonArray[i].setBackground(d.getColor());
				duckButtonArray[i].setOpaque(true);
				duckButtonArray[i].setActionCommand(d.getQuack().toString());
			} else {
				duckButtonArray[i].setEnabled(false);
				duckButtonArray[i].setOpaque(false);
				duckButtonArray[i].setBackground(new JButton().getForeground());
			}
		}
		
		quackCountLabel.setText(quackCountPrefix + quackCount);
	}

	@Override
	public void update() {
		updateButtons();
		
	}
	
}
