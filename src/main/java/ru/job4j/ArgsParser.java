package ru.job4j;

import java.util.HashMap;
import java.util.Map;

/**
 * <h2>Именованные аргументы</h2>
 * Класс реализует приём массива параметров и разбивание их на пары.
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 25.04.2021
 */
public class ArgsParser {

    private final Map<String, String> values = new HashMap<>();

    /**
     * Метод возвращает значение ключа.
     *
     * @param key Ключ.
     * @return Значение
     */
    public String get(String key) {
        if (!values.containsKey(key)) {
            throw new IllegalArgumentException("Notfound parameter: " + key);
        }
        return values.get(key);
    }

    /**
     * Приватный метод, парсит массив параметров полученный в виде аргумента.
     * Ключи регистронезависимые.
     *
     * @param args Массив параметров в формате -ключ=значение.
     */
    private void parse(String[] args) {
        for (String arg: args) {
            String[] split = arg.replaceFirst("-", "").split("=");
            if (split.length != 2) {
                throw new IllegalArgumentException("Invalid argument format: -param=value");
            }
            values.put(split[0].toLowerCase(), split[1]);
        }
        if (values.size() != 4) {
            throw new IllegalArgumentException("Not enough arguments: -d= -n= -t= -o=");
        }
    }

    /**
     * Парсер аргументов.
     *
     * @param args Массив параметров в формате -ключ=значение.
     * @return Объект типа ArgsParser с распарсенными параметрами.
     */
    public static ArgsParser of(String[] args) {
        if (args.length == 0) {
            System.out.print("Example: java -jar finder.jar ");
            System.out.print("-d=directory -t=search type [name, mask, regex, content] ");
            System.out.println("-n=[file name, regex, mask or content] -o=result file");
            throw new IllegalArgumentException("Notfound arguments.");
        }
        ArgsParser names = new ArgsParser();
        names.parse(args);
        return names;
    }
}
