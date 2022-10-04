import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

/**
 * Класс, описывающий покупательскую корзину.
 */
public class Basket {

    private int[] prices;
    private String[] productsNames;
    private int[] productsCount;

    private Scanner scanner = new Scanner(System.in);


    public Basket(int[] prices, String[] productsNames) {
        this.prices = prices;
        this.productsNames = productsNames;
        productsCount = new int[productsNames.length];
    }

    /**
     * Выводит на экран весь ассортимент продуктов для покупки.
     */
    public void printListAllProductsForBuy() {
        System.out.println("Список возможных товаров для покупки: ");
        for (int i = 0; i < productsNames.length; i++) {
            System.out.println((i + 1) + ". " + productsNames[i] + " " + prices[i] + " руб/шт.");
        }
    }

    /**
     * Функция, где производится заполнение покупательской корзины данными.
     *
     * @param file текстовый или бинарный файл.
     */
    public void fillProductBasket(File file) {
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
            addToCart(productNumber, productCount);
            saveTxt(file);
        }
        printCart();
    }

    /**
     * Добавляет кол-во продуктов по номеру.
     *
     * @param productNum номер продукта из списка.
     * @param amount     кол-во продукта из списка.
     */
    private void addToCart(int productNum, int amount) {
        productsCount[productNum] += amount;
    }

    /**
     * Выводит на экран покупательскую корзину.
     */
    private void printCart() {
        System.out.println("Ваша корзина:");
        int sum = 0;
        for (int i = 0; i < productsCount.length; i++) {
            int allCountProduct = productsCount[i];
            int priceSumByProduct = prices[i] * allCountProduct;
            if (allCountProduct > 0) {
                System.out.println(
                        productsNames[i] + " " + allCountProduct + " шт. в сумме " + priceSumByProduct
                        + " руб.");
                sum += priceSumByProduct;
            }
        }
        System.out.println("Итого: " + sum + " руб.");
    }

    /**
     * Сохраняет покупательскую корзину в текстовый файл.
     *
     * @param textFile текстовый файл.
     */
    private void saveTxt(File textFile) {
        try (PrintWriter writer = new PrintWriter(textFile)) {
            for (int pos = 0; pos < productsNames.length; pos++) {
                writer.append(productsNames[pos] + " " + prices[pos] + " " + productsCount[pos]);
                writer.println();

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error! File not found");
        }
    }

    private void setProductsCount(int[] productsCount) {
        this.productsCount = productsCount;
    }

    /**
     * Восстанавливает объект корзины из текстового файла, в который ранее она была сохранена.
     *
     * @param textFile текстовый файл.
     * @return покупательская корзина.
     */
    public static Basket loadFromTxtFile(File textFile) {
        Path path = textFile.toPath();
        List<String> basketList;
        try {
            basketList = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] productsNames = new String[basketList.size()];
        int[] prices = new int[basketList.size()];
        int[] productsCount = new int[basketList.size()];

        for (int pos = 0; pos <= basketList.size() - 1; pos++) {
            String[] data = basketList.get(pos).split(" ");
            productsNames[pos] = data[0];
            prices[pos] = Integer.parseInt(data[1]);
            productsCount[pos] = Integer.parseInt(data[2]);
        }

        Basket basket = new Basket(prices, productsNames);
        basket.setProductsCount(productsCount);
        return basket;
    }
}
