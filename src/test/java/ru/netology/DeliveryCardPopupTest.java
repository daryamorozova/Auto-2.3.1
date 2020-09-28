package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.web.DataGenerator.*;

public class DeliveryCardPopupTest {
    private final String city = getRandomCity();
    private final String name = getRandomName();
    private final String phone = getRandomPhone();

    @BeforeEach
    void setUpAll() {
        open("http://localhost:9999");
    }

    @Test
    void shouldViewListOfCity() {
        $("[data-test-id='city'] input").setValue("Мо");
        $$(".menu-item .menu-item__control").find(exactText("Кемерово")).click();

        $("[data-test-id='city'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='city'] input").setValue("Мо");
        $$(".menu-item .menu-item__control").find(exactText("Майкоп")).click();

        $("[data-test-id='city'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='city'] input").setValue("Мо");
        $$(".menu-item .menu-item__control").find(exactText("Москва")).click();

        $("[data-test-id='city'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='city'] input").setValue("Мо");
        $$(".menu-item .menu-item__control").find(exactText("Симферополь")).click();

        $("[data-test-id='city'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='city'] input").setValue("Мо");
        $$(".menu-item .menu-item__control").find(exactText("Смоленск")).click();

        $("[data-test-id='city'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='city'] input").setValue("Мо");
        $$(".menu-item .menu-item__control").find(exactText("Тамбов")).click();
    }

    @Test
    void shouldSelectDateNextWeek() {
        LocalDate currentDate = LocalDate.now();
        LocalDate dateOfDelivery = LocalDate.now().plusDays(7);
        String date = dateOfDelivery.format(DateTimeFormatter.ofPattern("d"));
        $(".input__icon").click();
        if (dateOfDelivery.getMonthValue() - currentDate.getMonthValue() == 1) {
            $("[data-step='1']").click();
        }
        $$("td.calendar__day").find(exactText(date)).click();
    }


    @Test
    void shouldCorrectForm() {
        $("[data-test-id='city'] input").setValue(city);
        LocalDate currentDate = LocalDate.now();
        LocalDate dateOfDelivery = LocalDate.now().plusDays(7);
        String dateOfDelivery1 = dateOfDelivery.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String date = dateOfDelivery.format(DateTimeFormatter.ofPattern("d"));
        $(".input__icon").click();
        if (dateOfDelivery.getMonthValue() - currentDate.getMonthValue() > 1) {
            $("[data-step='1']").click();
        }
        $$("td.calendar__day").find(exactText(date)).click();
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='phone'] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(byText("Успешно!")).waitUntil(visible, 5000);
        $("[data-test-id=success-notification] .notification__content").shouldHave(text("Встреча успешно запланирована на "+dateOfDelivery1));
        $("[data-test-id=success-notification] button").click();
    }

}
