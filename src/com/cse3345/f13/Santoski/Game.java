package com.cse3345.f13.Santoski;


public class Game {
	private String name;
	private String url;
	private String deck;
	private String description;
	private String image;
	
	
	public Game(String name, String url, String deck, String description, String image) {
        this.name = name;
        this.url = url;
        this.deck = deck;
        this.description = description;
        this.image = image;
}

public String getName() {
    return name;
}

public String getUrl() {
    return url;
}

public String getDeck() {
    return deck;
}

public String getDescription() {
    return description;
}

public String getImage() {
    return image;
}
}
