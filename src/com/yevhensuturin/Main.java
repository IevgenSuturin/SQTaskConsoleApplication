package com.yevhensuturin;
//Implement console application(mandatory)
//        -  Read txt file and split it by lines
//        -  Calculate statistic for each line: longest word(symbols between 2 spaces), shortest word, line length,
//        average word length. Unit test are mandatory
//        -  Aggregate these values for all lines from file(unit test)
//        -  Store line and file statistic into DB(with JDBC).

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//1 Implement console application(mandatory)
//        -  Read txt file and split it by lines
//        -  Calculate statistic for each line: longest word(symbols between 2 spaces), shortest word, line length,
//        average word length. Unit test are mandatory
//        -  Aggregate these values for all lines from file(unit test)
//        -  Store line and file statistic into DB(with JDBC).
//
//
//2. Implement Web application (mandatory)
//        - Create Hiberante mapping  for tables
//        - Return from server side list of handled files and statistic per file. You can use RESTful services or
//        Spring MVC controllers
//        - Implement frontend part - display list of files, and statistic per line for selected file. Usage of ReactJS
//        framework is encouraged. In other case any other JS framework can be used
//        3. Add to console app possibility to handle all files in directory and sub-directories(optional)
//        4. Add to Web app filter by file&#39;s statistic(example: files with more than 10 lines)(optional)
//        5. Add to Web app possibility to enter and send own text for handling(optional)
//        6. Implement concurrent handling of each file in directory


class StatisticData{
    private Integer longestLinesWord;
    private Integer shortestLinesWord;
    private Integer averageLinesWord;

    public StatisticData(Integer longestLinesWord, Integer shortestLinesWord, Integer averageLinesWord) {
        this.longestLinesWord = longestLinesWord;
        this.shortestLinesWord = shortestLinesWord;
        this.averageLinesWord = averageLinesWord;
    }

    public Integer getLongestLinesWord() {
        return longestLinesWord;
    }

    public Integer getShortestLinesWord() {
        return shortestLinesWord;
    }

    public Integer getAverageLinesWord() {
        return averageLinesWord;
    }
}

public class Main {


    public static void main(String[] args) {
        List<StatisticData> list = new ArrayList<>();
        ReadStatisticData("textFile.txt", list);
	}

    private static void ReadStatisticData(String filePathStr, List<StatisticData> listData){
        Path filePath = FileSystems.getDefault().getPath(filePathStr);
        try(BufferedReader reader = Files.newBufferedReader(filePath)){
            String input;
            int count=0;
            while((input = reader.readLine()) != null){
                System.out.print("Line "+ count++ + "\t");
                System.out.println(input);
                String[] data = input.split(" ");
                List<String> words = Arrays.asList(data);
                listData.add(getWordStatistic(words));
            }
        } catch(IOException e){
            System.out.println("Something went wrong " + e.getMessage());
        }
    }

    private static StatisticData getWordStatistic(List<String> words){
        Integer longestWord = words.stream().max((o1, o2)->o1.length()-o2.length()).get().length();
        Integer shortestWord = words.stream().min((o1, o2)->o1.length()-o2.length()).get().length();
        StringBuffer buffer = new StringBuffer();
        words.forEach(buffer::append);
        Integer averageWord = Math.toIntExact(buffer.toString().length() / words.stream().count());

        return new StatisticData(longestWord, shortestWord, averageWord);
    }
}
