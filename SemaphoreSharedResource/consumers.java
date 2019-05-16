/*
Scotty Fulton       4/3/19
Consumer == Professors
*/

import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

public class consumers extends Thread{
    //attributes
    candyBowl _bowl;
    String name;
    Semaphore mutex, empty, full, filling;
    public boolean stop = false;
    int speed = 100;

    //paramaterized constructor
    consumers (candyBowl b, String n, Semaphore m, Semaphore e, Semaphore f, Semaphore fl){
        _bowl = b;
        name = n;
        mutex = m;
        empty = e;
        full = f;
        filling = fl;
    }

    public consumers(consumers c){
        name = c.name;
    }

    public consumers (String name){
        this.name = name;
    }

    //for the consumer.start() methods in main.java
    public void run(){
        try{
            int value;
            candy ret_piece;
            Thread.sleep(speed);
            while(!stop) {
                full.acquire();
                mutex.acquire();
                //crit section
                ret_piece = _bowl.getPiece();
                System.out.println(name + ", got a candy colored " + ret_piece.candyC);
                System.out.println(_bowl.count + " pieces in the bowl");
                mutex.release();
                empty.release();
                Thread.sleep(speed);
            }
        }catch(InterruptedException e){
            System.out.println("Consumer: Not done yet!");
        }
    }
}