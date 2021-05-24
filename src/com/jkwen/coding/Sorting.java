package com.jkwen.coding;

public class Sorting {
    private Sorting() {

    }

    public static Sorting prepare() {
        return new Sorting();
    }

    /**
     * 冒泡排序
     * @param src
     */
    public void bubbleSort(int[] src) {
        System.out.print("排序前：");
        for (int j : src) {
            System.out.print(j + " ");
        }

        int sortEnd = src.length - 1;
        for (int loopTime = 1; loopTime < src.length; loopTime++) {
            int exchangeIndex = sortEnd;
            boolean isSorted = true;
            for (int i = 0; i < sortEnd; i++) {
                if (src[i] > src[i + 1]) {
                    int temp = src[i + 1];
                    src[i + 1] = src[i];
                    src[i] = temp;
                    exchangeIndex = i;
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
            sortEnd = exchangeIndex;
        }

        System.out.print("\n排序后：");
        for (int j : src) {
            System.out.print(j + " ");
        }
    }

    /**
     * 冒泡排序的演变-鸡尾酒排序
     * @param src
     */
    public void cocktailSort(int[] src) {
        System.out.print("排序前：");
        for (int j : src) {
            System.out.print(j + " ");
        }

        int rightSortEnd = src.length - 1;
        int leftSortEnd = 0;
        for (int loopTime = 0; loopTime < src.length / 2; loopTime++) {
            boolean isSorted = true;
            int lastSortIndex = rightSortEnd;
            for (int i = loopTime; i < rightSortEnd; i++) {
                if (src[i] > src[i + 1]) {
                    int temp = src[i + 1];
                    src[i + 1] = src[i];
                    src[i] = temp;
                    isSorted = false;
                    lastSortIndex = i;
                }
            }
            if (isSorted) {
                break;
            }
            rightSortEnd = lastSortIndex;

            isSorted = true;
            lastSortIndex = leftSortEnd;
            for (int i = src.length - 1 - loopTime; i > leftSortEnd; i--) {
                if (src[i] < src[i - 1]) {
                    int temp = src[i - 1];
                    src[i - 1] = src[i];
                    src[i] = temp;
                    isSorted = false;
                    lastSortIndex = i;
                }
            }
            if (isSorted) {
                break;
            }
            leftSortEnd = lastSortIndex;
        }

        System.out.print("\n排序后：");
        for (int j : src) {
            System.out.print(j + " ");
        }
    }

    /**
     * 简单选择排序
     * @param src
     */
    public void selectionSort(int[] src) {
        System.out.print("排序前：");
        for (int j : src) {
            System.out.print(j + " ");
        }

        for (int j = 0; j < src.length; j++) {
            int min = j;
            for (int i = j + 1; i < src.length; i++) {
                if (src[i] < src[min]) {
                    min = i;
                }
            }

            int temp = src[min];
            src[min] = src[j];
            src[j] = temp;
        }

        System.out.print("\n排序后：");
        for (int j : src) {
            System.out.print(j + " ");
        }
    }

    /**
     * 锦标赛排序
     * 没有用树的思想，显然效率并没有提高
     * @param src
     */
    public void tournamentSort(int[] src) {
        System.out.print("排序前：");
        for (int j : src) {
            System.out.print(j + " ");
        }

        for (int j = 0; j < src.length; j++) {
            int min = findMinValue(src, j);
            for (int i = 0; i < src.length; i++) {
                if (src[i] == min) {
                    int temp = src[i];
                    src[i] = src[j];
                    src[j] = temp;
                    break;
                }
            }
        }

        System.out.print("\n排序后：");
        for (int j : src) {
            System.out.print(j + " ");
        }
    }

    private int findMinValue(int[] src, int start) {
        int[] next = new int[(src.length - start + 1) / 2];
        int j = 0;
        for (int i = start; i < src.length - 1; i = i + 2) {
            if (src[i] < src[i+1]) {
                next[j] = src[i];
            } else {
                next[j] = src[i + 1];
            }
            j++;
        }
        if (next.length == 1) {
            return next[0];
        }

        if ((src.length - start) % 2 != 0) {
            next[next.length - 1] = src[src.length - 1];
        }

        return findMinValue(next, 0);
    }

    public void tournamentSortV2(int[] src) {
        //创建一棵完全二叉树，将元素放在叶子上
        //log2 b = log10 b / log10 2
        int treeLevel = (int) (Math.log10(src.length) / Math.log10(2) + 1);
        //总节点数是 2^n -1 ，n 为最大高度
        int[] array = new int[(int) Math.pow(2, treeLevel) - 1];
        //倒叙赋值
        int j = src.length - 1;
        int invalid = 0;
        for (int i = array.length - 1; i > -1; i--) {
            if (j > -1) {
                array[i] = src[j];
                if (src[j] > invalid) {
                    invalid = src[j];
                }
                j--;
            } else {
                array[i] = -2;
            }
        }
        invalid++;
        System.out.print("排序前：");
        for (int c : src) {
            System.out.print(c + " ");
        }

        for (int k = 0; k < src.length; k++) {
            //做一轮锦标赛
            for (int i = array.length - 1; i > 0; i = i - 2) {
                if (array[i] < array[i - 1]) {
                    int p = (i - 2) / 2;
                    array[p] = array[i];
                } else {
                    int p = (i - 1) / 2;
                    array[p] = array[i - 1];
                }
            }
            src[k] = array[0];
            //标记胜者
            int index = 0;
            while ((2 * index + 2) < array.length || (2 * index + 1) < array.length) {
                if (array[index] == array[2 * index + 1]) {
                    index = 2 * index + 1;
                } else {
                    index = 2 * index + 2;
                }
            }
            array[index] = invalid;
        }

        System.out.print("\n排序后：");
        for (int c : src) {
            System.out.print(c + " ");
        }
    }
}
