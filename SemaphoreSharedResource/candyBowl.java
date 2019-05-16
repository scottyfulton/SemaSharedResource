/*
Scotty Fulton       4/3/19
buffer == candybowl, with custom colored candies
*/

public class candyBowl extends Thread{
    candy [] bowl;
    int count = 0;
    int in = 0;
    int out = 0;
    int size;
    int speed = 100;

    //instantiate buffer
    candyBowl(int size){ 
        this.size = size;
        bowl = new candy [size];
    }

    /*This is a little trickier, since the entire bowl
    has to be filled before the consumers can consume, 
    and before they can interrupt the producing.
    */
    public void fillBowl(){
        try{
            //for the size of the bowl, fill it
            for (int i = 0; i < size; i++) {      
                candy newCandy;
                newCandy = new candy();
                bowl[in] = newCandy;
                System.out.println("Put " + bowl[i].candyC +" piece in.");
                ++count;
                in = (in+1) % size;
                //used to create a random number for random colow
                //updated for the Synchronized implementation
                Thread.sleep(3);
                Thread.sleep(speed);
            }
        }catch(InterruptedException e){
            System.out.println("bowl Not done yet!");
        }
    }

    // let consumers get one at a time, returns the color of the piece
    public candy getPiece(){
        candy ret_color;
        ret_color = bowl[out];
        --count;
        out = (out+1) % size;
        return (ret_color);
    }
    
    //created to print the contents of the bowl.
    public void examineBowl(){
        for (int i = 0; i < size; i++) {
            System.out.println( bowl[i].candyC);
        }
    }
}