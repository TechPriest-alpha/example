package io.example.preparations.easy.trees;

/**
 * BST - binary search tree, balanced tree where left nodes are strictly less than parent one,
 * and right nodes are strictly greater.
 */
public class ValidBst {
    public boolean isValidBST(TreeNode root) {

        return isValidBST_v1(root, Long.MAX_VALUE, Long.MIN_VALUE);

    }

    private boolean isValidBST_v1(TreeNode root, long maxAllowed, long minAllowed) {
        if (root == null) {return true;}
        var valid = true;
        valid = !(root.val >= maxAllowed || root.val <= minAllowed);
        //System.out.println("1 valid= " + valid + ", root = " + root.val + ", " + (root.val >= maxAllowed) + ", " + (root.val <= minAllowed));
        valid = valid && isValidBST_v1(root.left, root.val, minAllowed);
        //System.out.println("2 valid= " + valid + ", root = " + root.val);
        valid = valid && isValidBST_v1(root.right, maxAllowed, root.val);
//System.out.println("3 valid= " + valid + ", root = " + root.val);
        return valid;
    }
}
