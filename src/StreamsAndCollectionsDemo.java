import java.util.*;
import java.util.stream.*;

public class StreamsAndCollectionsDemo {

    public static void main(String[] args) {


        List<String> fruits = List.of("apple" , "banana", "mango", "kiwi", "orange", "grape");

        // ==========================================
        // 1. filter
        // ==========================================
        List<String> longFruits = fruits.stream()
                .filter(f -> f.length() > 5)
                .collect(Collectors.toList());

        System.out.println("Filtered (length > 5): " + longFruits);

        // ==========================================
        // 2. map
        // ==========================================
        List<String> upperFruits = fruits.stream()
                .map(f -> f.toUpperCase())
                .collect(Collectors.toList());

        System.out.println("Mapped (uppercase): " + upperFruits);

        // ==========================================
        // 3. filter + map
        // ==========================================
        List<String> filteredAndMapped = fruits.stream()
                .filter(f -> f.length() > 5)
                .map(f -> f.toUpperCase())
                .collect(Collectors.toList());

        System.out.println("Filtered + Mapped: " + filteredAndMapped);

        // ==========================================
        // 4. reduce
        // ==========================================
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);

        int sum = numbers.stream()
                .reduce(0, (total, current) -> total + current);

        System.out.println("Sum using reduce: " + sum);

        // ==========================================
        // 5. collect to Set
        // ==========================================
        Set<String> fruitSet = fruits.stream()
                .collect(Collectors.toSet());

        System.out.println("As a Set: " + fruitSet);

        // ==========================================
        // 6. collect to Map
        // ==========================================
        Map<String, Integer> fruitToLength = fruits.stream()
                .collect(Collectors.toMap(f -> f, f -> f.length()));

        System.out.println("Fruit to length map: " + fruitToLength);

        // ==========================================
        // 7. groupingBy
        // ==========================================
        Map<Integer, List<String>> groupedByLength = fruits.stream()
                .collect(Collectors.groupingBy(f -> f.length()));

        System.out.println("Grouped by length: " + groupedByLength);

        // ==========================================
        // 8. groupingBy  + counting
        // ==========================================
        Map<Integer, Long> countByLength = fruits.stream()
                .collect(Collectors.groupingBy(f -> f.length(), Collectors.counting()));

        System.out.println("Count by length: " + countByLength);

        // ==========================================
        // 9. sorted
        // ==========================================
        List<String> sortedFruits = fruits.stream()
                .sorted()
                .collect(Collectors.toList());

        System.out.println("Sorted fruits: " + sortedFruits);

        // ==========================================
        // 10. (loop) vs (stream)
        // ==========================================


        List<String> oldWayResult = new ArrayList<>();
        for (String f : fruits) {
            if (f.length() > 5) {
                oldWayResult.add(f.toUpperCase());
            }
        }

        List<String> newWayResult = fruits.stream()
                .filter(f -> f.length() > 5)
                .map(f -> f.toUpperCase())
                .collect(Collectors.toList());

        System.out.println("Old way result: " + oldWayResult);
        System.out.println("New way result: " + newWayResult);
    }
}