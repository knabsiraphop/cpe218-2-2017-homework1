
public class Homework1 {
    public static Tree tree = new Tree();
    public static String input;
    public static Node completeTree;

    public static void main(String[] args) {

        input = "251-*32*+";
        Node root = new Node(input.charAt(input.length()-1));
        input = input.substring(0,input.length()-1);
        tree.root = root;
        calculateTree(tree.root);
        inFix(tree.root);
        System.out.print("=");
        System.out.print(calculate(tree.root));


	}

	public static void inOrder(Node n) {
        if(isOperator(n.data)) {
            if(n.left.left == null ) System.out.print("(");
            inOrder(n.left);
            if((n.left.right!=null && isOperator(n.left.right.data))) System.out.print(")");
            System.out.print(n.data);
            if((n.right.left!=null && isOperator(n.right.left.data))) System.out.print("(");
            inOrder(n.right);
            if(n.right.right == null ) System.out.print(")");
        }else{
            System.out.print(n.data);
        }
    }

    public static void inFix(Node n){
        if(n == tree.root){
            inFix(n.left);
            System.out.print(n.data);
            inFix(n.right);
        }else if(isOperator(n.data)) {
            System.out.print("(");
            inFix(n.left);
            System.out.print(n.data);
            inFix(n.right);
            System.out.print(")");
        }else {
            System.out.print(n.data);
        }
    }
    public static int calculate(Node n){
        if(!isOperator(n.data)) return Integer.parseInt(n.data.toString());
        switch (n.data){
            case '+': return calculate(n.left)+calculate(n.right);
            case '-': return calculate(n.left)-calculate(n.right);
            case '*': return calculate(n.left)*calculate(n.right);
            case '/': return calculate(n.left)/calculate(n.right);
        }
        return 0;

    }
	public static void calculateTree(Node n) {
        if(isOperator(n.data)){
            n.right = new Node(input.charAt(input.length()-1));
            input = input.substring(0,input.length()-1);
            calculateTree(n.right);

            n.left = new Node(input.charAt(input.length()-1));
            input = input.substring(0,input.length()-1);
            calculateTree(n.left);
        }

	}

    private static boolean isOperator(char c) {
       return !Character.isDigit(c);
    }




}
