package edu.hw9.task2;

import java.util.concurrent.ForkJoinPool;

public class ParallelTreeProcessor {
    public static void main(String[] args) {
        // Пример использования
        try (ForkJoinPool pool = new ForkJoinPool()) {
//            List<Path> directoriesWithMoreThan1000Files = pool.invoke(new FindDirectoriesTask(Path.of("D:\\"), 1000));
//            System.out.println(directoriesWithMoreThan1000Files);
//
//             Пример 2: поиск файлов по предикату (например, файлы размером более 1 MB и с расширением ".txt")
//            Predicate<Path> sizeAndExtensionPredicate = path -> path.toFile().length() > 20;// && path.toFile().getName().endsWith("e");
//            List<Path> filesMatchingPredicate = pool.invoke(new FindFilesTask(Path.of("./src/test/resources"), sizeAndExtensionPredicate));
//            System.out.println("Files matching the predicate: " + filesMatchingPredicate);
        }

    }
}
