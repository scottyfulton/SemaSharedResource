/*
Scotty Fulton       4/3/19
producer = TA
*/

import java.util.concurrent.Semaphore;

public class producerTA extends Thread{
    //attributes
    Semaphore mutex, empty, full, filling;
    public boolean stop = false;
    String name;
    candyBowl bowl_;
    int value;
    int speed = 100;

    //contructor
    producerTA (candyBowl b, String n, Semaphore m, Semaphore e, Semaphore f, Semaphore fl){ //, int val
        bowl_ = b;
        name = n;
        mutex = m;
        empty = e;
        full = f;
        filling = fl;
    }

    //for the producer.start() methods in main.java
    public void run(){
        try{
            while(!stop){
                Thread.sleep(speed);
                empty.acquire();
                mutex.acquire();
                //crit section
                //if bowl is empty, fill it entirely
                if (bowl_.count == 0){
                    System.out.println(name + ", wakes up and fills the bowl.");
                    FillIt(bowl_);
                }
                mutex.release();
                full.release();
            }
        }catch(InterruptedException e){
            System.out.println("ProducerTA: MOAR candy!");
        }
    }

    public producerTA(producerTA p){
        name = p.name;
    }
    public producerTA (String name){
        this.name = name;
    }

    //call to fill entire bowl from candybowl.java
    public void FillIt(candyBowl b){
        b.fillBowl();
    }

}