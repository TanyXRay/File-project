import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Решение задачи №1 по теме "Одномерные массивы".
 * Решение задачи №1 по теме "Исключения".
 * Решение задачи №1 по теме "Работа с файлами. Потоки ввода-вывода. Сериализация".
 */

public class Main {

    public static void main(String[] args) {
        int[] prices = {35, 87, 109};
        String[] products = {"Хлеб", "Молоко", "Яблоки"};

        Scanner scanner = new Scanner(System.in);

        Basket basket1 = new Basket(prices, products);

        File fileTxt = new File("C:\\Users\\User\\IdeaProjects\\Files-project\\basket.txt");
        if (fileTxt.exists()) {
            System.out.println("Файл уже существует, восстанавливаем корзину...");
            Basket.loadFromTxtFile(fileTxt);
        } else {
            try {
                fileTxt.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            basket1.printListAllProductsForBuy();
            while (true) {
                System.out.println("Выберите товар и количество или введите \"end\" ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("end")) {
                    break;
                }
                String[] parts = input.split(" ");
                if (parts.length != 2) {
                    continue;
                }

                int productNumber;
                try {
                    productNumber = Integer.parseInt(parts[0]) - 1; // выбор продукта
                } catch (NumberFormatException e) {
                    System.out.println("Вы ввели текст заместо числа. Попробуйте снова!");
                    continue;
                }
                if (productNumber >= 3 || productNumber < 0) {
                    System.out.println("Вы ввели некорректное число продукта. Попробуйте снова!");
                    continue;
                }

                int productCount;
                try {
                    productCount = Integer.parseInt(parts[1]); // выбор количества продуктов
                } catch (NumberFormatException e) {
                    System.out.println("Вы ввели текст заместо числа. Попробуйте снова!");
                    continue;
                }
                if (productCount > 50 || productCount <= 0) {
                    System.out.println("Вы ввели некорректное кол-во продукта. Попробуйте снова!");
                    continue;
                }
                basket1.addToCart(productNumber, productCount);
                basket1.saveTxt(fileTxt);
            }
            basket1.printCart();
        }
    }
}

