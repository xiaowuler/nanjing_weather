var MapInfo = function () {

    this.Map = null;
    this.borders = null;
    this.layer = null;
    this.sitePoint = null;
    this.ContourLayer = null;
    this.plotValue = null;
    this.basePoltDirNameValue = null;
    this.encryptionPointNameValue = null;
    this.basePlotValueValue = null;
    this.encryPlotValueValue = null;

    this.Startup = function () {
        this.GetProBorder();
        this.GetRegionName();
    }

    this.CreateEasyMap = function () {
        /*this.Map = L.map("map", {
            center: [31.70247, 118.11951],
            zoom: 9.2,
            zoomControl: false
        });*/
        this.Map = L.map("map", {
            center: [32.00575, 119.26758],
            zoom: 9.2,
            zoomControl: false
        });
        var layer = L.tileLayer.chinaProvider('Geoq.Normal.Gray', {
            maxZoom: 18,
            minZoom: 5
        });
        this.Map.addLayer(layer);
    }

    this.CreateMap = function () {
        /*var layerMap = L.tileLayer.chinaProvider('Geoq.Normal.PurplishBlue', {
            maxZoom: 18,
            minZoom: 5
        });
        var normal = L.layerGroup([layerMap])
        this.Map = L.map("map", {
            center: [31.70247, 118.11951],
            zoom: 9.2,
            layer:[normal],
            zoomControl: false
        });*/
        this.Map = L.map("map", {
            center: [32.00575, 119.26758],
            zoom: 9.2,
            zoomControl: false
        });
        var layer = L.tileLayer.chinaProvider('Geoq.Normal.PurplishBlue', {
            maxZoom: 18,
            minZoom: 5
        });
        this.Map.addLayer(layer);
        //this.Map.addLayer(layerMap);
    }

    this.GetProBorder = function () {
        $.getJSON("json/nanjing-districts.json", function (data) {
            this.borders = L.geoJson(data, {
                style: {
                    weight: 0.5,
                    opacity: 0.5,
                    color: '#ffffff',
                    fillColor: '#063E64',
                    fillOpacity: 0.7
                }
            })
            this.Map.addLayer(this.borders);
        }.bind(this));
    }

    this.GetRegionName = function () {
        $.getJSON("json/nanjingprovice.json", function (labels) {
            this.features = new L.FeatureGroup();
            $(labels).each(function (index, label) {
                this.features.addLayer(L.marker([label.Latitude, label.Longitude], {
                    icon: L.divIcon({
                        className: 'district-name-label',
                        html: label.Name
                    })
                }));
            }.bind(this));
            this.Map.addLayer(this.features);
        }.bind(this));
    }

    this.CreateSpotLayer = function (polygons, levels) {
        if (this.layer != undefined) {
            this.Map.removeLayer(this.layer);
        }
        // Add feature group
        this.layer = new L.FeatureGroup();
        // Add polygons
        for (var i = 0; i < polygons.length; i++) {
            // Get ring
            var ring = [];
            for (var j = 0; j < polygons[i].OutLine.PointList.length; j++)
                ring.push([polygons[i].OutLine.PointList[j].Y, polygons[i].OutLine.PointList[j].X]);

            // Push the ring
            var polygon = [];
            polygon.push(ring);

            // For holes
            if (polygons[i].HasHoles) {
                for (var x = 0; x < polygons[i].HoleLines.length; x++) {
                    if (!polygons[i].IsClockWise)
                        polygons[i].HoleLines[x].PointList = polygons[i].HoleLines[x].PointList.reverse();

                    // Get points of hole
                    var hole = [];
                    for (var y = 0; y < polygons[i].HoleLines[x].PointList.length; y++)
                        hole.push([polygons[i].HoleLines[x].PointList[y].Y, polygons[i].HoleLines[x].PointList[y].X]);

                    // Push the hole
                    polygon.push(hole);
                }
            }
            // Add polygon
            this.layer.addLayer(L.polygon(polygon).setStyle({
                weight: 0,
                opacity: 0.5,
                fillOpacity: 0.5,
                fillColor: this.GetFillColor(polygons[i].IsHighCenter, polygons[i].HighValue, levels)
            }));
        }
        this.Map.addLayer(this.layer);
    };

    this.GetFillColor = function (IsHighCenter, value, levels) {
        var color = levels[levels.length - 1];
        for (var i = 0; i < levels.length; i++) {
            if (value < levels[i].EndValue) {
                color = levels[i].Color;
                break;
            }
        }
        return 'rgb(' + color + ')';
    };

    //基本站的站名
    this.basePoltDirName = function (labels) {
        if (this.basePoltDirNameValue != undefined) {
            this.Map.removeLayer(this.basePoltDirNameValue);
        }

        //var windBarLayer = new L.FeatureGroup();
        this.basePoltDirNameValue = new L.FeatureGroup();
        $(labels).each(function (index, label) {
            if (label.id.substring(0, 2) == '58') {
                this.basePoltDirNameValue.addLayer(L.marker([label.latitude, label.longitude], {
                    icon: L.divIcon({
                        className: 'name-label text-shadow',
                        html: label.name
                    })
                }));
                /*this.sitePoint.addLayer(L.marker([label.latitude, label.longitude], {
                    icon: L.divIcon({
                        className: 'value-label text-shadow',
                        html: parseFloat(label.value)
                    })
                }));*/
                this.basePoltDirNameValue.addLayer(L.circleMarker([label.latitude, label.longitude], {
                    opacity: 1,
                    weight: 0.5,
                    color: 'black',
                    fillColor: '#fff',
                    fillOpacity: 0.5,
                    radius: 2.4
                }))
            }

        }.bind(this));
        this.Map.addLayer(this.basePoltDirNameValue);
    }

    //加密站的站名
    this.encryptionPointName = function (labels) {
        if (this.encryptionPointNameValue != undefined) {
            this.Map.removeLayer(this.encryptionPointNameValue);
        }

        //var windBarLayer = new L.FeatureGroup();
        this.encryptionPointNameValue = new L.FeatureGroup();
        $(labels).each(function (index, label) {

            if (label.id.substring(0, 2) != '58') {
                this.encryptionPointNameValue.addLayer(L.marker([label.latitude, label.longitude], {
                    icon: L.divIcon({
                        className: 'name-label text-shadow',
                        html: label.name
                    })
                }));
                /*this.sitePoint.addLayer(L.marker([label.latitude, label.longitude], {
                    icon: L.divIcon({
                        className: 'value-label text-shadow',
                        html: parseFloat(label.value)
                    })
                }));*/
                this.encryptionPointNameValue.addLayer(L.circleMarker([label.latitude, label.longitude], {
                    opacity: 1,
                    weight: 0.5,
                    color: 'black',
                    fillColor: '#fff',
                    fillOpacity: 0.5,
                    radius: 2.4
                }))
            }

        }.bind(this));
        this.Map.addLayer(this.encryptionPointNameValue);
    }

    //基本站的站值
    this.basePlotValue = function (labels) {
        if (this.basePlotValueValue != undefined) {
            this.Map.removeLayer(this.basePlotValueValue);
        }
        this.basePlotValueValue = new L.FeatureGroup();
        $(labels).each(function (index, label) {
            //if (label.value > 0) {
                // Add label
                if (label.id.substring(0, 2) == '58') {
                    this.basePlotValueValue.addLayer(L.marker([label.latitude, label.longitude], {
                        icon: L.divIcon({
                            className: 'value-label text-shadow',
                            //html: parseFloat(label.value)
                            html: this.returnPlotValueHtml(label.typeRegions)
                        })
                    }));

                    $(label.typeRegions).each(function (index,typeRegion) {
                        if (typeRegion.instantDirection != null) {
                            this.basePlotValueValue.addLayer(L.marker([label.latitude, label.longitude], {
                                icon: L.WindBarb.icon({
                                    lat: 40,
                                    deg: typeRegion.instantDirection,
                                    speed: label.value,
                                    pointRadius: 5,
                                    strokeWidth: 1,
                                    strokeLength: 15
                                })
                            }))
                        }
                    }.bind(this));

                    this.basePlotValueValue.addLayer(L.circleMarker([label.latitude, label.longitude], {
                        opacity: 1,
                        weight: 0.5,
                        color: 'black',
                        fillColor: '#fb1c15',
                        fillOpacity: 0.5,
                        radius: 2.4
                    }))
                }
            //}
        }.bind(this))

        this.Map.addLayer(this.basePlotValueValue);
    }

    //JM站的站值
    this.encryPlotValue = function (labels) {
        if (this.encryPlotValueValue != undefined) {
            this.Map.removeLayer(this.encryPlotValueValue);
        }

        this.encryPlotValueValue = new L.FeatureGroup();
        $(labels).each(function (index, label) {
            // Add label
            if (label.id.substring(0, 2) != '58') {
                this.encryPlotValueValue.addLayer(L.marker([label.latitude, label.longitude], {
                    icon: L.divIcon({
                        className: 'value-label text-shadow',
                        //html: parseFloat(label.value)
                        html: this.returnPlotValueHtml(label.typeRegions)
                    })
                }));

                /*if (label.instantDirection != null) {
                    this.encryPlotValueValue.addLayer(L.marker([label.latitude, label.longitude], {
                        icon: L.WindBarb.icon({
                            lat: 40,
                            deg: label.instantDirection,
                            speed: label.value,
                            pointRadius: 5,
                            strokeWidth: 1,
                            strokeLength: 15
                        })
                    }))
                }*/

                $(label.typeRegions).each(function (index,typeRegion) {
                    if (typeRegion.instantDirection != null) {
                        this.basePlotValueValue.addLayer(L.marker([label.latitude, label.longitude], {
                            icon: L.WindBarb.icon({
                                lat: 40,
                                deg: typeRegion.instantDirection,
                                speed: label.value,
                                pointRadius: 5,
                                strokeWidth: 1,
                                strokeLength: 15
                            })
                        }))
                    }
                }.bind(this));

                this.encryPlotValueValue.addLayer(L.circleMarker([label.latitude, label.longitude], {
                    opacity: 1,
                    weight: 0.5,
                    color: 'black',
                    fillColor: '#fb1c15',
                    fillOpacity: 0.5,
                    radius: 2.4
                }))
            }

        }.bind(this))

        this.Map.addLayer(this.encryPlotValueValue);
    }

    this.returnPlotValueHtml = function (array) {
        var textHtml = '<div class="mapinfo"><div class="element-value mapdiv clearfix">';
        $(array).each(function (index,arr) {
            textHtml += '<span class="{1}">{0}</span>'.format(arr.value,arr.type);
        })
        textHtml += '</div></div>';
        return textHtml;
    }

    this.PlotColor = function (legendLevels) {
        if (legendLevels.length <= 0) {
            $("#color-value").html('');
            $("#color-number").html('');
        } else {
            var colorNumber = "";
            var colorValue = "";
            for (var x = legendLevels.length - 1; x >= 0; x--) {
                if (x < legendLevels.length - 1) {
                    colorValue += '<div><span>' + legendLevels[x].EndValue + '</span></div>'
                }
                colorNumber += '<span id="color' + x + '"></span>'
            }
            $("#color-value").html(colorValue);
            $("#color-number").html(colorNumber);
            if (legendLevels[0].type == 'temperatures') {
                $("#color-unit").html('<h1>单位：°c</h1>')
            } else if (legendLevels[0].type == 'winds') {
                $("#color-unit").html('<h1>单位：m/s</h1>')
            } else if (legendLevels[0].type == 'rainfalls') {
                $("#color-unit").html('<h1>单位：mm</h1>')
            } else if (legendLevels[0].type == 'humidities') {
                $("#color-unit").html('<h1>单位：%</h1>')
            } else if (legendLevels[0].type == 'pressures') {
                $("#color-unit").html('<h1>单位：Pa</h1>')
            } else if (legendLevels[0].type == 'groundTemperature') {
                $("#color-unit").html('<h1>单位：°c</h1>')
            }
            //$("#color-value").append('<div><span></span></div>'
            //+'<div><span>'+"mm"+'</span></div>');

            for (var x = legendLevels.length - 1; x >= 0; x--) {
                if (x == 0) {
                    this.setGradient("color" + x, 'rgb(' + legendLevels[x].Color + ')', 'rgb(' + legendLevels[x].Color + ')')
                } else {
                    this.setGradient("color" + x, 'rgb(' + legendLevels[x].Color + ')', 'rgb(' + legendLevels[x].Color + ')')
                }
            }
        }
    }

    this.CreateContourLayer = function (polylines) {

        if (this.ContourLayer != undefined) {
            this.Map.removeLayer(this.ContourLayer);
        }

        // Add feature group
        this.ContourLayer = new L.FeatureGroup();

        // Add polylines
        for (var i = 0; i < polylines.length; i++) {
            // Get polyline
            var polyline = [];
            for (var j = 0; j < polylines[i].PointList.length; j++)
                polyline.push([polylines[i].PointList[j].Y, polylines[i].PointList[j].X]);

            // Add polyline
            this.ContourLayer.addLayer(L.polyline(polyline).setStyle({
                opacity: 1,
                weight: 0.5,
                smoothFactor: 0,
                color: 'red'
            }));

            // Add label
            if (polyline.length > 0) {
                var index = Math.floor(polyline.length / 2);
                this.ContourLayer.addLayer(L.marker(polyline[index], {
                    icon: L.divIcon({
                        className: 'text-shadow contour-label polyline-color',
                        html: parseFloat(polylines[i].Value.toFixed(2).toString().trim('0').trim('.'))
                    })
                }));
            }
        }
        this.Map.addLayer(this.ContourLayer);
    };

    this.setGradient = (function () {
        var canvas = document.createElement('canvas');
        var useCanvas = !!(typeof (canvas.getContext) == 'function');
        var ctx = useCanvas ? canvas.getContext('2d') : null;
        var isIE = false;
        if (useCanvas) {
            return function (dEl, sColor1, sColor2) {
                if (typeof (dEl) == 'string') dEl = document.getElementById(dEl);
                if (!dEl) return false;
                var width = dEl.offsetWidth;
                var height = dEl.offsetHeight;
                canvas.width = width;
                canvas.height = height;
                var dGradient;
                var sRepeat;
                dGradient = ctx.createLinearGradient(0, 0, width, 0);
                sRepeat = 'repeat-y';
                dGradient.addColorStop(0, sColor1);
                dGradient.addColorStop(1, sColor2);
                ctx.fillStyle = dGradient;
                ctx.fillRect(0, 0, width, height);
                var sDataUrl = ctx.canvas.toDataURL('image/png');
                with (dEl.style) {
                    backgroundRepeat = sRepeat;
                    backgroundImage = 'url(' + sDataUrl + ')';
                    backgroundColor = sColor2;
                }
            }
        } else if (isIE) {
            return function (dEl, sColor1, sColor2, bRepeatY) {
                if (typeof (dEl) == 'string') dEl = document.getElementById(dEl);
                if (!dEl) return false;
                dEl.style.zoom = 1;
                dEl.style.filter += ' ' + ['progid:DXImageTransform.Microsoft.gradient( GradientType=', +(!!bRepeatY), ',enabled=true,startColorstr=', sColor1, ', endColorstr=', sColor2, ')'].join('');
            };
        } else {
            return function (dEl, sColor1, sColor2) {
                if (typeof (dEl) == 'string') dEl = document.getElementById(dEl);
                if (!dEl) return false;
                with (dEl.style) {
                    backgroundColor = sColor2;
                }
            }
        }
    })();

    this.writeInfo = function (result, requestValue) {
        var htmlText = '';
        if (requestValue != undefined) {
            if (requestValue == "rainfalls") {
                htmlText = result.time + "的降雨信息";
            } else if (requestValue == "temperatures") {
                htmlText = result.time + "的温度信息";
                ;
            } else if (requestValue == "winds") {
                htmlText = result.time + "的风信息";
                ;
            } else if (requestValue == "humidities") {
                htmlText = result.time + "的湿度信息";
                ;
            } else if (requestValue == "pressures") {
                htmlText = result.time + "的气压信息";
                ;
            } else if (requestValue == "groundTemperature") {
                htmlText += result.time + "的地温信息";
                ;
            }
            $("#map-info").html(htmlText)
        }
    }

}
