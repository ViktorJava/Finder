package ru.job4j;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * <h2>Поиск файлов по критерию.</h2>
 * Запуск: java -jar find.jar -d=c:/home -n=pom.xml -t=name -o=log.txt
 * Ключи регистронезависимые:
 * -d - директория, в которой начинать поиск.
 * -n - имя файла, маска, регулярное выражение, подстрока поиска в файле.
 * -o - результат записать в файл.
 * -t - тип поиска:
 * mask искать по маске,
 * name по полному совпадение имени,
 * regex по регулярному выражению,
 * content по подстроке в файле.
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 14.04.2021
 */
public class Finder {
    public static void main(String... args) {
        try {
            ArgsParser argsParser = ArgsParser.of(args);
            Predicate<Path> condition = new Condition().getPredicate(
                    argsParser.get("n"),
                    argsParser.get("t"));
            Finder finder = new Finder();
            List<Path> result = finder.search(Paths.get(argsParser.get("d")), condition);
            finder.writer(argsParser.get("o"), result, args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод обходит папки в поисках файлов согласно предиката.
     *
     * @param root      Папка поиска файлов.
     * @param condition Предикат поиска.
     * @return Список каталогов с результатами.
     * @throws IOException Возможное исключение.
     */
    public List<Path> search(Path root, Predicate<Path> condition) throws IOException {
        Search searcher = new Search(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }

    /**
     * Метод записывает в файл, результаты поиска файлов.
     *
     * @param output Имя файла с результатами.
     * @param paths  Список результатов.
     * @param args   Параметры запуска программы, для записи в отчёт.
     */
    private void writer(String output, List<Path> paths, String[] args) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(
                new FileWriter(output, UTF_8)))) {
            pw.println("--Parameters used: " + Arrays.toString(args));
            for (Path p: paths) {
                pw.print(p + System.getProperty("line.separator"));
            }
            pw.print("--Amount: " + paths.size());
            System.out.printf("Done. Amount: %d files", paths.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
