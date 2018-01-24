/**
 * Created by qowie on 1/21/2018.
 */
public class Tree {
    public static Node root;

    public Tree(){
        root = null;
    }
    public Tree(Node n) {
        root = n;
    }
    public void print(Node n){
        if(n == null)return;
        System.out.println(n.data);
        print(n.right);
        print(n.left);

    }

}
