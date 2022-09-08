package homework;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class ComplexExamples {

    static class Person {
        final int id;

        final String name;

        Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person person)) return false;
            return getId() == person.getId() && getName().equals(person.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }
    }

    private static Person[] RAW_DATA = new Person[]{
            new Person(0, "Harry"),
            new Person(0, "Harry"), // дубликат
            new Person(1, "Harry"), // тёзка
            new Person(2, "Harry"),
            new Person(3, "Emily"),
            new Person(4, "Jack"),
            new Person(4, "Jack"),
            new Person(5, "Amelia"),
            new Person(5, "Amelia"),
            new Person(6, "Amelia"),
            new Person(7, "Amelia"),
            new Person(8, "Amelia"),
    };
        /*  Raw data:

        0 - Harry
        0 - Harry
        1 - Harry
        2 - Harry
        3 - Emily
        4 - Jack
        4 - Jack
        5 - Amelia
        5 - Amelia
        6 - Amelia
        7 - Amelia
        8 - Amelia

        **************************************************

        Duplicate filtered, grouped by name, sorted by name and id:

        Amelia:
        1 - Amelia (5)
        2 - Amelia (6)
        3 - Amelia (7)
        4 - Amelia (8)
        Emily:
        1 - Emily (3)
        Harry:
        1 - Harry (0)
        2 - Harry (1)
        3 - Harry (2)
        Jack:
        1 - Jack (4)
     */

    public static void main(String[] args) {
        System.out.println("Raw data:");
        System.out.println();

        for (Person person : RAW_DATA) {
            System.out.println(person.id + " - " + person.name);
        }

        System.out.println();
        System.out.println("**************************************************");
        System.out.println();
        System.out.println("Duplicate filtered, grouped by name, sorted by name and id:");

        Map<String, Long> sortedNames = Arrays.stream(RAW_DATA)
                .distinct()
                .sorted(Comparator.comparingInt(Person::getId))
                .collect(groupingBy(Person::getName, counting()));

        for (String key : sortedNames.keySet()) {
            System.out.println("Key: " + key + "\nValue: " + sortedNames.get(key) + "\n");
        }



        /*
        Task1
            Убрать дубликаты, отсортировать по идентификатору, сгруппировать по имени

            Что должно получиться
                Key:Amelia
                Value:4
                Key: Emily
                Value:1
                Key: Harry
                Value:3
                Key: Jack
                Value:1
         */



        /*
        Task2

            [3, 4, 2, 7], 10 -> [3, 7] - вывести пару менно в скобках, которые дают сумму - 10
         */

        System.out.println("________________________________\nTask 2:\n");

        Integer[] numbers = new Integer[]{3, 4, 2, 7};
        int sum = 10;

        List<List<Integer>> pairOfNumbers = Arrays.asList(numbers).stream()
                .flatMap(a -> Arrays.asList(numbers).stream().
                        flatMap(b -> a + b == sum ? Stream.of(Arrays.asList(a, b)) : Stream.empty()))
                .limit(1)
                .toList();


        for (List<Integer> n : pairOfNumbers) {
            System.out.println(n + " add up to " + sum);
        }



/*        Task3
            Реализовать функцию нечеткого поиска
                    fuzzySearch("car", "ca6$$#_rtwheel"); // true
                    fuzzySearch("cwhl", "cartwheel"); // true
                    fuzzySearch("cwhee", "cartwheel"); // true
                    fuzzySearch("cartwheel", "cartwheel"); // true
                    fuzzySearch("cwheeel", "cartwheel"); // false
                    fuzzySearch("lw", "cartwheel"); // false
        */
        System.out.println("________________________________\nTask 3:\n");
        System.out.println("car in ca6$$#_rtwheel:\n" + fuzzySearch("car", "ca6$$#_rtwheel"));
        System.out.println("\ncwhl in cartwheel:\n" + fuzzySearch("cwhl", "cartwheel"));
        System.out.println("\ncwhee in cartwheel:\n" + fuzzySearch("cwhee", "cartwheel"));
        System.out.println("\ncartwheel in cartwheel:\n" + fuzzySearch("cartwheel", "cartwheel"));
        System.out.println("\ncwheeel in cartwheel:\n" + fuzzySearch("cwheeel", "cartwheel"));
        System.out.println("\nlw in cartwheel:\n" + fuzzySearch("lw", "cartwheel"));



    }

    private static boolean fuzzySearch(String str, String text) {

        if (str.length()==0 && text.length()>=0) return true;

        if (text.length() == 0 && str.length() > 0) return false;

        else {
            if (text.contains(str.substring(0,1))){
                return fuzzySearch(str.substring(1, str.length()), text.substring(text.indexOf(str.substring(0,1))+1, text.length()));
            }
            return false;
        }
    }
}
