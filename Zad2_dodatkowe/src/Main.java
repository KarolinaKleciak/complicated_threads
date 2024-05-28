import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] sizes = {899, 1200, 1750};
        int threads = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of available processors: " + threads);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("wyniki.txt", true))) {
            for (int size : sizes) {
                int[][] A = new int[size][size];
                int[][] B = new int[size][size];
                int[][] C = new int[size][size];
                int[][] D = new int[size][size];

                MatrixUtils.fillMatrixA(A);
                MatrixUtils.fillMatrixB(B);

                // Jednowątkowe mnożenie macierzy
                long startTime = System.nanoTime();
                MatrixUtils.multiplySingleThreaded(A, B, C, size);
                long endTime = System.nanoTime();
                double singleThreadedTime = (endTime - startTime) / 1e9; // Konwersja na sekundy

                // Wielowątkowe mnożenie macierzy
                startTime = System.nanoTime();
                MatrixUtils.multiplyMultiThreaded(A, B, D, size, threads);
                endTime = System.nanoTime();
                double multiThreadedTime = (endTime - startTime) / 1e9; // Konwersja na sekundy

                boolean isEqual = Arrays.deepEquals(C, D);

                String resultString = String.format(
                        "Matrix size: %dx%d\nSingle-threaded time: %.2f seconds\nMulti-threaded time: %.2f seconds\nResults are equal: %b\nSpeedup: %.2f",
                        size, size, singleThreadedTime, multiThreadedTime, isEqual, singleThreadedTime / multiThreadedTime
                );

                System.out.println(resultString);
                writer.write(resultString);

                // Obliczanie i wyświetlanie sum kontrolnych MD5
                String md5C = MatrixUtils.calculateMD5(C);
                String md5D = MatrixUtils.calculateMD5(D);
                System.out.println("MD5 checksum for C: " + md5C);
                System.out.println("MD5 checksum for D: " + md5D + "\n");
                writer.write("MD5 checksum for C: " + md5C + "\n");
                writer.write("MD5 checksum for D: " + md5D + "\n");
            }
        } catch (IOException | NoSuchAlgorithmException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
