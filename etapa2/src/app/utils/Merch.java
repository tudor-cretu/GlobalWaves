package app.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Merch {
    private String name;
    private String description;
    private int price;

    public Merch(final String name, final String description, final int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
