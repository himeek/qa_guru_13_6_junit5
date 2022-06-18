package test_myshows;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.util.Arrays.asList;

public class MainPageSearchTest {


    @ValueSource(strings = {"Sense8", "Hannibal"})
    @ParameterizedTest(name = "При поиске сериала {0} в результатх отображается текст {0}")
    void myShowsTestWithValue(String testData) {
        Selenide.open("https://myshows.me/");
        $(".Search-input").setValue(testData);
        $(".Search-submit").click();
        $$(".ShowCol").find(text(testData)).shouldBe(visible);
    }

    @CsvSource(value = {
            "Sense8, Восьмое чувство",
            "Hannibal, Ганнибал"
    })
    @ParameterizedTest(name = "При поиске сериала {0} в результатх отображается текст {1}")
    void myShowsTestWithCSV(String searchData, String expectedResult) {
        Selenide.open("https://myshows.me/");
        $(".Search-input").setValue(searchData);
        $(".Search-submit").click();
        $$(".ShowCol").find(text(expectedResult)).shouldBe(visible);
    }

    @CsvFileSource(resources = "testFiles/1.csv")
    @ParameterizedTest(name = "При поиске сериала {0} в результатх отображается текст {1}")
    void myShowsTestWithCSVFile(String searchData, String expectedResult) {
        Selenide.open("https://myshows.me/");
        $(".Search-input").setValue(searchData);
        $(".Search-submit").click();
        $$(".ShowCol").find(text(expectedResult)).shouldBe(visible);
    }

    static Stream<Arguments> myShowsTestWithMethod() {
        return Stream.of(
                Arguments.of("Sense8", asList("Sense8")),
                Arguments.of("Hannibal", asList(
                        "Hannibal",
                        "Why? With Hannibal Buress",
                        "On Hannibal's Trail",
                        "Hannibal Buress Live From Chicago"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "При поиске сериала {0} в результатх отображается текст {1}")
    void myShowsTestWithMethod(String searchData, List<String> expectedResult) {
        Selenide.open("https://myshows.me/");
        $(".Search-input").setValue(searchData);
        $(".Search-submit").click();
        $$(".ShowCol").shouldHave(CollectionCondition.texts(expectedResult));
    }

    @EnumSource(Rating.class)
    @ParameterizedTest
    void myShowsTestWithEnum(Rating rating) {
        Selenide.open("https://myshows.me/");
        $(".Search-input").setValue(rating.desc);
        $(".Search-submit").click();
        $$(".ShowCol").find(text(rating.desc)).shouldBe(visible);
    }
}
