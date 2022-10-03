import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Решение задачи №1 по теме "Одномерные массивы".
 * Решение задачи №1 по теме "Исключения".
 * Решение задачи №1 по теме "Работа с файлами. Потоки ввода-вывода. Сериализация".
 */

public class Main {
    static int[] prices = {35, 87, 109};
    static String[] products = {"Хлеб", "Молоко", "Яблоки"};

    public static Basket getBasket(File file) {
        if (file.exists() && file.length() != 0) {
            return Basket.loadFromTxtFile(file);
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Error! Need create the file");
            }
            return new Basket(prices, products);
        }
    }

    public static void main(String[] args) {

        File txtFile = new File("basket.txt");
        Basket basket = getBasket(txtFile);

        basket.printListAllProductsForBuy();
        basket.fillProductBasket(txtFile);
    }
}

