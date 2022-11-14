package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {
    private final ElementsCollection cards = $$(".list__item div");
    private final static ElementsCollection actionButton = $$x("//button[@data-test-id='action-deposit']");

    public DashboardPage() {
        SelenideElement heading = $("[data-test-id=dashboard]");
        heading.shouldBe(visible);
        SelenideElement reloadButton = $x("//button[@data-test-id='action-reload']");
        reloadButton.should(visible);
        SelenideElement error = $x("//div[@data-test-id='error-notification']");
    }

    public static MoneyTransfer clickFirstCardButton() {
        actionButton.first().click();
        return new MoneyTransfer();
    }

    public static MoneyTransfer clickSecondCardButton() {
        actionButton.last().click();
        return new MoneyTransfer();
    }

    public int getFirstCardBalance() {
        var text = cards.first().text();
        return extractBalance(text);
    }

    public int getSecondCardBalance() {
        var text = cards.last().text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        String balanceStart = "баланс: ";
        var start = text.indexOf(balanceStart);
        String balanceFinish = " р.";
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}