import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;


public class GraphAVL {

    int N;
    boolean dir = false;
    Vertex[] vertices;
	int ECount;
    GraphAVL(int n,boolean di){
        N=n;ECount=0;
        dir = di;
        vertices = new Vertex[N];
        for(int i=0;i<n;i++){
            this.addVertex(i);
        }
    }
    public void addVertex(int name){
        vertices[name] = new Vertex(name);
    }
    public void addEdge(int src, int dest, int weight){
        vertices[src].addEdge(new Edge(dest,weight));
        if(!dir)
            vertices[dest].addEdge(new Edge(src,weight));
    }
    public void dijkstra(int src,PrintWriter output,PrintWriter plot)
    {

	Long tinDiji = System.nanoTime(),toutDiji,maxEmin=Long.MIN_VALUE,minEmin=Long.MAX_VALUE,maxDKey=Long.MIN_VALUE,minDKey=Long.MAX_VALUE;
        boolean visited[] = new boolean[N];
        int dist[] = new int[N];      // dist values used to pick minimum weight edge in cut
        AVLTree tree = new AVLTree();

        // Initialize min heap with all vertices. dist value of all vertices 
        for (int v = 0; v < N; v++)
        {
            if(v!=src){
                dist[v] = Integer.MAX_VALUE;
            }
            else{
                dist[v] = 0;
            }
                visited[v] = false;
            tree.root = tree.insert(tree.root, dist[v],v);
        }
        
       
        // In the followin loop, min heap contains all nodes
        // whose shortest distance is not yet finalized.
        Node nodes[],min;int dest,temp;
        ArrayList<Edge> edges;
        
        while (tree.root!=null)
        {
            // Extract the vertex with minimum distance value
            toutDiji = System.nanoTime();
            // tree.preOrder(tree.root);
           // System.out.println("");
            nodes = tree.extractMin(tree.root);
            toutDiji = System.nanoTime()-toutDiji;
		if(toutDiji<minEmin)
			minEmin= toutDiji;
		else if(toutDiji>maxEmin)
			maxEmin = toutDiji;
            min = nodes[0];
            tree.root = nodes[1];
          //   tree.preOrder(tree.root);
          //  System.out.println("");
            
            int sr = min.name; // Store the extracted vertex number

       //    System.out.println(sr+" "+visited[sr]+" "+dist[sr]);
            
            if(visited[sr] || (dist[sr]==Integer.MAX_VALUE))
                continue;
            visited[sr] = true;
            
            edges = vertices[sr].edges;
            for(Edge e : edges)
            {
                dest = e.destination;
              //  System.out.println("src = "+sr+" dest = "+dest+" dist[sr]"+ dist[sr]);
                
                if ((e.weight + dist[sr] < dist[dest]))
                { 
                    temp = dist[dest];
                    dist[dest] = dist[sr] + e.weight;

                   // System.out.println("temp = "+temp+" dist[dest] = "+dist[dest]+" dest = "+dest);
                    // update distance value in min heap also
 			toutDiji = System.nanoTime();
            		tree.root = tree.updateNode(tree.root,temp,dest,dist[dest]);
            		toutDiji = System.nanoTime()-toutDiji;
			if(toutDiji<minDKey)
				minDKey= toutDiji;
			else if(toutDiji>maxDKey)
				maxDKey = toutDiji;
                    
                }
        /*               System.out.println("dist");
              for(int i=0;i<N;i++)
                   System.out.print(dist[i]+" "); 
                System.out.println("");*/
                
            }
        }
     //   System.out.println("finalDestinations");
	tinDiji = System.nanoTime() -tinDiji;
	String s = N+" ";
        for(int i=0;i<N;i++)
           s = s+dist[i]+" ";
	 output.println(s);
	if(tinDiji <0)tinDiji=0L;
	if(minEmin <0)minEmin=0L;
	if(maxEmin <0)maxEmin=0L;
	if(minDKey <0)minDKey=0L;
	if(maxDKey <0)maxDKey=0L;
	s = N+" "+this.ECount+" "+(tinDiji)+" "+minEmin+" "+maxEmin+" "+minDKey+" "+maxDKey;
	
	plot.println(s);
	
    }
    public static void execute(int VCount,PrintWriter output,PrintWriter plot) {
       // checkAVLTree();
	//int VCount = Integer.parseInt(args[0]);
	String line,parts[];

	    try {

		BufferedReader bufferreader = new BufferedReader(new FileReader("input/"+(VCount)+".txt"));
		line = bufferreader.readLine();
		GraphAVL g = new GraphAVL(VCount,true);
		if (line != null) {     
		  //do whatever here 
			int ECount = Integer.parseInt(line);
			g.ECount = ECount;
			if(ECount==0)
				return;
			for(int i =0;i<ECount;i++){
				line = bufferreader.readLine();
				parts = line.split(" ");
				g.addEdge(Integer.parseInt( parts[0]), Integer.parseInt(parts[1]),Integer.parseInt( parts[2]));
				
			}
			 g.dijkstra( 0,output,plot);
		}

	    } catch (FileNotFoundException ex) {
		ex.printStackTrace();
	    } catch (IOException ex) {
		ex.printStackTrace();
	    }catch (Exception ex) {
		ex.printStackTrace();
	    }
       
       
    }
    public static void checkAVLTree() {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        AVLTree tree = new AVLTree();
 
        /* Constructing tree given in the above figure */
        tree.root = tree.insert(tree.root, 9,1);
       
        tree.root = tree.insert(tree.root, 5,2);
        tree.root = tree.insert(tree.root, 10,3);
        tree.root = tree.insert(tree.root, 0,4);
        tree.root = tree.insert(tree.root, 6,5);
        tree.root = tree.insert(tree.root, 11,6);
        tree.root = tree.insert(tree.root, -1,7);
        tree.root = tree.insert(tree.root, 1,8);
        tree.root = tree.insert(tree.root, 2,9);
        tree.root = tree.insert(tree.root, 10,11);
        tree.root = tree.insert(tree.root, 9,91);
        tree.root = tree.insert(tree.root, 30,92);
        
        System.out.println("Preorder traversal of "+
                            "constructed tree is : ");
        tree.preOrder(tree.root);
 
        Node nodes[],min;
        while(tree.root!=null){
            nodes = tree.extractMin(tree.root); min = nodes[0];
            tree.root = nodes[1];
            System.out.println("MinNode key = "+min.key+" name = "+min.name);
            System.out.println("");
            tree.preOrder(tree.root);
        }
    }
}

class Edge{
    int destination;
    int weight;
    Edge(int destination,int weight){
        this.destination = destination;
        this.weight = weight;
    }
}
class Vertex{
    int name;
    ArrayList<Edge> edges;
    Vertex(int name){
        this.name = name;
        this.edges = new ArrayList<Edge>();
    }
    public void addEdge(Edge e){
        this.edges.add(e);
    }
}
class AVLTree {
 
   Node root;

    // A utility function to get height of the tree
    int height(Node N)
    {
        if (N == null)
             return 0;
         return N.height;
    }

    // A utility function to get maximum of two integers
    int max(int a, int b)
    {
        return (a > b) ? a : b;
    }

    // A utility function to right rotate subtree rooted with y
    // See the diagram given above.
    Node rightRotate(Node y)
    {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    // A utility function to left rotate subtree rooted with x
    // See the diagram given above.
    Node leftRotate(Node x)
    {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        //  Update heights
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }

    // Get Balance factor of node N
    int getBalance(Node N)
    {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    Node insert(Node node, int key,int name)
    {
        /* 1.  Perform the normal BST rotation */
        if (node == null)
            return (new Node(key,name));

        if (key < node.key)
            node.left = insert(node.left, key,name);
        else if (key > node.key)
            node.right = insert(node.right, key,name);
        else{
            if(node.multipleVal == null){
                node.multipleVal = new HashSet<Integer>();
                node.multipleVal.add(node.name);
            }
            node.multipleVal.add(name);
        }

        /* 2. Update height of this ancestor node */
        node.height = 1 + max(height(node.left),
                              height(node.right));

        /* 3. Get the balance factor of this ancestor
           node to check whether this node became
           Wunbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then
        // there are 4 cases Left Left Case
        if (balance > 1 && key < node.left.key)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && key > node.left.key)
        {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key < node.right.key)
        {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        /* return the (unchanged) node pointer */
        return node;
    }

    /* Given a non-empty binary search tree, return the
       node with minimum key value found in that tree.
       Note that the entire tree does not need to be
       searched. */
    Node minValueNode(Node node)
    {
        Node current = node;

        /* loop down to find the leftmost leaf */
        while (current.left != null)
           current = current.left;

        return current;
    }
    Node updateNode(Node root, int key,int name,int newKey){
        root = this.deleteNode(root,key,name);
        root = this.insert(root,newKey,name);
        return root;
    }
    Node[] extractMin(Node root){
        Node nodes[] = new Node[2],current = this.minValueNode(root);
	if(current ==null){
		nodes[0] = null;nodes[1] = null;
		return nodes;
	}
	int delNodeName = current.name;
        root = this.deleteNode(root,current.key,current.name);
        
        nodes[0] = new Node(current.key,delNodeName);
	nodes[0].height = current.height;
	nodes[0].left = current.left;
	nodes[0].right = current.right;
	nodes[0].multipleVal = current.multipleVal;
        nodes[1] = root;
        return nodes;
    }
     Node findNode(Node root, int key,int name)
    {
      
        // STEP 1: PERFORM STANDARD BST DELETE
        if (root == null)
            return root;

        // If the key to be deleted is smaller than
        // the root's key, then it lies in left subtree
        if (key < root.key)
            return findNode(root.left, key,name);

        // If the key to be deleted is greater than the
        // root's key, then it lies in right subtree
        else if (key > root.key)
            return findNode(root.right, key,name);
        
        // If the key to be deleted is greater than the
        // root's key, then it lies in right subtree
        else{
            if(root.multipleVal != null && root.multipleVal.contains(name)){
                return root;
            }
            else
                return null;
        }
     }
    Node deleteNode(Node root, int key,int name)
    {
      
        // STEP 1: PERFORM STANDARD BST DELETE
        if (root == null)
            return root;

        // If the key to be deleted is smaller than
        // the root's key, then it lies in left subtree
        if (key < root.key)
            root.left = deleteNode(root.left, key,name);

        // If the key to be deleted is greater than the
        // root's key, then it lies in right subtree
        else if (key > root.key)
            root.right = deleteNode(root.right, key,name);
        
        // if key is same as root's key, then this is the node
        // to be deleted
        else
        {
		//System.out.println("del node "+name);
            if(root.multipleVal != null && root.multipleVal.contains(name)){
                if(root.multipleVal.size()>1){
                    root.multipleVal.remove(name);
			while(root.name == name)
				root.name = root.multipleVal.iterator().next();
		  //  root.name = name;
		
                    return root;
                }
            }
            // node with only one child or no child
            if ((root.left == null) || (root.right == null))
            {
                Node temp = null;
                if (temp == root.left)
                    temp = root.right;
                else
                    temp = root.left;

                // No child case
                if (temp == null)
                {
                    temp = root;
                    root = null;
                }
                else   // One child case
                    root = temp; // Copy the contents of
                                 // the non-empty child
            }
            else
            {
                // node with two children: Get the inorder
                // successor (smallest in the right subtree)
                Node temp = minValueNode(root.right);

                // Copy the inorder successor's data to this node
                root.key = temp.key;
                root.name = temp.name;
                root.multipleVal = temp.multipleVal;

                // Delete the inorder successor
                root.right = deleteNode(root.right, temp.key,temp.name);
            }
        }

        // If the tree had only one node then return
        if (root == null)
            return root;

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.height = max(height(root.left), height(root.right)) + 1;

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
        //  this node became unbalanced)
        int balance = getBalance(root);

        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        // Left Right Case
        if (balance > 1 && getBalance(root.left) < 0)
        {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        // Right Left Case
        if (balance < -1 && getBalance(root.right) > 0)
        {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    // A utility function to print preorder traversal of
    // the tree. The function also prints height of every
    // node
    void preOrder(Node node)
    {
        if (node != null)
        {
            String nodeNames;
            if(node.multipleVal!=null){
                nodeNames = "[";
                for(int x : node.multipleVal)
                    nodeNames = nodeNames+" "+x;
                 nodeNames =  nodeNames+"]";
            }
            else
                nodeNames = "["+node.name+"]";
            System.out.print(node.key+" " + nodeNames+  " -> ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }
}
// Java program for insertion in AVL Tree
class Node {
    int key, height,name;
    Node left, right;
    Set<Integer> multipleVal;
    Node(int d,int n) {
        key = d;
        name = n;
        height = 1;
    }
}
