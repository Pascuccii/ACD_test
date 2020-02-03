import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    private static final String inPath = "in.txt";
    private static final String outPath = "out.txt";
    private static ArrayList<String> list = new ArrayList<>();

    public static void main(String[] args) {
        createFileIfNotExists(inPath);
        createFileIfNotExists(outPath);
        loadList(inPath);
        list.sort(new ComplexLineComparator());
        saveList(outPath);
        for (String s : list)
            System.out.println(s);
    }

    public static void createFileIfNotExists(String pathname) {
        File inFile = new File(pathname);
        if (inFile.exists()) {
            System.out.println(pathname + " exists");
        } else {
            try {
                if (inFile.createNewFile())
                    System.out.println(pathname + " created.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void loadList(String inPath) {
        try {
            FileReader fr = new FileReader(new File(inPath));
            Scanner scan = new Scanner(fr);
            while (scan.hasNextLine())
                list.add(scan.nextLine());
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveList(String outPath) {
        try {
            FileWriter fw = new FileWriter(new File(outPath));
            String toWrite = "";
            for (String line : list)
                toWrite = toWrite.concat(line + "\n");
            fw.write(toWrite);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ComplexLineComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        String[] line1 = o1.split("\\s+");
        String[] line2 = o2.split("\\s+");

        for (int i = 0; i < Math.min(line1.length, line2.length); i++) {
            if (line1[i].equals(line2[i]))
                continue;
            if (NumberUtils.isParsable(line1[i]) && !NumberUtils.isParsable(line2[i]))
                return -1;
            if (!NumberUtils.isParsable(line1[i]) && NumberUtils.isParsable(line2[i]))
                return 1;
            if (NumberUtils.isParsable(line1[i]) && NumberUtils.isParsable(line2[i]))
                if (Double.parseDouble(line1[i]) > Double.parseDouble(line2[i])) return 1;
                else return -1;
            if (!NumberUtils.isParsable(line1[i]) && !NumberUtils.isParsable(line2[i]))
                return line1[i].compareTo(line2[i]);
            if (i == Math.min(line1.length, line2.length) - 1)
                return line1.length - line2.length;
        }
        return 0;
    }
}