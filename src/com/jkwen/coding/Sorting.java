package com.jkwen.coding;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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

    /**
     * 锦标赛排序
     * 将数组元素挂在二叉树的叶子上，每轮选出一个最小值
     * @param src
     */
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

        //如果这样做排序，其实还没领会到锦标赛排序的精髓
        //关键点在于，找出第一个元素之后，按照原路径就可以很快找到第二个
        //因为第二个和第一个是有一定关系的。
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

    /**
     * 锦标赛排序
     * 参考内容 https://juejin.cn/post/6944185487691087902
     * 做了改进，抓住核心点，后一个元素基于前一个元素的路径做排序
     * @param src
     */
    public void tournamentSortV3(int[] src) {
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
        //先做一遍锦标赛
        for (int i = array.length - 1; i > 0; i = i - 2) {
            if (array[i] < array[i - 1]) {
                int p = (i - 2) / 2;
                array[p] = array[i];
            } else {
                int p = (i - 1) / 2;
                array[p] = array[i - 1];
            }
        }
        src[0] = array[0];

        for (int k = 1; k < src.length; k++) {
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
            //先直接晋级隔壁那位
            int p = 0;
            if (index % 2 != 0) {
                //要把右孩子上升
                p = (index - 1) / 2;
                array[p] = array[index + 1];
            } else {
                p = (index - 2) / 2;
                array[p] = array[index - 1];
            }
            //从刚才晋级的那位所在组开始重排，
            if (p % 2 != 0) {
                //左
                p = p + 1;
            }
            for (int i = p; i > 0; i = i - 2) {
                if (array[i] < array[i - 1]) {
                    int parent = (i - 2) / 2;
                    array[parent] = array[i];
                } else {
                    int parent = (i - 1) / 2;
                    array[parent] = array[i - 1];
                }
            }
            src[k] = array[0];
        }

        System.out.print("\n排序后：");
        for (int c : src) {
            System.out.print(c + " ");
        }
    }

    /**
     * 对锦标赛重新梳理，
     * 算法核心主要有两个过程，
     * 1.根据数组元素建立一棵完全二叉树（其实应该是棵满二叉），并完成一次锦标赛，选出最小值（最大值）
     * 2.每次摘取最小值后，再从最小值的叶子节点出发再开始一轮锦标赛
     *
     * 构建一棵满二叉，使得值都在叶子节点，已知数组长度，求树高，总节点数
     * 树高 = log2 src.length 的值向上取整，例如 2.8 取整为 3, 2.1 也取整为 3，然后 + 1
     * 总节点数 = 2^树高 - 1
     * @param src
     */
    public void tournamentSortV4(int[] src) {
        System.out.print("排序前：");
        for (int c : src) {
            System.out.print(c + " ");
        }

        TreeNode[] array = buildTreeInLeaf(src);
        for (int i = 0; i < src.length; i++) {
            //每次从根节点取出最小值赋值
            src[i] = array[0].data;
            //当 i == src.length - 1 时，其实已经得到完整结果了，再进行元素排序的话，最后都是 null
            //且即使做了最后一遍排序，i++ 后也会使条件不满足，所以干脆不做，节省一次
            if (i < src.length - 1) {
                //这里利用树节点存储了下标，如果不存的话，需要从根节点开始对比节点值来找到这条路径的叶子位置
                int index = array[0].index;
                //沿着最小值路径，将元素剔除，这里保存了下标，省去了路径回溯
                array[index] = null;
                //从剔除的位置开始进行相关位置调整，不相关的其实依然有序
                while (index > 0) {
                    TreeNode parent = array[index];
                    if (index % 2 == 0) {
                        TreeNode left = array[index - 1];
                        if (array[index] != null && left != null) {
                            if (array[index].data < left.data) {
                                parent = array[index];
                            } else {
                                parent = left;
                            }
                        } else if (array[index] == null) {
                            parent = left;
                        }
                        //偶数，说明是右孩子,将左孩子上升
                        index = (index - 2) / 2;
                    } else {
                        //左孩子
                        TreeNode right = array[index + 1];
                        if (array[index] != null && right != null) {
                            if (array[index].data < right.data) {
                                parent = array[index];
                            } else {
                                parent = right;
                            }
                        } else if (array[index] == null) {
                            parent = right;
                        }
                        index = (index - 1) / 2;
                    }
                    array[index] = parent;
//                array[(index - 1) / 2] = parent;
                }
            }
        }

        System.out.print("\n排序后：");
        for (int c : src) {
            System.out.print(c + " ");
        }
    }

    /**
     * 构建一棵元素都在叶子节点的完全二叉树
     * 并完成第一次锦标赛排序
     * @param src
     * @return
     */
    private TreeNode[] buildTreeInLeaf(int[] src) {
        //找到那层能放的下 src 的叶子数
//        int leafSize = 1;
//        while (leafSize < src.length) {
//            leafSize = leafSize << 1;
//        }
//        TreeNode[] array = new TreeNode[leafSize * 2 - 1];
        int treeLevel = (int) (Math.ceil(Math.log10(src.length) / Math.log10(2)) + 1);
        TreeNode[] array = new TreeNode[(int) (Math.pow(2, treeLevel) - 1)];
        //倒叙放入叶子节点中
        int j = src.length - 1;
        for (int i = array.length - 1; i > array.length - src.length - 1; i--) {
            array[i] = new TreeNode(src[j]);
            array[i].index = i;
            j--;
        }
        //对整个树进行遍历排序，使得最小值在根节点
        for (int i = array.length - 1; i > 0; i = i - 2) {
            TreeNode left = array[i - 1];
            TreeNode right = array[i];
            TreeNode parent = null;
            if (left != null && right != null) {
                if (left.data < right.data) {
                    parent = left;
                } else {
                    parent = right;
                }
            } else if (left == null && right != null) {
                parent = right;
            }
            array[(i - 1) / 2] = parent;
        }
        return array;
    }

    /**
     * 堆排序的思路也主要分两步
     * 1.对数组表示的完全二叉树，从最后一个非叶子节点开始做堆调整
     * 2.交换堆顶值，缩小范围重新进行堆调整
     * 最后一个非叶子节点的坐标 = src.length / 2 向下取整 - 1
     * @param src
     */
    public void heapSort(int[] src) {
        //先将数组映射成一棵二叉树，这里还是以数组表示二叉树
        //期望从小到大排序，那么可以建立一个大根堆，这样堆顶的值可以挂在最后
        //从最后一个非叶子节点开始，位置为 src.length / 2
        System.out.print("排序前：");
        for (int c : src) {
            System.out.print(c + " ");
        }
        //从第一个非叶子节点开始
        //依次对所有非叶子节点进行调整
        int index = Math.floorDiv(src.length, 2) - 1;
        while (index > -1) {
            adjustHeap_withLoop(src, index, src.length);
            index--;
        }
        //经过前面的初始堆调整已经基本有序了，
        //所以接下去改变堆顶后，只要再从堆顶开始调整即可
        for (int i = src.length - 1; i > 0; i--) {
            int temp = src[0];
            src[0] = src[i];
            src[i] = temp;
            adjustHeap_withLoop(src, 0, i);
        }

        System.out.print("\n排序后：");
        for (int c : src) {
            System.out.print(c + " ");
        }
    }

    private void adjustHeap(int[] src, int parent, int len) {
        int left = 2 * parent + 1;
        int right = 2 * parent + 2;
        int index = left;
        if (right < len) {
            if (src[right] > src[left]) {
                index++;
            }
        }
        if (index != left || (index < len && src[index] > src[parent])) {
            int temp = src[index];
            src[index] = src[parent];
            src[parent] = temp;
            //此时完成了一次调整，且三者中的最大值在根节点
            //接着分别以左节点和右节点出发继续调整
            if (index != left) {
                adjustHeap(src, right, len);
            } else {
                adjustHeap(src, left, len);
            }
        }
    }

    /**
     * 从给定节点出发，分别试探左右孩子，选出最大值上移，
     * 然后再从移动的一遍作为新的节点继续试探新的左右孩子
     * 直到超出限定返回
     * @param src
     * @param parent
     * @param len
     */
    private void adjustHeap_withLoop(int[] src, int parent, int len) {
        int left = 2 * parent + 1;
        int right = 2 * parent + 2;
        int index = left;
        int temp = src[parent];
        while (index < len) {
            if (right < len) {
                if (src[right] > src[left]) {
                    index++;
                }
            }
            if (index != left || src[index] > temp) {
                src[parent] = src[index];
                parent = index;
                left = 2 * parent + 1;
                right = 2 * parent + 2;
                index = left;
            } else {
                break;
            }
        }
        src[parent] = temp;
    }

    /**
     * 快速排序
     * @param src
     */
    public void quickSort(int[] src) {
        System.out.print("排序前：");
        for (int c : src) {
            System.out.print(c + " ");
        }

//        quickOnce_withDLoop(src, 0, src.length - 1);
//        quickOnce_withSLoop(src, 0, src.length - 1);
        quick_withStack(src);

        System.out.print("\n排序后：");
        for (int c : src) {
            System.out.print(c + " ");
        }
    }

    /**
     * 快排的双边循环，
     * @param src
     * @param left
     * @param right
     */
    private void quickOnce_withDLoop(int[] src, int left, int right) {
        if (left >= right) {
            return;
        }
        int pivot = left;
        int rightPos = right;

        while (left < right) {
            while (left < right && src[right] > src[pivot]) {
                right--;
            }
            while (left < right && src[left] <= src[pivot]) {
                left++;
            }
            if (left < right) {
                //exchange
                int temp = src[left];
                src[left] = src[right];
                src[right] = temp;
            }
        }
        if (pivot != left) {
            int temp = src[pivot];
            src[pivot] = src[left];
            src[left] = temp;
        }

        quickOnce_withDLoop(src, 0, left - 1);
        quickOnce_withDLoop(src, left + 1, rightPos);
    }

    /**
     * 快排的单边循环
     * @param src
     * @param left
     * @param right
     */
    private void quickOnce_withSLoop(int[] src, int left, int right) {
        if (left >= right) {
            return;
        }
        int pivot = left;
        int area = pivot;
        for (int i = left + 1; i <= right; i++) {
            if (src[i] <= src[pivot]) {
                area++;
                //exchange
                if (i != area) {
                    int temp = src[area];
                    src[area] = src[i];
                    src[i] = temp;
                }
            }
        }
        if (pivot != area) {
            int temp = src[pivot];
            src[pivot] = src[area];
            src[area] = temp;
        }

        quickOnce_withSLoop(src, 0, area - 1);
        quickOnce_withSLoop(src, area + 1, right);
    }

    private void quick_withStack(int[] src) {
        Stack<Map<String, Integer>> stack = new Stack<>();
        HashMap<String, Integer> map = new HashMap<>();
        map.put("left", 0);
        map.put("right", src.length - 1);
        stack.push(map);
        while (!stack.isEmpty()) {
            HashMap<String, Integer> param = (HashMap<String, Integer>) stack.pop();
            int pivot = getPivotPosition(src, param.get("left"), param.get("right"));
            if (pivot >= 0) {
                map = new HashMap<>();
                map.put("left", pivot + 1);
                map.put("right", param.get("right"));
                stack.push(map);

                map = new HashMap<>();
                map.put("left", 0);
                map.put("right", pivot - 1);
                stack.push(map);
            }
        }
    }

    private int getPivotPosition(int[] src, int left, int right) {
        if (left >= right) {
            return -1;
        }
        int pivot = left;
        int rightPos = right;

        while (left < right) {
            while (left < right && src[right] > src[pivot]) {
                right--;
            }
            while (left < right && src[left] <= src[pivot]) {
                left++;
            }
            if (left < right) {
                //exchange
                int temp = src[left];
                src[left] = src[right];
                src[right] = temp;
            }
        }
        if (pivot != left) {
            int temp = src[pivot];
            src[pivot] = src[left];
            src[left] = temp;
        }
        return left;
    }

    /**
     * 归并排序
     * @param src
     */
    public void mergeSort(int[] src) {

    }
}
