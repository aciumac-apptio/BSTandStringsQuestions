import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static class TreeNode {
        public int data;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int data) {
            this(data, null, null);
        }

        public TreeNode(int data, TreeNode left, TreeNode right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    private static class BinaryTree implements Iterable<Integer>{
        private TreeNode overallRoot;
        private int size;

        public BinaryTree() {

            overallRoot = null;
            size = 0;
        }

        private void add(int value) {
            overallRoot = add(overallRoot, value);
        }

        private TreeNode add(TreeNode root, int value) {
            if (root == null) {
                size++;
                root = new TreeNode(value);
            } else if (value < root.data) {
                root.left = add(root.left, value);
            } else if (value > root.data) {
                root.right = add(root.right, value);
            }
            return root;
        }

        private class InOrderIterator implements Iterator<Integer> {
            private Stack<TreeNode> s;
            private TreeNode root;

            public InOrderIterator() {
                    s = new Stack<>();
                    root = overallRoot;
                    // InOrder
                    /*while (root != null) {
                        s.push(root);
                        root = root.left;
                    }*/

                    // PreOrder
                    //s.push(root);

                    //Post Order
                    while (root != null) {
                        if (root.right != null) {
                            s.push(root.right);
                        }
                        s.push(root);
                        root = root.left;
                    }

            }

            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                TreeNode node = s.pop();
                int result = node.data;
                // InOrder
                /*if (node.right != null) {
                    node = node.right;
                    while (node != null) {
                        s.push(node);
                        node = node.left;
                    }
                }*/

                // PreOrder
                /*if (node.right != null) {
                    s.push(node.right);
                }
                if (node.left != null) {
                    s.push(node.left);
                }*/

                // PostOrder
                if(node.right != null && !s.isEmpty() && node.right.data == s.peek().data) {
                    //exchange nodes
                    s.pop();
                    s.push(node);

                    node = node.right;

                    while (node != null) {
                        if (node.right != null) {
                            s.push(node.right);
                        }
                        s.push(node);
                        node = node.left;
                    }
                    result = s.peek().data;
                }



                return result;
            };

            public boolean hasNext() {
                return !s.isEmpty();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        }

        public Iterator<Integer> iterator() {
            return new InOrderIterator();
        }

        public boolean contains(int value) {
            return contains(overallRoot, value);
        }

        private boolean contains(TreeNode root, int value) {
            if (root == null) {
                return false;
            } else if (root.data == value) {
                return true;
            } else if (value < root.data) {
                return contains(root.left, value);
            } else {
                return contains(root.right, value);
            }
        }

        public String pathTo(int value) {
            StringBuilder sb = new StringBuilder("");
            return pathTo(overallRoot, value, sb);
        }

        private String pathTo(TreeNode node, int value, StringBuilder path) {
            if (node == null) {
                return path.toString();
            } else if (value == node.data) {
                return path.append(value + " ").toString();
            } else if (value < node.data) {
                pathTo(node.left, value, path.append(node.data + " "));
            } else if (value > node.data) {
                pathTo(node.right, value, path.append(node.data + " "));
            }
            return path.toString();
        }

    }


    public static void main(String[] args) {
        /*System.out.println("Hello World!");
        int[] values = {5, 6, 3, 1, 2, 4};
        System.out.println("Expected: 3 Actual: " + bstDistance(values, 6, 2, 4));

        int[] values2 = {9, 7, 5, 3, 1};
        System.out.println("Expected: -1 Actual: " + bstDistance(values2, 5, 7, 20));*/

        //String s = "awaglknagawunagwkwagl";
        //["wagl", "aglk", "glkn", "lkna", "knag", "gawu", "awun", "wuna", "unag", "kwag"]
        String s = "abcd";
        System.out.println(subStringsKDist(s, 3));

        int[] values = {10, 7, 20, 5, 9, 12, 25};
        /*int[] values = {1, 2, 3, 4, 5, 6, 7};*/
        BinaryTree tree = new BinaryTree();
        for (int i = 0; i < values.length; i++) {
            tree.add(values[i]);
        };

        for (int i : tree) {
            System.out.print(i + " ");
        }
        System.out.println();


        int[] values2 = {5, 6, 3, 1, 2, 4};
        BinaryTree tree2 = new BinaryTree();
        for (int i = 0; i < values2.length; i++) {
            tree2.add(values2[i]);
        };

        for (int i : tree2) {
            System.out.print(i + " ");
        }
        System.out.println();



/*        int[] coins = {10, 50, 100};
        int[] quantity = {1, 2, 1};
        System.out.println("Expect: 9 Actual: " + possibleSums(coins, quantity));

        int[] coins2 = {10, 50, 100, 500};
        int[] quantity2 = {5, 3, 2, 2};
        System.out.println("Expect: 122 Actual: " + possibleSums(coins2, quantity2));*/

        int[] coins3 = {1, 1, 1, 1, 1};
        int[] quantity3 = {9, 19, 18, 12, 19};
        System.out.println("Expect: 77 Actual: " + possibleSums(coins3, quantity3));

        int[] example = {5, 6, 1, 1, 2, 4};
        int[] example2 = {1, 2, 3, 4, 5};
        int[] example3 = {1, 1, 2, 2, 3};
        System.out.println("Smallest unique integer, expect: 2 actual " + smallestUnique(example));
        System.out.println("Smallest unique integer, expect: 1 actual " + smallestUnique(example2));
        System.out.println("Smallest unique integer, expect: 3 actual " + smallestUnique(example3));


        int[] dummy = {3, 2, 1, 2, 3};
        List<Integer> example4 = Arrays.stream(dummy).boxed().collect(Collectors.toList());
        removeUniques(example4);
        System.out.println("Expect: [3, 2], Actual: " + example4);

        int[] dummy1 = {1, 2, 4, 7, 10};
        int[] dummy2 = {3, 5, 6, 8, 9};

        List<Integer> dummyList1 = Arrays.stream(dummy1).boxed().collect(Collectors.toList());
        List<Integer> dummyList2 = Arrays.stream(dummy2).boxed().collect(Collectors.toList());
        numbersAddUpTo(dummyList1, dummyList2, 9);
    }

    public static void numbersAddUpTo(List<Integer> list1, List<Integer> list2, int target) {
        Set<Integer> set = new HashSet<>();
        set.addAll(list1);

        for (int i = 0; i < list2.size(); i++) {
            if (set.contains(target - list2.get(i))) {
                int val = list2.get(i);
                System.out.println("Numbers " + list2.get(i) + " and " + (target - val) + " add up to " + target);
                return;
            }
        }
    }

    public static void removeUniques(List<Integer> list) {
        // Look at 3
            // Check if 3 exists in sublist (1, length of list) does
            // remove 3 at that index
            // advance counter
        // Look at 2
            // Check if 3 exists in sublist (1, length of list) does
            // remove 3 at that index
            // advance counter
        // Look at 1
            // Check if 1 exists in sublist (1, length of list) does NOT
            // remove 1 at that index i

        int index = 0;
        while (index < list.size()) {
            int value = list.get(index);
            if (list.subList(index + 1, list.size()).contains(value)) {
                do {
                    list.subList(index + 1, list.size()).remove(list.subList(index + 1, list.size()).indexOf(value));
                } while (list.subList(index + 1, list.size()).contains(value));
                index++;
            } else {
                list.remove(index);
            }
        }
    }

    // Find the smallest unique integer in a random integer array with possible duplicates.
    public static int smallestUnique(int[] array) {
        /*Set<Integer> set = new TreeSet<>();
        for (int i = 0; i < array.length; i++) {
            if (set.contains(array[i])) {
                set.remove(array[i]);
            } else {
                set.add(array[i]);
            }
        }

        Iterator<Integer> itr = set.iterator();
        return itr.next();*/

        Arrays.sort(array);
        if (array[0] != array[1]) {
            return array[0];
        }

        for (int i = 1; i < array.length - 1; i++) {
            if (array[i - 1] != array[i] && array[i] != array[i + 1]) {
                return array[i];
            }
        }

        return array[array.length - 1] != array[array.length - 2] ? array[array.length - 1] : -1;
    }



    public static int possibleSums(int[] coins, int[] quantity) {

        // Build the list of choices

        // if no more choices
        // return
        // else process all choices
        // choose
        // explore possibleSums()
        // unchoose  no need

        // return sums
        //
        // Store sums in a set
        // return size of the set
       /* List<Integer> choices = new ArrayList<>();
        for (int i = 0; i < coins.length; i++) {
            for (int j = 0; j < quantity[i]; j++) {
                choices.add(coins[i]);
            }
        }


        //List<Integer> sums = new ArrayList<>();
        Set<Integer> sums = new HashSet();
        possibleSums(choices, sums, 0);

        *//*set.addAll(sums);*//*
        return sums.size();*/
        Set<Integer> sums = new HashSet<>();
        for (int i = 0; i < coins.length; i++) {
            for (int j = 0; j < quantity[i]; j++) {
                List<Integer> list = new ArrayList<>();
                for (int val : sums) {
                    list.add(val + coins[i]);
                }
                sums.add(coins[i]);
                sums.addAll(list);
            }
        }

        return sums.size();
    }


    private static void possibleSums(List<Integer> choices, Set<Integer> sums, int cumulative) {
        if (choices.isEmpty()) {
            return;
        } else {
            for (int i = 0; i < choices.size(); i++) {
                int val = choices.get(i);
                cumulative += val;
                sums.add(cumulative);
                choices.remove(i);

                possibleSums(choices, sums, cumulative);

                cumulative -= val;
                choices.add(i, val);
            }
        }
    }



    // METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
    public static List<String> subStringsKDist(String inputStr, int num)
    {
        // WRITE YOUR CODE HERE
        Set<String> set = new HashSet<>();

        int startIndex = 0;
        int currentLength = 0;

        for (int i = 0; i < inputStr.length(); i++) {
            char c = inputStr.charAt(i);
            if (currentLength == num && !inputStr.substring(startIndex, i).contains(c + "")) {
                set.add(inputStr.substring(startIndex, i));
                startIndex += 1;
                currentLength =- 1;
            }

            if (inputStr.substring(startIndex, i).contains(c + "")) {
                while(inputStr.substring(startIndex, i).contains(c + "") && startIndex <= i) {
                    startIndex += 1;
                }
                currentLength = i - startIndex + 1;
            } else {
                currentLength++;
            }
        }

        List<String> list = new ArrayList<>();
        for (String st: set) {
            list.add(st);
        }
        return list;

    }


    public static int bstDistance(int[] values, int n,
                                  int node1, int node2) {
        // WRITE YOUR CODE HERE
        try {
            if (values == null || n < 1) {
                throw new IllegalArgumentException();
            }

            if (values.length < 2) {
                return -1;
            }

            BinaryTree tree = new BinaryTree();
            for (int i = 0; i < values.length; i++) {
                tree.add(values[i]);
            }

            if (!tree.contains(node1) || !tree.contains(node2)) {
                return -1;
            }

            String path1 = tree.pathTo(node1);
            String path2 = tree.pathTo(node2);

            String[] arr1 = path1.split("[ \t]+");
            String[] arr2 = path2.split("[ \t]+");

            int size = arr1.length < arr2.length ? arr1.length : arr2.length;
            int ancestor = 0;
            int distance = 0;
            for (int i = 0; i < size; i++) {
                if (!arr1[i].equals(arr2[i])) {
                    distance = (arr1.length - 1 - ancestor) + (arr2.length - 1 - ancestor);
                    break;
                } else {
                    ancestor = i;
                }
            }
            return distance;

        } catch (IllegalArgumentException e) {
            return -1;
        }

    }
}
