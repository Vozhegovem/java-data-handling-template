package com.epam.izh.rd.online.repository;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;

public class SimpleFileRepository implements FileRepository {

    private int cntFile = 0;
    private int cntDirs = 0;
    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(path);
        File dir = new File(resource.getPath());
        if (dir.exists()) {
            File[] files = dir.listFiles();
            for(File file: files){
                if (file.isDirectory()){
                    countFilesInDirectory(path + "/" +file.getName());
                } else {
                    cntFile++;
                }
            }
        }
        return cntFile;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(path);
        File dir = new File(resource.getPath());
        if (dir.exists() && dir.isDirectory()) {
            cntDirs ++;
            File[] files = dir.listFiles();
            for(File file: files){
                if (file.isDirectory()){
                    countDirsInDirectory(path + "/" +file.getName());
                }
            }
        }
        return cntDirs;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        File dir = new File(from);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                try {
                    Files.copy(file.toPath(), new File(to).toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        File newDir = new File(path);
        if (!newDir.isDirectory()){
            if(!newDir.mkdir()){
                return false;
            }
        }
        File newFile = new File(path, name);
        try {
            if (newFile.createNewFile()){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(fileName);
        String text = "";
        try(BufferedReader reader = new BufferedReader(new FileReader(resource.getFile()))){
            text = reader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
