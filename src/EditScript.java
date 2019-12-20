import java.util.ArrayList;
import java.util.List;

public class EditScript<Integer> {
    private final List<EditCommand<Integer>> commands;
    private int modifications;

    public EditScript() {
        commands = new ArrayList<EditCommand<Integer>>();
        modifications = 0;
    }

    public void append(final Commands.KeepCommand<Integer> command) {
        commands.add(command);
    }

    public void append(final Commands.InsertCommand<Integer> command) {
        commands.add(command);
        ++modifications;
    }

    public void append(final Commands.DeleteCommand<Integer> command) {
        commands.add(command);
        ++modifications;
    }

    public void visit(final CommandVisitor<Integer> visitor) {
        for (final EditCommand<Integer> command : commands) {
            command.accept(visitor);
        }
    }

    public int getModifications() {
        return modifications;
    }
}