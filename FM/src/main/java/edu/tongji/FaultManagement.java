package edu.tongji;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FaultManagement {
    public void generateWarningMessage(String message) {
        this.generateWarningMessage(message, "./");
    }

    public void generateWarningMessage(String message, String filepath) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(now);
        String filename = date + ".txt";

        try {
            File parentDir = new File(filepath);
            
            if (!parentDir.exists()) {
                System.out.println("[Parent directory doesn't exist!]");
                parentDir.mkdirs();
            }
            File warningFile = new File(parentDir, filename);

            if (!warningFile.exists()) {
                System.out.println("[File doesn't exist!]");
                warningFile.createNewFile();
            }

            System.out.println("Path: " + warningFile.getPath());
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(warningFile.getPath()), "UTF-8"));
            bw.write(date);
            bw.newLine();
            bw.write("MESSAGE:");
            bw.newLine();
            bw.write(message);
            bw.newLine();
            bw.close();
            
            System.out.println("Done!");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}