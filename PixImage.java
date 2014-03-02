/* PixImage.java */

/**
 *  The PixImage class represents an image, which is a rectangular grid of
 *  color pixels.  Each pixel has red, green, and blue intensities in the range
 *  0...255.  Descriptions of the methods you must implement appear below.
 *  They include a constructor of the form
 *
 *      public PixImage(int width, int height);
 *
 *  that creates a black (zero intensity) image of the specified width and
 *  height.  Pixels are numbered in the range (0...width - 1, 0...height - 1).
 *
 *  All methods in this class must be implemented to complete Part I.
 *  See the README file accompanying this project for additional details.
 */

public class PixImage {
  private final boolean DEBUG = false;
  /**
   *  Define any variables associated with a PixImage object here.  These
   *  variables MUST be private.
   */
  private int[][][] image;
  private int width=this.width;
  private int height=this.height;

  /**
   * PixImage() constructs an empty PixImage with a specified width and height.
   * Every pixel has red, green, and blue intensities of zero (solid black).
   *
   * @param width the width of the image.
   * @param height the height of the image.
   */
  public PixImage(int width, int height) {
    image = new int[width][height][3];
    this.width =width; 
    this.height=height;
  }

  /**
   * getWidth() returns the width of the image.
   *
   * @return the width of the image.
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * getHeight() returns the height of the image.
   */
  public int getHeight() {
    // Replace the following line with your solution.
    return this.height;
  }

  /**
   * getRed() returns the red intensity of the pixel at coordinate (x, y).
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the red intensity of the pixel at coordinate (x, y).
   */
  public short getRed(int x, int y) {
    // Replace the following line with your solution.
    return (short) image[x][y][0];
  }

  /**
   * getGreen() returns the green intensity of the pixel at coordinate (x, y).
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the green intensity of the pixel at coordinate (x, y).
   */
  public short getGreen(int x, int y) {
    // Replace the following line with your solution.
    return (short)image[x][y][1];
  }

  /**
   * getBlue() returns the blue intensity of the pixel at coordinate (x, y).
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the blue intensity of the pixel at coordinate (x, y).
   */
  public short getBlue(int x, int y) {
    // Replace the following line with your solution.
    return (short)image[x][y][2];
  }

  /**
   * setPixel() sets the pixel at coordinate (x, y) to specified red, green,
   * and blue intensities.
   *
   * If any of the three color intensities is NOT in the range 0...255, then
   * this method does NOT change any of the pixel intensities.
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @param red the new red intensity for the pixel at coordinate (x, y).
   * @param green the new green intensity for the pixel at coordinate (x, y).
   * @param blue the new blue intensity for the pixel at coordinate (x, y).
   */
  public void setPixel(int x, int y, short red, short green, short blue) {
    if (red>0 && red < 256 )  image[x][y][0]= red;
    if (green>0 && green<256) image[x][y][1]= green; 
    if (blue>0 && blue<256)   image[x][y][2]= blue;
  }

  /**
   * toString() returns a String representation of this PixImage.
   *
   * This method isn't required, but it should be very useful to you when
   * you're debugging your code.  It's up to you how you represent a PixImage
   * as a String.
   *
   * @return a String representation of this PixImage.
   */
 public String toString() {
    //Execute toString() only if I need to debug, change the value of DEBUG constant 
    if (DEBUG==true){
      // Replace the following line with your solution.
      for(int current_x = 0 ;  current_x < this.getWidth()  ; current_x ++){
        for (int current_y = 0  ; current_y < this.getHeight(); current_y ++ ){
            System.out.print(image[current_y][current_x][0]);
            System.out.print("  |  ");
          }
        System.out.println(" ");
        //System.out.println("--------------------------------------------");
      }
      return "";
    }
    return "";
  }
 

  /**
   * boxBlur() returns a blurred version of "this" PixImage.
   *
   * If numIterations == 1, each pixel in the output PixImage is assigned
   * a value equal to the average of its neighboring pixels in "this" PixImage,
   * INCLUDING the pixel itself.
   *
   * A pixel not on the image boundary has nine neighbors--the pixel itself and
   * the eight pixels surrounding it.  A pixel on the boundary has six
   * neighbors if it is not a corner pixel; only four neighbors if it is
   * a corner pixel.  The average of the neighbors is the sum of all the
   * neighbor pixel values (including the pixel itself) divided by the number
   * of neighbors, with non-integer quotients rounded toward zero (as Java does
   * naturally when you divide two integers).
   *
   * Each color (red, green, blue) is blurred separately.  The red input should
   * have NO effect on the green or blue outputs, etc.
   *
   * The parameter numIterations specifies a number of repeated iterations of
   * box blurring to perform.  If numIterations is zero or negative, "this"
   * PixImage is returned (not a copy).  If numIterations is positive, the
   * return value is a newly constructed PixImage.
   *
   * IMPORTANT:  DO NOT CHANGE "this" PixImage!!!  All blurring/changes should
   * appear in the new, output PixImage only.
   *
   * @param numIterations the number of iterations of box blurring.
   * @return a blurred version of "this" PixImage.
   */
  
  public PixImage boxBlur(int numIterations) {
    int n = numIterations;
    
    //Initializing a new PixImage
    //PixImage new_image= new PixImage(this.getWidth(),this.getHeight()); 
      
    short average_red =0 ;
    short average_green = 0;
    short average_blue = 0;

    //For the weird rep, numIteration error, what you need to do is create a new image everytime 
    //and update it to the original image. (your original image is "this")
    //Then instead of getBlue() (which is basically just this.getBlue() )
    // You have updatedImage.getBlue()
    //This is why even though you decremented, because you are copying and calculating on original
    // every single time. so you end up getting the same image everytime
    // which is good for the 1st rep, but perhaps not so the other :(
    PixImage original=this;
    PixImage updatedImage =new PixImage (this.getWidth(),this.getHeight()) ;;
    //If numIterations == 1, each pixel in the output PixImage is assigned
    //a value equal to the average of its neighboring pixels in "this" PixImage,
    // INCLUDING the pixel itself.
    //Anything with numIterations >1 runs through this loop multiple times
    while (n >= 1){
      //At the end of the loop, you your original and updatedImage point to the same object
      // remember this is a reference variable to an object this is why the object is not copied over
      // So you are changing the same object
      // so you need to remake a new image every time you finish iteration
      // to clear out the distinction between updatedImage and original
      updatedImage= new PixImage (this.getWidth(),this.getHeight()) ;

      //Blurring Red
      for (int current_y = 0  ; current_y < this.getHeight() ; current_y ++ ){
        for(int current_x = 0 ;  current_x < this.getWidth()  ; current_x ++){
          //Sum up all neighbors in the same way
          //With the "out of bound" pixels simply as value=0
          
          //Calculate the value of left-top-most pixel from our given (central pixel)
          int y0 = current_y - 1;
          int x0 = current_x - 1;
          int sum = 0;
          int num = 0;
          //Looping through all neighboring pixels
          for (int x=x0 ; x<3+x0 ;x++ ){
              for (int y=y0 ; y<3+y0 ;y++ ){
                  if (x<0 || y<0 || x >= this.width || y >= this.height){
                    //"out of bound" pixels  value=0
                    // System.out.println(y+" "+x);
                    sum = sum + 0;  
                  }
                  else {
                    //Access the values
                    sum = sum + original.getRed(x,y);
                  }
              }
              //System.out.println("--------------");
          }

          //Dividing by num 

          if ( ((current_x==0) && (current_y==0)) || ((current_x==0) && (current_y==this.getHeight()-1)) ||
               ((current_x==this.getWidth()-1) && (current_y==0) || ((current_x==this.getWidth()-1) && (current_y==this.getHeight()-1)))   
                  ){    
              //case where it is a corner pixel  
              num = 4; //3 surrounding and 1 self
          }
          else if ((current_x==0)||(current_x==this.getWidth()-1)||(current_y==0)||(current_y==this.getHeight()-1)){
              //case where pixel on boundary 
              num = 6 ; //5 surrounding and 1 self 
          }
          else{
              //case where it has all surrounding pixels 
              num = 9 ;//8 surrounding and 1 self 
          }

          //Calculating the average pixel
          average_red = (short) (sum/num);    

  /////////////////////////////////////////////////////////////////////////////////////////    
  /////////////////////////////////////////////////////////////////////////////////////////    
      //Blurring Green, same procedure but 3rd index as [1]
          //Sum up all neighbors in the same way
          //With the ``out of bound" pixels simply as value=0
        
          //Calculate the value of left-top-most pixel from our given (central pixel)
          y0 = current_y - 1;
          x0 = current_x - 1;
          sum = 0;
          num = 0;
          //Looping through all neighboring pixels
          for (int x=x0 ; x<3+x0 ;x++ ){
              for (int y=y0 ; y<3+y0 ;y++ ){
                  if (x<0 || y<0 || x >= this.width || y >= this.height){
                    //``out of bound" pixels  value=0
                    // System.out.println(y+" "+x);
                    sum = sum + 0;  
                  }
                  else {
                    //Access the values
                    sum = sum + original.getGreen (x,y);
                  }
              }
              //System.out.println("--------------");
          }

          //Dividing by num 

          if ( ((current_x==0) && (current_y==0)) || ((current_x==0) && (current_y==this.getHeight()-1)) ||
               ((current_x==this.getWidth()-1) && (current_y==0) || ((current_x==this.getWidth()-1) && (current_y==this.getHeight()-1))) ){    
              //case where it is a corner pixel  
              num = 4; //3 surrounding and 1 self
          }
          else if ((current_x==0)||(current_x==this.getWidth()-1)||(current_y==0)||(current_y==this.getHeight()-1)){
              //case where pixel on boundary 
              num = 6 ; //5 surrounding and 1 self 
          }
          else{
              //case where it has all surrounding pixels 
              num = 9 ;//8 surrounding and 1 self 
          }

          //Calculating the average pixel
          average_green = (short) (sum/num);    

  /////////////////////////////////////////////////////////////////////////////////////////    
  /////////////////////////////////////////////////////////////////////////////////////////    

      //Blurring Blue, same procedure but 3rd index as [2]
          //Sum up all neighbors in the same way
          //With the ``out of bound" pixels simply as value=0
        
          //Calculate the value of left-top-most pixel from our given (central pixel)
          y0 = current_y - 1;
          x0 = current_x - 1;
          sum = 0;
          num = 0;
          //Looping through all neighboring pixels
          for (int x=x0 ; x<3+x0 ;x++ ){
              for (int y=y0 ; y<3+y0 ;y++ ){
                  if (x<0 || y<0 || x >= this.width || y >= this.height){
                    //``out of bound" pixels  value=0
                    // System.out.println(y+" "+x);
                    sum = sum + 0;  
                  }
                  else {
                    //Access the values
                    sum = sum + original.getBlue(x,y);
                  }
              }
              //System.out.println("--------------");
          }

          //Dividing by num 

          if ( ((current_x==0) && (current_y==0)) || ((current_x==0) && (current_y==this.getHeight()-1)) ||
               ((current_x==this.getWidth()-1) && (current_y==0) || ((current_x==this.getWidth()-1) && (current_y==this.getHeight()-1)))   
                  ){    
              //case where it is a corner pixel  
              num = 4; //3 surrounding and 1 self
          }
          else if ((current_x==0)||(current_x==this.getWidth()-1)||(current_y==0)||(current_y==this.getHeight()-1)){
              //case where pixel on boundary 
              num = 6 ; //5 surrounding and 1 self 
          }
          else{
              //case where it has all surrounding pixels 
              num = 9 ;//8 surrounding and 1 self 
          }
          //Calculating the average pixel
          average_blue = (short) (sum/num);    
     
  /////////////////////////////////////////////////////////////////////////////////////////    
  /////////////////////////////////////////////////////////////////////////////////////////    
      // Need to set them all together: writing the new average pixel into the new array
          updatedImage.setPixel (current_x,current_y, average_red, average_green,  average_blue);

          }
        }
          original =updatedImage;
        n--;
      }
      return updatedImage;
      
  }

  /**
   * mag2gray() maps an energy (squared vector magnitude) in the range
   * 0...24,969,600 to a grayscale intensity in the range 0...255.  The map
   * is logarithmic, but shifted so that values of 5,080 and below map to zero.
   *
   * DO NOT CHANGE THIS METHOD.  If you do, you will not be able to get the
   * correct images and pass the autograder.
   *
   * @param mag the energy (squared vector magnitude) of the pixel whose
   * intensity we want to compute.
   * @return the intensity of the output pixel.
   */
  private static short mag2gray(long mag) {
    short intensity = (short) (30.0 * Math.log(1.0 + (double) mag) - 256.0);

    // Make sure the returned intensity is in the range 0...255, regardless of
    // the input value.
    if (intensity < 0) {
      intensity = 0;
    } else if (intensity > 255) {
      intensity = 255;
    }
    return intensity;
  }

  /**
   * sobelEdges() applies the Sobel operator, identifying edges in "this"
   * image.  The Sobel operator computes a magnitude that represents how
   * strong the edge is.  We compute separate gradients for the red, blue, and
   * green components at each pixel, then sum the squares of the three
   * gradients at each pixel.  We convert the squared magnitude at each pixel
   * into a grayscale pixel intensity in the range 0...255 with the logarithmic
   * mapping encoded in mag2gray().  The output is a grayscale PixImage whose
   * pixel intensities reflect the strength of the edges.
   *
   * See http://en.wikipedia.org/wiki/Sobel_operator#Formulation for details.
   *
   * @return a grayscale PixImage representing the edges of the input image.
   * Whiter pixels represent stronger edges.
   */
  public PixImage sobelEdges() {
    PixImage sobel_image = new PixImage(width,height);
    for(int x=0; x<sobel_image.getWidth();x++){
      for(int y=0;y<sobel_image.getHeight();y++){
            //Calculation of Sobel Pixels 
              //for convenience: I will be using "int" and making appropriate casting
              // RGB values are stored as short , since it is a smaller D.T. 
              // there is no need to possibility of loss of precision ,hence no need to cast
            int[] value = new int[3]; // RGB values for each pixel 
            int rx = 0; 
            int gx = 0;
            int bx = 0; 
            int ry = 0; 
            int gy = 0; 
            int by = 0;
            int a = 0;
            int b = 0;
            // Gradient values (~)
            int xgrad;
            int ygrad;
            // Defining 3 by 3 convolutions 
            int[][] convo_x = {{1,0,-1},{2,0,-2}, {1,0,-1}};
            int[][] convo_y = {{1,2,1},{0,0,0},{-1,-2,-1}};

            for(int i = -1; i<=1; i++){
              for(int j = -1; j<=1; j++){
                  //Indexing by the central pixel(~)
                
                // Accessing corresponding element from the convolution matrix for dot product later
                xgrad = convo_x[i+1][j+1]; 
                ygrad = convo_y[i+1][j+1];

                if (x+i >= 0 && x+i < width &&  y+j >= 0 && y+j < height){
                  //Case where pixel is does not exceed to boundary
                  a = x+i; 
                  b = y+j;
                } 
                else{
                  // Reflecting Boundary Pixels 
                  if ( x + i < 0 )   {a = x; b = y + j;}
                  if ( y + j < 0 )   {a = x + i; b = y;}
                  if ( y + j >= height) { a = x + i; b = height - 1;}
                  
                  // Out of bound case (Exceed beyond right boundary) ; with their corresponding vertical cases
                  if ( x + i >= width){ a = width -1 ; b = y + j;}
                  if ( y + j < 0) { b = 0;} 

                  // Out of bound (Exceed beyond left boundary); with their corresponding vertical cases
                  if (x+i <0) { a=0;}
                  if ( y + j >= height){ b = height - 1;}
                  if ( y + j < 0){ b = 0;}

                }

                //Compute approximate gradient vector for each of the 3 vectors:
                  // Dot product:
                    //Multiplying corresponding convolution element with corresponding pixel value 
                ry += ygrad * getRed(a,b)   ;
                gy += ygrad * getGreen(a,b) ;
                by += ygrad * getBlue(a,b)  ;
                rx += xgrad * getRed(a,b)   ;
                gx += xgrad * getGreen(a,b) ;
                bx += xgrad * getBlue(a,b)  ;
              }
            }

            int energy = rx*rx + ry*ry + gx*gx + gy * gy + bx*bx +by*by;
            //Input energy into mag2gray to get the grey-scale intensity
            short intensity = mag2gray(energy);
            // for greyscale image the Red,Green,Blue all have the same value
            value[0] = value[1] = value[2]= intensity; 
            sobel_image.image[x][y] = value;
        }
    }
    return sobel_image;
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
   * equals() checks whether two images are the same, i.e. have the same
   * dimensions and pixels.
   *
   * @param image a PixImage to compare with "this" PixImage.
   * @return true if the specified PixImage is identical to "this" PixImage.
   */
  public boolean equals(PixImage image) {
    int width = getWidth();
    int height = getHeight();

    if (image == null ||
        width != image.getWidth() || height != image.getHeight()) {
      return false;
    }

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        if (! (getRed(x, y) == image.getRed(x, y) &&
               getGreen(x, y) == image.getGreen(x, y) &&
               getBlue(x, y) == image.getBlue(x, y))) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * main() runs a series of tests to ensure that the convolutions (box blur
   * and Sobel) are correct.
   */
  public static void main(String[] args) {
    // Be forwarned that when you write arrays directly in Java as below,
    // each "row" of text is a column of your image--the numbers get
    // transposed.
    PixImage image1 = array2PixImage(new int[][] { { 0, 10, 240 },
                                                   { 30, 120, 250 },
                                                   { 80, 250, 255 } });
    System.out.println("Testing getWidth/getHeight on a 3x3 image.  " +
                       "Input image:");
    System.out.print(image1);
    doTest(image1.getWidth() == 3 && image1.getHeight() == 3,
           "Incorrect image width and height.");

    System.out.println("Testing blurring on a 3x3 image.");
    doTest(image1.boxBlur(1).equals(
           array2PixImage(new int[][] { { 40, 108, 155 },
                                        { 81, 137, 187 },
                                        { 120, 164, 218 } })),
           "Incorrect box blur (1 rep):\n" + image1.boxBlur(1));
    doTest(image1.boxBlur(2).equals(
           array2PixImage(new int[][] { { 91, 118, 146 },
                                        { 108, 134, 161 },
                                        { 125, 151, 176 } })),
           "Incorrect box blur (2 rep):\n" + image1.boxBlur(2));
    doTest(image1.boxBlur(2).equals(image1.boxBlur(1).boxBlur(1)),
           "Incorrect box blur (1 rep + 1 rep):\n" +
           image1.boxBlur(2) + image1.boxBlur(1).boxBlur(1));

    System.out.println("Testing edge detection on a 3x3 image.");
    doTest(image1.sobelEdges().equals(
           array2PixImage(new int[][] { { 104, 189, 180 },
                                        { 160, 193, 157 },
                                        { 166, 178, 96 } })),
           "Incorrect Sobel:\n" + image1.sobelEdges());


    PixImage image2 = array2PixImage(new int[][] { { 0, 100, 100 },
                                                   { 0, 0, 100 } });
    System.out.println("Testing getWidth/getHeight on a 2x3 image.  " +
                       "Input image:");
    System.out.print(image2);
    doTest(image2.getWidth() == 2 && image2.getHeight() == 3,
           "Incorrect image width and height.");

    System.out.println("Testing blurring on a 2x3 image.");
    doTest(image2.boxBlur(1).equals(
           array2PixImage(new int[][] { { 25, 50, 75 },
                                        { 25, 50, 75 } })),
           "Incorrect box blur (1 rep):\n" + image2.boxBlur(1));

    System.out.println("Testing edge detection on a 2x3 image.");
    doTest(image2.sobelEdges().equals(
           array2PixImage(new int[][] { { 122, 143, 74 },
                                        { 74, 143, 122 } })),
           "Incorrect Sobel:\n" + image2.sobelEdges());
  }
}