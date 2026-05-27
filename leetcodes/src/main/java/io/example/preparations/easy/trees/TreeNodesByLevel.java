package io.example.preparations.easy.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TreeNodesByLevel {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {return result;}
        var queue = new LinkedList<Wrap>();
        queue.offer(new Wrap(root, 0));
        while (!queue.isEmpty()) {
            var node = queue.poll();
            if (result.size() < node.level + 1) {result.add(new ArrayList<>());}
            if (node.node.left != null) {queue.offer(new Wrap(node.node.left, node.level + 1));}
            if (node.node.right != null) {queue.offer(new Wrap(node.node.right, node.level + 1));}
            result.get(node.level).add(node.node.val);
        }
        return result;
    }

    public record Wrap(TreeNode node, int level) {}
}
