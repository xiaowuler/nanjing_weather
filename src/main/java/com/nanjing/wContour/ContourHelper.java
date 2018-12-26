package com.nanjing.wContour;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nanjing.wContour.bean.ContourResult;
import com.nanjing.wContour.bean.LegendLevel;
import com.nanjing.wContour.bean.ValuePoint;
import org.apache.commons.io.FileUtils;
import wContour.Contour;
import wContour.Global.Border;
import wContour.Global.PointD;
import wContour.Global.PolyLine;
import wContour.Global.Polygon;
import wContour.Interpolate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ContourHelper {
    public static double MinX = 118.359192;
    public static double MaxX = 119.237312;
    public static double MinY = 31.230221;
    public static double MaxY = 32.611660;
    List<List<PointD>> _clipLines = new ArrayList<>();

    public ContourHelper(String boundFile) {
        if (_clipLines.size() == 0)
            InitBound(boundFile);
    }

    public static List<double[]> CreateGridXY_Delt(double Xlb, double Ylb, double Xrt, double Yrt, double XDelt, double YDelt, double[] var12, double[] var13) {
        int var5 = (int) ((Xrt - Xlb) / XDelt + 1.0D);
        int Yrt1 = (int) ((Yrt - Ylb) / YDelt + 1.0D);
        var12 = new double[var5];
        var13 = new double[Yrt1];

        int Xrt1;
        for (Xrt1 = 0; Xrt1 < var5; ++Xrt1) {
            var12[Xrt1] = Xlb + (double) Xrt1 * XDelt;
        }

        for (Xrt1 = 0; Xrt1 < Yrt1; ++Xrt1) {
            var13[Xrt1] = Ylb + (double) Xrt1 * YDelt;
        }
        List<double[]> list = new ArrayList<>();
        list.add(var12);
        list.add(var13);
        return list;
    }

    private void InitBound(String boundFile) {
        try {
            String s = FileUtils.readFileToString(new File(boundFile));
            //System.out.println(s);
            JSONObject object = JSON.parseObject(s);
            //JSONObject object = JSONObject.parseObject(s);
            JSONArray features = object.getJSONArray("features");
            for (int i = 0; i < features.size(); i++) {
                JSONObject jsonObject = features.getJSONObject(i);
                JSONObject geometry = jsonObject.getJSONObject("geometry");
                JSONArray coordinates = geometry.getJSONArray("coordinates");
                for (int j = 0; j < coordinates.size(); j++) {
                    List<PointD> line = new ArrayList<PointD>();
                    JSONArray jsonArray = coordinates.getJSONArray(j);
                    for (int k = 0; k < jsonArray.size(); k++) {
                        JSONArray jsonArray1 = jsonArray.getJSONArray(k);
                        PointD point = new PointD(Double.parseDouble(jsonArray1.get(0).toString()), Double.parseDouble(jsonArray1.get(1).toString()));
                        line.add(point);
                    }
                    _clipLines.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ContourResult Calc(List<ValuePoint> points, List<LegendLevel> levels, int numberOfNearestNeighbors, double missData) {

        double[][] _gridData;
        double[][] _discreteData;
        double[] _X = {};
        double[] _Y = {};
        double[] _CValues;
        double _undefData = -9999.0;
        List<Border> _borders;
        List<PolyLine> _contourLines;
        List<PolyLine> _clipContourLines;
        List<Polygon> _contourPolygons;
        List<Polygon> _clipContourPolygons;
        try {
            _discreteData = new double[3][points.size()];
            for (int i = 0; i < points.size(); i++) {
                _discreteData[0][i] = points.get(i).getLongitude();
                _discreteData[1][i] = points.get(i).getLatitude();
                _discreteData[2][i] = points.get(i).getValue();
            }

            List<double[]> list = CreateGridXY_Delt(MinX, MinY, MaxX, MaxY, 0.01, 0.01, _X, _Y);

            _X = list.get(0);
            _Y = list.get(1);
            _gridData = Interpolate.Interpolation_IDW_Neighbor(_discreteData, _X, _Y, numberOfNearestNeighbors, _undefData);

            double[] values = new double[levels.size() + 1];
            for (int i = 0; i < levels.size(); i++) {
                if (i == 0) {
                    values[i] = levels.get(i).getBeginValue();
                }
                values[i + 1] = levels.get(i).getEndValue();
            }
            _CValues = values;
            int nc = _CValues.length;
            int[][] S1 = new int[_gridData.length][_gridData[0].length];
            _borders = Contour.tracingBorders(_gridData, _X, _Y, S1, _undefData);
            _contourLines = Contour.tracingContourLines(_gridData, _X, _Y, nc, _CValues, _undefData, _borders, S1);
            _contourLines = Contour.smoothLines(_contourLines);

            _clipContourLines = new ArrayList<>();
            for (List<PointD> cLine : _clipLines) {
                _clipContourLines.addAll(Contour.clipPolylines(_contourLines, cLine));
            }

            _contourPolygons = Contour.tracingPolygons(_gridData, _contourLines, _borders, _CValues);
            _clipContourPolygons = new ArrayList<>();
            for (List<PointD> cLine : _clipLines) {
                _clipContourPolygons.addAll(Contour.clipPolygons(_contourPolygons, cLine));
            }

            ContourResult contourResult = new ContourResult();
            contourResult.setSpotPolygons(_clipContourPolygons);
            contourResult.setContourPolylines(_clipContourLines);
            contourResult.setLegendLevels(levels);
            contourResult.setValuePoints(points);
            return contourResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
