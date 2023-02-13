package Entidades;

import java.awt.Color;

/**
 *
 * @author joaoan2
 */
public class Config {
    private Color color;
    private String path;
    private String  author;
    
    public Config(Color color, String path, String author) {
        this.color = color;
        this.path = path;
        this.author = author;
    }

    public Config() {
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return color + ";" + path + ";" + author;
    }
    
}
