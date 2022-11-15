import processing.core.PApplet;

public class Main extends PApplet{
        Interface anInterface;
        TextEditor editor;
        public static void main(String... args){
            PApplet.main("Main");
        }

        public void settings(){
            setSize(800, 450);
        }

        public void setup(){
            this.anInterface = new Interface(this);
            this.editor = new TextEditor(this);
        }

        public void draw(){
            anInterface.draw();
            anInterface.text(editor.getMessage());
        }

        public void keyPressed(){
            editor.treatKey();
        }

    public void test() {
        PitchDetector pd = new PitchDetector();
        pd.start();
        boolean run = true; // Ceci sera ta condition pour continuer la boucle
        //while(!pd.started){} // On attend que un son soit joué

        while(run) {
            double freq = pd.getFreq();
            System.out.println("Fréquence : "+freq);
        }

        pd.stop();
        System.exit(0);
    }

}
