package peaksoft;

import lombok.extern.slf4j.Slf4j;
import peaksoft.taskManager.Priority;
import peaksoft.taskManager.Task;
import peaksoft.taskManager.TaskStatus;
import peaksoft.taskManager.TaskType;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Slf4j

public class FileManager {
    public FileManager() {
    }

    public List<Task> taskLaden(){
       List<Task> tasks = new ArrayList<Task>();
        try(BufferedReader taskReader=new BufferedReader(new FileReader("todo.txt"))){
            String line;
            while((line=taskReader.readLine())!=null){
               String[]teile=line.split("\\|");
               Task task=new Task(
                       teile[0],
                       TaskType.valueOf(teile[1]),
                       teile[2],
                       Priority.valueOf(teile[3]),
                       TaskStatus.valueOf(teile[4]),
                       LocalDate.parse(teile[5])
               );
               tasks.add(task);

            }
        }
        catch(
                IOException e){
           log.error("Fehler beim Laden der Task aufgetreten");
        }
       return tasks;
   }
    public void taskSpeichern(List<Task> tasks){
        try (BufferedWriter taskwriter=new BufferedWriter(new FileWriter("todo.txt"))){
           for(Task task:tasks){
               taskwriter.write(
                       task.getTaskName()+"|"+
                       task.getTaskType()+"|"+
                               task.getTaskDescription()+"|"+
                               task.getPriority()+"|"+
                               task.getStatus()+"|"+
                               task.getFalligkeit()
               );
               taskwriter.newLine();
           }
        }
        catch(IOException e){
            log.error("Fehler beim Speichern aufgetreten");
        }
        log.info("Task wurde erfolgreich gespeichert");
    }

}
