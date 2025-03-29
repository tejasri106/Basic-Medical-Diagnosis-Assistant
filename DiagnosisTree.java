import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
// SIMILAR TO DiagnosisGUI BUT WITHOUT THE GUI - RUNS IN TERMINAL.

/**
 * DiagnosisTree handles the interactive process of medical diagnosis using a binary tree.
 * It allows the program to traverse the tree, ask the user yes/no questions,
 * learn new diagnoses, and automatically save any updates to the file.
 */
public class DiagnosisTree {

    // Scanner for reading user input
    private Scanner userInput;

    // Root node of the decision tree
    private TreeNode root;

    /**
     * Constructor for DiagnosisTree.
     *
     * @param userInput Scanner for user input
     * @param root      Root of the decision tree
     */
    public DiagnosisTree(Scanner userInput, TreeNode root) {
        this.userInput = userInput;
        this.root = root;
    }

    /**
     * Starts the diagnosis process by traversing the tree based on user responses.
     * If the diagnosis is incorrect, it allows the user to teach the program a new diagnosis,
     * inserts it into the tree, and saves the updated tree automatically.
     *
     * @param node         Current node being evaluated
     * @param parent       Parent of the current node (used for tree updating)
     * @param cameFromYes  True if the current node is the yesChild of the parent
     */
    public void startDiagnosis(TreeNode node, TreeNode parent, boolean cameFromYes) {

        // Base case: if current node is a leaf, make a diagnosis
        if (node.isLeaf()) {
            System.out.println("The diagnosis is: " + node.data);
            System.out.println("Is this correct?");
            String response = userInput.nextLine().trim();

            // If the diagnosis was correct
            if (response.equalsIgnoreCase("yes")) {
                System.out.println("Great! I'm glad I could help.");
            } else {
                // Begin learning process
                System.out.println("Help me learn!");
                System.out.println("What is the correct diagnosis?");
                String correctDiagnosis = userInput.nextLine();

                System.out.println("What yes/no question would distinguish between \"" + node.data + "\" and \"" + correctDiagnosis + "\"?");
                String question = userInput.nextLine();

                System.out.println("For \"" + correctDiagnosis + "\", what is the answer to that question? (yes/no)");
                String answer = userInput.nextLine().trim();

                // Create new nodes for learning
                TreeNode newDiagnosis = new TreeNode(correctDiagnosis);
                TreeNode oldDiagnosis = new TreeNode(node.data);
                TreeNode newQuestion = new TreeNode(question);

                // Attach children based on user response
                if (answer.equalsIgnoreCase("yes")) {
                    newQuestion.yesChild = newDiagnosis;
                    newQuestion.noChild = oldDiagnosis;
                } else {
                    newQuestion.yesChild = oldDiagnosis;
                    newQuestion.noChild = newDiagnosis;
                }

                // Re-link parent to new question node
                if (parent != null) {
                    if (cameFromYes) {
                        parent.yesChild = newQuestion;
                    } else {
                        parent.noChild = newQuestion;
                    }
                }

                // Save the updated tree automatically
                try {
                    PrintWriter writer = new PrintWriter("diagnosis_tree.txt");
                    saveTree(root, writer);
                    writer.close();
                    System.out.println("[Changes saved automatically.]");
                } catch (IOException e) {
                    System.out.println("Failed to save changes: " + e.getMessage());
                }
            }

        } else {
            // Recursively traverse the tree based on user answer
            if (askYesNo(node.data)) {
                startDiagnosis(node.yesChild, node, true);
            } else {
                startDiagnosis(node.noChild, node, false);
            }
        }
    }

    /**
     * Asks the user a yes/no question and validates the response.
     *
     * @param question The question to ask
     * @return True if the user answers "yes", false if "no"
     */
    public boolean askYesNo(String question) {
        do {
            System.out.println(question + " (yes/no)");
            String response = userInput.nextLine();

            if (response.equalsIgnoreCase("yes")) {
                return true;
            } else if (response.equalsIgnoreCase("no")) {
                return false;
            } else {
                System.out.println("Please answer yes or no.");
            }

        } while (true);
    }

    /**
     * Recursively saves the tree to a file using pre-order traversal.
     * Each node is written as either a question (Q:) or an answer (A:).
     *
     * @param node   The current node to save
     * @param writer PrintWriter connected to the output file
     */
    private void saveTree(TreeNode node, PrintWriter writer) {

        // Base case: do nothing if node is null
        if (node == null) {
            return;
        }

        // Write answer (leaf)
        if (node.isLeaf()) {
            writer.println("A:" + node.data);
        } else {
            // Write question and recursively save children
            writer.println("Q:" + node.data);
            saveTree(node.yesChild, writer);
            saveTree(node.noChild, writer);
        }
    }
}
