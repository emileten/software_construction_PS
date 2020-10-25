/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package turtle;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        turtle.forward(sideLength);
        for (int i=0; i<3;i++){
            turtle.turn(90);
            turtle.forward(sideLength);
        }
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        double angle = (double) (180*(sides-2))/(sides);
        /*interesting, there are several ways to do that...
         * 1. 'cast' all the inputs to the operation (what I am doing) or each input individually
         * 2. write the constants as doubles (180.0, 2.0, together or alone b/c java interprets mixed types as double)
         * 3. declare another variable beforehand that's a 'double' conversion of sides. 
         */
        
        return angle;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        int sides= (int) Math.round(2/(1-(angle/180)));
        return sides;
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        double angle = calculateRegularPolygonAngle(sides);
        turtle.forward(sideLength);
        for (int i=0; i<sides-1; i++){
            turtle.turn(angle);
            turtle.forward(sideLength);
        }
    }

    
    
    /**
     * Given the current direction, current location, and a target location, calculate the heading
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentHeading. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentHeading current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to heading (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateHeadingToPoint(double currentHeading, int currentX, int currentY,
                                                 int targetX, int targetY) {
        
        double targetHeading;
        double moveX=targetX-currentX;
        double moveY=targetY-currentY;
        
        if (moveX==0 && moveY==0){
            
            targetHeading=currentHeading;
            
        } else {

            double theta=Math.toDegrees(Math.atan2(Math.abs(moveY), Math.abs(moveX)));
            
            if (moveX>0 && moveY>0){
                
                targetHeading=90-theta;
                
            } else if (moveX>0 && moveY<0){
                
                targetHeading=90+theta;
                
            } else if (moveX<0 && moveY<0){

                targetHeading=270-theta;

            } else if (moveX<0 && moveY>0){

                targetHeading=270+theta;

            } else if (moveX==0 && moveY!=0){
                
                if (moveY>0){
                    
                    targetHeading=0;
                    
                } else {
                    
                    targetHeading=180;
                    
                }
                
            } else { //If you write "moveX!=0 && moveY==0" here, Java thinks targetHeading may not be defined ! 

                if (moveX>0){
                    
                    targetHeading=90;
                    
                } else {
                    
                    targetHeading=270;
                    
                }
                
            }
            
        }
        
        if (targetHeading==currentHeading){
            
            return 0;
            
        } else if (targetHeading>currentHeading){
            
            return targetHeading-currentHeading;
            
        } else {
            
            return 360-(currentHeading-targetHeading);
            
        }

        
    }
    
   
   
    /**
     * I INITIALLY HAD THE WRONG GOAL :( 
     * 
     * Given the current direction, current location, and a target location, calculate the heading
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentHeading. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentHeading current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to heading (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateHeadingToPoint2D(double currentHeading, int currentX, int currentY,
                                                 int targetX, int targetY) {
        
        
        double headingX;
        double headingY;
        
        if (targetX==currentX && targetY==currentY){
            
            return 0 ;
            
        } else {

            if (targetX < currentX) {
                
                headingX=270;
                
            } else {
                
                headingX=90;
                
            }
           
            if (targetY < currentY){
                
                headingY=180; 
                
            } else {
                
                headingY=0;
                
            }
            
            double moveX=headingX-currentHeading;
            double moveY=headingY-currentHeading;
            
            if (moveX<0){
                moveX=moveX+360;
            }
            
            if (moveY<0){
                moveY=moveY+360;
            }
            
            
            if (targetX!=currentX && targetY!=currentY){
            
                if (moveX>moveY){
                    return moveY;
                } else {
                    return moveX;
                }
                
            } else if (targetX==currentX && targetY!=currentY){
                
                return moveY;
                
            } else {
                
                return moveX;
            }
            
        }
        

   
    }

    
       
    
   
    
    /**
     * Given a sequence of points, calculate the heading adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateHeadingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of heading adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateHeadings(List<Integer> xCoords, List<Integer> yCoords) {
        
        List<Double> headingAdjustments = new ArrayList<>();
        
        if (xCoords.size()==0){
            
            return headingAdjustments;
            
        } else {
            
            double currentHeading=0;
            
            for (int i=1 ; i<=xCoords.size()-1; i++){
               
                headingAdjustments.add(i-1,calculateHeadingToPoint(currentHeading, xCoords.get(i-1), yCoords.get(i-1), xCoords.get(i), yCoords.get(i)));
                currentHeading=currentHeading+headingAdjustments.get(i-1);
                
            }
            
            return headingAdjustments;
            
        }
        
    }

    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * I AM DRAWING A STAR ! :D :D :D :D 
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        
        
        List<Integer> starX=Arrays.asList(0,-1,-3,-1,0,1,3,1,0); //don't ask me why I didn't start at 0 ! XD
        List<Integer> starY=Arrays.asList(0,2,3,4,6,4,3,2,0);
        
        List<Double> headingAdjustments=calculateHeadings(starX, starY);
        
        for (int i=0; i<headingAdjustments.size();i++){
            turtle.turn(headingAdjustments.get(i));
            turtle.forward(40);
        }
        
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        // drawRegularPolygon(turtle, 4, 40);
        // drawSquare(turtle, 40);
        drawPersonalArt(turtle);

        // draw the window
        turtle.draw();
    }

}
