package Entidades;

import java.awt.Color;

/**
 *
 * @author joaoan2
 */
public class Config {
    private Color color;
    private String path;
    private String  Author;

    public Config(Color color, String path, String Author) {
        this.color = color;
        this.path = path;
        this.Author = Author;
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
        return Author;
    }

    public void setAuthor(String Author) {
        this.Author = Author;
    }

    @Override
    public String toString() {
        return color + ";" + path + ";" + Author;
    }
    
}
