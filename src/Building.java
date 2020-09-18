import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class Building {
    static HashMap<Integer, String> map = setUp();
    public static void main(String[] args) {
        System.out.println("Key");
        System.out.println("1. Historic building");
        System.out.println("2. Castle");
        System.out.println("3. Vernacular building");
        System.out.println("4. Industrial building");
        System.out.println("5. Architectural treasure");
        System.out.println("6. Natural heritage");
        System.out.println("7. Museum");
        System.out.println("8. Archaeological site");
        System.out.println("9. Town site");
        System.out.println("10. Battlefield");
        System.out.println("11. Park");
        System.out.println("12. Other");
        String file = "1.csv";
        BufferedReader br = null;
        String line = "";

        try {
            FileWriter myWriter = new FileWriter("result1.txt");
            br = new BufferedReader(new FileReader(file));
            br.readLine();
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] content = getContent(line);
                System.out.println(content[0]);
                String buildingType = view(content);
                myWriter.write(buildingType + "\n");
                System.out.println("Wrote successfully");
            }
            myWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String[] getContent(String line) {
        // https://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes
        String[] content = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        String[] result = new String[7];
        int counter = 1;
        result[0] = content[0];
        for (int i = 1; i < content.length; i++) {
            String field = content[i];
            if (!field.equals("")) {
                if (field.contains("\"")) {
                    result[counter] = field.replace("\"", "");
                    counter++;
                }
                else {
                    result[counter] = field;
                    counter++;
                }
            }
        }
        return result;
    }

    public static HashMap<Integer, String> setUp() {
        HashMap<Integer, String> buildings = new HashMap<>();
        buildings.put(1, "Historic building");
        buildings.put(2, "Castle");
        buildings.put(3, "Vernacular building");
        buildings.put(4, "Industrial building");
        buildings.put(5, "Architectural treasure");
        buildings.put(6, "Natural heritage");
        buildings.put(7, "Museum");
        buildings.put(8, "Archaeological site");
        buildings.put(9, "Town site");
        buildings.put(10, "Battlefield");
        buildings.put(11, "Park");
        buildings.put(12, "Other");
        return buildings;
    }

    public static String view(String[] content) {
        int source = 1;
        Scanner sc= new Scanner(System.in);
        String url = "";
        while (source <= 5) {
            while ((url = content[source]) == null) {
                source++;
                if (source > 5)
                    break;
            }
            if (url != null)
                openWebpage(url);
            else break;
            String input = sc.nextLine();
            if (!input.equals("N")) {
                String ans = getBuildingTypeFromInput(input);
                while (ans == null) {
                    System.out.println("Invalid building type(s), please try again.");
                    input = sc.nextLine();
                    ans = getBuildingTypeFromInput(input);
                }
                return ans;
            }
            source++;
        }
        System.out.println("Enter building type(s)");
        String input = sc.nextLine();
        String ans = getBuildingTypeFromInput(input);
        while (ans == null) {
            System.out.println("Invalid building type(s), please try again.");
            input = sc.nextLine();
            ans = getBuildingTypeFromInput(input);
        }
        return ans;
    }

    public static String getBuildingTypeFromInput(String input) {
        String[] types = input.split(" ");
        StringBuilder result = new StringBuilder();
        for (String type : types) {
            try {
                int num = Integer.parseInt(type);
                if (num > 0 && num < 13) {
                    result.append(map.get(num));
                    result.append(",");
                }
            }
            catch (NumberFormatException e) {
                return null;
            }
        }
        if (result.length() > 0)
            result.setLength(result.length() - 1);
        return result.toString();
    }
    /**
     * https://stackoverflow.com/questions/10967451/open-a-link-in-browser-with-java-button
     */
    public static void openWebpage(String urlString) {
        System.out.println(urlString);
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
