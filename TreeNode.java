/**
 * The TreeNode class represents a node in the diagnosis decision tree.
 * Each node contains a string of data (either a question or a diagnosis),
 * and may have a yesChild and noChild depending on whether it is a branch or a leaf.
 */
class TreeNode {

    // The content of the node (a question or a diagnosis)
    String data;

    // Child node to follow if the user answers "yes"
    TreeNode yesChild;

    // Child node to follow if the user answers "no"
    TreeNode noChild;

    /**
     * Constructor that creates a TreeNode with the given data.
     *
     * @param data The question or diagnosis stored in the node
     */
    public TreeNode(String data) {
        this.data = data;
    }

    /**
     * Checks if the node is a leaf node (i.e., has no children).
     *
     * @return true if this is a leaf node; false otherwise
     */
    boolean isLeaf() {
        return yesChild == null && noChild == null;
    }
}
