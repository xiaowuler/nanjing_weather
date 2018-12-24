package com.nanjing.wContour.bean;

public class LegendLevel {
    public Integer Level_id;
    public double BeginValue;
    public double EndValue;
    public String Color;
    public String Text;
    public String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLevel_id() {
        return Level_id;
    }

    public void setLevel_id(Integer level_id) {
        Level_id = level_id;
    }

    public double getBeginValue() {
        return BeginValue;
    }

    public void setBeginValue(double beginValue) {
        BeginValue = beginValue;
    }

    public double getEndValue() {
        return EndValue;
    }

    public void setEndValue(double endValue) {
        EndValue = endValue;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }
}
