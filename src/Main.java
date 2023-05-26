import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    static ArrayList<String> files = new ArrayList<>();
    static StringBuilder sb = new StringBuilder();
    static final String WORK_PATH = "/Users/serjzlo/Games/saveGames/";

    public static void main(String[] args) {
        GameProgress gameProgress = new GameProgress(50, 8, 1, 1.2);
        GameProgress gameProgress2 = new GameProgress(100, 2, 89, 5.5);
        GameProgress gameProgress3 = new GameProgress(74, 4, 20, 3.0);

        saveGame("save1.dat", gameProgress);
        saveGame("save2.dat", gameProgress2);
        saveGame("save3.dat", gameProgress3);

        zipFiles(files, "saves.zip");

        deleteFiles(files);

        System.out.println("Успешно завершено!");
    }

    public static void saveGame(String path, GameProgress gameProgress) {
        files.add(path);
        try (FileOutputStream fos = new FileOutputStream(WORK_PATH + path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }


    }

    public static void zipFiles(ArrayList<String> list, String zipName) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(WORK_PATH + zipName))) {
            for (String s : list) {
                FileInputStream fis = new FileInputStream(WORK_PATH + s);
                zos.putNextEntry(new ZipEntry(s));
                byte[] buffer = new byte[fis.available()];
                int a;
                if ((a = fis.read(buffer)) != -1) {
                    zos.write(buffer, 0, a);
                    sb.append(s).append(" - файл успешно записан в архив\n");
                }
                fis.close();
                zos.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFiles(List<String> files) {
        for (String temp : files) {
            try {
                Files.delete(Path.of(WORK_PATH + temp));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}