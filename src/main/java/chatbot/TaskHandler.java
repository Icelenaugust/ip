package chatbot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import chatbot.exceptions.IndexOutOfRangeException;
import chatbot.exceptions.TaskDoneException;
import chatbot.tasks.DeadlineTask;
import chatbot.tasks.EventTask;
import chatbot.tasks.Task;
import chatbot.tasks.TodoTask;

/**
 * Represents a task handler that handles the tasks
 */
public class TaskHandler {
    private ArrayList<Task> taskList;

    public TaskHandler() {
        this.taskList = new ArrayList<>();
    }

    public void clearTaskList() {
        this.taskList = new ArrayList<>();
    }

    public ArrayList<Task> getTaskList() {
        return this.taskList;
    }

    public int getLength() {
        return this.taskList.size();
    }

    public void addTodoTask(String taskName) {
        taskList.add(new TodoTask(taskName));
    }

    public void addDeadlineTask(String taskName, LocalDate deadline) {
        taskList.add(new DeadlineTask(taskName, deadline));
    }

    public void addEventTask(String taskName, LocalDateTime startTime, LocalDateTime endTime) {
        taskList.add(new EventTask(taskName, startTime, endTime));
    }

    public Task taskIsDone(int index) throws IndexOutOfRangeException, TaskDoneException {
        try {
            Task task = taskList.get(index - 1);
            if (!task.getIsDone()) {
                task.taskDone();
                return task;
            } else {
                throw new TaskDoneException();
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfRangeException();
        }
    }

    public Task deleteTask(int index) throws IndexOutOfRangeException {
        try {
            Task task = taskList.get(index - 1);
            taskList.remove(index - 1);
            return task;
        } catch(IndexOutOfBoundsException e) {
            throw new IndexOutOfRangeException();
        }
    }

    /**
     * Reads from the task data file.
     *
     * @param storedTaskList an ArrayList of tasks in file format.
     */

    public void loadTaskList(ArrayList<String> storedTaskList) {
        for (String eachTask : storedTaskList) {
            String[] words = eachTask.split("\\|");
            String type = words[0].strip();
            boolean isDone = words[1].strip().equals("1");
            String taskName = words[2].strip();

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMM yyyy");
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a");
            switch (type) {
            case "T": {
                Task task = new TodoTask(taskName);
                if (isDone) {
                    task.taskDone();
                }
                this.taskList.add(task);
                break;
            }
            case "D": {
                LocalDate deadline = LocalDate.parse(words[3].strip(), dateFormat);
                Task task = new DeadlineTask(taskName, deadline);
                if (isDone) {
                    task.taskDone();
                }
                this.taskList.add(task);
                break;
            }
            case "E": {
                LocalDateTime startTime = LocalDateTime.parse(words[3].strip(), timeFormat);
                LocalDateTime endTime = LocalDateTime.parse(words[4].strip(), timeFormat);
                Task task = new EventTask(taskName,startTime, endTime);
                if (isDone) {
                    task.taskDone();
                }
                this.taskList.add(task);
                break;
            }
            }

        }
    }
}
