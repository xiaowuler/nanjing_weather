package com.nanjing.wContour.bean;


import com.nanjing.weather.domain.Stations;
import wContour.Global.PolyLine;
import wContour.Global.Polygon;


import java.util.List;

public class ContourResult<T> {
    public List<ValuePoint> valuePoints;
    public List<Polygon> spotPolygons;
    public List<PolyLine> contourPolylines;
    public List<LegendLevel> legendLevels;

    public List<LegendLevel> getLegendLevels() {
        return legendLevels;
    }

    public void setLegendLevels(List<LegendLevel> legendLevels) {
        this.legendLevels = legendLevels;
    }

    public double[] levelArray;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String time;
    public Object reserved3;

    public List<ValuePoint> getValuePoints() {
        return valuePoints;
    }

    public void setValuePoints(List<ValuePoint> valuePoints) {
        this.valuePoints = valuePoints;
    }

    public List<Polygon> getSpotPolygons() {
        return spotPolygons;
    }

    public void setSpotPolygons(List<Polygon> spotPolygons) {
        this.spotPolygons = spotPolygons;
    }

    public List<PolyLine> getContourPolylines() {
        return contourPolylines;
    }

    public void setContourPolylines(List<PolyLine> contourPolylines) {
        this.contourPolylines = contourPolylines;
    }

    public Object getReserved3() {
        return reserved3;
    }

    public void setReserved3(Object reserved3) {
        this.reserved3 = reserved3;
    }

    public double[] getLevelArray() {
        return levelArray;
    }

    public void setLevelArray(double[] levelArray) {
        this.levelArray = levelArray;
    }
}
