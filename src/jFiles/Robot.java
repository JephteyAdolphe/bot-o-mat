package jFiles;

import java.util.ArrayList;
import java.util.Random;

public class Robot {

    // WORKING ON PROPERLY IMPLEMENTING THIS CLASS

    private ArrayList<Integer> toDo = new ArrayList<>();

    public Robot() {
        // Generates the initial 5 random tasks

        ArrayList<Integer> seen = new ArrayList<>();
        Random generatedTask = new Random();

        while(toDo.size() != 5) {
            int task = generatedTask.nextInt(9);
            if(!seen.contains(task)) {
                seen.add(task);
                toDo.add(task);
            }
        }
        seen.clear();
    }

    public ArrayList<Integer> getTasks() {
        return toDo;
    }
}
