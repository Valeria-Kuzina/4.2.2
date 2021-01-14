package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.By.cssSelector;

import static ru.netology.web.DataGenerate.*;

public class FormTest {
    final String city = getCity();
    final String date = getRelevantDate(6);
//    final String otherDate = getRelevantDate(10);
    final String invalidDate = getIrrelevantDate();
    final String name = getName();
    final String phone = getPhone();
    final String invalidPhone = getInvalidPhone();

    @Test
    void shouldSubmitRequest() {
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=city] input")).sendKeys(city);
        form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
        form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
        form.$(cssSelector("[name=name]")).sendKeys(name);
        form.$(cssSelector("[name=phone]")).sendKeys(phone);
        form.$(cssSelector("[data-test-id=agreement]")).click();
        form.$(byText("Забронировать")).click();
        $(cssSelector(".notification__title")).waitUntil(Condition.visible, 15000);
    }

//    @Test
//    void shouldGiveNewDateWhenSameRequest() {
//        open("http://localhost:9999");
//        SelenideElement form = $("[action]");
//        form.$(cssSelector("[data-test-id=city] input")).sendKeys(city);
//        form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
//        form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
//        form.$(cssSelector("[name=name]")).sendKeys(name);
//        form.$(cssSelector("[name=phone]")).sendKeys(phone);
//        form.$(cssSelector("[data-test-id=agreement]")).click();
//        form.$(byText("Забронировать")).click();
//        $(byText("Успешно!")).waitUntil(Condition.visible, 15000);
//
//        open("http://localhost:9999");
//        form.$(cssSelector("[data-test-id=city] input")).sendKeys(city);
//        form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
//        form.$(cssSelector("[data-test-id=date] input")).sendKeys(otherDate);
//        form.$(cssSelector("[name=name]")).sendKeys(name);
//        form.$(cssSelector("[name=phone]")).sendKeys(phone);
//        form.$(cssSelector("[data-test-id=agreement]")).click();
//        form.$(byText("Забронировать")).click();
//        $(byText("Успешно!")).waitUntil(Condition.visible, 15000);
//    }

    @Test
    void shouldNotGiveNewDateWhenInvalidDate() {
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=city] input")).sendKeys(city);
        form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
        form.$(cssSelector("[data-test-id=date] input")).sendKeys(invalidDate);
        form.$(cssSelector("[name=name]")).sendKeys(name);
        form.$(cssSelector("[name=phone]")).sendKeys(phone);
        form.$(cssSelector("[data-test-id=agreement]")).click();
        form.$(byText("Забронировать")).click();
        $(byText("Заказ на выбранную дату невозможен")).waitUntil(Condition.visible, 15000);
    }

    @Test
    void shouldNotGiveWhenInvalidPhone() {
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=city] input")).sendKeys(city);
        form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
        form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
        form.$(cssSelector("[name=name]")).sendKeys(name);
        form.$(cssSelector("[name=phone]")).sendKeys(invalidPhone);
        form.$(cssSelector("[data-test-id=agreement]")).click();
        form.$(byText("Забронировать")).click();
        $(withText("Телефон указан неверно. Должно быть 11 цифр")).waitUntil(Condition.visible, 15000);
    }
}
