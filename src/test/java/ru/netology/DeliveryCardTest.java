package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.web.DataGenerator.*;

public class DeliveryCardTest {
    private final String city = getRandomCity();
    private final String dateOfDelivery = getCorrectDate(3);
    private final String dateOfDeliveryAnother = getCorrectDate(7);
    private final String notCorrectDate = getNotCorrectDate();
    private final String name = getRandomName();
    private final String phone = getRandomPhone();

    @BeforeEach
    void setUpAll() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
    }

    @Test
    void shouldCorrectForm() {
        $("[data-test-id='date'] input").setValue(dateOfDelivery);
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='phone'] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(byText("Успешно!")).waitUntil(visible, 11000); // ???
        $("[data-test-id=success-notification] .notification__content").shouldHave(text("Встреча успешно запланирована на "+dateOfDelivery));
        $("[data-test-id=success-notification] button").click();

        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(dateOfDeliveryAnother);
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='replan-notification'] .notification__content").shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $$("[data-test-id='replan-notification'] button").find(exactText("Перепланировать")).click();

        $("[data-test-id=success-notification] .notification__content").shouldHave(text("Встреча успешно запланирована на "+dateOfDeliveryAnother));
    }
}
