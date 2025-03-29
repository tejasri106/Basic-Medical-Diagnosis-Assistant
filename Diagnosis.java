import java.awt.*;
import java.io.*;
import javax.swing.*;

/**
 * Diagnosis is the graphical interface for a simple Medical Diagnosis Assistant.
 * It guides users through a series of yes/no questions using a decision tree (TreeNode)
 * to provide a diagnosis. If the diagnosis is wrong, it can learn from mistakes
 * and save updates to a file. 
 */
public class Diagnosis {

    private JFrame frame;
    private JTextArea textArea;
    private JButton yesButton, noButton, idkButton;

    private TreeNode currentNode;
    private TreeNode parentNode;
    private boolean cameFromYes; // Tracks path for learning purposes

    private TreeNode root;

    /**
     * Constructor initializes the GUI and sets the root of the tree.
     * @param root The decision tree's starting point
     */
    public Diagnosis(TreeNode root) {
        this.root = root;
        this.currentNode = root;
        initializeGUI();
    }

    /**
     * Sets up the GUI components and layout.
     */
    private void initializeGUI() {
        frame = new JFrame("Medical Diagnosis Assistant");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Text area to show questions and diagnoses
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        yesButton = createButton("Yes", e -> handleAnswer(true));
        noButton = createButton("No", e -> handleAnswer(false));
        idkButton = createButton("I'm not sure", e -> notSure());
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        buttonPanel.add(idkButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);

        display(); // Start the diagnosis process
    }

    /**
     * Helper method to create buttons with attached actions.
     */
    private JButton createButton(String label, java.awt.event.ActionListener action) {
        JButton button = new JButton(label);
        button.addActionListener(action);
        return button;
    }

    /**
     * Displays the current question or diagnosis.
     */
    private void display() {
        if (currentNode.isLeaf()) {
            textArea.append("Diagnosis: " + currentNode.data + "\n\nIs this correct?\n");
        } else {
            textArea.append(currentNode.data + "\n");
        }
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    /**
     * Handles user responses (Yes/No).
     * Updates tree navigation and prompts the user appropriately.
     */
    private void handleAnswer(boolean isYes) {
        if (currentNode.isLeaf()) {
            int confirm = JOptionPane.showConfirmDialog(frame, 
                "Was this diagnosis correct?", 
                "Confirm Diagnosis", 
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(frame, "Great! Stay healthy!");
                reset();
            } else {
                if (JOptionPane.showConfirmDialog(frame, 
                    "Would you like to help me improve?", 
                    "Help Me Learn", 
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    learnNewDiagnosis();
                } else {
                    JOptionPane.showMessageDialog(frame, "Thanks for using the assistant. Take care!");
                    reset();
                }
            }
        } else {
            parentNode = currentNode;
            cameFromYes = isYes;
            currentNode = isYes ? currentNode.yesChild : currentNode.noChild;
            textArea.append("→ " + (isYes ? "Yes" : "No") + "\n\n");
            display();
        }
    }

    /**
     * Handles uncertain responses from the user.
     */
    private void notSure() {
        JOptionPane.showMessageDialog(frame, 
            "No problem! Diagnosing is tricky. Consider consulting a healthcare professional.", 
            "Uncertain Diagnosis", 
            JOptionPane.INFORMATION_MESSAGE);

        if (JOptionPane.showConfirmDialog(frame, 
            "Would you like to try another diagnosis?", 
            "Restart Diagnosis", 
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            reset();
        } else {
            JOptionPane.showMessageDialog(frame, "Thanks for using the Diagnosis Assistant. Stay well!");
            frame.dispose();
        }
    }

    /**
     * Learns a new diagnosis when the previous guess was incorrect.
     */
    private void learnNewDiagnosis() {
        String correct = JOptionPane.showInputDialog(frame, "What was the correct diagnosis?");
        if (correct == null || correct.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Oops! Diagnosis cannot be empty. Try again.");
            return;
        }

        String question = JOptionPane.showInputDialog(frame, 
            "What yes/no question would distinguish \"" + currentNode.data + "\" from \"" + correct + "\"?");
        if (question == null || question.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please provide a valid question.");
            return;
        }

        boolean isYesForCorrect = JOptionPane.showConfirmDialog(frame, 
            "For \"" + correct + "\", is the answer to the question YES?", 
            "Answer Confirmation", 
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

        TreeNode newDiagnosis = new TreeNode(correct);
        TreeNode oldDiagnosis = new TreeNode(currentNode.data);
        TreeNode newQuestion = new TreeNode(question);

        if (isYesForCorrect) {
            newQuestion.yesChild = newDiagnosis;
            newQuestion.noChild = oldDiagnosis;
        } else {
            newQuestion.yesChild = oldDiagnosis;
            newQuestion.noChild = newDiagnosis;
        }

        // Link the updated question to the parent
        if (parentNode != null) {
            if (cameFromYes) {
                parentNode.yesChild = newQuestion;
            } else {
                parentNode.noChild = newQuestion;
            }
        } else {
            root = newQuestion; // Update root if needed
        }

        saveTreeToFile();
        JOptionPane.showMessageDialog(frame, "Thanks! I've learned from this experience.");
        reset();
    }

    /**
     * Resets the diagnosis session to start over.
     */
    private void reset() {
        textArea.setText("");
        currentNode = root;
        parentNode = null;
        display();
    }

    /**
     * Saves the tree to a file for future use.
     */
    private void saveTreeToFile() {
        try (PrintWriter writer = new PrintWriter("diagnosis_tree.txt")) {
            saveTree(root, writer);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving tree: " + e.getMessage());
        }
    }

    /**
     * Recursively saves the tree to the file using a pre-order format.
     */
    private void saveTree(TreeNode node, PrintWriter writer) {
        if (node == null) return;
        writer.println((node.isLeaf() ? "A:" : "Q:") + node.data);
        saveTree(node.yesChild, writer);
        saveTree(node.noChild, writer);
    }
}
