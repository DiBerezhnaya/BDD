package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class MoneyTransfer {
    private final SelenideElement amountInput = $x("//span[@data-test-id='amount']//input");
    private final SelenideElement fromInput = $x("//span[@data-test-id='from']//input");
    private final SelenideElement button = $("[data-test-id=action-transfer]");
    private final SelenideElement error = $x(".//div[@class='money-input__currency']");
    private final SelenideElement errorButton = $x("//div[@data-test-id='error-notification']/button");

    public void transfer(String amount, String cardNumber) {
        amountInput.setValue(amount);
        fromInput.setValue(cardNumber);
        button.click();
    }

    public void error() {
        error.should(text("Операция невозможна! На карте недостаточно средств."));
        error.shouldBe(visible);
    }

    public void invalidCard() {
        errorButton.should(Condition.text("Ошибка! Произошла ошибка"));
        errorButton.shouldBe(visible);
    }
}
