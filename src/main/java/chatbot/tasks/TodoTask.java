package chatbot.tasks;

public class TodoTask extends Task {
    private String type;

    public TodoTask(String taskName) {
        super(taskName);
        this.type = "[T]";
    }

    public String writeToFileFormat() {
        return String.format("%s|%s|%s",
                "T",
                isDone == true ? "1" : "0",
                taskName);
    }

    @Override
    public String toString() {
        return this.type + super.toString();
    }
}
