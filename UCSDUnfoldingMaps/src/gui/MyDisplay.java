package gui;


import processing.core.PApplet;


public class MyDisplay extends PApplet {

    public void setup() {
        size(400, 400);
        background(200, 200, 200);
    }

    public void draw() {
        fill(255, 255, 0);
        ellipse(200, 200, 390, 390);

        // draw the eyes
        fill(0, 0, 0);
        ellipse(120, 130, 50, 70);
        ellipse(280, 130, 50, 70);

        // draw the mouth
        noFill();
        arc(200, 280, 100, 100, 0, PI);
    }

}
