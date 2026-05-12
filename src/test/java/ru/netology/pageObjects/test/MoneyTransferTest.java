package ru.netology.pageObjects.test;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.pageObjects.data.DataHelper;
import ru.netology.pageObjects.page.DashBoardPage;
import ru.netology.pageObjects.page.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

    private DashBoardPage dashBoardPage;
    private DataHelper.CardInfo firstCard;
    private DataHelper.CardInfo secondCard;

    @BeforeEach
    void setup() {
        var info = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(info);
        var loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        var verificationPage = loginPage.validLogin(info);
        dashBoardPage = verificationPage.validVerify(verificationCode);

        firstCard = DataHelper.getFirstCardInfo();
        secondCard = DataHelper.getSecondCardInfo();
    }

    @Test
    void transferBetweenOwnCards() {
        int initialFirstCardBalance = dashBoardPage.getCardBalance(firstCard);
        int initialSecondCardBalance = dashBoardPage.getCardBalance(secondCard);

        int transferAmount = 3000;

        var transferPage = dashBoardPage.selectCard(secondCard);
        var updateDashBoard = transferPage.validTransfer(transferAmount, firstCard);

        int newFirstCardBalance = updateDashBoard.getCardBalance(firstCard);
        int newSecondCardBalance = updateDashBoard.getCardBalance(secondCard);

        assertEquals(initialFirstCardBalance - transferAmount, newFirstCardBalance,
                "Баланс первой карты должен уменьшится на сумму перевода");
        assertEquals(initialSecondCardBalance + transferAmount, newSecondCardBalance,
                "Баланс второй карты должен увеличиться на сумму перевода");
    }
}
