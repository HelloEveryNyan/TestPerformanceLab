import java.io.*;
import java.util.*;

public class MinMovesToEqualArray {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("usage: java MinMovesToEqualArray <inputfile>");
            return;
        }

        String inputFileName = args[0];
        List<Integer> nums = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                nums.add(Integer.parseInt(line));
            }
        } catch (IOException e) {
            System.out.println("error reading file: " + e.getMessage());
            return;
        }

        if (nums.isEmpty()) {
            System.out.println("input empty.");
            return;
        }

        int[] numsArray = nums.stream().mapToInt(i -> i).toArray();

        Arrays.sort(numsArray);
        int median = numsArray[numsArray.length / 2];

        int minMoves = 0;
        for (int num : numsArray) {
            minMoves += Math.abs(num - median);
        }

        System.out.println(minMoves);
    }
}
