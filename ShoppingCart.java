import java.util.Scanner;

public class ShoppingCart {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] products = {"Product A", "Product B", "Product C"};
        int[] prices = {20, 40, 50};
        int[] productQuantities = new int[products.length];

        for (int i = 0; i < products.length; i++) {
            System.out.print("Enter the quantity of " + products[i] + ": ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // consume the newline character
            System.out.print("Is " + products[i] + " wrapped as a gift? (yes/no): ");
            boolean giftWrap = scanner.nextLine().toLowerCase().equals("yes");

            int totalAmount = quantity * prices[i] + (giftWrap ? 1 : 0);
            productQuantities[i] = quantity;
            System.out.println(products[i] + " - Quantity: " + quantity + ", Total Amount: $" + totalAmount);
        }

        int subtotal = 0;
        for (int i = 0; i < products.length; i++) {
            subtotal += productQuantities[i] * prices[i];
        }

        String[] discountRules = {"flat_10_discount", "bulk_5_discount", "bulk_10_discount", "tiered_50_discount"};
        int[] discounts = {10, 5, 10, 50};

        String discountName = null;
        int discountAmount = 0;

        for (int i = 0; i < discountRules.length; i++) {
            boolean condition = false;

            if (i == 0) {
                condition = subtotal > 200;
            } else if (i == 1) {
                condition = hasQuantityExceeding(productQuantities, 10);
            } else if (i == 2) {
                condition = hasTotalQuantityExceeding(productQuantities, 20);
            } else if (i == 3) {
                condition = hasTotalQuantityExceeding(productQuantities, 30) && hasQuantityExceeding(productQuantities, 15);
            }

            if (condition && discounts[i] > discountAmount) {
                discountName = discountRules[i];
                discountAmount = discounts[i];
            }
        }

        int shippingFee = (sum(productQuantities) / 10) * 5;
        int giftWrapFee = sum(productQuantities);
        
        System.out.println("\nOrder Summary:");
        System.out.println("Subtotal: $" + subtotal);
        System.out.println((discountName != null) ? "Discount Applied: " + discountName + " - $" + discountAmount : "No Discounts Applied");
        System.out.println("Shipping Fee: $" + shippingFee);
        System.out.println("Gift Wrap Fee: $" + giftWrapFee);
        System.out.println("Total: $" + (subtotal - discountAmount + shippingFee + giftWrapFee));
    }

    private static boolean hasQuantityExceeding(int[] quantities, int limit) {
        for (int qty : quantities) {
            if (qty > limit) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasTotalQuantityExceeding(int[] quantities, int limit) {
        int totalQuantity = sum(quantities);
        return totalQuantity > limit;
    }

    private static int sum(int[] array) {
        int sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }
}
