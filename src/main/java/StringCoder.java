public class StringCoder {
    public static void play(String message){
        char[] charArray = new char[message.length()];

        for (int i = 0; i < message.length(); i++) {
            charArray[i] = message.charAt(i);
        }

        for (char c : charArray) {
            Buzzer.playTone((int)c * 10, 0.1);
        }
    }
}
