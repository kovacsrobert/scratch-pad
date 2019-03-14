package quartz;

import java.io.Serializable;

public class HelloJobContext implements Serializable {

    private int greetingsCounter;
    private String guestName;

    public HelloJobContext() {}

    public HelloJobContext(int greetingsCounter, String guestName) {
        this.greetingsCounter = greetingsCounter;
        this.guestName = guestName;
    }

    public void increaseCounter() {
        ++greetingsCounter;
    }

    public int getGreetingsCounter() {
        return greetingsCounter;
    }

    public void setGreetingsCounter(int greetingsCounter) {
        this.greetingsCounter = greetingsCounter;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }
}
