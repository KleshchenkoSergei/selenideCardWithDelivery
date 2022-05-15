import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardWithDeliveryTest {

    @BeforeAll
    static void setUp() {
    }

    @BeforeEach
    void setUp2() {
        //headless webdriver mode
        Configuration.headless = true;
    }

    @AfterEach
    public void close() {
    }

    @Test
    public void shouldPositiveTest() {
        open("http://localhost:9999"); // open webpage
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue("Москва"); // input city

        //set actual date
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        String actualDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(actualDate);

        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Иванов Иван"); // input family and name
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+79990001122"); // input family and name
        $("[class=\"checkbox__text\"]").click(); //check agreement
        $$("button[class*=\"button\"] ").findBy(text("Забронировать")).click(); // press order button
        $("[data-test-id=\"notification\"]").waitUntil(visible, 15000); // wait notification
        String text = $("[data-test-id=\"notification\"] [class=\"notification__content\"]").getText();

        assertTrue(text.contains("Встреча успешно забронирована на"));
    }

    @Test
    public void shouldCityEmpty() {
        open("http://localhost:9999"); // open webpage
        //set actual date
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        String actualDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(actualDate);
        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Иванов Иван"); // input family and name
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+79990001122"); // input family and name
        $("[class=\"checkbox__text\"]").click(); //check agreement
        $$("button[class*=\"button\"] ").findBy(text("Забронировать")).click(); // press order button
        String text = $("[data-test-id=\"city\"] [class=\"input__sub\"]").getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    public void shouldCityLatin() {
        open("http://localhost:9999"); // open webpage
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue("Moscow"); // input city
        //set actual date
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        String actualDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(actualDate);
        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Иванов Иван"); // input family and name
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+79990001122"); // input family and name
        $("[class=\"checkbox__text\"]").click(); //check agreement
        $$("button[class*=\"button\"] ").findBy(text("Забронировать")).click(); // press order button
        String text = $("[data-test-id=\"city\"] [class=\"input__sub\"]").getText();
        assertEquals("Доставка в выбранный город недоступна", text.trim());
    }

    @Test
    public void shouldCityNumeric() {
        open("http://localhost:9999"); // open webpage
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue("Москва2"); // input city
        //set actual date
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        String actualDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(actualDate);
        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Иванов Иван"); // input family and name
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+79990001122"); // input family and name
        $("[class=\"checkbox__text\"]").click(); //check agreement
        $$("button[class*=\"button\"] ").findBy(text("Забронировать")).click(); // press order button
        String text = $("[data-test-id=\"city\"] [class=\"input__sub\"]").getText();
        assertEquals("Доставка в выбранный город недоступна", text.trim());
    }

    @Test
    public void shouldCityNoDelivery() {
        open("http://localhost:9999"); // open webpage
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue("Рубцовск"); // input city
        //set actual date
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        String actualDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(actualDate);
        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Иванов Иван"); // input family and name
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+79990001122"); // input family and name
        $("[class=\"checkbox__text\"]").click(); //check agreement
        $$("button[class*=\"button\"] ").findBy(text("Забронировать")).click(); // press order button
        String text = $("[data-test-id=\"city\"] [class=\"input__sub\"]").getText();
        assertEquals("Доставка в выбранный город недоступна", text.trim());
    }

    @Test
    public void shouldDateEmpty() {
        open("http://localhost:9999"); // open webpage
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue("Барнаул"); // input city
        //set actual date
        LocalDate date = LocalDate.now();
        date = date.minusDays(1);
        String actualDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Иванов Иван"); // input family and name
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+79990001122"); // input family and name
        $("[class=\"checkbox__text\"]").click(); //check agreement
        $$("button[class*=\"button\"] ").findBy(text("Забронировать")).click(); // press order button
        String text = $("[data-test-id=\"date\"] [class=\"input__sub\"]").getText();
        assertEquals("Неверно введена дата", text.trim());
    }

    @Test
    public void shouldDateYesterday() {
        open("http://localhost:9999"); // open webpage
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue("Барнаул"); // input city
        //set actual date
        LocalDate date = LocalDate.now();
        date = date.minusDays(1);
        String actualDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(actualDate);
        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Иванов Иван"); // input family and name
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+79990001122"); // input family and name
        $("[class=\"checkbox__text\"]").click(); //check agreement
        $$("button[class*=\"button\"] ").findBy(text("Забронировать")).click(); // press order button
        String text = $("[data-test-id=\"date\"] [class=\"input__sub\"]").getText();
        assertEquals("Заказ на выбранную дату невозможен", text.trim());
    }

    @Test
    public void shouldDateToday() {
        open("http://localhost:9999"); // open webpage
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue("Барнаул"); // input city
        //set actual date
        LocalDate date = LocalDate.now();
        String actualDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(actualDate);
        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Иванов Иван"); // input family and name
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+79990001122"); // input family and name
        $("[class=\"checkbox__text\"]").click(); //check agreement
        $$("button[class*=\"button\"] ").findBy(text("Забронировать")).click(); // press order button
        String text = $("[data-test-id=\"date\"] [class=\"input__sub\"]").getText();
        assertEquals("Заказ на выбранную дату невозможен", text.trim());
    }

    @Test
    public void shouldDateTomorrow() {
        open("http://localhost:9999"); // open webpage
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue("Барнаул"); // input city
        //set actual date
        LocalDate date = LocalDate.now();
        date = date.plusDays(1);
        String actualDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(actualDate);
        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Иванов Иван"); // input family and name
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+79990001122"); // input family and name
        $("[class=\"checkbox__text\"]").click(); //check agreement
        $$("button[class*=\"button\"] ").findBy(text("Забронировать")).click(); // press order button
        String text = $("[data-test-id=\"date\"] [class=\"input__sub\"]").getText();
        assertEquals("Заказ на выбранную дату невозможен", text.trim());
    }

    @Test
    public void shouldDatePlus2() {
        open("http://localhost:9999"); // open webpage
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue("Барнаул"); // input city
        //set actual date
        LocalDate date = LocalDate.now();
        date = date.plusDays(2);
        String actualDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(actualDate);
        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Иванов Иван"); // input family and name
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+79990001122"); // input family and name
        $("[class=\"checkbox__text\"]").click(); //check agreement
        $$("button[class*=\"button\"] ").findBy(text("Забронировать")).click(); // press order button
        String text = $("[data-test-id=\"date\"] [class=\"input__sub\"]").getText();
        assertEquals("Заказ на выбранную дату невозможен", text.trim());
    }

    @Test
    public void shouldDatePlus4() {
        open("http://localhost:9999"); // open webpage
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue("Барнаул"); // input city
        //set actual date
        LocalDate date = LocalDate.now();
        date = date.plusDays(4);
        String actualDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(actualDate);
        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Иванов Иван"); // input family and name
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+79990001122"); // input family and name
        $("[class=\"checkbox__text\"]").click(); //check agreement
        $$("button[class*=\"button\"] ").findBy(text("Забронировать")).click(); // press order button
        $("[data-test-id=\"notification\"]").waitUntil(visible, 15000); // wait notification
        String text = $("[data-test-id=\"notification\"] [class=\"notification__content\"]").getText();
        assertTrue(text.contains("Встреча успешно забронирована на"));
    }

    @Test
    public void shouldNameEmpty() {
        open("http://localhost:9999"); // open webpage
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue("Барнаул"); // input city
        //set actual date
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        String actualDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(actualDate);
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+79990001122"); // input family and name
        $("[class=\"checkbox__text\"]").click(); //check agreement
        $$("button[class*=\"button\"] ").findBy(text("Забронировать")).click(); // press order button
        String text = $("[data-test-id=\"name\"] [class=\"input__sub\"]").getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    public void shouldNameLatin() {
        open("http://localhost:9999"); // open webpage
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue("Барнаул"); // input city
        //set actual date
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        String actualDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(actualDate);
        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Ivanov Ivan"); // input family and name
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+79990001122"); // input family and name
        $("[class=\"checkbox__text\"]").click(); //check agreement
        $$("button[class*=\"button\"] ").findBy(text("Забронировать")).click(); // press order button
        String text = $("[data-test-id=\"name\"] [class=\"input__sub\"]").getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void shouldNameNumeric() {
        open("http://localhost:9999"); // open webpage
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue("Барнаул"); // input city
        //set actual date
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        String actualDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(actualDate);
        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Иванов Иван35"); // input family and name
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+79990001122"); // input family and name
        $("[class=\"checkbox__text\"]").click(); //check agreement
        $$("button[class*=\"button\"] ").findBy(text("Забронировать")).click(); // press order button
        String text = $("[data-test-id=\"name\"] [class=\"input__sub\"]").getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void shouldNameSpecialSymbol() {
        open("http://localhost:9999"); // open webpage
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue("Барнаул"); // input city
        //set actual date
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        String actualDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(actualDate);
        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Иванов Иван@"); // input family and name
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+79990001122"); // input family and name
        $("[class=\"checkbox__text\"]").click(); //check agreement
        $$("button[class*=\"button\"] ").findBy(text("Забронировать")).click(); // press order button
        String text = $("[data-test-id=\"name\"] [class=\"input__sub\"]").getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void shouldPhoneEmpty() {
        open("http://localhost:9999"); // open webpage
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue("Барнаул"); // input city
        //set actual date
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        String actualDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(actualDate);
        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Иванов Иван"); // input family and name
        $("[class=\"checkbox__text\"]").click(); //check agreement
        $$("button[class*=\"button\"] ").findBy(text("Забронировать")).click(); // press order button
        String text = $("[data-test-id=\"phone\"] [class=\"input__sub\"]").getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    public void shouldPhoneLessPlus() {
        open("http://localhost:9999"); // open webpage
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue("Барнаул"); // input city
        //set actual date
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        String actualDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(actualDate);
        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Иванов Иван"); // input family and name
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("79990001122"); // input family and name
        $("[class=\"checkbox__text\"]").click(); //check agreement
        $$("button[class*=\"button\"] ").findBy(text("Забронировать")).click(); // press order button
        String text = $("[data-test-id=\"phone\"] [class=\"input__sub\"]").getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldPhoneLessNumber() {
        open("http://localhost:9999"); // open webpage
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue("Барнаул"); // input city
        //set actual date
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        String actualDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(actualDate);
        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Иванов Иван"); // input family and name
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+7999000112"); // input family and name
        $("[class=\"checkbox__text\"]").click(); //check agreement
        $$("button[class*=\"button\"] ").findBy(text("Забронировать")).click(); // press order button
        String text = $("[data-test-id=\"phone\"] [class=\"input__sub\"]").getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldPhoneMoreNumber() {
        open("http://localhost:9999"); // open webpage
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue("Барнаул"); // input city
        //set actual date
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        String actualDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(actualDate);
        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Иванов Иван"); // input family and name
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+799900011200"); // input family and name
        $("[class=\"checkbox__text\"]").click(); //check agreement
        $$("button[class*=\"button\"] ").findBy(text("Забронировать")).click(); // press order button
        String text = $("[data-test-id=\"phone\"] [class=\"input__sub\"]").getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldNoAgreement() {
        open("http://localhost:9999"); // open webpage
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue("Барнаул"); // input city
        //set actual date
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        String actualDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(actualDate);
        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Иванов Иван"); // input family and name
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+79990001120"); // input family and name
        $$("button[class*=\"button\"] ").findBy(text("Забронировать")).click(); // press order button
        String text = $("[class*=\"input_invalid\"][data-test-id=\"agreement\"] [role=\"presentation\"]").getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных", text.trim());
    }

    @Test
    public void shouldCityDropDownList() {
        open("http://localhost:9999"); // open webpage
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue("Мо"); // input city
        $$("[class=\"popup__content\"] [class=\"menu-item__control\"]").findBy(text("Москва")).click();
        //set actual date
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        String actualDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [placeholder=\"Дата встречи\"]").doubleClick().sendKeys(actualDate);

        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Иванов Иван"); // input family and name
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+79990001122"); // input family and name
        $("[class=\"checkbox__text\"]").click(); //check agreement
        $$("button[class*=\"button\"] ").findBy(text("Забронировать")).click(); // press order button
        $("[data-test-id=\"notification\"]").waitUntil(visible, 15000); // wait notification
        String text = $("[data-test-id=\"notification\"] [class=\"notification__content\"]").getText();

        assertTrue(text.contains("Встреча успешно забронирована на"));
    }

    @Test
    public void shouldDateDropDownList() {
        open("http://localhost:9999"); // open webpage
        $("[data-test-id=\"city\"] [placeholder=\"Город\"]").setValue("Москва"); // input city

//        set actual date
        LocalDate date = LocalDate.now();
        int lengthOfMonth = date.lengthOfMonth();
        int increment = 7; // increment of date for test
        int actualDate = date.getDayOfMonth() + increment;
        date = date.plusDays(increment);
        int dayOfMonth = date.getDayOfMonth();

        $("[data-test-id=\"date\"] [class=\"input__icon\"]").click();
        if (actualDate > lengthOfMonth) {
            dayOfMonth = actualDate - lengthOfMonth;
            $("[class=\"calendar__arrow calendar__arrow_direction_right\"]").click();
        }
        $$("[class=\"calendar__layout\"] [role=\"gridcell\"]").findBy(text(String.valueOf(dayOfMonth))).click();
        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Иванов Иван"); // input family and name
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+79990001122"); // input family and name
        $("[class=\"checkbox__text\"]").click(); //check agreement
        $$("button[class*=\"button\"] ").findBy(text("Забронировать")).click(); // press order button
        $("[data-test-id=\"notification\"]").waitUntil(visible, 15000); // wait notification
        String text = $("[data-test-id=\"notification\"] [class=\"notification__content\"]").getText();

        assertTrue(text.contains("Встреча успешно забронирована на"));
    }
}
