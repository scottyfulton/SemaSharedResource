/*
Scotty Fulton       4/3/19
class for random candies to fill bowl with.
*/

import java.util.Random;

public class candy {
    enum color {Red, Blue, Green, Pink, Orange, Purple, White, Black, Yellow};
    color [] values = color.values();
    color candyC;
    int num = candy.color.values().length;

    //returns the color based on the random int fed
    public color getColor(int rand) {
        return values[rand];

    }
    
    //got random number to feed the enum colors for candy
    public int getRandom(){
        int randInt;
        randInt = (int)(System.currentTimeMillis()%num);
        return randInt;
    }

    //instandiated the randomly colored candy
    candy (){
        //get random num
        int colorint = getRandom();
        //get color from rand num
        candyC = getColor(colorint);
    }


}