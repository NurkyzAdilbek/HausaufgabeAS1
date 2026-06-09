import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import peaksoft.taskManager.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.List;

public class TestTaskManager {
    private TaskManager taskManager;
    private Task testTask;
    @BeforeEach
    public void setUp() {
        taskManager = new TaskManager();
        testTask = new Task("AS", TaskType.STUDIUM,
                "Hausaufgabe 1", Priority.HOCH, TaskStatus.IN_BEARBEITUNG,
                LocalDate.now().plusDays(1));
    }
    @AfterEach
    void tearDown(){
        try {
            new java.io.FileWriter("todo.txt",false).close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    @DisplayName("Task erfolgreich hinzufügen")
    void testAddTask() {
        taskManager.addTask(testTask);
        assertTrue(taskManager.getTasks().contains(testTask));
    }
    @Test
    @DisplayName("Null Task wirft Exception")
    void testAddTaskNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.addTask(null);
        });
    }
    @Test
    @DisplayName("Get all Tasks ")
    void testGetAllTasks() {
        taskManager.addTask(testTask);
        assertFalse(taskManager.getTasks().isEmpty());
    }
    @Test
    @DisplayName("Task wurde erfolgreich gefunden")
    void testTaskByName() {
        taskManager.addTask(testTask);
        assertNotNull(taskManager.getTaskByName("AS"));
    }
    @Test
    @DisplayName("Null Name wirft Exception")
    void testTaskByNameNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.addTask(null);
        });
    }
    @Test
    @DisplayName("Leerer Name wirft Exception")
    void testTaskByNameEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.getTaskByName("");
        });
    }
    @Test
    @DisplayName("Null Priority wirft Exception")
    void testTaskByPriorityNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.getTasksByPriority(null);
        });
    }
    @Test
    @DisplayName("Task wurde erfolgreich bei Priority gefunden")
    void testTaskByPriority() {
        taskManager.addTask(testTask);
        assertFalse(taskManager.getTasksByPriority(testTask.getPriority()).isEmpty());
    }
    @Test
    @DisplayName("Get by Date ")
    void testTaskByDate() {
        taskManager.addTask(testTask);
        assertFalse(taskManager.getTasksByDate(testTask.getFalligkeit()).isEmpty());
    }
    @Test
    @DisplayName("Null Falligkeit wirft Exception")
    void testTaskByFalligkeitNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.getTasksByDate(null);
        });
    }
    @Test
    @DisplayName("Task in diesem Zeitraum wurde erfolgreich gefunden ")
    void testTaskFromDateTo() {
        taskManager.addTask(testTask);
        assertFalse(taskManager.getTasksByDateFromTo
                (testTask.getFalligkeit(), testTask.getFalligkeit()).isEmpty());

    }

    @Test
    @DisplayName("Null Datum wirft Exception")
    void testTaskFromDateNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.getTasksByDateFromTo(null, null);
        });
    }

    @Test
    @DisplayName("Kein Task in diesem Zeitraum")
    void testTaskFromDate() {
        taskManager.addTask(testTask);
        assertTrue(taskManager.getTasksByDateFromTo(
                LocalDate.of(2023, 02, 23),
                LocalDate.of(2024, 04, 12)
        ).isEmpty());
    }

    @Test
    @DisplayName("Offene Task wurde erfolgreich gefunden")
    void testGetOffenTask() {
        Task offenTask = new Task("Einkaufen", TaskType.HAUSHALT,
                "Brot kaufen", Priority.MITTEL,
                TaskStatus.OFFEN, LocalDate.of(2026, 06, 12));
        taskManager.addTask(offenTask);
        assertTrue(taskManager.getOffeneTasks().contains(offenTask));
    }

    @Test
    @DisplayName("IN_Bearbeirung Task wurde erfolggreich gefunden")
    void testGetInBearbeirungTask() {
        Task inBearbeuntgTask = new Task("Termin beim Arzt", TaskType.PRIVAT,
                "Kompletter Check Up", Priority.HOCH, TaskStatus.IN_BEARBEITUNG, LocalDate.of(2026, 06, 12));
    }

    @Test
    @DisplayName("Type wurde erfolgreich gefunden")
    void testGetByType() {
        taskManager.addTask(testTask);
        assertTrue(taskManager.getTasksByPriority(testTask.getPriority()).contains(testTask));
    }

    @Test
    @DisplayName("Null Type wirft Exception ")
    void testGetByTypeNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.getTasksByPriority(null);
        });
    }

    @Test
    @DisplayName("Status wurde erfolgreich gefunden")
    void testGetByStatus() {
        taskManager.addTask(testTask);
        assertTrue(taskManager.getByStatus(testTask.getStatus()).contains(testTask));
    }

    @Test
    @DisplayName("Null Status wirft Exception")
    void testGetByStatusNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.getByStatus(null);
        });
    }

    @Test
    @DisplayName("Tasks Status wurde erfolgreich aktualisiert ")
    void testUpdateTaskStatus() {
        taskManager.addTask(testTask);
        taskManager.updateStatus(testTask, TaskStatus.FERTIG);
        assertEquals(TaskStatus.FERTIG, taskManager.getTaskByName("AS").getStatus());
    }

    @Test
    @DisplayName("Null Task wirft Exception")
    void testUpdateTaskNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.updateStatus(null, TaskStatus.FERTIG);
        });
    }

    @Test
    @DisplayName("Null Status wirft Exception")
    void testUpdateStatusNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.updateStatus(null, null);
        });
    }

    @Test
    @DisplayName("Null Task und Null Status werfen Exception")
    void testUpdateTaskNullNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.updateStatus(null, null);
        });

    }

    @Test
    @DisplayName("Tasks Priority wurde erfolgreich aktualisiert ")
    void testUpdateTaskPriority() {
        taskManager.addTask(testTask);
        taskManager.updatePriority(testTask, Priority.NIEDRIG);
        assertEquals(Priority.NIEDRIG, testTask.getPriority());

    }

    @Test
    @DisplayName("Null Task und Null Priority werfen Exception")
    void testUpdateTaskPriorityNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.updatePriority(null, null);
        });
    }

    @Test
    @DisplayName("Null Task wirft Exception")
    void testUpdateByTaskNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.updatePriority(null, Priority.NIEDRIG);
        });
    }

    @Test
    @DisplayName("Null Priority wirft Exception")
    void testUpdatePriorityNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.updatePriority(testTask, null);
        });
    }

    @Test
    @DisplayName("Datum wurde erfolgreich aktualiesiert")
    void testUpdateByDatum() {
        taskManager.addTask(testTask);
        taskManager.updateDate(testTask, LocalDate.of(2026, 07, 22));
        Task task = taskManager.getTaskByName("AS");
        assertEquals(LocalDate.of(2026, 07, 22), task.getFalligkeit());
    }

    @Test
    @DisplayName("Null Datum wirft Exception")
    void testUpdateByDatumNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.updateDate(testTask, null);
        });
    }

    @Test
    @DisplayName("Null Task wirft Exception")
    void testUpdateByNullTask() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.updateDate(null, LocalDate.of(2026, 07, 22));
        });
    }

    @Test
    @DisplayName("Null Datum und Null Task werfen Exception")
    void testUpdateByNullDatumNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.updateDate(null, null);
        });
    }

    @Test
    @DisplayName("Task wurde erfolgreich by Name gefunden und Status wurde aktualisiert")
    void testTaskbyNameandStatusUpdate() {
        taskManager.addTask(testTask);
        taskManager.getByNameAndUpdateStatus(testTask.getTaskName(), TaskStatus.OFFEN);
        assertEquals(TaskStatus.OFFEN, taskManager.getTaskByName("AS").getStatus());
    }

    @Test
    @DisplayName("Null Taskname wirft Exception")
    void testUpdateTasknameNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.getByNameAndUpdateStatus(null, TaskStatus.OFFEN);
        });
    }

    @Test
    @DisplayName("Null Status wirft Exception")
    void testUpdateTaskStatusNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.getByNameAndUpdateStatus("AS", null);
        });
    }

    @Test
    @DisplayName("Null Taskname und Null Status werfen Exception")
    void testUpdateNullNameandNullStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.getByNameAndUpdateStatus(null, null);
        });
    }

    @Test
    @DisplayName("Task wurde erfolgreich gelöscht")
    void testDeleteTask() {
        taskManager.addTask(testTask);
        taskManager.deleteTask(testTask);
        assertFalse(taskManager.getTasks().contains(testTask));
    }

    @Test
    @DisplayName("Null Task wirft Exception")
    void testDeleteTaskNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.deleteTask(null);
        });
    }

    @Test
    @DisplayName("Task wurde by Name gefunden und erfolgreich geloscht")
    void testDeleteTaskByTaskName() {
        taskManager.addTask(testTask);
        taskManager.deleteTaskByName("AS");
        assertNull(taskManager.getTaskByName("AS"));
    }

    @Test
    @DisplayName("Null Name wirft Exception")
    void testDeleteTaskNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.deleteTask(null);
        });
    }

    @Test
    @DisplayName("Task wurde erfolgreich by Priority geloscht")
    void testDeleteTaskByPriority() {
        taskManager.addTask(testTask);
        taskManager.deleteByPriority(testTask.getPriority());
        assertTrue(taskManager.getTasksByPriority(Priority.HOCH).isEmpty());
    }

    @Test
    @DisplayName("Null Priority wirft Exception")
    void testDeleteTaskNullPriority() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.deleteTask(null);
        });
    }

    @Test
    @DisplayName("Task wurde  by Status gefunden und erfolgreich geloscht")
    void testDeleteTaskByStatus() {
        taskManager.addTask(testTask);
        taskManager.deleteByStatus(TaskStatus.OFFEN);
        assertTrue(taskManager.getByStatus(TaskStatus.OFFEN).isEmpty());
    }

    @Test
    @DisplayName("Null Status wirft Exception")
    void testDeleteTaskNullStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.deleteTask(null);
        });
    }

    @Test
    @DisplayName("Offene Tasks wurden erfolgreich mit gewunschter Priority gefunden")
    void testGetOffeneTasksByPriority() {
        Task task = new Task("ISDA", TaskType.STUDIUM, "Hausaufgabe 2", Priority.HOCH, TaskStatus.OFFEN, LocalDate.of(2026, 06, 12));

        taskManager.addTask(task);
        assertTrue(taskManager.getOffeneTasksByPriority(Priority.HOCH).contains(task));

    }

    @Test
    @DisplayName("Null Priority wirft Exception")
    void testGetOffeneTaskNullPriority() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.getOffeneTasksByPriority(null);
        });
    }

    @Test
    @DisplayName("Get Tasks by Type and by Status")
    void testGetTasksByTypeAndStatus() {
        taskManager.addTask(testTask);
        assertTrue(taskManager.getTasksByTypeAndStatus(TaskType.STUDIUM, TaskStatus.IN_BEARBEITUNG).contains(testTask));
    }

    @Test
    @DisplayName("Null Task Type und Status werfen Exception")
    void testGetTaskNullTypeAndStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.getTasksByTypeAndStatus(null, null);
        });
    }

    @Test
    @DisplayName("Null Type wirft Exception")
    void testGetTaskNullType() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.getTasksByTypeAndStatus(null, TaskStatus.IN_BEARBEITUNG);
        });
    }

    @Test
    @DisplayName("Null Status wirft Exception")
    void testGetTaskNullStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.getTasksByTypeAndStatus(TaskType.STUDIUM, null);
        });
    }

@Test
    @DisplayName("Uberfallige Aufgaben wurden erfolgreich gefunden")
    void testUberfalligeTaks(){
    Task task=new Task("AS",TaskType.STUDIUM,
            "Hausaufgabe 1",Priority.HOCH,TaskStatus.IN_BEARBEITUNG,
            LocalDate.now().minusDays(3));
    taskManager.addTask(task);
        assertTrue(taskManager.getUberfalligeTasks().contains(task));}


@Test
    @DisplayName("Tasks fur diese Woche wurden erfolgreich gefunden")
    void testTaskDieserWoche(){
        taskManager.addTask(testTask);
        assertTrue(taskManager.getTasksOfWeek().contains(testTask));
}
@Test
    @DisplayName("Tasks by Type und by Priority wurden erfolgreich gefunden")
    void testGetTasksByTypeAndByPriority(){
        taskManager.addTask(testTask);
        assertTrue(taskManager.getTasksbyTypeAndPriority(TaskType.STUDIUM,Priority.HOCH).contains(testTask));

}
@Test
    @DisplayName("Null Type und Null Priority werfen Exception")
    void testGetTaskNullTypeAndNullPriority(){
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.getTasksbyTypeAndPriority(null,null);
        });
}


@Test
    @DisplayName("Task wurde als erledigt markieren")
    void testTaskAlsErledigtMarkieren(){
        Task task=new Task("AS",TaskType.STUDIUM,"Hausaufgabe 2",Priority.HOCH,TaskStatus.IN_BEARBEITUNG,LocalDate.now().plusDays(4));
        taskManager.addTask(task);
        taskManager.taskAlsErledingtMarkieren("AS");
        assertEquals(TaskStatus.FERTIG,taskManager.getTaskByName("AS").getStatus());
}
@Test
    void testGetMotivation(){
        String satz=taskManager.getMotivation();
        assertNotNull(satz);
        assertFalse(satz.isEmpty());
}

@Test
    void testTaskAlsErledingtMarkieren(){
    Task task=new Task("AS",TaskType.STUDIUM,"Hausaufgabe 2",Priority.HOCH,TaskStatus.IN_BEARBEITUNG,LocalDate.now().plusDays(4));
    taskManager.addTask(task);
        taskManager.taskAlsErledingtMarkieren("AS");
        assertEquals(TaskStatus.FERTIG,taskManager.getTaskByName("AS").getStatus());
}
@Test
    void testTaskAlsErledingtMarkierenByNullName(){
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.taskAlsErledingtMarkieren(null);
        });
}
@Test
    @DisplayName("Testen sortieren nach Falligkeit")
    void testSortierenNachFalligkeit(){
        Task task1=new Task("Hausaufgabe 3",TaskType.STUDIUM,
                "Hausaufgabe 3",Priority.HOCH ,TaskStatus.OFFEN,
                LocalDate.of(2026, 06, 12));
        Task task2=new Task("Hausaufgabe 4",TaskType.STUDIUM,
                "Hausaufgabe 4",Priority.MITTEL ,TaskStatus.OFFEN,
                LocalDate.of(2026, 06, 24));
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        List<Task> sortierteT=taskManager.sortierenNachFalligkeit();
        assertEquals(LocalDate.of(2026, 06, 12),sortierteT.get(0).getFalligkeit());
}


@Test
    @DisplayName("Testen sortieren nach Status")
    void testSortierenNachStatus(){
    Task task1=new Task("Hausaufgabe 3",TaskType.STUDIUM,"Hausaufgabe 3",Priority.HOCH ,TaskStatus.FERTIG, LocalDate.of(2026, 06, 12));
    Task task2=new Task("Hausaufgabe 4",TaskType.STUDIUM,"Hausaufgabe 4",Priority.MITTEL ,TaskStatus.OFFEN, LocalDate.of(2026, 06, 24));
    taskManager.addTask(task1);
    taskManager.addTask(task2);
    List<Task> sortierteT=taskManager.sortierenNachStatus();
    assertEquals(TaskStatus.OFFEN,sortierteT.get(0).getStatus());

}

@Test
    @DisplayName("Test Sortieren nach Priority")
    void testSortierenNachPriority(){
    Task task1=new Task("Hausaufgabe 3",TaskType.STUDIUM,"Hausaufgabe 3",Priority.HOCH ,TaskStatus.FERTIG, LocalDate.of(2026, 06, 12));
    Task task2=new Task("Hausaufgabe 4",TaskType.STUDIUM,"Hausaufgabe 4",Priority.MITTEL ,TaskStatus.OFFEN, LocalDate.of(2026, 06, 24));
    taskManager.addTask(task1);
    taskManager.addTask(task2);
    List<Task>sortierteT=taskManager.sortierenNachPriority();
    assertEquals(Priority.HOCH,sortierteT.get(0).getPriority());
}

@Test
    @DisplayName("Test Sortieren nach Datum und Priority")
    void testSortierenNachDatumundPriority(){
    Task task1=new Task("Hausaufgabe 3",TaskType.STUDIUM,"Hausaufgabe 3",Priority.HOCH ,TaskStatus.FERTIG, LocalDate.of(2026, 06, 12));
    Task task2=new Task("Hausaufgabe 4",TaskType.STUDIUM,"Hausaufgabe 4",Priority.MITTEL ,TaskStatus.OFFEN, LocalDate.of(2026, 06, 24));
    taskManager.addTask(task1);
    taskManager.addTask(task2);
    List<Task>sortierte=taskManager.sortierenNachDAtumUndPriority();
    assertEquals(LocalDate.of(2026, 06, 12), sortierte.get(0).getFalligkeit() );
    assertEquals(Priority.HOCH,sortierte.get(0).getPriority());

}

@Test
    @DisplayName("Test erledingte AUfgabe")
    void testErledingteAUfgabe(){
        Task task1=new Task("Vorleistung 2",TaskType.STUDIUM,"SQL DML",Priority.HOCH,TaskStatus.FERTIG, LocalDate.of(2026, 05, 12));
taskManager.addTask(task1);
  assertTrue(taskManager.erledigteAufgaben().contains(task1));
    }


    @Test
    @DisplayName("Test Get Anzahl erledingte Aufgaben")
    void testAnzahlErl(){
        Task task1=new Task("Vorleistung 2",TaskType.STUDIUM,"SQL DML",Priority.HOCH,TaskStatus.FERTIG, LocalDate.of(2026, 05, 12));
        Task task2=new Task("Vorleistung 2",TaskType.STUDIUM,"SQL DML",Priority.HOCH,TaskStatus.FERTIG, LocalDate.of(2026, 05, 12));
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        assertEquals(2,taskManager.getAnzahlErledingteAufgabe());

    }

    @Test
    @DisplayName("Test Get Anzahl offene Aufgaben")
    void testAnzahlOffen(){
        Task task1=new Task("Vorleistung 2",TaskType.STUDIUM,"SQL DML",Priority.HOCH,TaskStatus.OFFEN, LocalDate.of(2026, 05, 12));
        Task task2=new Task("Vorleistung 2",TaskType.STUDIUM,"SQL DML",Priority.HOCH,TaskStatus.OFFEN, LocalDate.of(2026, 05, 12));
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        assertEquals(2,taskManager.getAnzahlOffeneTasks());
    }

    @Test
    @DisplayName("Test Get Task In_Bearbeitung")
    void testGetTinBearbeitung(){
        taskManager.addTask(testTask);
        assertTrue(taskManager.getTasksIN_Bearbeitung().contains(testTask));
    }

    @Test
    @DisplayName("Test Get Task by Type")
    void testGetTaskByPriority(){
        taskManager.addTask(testTask);
        assertTrue(taskManager.getTasksByType(TaskType.STUDIUM).contains(testTask));
    }
}