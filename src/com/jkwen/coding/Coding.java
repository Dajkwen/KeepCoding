package com.jkwen.coding;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class Coding {
    private TreeNode binaryTree;

    private Coding() {

    }

    public static Coding launch() {
        return new Coding();
    }

    /**
     * 指定节点个数方式创建一棵二叉树
     * @param count
     * @return
     */
    public TreeNode createBinaryTree(int count) {
        //输入节点值，按数组的形式创建，如果不想节点有值可以用 -1 代替
        int[] array = new int[count];
        System.out.println("input tree node data, each once");
        for (int i = 0; i < count; i++) {
            Scanner s = new Scanner(System.in);
            array[i] = s.nextInt();
        }
        binaryTree = createTreeNode(array, 0, 1);
        //在这里把树展示出来，方便查看
        printTree(binaryTree);
        return binaryTree;
    }

    public TreeNode createBinaryTree(int[] src) {
        binaryTree = createTreeNode(src, 0, 1);
        //在这里把树展示出来，方便查看
        printTree(binaryTree);
        return binaryTree;
    }

    /**
     * 内建方式创建一棵二叉树
     * @return
     */
    public TreeNode createBinaryTreeByPreInput() {
        int[] array = new int[]{1,2,-1,3,-1,-1,-1,4,-1,-1,-1,-1,-1,-1,-1,5,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,6};
        binaryTree = createTreeNode(array, 0, 1);
        //在这里把树展示出来，方便查看
        printTree(binaryTree);
        return binaryTree;
    }

    /**
     * 将元素置于叶子上
     * @param src
     * @return
     */
    public TreeNode createBinaryTreeInLeaf(int[] src) {
        //log2 b = log10 b / log10 2
        int treeLevel = (int) (Math.log10(src.length) / Math.log10(2) + 1);
        //总节点数是 2^n -1 ，n 为最大高度
        int[] array = new int[(int) Math.pow(2, treeLevel) - 1];
        //倒叙赋值
        int j = src.length - 1;
        for (int i = array.length - 1; i > -1; i--) {
            if (j > -1) {
                array[i] = src[j];
                j--;
            } else {
                array[i] = -2;
            }
        }
        binaryTree = createTreeNode(array, 0, 1);
        printTree(binaryTree);
        return binaryTree;
    }

    private TreeNode createTreeNode(int[] src, int p, int level) {
        int left = 2 * p + 1;
        int right = 2 * p + 2;

        if (src[p] == -1) {
            return null;
        }
        TreeNode node = new TreeNode(src[p], level);
        if (left > src.length - 1 && right > src.length - 1) {
            return node;
        }
        if (left < src.length) {
            node.left = createTreeNode(src, left, level + 1);
        }
        if (right < src.length) {
            node.right = createTreeNode(src, right, level + 1);
        }
        return node;
    }

    /**
     * 前序
     * @param node
     */
    public void preDFSTravels(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.data);
        preDFSTravels(node.left);
        preDFSTravels(node.right);
    }

    /**
     * 中序
     * @param node
     */
    public void midDFSTravels(TreeNode node) {
        if (node == null) {
            return;
        }
        midDFSTravels(node.left);
        System.out.print(node.data);
        midDFSTravels(node.right);
    }

    /**
     * 后序
     * @param node
     */
    public void postDFSTravels(TreeNode node) {
        if (node == null) {
            return;
        }
        postDFSTravels(node.left);
        postDFSTravels(node.right);
        System.out.print(node.data);
    }

    /**
     * 前序，栈实现
     * @param root
     */
    public void preDFSTravels_Stack(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            TreeNode node = stack.pop();
            System.out.print(node.data);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }

    /**
     * 中序，栈实现
     * @param root
     */
    public void midDFSTravels_Stack(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        TreeNode node = root;
        while (node.left != null) {
            stack.push(node.left);
            node = node.left;
        }
        while(!stack.empty()) {
            node = stack.pop();
            System.out.print(node.data);
            if (node.right != null) {
                stack.push(node.right);
                node = node.right;
                while (node.left != null) {
                    stack.push(node.left);
                    node = node.left;
                }
            }
        }
    }

    /**
     * 后序，栈实现，对树具有破坏性
     * @param root
     */
    public void postDFSTravels_Stack(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        TreeNode node = root;
        while (node.left != null) {
            stack.push(node.left);
            node = node.left;
        }
        while(!stack.empty()) {
            node = stack.peek();
            if (node.right == null) {
                System.out.print(node.data);
                stack.remove(node);
            } else {
                TreeNode p = node.right;
                node.right = null;
                stack.push(p);
                while (p.left != null) {
                    stack.push(p.left);
                    p = p.left;
                }
            }
        }
    }

    /**
     * 后序，栈实现
     * @param root
     */
    public void postDFSTravels_Stack_v2(TreeNode root) {
        if (root == null) {
            return;
        }
        //考虑到第一版破坏了树的结构，v2 争取保留不破坏,基于树中节点值不重复
        //不过也可以比对节点是否相同，所以节点值不重复这个前提其实限制不大
        int data = -1;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        TreeNode node = root;
        while (node.left != null) {
            stack.push(node.left);
            node = node.left;
        }
        while(!stack.empty()) {
            node = stack.peek();
            if (node.right != null) {
                if (data != node.right.data) {
                    node = node.right;
                    stack.push(node);
                    while (node.left != null) {
                        stack.push(node.left);
                        node = node.left;
                    }
                    continue;
                }
            }
            System.out.print(node.data);
            data = node.data;//记录最近一次的输出结果
            stack.remove(node);
        }
    }

    /**
     * BFS
     * @param root
     */
    public void bfsTravels(TreeNode root) {
        if (root == null) {
            return;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.print(node.data);
            if (node.left != null || node.right != null) {
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
    }

    public void bfsTravels_Recursion(TreeNode root) {
        LinkedList<TreeNode> list = new LinkedList<>();
        list.add(root);
        bfsTravels_recursion(list);
    }

    /**
     * BFS, 递归实现
     * @param nodes
     */
    private void bfsTravels_recursion(LinkedList<TreeNode> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return;
        }
        LinkedList<TreeNode> list = new LinkedList<>();
        for (TreeNode node : nodes) {
            System.out.print(node.data);
            if (node.left != null) {
                list.add(node.left);
            }
            if (node.right != null) {
                list.add(node.right);
            }
        }
        bfsTravels_recursion(list);
    }

    public void printTree(TreeNode root) {
        if (root == null) {
            return;
        }
        int maxLevel = getTreeLevel(root);
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        //暂且支持 4 层展示
        for (int i = 0; i < maxLevel * 2 - 1; i++) {
            System.out.print(" ");
        }
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (queue.peek() == null || queue.peek().level != node.level) {
                System.out.println(node.data);
            } else {
                if (node.level == 2) {
                    for (int i = 0; i < 3; i++) {
                        System.out.print(" ");
                    }
                } else if (node.level == 3) {
                    System.out.print(" ");
                }
                System.out.print(node.data);
                if (node.level == 2) {
                    for (int i = 0; i < 7; i++) {
                        System.out.print(" ");
                    }
                } else if (node.level == 3) {
                    for (int i = 0; i < 3; i++) {
                        System.out.print(" ");
                    }
                } else {
                    System.out.print(" ");
                }
            }
            if (node.left != null || node.right != null) {
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
    }

    private int getTreeLevel(TreeNode node) {
        if (node == null) {
            return -1;
        }
        int leftLevel = getTreeLevel(node.left);
        int rightLevel = getTreeLevel(node.right);
        leftLevel = Math.max(leftLevel, rightLevel);
        return Math.max(node.level, leftLevel);
    }

    /**
     * 求树高
     * @param node
     * @param height
     * @return
     */
    public int getTreeHeight(TreeNode node, int height) {
        if (node == null) {
            return -1;
        }
        height++;
        int leftHeight = getTreeHeight(node.left, height);
        int rightHeight = getTreeHeight(node.right, height);
        leftHeight = Math.max(leftHeight, rightHeight);
        return Math.max(height, leftHeight);
    }

    /**
     * 求树高
     * @param root
     * @return
     */
    public int getTreeHeight_Stack(TreeNode root) {
        if (root == null) {
            return -1;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        TreeNode node = root;
        while (node.left != null) {
            stack.push(node.left);
            node = node.left;
        }
        int height = stack.size();
        int data = -1;
        while(!stack.empty()) {
            node = stack.peek();
            if (node.right != null) {
                if (data != node.right.data) {
                    node = node.right;
                    stack.push(node);
                    while (node.left != null) {
                        stack.push(node.left);
                        node = node.left;
                    }
                    height = Math.max(height, stack.size());
                    continue;
                }
            }
            data = node.data;
            stack.remove(node);
        }
        return height;
    }
}
