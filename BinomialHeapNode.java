import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
/* Class BinomialHeapNode */
class BinomialHeapNode
{
    int key, degree;
    BinomialHeapNode parent;
    BinomialHeapNode sibling;
    BinomialHeapNode child;
    Set<Integer> names;
    /* Constructor */
    public BinomialHeapNode(int k,int nam) 
    {
        names = new HashSet<Integer>();
        names.add(nam);
        key = k;
        degree = 0;
        parent = null;
        sibling = null;
        child = null;        
    }
    /* Function reverse */
    public BinomialHeapNode reverse(BinomialHeapNode sibl) 
    {
            BinomialHeapNode ret;
            if (sibling != null)
                ret = sibling.reverse(this);
            else
                ret = this;
            sibling = sibl;
            return ret;
    }
    /* Function to find min node */
    public BinomialHeapNode findMinNode() 
    {
            BinomialHeapNode x = this, y = this;
            int min = x.key;
 
            while (x != null) {
                if (x.key < min) {
                    y = x;
                    min = x.key;
                }
                x = x.sibling;
            }
 
            return y;
    }
    /* Function to find node with key value */
    public BinomialHeapNode findANodeWithKey(int value) 
    {
            BinomialHeapNode temp = this, node = null;
 
            while (temp != null) 
            {
                if (temp.key == value ) 
                {
                    node = temp;
                    break;
                }
                if (temp.child == null)
                    temp = temp.sibling;
                else 
                {
                    node = temp.child.findANodeWithKey(value);
                    if (node == null)
                        temp = temp.sibling;
                    else
                        break;
                }
            }
            //if(node !=null)
           //     System.out.println("findANodeWithKey = "+ node.name+" "+node.key+" input = "+name+" "+value);
            return node;
    }
    /* Function to find node with key value */
    public BinomialHeapNode findANodeWithKey(int value,int name) 
    {
            BinomialHeapNode temp = this, node = null;
 
            while (temp != null) 
            {
                if (temp.key == value && temp.names.contains(name)) 
                {
                    node = temp;
                    break;
                }
                if (temp.child == null)
                    temp = temp.sibling;
                else 
                {
                    node = temp.child.findANodeWithKey(value,name);
                    if (node == null)
                        temp = temp.sibling;
                    else
                        break;
                }
            }
            //if(node !=null)
           //     System.out.println("findANodeWithKey = "+ node.name+" "+node.key+" input = "+name+" "+value);
            return node;
    }
    /* Function to get size */
    public int getSize() 
    {
        return (1 + ((child == null) ? 0 : child.getSize()) + ((sibling == null) ? 0 : sibling.getSize()));
    }
}
 
/* class BinomialHeap */
class BinomialHeap
{
    private BinomialHeapNode Nodes;
    private int size;
 
    /* Constructor */
    public BinomialHeap()
    {
        Nodes = null;
        size = 0;
    }
    /* Check if heap is empty */
    public boolean isEmpty()
    {
        return Nodes == null;
    }
    /* Function to get size */
    public int getSize()
    {
        return size;
    }
    /* clear heap */
    public void makeEmpty()
    {
        Nodes = null;
        size = 0;
    }
    /* Function to insert */        
    public void insert(int value,int name) 
    {
        BinomialHeapNode temp = null;
        if(Nodes!=null)
             temp = Nodes.findANodeWithKey(value);
        if(temp==null)
            insertUtil(value,name);
        else
            temp.names.add(name);
    }
    public void insertUtil(int value,int name) 
    {
        if (value >= 0)
        {
            BinomialHeapNode temp = new BinomialHeapNode(value,name);
            if (Nodes == null) 
            {
                Nodes = temp;
                size = 1;
            } 
            else 
            {
                unionNodes(temp);
                size++;
            }
        }
    }
    /* Function to unite two binomial heaps */
    private void merge(BinomialHeapNode binHeap) 
    {
        BinomialHeapNode temp1 = Nodes, temp2 = binHeap;
 
        while ((temp1 != null) && (temp2 != null)) 
        {
            if (temp1.degree == temp2.degree) 
            {
                BinomialHeapNode tmp = temp2;
                temp2 = temp2.sibling;
                tmp.sibling = temp1.sibling;
                temp1.sibling = tmp;
                temp1 = tmp.sibling;
            } 
            else 
            {
                if (temp1.degree < temp2.degree) 
                {
                    if ((temp1.sibling == null) || (temp1.sibling.degree > temp2.degree)) 
                    {
                        BinomialHeapNode tmp = temp2;
                        temp2 = temp2.sibling;
                        tmp.sibling = temp1.sibling;
                        temp1.sibling = tmp;
                        temp1 = tmp.sibling;
                    }
                    else 
                    {
                        temp1 = temp1.sibling;
                    }
                }
                else 
                {
                    BinomialHeapNode tmp = temp1;
                    temp1 = temp2;
                    temp2 = temp2.sibling;
                    temp1.sibling = tmp;
                    if (tmp == Nodes) 
                    {
                        Nodes = temp1;
                    }
                    else 
                    {
 
                    }
                }
            }
        }
        if (temp1 == null) 
        {
            temp1 = Nodes;
            while (temp1.sibling != null) 
            {
                temp1 = temp1.sibling;
            }
            temp1.sibling = temp2;
        } 
        else
        {
 
        }
    }
    /* Function for union of nodes */
    private void unionNodes(BinomialHeapNode binHeap) 
    {
        merge(binHeap);
 
        BinomialHeapNode prevTemp = null, temp = Nodes, nextTemp = Nodes.sibling;
 
        while (nextTemp != null) 
        {
            if ((temp.degree != nextTemp.degree) || ((nextTemp.sibling != null) && (nextTemp.sibling.degree == temp.degree))) 
            {                
                prevTemp = temp;
                temp = nextTemp;
            } 
            else
            {
                if (temp.key <= nextTemp.key) 
                {
                    temp.sibling = nextTemp.sibling;
                    nextTemp.parent = temp;
                    nextTemp.sibling = temp.child;
                    temp.child = nextTemp;
                    temp.degree++;
                } 
                else 
                {
                    if (prevTemp == null) 
                    {
                        Nodes = nextTemp;
                    }
                    else 
                    {
                        prevTemp.sibling = nextTemp;
                    }
                    temp.parent = nextTemp;
                    temp.sibling = nextTemp.child;
                    nextTemp.child = temp;
                    nextTemp.degree++;
                    temp = nextTemp;
                }
            }
            nextTemp = temp.sibling;
        }
    }
    /* Function to return minimum key */
    public int findMinimum() 
    {
        return Nodes.findMinNode().key;
    }
    /* Function to delete a particular element */
    public void delete(int value,Integer name) 
    {
        BinomialHeapNode temp = null;
	if(Nodes !=null)
	    temp = Nodes.findANodeWithKey(value,name);
        if ((Nodes != null) && (temp != null)) 
        {
            if(temp.names.size()>1)
                temp.names.remove(name);
            else{
            decreaseKeyValue(value, findMinimum() - 1,name);
            extractMin();
        }
    }
    }
    /* Function to decrease key with a given value */
    public void decreaseKeyValue(int old_value, int new_value,Integer name) 
    {
        BinomialHeapNode temp = null;
	if(Nodes !=null)
	    temp = Nodes.findANodeWithKey(old_value,name);
        if (temp == null)
            return;
        if(temp.names.size()==1){
        temp.key = new_value;
        BinomialHeapNode tempParent = temp.parent;
 
        while ((tempParent != null) && (temp.key < tempParent.key)) 
        {
            int z = temp.key;
            temp.key = tempParent.key;
            tempParent.key = z;
                Set<Integer> zn = temp.names;
                temp.names = tempParent.names;
                tempParent.names = zn;
            temp = tempParent;
            tempParent = tempParent.parent;
        }
    }
        else{
            temp.names.remove(name);
            this.insert(new_value,name);
        }
    }
    /* Function to extract the node with the minimum key */
    public BinomialHeapNode extractMin() 
    {
        if (Nodes == null)
            return null;
 
        BinomialHeapNode temp = Nodes, prevTemp = null;
        BinomialHeapNode minNode = Nodes.findMinNode();
       // System.out.println(temp.name+" "+ minNode.name);
        if(minNode.names.size()>1){
            Integer name = minNode.names.iterator().next();
            minNode.names.remove(name);
            prevTemp = new BinomialHeapNode(minNode.key,name);
            return prevTemp;
        }
        while (temp.key != minNode.key) 
        {
            prevTemp = temp;
            temp = temp.sibling;
        }
 
        if (prevTemp == null) 
        {
            Nodes = temp.sibling;
        }
        else
        {
            prevTemp.sibling = temp.sibling;
        }
 
        temp = temp.child;
        BinomialHeapNode fakeNode = temp;
 
        while (temp != null) 
        {
            temp.parent = null;
            temp = temp.sibling;
        }
 
        if ((Nodes == null) && (fakeNode == null))
        {
            size = 0;
        } 
        else
        {
            if ((Nodes == null) && (fakeNode != null)) 
            {
                Nodes = fakeNode.reverse(null);
                size = Nodes.getSize();
            }
            else
            {
                if ((Nodes != null) && (fakeNode == null))
                {
                    size = Nodes.getSize();
                }
                else
                {
                    unionNodes(fakeNode.reverse(null));
                    size = Nodes.getSize();
                }
            }
        }
 
        return minNode;
    }
 
    /* Function to display heap */
    public void displayHeap()
    {
        System.out.print("\nHeap : ");
        displayHeap(Nodes,0);
        System.out.println("\n");
    }
    private void displayHeap(BinomialHeapNode r,int height)
    {
        if (r != null)
        {
            System.out.print("height= "+height);
            String s = "";
            for(Integer i:r.names)
                s = s+ " "+i+" ";
            
            System.out.print(" ( ["+s +"] , "+r.key+" ) ");
            displayHeap(r.child,height+1);
            System.out.println(" ");
            displayHeap(r.sibling,height);
        }
    }    
}    

public class GraphBH {

    int N;
    boolean dir = false;
    Vertex[] vertices;
	int ECount;
    GraphBH(int n,boolean di){
	ECount=0;
        N=n;
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
        
        BinomialHeap bh = new BinomialHeap( );
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
     //        System.out.println("v= "+v);
            bh.insert( dist[v],v);
        }
        
        // In the followin loop, min heap contains all nodes
        // whose shortest distance is not yet finalized.
        BinomialHeapNode min;int dest,temp;
        ArrayList<Edge> edges;
        
        while (!bh.isEmpty())
        {
            // Extract the vertex with minimum distance value
         //    bh.displayHeap();
	    toutDiji = System.nanoTime();
            min = bh.extractMin();
            toutDiji = System.nanoTime()-toutDiji;
		if(toutDiji<minEmin)
			minEmin= toutDiji;
		else if(toutDiji>maxEmin)
			maxEmin = toutDiji;
           
          //  bh.displayHeap();
            
             int sr = min.names.iterator().next(); // Store the extracted vertex number

          // System.out.println(sr+" "+visited[sr]+" "+dist[sr]);
            
            if(visited[sr] || (dist[sr]==Integer.MAX_VALUE))
                continue;
            visited[sr] = true;
            
            edges = vertices[sr].edges;
            for(Edge e : edges)
            {
                dest = e.destination;
            //    System.out.println("src = "+sr+" dest = "+dest+" dist[sr]"+ dist[sr]);
                
                if ((e.weight + dist[sr] < dist[dest]))
                { 
                    temp = dist[dest];
                    dist[dest] = dist[sr] + e.weight;

                    // update distance value in min heap also
                    
               //     System.out.println("temp = "+temp+" dist[dest] = "+dist[dest]+" dest = "+dest);
                    
 			toutDiji = System.nanoTime();
	                    bh.decreaseKeyValue(temp,dist[dest],dest);
            		toutDiji = System.nanoTime()-toutDiji;
			if(toutDiji<minDKey)
				minDKey= toutDiji;
			else if(toutDiji>maxDKey)
				maxDKey = toutDiji;
			
		     //       bh.displayHeap();
                }
                
         /*       System.out.println("dist");
              for(int i=0;i<N;i++)
                    System.out.print(dist[i]+" "); 
                System.out.println("");*/
                
            }
        }
     //   System.out.println("final Destinations");
   //     for(int i=0;i<N;i++)
       //    System.out.print(dist[i]+" "); 
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
		GraphBH g = new GraphBH(VCount,true);
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
   /* public static void main(String[] args)
    {
     //    checkAVLTree();
        GraphBH g = new GraphBH(9,false);
        g.addEdge( 0, 1, 4);
        g.addEdge( 0, 7, 8);
        g.addEdge( 1, 2, 8);
        g.addEdge( 1, 7, 11);
        g.addEdge( 2, 3, 7);
        g.addEdge( 2, 8, 2);
        g.addEdge( 2, 5, 4);
        g.addEdge( 3, 4, 9);
        g.addEdge( 3, 5, 14);
        g.addEdge( 4, 5, 10);
        g.addEdge( 5, 6, 2);
        g.addEdge( 6, 7, 1);
        g.addEdge( 6, 8, 6);
        g.addEdge( 7, 8, 7);
        g.addEdge( 0, 4, 1);
         g.dijkstra( 0);
        
        
    }*/
    public static void checkAVLTree() {
        /* Make object of BinomialHeap */
        BinomialHeap bh = new BinomialHeap( );
 
         bh.insert(73,1);
        bh.insert(19,2);
        bh.insert(24,3);
        bh.insert(51,4);
        bh.insert(99,5);
        bh.insert(25,6);
        bh.insert(52,7);
        bh.insert(998,8);
        bh.insert(241,9);
        bh.insert(531,10);
        bh.insert(992,11);
        bh.displayHeap();
        System.out.println( bh.getSize());
        bh.delete(51,4);
        bh.displayHeap();
        bh.makeEmpty();
        System.out.println( bh.getSize());
        System.out.println( bh.isEmpty());
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

