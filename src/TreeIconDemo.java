import javax.swing.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;

import java.awt.*;
import java.net.URL;
import java.io.IOException;

public class TreeIconDemo extends JPanel
    implements TreeSelectionListener{
        private JEditorPane htmlPane;
        private JTree tree;
        private URL helpURL;
        public static Tree Tree = new Tree();
        public static String input;
        private static boolean DEBUG = false;
        //Optionally play with line styles.  Possible values are
        //"Angled" (the default), "Horizontal", and "None".
        private static boolean playWithLineStyle = false;
        private static String lineStyle = "Horizontal";
        //Optionally set the look and feel.
        private static boolean useSystemLookAndFeel = false;
        public static Node root = null;
        
        public TreeIconDemo() {
            super(new GridLayout(1,0));
            //Create the nodes.
            DefaultMutableTreeNode top = new DefaultMutableTreeNode(root);
            createNodes(top,Homework1.completeTree);
            //Create a tree that allows one selection at a time.
            tree = new JTree(top);
            tree.getSelectionModel().setSelectionMode
                    (TreeSelectionModel.SINGLE_TREE_SELECTION);
            //Listen for when the selection changes.
            tree.addTreeSelectionListener(this);
            if (playWithLineStyle) {
                System.out.println("line style = " + lineStyle);
                tree.putClientProperty("JTree.lineStyle", lineStyle);
            }
            //Create the scroll pane and add the tree to it.
            JScrollPane treeView = new JScrollPane(tree);
            //Create the HTML viewing pane.
            htmlPane = new JEditorPane();
            htmlPane.setEditable(false);
            initHelp();
            JScrollPane htmlView = new JScrollPane(htmlPane);
            //Add the scroll panes to a split pane.
            JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
            splitPane.setTopComponent(treeView);
            splitPane.setBottomComponent(htmlView);
            Dimension minimumSize = new Dimension(100, 50);
            htmlView.setMinimumSize(minimumSize);
            treeView.setMinimumSize(minimumSize);
            splitPane.setDividerLocation(100);
            splitPane.setPreferredSize(new Dimension(500, 300));
            add(splitPane);
            ImageIcon leafIcon = createImageIcon("images/middle.gif");
            if(leafIcon != null) {
                DefaultTreeCellRenderer renderer =
                        new DefaultTreeCellRenderer();
                renderer.setOpenIcon(leafIcon);
                renderer.setClosedIcon(leafIcon);
                tree.setCellRenderer(renderer);
            }
        }

        public static boolean IsLeaf = false;
        public static boolean IsRoot = false;
        /** Required by TreeSelectionListener interface. */

        protected static ImageIcon createImageIcon(String path) {
            java.net.URL imgURL = TreeIconDemo.class.getResource("middle.gif");
            if (imgURL != null) {
                return new ImageIcon(imgURL);
            } else {
                System.err.println("Couldn't find file: " + path);
                return null;
            }
        }

        public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                    tree.getLastSelectedPathComponent();
            Node root = new Node(input.charAt(input.length()-1));
            input = input.substring(0,input.length()-1);
            tree.root = root;
            calculateTree(tree.root);
            inFix(tree.root);
            System.out.print("=");
            System.out.print(calculate(tree.root));

        }




    private class BookInfo {
            public String bookName;
            public URL bookURL;
            public BookInfo(String book, String filename) {
                bookName = book;
                bookURL = getClass().getResource(filename);
                if (bookURL == null) {
                    System.err.println("Couldn't find file: "
                            + filename);
                }
            }
            public String toString() {
                return bookName;
            }
        }

        private void initHelp() {
            String s = "TreeDemoHelp.html";
            helpURL = getClass().getResource(s);
            if (helpURL == null) {
                System.err.println("\n");
            } else if (DEBUG) {
                System.out.println("Help URL is " + helpURL);
            }
            displayURL(helpURL);
        }

        private void displayURL(URL url) {
            try {
                if (url != null) {
                    htmlPane.setPage("TEST TEST");
                } else { //null url
                    htmlPane.setText("File Not Found");
                    if (DEBUG) {
                        System.out.println("Attempted to display a null URL.");
                    }
                }
            } catch (IOException e) {
                System.err.println("Attempted to read a bad URL: " + url);
            }
        }
        public static String Text;
        public static String fullTxt;


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
    public static void inFix( Node n){
        if(n == Tree.root){
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


        private void createNodes(DefaultMutableTreeNode top,Node completeTree) {
            if(completeTree.left != null){
                DefaultMutableTreeNode left = new DefaultMutableTreeNode(completeTree.left);
                top.add(left);
                createNodes(left,completeTree.left);
            }
            if(completeTree.right != null){
                DefaultMutableTreeNode right = new DefaultMutableTreeNode(completeTree.right);
                top.add(right);
                createNodes(right,completeTree.right);
            }
        }

        /**
         * Create the GUI and show it.  For thread safety,
         * this method should be invoked from the
         * event dispatch thread.
         */
        private static void createAndShowGUI() {
            //Create and set up the window.
            JFrame frame = new JFrame("HW1");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //Create and set up the content pane.
            TreeIconDemo newContentPane = new TreeIconDemo();
            newContentPane.setOpaque(true); //content panes must be opaque
            frame.setContentPane(newContentPane);

            //Display the window.
            frame.pack();
            frame.setVisible(true);
        }

        public static void main(Node n) {
            //Schedule a job for the event dispatch thread:
            //creating and showing this application's GUI.
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI();
                }
            });
        }
    }