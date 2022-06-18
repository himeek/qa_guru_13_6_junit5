package test_myshows;

public enum Rating {
    FirstPlace("Sense8"),
    SecondPlace("Hannibal");

    public final String desc;

    Rating(String desc) {
        this.desc = desc;
    }
}
