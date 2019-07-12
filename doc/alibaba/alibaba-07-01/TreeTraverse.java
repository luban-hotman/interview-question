import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * 二叉树遍历。
 *
 * @author mazhuang
 * @date 2019-06-20 11:34
 */
public class TreeTraverse {


    public static void main(String[] args) {

        TreeNode root = new TreeNode("0");
        root.left = new TreeNode("1");
        root.right = new TreeNode("2");
        root.left.right = new TreeNode("3");
        root.left.right.right = new TreeNode("4");
        root.right.left = new TreeNode("5");
        printTree(root);

    }

    public static void printTree(TreeNode root){

        if(root == null){
            return ;
        }
        System.out.println("层级优先");
        System.out.println(levelTraverse(root));
        System.out.println("前序遍历");
        System.out.println(traverse1(root));
        System.out.println("中序遍历");
        System.out.println(traverse2(root));
        System.out.println("后序遍历");
        System.out.println(traverse3(root));


    }
    public  static List<String> traverse1(TreeNode root){
        List<String> result = new ArrayList<>();
        if(root == null){
            return result;
        }
        result.add(root.data);
        result.addAll(traverse1(root.left));

        result.addAll(traverse1(root.right));

        return  result;
    }
    public  static List<String> traverse2(TreeNode root){
        List<String> result = new ArrayList<>();
        if(root == null){
            return result;
        }
        result.addAll(traverse2(root.left));
        result.add(root.data);

        result.addAll(traverse2(root.right));

        return  result;
    }
    public  static List<String> traverse3(TreeNode root){
        List<String> result = new ArrayList<>();
        if(root == null){
            return result;
        }
        result.addAll(traverse3(root.left));

        result.addAll(traverse3(root.right));
        result.add(root.data);


        return  result;
    }

    public static List<String> levelTraverse(TreeNode root){
        List<String> result = new ArrayList<>();
        if(root == null){
            return result;
        }
        Queue<TreeNode> queue = new ArrayDeque();
        queue.add(root);
        while(!queue.isEmpty()){
            TreeNode node = queue.poll();
            result.add(node.data);
            if(node.left !=null) {
                queue.add(node.left);
            }
            if(node.right !=null) {
                queue.add(node.right);
            }
        }

        return  result;

    }




}


class TreeNode {
    public String data;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(String data) {
        this.data =data;
    }
}
