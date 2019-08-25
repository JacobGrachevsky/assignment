package Playlist2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static String fileName = "";
    private static boolean loaded;
    private static ArrayList<Track> plst = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Привет! Надеюсь, ты прочитал README.md." +
                "\nНапоминаю, для работы с плейлистом его нужно сначала загрузить.");
        //noinspection InfiniteLoopStatement
        while (true) {
            loadPlaylist();
            loaded = true;
            while (loaded) {
                command();
            }
        }
    }

    private static void addTrack() {
        Scanner in = new Scanner(System.in);
        Track track = new Track();
        boolean write;
        Playlist playlist = new Playlist();
        playlist.setPlaylist(plst);
        System.out.println("Введите трек в формате <Track Name> [mm:ss] - <Author Name> - <Genre>, <Rating>: ");
        String line = in.nextLine();
        Pattern patternSplit = Pattern.compile("\\s*[\\[\\]\\-,:]\\s*"), // разбиение
                patternCheckFormat = Pattern.compile("(.*)*\\[\\d*:\\d*].-.(.*)*-.(.*)*,\\s\\d*.\\d*"); // проверка
        String[] wordsInLine = patternSplit.split(line);
        Matcher matcher = patternCheckFormat.matcher(line);
        boolean matches = matcher.matches();
        if (matches) {
            track.setTitle(wordsInLine[0]);
            track.setLength(((Integer.parseInt(wordsInLine[1]) * 60) + Integer.parseInt(wordsInLine[2])) * 1000);
            track.setArtist(wordsInLine[4]);
            track.setGenre(wordsInLine[5]);
            track.setRating(Double.parseDouble(wordsInLine[6]));
            playlist.addTrack(track);
            write = true;
        } else {
            write = false;
            System.out.println("Введенное название не соответствует формату!");
        }
        if (write) {
            try (BufferedWriter bw = new BufferedWriter(
                    new FileWriter(fileName, true))) {
                bw.newLine();
                bw.write(track.toString());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            System.out.println("В плей лист ничего записано не было...");
        }
        plst = playlist.getPlaylist();
    }

    private static void deleteTrack() {
        Scanner in = new Scanner(System.in);
        Track track = new Track();
        Playlist playlist = new Playlist();
        playlist.setPlaylist(plst);
        System.out.println("Введите трек в формате <Track Name> [mm:ss] - <Author Name> - <Genre>, <Rating>: ");
        String line = in.nextLine();
        Pattern patternSplit = Pattern.compile("\\s*[\\[\\]\\-,:]\\s*"), // разбиение
                patternCheckFormat = Pattern.compile("(.*)*\\[\\d*:\\d*].-.(.*)*-.(.*)*,\\s\\d*.\\d*"); // проверка
        String[] wordsInLine = patternSplit.split(line);
        Matcher matcher = patternCheckFormat.matcher(line);
        boolean matches = matcher.matches();

        if (matches) {
            track.setTitle(wordsInLine[0]);
            track.setLength(((Integer.parseInt(wordsInLine[1]) * 60) + Integer.parseInt(wordsInLine[2])) * 1000);
            track.setArtist(wordsInLine[4]);
            track.setGenre(wordsInLine[5]);
            track.setRating(Double.parseDouble(wordsInLine[6]));
            playlist.deleteTrack(track);
            line = track.toString();
        } else {
            System.out.println("Введенное название не соответствует формату!");
        }
        plst = playlist.getPlaylist();
         DeleteFromFile(line);
    }

    private static void loadPlaylist() {
        Scanner scanner = new Scanner(System.in);
        if ( fileName.isEmpty()) {
             fileName = scanner.nextLine().split(" ")[1];
        }
        Pattern pattern = Pattern.compile(".*\\.plst");
        Matcher matcher = pattern.matcher( fileName);
        boolean matches = matcher.matches();
        if (matches) {
            loadPlst( fileName);
        } else {
            List<String> listOfPlst = loadDir( fileName);
            System.out.println("\nВыбери плейлист - введи его порядковый номер в списке выше: ");
            int num = scanner.nextInt();
             fileName = listOfPlst.get(--num);
            loadPlst(fileName);
        }
    }

    private static List<String> loadDir(String dir) {
        List<String> listOfPlst = null;
        try (Stream<java.nio.file.Path> walk = Files.walk(Paths.get(dir))) {
            listOfPlst = walk.map(Path::toString).filter(f -> f.endsWith(".plst")).collect(Collectors.toList());
            listOfPlst.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfPlst;
    }

    private static void loadPlst(String fileName) {
        Pattern patternCheckFormat = Pattern.compile("(.*)*\\[\\d*:\\d*].-.(.*)*-.(.*)*,\\s\\d*.\\d*"),
                patternSplit = Pattern.compile("\\s*[\\[\\]\\-,:]\\s*");
        boolean match;
        Matcher matcher;
        String[] wordsInLine;
        Playlist playlist = new Playlist();
        ArrayList<String> incorrectLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            for (String line; (line = br.readLine()) != null; ) {
                matcher = patternCheckFormat.matcher(line);
                match = matcher.matches(); // проверяем строки на соотвествие формату
                if (match) {
                    Track track = new Track();
                    wordsInLine = patternSplit.split(line); // разбиваем строку из текстовго файла
                    track.setTitle(wordsInLine[0]);
                    track.setLength(((Integer.parseInt(wordsInLine[1]) * 60) + Integer.parseInt(wordsInLine[2])) * 1000);
                    track.setArtist(wordsInLine[4]);
                    track.setGenre(wordsInLine[5]);
                    track.setRating(Double.parseDouble(wordsInLine[6]));
                    playlist.addTrack(track); // собираем и добавляем в плейлист
                } else {
                    incorrectLines.add(line);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        if (incorrectLines.isEmpty()) {
            System.out.println("\nВсе хорошо, строк некорректного формата не найдено!");
        } else {
            System.out.println("\nЕсть строки некорректного формата (не будут загружены в плейлист):");
            incorrectLines.forEach(System.out::println); // вывод строк некорректного формата
        }
        plst = playlist.getPlaylist();
        System.out.println("\n--ЗАГРУЖЕННЫЙ ПЛЕЙЛИСТ--"); // содержит строки только корректного формата
        plst.forEach(System.out::println);
    }

    private static void printInitialPlst() {
        System.out.println("\n--НАЧАЛЬНЫЙ ФАЙЛ (может содержать в строках текстовые значения некорректного формата)--");
        try (BufferedReader br = new BufferedReader(new FileReader( fileName))) {
            for (String line; (line = br.readLine()) != null; ) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void  DeleteFromFile(String track) {
        String string = null;
        StringBuilder stringBuffer = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))) {
            while ((string = reader.readLine()) != null) {
                if (!track.equals(string)) {
                    stringBuffer.append(string).append("\n");
                }
            }
            string = stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert string != null;
        char[] buffer = new char[string.length()];
        string.getChars(0, string.length(), buffer, 0);

        try (FileWriter writer = new FileWriter(fileName, false)) {
            writer.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void command() {
        System.out.println("\nЧто нужно сделать?");
        Scanner in = new Scanner(System.in);
        String command = in.nextLine();
        Pattern patternSplit;
        Playlist playlist = new Playlist();
        playlist.setPlaylist(plst);
        String[] wordsInLine, params;

        if (command.contains("load")) {
            patternSplit = Pattern.compile(" ");
            wordsInLine = patternSplit.split(command);
        } else {
            patternSplit = Pattern.compile("\\s-");
            wordsInLine = patternSplit.split(command);
        }
        switch (wordsInLine[0]) {
            case "load":
                fileName = wordsInLine[1];
                Main.loaded = false;
                break;
            case "add":
                addTrack();
                break;
            case "deleteTrack":
                deleteTrack();
                break;
            case "select":
                params = Arrays.copyOfRange(wordsInLine, 1, wordsInLine.length);
                break;
            case "delete":
                params = Arrays.copyOfRange(wordsInLine, 1, wordsInLine.length);
                break;
            case "find":
                params = Arrays.copyOfRange(wordsInLine, 1, wordsInLine.length);
                break;
            case "showInitialPlst":
                printInitialPlst();
                break;
            case " DeleteFromFile":
                 DeleteFromFile(wordsInLine[1]);
                break;
            case "showPlst":
                if (playlist.getPlaylist().isEmpty()) {
                    System.out.println("Плейлист пуст!");
                } else {
                    playlist.getPlaylist().forEach(System.out::println);
                }
                break;
            case "q":
                System.exit(-1);
        }
    }
    
}
