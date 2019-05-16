/*
Scotty Fulton       4/3/19
Candybowl Semaphore and Mutual exclusion Classes
for mutual exclusion to a critical area 
(Candybowl) to eliminate RACE conditions.

Ran with BASH by:
$javac Main.java
$java Main

this will initiate jframe pop ups to tell if you, Y, want to alter
the default parameters, or N, to leave the and execute the defaults.

**Did not validate the input, as was not requested.**

You can enter: 
numer of Professors/Consumers
number of Candies/buffer size
speed multiplier.  
^ is a number that is multiplied with the dfault 100 ms 
sleep time for threads.
*/

import java.util.*;
import java.util.concurrent.Semaphore;
import javax.swing.*;
import java.io.*;
import javax.swing.JOptionPane;

public class Main{
    public static void main(String[] args) {

        int n = 10;     //# of professors
        int m = 20;     //# of candies
        int s = 100;    //speed multiplicand

        //creates a pop up prompt to alter the # of Proffs, Candies, and speed multiplier
        String answer = JOptionPane.showInputDialog("Do you want to change the settings for the run?  Y or N?: \n" 
        + "\n-------------------------------------------------- "
        + "  \nD-falt settings:\n10 Profs\n20 candies\nPlaid Speed (0 sec thread sleep)" 
        + "\n--------------------------------------------------\n ");
        
        //if you do want to change the settings then these values will be used.
        answer = answer.toUpperCase();
        if (answer.equals("Y")){
            String numProf = JOptionPane.showInputDialog("How many Professors to do your bidding? : ");
            String numCandy = JOptionPane.showInputDialog("How many pieces of candy? : ");
            String altSpeed = JOptionPane.showInputDialog("What multiplier for 100 ms thread sleepy times?  : ");
            n = Integer.parseInt(numProf);
            m = Integer.parseInt(numCandy);
            s *= Integer.parseInt(altSpeed);
        }
        
        //instantiate the buffer and semaphores
        candyBowl cb = new candyBowl(m);
        cb.speed = s;
        Semaphore mutex = new Semaphore(1);
        Semaphore full = new Semaphore(0);
        Semaphore empty = new Semaphore(m);
        Semaphore filling = new Semaphore(1);

        consumers [] consumeArr = new consumers [n];

        //instantiate n consumers
        for (int i = 0; i < n; i ++){
            String tempName = "Professor " + (i +1);
            consumers temp = new consumers(cb, tempName, mutex, empty, full, filling);
            if (s !=100){
                temp.speed = s;
            }
            consumeArr[i] = temp;
        }
        
        //print array of comsumers
        for (consumers person : consumeArr) {
            System.out.println(person.name + " showed up");
        }

        //instantiate the TA
        producerTA candyTA = new producerTA(cb, "Sven", mutex, empty, full, filling);
        if (s !=10){
            candyTA.speed = s;
        }
        System.out.println("TA : " + candyTA.name + " is sleeping");

        //The start section for the Producer and Consumers
        //******************************************************** */
            candyTA.start();
            // candyTA.join();       
            for (int i = 0; i < n; i++) {
                try {
                    consumeArr[i].start();
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    System.out.println("Blarr");
                }
            }
        //********************************************************
    }
}