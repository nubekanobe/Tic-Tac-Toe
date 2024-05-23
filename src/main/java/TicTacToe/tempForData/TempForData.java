package TicTacToe.tempForData;


public class TempForData
{
    //toggle sides
    public static boolean sidesOnButton = true;
    public static boolean sidesOffButton = false;

    //normal/easy difficulty
    public static boolean normalButton = true;
    public static boolean easyButton = false;

    //volume
    public static int soundVolume = 100;
    public static int musicVolume = 30;

    // 1 OR 2 player
    public static int mode = 1;

    //current theme 0-3 -- Winter == 0, Spring == 1, Summer == 2, Fall == 3
    public static int currentTheme = 0;

    //the css information for the 4 themes
    public static String[] theme = {"-fx-background-color: linear-gradient(to bottom, #B0E0E6, #FFFFFF, #C0C0C0)", "-fx-background-color: linear-gradient(hotpink, #AEEEEE, #98FF98, gold);", "-fx-background-color: linear-gradient(to bottom, Skyblue, yellow, darkorange, seagreen);", "-fx-background-color: linear-gradient(to bottom, crimson, lightcoral, skyblue, deepskyblue);"};
}
