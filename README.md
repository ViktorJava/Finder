# Проект Finder [<img alt="Logo" src="images/logo.png" height="80" align="right"/>](https://www.vectorlogo.zone)
> Учебный проект поиска файлов..

[![Build Status](https://app.travis-ci.com/ViktorJava/Finder.svg?branch=master)](https://app.travis-ci.com/ViktorJava/Finder)
[![codecov](https://codecov.io/gh/ViktorJava/Job4j_finder/branch/master/graph/badge.svg?token=tt2knqhxMN)](https://codecov.io/gh/ViktorJava/Job4j_finder)

## Технологии и инструменты:
<p align="center">
<img src="https://www.vectorlogo.zone/logos/java/java-ar21.svg" alt="java" width="120" height="60"/>
<img src="images/idea.png" alt="intellij" height="50"/>
<img src="https://www.vectorlogo.zone/logos/github/github-ar21.svg" alt="github" height="70"/>
<img src="images/maven.png" alt="maven" height="30"/>
<img src="https://www.vectorlogo.zone/logos/travis-ci/travis-ci-ar21.svg" alt="Travis CI" width="120" height="60"/>
<img src="images/checkstyle.png" alt="CheckStyle"  height="40"/>
<img src="images/codecov.png" alt="Codecov"  height="35"/>
<img src="images/junit.png" alt="JUnit"  height="40"/>
<img src="images/jcf.png" alt="JCF"  width="90"/>
</p>

 Создал программу для поиска файлов.

1. Программа должна искать данные в заданном каталоге и подкаталогах.
2. Имя файла может задаваться, целиком, по маске, по регулярному выражению ~~(не обещаю)~~.
3. Программа должна собираться в `jar` 
4. Программа должна записывать результат в файл.
5. В программе должна быть валидация ключей и подсказка.

## Проверка вашей установки Java
Для работы с любым программным обеспечением, написанным на **JAVA**, 
у вас должен быть установлен **Java SE Development Kit (JDK)**. Я использую **jdk-14.0.1**. 
Если у вас все настроено правильно, вы сможете открыть командное окно и выполнить следующие две команды:

`$ java -version`

`$ javac -version`

Обе команды должны завершиться успешно и сообщить об одной и той же версии **Java**.

## Компиляция и запуск
Для компиляции проекта, неберите: `mvn package`

1. Программа должна собираться в **jar** и запускаться с **обязательным** указанием всех четырёх параметров.
Запуск программы без параметров `java -jar find.jar` выводит в консоль, подсказку с параметрами.

2. В результате работы программы, будет сформирован файл результатов, который сохраниться 
в текстовом формате **UTF8** кодировкой. Сохранение файла происходит в папке расположения программы.

Примеры:

* На диске `C:\` будут найдены все файлы с любым именем и только с расширением **txt**. 
Результат поиска будет записан в файл **log.txt**. Файлы с запрещённым доступом, пропускаются,
о чём будет сообщено в консоли.

`java -jar find.jar -d=c:/ -n=*.txt -t=mask -o=log.txt`

* На диске `C:\` будут найдены все файлы с именем **file** и с расширением **txt**. 
Результат поиска будет записан в файл **log.txt**

`java -jar find.jar -d=c:/ -n=file.txt -t=name -o=log.txt`

* На диске `C:\` будут найдены все файлы, внутри которых найдено слово Integral. Поиск case sensitive.
Результат поиска будет записан в файл **log.txt**

`java -jar find.jar -d=c:\projects\job4j_finder\, -n=Integral, -t=content, -o=log.log`

* На диске `C:\` будут найдены все файлы с именем в котором обязателно присутствуют от трёх и более цыфр 
и с любым расширением. Результат поиска будет записан в файл **log.txt**.

`java -jar find.jar -d=c:/ -n=\d{3}.* -t=regex -o=log.txt`

Ключи:

**-d** - директория, в которой начинать поиск.<p>
**-n** - имя файла, маска, либо регулярное выражение.<p>
**-t** - тип поиска: **mask** искать по маске, **name** по полному совпадение имени, **regex** по регулярному выражению,
         **content** по подстроке в файле.<p>
**-o** - результат записать в файл.<p>
Ключи, регистронезависимые: `-T=content = -t=content`. 

## Лицензия
	
[MIT. Free Software!](https://github.com/ViktorJava/job4j/tree/master/LICENSE)

---

>e-mail:[gmail.com](mailto:gipsyscrew@gmail.com) &nbsp;&middot;&nbsp;
>fb:[Facebook.com](https://www.facebook.com/viktor.vdovichenko) &nbsp;&middot;&nbsp;
> GitHub:[@ViktorJava](https://github.com/ViktorJava) &nbsp;&middot;&nbsp;
> OK:[Odnoklassniki](https://ok.ru/profile/571539586668)

