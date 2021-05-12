package com.jkwen.coding;

public class TreeNode {
    public int data;

    public int level;

    public TreeNode left;

    public TreeNode right;

    public TreeNode(int data) {
        this.data = data;
    }

    public TreeNode(int data, int level) {
        this.data = data;
        this.level = level;
    }
}
