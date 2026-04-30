package ru.netology.pageObjects.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import ru.netology.pageObjects.data.DataHelper;
import ru.netology.pageObjects.page.LoginPage;


import static com.codeborne.selenide.Selenide.$;

class MoneyTransferTest {

    @Test
    void transferBetweenOwnCards() {
        var info = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(info);
        var loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        var verificationPage = loginPage.validLogin(info);
        verificationPage.validVerify(verificationCode);
    }
}
