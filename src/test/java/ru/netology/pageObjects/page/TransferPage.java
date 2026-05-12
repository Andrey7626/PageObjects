package ru.netology.pageObjects.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.pageObjects.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement amountField = $("[data-test-id='amount'] input");
    private final SelenideElement fromField = $("[data-test-id='from'] input");
    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private final SelenideElement cancelButton = $("[data-test-id='action-cancel']");
    private final SelenideElement errorMessage = $("[data-test-id='error-notification']");

    public TransferPage() {
        transferButton.should(Condition.visible);
    }

    //выполняет успешный перевод и возвращает страницу Dashboard
    public DashBoardPage validTransfer(int amount, DataHelper.CardInfo fromCard) {
        amountField.setValue(String.valueOf(amount));
        fromField.setValue(fromCard.getNumber());
        transferButton.click();
        return new DashBoardPage();
    }

    //выполняет перевод без возврата страницы
    public void transfer(int amount, DataHelper.CardInfo fromCard) {
        amountField.setValue(String.valueOf(amount));
        fromField.setValue(fromCard.getNumber());
        transferButton.click();
    }

    //выполняет перевод с некорректными данными
    public void transferWithInvalidData(int amount, String fromCardNumber) {
        amountField.setValue(String.valueOf(amount));
        fromField.setValue(fromCardNumber);
        transferButton.click();
    }

    //отменяет перевод и возвращается на Dashboard
    public DashBoardPage cancelTransfer() {
        cancelButton.click();
        return new DashBoardPage();
    }

    //проверяет сообщение об ошибке
    public void checkErrorMessage(String expectedText) {
        errorMessage.should(Condition.visible).should(Condition.text(expectedText));
    }

    //проверяет, что сообщение об ошибке отсутствует
    public void checkErrorMessageNotVisible() {
        errorMessage.should(Condition.hidden);
    }

    //очищает поля ввода
    public void clearFields() {
        amountField.clear();
        fromField.clear();
    }
}

