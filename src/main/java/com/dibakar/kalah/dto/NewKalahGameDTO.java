package com.dibakar.kalah.dto;

/**
 * @author divakar721304@gmail.com
 */
public class NewKalahGameDTO {

    private int id;

    private String uri;

    public NewKalahGameDTO() { }

    public NewKalahGameDTO(int id, String uri) {
        this.id = id;
        this.uri = uri;
    }

    public int getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
