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
    final String City = getCity();
    final String Date = getRelevantDate(6);
    final String OtherDate = getRelevantDate(10);
    final String InvalidDate = getIrrelevantDate();
    final String Name = getName();
    final String Phone = getPhone();

    @Test
    void shouldSubmitRequest() {
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=city] input")).sendKeys(City);
        form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
        form.$(cssSelector("[data-test-id=date] input")).sendKeys(Date);
        form.$(cssSelector("[name=name]")).sendKeys(Name);
        form.$(cssSelector("[name=phone]")).sendKeys(Phone);
        form.$(cssSelector("[data-test-id=agreement]")).click();
        form.$(byText("Забронировать")).click();
        $(cssSelector(".notification__title")).waitUntil(Condition.visible, 15000);
    }

    @Test
    void shouldGiveNewDateWhenSameRequest() {
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=city] input")).sendKeys(City);
        form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
        form.$(cssSelector("[data-test-id=date] input")).sendKeys(Date);
        form.$(cssSelector("[name=name]")).sendKeys(Name);
        form.$(cssSelector("[name=phone]")).sendKeys(Phone);
        form.$(cssSelector("[data-test-id=agreement]")).click();
        form.$(byText("Забронировать")).click();
        $(byText("Успешно!")).waitUntil(Condition.visible, 15000);

        open("http://localhost:9999");
        form.$(cssSelector("[data-test-id=city] input")).sendKeys(City);
        form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
        form.$(cssSelector("[data-test-id=date] input")).sendKeys(OtherDate);
        form.$(cssSelector("[name=name]")).sendKeys(Name);
        form.$(cssSelector("[name=phone]")).sendKeys(Phone);
        form.$(cssSelector("[data-test-id=agreement]")).click();
        form.$(byText("Забронировать")).click();
        $(byText("Успешно!")).waitUntil(Condition.visible, 15000);
    }

    @Test
    void shouldNotGiveNewDateWhenInvalidDate() {
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=city] input")).sendKeys(City);
        form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
        form.$(cssSelector("[data-test-id=date] input")).sendKeys(Date);
        form.$(cssSelector("[name=name]")).sendKeys(Name);
        form.$(cssSelector("[name=phone]")).sendKeys(Phone);
        form.$(cssSelector("[data-test-id=agreement]")).click();
        form.$(byText("Забронировать")).click();
        $(byText("Успешно!")).waitUntil(Condition.visible, 15000);

        open("http://localhost:9999");
        form.$(cssSelector("[data-test-id=city] input")).sendKeys(City);
        form.$(cssSelector("[data-test-id=date] input")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
        form.$(cssSelector("[data-test-id=date] input")).sendKeys(InvalidDate);
        form.$(cssSelector("[name=name]")).sendKeys(Name);
        form.$(cssSelector("[name=phone]")).sendKeys(Phone);
        form.$(cssSelector("[data-test-id=agreement]")).click();
        form.$(byText("Забронировать")).click();
        $(byText("Заказ на выбранную дату невозможен")).waitUntil(Condition.visible, 15000);
    }
}
