package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileGameOfLifeBoardDao implements Dao<GameOfLifeBoard> {
    private String filename;

    public FileGameOfLifeBoardDao(String filename) {
        this.filename = filename;
    }
    public GameOfLifeBoard read() throws FileNotFoundException {
        try (Scanner odczyt = new Scanner(new File(filename))) {
        }

    }

    public void write(GameOfLifeBoard gameOfLifeBoard) throws FileNotFoundException {
        try (PrintWriter zapis = new PrintWriter(filename)) {
        }


    }



}
