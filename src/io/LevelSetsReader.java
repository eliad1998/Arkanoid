package io;
import levels.LevelInformation;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * LevelSetsReader.
 * Class that has functions working with level sets file.
 * We can get all level keys and names and the levels files.
 */
public class LevelSetsReader {
    /**
     * getLevels.
     * @param reader a reader of the level sets file.
     * @return list of the list of level information that represent the level sets.
     */
    public static List<List<LevelInformation>> getLevels(Reader reader) {
        List<List<LevelInformation>> listLevelSets = new ArrayList<List<LevelInformation>>();
        LineNumberReader linesReader = new LineNumberReader(reader);
        String line = "";
        try {
            //Reading the first line.
            line = linesReader.readLine();
            while (line != null) {
                //The lel set files.
                if (linesReader.getLineNumber() % 2 == 0) {
                    //Reading the current level set.
                    LevelSpecificationReader levelSpecificationReader = new LevelSpecificationReader();
                    InputStreamReader streamReader
                            = new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(line));
                    listLevelSets.add(levelSpecificationReader.fromReader(streamReader));
                }
                line = linesReader.readLine();
            }
            return listLevelSets;
        } catch (IOException e) {
            throw new RuntimeException("Problem with reading the file.");
        }
    }
    /**
     * getLevels.
     * @param reader a reader of the level sets file.
     * @return map with keys of level set key and value of level set name.
     */
    public static Map<String, String> getLevelSetMap(Reader reader) {
        Map<String, String> map = new TreeMap<String, String>();
        LineNumberReader linesReader = new LineNumberReader(reader);
        String line = "";
        try {
            //Reading the first line.
            line = linesReader.readLine();
            while (line != null) {
                //Level key and name lines are the odds lines.
                if (linesReader.getLineNumber() % 2 == 1) {
                    //Splitting the lines to level key and level name.
                    String[]sepparated = line.split(":");
                    map.put(sepparated[0], sepparated[1]);
                }
                line = linesReader.readLine();
            }
            return map;
        } catch (IOException e) {
            throw new RuntimeException("Problem with reading the file.");
        }
    }
}
