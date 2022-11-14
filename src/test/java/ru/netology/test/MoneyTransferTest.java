package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPageV1;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.getFirstCardInfo;
import static ru.netology.data.DataHelper.getSecondCardInfo;
import static ru.netology.page.DashboardPage.clickFirstCardButton;
import static ru.netology.page.DashboardPage.clickSecondCardButton;

public class MoneyTransferTest {

    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        }

    @Test
    void moneyTransferFromFirstToSecondCard() {
        int amount = 5_000;
        var dashboardPage = new DashboardPage();
        var firstCardBalanceStart = dashboardPage.getFirstCardBalance();
        var secondCardBalanceStart = dashboardPage.getSecondCardBalance();
        var moneyTransfer = clickFirstCardButton();
        var firstCardBalanceFinish = firstCardBalanceStart + Integer.parseInt(String.valueOf(amount));
        var secondCardBalanceFinish = secondCardBalanceStart - Integer.parseInt(String.valueOf(amount));
        moneyTransfer.transfer(String.valueOf(amount), String.valueOf(getSecondCardInfo()));

        assertEquals(firstCardBalanceFinish, dashboardPage.getFirstCardBalance());
        assertEquals(secondCardBalanceFinish, dashboardPage.getSecondCardBalance());
    }

    @Test
    public void moneyTransferFromSecondToFirstCard() {
        int amount = 3_000;
        var dashboardPage = new DashboardPage();
        var firstCardBalanceStart = dashboardPage.getSecondCardBalance();
        var secondCardBalanceStart = dashboardPage.getFirstCardBalance();
        var moneyTransfer = clickSecondCardButton();
        var firstCardBalanceFinish = firstCardBalanceStart + Integer.parseInt(String.valueOf(amount));
        var secondCardBalanceFinish = secondCardBalanceStart - Integer.parseInt(String.valueOf(amount));
        moneyTransfer.transfer(String.valueOf(amount), String.valueOf(getFirstCardInfo()));

        assertEquals(firstCardBalanceFinish, dashboardPage.getSecondCardBalance());
        assertEquals(secondCardBalanceFinish, dashboardPage.getFirstCardBalance());
    }

    @Test
    public void moneyTransferExceedCardBalance() {
        int amount = 20_000;
        var moneyTransfer = clickFirstCardButton();
        moneyTransfer.transfer(String.valueOf(amount), String.valueOf(getSecondCardInfo()));
        moneyTransfer.error();
    }

    @Test
    public void moneyTransferFromSecondToSecondCard() {
        int amount = 4_000;
        var moneyTransfer = clickSecondCardButton();
        moneyTransfer.transfer(String.valueOf(amount), String.valueOf(getSecondCardInfo()));
        moneyTransfer.invalidCard();
      }
}
