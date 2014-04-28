package pprefixspanalgorithm;
import java.util.*;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import pprefixspanalgorithm.Item;

/**
 *
 * @author Ananya
 */
public class PPrefixSpanAlgorithm {

    static int min_support=2,expected_time_period=7;

   
        static double min_probability=0.3;
    static ArrayList S=new ArrayList();
          static int tMax;
    
    public static void main(String[] args) {
        //System.out.println("kanav anand");
        //assuming inputs
       ArrayList t0=new ArrayList();
       ArrayList i0=new ArrayList();
       i0.add(new Item("b",2));
       i0.add(new Item("c",2));
       i0.add(new Item("a",4));
       i0.add(new Item("d",7));
       i0.add(new Item("a",8));
       i0.add(new Item("f",8));
       i0.add(new Item("c",15));
       t0.add(i0);
       S.add(0,t0);
       ArrayList t1=new ArrayList();
       
       ArrayList i1=new ArrayList();
       i1.add(new Item("a",0));
       i1.add(new Item("d",5));
       i1.add(new Item("f",5));
       i1.add(new Item("b",12));
       t1.add(i1);
       S.add(1,t1);
       ArrayList t2=new ArrayList();
       
       ArrayList i2=new ArrayList();
       i2.add(new Item("a",1));
       i2.add(new Item("b",1));
       i2.add(new Item("c",1));
       i2.add(new Item("f",4));
       i2.add(new Item("a",6));
       i2.add(new Item("c",6));
       i2.add(new Item("b",8));
       i2.add(new Item("c",9));
       t2.add(i2);
       S.add(2,t2);
       ArrayList t3=new ArrayList();
       
       ArrayList i3=new ArrayList();
       i3.add(new Item("c",5));
       i3.add(new Item("a",7));
       i3.add(new Item("b",15));
       i3.add(new Item("d",18));
       i3.add(new Item("f",18));
       t3.add(i3);
       S.add(3,t3);
       tMax=18;
        String a="";
       
        PPrefixSpan(a,a.length(),S);
       
       
       
       
       
    }
    public static void PPrefixSpan(String a,int l,ArrayList S_a)
    {
        ArrayList freq_items=new ArrayList();
        //scan S_a and find frequent items
        HashMap hm=new HashMap();
        Iterator itr=S_a.listIterator();
        while(itr.hasNext())//id
        {
            ArrayList itemlist=new ArrayList();
            itemlist=(ArrayList) itr.next();
            Iterator i1=itemlist.listIterator();
            
            while(i1.hasNext())//postfixes
            {
                ArrayList items=new ArrayList();
                items=(ArrayList)i1.next();
                Iterator i2=items.listIterator();
                while(i2.hasNext())
                {
                    Item item=(Item)i2.next();
                
                if(hm.containsKey(item.Item) && (Integer)hm.get(item.Item)>=PPrefixSpanAlgorithm.min_support && !freq_items.contains(item.Item))
                {
                    hm.put(item.Item, (Integer)hm.get(item.Item)+1);
                    freq_items.add(item.Item);
                }
                else if(hm.containsKey(item.Item) && (Integer)hm.get(item.Item)<PPrefixSpanAlgorithm.min_support)
                    hm.put(item.Item, (Integer)hm.get(item.Item)+1);
                else
                    hm.put(item.Item, 1);
                }
                }
                
                
            
        }
        
        
        
        ArrayList a_=new ArrayList();
        int i=0;
        if(l==0)
        {
            //append all freq items to a to get a_
            Iterator itr2=freq_items.listIterator();
            while(itr2.hasNext())
            {
                a_.add(a+itr2.next().toString());
                
                
                i++;
            }
        }
        
       
        if(l>0)
        {
            
            Iterator itr2=freq_items.listIterator();
            while(itr2.hasNext())
            {
                String curr=itr2.next().toString();
                double arrivalRate=calcArrivalRate(a,S_a,curr);
                double IATP=1-Math.exp(arrivalRate*expected_time_period);
                if(IATP>min_probability)
                {
                    //append freq item to a as a_
                	System.out.println("kanav anand");
                    a_.add(curr);
                }
            }
        }
        
        Iterator itr3=a_.listIterator();
        
            
        while(itr3.hasNext())
        {
            ArrayList S_a_=new ArrayList();
            String curr=itr3.next().toString();
            
            //construct S_a_
            Iterator itr4=S.listIterator();
            while(itr4.hasNext())//id
            {
                ArrayList t=(ArrayList)itr4.next();
                ListIterator itr5=t.listIterator();
                while(itr5.hasNext())//postfix
                {
                    ArrayList q=(ArrayList)itr5.next();
                    Iterator itr6=q.listIterator();
                    int flag=0;
                    while(itr6.hasNext())
                    {
                        ArrayList temp_items=new ArrayList();
                        Item item=(Item)itr6.next();
                        if(item.Item.compareTo(curr)==0 && itr6.hasNext())
                        {flag=1;
                        
                        temp_items.add(new Item("_",item.transactionTime));
                        continue;
                        }
                        if (flag==1)
                        {
                        
                        temp_items.add(new Item(item.Item,item.transactionTime));
                
                
                        }
                    itr5.add(temp_items);
                    }
                }
            
            S_a_.add(t);
            
            }
            /*ArrayList al=(ArrayList)S_a_.get(0);
            System.out.println(al.size());
            
            System.out.println(curr+" ");
            Iterator i1=S_a_.listIterator();
            
            while(i1.hasNext())
            {
            ArrayList x=new ArrayList();
            x =(ArrayList)i1.next();
               Iterator i2=x.listIterator();
               while(i2.hasNext())
               {
                   ArrayList y=new ArrayList();
                    y=(ArrayList)i2.next();
                    Iterator i3=y.listIterator();
                    while(i3.hasNext())
                    {
                       Item z=(Item)i3.next();
                        System.out.print(z.Item);
             
                    }
                    System.out.println();
               }
               System.out.println();
            }
            
            */
            //call pprefixspan recursively
//            PPrefixSpan(curr,curr.length(),S_a_);
            
        }
        
        
    }
     public static double calcArrivalRate(String a, ArrayList S_a, String b) {
        
         int t1;//transaction time of last item in a
         int r=0;
         int o1=0,o2=0;
         
         Iterator itr1=S_a.listIterator();
         while(itr1.hasNext())//id
         {
             ArrayList itemlist=(ArrayList)itr1.next();
             Iterator itr2=itemlist.listIterator();
             int flag=0;
             while(itr2.hasNext())//postfixes
             {
                 
                 ArrayList items=(ArrayList)itr2.next();
                 Iterator itr3=items.listIterator();
                 if(!items.isEmpty())
                 t1=((Item)(items.get(0))).transactionTime;
                 else t1=0;
                 while(itr3.hasNext())//items
                 {
                     Item item=(Item)itr3.next();
                     
                     if(item.Item.compareTo(b)==0)
                     {
                         r++;
                         int tb=item.transactionTime;
                         
                         o1=o1+tb-t1;
                         flag=1;
                     }
                 }
                 if(flag==0)
                 {
                     o2=o2+tMax-t1;
                 
                 }
             }
             
         }
         
         return r/(o1+o2);
    }
}
