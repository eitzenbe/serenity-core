package net.serenitybdd.screenplay.playwright.integration;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.playwright.Target;
import net.serenitybdd.screenplay.playwright.abilities.BrowseTheWebWithPlaywright;
import net.serenitybdd.screenplay.playwright.interactions.Click;
import net.serenitybdd.screenplay.playwright.interactions.Enter;
import net.serenitybdd.screenplay.playwright.interactions.Open;
import net.serenitybdd.screenplay.playwright.questions.Attribute;
import net.serenitybdd.screenplay.playwright.questions.TheWebPage;
import net.serenitybdd.screenplay.playwright.questions.Visibility;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

@RunWith(SerenityRunner.class)
public class SimpleScreenplayPlaywrightTest {

    Actor daffy;

    @Before
    public void prepareActor() {
        daffy = Actor.named("Daffy").whoCan(BrowseTheWebWithPlaywright.usingTheDefaultConfiguration());
    }

    @Test
    public void simpleSearch() {
        daffy.attemptsTo(
            Open.url("https://www.duckduckgo.com"),
            Enter.theValue("Penguins").into("#search_form_input_homepage"),
            Click.on("#search_button_homepage")
        );
        daffy.should(
            seeThat(TheWebPage.title(), Matchers.containsString("Penguins at DuckDuckGo")),
            // Image from wikipedia
            seeThat(Attribute.of(".module__image", "href"), Matchers.containsStringIgnoringCase("penguin"))
        );
    }

    static class SearchPage {
        static final Target SEARCH_FIELD = Target.the("Search field").locatedBy("#search_form_input_homepage");
        static final Target SEARCH_BUTTON = Target.the("Search button").locatedBy("#search_button_homepage");
    }

    @Test
    public void searchUsingTargetSelectors() {
        daffy.attemptsTo(
            Open.url("https://www.duckduckgo.com"),
            Enter.theValue("Penguins").into(SearchPage.SEARCH_FIELD),
            Click.on(SearchPage.SEARCH_BUTTON)
        );
        daffy.should(
            seeThat(TheWebPage.title(), Matchers.containsString("Penguins at DuckDuckGo")),
            seeThat(Visibility.of(".header__logo"))
        );
    }
}

