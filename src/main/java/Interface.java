import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

public class Interface {
    PApplet processing;
    int centerX;
    int centerY;
    PFont sourceCode;
    char cursor;

    public Interface(PApplet processing) {
        this.processing = processing;
        centerX = processing.width / 2;
        centerY = processing.height / 2;
        this.sourceCode = processing.createFont("Retro Gaming.ttf", 20, true);
        processing.textFont(sourceCode);
    }

    public void draw(){
        processing.background(0);
        processing.rectMode(PConstants.CENTER);
        processing.fill(250);
        processing.rect(centerX, centerY, processing.width / 2, processing.height / 10);
        processing.fill(0);
        processing.rect(centerX, centerY, processing.width / 2 - 5, processing.height / 10 - 5);
    }

    public void text(String message) {
        cursor = '|';
        processing.fill(255);
        processing.text(message + cursor, (float)processing.width / 4 + 10, centerY + 10);
    }
}
