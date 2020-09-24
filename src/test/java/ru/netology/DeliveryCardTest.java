package ru.netology;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.netology.DeliveryDataInfo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class DeliveryCardTest {

    private Faker faker;

    @BeforeEach
    void setUpAll() {
        faker = new Faker(new Locale("ru"));
    }

    @Test
    void shouldCorrectForm() {
        open("http://localhost:9999");
        String city = faker.address().cityName();
        $("[data-test-id='city'] input").setValue(city);

        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate dateOfDelivery = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = formatter.format(dateOfDelivery);
        $("[data-test-id='date'] input").setValue(date);

        String name = faker.name().fullName();
        $("[data-test-id='name'] input").setValue(name);

        String phone = faker.numerify("+7##########");
        $("[data-test-id='phone'] input").setValue(phone);


        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(byText("Успешно!")).waitUntil(visible, 11000);
        $("[data-test-id=success-notification] .notification__content").shouldHave(text("Встреча успешно запланирована на "+date));
        $("[data-test-id=success-notification] button").click();

        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        LocalDate newdateOfDelivery = LocalDate.now().plusDays(6);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String newdate = formatter1.format(newdateOfDelivery);
        $("[data-test-id='date'] input").setValue(newdate);

        $$("button").find(exactText("Запланировать")).click();

        $("[data-test-id='replan-notification'] .notification__content").shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $$("[data-test-id='replan-notification'] button").find(exactText("Перепланировать")).click();

        $("[data-test-id=success-notification] .notification__content").shouldHave(text("Встреча успешно запланирована на "+newdate));
    }
}
