package peaksoft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import peaksoft.taskManager.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private static final Logger log = LoggerFactory.getLogger(UserInterface.class);
    private TaskManager taskManager;
   private Scanner scanner;

   public UserInterface() {
       taskManager = new TaskManager();
       scanner = new Scanner(System.in);
   }
   public void start() {
       boolean gehtLos=true;
       while(gehtLos) {
           System.out.println("|n-----TO DO LISTE-----");
           System.out.println("1. Aufgaben anzeigen");
           System.out.println("2. Neue Aufgaben erstellen");
           System.out.println("3. Aufgaben suchen");
           System.out.println("4. Aufgabe aktualisieren");
           System.out.println("5. Aufgabe loschen");
           System.out.println("6.Statistik anzeigen");
           System.out.println("0. Beenden");
           int wahlen=scanner.nextInt();
           scanner.nextLine();
           switch (wahlen) {
               case 1->aufgabenZeigen();
               case 2->neueAufgabenErstellen();
               case 3->aufgabenSuchen();
               case 4->aufgabenAktualisieren();
               case 5->deleteTask();
               case 6->statistikAnzeigen();
               case 0->gehtLos=false;
               default -> System.out.println("Ungultige Eingabe!");
           }
       }
   }

private void aufgabenZeigen(){
    boolean gehtLos=true;
    while (gehtLos){

    log.info("Userin hat alle Tasks abgerufen");
    System.out.println("-----Aufgaben anzeigen--------");
    System.out.println("1. Alle Aufgaben anzeigen" );
    System.out.println("2. Offene Aufgaben anzeigen" );
    System.out.println("3 Aufgaben, die ich daran arbeite" );
    System.out.println( "4 Überfällige Aufgaben anzeigen" );
    System.out.println( "5 Aufgaben dieser  Woche anzeigen" );
    System.out.println("0 Beenden");
    int wahlen=scanner.nextInt();
    scanner.nextLine();
    switch (wahlen) {
        case 1->zeigeTasks(taskManager.getTasks());
        case 2->zeigeTasks(taskManager.getOffeneTasks());
        case 3->zeigeTasks(taskManager.getTasksIN_Bearbeitung());
        case 4->zeigeTasks(taskManager.getUberfalligeTasks() );
        case 5->zeigeTasks(taskManager.getTasksOfWeek() );
        case 0->gehtLos=false;
        default -> System.out.println("Ungultige Eingabe!");
    }
       }
}
private void zeigeTasks(List<Task>tasks){
       if (tasks.isEmpty()){
           System.out.println("Keine Aufgaben vorhanden, geniss es!!!");
       }
       else{
           for (Task task : tasks) {
               System.out.println(task);
           }
       }
}
private void neueAufgabenErstellen(){
       while (true){
try {
           log.info("Userin mochte neue Aufgabe erstellen");
           System.out.println("Name von Task: ");
           String taskName = scanner.nextLine();
           System.out.println("Type von Task wahlen:  1-STUDIUM, 2-HAUSHALT, 3-PRIVAT, 4->SONSTIGES ");
           int taskType = scanner.nextInt();
    scanner.nextLine();
           TaskType type = switch (taskType) {
               case 1 -> TaskType.STUDIUM;
               case 2 -> TaskType.HAUSHALT;
               case 3 -> TaskType.PRIVAT;
               case 4-> TaskType.SONSTIGES;
               default -> throw new IllegalArgumentException("Ungultige Tasktyp");

           };

           System.out.println("Beschreibung von Task: ");
           String taskDescription = scanner.nextLine();

           System.out.println("Priority von Task:  1-HOCH, 2-MITTEL, 3-NIEDRIG");
           int priority = scanner.nextInt();
    scanner.nextLine();
           Priority p = switch (priority) {
               case 1 -> Priority.HOCH;
               case 2 -> Priority.MITTEL;
               case 3 -> Priority.NIEDRIG;
               default -> throw new IllegalArgumentException("Ungultige Priority");

           };

           System.out.println("Status von Task:  1-OFFEN, 2-IN BEARBEITUNG, 3-FERTIG");
           int status = scanner.nextInt();
    scanner.nextLine();
           TaskStatus s = switch (status) {
               case 1 -> TaskStatus.OFFEN;
               case 2 -> TaskStatus.IN_BEARBEITUNG;
               case 3 -> TaskStatus.FERTIG;
               default -> throw new IllegalArgumentException("Ungultige Status");
           };

           System.out.println("Falligkeitsdatum (YYYY-MM-DD oder YYYY-M-D): ");
           String falligkeitsdatum = scanner.next();
    DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-M-d");
           LocalDate datum = LocalDate.parse(falligkeitsdatum, formatter);

           Task task = new Task(taskName, type, taskDescription, p, s, datum);
           taskManager.addTask(task);
           System.out.println("Aufgabe erfolgreich erstellt");
           break;
       }
       catch (Exception e){
    log.error(e.getMessage());
           System.out.println("Ungulge Eingabe!");
}
       }

   }
private void aufgabenSuchen() {
    boolean gehtlos = true;
    while (gehtlos){
        log.info("Userin mochte aufgabenSuchen");
        System.out.println("-------Aufgaben suchen--------");
        System.out.println("1 Suchen nach Name");
        System.out.println("2 Suchen nach Priority");
        System.out.println("3 Suchen nach Datum");
        System.out.println("4 Suchen nach Type");
        System.out.println("5 Suchen nach Status");
        System.out.println("0 Beenden");

        int wahlen=scanner.nextInt();
        switch (wahlen) {
            case 1->suchenName();
            case 2->suchenPriority();
            case 5-> sucheByStatus();
            case 4->suchenType();
            case 3->sucheByDate();
            case 0-> gehtlos=false;
            default -> System.out.println("Ungultige Eingabe!");
        }
    }
}
private void suchenName(){
    System.out.println("Geben Sie Name ein");
    String name = scanner.next();
    Task task=taskManager.getTaskByName(name);
    System.out.println(task);
}
private void suchenPriority(){
    System.out.println("------Priority Suchen------ ");
    System.out.println("1. Alle Aufgaben mit Priority");
    System.out.println("2. Offene Aufgaben mit Priority");
    System.out.println("3. Aufgaben nach Type und Priority");
    int wahlen=scanner.nextInt();
    switch (wahlen){
        case 1->suchallePriority();
        case 2-> offeneTasksMitPriority();
        case 3->typeAndPriority();
        default -> System.out.println("Ungtige Eingabe!");


    }
}
private Priority lesePriority(){
    System.out.println("----Priority----");
    System.out.println("1 HOCH");
    System.out.println("2 MITTEL");
    System.out.println("3 NIEDRIG");
    int wahlen=scanner.nextInt();
   return switch (wahlen){
        case 1->Priority.HOCH;
        case 2->Priority.MITTEL;
        default -> Priority.NIEDRIG;
    };
}
private TaskType leseType(){
    System.out.println("-----Type-----");
    System.out.println("1 STUDIUM");
    System.out.println("2 HAUSHALT");
    System.out.println("3 PRIVAT");
    System.out.println("4 SONSTIGES");
    int wahlen=scanner.nextInt();
    return switch (wahlen){
        case 1->TaskType.STUDIUM;
        case 2->TaskType.HAUSHALT;
        case 3 -> TaskType.PRIVAT;
        default -> TaskType.SONSTIGES;

    };
}
private TaskStatus leseStatus(){
    System.out.println("-----Status----");
       System.out.println("1 OFFEN");
       System.out.println("2 IN BEARBEITUNG");
       System.out.println("3 FERTIG");
       int wahlen=scanner.nextInt();
      return switch (wahlen){
           case 1->TaskStatus.OFFEN;
           case 2->TaskStatus.IN_BEARBEITUNG;
           default -> TaskStatus.FERTIG;
       };

}
private void suchallePriority(){
       Priority lesePriority=lesePriority();
       List<Task>tasks= taskManager.getTasksByPriority(lesePriority);
       for(Task task:tasks){
           System.out.println(task);
       }
}
private void offeneTasksMitPriority(){
       Priority lesePriority=lesePriority();
       List<Task>tasks= taskManager.getOffeneTasksByPriority(lesePriority);
       if(tasks.isEmpty()){
           System.out.println("Keine Aufgaben vorhanden, geniss es!!!");
           return;
       }
       for(Task task:tasks){
           System.out.println(task);
       }
}
private void typeAndPriority(){
       Priority priority=lesePriority();
       TaskType taskType=leseType();
       List<Task>tasks=taskManager.getTasksbyTypeAndPriority(taskType,priority);
       if(tasks.isEmpty()){
           System.out.println("Keine Aufgaben vorhanden");
       }
       for(Task task:tasks){
           System.out.println(task);
       }
}
private void sucheByStatus(){
       TaskStatus status=leseStatus();
       List<Task>tasks=taskManager.getByStatus(status);
       if(tasks.isEmpty()){
           System.out.println("Keine Aufgaben vorhanden");
       }
       for (Task task:tasks){
           System.out.println(task);
       }

}
private void suchenType(){
            System.out.println("------Type Suchen------ ");
            System.out.println("1. Alle Aufgaben mit Type");
            System.out.println("2. Aufgaben nach Type und Status");
            int wahlen=scanner.nextInt();
            switch (wahlen){
                case 1->sucheByType();
                case 2-> suchByTypeAndStatus();
                default -> System.out.println("Ungtige Eingabe!");
            }

    }
private void sucheByType(){
       TaskType taskType=leseType();
       List<Task>tasks=taskManager.getTasksByType(taskType);
       if(tasks.isEmpty()){
           System.out.println("Keine Aufgaben vorhanden");
       }
       for (Task task:tasks){
           System.out.println(task);
       }
}
private void suchByTypeAndStatus(){
       TaskType taskType=leseType();
       TaskStatus status=leseStatus();
       List<Task>tasks=taskManager.getTasksByTypeAndStatus(taskType,status);
       if(tasks.isEmpty()){
           System.out.println("Keine Aufgaben vorhanden");
       }
       for (Task task:tasks){
           System.out.println(task);
       }
}
private void sucheByDate(){
    System.out.println("-----Datum suchen-----");
    System.out.println("1 Bestimmtes Datum");
    System.out.println("2 Zeitraum");
    int wahlen=scanner.nextInt();
    switch (wahlen){
        case 1->sucheBestimDatum();
        case 2->zeitraumSuchen();
    }
}
private void sucheBestimDatum(){
    System.out.println("Welches DAtum suchen Sie?");
    System.out.println("Geben Sie Datum ein YYYY-MM-DD");
    LocalDate localDate=LocalDate.parse(scanner.next());
    List<Task>tasks=taskManager.getTasksByDate(localDate);
    if(tasks.isEmpty()){
        System.out.println("Keine Aufgaben vorhanden");
    }
    for (Task task:tasks){
        System.out.println(task);
    }
}
private void zeitraumSuchen(){
    System.out.println("Von Datum YYYY-MM-DD");
    LocalDate from=LocalDate.parse(scanner.next());
    System.out.println("bis DAtum YYYY-MM-DD");
    LocalDate to=LocalDate.parse(scanner.next());
    List<Task>tasks=taskManager.getTasksByDateFromTo(from,to);
    for(Task task:tasks){
        System.out.println(task);
    }
}
private void aufgabenAktualisieren(){
    boolean gehtlos = true;
    while (gehtlos){
        log.info("Userin mochte Aufgabe aktualiesieren");
        System.out.println("-------Aufgaben aktualisieren--------");
        System.out.println("1 Status aktualisieren");
        System.out.println("2 Priority aktualisieren");
        System.out.println("3  Datum aktualisieren");
        System.out.println("4 Als erledig markieren");
        System.out.println("0 Beenden");

        int wahlen=scanner.nextInt();
        scanner.nextLine();
        switch (wahlen) {
            case 1->statusAktualisieren();
            case 2->priorityAktualisieren();
            case 3->datumAktualisieren();
            case 4->erledigMarkieren();
            case 0-> gehtlos=false;
            default -> System.out.println("Ungultige Eingabe!");
        }
}
}
private void statusAktualisieren(){
    System.out.println("Name von Task eingeben");
    String name= scanner.nextLine();
    Task task=taskManager.getTaskByName(name);
    if(task==null){
        System.out.println("Keine Aufgaben vorhanden");
        return;
    }
    System.out.println("Status von Task:  1-OFFEN, 2-IN BEARBEITUNG, 3-FERTIG");
    int newstatus = scanner.nextInt();
    scanner.nextLine();
    TaskStatus s = switch (newstatus) {
        case 1 -> TaskStatus.OFFEN;
        case 2 -> TaskStatus.IN_BEARBEITUNG;
        case 3 -> TaskStatus.FERTIG;
        default -> throw new IllegalArgumentException("Ungultige Status");
    };
    taskManager.updateStatus(task,s);
}
private void priorityAktualisieren(){
    System.out.println("Name von Task eingeben");
    String name= scanner.nextLine();
    Task task=taskManager.getTaskByName(name);
    if(task==null){
        System.out.println("Keine Aufgaben vorhanden");
        return;
    }
    System.out.println("Priority von Task:  1-HOCH, 2-MITTEL, 3-NIEDRIG");
    int newp = scanner.nextInt();
    scanner.nextLine();
    Priority p = switch (newp) {
        case 1 -> Priority.HOCH;
        case 2 -> Priority.MITTEL;
        case 3 -> Priority.NIEDRIG;
        default -> throw new IllegalArgumentException("Ungultige Status");
    };
    taskManager.updatePriority(task,p);
}
private void datumAktualisieren(){
        System.out.println("Name von Task eingeben");
        String name= scanner.nextLine();
        Task task=taskManager.getTaskByName(name);
        if(task==null){
            System.out.println("Keine Aufgaben vorhanden");
            return;
        }
    System.out.println("Falligkeitsdatum (YYYY-MM-DD oder YYYY-M-D): ");
    String falligkeitsdatum = scanner.next();
    DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-M-d");
    LocalDate datum = LocalDate.parse(falligkeitsdatum, formatter);
        taskManager.updateDate(task,datum);
    }
    private void erledigMarkieren(){
       System.out.println("Name von Task eingeben");
       String name= scanner.nextLine();
       taskManager.taskAlsErledingtMarkieren(name);
    }
private void deleteTask(){
       System.out.println("Name von Task eingeben");
       String name= scanner.nextLine();
       taskManager.deleteTaskByName(name);
    System.out.println("Falls vorhanden wurde Task geloscht");

}
private void statistikAnzeigen(){
       boolean los=true;
       while (los){
           log.info("User mcohte statistik sehen");
           System.out.println("------Statistik-------");
           System.out.println("1 Anzahl offene Aufgaben");
           System.out.println(" 2 Anzahl erledigte Aufgabe");
           System.out.println("3 Aufgaben nach Falligkeit sortieren");
           System.out.println("4 Aufgaben nach Status sortieren");
           System.out.println("5 Aufgaben nach Priority sortieren");
           System.out.println("6 Erledigte Aufgabe");
           System.out.println("7 Aufgaben nach Datum+Priority sortieren");
           System.out.println("0 Zuruck");
           int wahlen=scanner.nextInt();
           scanner.nextLine();
           switch (wahlen) {
               case 1-> System.out.println("Anzahl offener Aufgaben: "+taskManager.getAnzahlOffeneTasks());
               case 2-> System.out.println("Anzahl erledigte Aufgaben: "+taskManager.getAnzahlErledingteAufgabe());
               case 3->zeigeTasks(taskManager.sortierenNachFalligkeit());
               case 4->zeigeTasks(taskManager.sortierenNachStatus());
               case 5->zeigeTasks(taskManager.sortierenNachPriority());
               case 6->zeigeTasks(taskManager.erledigteAufgaben());
               case 7->zeigeTasks(taskManager.sortierenNachDAtumUndPriority());
               case 0-> los=false;
               default -> throw new IllegalArgumentException("Ungultige Eingabe");
           }
       }
}
}