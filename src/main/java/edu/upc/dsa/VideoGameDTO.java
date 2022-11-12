package edu.upc.dsa;

public class VideoGameDTO {
    public String id;
    public String description;
    public int levelsAmount;
    public VideoGameDTO(String id, String description, int levelsAmount){
        this.id = id;
        this.description = description;
        this.levelsAmount = levelsAmount;
    }
}
