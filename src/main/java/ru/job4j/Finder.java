package ru.job4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
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
    private static final Logger LOG = LoggerFactory.getLogger(Finder.class.getName());

    public static void main(String... args) {
        try {
            ArgsParser argsParser = ArgsParser.of(args);
            Path path = Paths.get(argsParser.get("d"));
            System.out.println(path);
            Predicate<Path> condition = getPredicate(
                    argsParser.get("n"),
                    argsParser.get("t"));
            List<Path> result = search(Paths.get(argsParser.get("d")), condition);
            System.out.println(result);
            writer(argsParser.get("o"), result);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static List<Path> search(Path root, Predicate<Path> condition) throws IOException {
        Search searcher = new Search(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }

    public static Predicate<Path> getPredicate(String name, String type) {
        if (type.equals("name")) {
            return path -> path.toFile().getName().equals(name);
        }
        return null;
    }

    private static void writer(String o, List<Path> paths) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(o)))) {
            for (Path p: paths) {
                pw.print(p + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

