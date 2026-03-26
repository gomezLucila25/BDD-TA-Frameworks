package com.epam.framework.pages;

import com.epam.framework.model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class InventoryPage extends BasePage {

    @FindBy(css = ".title")
    private WebElement pageTitle;

    @FindBy(css = ".inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(css = ".shopping_cart_link")
    private WebElement cartIcon;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement burgerMenuButton;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    public InventoryPage verifyPageLoaded() {
        log.info("Verifying inventory page is loaded");
        wait.until(d -> isDisplayed(pageTitle));
        log.info("Inventory page loaded — title: [{}]", getText(pageTitle));
        return this;
    }

    public InventoryPage addProductToCart(String productName) {
        log.info("ACTION add to cart — product: [{}]", productName);
        inventoryItems.stream()
                .filter(item -> item.findElement(By.cssSelector(".inventory_item_name"))
                        .getText().equals(productName))
                .findFirst()
                .ifPresentOrElse(
                        item -> {
                            WebElement addBtn = item.findElement(By.cssSelector("button"));
                            click(addBtn);
                            log.info("Product [{}] added to cart", productName);
                        },
                        () -> log.error("Product not found: [{}]", productName)
                );
        return this;
    }

    public CartPage goToCart() {
        log.info("Navigating to cart");
        click(cartIcon);
        return new CartPage();
    }

    public int getCartItemCount() {
        if (!isDisplayed(cartBadge)) return 0;
        int count = Integer.parseInt(getText(cartBadge));
        log.debug("Cart item count: [{}]", count);
        return count;
    }

    public String getPageTitle() {
        return getText(pageTitle);
    }

    public List<Product> getAvailableProducts() {
        log.debug("Fetching all available products");
        return inventoryItems.stream()
                .map(item -> {
                    String name = item.findElement(By.cssSelector(".inventory_item_name")).getText();
                    String priceText = item.findElement(By.cssSelector(".inventory_item_price"))
                            .getText().replace("$", "");
                    return new Product(name, Double.parseDouble(priceText));
                })
                .toList();
    }

    public LoginPage logout() {
        log.info("Logging out");
        click(burgerMenuButton);
        click(logoutLink);
        return new LoginPage();
    }
}
