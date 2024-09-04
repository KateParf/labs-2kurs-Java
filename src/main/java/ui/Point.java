package ui;

public class Point {
    private double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getXstr() {
        return String.valueOf(this.x);
    }

    public boolean setXstr(String x) {
        try {
            this.x = Double.parseDouble(x);
            return true;
        } catch (Exception err) {
            // не меняем старое значение
            return false;
        }
    }

    public String getYstr() {
        return String.valueOf(this.y);
    }

    public boolean setYstr(String y) {
        try {
            this.y = Double.parseDouble(y);
            return true;
        } catch (Exception err) {
            // не меняем старое значение
            return false;
        }
    }

}
