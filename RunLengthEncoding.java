/* RunLengthEncoding.java */

/**
 *  The RunLengthEncoding class defines an object that run-length encodes
 *  a PixImage object.  Descriptions of the methods you must implement appear
 *  below.  They include constructors of the form
 *
 *      public RunLengthEncoding(int width, int height);
 *      public RunLengthEncoding(int width, int height, int[] red, int[] green,
 *                               int[] blue, int[] runLengths) {
 *      public RunLengthEncoding(PixImage image) {
 *
 *  that create a run-length encoding of a PixImage having the specified width
 *  and height.
 *
 *  The first constructor creates a run-length encoding of a PixImage in which
 *  every pixel is black.  The second constructor creates a run-length encoding
 *  for which the runs are provided as parameters.  The third constructor
 *  converts a PixImage object into a run-length encoding of that image.
 *
 *  See the README file accompanying this project for additional details.
 */

/*Bug Fixes from OH (02.20.2014)
1) Since you are using a circularly linked DList , when you keep doing next next next 
you get back o the same node, so unlike Slist you cant just check for !=null on your last item
instead you need to check if head == null 
  @fixed RunLength encoding constructors for this error and various things 
2) In the toPixImage(),
  @ instead of checking if the next item is the same as the current item
  do check if PREVIOUS item is the same as the current item
  because if you do this then you don't have to implement a case when you reach the last (rightmost) column
  You need a case for the first array anyways
3) Be very careful of how you create local variables inside the loop
do you want the variable to refresh every time?
get initialized every time you loop through? (this rarely happens)
4) Overloaded method need to be called by this (parameter)
5) rle is a DList not a RunLengthEncoding
6) When walking though a piximage you want to be walking through it horizontally
so instead of checking x+1,y+1 which checks the diagonal pixel, you need x+1,y
and use a special edge case for going to the next row.
*/


import java.util.Iterator;

public class RunLengthEncoding implements Iterable {

  /**
   *  Define any variables associated with a RunLengthEncoding object here.
   *  These variables MUST be private.
   */


private static final boolean DEBUG = false;

  /**
   *  The following methods are required for Part II.
   */

  /**
   *  RunLengthEncoding() (with two parameters) constructs a run-length
   *  encoding of a black PixImage of the specified width and height, in which
   *  every pixel has red, green, and blue intensities of zero.
   *
   *  @param width the width of the image.
   *  @param height the height of the image.
   */
    private DList rle;

    //A public method that lets you get the DList
    public DList getDList(){
      return rle;
    }
    private int width;  
    private int height;
    private int length;

    //Implementation of 2 parameter constructor
    public RunLengthEncoding(int width, int height) {
      // Your solution here.
      this.width = width;
      this.height = height;
      this.length = width *height;
      // constructing a one-node doublly-linked list with sentinel (Dlist2) 
      Run run_obj = new Run (0,0,0,length);
      //Can not use a Dlist2 since that only takes int value we want this to store the run object
      //DList2 image = new DList2(run_obj);
      //DList image = new DList (run_obj, null, null);
      rle = new DList (run_obj);
      //image.head.prev=null;
      //image.insertFront(0);
    }



  /**
   *  RunLengthEncoding() (with one parameter) is a constructor that creates
   *  a run-length encoding of a specified PixImage.
   * 
   *  Note that you must encode the image in row-major format, i.e., the second
   *  pixel should be (1, 0) and not (0, 1).
   *
   *  @param image is the PixImage to run-length encode.
   */

//RunLengthEncoding constructor that converts a PixImage to a RunLength Encoding
  public RunLengthEncoding (PixImage image){
      this.width = image.getWidth();
      this.height = image.getHeight();
      this.length = width *height;
      Run run =null;
    //RunLengthEncoding rle = new RunLengthEncoding(image.getWidth(),image.getHeight());
    rle = new DList();
    for (int y= 0; y<image.getHeight() ;y++){
      for (int x=0 ; x< image.getWidth() ; x++){
        
        //This is a dummy initialization just to pass the error
        //error: variable run might not have been initialized
        if (x==0 && y==0){
         run = new Run (image.getRed(0,0),image.getGreen(0,0),image.getBlue(0,0),1);
        }
                //1)Building the Run Object:
        //if (y+1 <image.getHeight() && x+1<image.getWidth()){
            //Last pixel has no right neighbour only needed to be checked against previous pixel
            //that checking was already done by the previous pixel.
            // So we are stopping after checking for the 2nd to last pixel
            //which means we might have to put in an extra case for the last pixels
          else if ( (image.getRed(x,y)== run.red) 
            && (image.getGreen(x,y) == run.green)
            && (image.getBlue(x,y) == run.blue) ){
            //if consecutive RGB values are identical then don't create a new Run
            //Simply increase the length of the run
            run.length ++;
          }
          else{
            //create a new run (only do this once!)
            //length of run is 1 upon initialization
            rle.insertBack(run);
            run = new Run (image.getRed(x,y) ,image.getGreen(x,y), image.getBlue(x,y), 1);
          }
        //}
        //somehow put there Run objects into a DList into your rle
        //2)Every run is inserted into the DList
        
        //DListNode node = new DListNode (run);
        //
      }
    }
    rle.insertBack(run);
    check();
  }




/**
   *  RunLengthEncoding() (with six parameters) constructs a run-length
   *  encoding of a PixImage of the specified width and height.  The runs of
   *  the run-length encoding are taken from four input arrays of equal length.
   *  Run i has length runLengths[i] and RGB intensities red[i], green[i], and
   *  blue[i].
   *
   *  @param width the width of the image.
   *  @param height the height of the image.
   *  @param red is an array that specifies the red intensity of each run.
   *  @param green is an array that specifies the green intensity of each run.
   *  @param blue is an array that specifies the blue intensity of each run.
   *  @param runLengths is an array that specifies the length of each run.
   *
   *  NOTE:  All four input arrays should have the same length (not zero).
   *  All pixel intensities in the first three arrays should be in the range
   *  0...255.  The sum of all the elements of the runLengths array should be
   *  width * height.  (Feel free to quit with an error message if any of these
   *  conditions are not met--though we won't be testing that.)
   */



  public RunLengthEncoding(int width, int height, int[] red, int[] green,
                           int[] blue, int[] runLengths) {
    // Your solution here.
    int [] r =red;
    int [] g =green;
    int [] b =blue;

    this.width = width;
    this.height = height;
    /*//Just to check that all input array have the same length and is non-zero
    if ( (red!=blue || green != blue || red != green || red!=runLengths )&&(red.length != 0 && blue.length!=0 && green.length!=0) ){
      System.out.println ("Input Error!!");
    }
    */
/*  this.red = red ;
    this.green = green;
    this.blue = blue; */
    rle = new DList (new Run(r[0],g[0],b[0],runLengths[0]));
    //this basically puts the rgb values into a run object
    //the run object is then inserted into the DList rle
    for (int i=1 ; i < red.length ; i++) { 
      Run run_obj = new Run (red[i],green[i],blue[i],runLengths[i]);
      rle.insertBack(run_obj); 
    }

  }

  /**
   *  getWidth() returns the width of the image that this run-length encoding
   *  represents.
   *
   *  @return the width of the image that this run-length encoding represents.
   */

  public int getWidth() {
    // Replace the following line with your solution.
    return width;
  }

  /**
   *  getHeight() returns the height of the image that this run-length encoding
   *  represents.
   *
   *  @return the height of the image that this run-length encoding represents.
   */
  public int getHeight() {
    // Replace the following line with your solution.
    return height;
  }

  /**
   *  iterator() returns a newly created RunIterator that can iterate through
   *  the runs of this RunLengthEncoding.
   *
   *  @return a newly created RunIterator object set to the first run of this
   *  RunLengthEncoding.
   */
  public RunIterator iterator() {
    // Replace the following line with your solution.
    /*System.out.println ("her0");
    if (rle !=null){
      System.out.println ("there");
    RunIterator iter = new RunIterator(rle.head.next);
    return iter;
    }
    System.out.println ("here");
    return iterator(); */
    if (rle!=null){
      return new RunIterator(rle.head.next);
    }
    //If null then return an empty RunIterator
    return new RunIterator(new DListNode());

    // You'll want to construct a new RunIterator, but first you'll need to
    // write a constructor in the RunIterator class.
  }

  /**
   *  toPixImage() converts a run-length encoding of an image into a PixImage
   *  object.
   *
   *  @return the PixImage that this RunLengthEncoding encodes.
   */
  /*
*/
/*Implement a toPixImage() method in the RunLengthEncoding class,
which converts a run-length encoding to a PixImage object.*/
    /*
    public PixImage toPixImage() {
    PixImage image = new PixImage (width,height) ; 
    // Replace the following line with your solution.
    return new PixImage(1, 1);
    for (int y=0 ; y < height ; y++){
      for (int x=0; x < width ;x++ ){
        Run run = new (__??)
        short  red = (short) getItem(this).red;
        short green = (short) this.getItem().green;
        short blue = (short) this.getItem().blue;
        image.setPixel(x,y, red,green,blue);
        this.getItem().length -- ;
      }
    }
    return image;
  }*/
  //Instead of using a for loop as above and accessing each element
  //We can just use the RunIterator and call next on it to get the PixImage
  //COMPLETE BUT UNABLE TO TEST BECAUSE OF UNIMPLEMENTED CONSTRUCTOR
  public PixImage toPixImage(){
      PixImage image = new PixImage (width, height);
      //SOMETHING IS WRONG HERE , NULL POINTER ERROR
      RunIterator iter = this.iterator();
      if (iter != null ){
        int y=0;
        int x=0;
        while (iter.hasNext()){
          //System.out.println("here");
          //System.out.println("pass");
          //Refreshing the current array with the next one in the iterator 
          int[] temp=iter.next();
          if(temp!= null){
            //System.out.println("here2");
            int[] curr_array = temp;
            int length = curr_array[0];
//            for(y = 0; y < image.getHeight() ; y++ ){
//                for( x = 0;  x < image.getWidth() ; x++){
                  //if there are 1 or more entry of the RGB intensity, we must write it in image
                  while (length>0){
                  // in curr_array we have [pixel_count,r_value,g_value,b_value]
                    //System.out.println(x + " "+y );
                    image.setPixel(x,y,(short)curr_array[1],(short)curr_array[2],(short)curr_array[3]);
                    length --;
                    x++;
                    if (x==image.getWidth()){
                      x=0;
                      y++;
                    }
                  }
              
            
          }
        }
      }
      return image;
  }

  /**
   *  toString() returns a String representation of this RunLengthEncoding.
   *
   *  This method isn't required, but it should be very useful to you when
   *  you're debugging your code.  It's up to you how you represent
   *  a RunLengthEncoding as a String.
   *
   *  @return a String representation of this RunLengthEncoding.
   */
  public String toString() {
    // Replace the following line with your solution.
    if (rle != null){
      return rle.toString(); // this basically just uses the toString method in Dlist
    }
    System.out.println("RLE is null here");
    return "";
  }



  /**
   *  check() walks through the run-length encoding and prints an error message
   *  if two consecutive runs have the same RGB intensities, or if the sum of
   *  all run lengths does not equal the number of pixels in the image.
   */
  /*
  1) If two consecutive runs have exactly the same type of contents.
  For instance, a "99,12" run followed by an "99,8" run is illegal, because
  they should have been consolidated into a single run of twenty 99’s.
  2) If the sum of all the run lengths doesn’t equal the size (in pixels) of
  the PixImage; i.e. its width times its height.
  3) If a run has a length less than 1.
*/

  public void check() {
    if (this.rle!=null){
      //we can only do check() when the DList is non-empty
      // or else it will throw NullPointerException when you call head on it 
      // because there is no "head" to null

      if (this.rle.head.item !=null){
        if (this.rle.head.item.length < 1){
          System.out.println("check() ERROR: run has a length less than 1 ");
        }
      }
      //Counting the sum of all run lengths by looping through the DList 
      //and summing the length value of every single item (runs)
      int sum=0;
      Run run =this.rle.head.next.item;
      Run next_run;
      DListNode curr = this.rle.head.next;
      while(curr!=rle.head){
        //the loop ends when you keep .next .next until you are back to your original position at the sentinel
        run= curr.item;
        sum = sum + run.length;
        curr = curr.next;
        //Just for convenience, we are also going to check for non-identical values 
        //for consecutive runs, while we walk through the dlist
        if (curr !=rle.head){
        next_run=curr.item;

        if ((run.red == next_run.red) &&
            (run.green == next_run.green) &&
            (run.blue == next_run.blue) ){ //If each RGB intensity of consecutive runs are identical then error
          System.out.println("check() ERROR: RGB intensity of consecutive runs should not be equal");
        }
        }
      }

      PixImage img_from_rle = this.toPixImage();
      int pixelSize = img_from_rle.getWidth()*img_from_rle.getHeight();

      if (sum != pixelSize){
        System.out.println(" check() ERROR: Total Run length must equal size of PixImage generated.");
      }
    }
    if (DEBUG == true ) {System.out.println("Sucess!! Passed check() !");}
    //System.out.println("Sucess!! Passed check() !");
  }


  /**
   *  The following method is required for Part IV.
   */

  /**
   *  setPixel() modifies this run-length encoding so that the specified color
   *  is stored at the given (x, y) coordinates.  The old pixel value at that
   *  coordinate should be overwritten and all others should remain the same.
   *  The updated run-length encoding should be compressed as much as possible;
   *  there should not be two consecutive runs with exactly the same RGB color.
   *
   *  @param x the x-coordinate of the pixel to modify.
   *  @param y the y-coordinate of the pixel to modify.
   *  @param red the new red intensity to store at coordinate (x, y).
   *  @param green the new green intensity to store at coordinate (x, y).
   *  @param blue the new blue intensity to store at coordinate (x, y).
   */
public void setPixel(int x, int y, short red, short green, short blue) {
    ////////////////////////////////////
    //DONT IMPLEMENT THIS SOLUTION WILL BE COMPLICATED!!!!!
    // Your solution here, but you should probably leave the following line
    //   at the end.
    // If solution is proportional  to run time,
    // I can add the pixel in by creating a new run
    // insert the new run into the list using insertBack(?)
    // then go through all the list, if consecutive runs have identical intensity then merge them
    // this will yield a run time of n: number of runs + N: position of the pixel in the rle
    // worst case is 2n so in big thetha notation we ignore constant, thus we have solution proportional to runtime
    /*
    *    if (rle != null){
    *  //if (DEBUG){System.out.println("RLE is not null");}
    *  rle.insertBack( (Run) new_pixrun);
    *  //if (DEBUG){System.out.println(rle.toString());}
    *}
    */
    /////////////////////////////////////////
    //Alternative solution
    //Construct a new Run that stores the RGB value, initialize with length of 1
    Run new_pixrun = new Run(red,green,blue,1);

    /* need to walk through by decrementing everytime then when we are at current location
    you test for next and current and prev and see if they are identical.
    */   
    int original_x = x;
    int xi = x;
    int yi = y;
    DListNode prev = null;
    DListNode next = null;
    DListNode curr = this.rle.head.next;
    //while(curr!=rle.head){
      //since this is a circularly linked list
      //this checks if it is already at the end of the list (i.e. the start of your Dlist)
      while (xi>=0 && yi>=0 &&curr!=null){//while this is not beyond our (x,y) 
          //run= curr.item;
          //sum = sum + run.length;
          curr = curr.next;
        if (xi==1 && yi ==0 && curr.next!=null){
          //when at one before (x,y) of pixel
          //save this node as prev as advance curr to (x,y)
          prev = curr;
          curr=curr.next;
        //else if (xi==0 && yi ==0){
          //curr is still curr
          //but we need to save the next one into next
          //next = curr.next;
        
        if (prev!=null&&prev.item!=null){
          //So now we have prev, curr, and next DListNode values
          //we need to do some equality comparisons
            if ( ((prev.item.red ==new_pixrun.red) &&
                  (prev.item.green ==new_pixrun.green) &&
                  (prev.item.blue ==new_pixrun.blue) ) ||
                 ( (curr.item.red ==new_pixrun.red) &&
                   (curr.item.green ==new_pixrun.green)&&
                   (curr.item.blue ==new_pixrun.blue) )  ) {
                //if current run does not contain the same RGB intensity as next and prev
                //then insert the new_pixrun in between curr and prev
                DListNode new_node= new DListNode(new_pixrun);
                new_node.prev = prev;
                new_node.next = curr;
                prev.next = new_node;
                curr.prev = new_node;
              }
            else if ( ((prev.item.red ==new_pixrun.red) &&
                  (prev.item.green ==new_pixrun.green) &&
                  (prev.item.blue ==new_pixrun.blue) ) &&
                 ( (curr.item.red ==new_pixrun.red) &&
                   (curr.item.green ==new_pixrun.green)&&
                   (curr.item.blue ==new_pixrun.blue) ) ) {
                //if current run contain same RGB intensity as BOTH prev and curr
                //then simply add 1 to the length of the run
                curr.item.length++;
            } 
            else if ( ((prev.item.red ==new_pixrun.red) &&
                  (prev.item.green ==new_pixrun.green) &&
                  (prev.item.blue ==new_pixrun.blue) ) &&
                 ( (curr.item.red ==new_pixrun.red) &&
                   (curr.item.green ==new_pixrun.green)&&
                   (curr.item.blue ==new_pixrun.blue) ) ) {
              //if current run contain same RGB intensity as BOTH prev and curr
              //then simply add 1 to the length of the run
                curr.item.length++;
                //it doesn't matter if you do curr.item.length or prev.item.length 
                //they point to the same run object anyways
            } 
            else if ( (curr.item.red ==new_pixrun.red) &&
                   (curr.item.green ==new_pixrun.green)&&
                   (curr.item.blue ==new_pixrun.blue) )  {
                  // if current run contain same RGB intensity as curr
                  //then add 1 to the length of the curr's run
                  curr.item.length++;
            }
            else if ( ((prev.item.red ==new_pixrun.red) &&
                  (prev.item.green ==new_pixrun.green) &&
                  (prev.item.blue ==new_pixrun.blue) )) {
                  // if current run contain same RGB intensity as prev
                  //then add 1 to the length of prev's run
                  prev.item.length++;
            }
            else{
              //In the case where the prev and curr run is the same
              //but the new_pixrun is different. 
              //the we need to break up the run into two 
              //and insert new_node (which contains new_pixrun) in between
                DListNode new_node= new DListNode(new_pixrun);
                new_node.prev = prev;
                new_node.next = curr;
                prev.next = new_node;
                curr.prev = new_node;
                int count =0;
                //Back track to the different Run
                 
              
                //Forward track to the different Run
            }
          }
        }


        



        //this is what advances the xi,yi values
        if (xi==0 &&yi>=0){
          //more row to go
          yi--;
          xi=original_x;//re-setting the x value 
        }else{
          xi --;
        }
      }

    //}
    /* We go through the DList and try to find a run that has identical RGB values
    If we can find it then we increate the length of that run to ++
    If we can not find it, that means we need to actually insert the run
     to  a positon right after the one you just checked
    */

    //check();
  }




  /**
   * TEST CODE:  YOU DO NOT NEED TO FILL IN ANY METHODS BELOW THIS POINT.
   * You are welcome to add tests, though.  Methods below this point will not
   * be tested.  This is not the autograder, which will be provided separately.
   */


  /**
   * doTest() checks whether the condition is true and prints the given error
   * message if it is not.
   *
   * @param b the condition to check.
   * @param msg the error message to print if the condition is false.
   */
  private static void doTest(boolean b, String msg) {
    if (b) {
      System.out.println("Good.");
    } else {
      System.err.println(msg);
    }
  }

  /**
   * array2PixImage() converts a 2D array of grayscale intensities to
   * a grayscale PixImage.
   *
   * @param pixels a 2D array of grayscale intensities in the range 0...255.
   * @return a new PixImage whose red, green, and blue values are equal to
   * the input grayscale intensities.
   */
  private static PixImage array2PixImage(int[][] pixels) {
    int width = pixels.length;
    int height = pixels[0].length;
    PixImage image = new PixImage(width, height);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        image.setPixel(x, y, (short) pixels[x][y], (short) pixels[x][y],
                       (short) pixels[x][y]);
      }
    }

    return image;
  }

  /**
   * setAndCheckRLE() sets the given coordinate in the given run-length
   * encoding to the given value and then checks whether the resulting
   * run-length encoding is correct.
   *
   * @param rle the run-length encoding to modify.
   * @param x the x-coordinate to set.
   * @param y the y-coordinate to set.
   * @param intensity the grayscale intensity to assign to pixel (x, y).
   */
  private static void setAndCheckRLE(RunLengthEncoding rle,
                                     int x, int y, int intensity) {
    rle.setPixel(x, y,(short) intensity, (short) intensity, (short) intensity);
    rle.check();
  }

  /**
   * main() runs a series of tests of the run-length encoding code.
   */
  public static void main(String[] args) {
    // Be forwarned that when you write arrays directly in Java as below,
    // each "row" of text is a column of your image--the numbers get
    // transposed.
    PixImage image1 = array2PixImage(new int[][] { { 0, 3, 6 },
                                                   { 1, 4, 7 },
                                                   { 2, 5, 8 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 3x3 image.  Input image:");
    System.out.print(image1);
    RunLengthEncoding rle1 = new RunLengthEncoding(image1);
    System.out.println("Representation of RLE1: "+rle1.toString());
    rle1.check();
    System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
    doTest(rle1.getWidth() == 3 && rle1.getHeight() == 3,
           "RLE1 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 3x3 encoding.");
    doTest(image1.equals(rle1.toPixImage()),
           "image1 -> RLE1 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 0, 42);
    image1.setPixel(0, 0, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           /*
                       array2PixImage(new int[][] { { 42, 3, 6 },
                                                    { 1, 4, 7 },
                                                    { 2, 5, 8 } })),
           */
           "Setting RLE1[0][0] = 42 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 1, 0, 42);
    image1.setPixel(1, 0, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[1][0] = 42 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 1, 2);
    image1.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[0][1] = 2 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 0, 0, 0);
    image1.setPixel(0, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[0][0] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 2, 2, 7);
    image1.setPixel(2, 2, (short) 7, (short) 7, (short) 7);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[2][2] = 7 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 2, 2, 42);
    image1.setPixel(2, 2, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[2][2] = 42 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle1, 1, 2, 42);
    image1.setPixel(1, 2, (short) 42, (short) 42, (short) 42);
    doTest(rle1.toPixImage().equals(image1),
           "Setting RLE1[1][2] = 42 fails.");


    PixImage image2 = array2PixImage(new int[][] { { 2, 3, 5 },
                                                   { 2, 4, 5 },
                                                   { 3, 4, 6 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on another 3x3 image.  Input image:");
    System.out.print(image2);
    RunLengthEncoding rle2 = new RunLengthEncoding(image2);
    System.out.println("Representation of RLE2: "+rle2.toString());
    rle2.check();
    System.out.println("Testing getWidth/getHeight on a 3x3 encoding.");
    doTest(rle2.getWidth() == 3 && rle2.getHeight() == 3,
           "RLE2 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 3x3 encoding.");
    doTest(rle2.toPixImage().equals(image2),
           "image2 -> RLE2 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle2, 0, 1, 2);
    image2.setPixel(0, 1, (short) 2, (short) 2, (short) 2);
    doTest(rle2.toPixImage().equals(image2),
           "Setting RLE2[0][1] = 2 fails.");

    System.out.println("Testing setPixel() on a 3x3 encoding.");
    setAndCheckRLE(rle2, 2, 0, 2);
    image2.setPixel(2, 0, (short) 2, (short) 2, (short) 2);
    doTest(rle2.toPixImage().equals(image2),
           "Setting RLE2[2][0] = 2 fails.");


    PixImage image3 = array2PixImage(new int[][] { { 0, 5 },
                                                   { 1, 6 },
                                                   { 2, 7 },
                                                   { 3, 8 },
                                                   { 4, 9 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 5x2 image.  Input image:");
    System.out.print(image3);
    RunLengthEncoding rle3 = new RunLengthEncoding(image3);
    System.out.println("Representation of RLE3: "+rle3.toString());
    rle3.check();
    System.out.println("Testing getWidth/getHeight on a 5x2 encoding.");
    doTest(rle3.getWidth() == 5 && rle3.getHeight() == 2,
           "RLE3 has wrong dimensions");

    System.out.println("Testing toPixImage() on a 5x2 encoding.");
    doTest(rle3.toPixImage().equals(image3),
           "image3 -> RLE3 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 4, 0, 6);
    image3.setPixel(4, 0, (short) 6, (short) 6, (short) 6);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[4][0] = 6 fails.");

    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 0, 1, 6);
    image3.setPixel(0, 1, (short) 6, (short) 6, (short) 6);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[0][1] = 6 fails.");

    System.out.println("Testing setPixel() on a 5x2 encoding.");
    setAndCheckRLE(rle3, 0, 0, 1);
    image3.setPixel(0, 0, (short) 1, (short) 1, (short) 1);
    doTest(rle3.toPixImage().equals(image3),
           "Setting RLE3[0][0] = 1 fails.");


    PixImage image4 = array2PixImage(new int[][] { { 0, 3 },
                                                   { 1, 4 },
                                                   { 2, 5 } });

    System.out.println("Testing one-parameter RunLengthEncoding constuctor " +
                       "on a 3x2 image.  Input image:");
    System.out.print(image4);
    RunLengthEncoding rle4 = new RunLengthEncoding(image4);
    System.out.println("Representation of RLE4: "+rle4.toString());
    rle4.check();
    System.out.println("Testing getWidth/getHeight on a 3x2 encoding.");
    doTest(rle4.getWidth() == 3 && rle4.getHeight() == 2,
           "RLE4 has wrong dimensions");
    System.out.println("Testing toPixImage() on a 3x2 encoding.");
    doTest(rle4.toPixImage().equals(image4),
           "image4 -> RLE4 -> image does not reconstruct the original image");

    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 2, 0, 0);
    image4.setPixel(2, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[2][0] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 1, 0, 0);
    image4.setPixel(1, 0, (short) 0, (short) 0, (short) 0);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[1][0] = 0 fails.");

    System.out.println("Testing setPixel() on a 3x2 encoding.");
    setAndCheckRLE(rle4, 1, 0, 1);
    image4.setPixel(1, 0, (short) 1, (short) 1, (short) 1);
    doTest(rle4.toPixImage().equals(image4),
           "Setting RLE4[1][0] = 1 fails.");
  }
}
