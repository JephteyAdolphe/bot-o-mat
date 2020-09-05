package resources.data;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// Text file data center (Reading and Writing data)

public class dataCenter {
    private File file = new File("src/resources/data/mockDatabase.txt");
    private File taskFile = new File("src/resources/data/tasks.txt");

    public boolean read(String verification) throws FileNotFoundException {

        try {
            Scanner scan = new Scanner(this.file);

            while (scan.hasNextLine()) {
                String x = scan.nextLine();
                if (x.equals(verification)) {
                    return true;
                }
            }

        } catch (Exception e) {

            System.out.println("No Available Data");
            return false;
        } return false;
    }

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

                //return details;
            }
        }

        scan.close();
        return null;
    }

    public String getFullDetails(String selected) throws FileNotFoundException {
        Scanner scan = new Scanner(taskFile);

        while (scan.hasNextLine()) {
            String robot_line = scan.nextLine();

            if (robot_line.contains(selected)) {
                return robot_line;
            }
        }

        scan.close();
        return null;
    }

    // Deletes all data connected to the selected robot

    private void updateRobot(String robotToUpdate) throws IOException {
        //File inputFile = new File(String.valueOf(taskFile));
        File tempFile = new File("src/resources/data/myTempFile.txt");

        ArrayList<String> robotDetails = new ArrayList<>();
        ArrayList<String> robots = new ArrayList<>();
        Scanner scan = new Scanner(taskFile);
        Scanner scan2 = new Scanner(file);

        while (scan.hasNextLine()) {
            robotDetails.add(scan.nextLine());
            robots.add(scan2.nextLine());
        }
        while (scan2.hasNextLine()) {
           // robots.add(scan2.nextLine());
        }
        scan.close();
        scan2.close();
        clear();

        PrintWriter pw = new PrintWriter(new FileWriter(file, true));
        PrintWriter pwTasks = new PrintWriter(new FileWriter(taskFile, true));

        for(int i = 0; i < robotDetails.size(); i++) {
            if(!robotDetails.get(i).contains(robotToUpdate)) {
                pw.println(robots.get(i));
                pwTasks.println(robotDetails.get(i));
            } else {
                String[] parsed = robots.get(i).split(" - ");
                int completed = Integer.parseInt(parsed[1]);
                String updated = parsed[0] + " - " + (completed += 5);
                pw.println(updated);
                pwTasks.println(updated + ":");
                //pwTasks.println();    update completed tasks in both files
                // and add timer (sleep)
            }
        }
        pw.close();
        pwTasks.close();

        /*BufferedReader robot_reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter robot_writer = new BufferedWriter(new FileWriter(tempFile));


        String currentLine;

        while ((currentLine = robot_reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if (trimmedLine.equals(robotToDelete)) continue;
            robot_writer.write(currentLine + System.getProperty("line.separator"));
        }
        robot_writer.close();
        robot_reader.close();

        //delete file
        boolean del = inputFile.delete();
        //boolean del2 = this.taskFile.delete();

        //rename file
        boolean newData = tempFile.renameTo(new File("src/resources/data/tasks.txt"));

         */
    }

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

    public void completeTasks(String selected) throws IOException {
        updateRobot(selected);
       /* Scanner scan = new Scanner(taskFile);

        while (scan.hasNextLine()) {
            String robot_line = scan.nextLine();

            if (robot_line.contains(selected)) {
                halves = robot_line.split(", ");
                details = halves[1].split(" ");
                return details;
            }
        }

        scan.close();
        return null;*/
    }

    public void generateTasks(String selected) throws IOException {
        Scanner scan = new Scanner(taskFile);
        ArrayList<Integer> toDo = new ArrayList<>();
        ArrayList<Integer> seen = new ArrayList<>();
        ArrayList<String> robotDetails = new ArrayList<>();
        ArrayList<String> robots = new ArrayList<>();
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
}
