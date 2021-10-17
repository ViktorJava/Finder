package ru.job4j;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
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
    public static void main(String... args) {
        try {
            Finder finder = new Finder();
            ArgsParser argsParser = ArgsParser.of(args);
            Predicate<Path> condition = new Condition().getPredicate(
                    argsParser.get("n"),
                    argsParser.get("t"));
            List<Path> result = finder.search(Paths.get(argsParser.get("d")), condition);
            finder.writer(argsParser.get("o"), result);
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
     */
    private void writer(String output, List<Path> paths) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(
                new FileWriter(output, Charset.forName("WINDOWS-1251"))))) {
            for (Path p: paths) {
                pw.print(p + System.getProperty("line.separator"));
            }
            pw.print("Amount: " + paths.size());
            System.out.printf("Done. Amount: %d files", paths.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
