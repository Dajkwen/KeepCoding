package com.jkwen;

import com.jkwen.coding.Coding;
import com.jkwen.coding.TreeNode;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
//        String money = MoneyUtils.formatAmountLeaveFraction2(100990.219, false);
//        System.out.println(money);
//        MoneyUtils.multi();
        System.out.println("input tree node count");
        Scanner s = new Scanner(System.in);
//        TreeNode tree = Coding.launch().createBinaryTree(s.nextInt());
        TreeNode tree = Coding.launch().createBinaryTreeByPreInput();
        System.out.println("pre_DFS");
        Coding.launch().preDFSTravels(tree);
        System.out.println();
        System.out.println("pre_DFS_with_stack");
        Coding.launch().preDFSTravels_Stack(tree);
        System.out.println();
        System.out.println("mid_DFS");
        Coding.launch().midDFSTravels(tree);
        System.out.println();
        System.out.println("mid_DFS_with_stack");
        Coding.launch().midDFSTravels_Stack(tree);
        System.out.println();
        System.out.println("post_DFS");
        Coding.launch().postDFSTravels(tree);
        System.out.println();
        System.out.println("post_DFS_with_stack");
        Coding.launch().postDFSTravels_Stack_v2(tree);
        System.out.println();
        System.out.println("BFS");
        Coding.launch().bfsTravels(tree);
        System.out.println();
        System.out.println("BFS_with_recursion");
        Coding.launch().bfsTravels_Recursion(tree);
        System.out.println();
        System.out.println("当前树高");
        System.out.print(Coding.launch().getTreeHeight(tree, 0));
        System.out.println();
        System.out.println("当前树高_Stack");
        System.out.print(Coding.launch().getTreeHeight_Stack(tree));
    }
}
