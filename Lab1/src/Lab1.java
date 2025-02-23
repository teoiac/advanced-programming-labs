//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //Laborator 1 - IACOB TEODORA
        //Exercitiul 1 : Afiseaza Hello World
        System.out.printf("Hello World!\n");
        //Exercitiul 2 : array de string uri
        String[] languages = new String[]{"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"};
        //Exercitiul 3 : Genereaza random integer n
        int n;
        n = (int) (Math.random() *1_000_000);
        System.out.println("n = " + n);
        n=n*3;
        n += 0b10101;
        n+=0xFF;
        n*=6;
        System.out.println("n = " + n);
        while(n>=10)
        {
            n=sumOfDigits(n);
        }
        System.out.println("Willy-nilly, this semester I will learn " + languages[n]);
    }

    private static int sumOfDigits(int n) {
        int sum = 0;
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }
}