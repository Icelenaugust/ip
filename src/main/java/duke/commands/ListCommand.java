package duke.commands;

import duke.Storage;
import duke.TaskHandler;
import duke.exceptions.ChatBotException;
import duke.tasks.Task;

import java.util.ArrayList;

/**
 * Represents a list command line
 */
public class ListCommand extends ChatBotCommand {
    public ListCommand() {
    }

    /**
     * Returns list of tasks.
     *
     * @param th TaskHandler.
     * @param storage Storage to write and read file.
     * @return Command response.
     * @throws ChatBotException if encounters error.
     */
    public String runTask(TaskHandler th, Storage storage) throws ChatBotException {
        ArrayList<Task> tasks = th.getTaskList();
        StringBuilder output = new StringBuilder("Here are the tasks in your list: \n");
        for (int i = 0; i < tasks.size(); i++) {
            int num = i + 1;
            output.append(num)
                    .append(": ")
                    .append(tasks.get(i))
                    .append("\n");
        }

        if (tasks.size() == 0) {
            output.append("--- No Tasks Found ---");
        }
        return output.toString();
    }

}
