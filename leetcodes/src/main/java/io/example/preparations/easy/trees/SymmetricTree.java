package io.example.preparations.easy.trees;

public class SymmetricTree {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {return true;}
        var left = root.left;
        var right = root.right;
        return helper(left, right);
    }

    private boolean helper(TreeNode left, TreeNode right) {
        if (left == null && right == null) {return true;}
        if (left != null && right != null) {return left.val == right.val && helper(left.left, right.right) && helper(left.right, right.left);}
        return false;
    }
}
