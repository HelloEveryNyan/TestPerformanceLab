import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CirclePointPosition {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("provide paths to both files arguments");
            return;
        }

        String circleFilePath = args[0];
        String pointsFilePath = args[1];

        try {
            List<String> circleFileLines = Files.readAllLines(Paths.get(circleFilePath));
            String[] centerCoordinates = circleFileLines.get(0).split(" ");
            double centerX = Double.parseDouble(centerCoordinates[0]);
            double centerY = Double.parseDouble(centerCoordinates[1]);
            double radius = Double.parseDouble(circleFileLines.get(1));

            List<String> pointsFileLines = Files.readAllLines(Paths.get(pointsFilePath));
            List<Point> points = new ArrayList<>();
            for (String line : pointsFileLines) {
                String[] pointCoordinates = line.split(" ");
                double x = Double.parseDouble(pointCoordinates[0]);
                double y = Double.parseDouble(pointCoordinates[1]);
                points.add(new Point(x, y));
            }

            for (Point point : points) {
                int position = determinePointPosition(centerX, centerY, radius, point);
                System.out.println(position);
            }

        } catch (IOException e) {
            System.out.println("error reading files: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("error number format " + e.getMessage());
        }
    }

    public static int determinePointPosition(double centerX, double centerY, double radius, Point point) {
        double distanceSquared = Math.pow(point.x - centerX, 2) + Math.pow(point.y - centerY, 2);
        double radiusSquared = Math.pow(radius, 2);

        if (distanceSquared < radiusSquared) {
            return 1;
        } else if (distanceSquared == radiusSquared) {
            return 0;
        } else {
            return 2;
        }
    }

    static class Point {
        double x, y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
