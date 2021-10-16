package ru.job4j;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

/**
 * <h2>Поиск файлов по критерию.</h2>
 * Запуск: java -jar find.jar -d=c:/home -n=pom.xml -t=name -o=log.txt
 * Ключи:
 * -d - директория, в которой начинать поиск.
 * -n - имя файла, маска, либо регулярное выражение.
 * -o - результат записать в файл.
 * -t - тип поиска:
 * mask искать по маске,
 * name по полному совпадение имени,
 * regex по регулярному выражению.
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 14.04.2021
 */
public class Finder {
    //TODO Add Javadoc
    public static void main(String... args) {
        try {
            ArgsParser argsParser = ArgsParser.of(args);
            Path path = Paths.get(argsParser.get("d"));
            Predicate<Path> condition = new Condition().getPredicate(
                    argsParser.get("n"),
                    argsParser.get("t"));
            List<Path> result = search(Paths.get(argsParser.get("d")), condition);
            writer(argsParser.get("o"), result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Path> search(Path root, Predicate<Path> condition) throws IOException {
        Search searcher = new Search(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }

    private static void writer(String o, List<Path> paths) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(o)))) {
            for (Path p: paths) {
                pw.print(p + "\n");
            }
            pw.print("Amount: " + paths.size());
            System.out.println("Done.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
