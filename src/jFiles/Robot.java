package jFiles;

import java.util.ArrayList;
import java.util.Random;

public class Robot {

    // WORKING ON PROPERLY IMPLEMENTING THIS CLASS

    private String name;
    private String type;
    private ArrayList<Integer> toDo = new ArrayList<>();
    private ArrayList<Integer> seen = new ArrayList<>();
    //private HashMap<Integer, String> taskMap = new HashMap();
    private Random generatedTask = new Random();
    private int totalETA = 0;

    public Robot(String object) {
        /*taskMap.put(0, "do the dishes");
        taskMap.put(1, "sweep the house");
        taskMap.put(2, "do the laundry");
        taskMap.put(3, "take out the recycling");
        taskMap.put(4, "make a sammich");
        taskMap.put(5, "mow the lawn");
        taskMap.put(6, "rake the leaves");
        taskMap.put(7, "give the dog a bath");
        taskMap.put(8, "bake some cookies");
        taskMap.put(9, "wash the car");*/

        while(toDo.size() != 5) {   // Generating 5 random tasks
            int task = generatedTask.nextInt(9);
            if(!seen.contains(task)) {
                seen.add(task);
                toDo.add(task);
            }
        }
        seen.clear();

        String[] robot = object.split(", ");
        name = robot[0];
        type = robot[1];
    }

    public void completeTasks() {
        for(int i = 0; i < toDo.size(); i++) {
            switchCases(toDo.get(i));
        }
        toDo.clear();
        System.out.println("Total ETA is: " + totalETA + " milliseconds... or: " + (totalETA/1000) + " seconds");
    }

    public ArrayList<Integer> getTasks() {
        return toDo;
    }

    private void switchCases(int task) {
        switch(task) {
            case 0:
                doDishes();
                break;
            case 1:
                sweepHouse();
                break;
            case 2:
                doLaundry();
                break;
            case 3:
                disposeRecycling();
                break;
            case 4:
                makeSammich();
                break;
            case 5:
                mowLawn();
                break;
            case 6:
                rakeLeaves();
                break;
            case 7:
                batheDog();
                break;
            case 8:
                bakeCookies();
                break;
            case 9:
                washCar();
                break;
        }
    }

    private void doDishes() {
        System.out.print("dishes");
        System.out.println("1000 milliseconds");
        totalETA += 1000;
    }

    private void sweepHouse() {
        System.out.print("houese");
        System.out.println("3000 milliseconds");
        totalETA += 3000;
    }

    private void doLaundry() {
        System.out.print("laundry");
        System.out.println("10000 milliseconds");
        totalETA += 10000;
    }

    private void disposeRecycling() {
        System.out.print("recycle");
        System.out.println("4000 milliseconds");
        totalETA += 4000;
    }

    private void makeSammich() {
        System.out.print("sammich");
        System.out.println("7000 milliseconds");
        totalETA += 7000;
    }

    private void mowLawn() {
        System.out.print("lawn");
        System.out.println("20000 milliseconds");
        totalETA += 20000;
    }

    private void rakeLeaves() {
        System.out.print("leaves");
        System.out.println("18000 milliseconds");
        totalETA += 18000;
    }

    private void batheDog() {
        System.out.print("dog");
        System.out.println("14500 milliseconds");
        totalETA += 14500;
    }

    private void bakeCookies() {
        System.out.print("cookies");
        System.out.println("8000 milliseconds");
        totalETA += 8000;
    }

    private void washCar() {
        System.out.print("car");
        System.out.println("20000 milliseconds");
        totalETA += 20000;
    }
}
