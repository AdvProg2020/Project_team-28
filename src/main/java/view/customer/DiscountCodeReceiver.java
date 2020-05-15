package view.customer;

import controller.CustomerController;
import view.Menu;

public class DiscountCodeReceiver {
    private CustomerController controller;

    public DiscountCodeReceiver(CustomerController controller) {
        this.controller = controller;
        System.out.println();

        while (execute()) ;
    }

    private boolean execute() {
        System.out.println("Enter your discount code below:");

        String input = Menu.scanner.nextLine();
        input = input.replaceAll("^\\s+", "");
        input = input.replaceAll("\\s+$", "");

        if (controller.validateDiscountCode(input)) {
            try {
                controller.useDiscuontCode(input);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        } else return !input.equals("");
        return false;
    }
}
