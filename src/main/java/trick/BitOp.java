package trick;

public class BitOp
{
    /**
     * m的n次方
     */
    static int power(int m, int n)
    {
        int sum = 1;
        int tmp = m;
        while (n != 0) {
            if ((n & 1) == 1) {
                sum *= tmp;
            }
            tmp *= tmp;
            n = n >> 1;
        }

        return sum;
    }

    public static void main(String[] args)
    {
        System.out.println(power(3, 5));
    }
}
