package javashoppe;


/** 
   The LinkedList1 class implements a Linked list.
*/

class Queues
{
    /**
       The Node class stores a list element
       and a reference to the next node.
    */
    
    private class Node
    {
        Customer value;   
        Node next;      
        
        /**
           Constructor.            
           @param val The element to store in the node.
           @param n The reference to the successor node.
        */
        
        Node(Customer val, Node n)
        {
            value = val;
            next = n;
        } 
        
        /**
           Constructor. 
           @param val The element to store in the node.
        */
        
        Node(Customer val)
        {
           // Call the other (sister) constructor.
           this(val, null);            
        }
    }	
	 
    private Node first;  // list head
    private Node last;   // last element in list
	     
    /**
       Constructor.
    */
    
    public Queues()
    {
        first = null;
        last = null;        
    }
    
    /**
       The isEmpty method checks to see 
		 if the list is empty.
		 @return true if list is empty, 
		 false otherwise.
    */
    
    public boolean isEmpty()
    {        
        return first == null;       
    }
    
    /**
       The size method returns the length of the list.
       @return The number of elements in the list.
    */
    
    public int size()
    {
       int count = 0;
       Node p = first;     
       while (p != null)
       {
           // There is an element at p
           count ++;
           p = p.next;
       }
       return count;
    }
    
    /**
       The add method adds an element to
		 the end of the list.
       @param e The value to add to the
		 end of the list.       
    */
    
    public void add(Customer e)
    {
      if (isEmpty()) 
      {
          first = new Node(e);
          last = first;
      }
      else
      {
          // Add to end of existing list
          last.next = new Node(e);
          last = last.next;
      }      
    }
    
   
    /**
       The toString method computes the string
       representation of the list.
       @return The string form of the list.
    */
    
    public String toString()
    {
      StringBuilder strBuilder = new StringBuilder();
      
      // Use p to walk down the linked list
      Node p = first;
      while (p != null)
      {
         strBuilder.append(p.value + "\n"); 
         p = p.next;
      }      
      return strBuilder.toString(); 
    }
    
    /**
       The remove method removes the element at an index.
       @param index The index of the element to remove. 
       @return The element removed.  
       @exception IndexOutOfBoundsException When index is 
                  out of bounds.     
    */
    
    public Customer removeFirst()
    {
    	
       int index = 0;
    	
       if (index >= size())
       {  
           String message = String.valueOf(index);
           throw new IndexOutOfBoundsException(message);
       }
       
       Customer element;  // The element to return     

          // Removal of first item in the list
          element = first.value;    
          first = first.next;
          if (first == null)
              last = null;             
       
       return element;        
    }  
    
    
    //gets specific value in the linked list
    public Customer get(int index) {
    	
    	if (index < 0 || index >= size())
        {  
            String message = String.valueOf(index);
            throw new IndexOutOfBoundsException(message);
        }
    	
    	Node pred = first;
        
        // Move pred forward index - 1 times
        for (int k = 1; k <= index -1; k++)
            pred = pred.next;
        
        // Store the value to return
        Customer element = pred.value;
        
        return element;
    }
    
   
    
}