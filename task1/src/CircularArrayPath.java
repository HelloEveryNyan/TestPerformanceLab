public class CircularArrayPath {
    public static void main(String[] args) {
        if (args.length != 2) {
            return;
        }

        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);

        if (n <= 0 || m <= 0) {
            return;
        }

        StringBuilder path = new StringBuilder();
        int currentPosition = 1;

        do {
            path.append(currentPosition);
            currentPosition = (currentPosition + m - 2) % n + 1;
        } while (currentPosition != 1);

        System.out.println(path);
    }
}
