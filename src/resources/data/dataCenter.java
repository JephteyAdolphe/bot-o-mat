package resources.data;

import java.io.*;
import java.util.*;

// Text file data center (Reading and Writing data)

public class dataCenter {
    private File file = new File("src/resources/data/mockDatabase.txt");
    private File taskFile = new File("src/resources/data/tasks.txt");

    // Adds the created robot (and tasks) to the appropriate text files

    public void write(String name, String type, int completedTasks, ArrayList<Integer> toDo) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(this.file, true));
        PrintWriter pwTasks = new PrintWriter(new FileWriter(this.taskFile, true));

        String full = name + ", " + type + " - " + completedTasks;

        pw.println(full);
        pwTasks.println(full + ":" + toDo.get(0) + " " + toDo.get(1) + " " + toDo.get(2) + " " + toDo.get(3) + " " + toDo.get(4));
        pw.close();
        pwTasks.close();
    }

    // Scans through, parses, and returns the full details of the selected robot

    public String[] getDetails(String selected) throws FileNotFoundException {
        Scanner scan = new Scanner(taskFile);
        String[] halves;
        String[] details;

        while (scan.hasNextLine()) {
            String robot_line = scan.nextLine();

            if (robot_line.contains(selected)) {
                halves = robot_line.split(":");
                if(halves.length > 1) {
                    details = halves[1].split(" ");
                    return details;
                }
            }
        }
        scan.close();
        return null;
    }

    // Updates all data connected to the selected robot

    private double updateRobot(String robotToUpdate) throws IOException {

        ArrayList<String> robotDetails = new ArrayList<>();
        ArrayList<String> robots = new ArrayList<>();
        Scanner scan = new Scanner(taskFile);
        Scanner scan2 = new Scanner(file);
        double eta = 0;

        while (scan.hasNextLine()) {
            robotDetails.add(scan.nextLine());
            robots.add(scan2.nextLine());
        }
        scan.close();
        scan2.close();
        clear();

        PrintWriter pw = new PrintWriter(new FileWriter(file, true));
        PrintWriter pwTasks = new PrintWriter(new FileWriter(taskFile, true));

        for(int i = 0; i < robots.size(); i++) {
            if(!robots.get(i).contains(robotToUpdate)) {
                pw.println(robots.get(i));
            } else {
                String[] parsed = robots.get(i).split(" - ");
                int completed = Integer.parseInt(parsed[1]);
                String updated = parsed[0] + " - " + (completed += 5);
                pw.println(updated);
            }
        }

        for(int i = 0; i < robotDetails.size(); i++) {
            if(!robotDetails.get(i).contains(robotToUpdate)) {
                pwTasks.println(robotDetails.get(i));
            } else {
                String[] parsed = robots.get(i).split(" - ");
                String[] halves = robotDetails.get(i).split(":");
                String[] tasks = halves[1].trim().split(" ");
                eta = getTime(tasks);
                int completed = Integer.parseInt(parsed[1]);
                String updated = parsed[0] + " - " + (completed += 5);
                pwTasks.println(updated + ":");
            }
        }
        pw.close();
        pwTasks.close();
        sortLeaderboard();
        return eta;
    }

    // Sorts robots in descending order based on how many tasks it completed

    private void sortLeaderboard() throws IOException {
        ArrayList<String> robots = new ArrayList<>();
        ArrayList<Integer> numOfTasksCompleted = new ArrayList<>();
        ArrayList<String> sortedRobots = new ArrayList<>();
        ArrayList<String> seen = new ArrayList<>();
        Scanner scan = new Scanner(file);


        while (scan.hasNextLine()) {
            String cur = scan.nextLine();
            String[] parsed = cur.split(" - ");
            numOfTasksCompleted.add(Integer.parseInt(parsed[1]));
            robots.add(cur);
        }
        Collections.sort(numOfTasksCompleted);

        int counter = numOfTasksCompleted.size() - 1;
        while(sortedRobots.size() != robots.size()) {
            String task = Integer.toString(numOfTasksCompleted.get(counter));

            for(int i = 0; i < robots.size(); i++) {
                if(robots.get(i).contains(task) && !seen.contains(robots.get(i))) {
                    sortedRobots.add(robots.get(i));
                    seen.add(robots.get(i));
                    break;
                }
            }
            counter -= 1;
        }

        numOfTasksCompleted.clear();
        seen.clear();
        robots.clear();
        scan.close();
        clearLeaderboard();

        PrintWriter pw = new PrintWriter(new FileWriter(file, true));

        for(int i = 0; i < sortedRobots.size(); i++) {
            pw.println(sortedRobots.get(i));
        }
        sortedRobots.clear();
        pw.close();
    }

    // Checks if the robot already exists

    public boolean exists(String robot) throws FileNotFoundException {
        Scanner scan = new Scanner(file);

        while (scan.hasNextLine()) {
            String robot_line = scan.nextLine();

            if (robot_line.contains(robot) && robot.length() == robot_line.length()) {
                return true;
            }
        }

        scan.close();
        return false;
    }

    public double completeTasks(String selected) throws IOException {
        return updateRobot(selected);

    }

    public void generateTasks(String selected) throws IOException {
        Scanner scan = new Scanner(taskFile);
        ArrayList<Integer> toDo = new ArrayList<>();
        ArrayList<Integer> seen = new ArrayList<>();
        ArrayList<String> robotDetails = new ArrayList<>();
        Random generatedTask = new Random();

        while(toDo.size() != 5) {   // Generating 5 random tasks
            int task = generatedTask.nextInt(9);
            if(!seen.contains(task)) {
                seen.add(task);
                toDo.add(task);
            }
        }

        while (scan.hasNextLine()) {
            robotDetails.add(scan.nextLine());
        }

        scan.close();
        clearTasks();

        PrintWriter pwTasks = new PrintWriter(new FileWriter(taskFile, true));

        for(int i = 0; i < robotDetails.size(); i++) {
            if(!robotDetails.get(i).contains(selected)) {
                pwTasks.println(robotDetails.get(i));
            } else {
                String updated = selected + ":";

                for(int j = 0; j < toDo.size(); j++) {
                    updated += toDo.get(j).toString() + " ";
                }
                pwTasks.println(updated.trim());
            }
        }
        pwTasks.close();
    }

    // Calculates total time needed to complete the tasks

    private double getTime(String[] tasks) {
        double totalETA = 0;

        HashMap<Integer, Integer> times = new HashMap();
        times.put(0, 1000);
        times.put(1, 3000);
        times.put(2, 10000);
        times.put(3, 4000);
        times.put(4, 7000);
        times.put(5, 20000);
        times.put(6, 18000);
        times.put(7, 14500);
        times.put(8, 8000);
        times.put(9, 20000);

        for(int i = 0; i < 5; i++) {
            totalETA += times.get(Integer.parseInt(tasks[i]));
        }
        return totalETA / 1000;
    }


    // Clears all text file data after app is closed

    public void clear() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(this.file);
        PrintWriter taskWriter = new PrintWriter(this.taskFile);
        PrintWriter tempWriter = new PrintWriter(new File("src/resources/data/myTempFile.txt"));

        writer.close();
        taskWriter.close();
        tempWriter.close();
    }

    private void clearTasks() throws FileNotFoundException {
        PrintWriter taskWriter = new PrintWriter(this.taskFile);
        taskWriter.close();
    }

    private void clearLeaderboard() throws FileNotFoundException {
        PrintWriter taskWriter = new PrintWriter(this.file);
        taskWriter.close();
    }
}
