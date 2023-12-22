package edu.hw11.task3;

public class CheckAsm {
    private long run(int n) {
        if (n <= 1) {
            return n;
        }
        return run(n - 1) + run(n - 2);
    }
    public static void main(String[] args) {
        CheckAsm checkAsm = new CheckAsm();
        System.out.println(checkAsm.run(6));
    }
}
