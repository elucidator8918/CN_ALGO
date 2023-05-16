import java.util.Scanner;

public class BitStuff {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter string: ");
        String str = scanner.nextLine();
        scanner.close();

        StringBuilder bits = new StringBuilder();
        int b = 0;

        for (char c : str.toCharArray()) {
            bits.append(c);
            if (c == '1' && b != 5) {
                b++;
                if (b == 5) {
                    bits.append('0');
                    b = 0;
                }
            } else {
                b = 0;
            }
        }

        System.out.println("After bit stuffing: ");
        System.out.println(bits.toString());
    }
}
