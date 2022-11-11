import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import static processing.core.PApplet.loadStrings;

public class ProcessFile {
    public static void processFile(String filePath) throws IOException {
        File input = new File(filePath);
        FileWriter output = new FileWriter("drawMe.txt");
        List<Point> points = new ArrayList<>();
        double x, y, z;
        Scanner sc = new Scanner(input);
        while(sc.hasNext()){
            String currentLine = sc.nextLine();
            String[] coordinates = currentLine.split(",");
            x = Double.parseDouble(coordinates[0]);
            y = Double.parseDouble(coordinates[1]);
            z = Double.parseDouble(coordinates[2]);
            points.add(new Point(x, y, z));
        }

        List<Point> newPoints = points.stream().filter(p -> p.getZ() <= 2.0). //all z <= 2
                map(p -> new Point(0.2 * p.getX(), 0.2 * p.getY(), 0.2 * p.getZ())).
                map(p -> new Point(p.getX() - 150, p.getY() - 37, p.getZ())).collect(Collectors.toList());

        for(int i = 0; i < newPoints.size(); i++){
            output.write(newPoints.get(i).getX() + ", " + newPoints.get(i).getY() + ", " +
                    newPoints.get(i).getZ()+ "\n");
        }
    }
}
