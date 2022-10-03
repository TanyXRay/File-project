import java.io.*;

/**
 * Класс, описывающий покупательскую корзину.
 */
public class Basket {

    private int[] prices;
    private String[] productsName;
    private int[] productsCount;


    public Basket(int[] prices, String[] productsName) {
        this.prices = prices;
        this.productsName = productsName;
        productsCount = new int[productsName.length];
    }

    /**
     * Выводит на экран весь ассортимент продуктов для покупки.
     */
    public void printListAllProductsForBuy() {
        System.out.println("Список возможных товаров для покупки: ");
        for (int i = 0; i < productsName.length; i++) {
            System.out.println((i + 1) + ". " + productsName[i] + " " + prices[i] + " руб/шт.");
        }
    }

    /**
     * Добавляет кол-во продуктов по номеру.
     *
     * @param productNum номер продукта из списка.
     * @param amount     кол-во продукта из списка.
     */
    public void addToCart(int productNum, int amount) {
        productsCount[productNum] += amount;
    }

    /**
     * Выводит на экран покупательскую корзину.
     */
    public void printCart() {
        System.out.println("Ваша корзина:");
        int sum = 0;
        for (int i = 0; i < productsCount.length; i++) {
            int allCountProduct = productsCount[i];
            int priceSumByProduct = prices[i] * allCountProduct;
            if (allCountProduct > 0) {
                System.out.println(productsName[i] + " " + allCountProduct + " шт. в сумме " + priceSumByProduct + " руб.");
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
    public void saveTxt(File textFile) {
        try (PrintWriter saveInTxt = new PrintWriter(textFile)) {
            for (String productName : productsName) {
                saveInTxt.print(productName + " ");
            }
            saveInTxt.println();
            for (int productPrice : prices) {
                saveInTxt.print(productPrice + " ");
            }
            saveInTxt.println();
            for (int productCount : productsCount) {
                saveInTxt.print(productCount + " ");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Восстанавливает объект корзины из текстового файла,
     * в который ранее она была сохранена.
     *
     * @param textFile текстовый файл.
     * @return покупательская корзина.
     */
    public static Basket loadFromTxtFile(File textFile) {
        try (BufferedReader readTxt = new BufferedReader(new FileReader(textFile))) {
            StringBuilder resultStringBuilder = new StringBuilder();
            String line;
            while ((line = readTxt.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
