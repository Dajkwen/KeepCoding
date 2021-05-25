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

    public void heapSort(int[] src) {
        //先将数组映射成一棵二叉树，这里还是以数组表示二叉树
        //期望从小到大排序，那么可以建立一个大根堆，这样堆顶的值可以挂在最后
        //从最后一个非叶子节点开始，位置为 src.length / 2
        System.out.print("排序前：");
        for (int c : src) {
            System.out.print(c + " ");
        }
        //完成了一次堆调整
        int index = (src.length - 1) / 2;
        while (index > -1) {
            adjustHeap_withLoop(src, index, src.length);
            index--;
        }
        //接着移动最大值，然后从顶部开始重新调整
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



    public void standHeapSort(int[] src, int n) {
        System.out.print("排序前：");
        for (int c : src) {
            System.out.print(c + " ");
        }

        int i;
        int temp;
        for (i = n / 2; i >= 1; --i) {
            standAdjustHeap(src, i, n);
        }
        for (i = n; i >= 2; --i) {
            temp = src[1];
            src[1] = src[i];
            src[i] = temp;
            standAdjustHeap(src, 1, i - 1);
        }

        System.out.print("\n排序后：");
        for (int c : src) {
            System.out.print(c + " ");
        }

    }
    private void standAdjustHeap(int[] src, int low, int high) {
        int i = low, j = 2 * i;//j 代表 i 的左节点
        int temp = src[i];
        while(j <= high) {
            if (j < high && src[j] < src[j + 1]) {
                //如果有右节点，先判断左右节点大小
                ++j;
            }
            if (temp < src[j]) {
                src[i] = src[j];
                i = j;
                j = 2 * i;
            } else {
                break;
            }
        }
        src[i] = temp;
    }
}
