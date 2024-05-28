import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MatrixUtils {
    public static void fillMatrixA(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = Math.abs((int) Math.round(Math.sin(i + j) * 10));
            }
        }
    }

    public static void fillMatrixB(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = Math.abs((int) Math.round(Math.cos(i + j) * 10));
            }
        }
    }

    public static void multiplySingleThreaded(int[][] A, int[][] B, int[][] C, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
    }

    public static void multiplyMultiThreaded(int[][] A, int[][] B, int[][] D, int size, int threads) throws InterruptedException {
        Thread[] threadArray = new Thread[threads];
        for (int i = 0; i < threads; i++) {
            threadArray[i] = new MatrixMultiplierThread(A, B, D, size, i, threads);
            threadArray[i].start();
        }
        for (Thread thread : threadArray) {
            thread.join();
        }
    }

    public static String calculateMD5(int[][] matrix) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        for (int[] row : matrix) {
            for (int element : row) {
                md.update((byte) element);
            }
        }
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
