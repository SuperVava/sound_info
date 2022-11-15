import processing.core.PApplet;

import java.util.Objects;

public class TextEditor {
    PApplet processing;
    String message;
    public TextEditor(PApplet processing) {
        this.processing = processing;
        this.message = "";
    }
    public void treatKey(){

        if(processing.key == processing.ENTER){
            StringCoder.play(message);
            message = "";
        }
        else if(processing.key == processing.BACKSPACE){
            if(!Objects.equals(message, "")) message = message.substring(0, message.length()-1);
        }

        else if(processing.key != processing.CODED && (message.length() < 25)){
            message += processing.key;
        }
    }

    public String getMessage() {
        return message;
    }
}
