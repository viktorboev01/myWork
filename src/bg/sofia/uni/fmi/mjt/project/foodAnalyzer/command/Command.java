package bg.sofia.uni.fmi.mjt.foodAnalyzer.command;

//reference:
//https://github.com/fmi/java-course/blob/master/11-network-ii/snippets/todo-list-app/src/bg/sofia/uni/fmi/mjt/todo/command/Command.java
public record Command(String command, String[] arguments) {
}
