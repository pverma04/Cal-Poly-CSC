import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        PathingStrategy a = new AStarPathingStrategy();
        SingleStepPathingStrategy s = new SingleStepPathingStrategy();
        a = s;

        ArrayList<String> words = new ArrayList<>();
        words.add("hello");
        words.add("hello1");
        words.add("hello2");

        System.out.println(words.stream().filter(str -> str.length() > 5).peek(System.out::println).allMatch(str -> str.length() > 5));
    }
}

