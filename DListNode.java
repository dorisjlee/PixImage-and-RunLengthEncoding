/* DListNode.java */

//package list;

/**
 *  A DListNode is a node in a DList (doubly-linked list).
 */

public class DListNode {

  /**
   *  item references the item stored in the current node.
   *  prev references the previous node in the DList.
   *  next references the next node in the DList.
   *
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
   */

  public Run item;
  protected DListNode prev;
  protected DListNode next;
  protected DListNode head;
  public int size;
  //Makes a bunch of overloaded constructors
  /**
   *  DListNode() constructor.
   *  @param i the item to store in the node.
   *  @param p the node previous to this node.
   *  @param n the node following this node.
   */
  DListNode(Object i, DListNode p, DListNode n) {
    item = (Run)i;
    prev = p;
    next = n;
  }
  // Empty DListNode constructor with no args 
  /*DListNode() {
    item = null;
    prev = null;
    next = null;
  }
  DListNode(Object i) {
    this();
    //head.next.item=(Run)i;
    //this is the cause of the error from autograder 
    //Input Error!!
    //Exception in thread "main" java.lang.StackOverflowError
    //In the line below you are calling a Constructor inside a constructor 
    // So then you reach an infinite loop
    //"fix this, you probably don't need that many constructors inside the DListConstructor"
    //head.next = new DListNode((Run)i);
    head.prev = head.next;
    head.next.prev = head;
    head.prev.next = head;
    size = 1;
  }*/

 /** This is from Lab 4
   *  DListNode2() constructor.
   */
  DListNode() {
    item = null;
    prev = null;
    next = null;
  }

  DListNode(Object i) {
    item = (Run)i;
    prev = null;
    next = null;
  }
}
