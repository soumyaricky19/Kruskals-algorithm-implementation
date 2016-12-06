package KruskalsAlgo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Iterator;

public class Kruskals 
{
	private ArrayList<Edge> edges;
	int index=0;
	HashMap<Integer,String> hm=new HashMap<>();
	int length=0;
	Kruskals()
	{
		edges=new ArrayList<>();
	}
	private class Edge implements Comparable<Edge>
	{
		private Integer w,v1,v2;
		Edge(Integer w,Integer v1,Integer v2)
		{
			this.w=w;
			this.v1=v1;
			this.v2=v2;
		}
		public int compareTo(Edge that) 
		{
	      //  return Integer.compare(this.w, that.w);
			return this.w.compareTo(that.w);
	    }
	}

	public void addEdge(Integer w,String v1,String v2)
	{
		Integer intv1 = 0,intv2 = 0;		
		if (!hm.containsValue(v1))
		{
			intv1=length++;
			hm.put(intv1, v1);
		}
		else 
		{
			for (int i=0;i<length;i++)
			{
				if (hm.get(i)!= null && hm.get(i).equals(v1))
				{
					intv1=i;
					break;
				}
			}
		}
		if (!hm.containsValue(v2))
		{
			intv2=length++;
			hm.put(intv2, v2);		
		}
		else 
		{
			for (int i=0;i<length;i++)
			{
				if (hm.get(i)!= null && hm.get(i).equals(v2))
				{
					intv2=i;
					break;
				}
			}				
		}
					
		Edge edge = new Edge(w,intv1,intv2);
		edges.add(edge);
	}
	public void displayEdges()
	{
		Iterator<Edge> itr= edges.iterator();
		while(itr.hasNext())
		{
			System.out.println(itr.next());
		}
	}
	public ArrayList<Edge> getEdges()
	{
		return edges;			
	}
	
	
	public int solve()
	{
		int weight=0;
		int edgesAccepted = 0;
		DisjSets ds = new DisjSets(length);
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		pq.addAll(getEdges());
		
		Edge e; 
		
		while (edgesAccepted < length - 1)
	    {
	        e = (pq.remove());				       
	        int uset = ds.find(e.v1); 
	        int vset = ds.find(e.v2); 
	         if (uset != vset)    			
	        {
	             edgesAccepted++;
	             ds.union(uset, vset); 
	             weight+=e.w;
	             System.out.print(e.w+";"+hm.get(e.v1)+";"+hm.get(e.v2));
	             System.out.println("");
	         }
	   }		
		return weight;
	}
	
	public static void main(String args[])
	{
		Kruskals k=new Kruskals();
		String line=null;
		String separator=","; 
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader("assn9_data.csv"));
            while ((line = br.readLine()) != null) 
            {
                String[] fields = line.split(separator);
                for (int i=1;i<fields.length;i+=2)
                {
                	k.addEdge(Integer.valueOf(fields[i+1]),fields[0],fields[i]);
                }
            }
            br.close();
		}
		catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
		System.out.println("SUM OF DISTANCES="+k.solve());
	}
}
