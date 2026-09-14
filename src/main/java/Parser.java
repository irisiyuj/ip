public class Parser {
    public static String parseTodo(String input) throws IrriException{
        String description = input.substring(5).trim();
        if (description.isEmpty()){
            throw new IrriException("The description of a todo cannot be empty.");
        }
        return description;
    }
    public static String[] parseDeadline(String input) throws IrriException{
        String rest = input.substring(9).trim();
        int byIndex = rest.indexOf(" /by ");
        if (byIndex == -1){
            throw new IrriException("A deadline must include a due date. Use: deadline <description> /by <date>");
        }
        String description = rest.substring(0,byIndex).trim();
        String by = rest.substring(byIndex + 5).trim();
        if (description.isEmpty()){
            throw new IrriException("The description of a deadline cannot be empty.");
        }
        if (by.isEmpty()){
            throw new IrriException("The deadline date cannot be empty. Use: /by <date>");
        }
        return new String[] {description, by};
    }
    public static String[] parseEvent(String input) throws IrriException{
        String rest = input.substring(6).trim();
        int fromIndex = rest.indexOf(" /from ");
        int toIndex = rest.indexOf(" /to ");
        if (fromIndex == -1 || toIndex == -1 || fromIndex > toIndex){
            throw new IrriException("An event must include start and end times. Use: event <description> /from <start> /to <end>");
        }
        String description = rest.substring(0, fromIndex).trim();
        String from = rest.substring(fromIndex + 6, toIndex).trim();
        String to = rest.substring(toIndex +5).trim();
        if (description.isEmpty()){
            throw new IrriException("The description of an event cannot be empty.");
        }
        if (from.isEmpty()){
            throw new IrriException("The start time cannot be empty. Use: /from <start>");
        }
        if (to.isEmpty()){
            throw new IrriException("The end time cannot be empty. Use: /to <end>");
        }
        return new String[]{description, from, to};
    }
    public static int parseTaskNumber(String input, String command) throws IrriException{
        String rest = input.substring(command.length()).trim();
        if (rest.isEmpty()){
            throw new IrriException("Please specify a task number. Use: " + command + " <number>");
        }
        try {
            int index = Integer.parseInt(rest) - 1;
            if (index < 0){
                throw new IrriException("Task number must be positive.");
            }
            return index;
        }catch (NumberFormatException e){
            throw new IrriException("Please enter a valid task number (e.g., \" + command + \" 2)");
        }
    }
}
