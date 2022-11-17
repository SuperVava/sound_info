import processing.core.PApplet;

public class Main extends PApplet{
        Interface anInterface;
        TextEditor editor;
        PitchDetector pd;
        public static void main(String... args){
            PApplet.main("Main");
        }

        public void settings(){
            setSize(800, 450);
        }

        public void setup(){
            this.anInterface = new Interface(this);
            this.editor = new TextEditor(this);
            this.pd = new PitchDetector();
        }

        public void draw(){
            anInterface.draw();
            anInterface.text(editor.getMessage());
        }

        public void keyPressed(){
            editor.treatKey();
            if (key == '²'){
                test();
            }
        }

    public void test() {
        pd.start();
        double freq = 2*pd.getFreq();
        char sign = pd.getChar();
        System.out.print("Fréquence : "+freq);
        System.out.println(" | Character : "+sign);
        PitchDetector.stop();
    }

}
