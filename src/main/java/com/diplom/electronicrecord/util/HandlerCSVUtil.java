package com.diplom.electronicrecord.util;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HandlerCSVUtil  {

    private static final char DEFAULT_SEPARATOR = ',';

    private  static void writeLine(Writer w, List<String> values) throws IOException {
        writeLine(w, values, DEFAULT_SEPARATOR, ' ');
    }

    private static void writeLine(Writer w, List<String> values, char separators) throws IOException {
        writeLine(w, values, separators, ' ');
    }

    private  static String followCVSFormat(String value) {

        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;

    }

    private static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

        boolean first = true;


        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(followCVSFormat(value));
            } else {
                sb.append(customQuote).append(followCVSFormat(value)).append(customQuote);
            }

            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());

    }




    public static void exportFile(List<String[]> value, List<String> header, char separator, String path) throws IOException {
       final FileOutputStream fileOutputStream = new FileOutputStream(path);
       final Writer writer= new OutputStreamWriter(fileOutputStream, "windows-1251");
        writeLine(writer, header,separator);
        for (String[] line : value) {
            writeLine(writer, Arrays.asList(line), separator);
        }
        writer.flush();
        writer.close();
    }


    public static List<Object> importFile(String path, char separator) throws IOException {

        ArrayList<String> stringArrayList = new ArrayList<>();
        ArrayList<Object> result = new ArrayList<>();
        final FileInputStream    fileInputStream    = new FileInputStream(path);
        final InputStreamReader  inputStreamReader  = new InputStreamReader(fileInputStream, "windows-1251");
        CSVReader reader = new CSVReader(inputStreamReader,separator);
            String[] line;
            while ((line = reader.readNext()) != null) {
                stringArrayList.addAll(Arrays.asList(line));
                result.add(line);
            }

       return result;
    }
}
