package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;

public class ReadTextFile {
	public static String[] readCarRegFromTextFile(String filePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line;
		StringBuilder sb = new StringBuilder();

		Pattern carReg = Pattern.compile("\\b[A-Z]{2}[0-9]{2}\\s?[A-Z]{3}\\b");

		while ((line = br.readLine()) != null) {
			Matcher matcher = carReg.matcher(line);
			while (matcher.find()) {
				sb.append(matcher.group()).append(",");
			}
		}
		br.close();

		return sb.length() > 0 ? sb.toString().split(",") : new String[0];
	}

    public static Map<String, String> carOutputDetails(String fileName) {
        Map<String, String> carData = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 4); 
                if (parts.length == 4) {
                    String reg = parts[0].trim();
                    String details = parts[1].trim() + ", " + parts[2].trim() + ", " + parts[3].trim();
                    carData.put(reg, details);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return carData;
    }


}
