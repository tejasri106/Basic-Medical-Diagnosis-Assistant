import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * The MedicalDiagnosisTree class contains the main program loop for the
 * Medical Diagnosis Assistant. It loads the tree from a file, prompts
 * the user with yes/no questions, and interacts with the DiagnosisTree class
 * to diagnose conditions and support learning.
 */
public class MedicalDiagnosisTree {

    /**
     * Main method that runs the diagnosis assistant.
     * It loads the tree and runs the diagnosis.
     *
     * @param args Command-line arguments (not used)
     */

    public static void main(String[] args) {
    JOptionPane.showMessageDialog(
        null,
        "Disclaimer: This program is not a substitute for professional medical advice.\nPlease consult a doctor for a proper diagnosis.",
        "Disclaimer",
        JOptionPane.INFORMATION_MESSAGE
    );
    // Load the decision tree from a file
    try {
        Scanner reader = new Scanner(new FileInputStream("diagnosis_tree.txt"));
        TreeNode root = loadTree(reader);
        new Diagnosis(root);  // Launch GUI
    } catch (IOException e) {
        System.out.println("Error loading tree: " + e.getMessage());
    }
}

    /**
     * Recursively loads the decision tree from a file using pre-order traversal.
     * Each line in the file must start with "Q:" for questions or "A:" for answers.
     *
     * @param reader Scanner connected to the diagnosis_tree.txt file
     * @return The root node of the tree
     * @throws IOException If the file format is invalid
     */
    public static TreeNode loadTree(Scanner reader) throws IOException {
        // Base case: end of file
        if (!reader.hasNextLine()) return null;

        // Read the next line and determine node type
        String line = reader.nextLine();

        // If it's a question, recursively load its two children
        if (line.startsWith("Q")) {
            TreeNode node = new TreeNode(line.substring(2).trim());
            node.yesChild = loadTree(reader);
            node.noChild = loadTree(reader);
            return node;
        }

        // If it's a diagnosis (leaf), return a leaf node
        else if (line.startsWith("A")) {
            return new TreeNode(line.substring(2).trim());
        }

        // Invalid format: return null or throw exception
        return null;
    }
}
