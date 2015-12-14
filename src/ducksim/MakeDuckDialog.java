package ducksim;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MakeDuckDialog extends JDialog {

    // Fields
    
    // Duck panel
    private final JPanel duckPanel = new JPanel();
    private final JLabel duckLabel = new JLabel("Duck");
    private final String[] duckStrings = {"Mallard", "Redhead", "Rubber", "Decoy", "Goose"};
    private final JComboBox<?> duckOptions = new JComboBox<>(duckStrings);

    // Button panel
    private final JPanel buttonPanel = new JPanel();
    private final JButton okayButton = new JButton("Okay");
    private final JButton cancelButton = new JButton("Cancel");

    // Stored Data
    private String duckType = "Mallard";

    // Constructor
    public MakeDuckDialog(DuckSimModel model, JPanel view) {

    		this.setContentPane(new Box(BoxLayout.Y_AXIS));

        // add duck panel
        duckPanel.add(duckLabel);
        duckOptions.addActionListener(e -> {
            JComboBox<?> cb = (JComboBox<?>) e.getSource();
            duckType = (String) cb.getSelectedItem();
        });
        duckPanel.add(duckOptions);
        this.add(duckPanel);

        // add button panel
        cancelButton.addActionListener(e -> {
            this.dispose();
        });
        buttonPanel.add(cancelButton);
        okayButton.addActionListener(e -> {
            // makeDuckDialog
            Duck duck;
            switch (duckType) {
                case "Mallard":
                    duck = new QuackCountingDuck(new MallardDuck());
                    break;
                case "Redhead":
                    duck = new QuackCountingDuck(new RedheadDuck());
                    break;
                case "Rubber":
                    duck = new QuackCountingDuck(new RubberDuck());
                    break;
                case "Decoy":
                	duck = new DecoyDuck();
                	break;
                case "Goose":
                	duck = new QuackCountingDuck(new GooseDuck(new Goose()));
                	break;
                default:
                    duck = null;
            }
            if (duck != null) {
                model.addNewDuck(duck);
                model.notifyObservers();
//                view.repaint();
//                if (view instanceof DuckSimButtonView) {
//                		((DuckSimButtonView) view).updateButtons();
//                }
            }
            this.dispose();
        });
        buttonPanel.add(okayButton);
        this.add(buttonPanel);
    }

    // Public Methods
    public String getDuckType() {
        return duckType;
    }
}
