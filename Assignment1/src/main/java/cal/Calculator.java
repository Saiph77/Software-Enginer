package cal;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

class Calculator extends JFrame {
    private JTextField textfield;
    private boolean number = true;
    private String equalOp = "=";
    private CalculatorOp op = new CalculatorOp();


    private final Font BIGGER_FONT = new Font("monspaced", Font.PLAIN, 20);


    public Calculator() {
        // Create a textfield for displaying input and results
        textfield = new JTextField("", 12);
        textfield.setHorizontalAlignment(JTextField.RIGHT);
        textfield.setFont(BIGGER_FONT);

        // Create an ActionListener for handling number button clicks
        ActionListener numberListener = new NumberListener();

        // Define the order of buttons for numbers and a space
        String buttonOrder = "1234567890 ";

        // Create a JPanel for the number buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 4, 4));

        // Loop through the buttonOrder string to create buttons
        for (int i = 0; i < buttonOrder.length(); i++) {
            String key = buttonOrder.substring(i, i + 1);
            if (key.equals(" ")) {
                buttonPanel.add(new JLabel(""));
            } else {
                JButton button = new JButton(key);
                button.addActionListener(numberListener);
                button.setFont(BIGGER_FONT);
                buttonPanel.add(button);
            }
        }

        // Create an ActionListener for handling operator button clicks
        ActionListener operatorListener = new OperatorListener();

        // Create a JPanel for the operator buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 4, 4));

        // Define the order of operator buttons
        String[] opOrder = {"+", "-", "*", "/", "=", "C", "sin", "cos", "log"};

        // Loop through the opOrder array to create operator buttons
        for (int i = 0; i < opOrder.length; i++) {
            JButton button = new JButton(opOrder[i]);
            button.addActionListener(operatorListener);
            button.setFont(BIGGER_FONT);
            panel.add(button);
        }

        // Create a main JPanel to organize the components
        JPanel pan = new JPanel();
        pan.setLayout(new BorderLayout(4, 4));

        // Add the textfield to the top of the main panel
        pan.add(textfield, BorderLayout.NORTH);

        // Add the number buttons panel to the center of the main panel
        pan.add(buttonPanel, BorderLayout.CENTER);

        // Add the operator buttons panel to the right of the main panel
        pan.add(panel, BorderLayout.EAST);

        // Set the main panel as the content pane of the calculator frame
        this.setContentPane(pan);

        // Set frame properties
        this.pack();
        this.setTitle("Calculator");
        this.setResizable(false);
    }

    private void action() {
        // Set the "number" flag to true, indicating that a number input is expected.
        number = true;

        // Clear the textfield.
        textfield.setText("");

        // Set the "equalOp" variable to "=" to prepare for a new operation.
        equalOp = "=";

        // Reset the operator object's total value.
        op.setTotal("");
    }

    class OperatorListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Get the current text from the textfield.
            String displayText = textfield.getText();

            // Check if the clicked button is the "sin" operator.
            if (e.getActionCommand().equals("sin")) {
                // Calculate the sine of the input value and display it.
                textfield.setText("" + Math.sin(Double.valueOf(displayText).doubleValue()));
            } else if (e.getActionCommand().equals("cos")) {
                // Calculate the cosine of the input value and display it.
                textfield.setText("" + Math.cos(Double.valueOf(displayText).doubleValue()));
            } else if (e.getActionCommand().equals("log")) {
                // Calculate the natural logarithm of the input value and display it.
                textfield.setText("" + Math.log(Double.valueOf(displayText).doubleValue()));
            } else if (e.getActionCommand().equals("C")) {
                // Clear the textfield when the "C" (Clear) button is clicked.
                textfield.setText("");
            } else {
                if (number) {
                    // If a number is expected, perform necessary actions and clear the textfield.
                    action();
                    textfield.setText("");
                } else {
                    number = true;

                    // Handle different operators based on the previous operator ("equalOp").
                    if (equalOp.equals("=")) {
                        op.setTotal(displayText);
                    } else if (equalOp.equals("+")) {
                        op.add(displayText);
                    } else if (equalOp.equals("-")) {
                        op.subtract(displayText);
                    } else if (equalOp.equals("*")) {
                        op.multiply(displayText);
                    } else if (equalOp.equals("/")) {
                        op.divide(displayText);
                    }

                    // Display the result of the operation in the textfield.
                    textfield.setText("" + op.getTotalString());

                    // Update the "equalOp" variable with the current operator.
                    equalOp = e.getActionCommand();
                }
            }
        }
    }

    class NumberListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            // Get the digit (button label) that triggered the event.
            String digit = event.getActionCommand();

            // If a number is expected, set the textfield to the digit and mark that a number has been entered.
            if (number) {
                textfield.setText(digit);
                number = false;
            } else {
                // If there is already content in the textfield, append the digit to it.
                textfield.setText(textfield.getText() + digit);
            }
        }
    }

    public class CalculatorOp {
        private int total;

        // Set the total to the converted value of the provided string.
        public void setTotal(String n) {
            total = convertToNumber(n);
        }

        // Add the converted value of the provided string to the total.
        public void add(String n) {
            total += convertToNumber(n);
        }

        // Subtract the converted value of the provided string from the total.
        public void subtract(String n) {
            total -= convertToNumber(n);
        }

        // Multiply the total by the converted value of the provided string.
        public void multiply(String n) {
            total *= convertToNumber(n);
        }

        // Divide the total by the converted value of the provided string.
        public void divide(String n) {
            total /= convertToNumber(n);
        }

        // Convert a string to an integer.
        private int convertToNumber(String n) {
            return Integer.parseInt(n);
        }

        // Initialize the total to 0 when an instance of CalculatorOp is created.
        public CalculatorOp() {
            total = 0;
        }

        // Get the current total as a string.
        public String getTotalString() {
            return "" + total;
        }
    }
}

class SwingCalculator {
    public static void main(String[] args) {
        // Create a JFrame using the Calculator class, set it to be visible, and define the close operation.
        JFrame frame = new Calculator();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
