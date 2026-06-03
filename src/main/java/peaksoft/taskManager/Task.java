package peaksoft.taskManager;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Task {
    private String taskName;
private TaskType taskType;
private String taskDescription;
private Priority priority;
private TaskStatus status;
private LocalDate falligkeit;
@Override
    public String toString() {
    return "---------------" +
            "Aufgabe :  " + taskName +
            "  Tasktype: " + taskType +
            "  Beschreibung:  " + taskDescription +
            "  Priority:   " + priority +
            "  Status:   " + status +"" +
            "   Falligkeitsdatum:   "+ falligkeit ;

}
}

