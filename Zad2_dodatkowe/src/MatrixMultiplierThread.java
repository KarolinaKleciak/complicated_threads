
class MatrixMultiplierThread extends Thread {
    int[][] A, B, W;
    int SIZE, start, fault;

    public MatrixMultiplierThread(int[][] A, int[][] B, int[][] W, int SIZE, int start, int fault) {
        this.A = A;
        this.B = B;
        this.W = W;
        this.SIZE = SIZE;
        this.start = start;
        this.fault = fault;
    }

    @Override
    public void run() {
        for (int i = start; i < SIZE; i += fault) {
            for (int j = 0; j < A.length; j++) {
                for (int k = 0; k < A[i].length; k++) {
                    W[i][j] += A[i][k] * B[k][j];
                }
            }
        }
    }
}