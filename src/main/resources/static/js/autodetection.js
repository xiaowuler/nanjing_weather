var Autodetecion = function () {

    this.result = null;
    this.MapInfo = new MapInfo(this);

    this.Startup = function () {
        this.Drag();
        this.MapInfo.CreateMap();
        this.MapInfo.Startup();
        this.timeProcessing();
        this.clickQueryButton();
        $("#checkbox0").on('click', this.layerInfoZeroButtonClick.bind(this));
        $("#checkbox1").on('click', this.layerInfoOneButtonClick.bind(this));
        $("#checkbox2").on('click', this.layerInfoTwoButtonClick.bind(this));
        $("#checkbox4").on('click', this.layerInfoFourButtonClick.bind(this));
        $("#checkbox3").on('click', this.layerInfoThreeButtonClick.bind(this));
        $("#checkbox5").on('click', this.layerInfoFiveButtonClick.bind(this));
    }

    this.clickQueryButton = function () {
        $(".easyui-linkbutton").on('click', function (e) {
            //alert($(".rainfall").find(".clearfix li.action").attr("val"))
            var requestPath = $(".rainfall").find(".clearfix li.action").attr("val")
            var parmOne = null;
            var parmTwo = null;
            if (requestPath == "temperatures") {
                parmOne = $("input[name=radio3]:checked").attr("value");
                parmTwo = $("input[name=radio8]:checked").attr("value");
            } else if (requestPath == "rainfalls") {
                parmOne = $("input[name=radio2]:checked").attr("value");
            } else if (requestPath == "winds") {
                parmOne = $("input[name=radio4]:checked").attr("value");
                parmTwo = $("input[name=radio9]:checked").attr("value");
            } else if (requestPath == "humidities") {
                parmOne = $("input[name=radio5]:checked").attr("value");
            } else if (requestPath == "pressures") {
                parmOne = $("input[name=radio6]:checked").attr("value");
            } else if (requestPath == "groundTemperature") {
                parmOne = $("input[name=radio7]:checked").attr("value");
            }
            var time = this.timeFormat($("#date-start").val() + '/' + $("#start-text").text(), $("#date-end").val() + '/' + $("#end-text").text())
            /*alert(time)
            alert(parmOne+'  '+parmTwo)*/
            if (time != null) {
                this.getInfoData(requestPath, parmOne, parmTwo, time)
            }
        }.bind(this))
    }

    this.getInfoData = function (requestPath, parmOne, parmTwo, time) {
        $.ajax({
            type: "POST",
            dataType: 'json',
            url: requestPath + "/findAllBySomeTerm",
            data: {
                parmOne: parmOne,
                parmTwo: parmTwo,
                time: time
            },
            beforeSend: function () {
                // show loading...
                this.loadClick(true)
            }.bind(this),
            error: function () {
                // hide loading
                this.flag = true;
                this.loadClick(false);
                this.checkNull();
            }.bind(this),
            success: function (data) {
                // hide loading...
                this.loadClick(false)
                this.flag = true;
                if (data == null) {
                    //alert("查询信息为空！！")
                    this.checkNull();
                } else {
                    this.result = null;
                    this.result = data;
                    if ($("#checkbox3").prop('checked')) {
                        this.MapInfo.CreateSpotLayer(data.spotPolygons, data.legendLevels)
                        this.MapInfo.PlotColor(data.legendLevels);
                    }
                    if ($("#checkbox2").prop('checked')) {
                        this.MapInfo.CreateContourLayer(data.contourPolylines);
                    }
                    if ($("#checkbox0").prop('checked')) {
                        if ($("#checkbox1").prop("checked")) {
                            this.MapInfo.basePoltDirName(this.result.valuePoints);
                        }
                        if ($("#checkbox4").prop("checked")) {
                            this.MapInfo.basePlotValue(this.result.valuePoints);
                        }
                    }
                    if ($("#checkbox5").prop('checked')) {
                        if ($("#checkbox1").prop("checked")) {
                            this.MapInfo.encryptionPointName(this.result.valuePoints);
                        }
                        if ($("#checkbox4").prop("checked")) {
                            this.MapInfo.encryPlotValue(this.result.valuePoints);
                        }
                    }
                }
            }.bind(this)
        });
    }

    this.timeProcessing = function () {
        var myDate = new Date();
        var hour = myDate.getHours();
        $('#start-text').text(hour + '小时')
        if (hour == '23') {
            $('#end-text').text('00小时')
        } else {
            $('#end-text').text((hour + 1) + '小时')
        }
    }

    this.checkNull = function () {
        if (this.MapInfo.basePlotValueValue != null) {
            this.MapInfo.Map.removeLayer(this.MapInfo.basePlotValueValue);
        }
        if (this.MapInfo.encryPlotValueValue != null) {
            this.MapInfo.Map.removeLayer(this.MapInfo.encryPlotValueValue);
        }
        if (this.MapInfo.basePoltDirNameValue != null) {
            this.MapInfo.Map.removeLayer(this.MapInfo.basePoltDirNameValue);
        }
        if (this.MapInfo.encryptionPointNameValue != null) {
            this.MapInfo.Map.removeLayer(this.MapInfo.encryptionPointNameValue);
        }
        if (this.MapInfo.ContourLayer != null) {
            this.MapInfo.Map.removeLayer(this.MapInfo.ContourLayer);
        }
        if (this.MapInfo.layer != null) {
            this.MapInfo.Map.removeLayer(this.MapInfo.layer);
        }
        this.MapInfo.PlotColor('');
        $("#color-unit").attr("style", "display: none")
    }

    this.loadClick = function (flag) {
        if (flag) {
            $("#map-loading").attr("style", "display: block")
        } else {
            $("#map-loading").attr("style", "display: none")
        }
    }

    this.timeFormat = function (timeStart, timeEnd) {
        var startArray = timeStart.split("/");
        var startHour = startArray[startArray.length - 1].substring(0, 2);
        var endArray = timeEnd.split("/")
        var endHour = endArray[endArray.length - 1].substring(0, 2);
        var startTime = '';
        var endTime = '';
        var startNum = '';
        var endNum = '';
        for (var i = 0; i < startArray.length - 1; i++) {
            startNum += startArray[i];
            endNum += endArray[i];
            if (parseFloat(startNum) > parseFloat(endNum)) {
                return null;
            }
            startTime += startArray[i] + '/';
            endTime += endArray[i] + '/';
        }
        if (parseFloat(startNum + startHour) > parseFloat(endNum + endHour)) {
            alert("时间选段出错，请调整后查询！！");
            return null;
        }
        startTime += startHour;
        endTime += endHour;
        return startTime + ":" + endTime
    }

    this.Drag = function () {
        var oDiv = document.getElementById("drag");
        var disX = 0;
        var disY = 0;
        oDiv.onmousedown = function () {
            var e = e || window.event;
            disX = e.clientX - oDiv.offsetLeft;
            disY = e.clientY - oDiv.offsetTop;

            document.onmousemove = function (e) {
                var e = e || window.event;
                var leftX = e.clientX - disX;
                var topY = e.clientY - disY;

                if (leftX < 0) {
                    leftX = 0;
                }
                else if (leftX > document.documentElement.clientWidth - oDiv.offsetWidth) {
                    leftX = document.document.documentElement.clientWidth - oDiv.offsetWidth;
                }

                if (topY < 0) {
                    topY = 0;
                } else if (topY > document.documentElement.clientHeight - oDiv.offsetHeight) {
                    topY = document.documentElement.clientHeight - oDiv.offsetHeight;
                }
                oDiv.style.left = leftX + "px";
                oDiv.style.top = topY + "px";
            };
            document.onmouseup = function () {
                document.onmousemove = null;
                document.onmouseup = null;
            }
        }
    }

    //定义用户点击图层信息按钮
    this.layerInfoZeroButtonClick = function () {
        if (this.result != null) {
            if ($("#checkbox0").prop('checked')) {
                if ($("#checkbox1").prop("checked")) {
                    this.MapInfo.basePoltDirName(this.result.valuePoints);
                }
                if ($("#checkbox4").prop("checked")) {
                    this.MapInfo.basePlotValue(this.result.valuePoints);
                }
                //this.MapInfo.CreateDir(this.result.valuePoints);
            } else {
                this.MapInfo.Map.removeLayer(this.MapInfo.basePoltDirNameValue);
                this.MapInfo.Map.removeLayer(this.MapInfo.basePlotValueValue);
            }
        }
    }
    this.layerInfoFiveButtonClick = function () {
        if (this.result != null) {
            if ($("#checkbox5").prop('checked')) {
                if ($("#checkbox1").prop("checked")) {
                    this.MapInfo.encryptionPointName(this.result.valuePoints);
                }
                if ($("#checkbox4").prop("checked")) {
                    this.MapInfo.encryPlotValue(this.result.valuePoints);
                }
                //this.MapInfo.CreateDir(this.result.valuePoints);
            } else {
                this.MapInfo.Map.removeLayer(this.MapInfo.encryptionPointNameValue);
                this.MapInfo.Map.removeLayer(this.MapInfo.encryPlotValueValue);
            }
        }
    }

    this.layerInfoOneButtonClick = function () {
        if (this.result != null) {
            if ($("#checkbox1").prop('checked')) {
                /*this.MapInfo.PlotSite(this.result.valuePoints);*/
                if ($("#checkbox0").prop('checked')) {
                    this.MapInfo.basePoltDirName(this.result.valuePoints);
                }
                if ($("#checkbox5").prop('checked')) {
                    this.MapInfo.encryptionPointName(this.result.valuePoints);
                }
                //this.MapInfo.CreateDir(this.result.valuePoints);
            } else {
                this.MapInfo.Map.removeLayer(this.MapInfo.basePoltDirNameValue);
                this.MapInfo.Map.removeLayer(this.MapInfo.encryptionPointNameValue);
            }
        }
    }

    this.layerInfoTwoButtonClick = function () {
        if (this.result != null) {
            if ($("#checkbox2").prop('checked')) {
                this.MapInfo.CreateContourLayer(this.result.contourPolylines)
            } else {
                this.MapInfo.Map.removeLayer(this.MapInfo.ContourLayer);
            }
        }
    }

    this.layerInfoFourButtonClick = function () {
        if (this.result != null) {
            if ($("#checkbox4").prop('checked')) {
                /*this.MapInfo.PlotSite(this.result.valuePoints);*/
                if ($("#checkbox0").prop('checked')) {
                    this.MapInfo.basePlotValue(this.result.valuePoints);
                }
                if ($("#checkbox5").prop('checked')) {
                    this.MapInfo.encryPlotValue(this.result.valuePoints);
                }
                //this.MapInfo.CreateDir(this.result.valuePoints);
            } else {
                this.MapInfo.Map.removeLayer(this.MapInfo.basePlotValueValue);
                this.MapInfo.Map.removeLayer(this.MapInfo.encryPlotValueValue);
            }
        }
    }

    this.layerInfoThreeButtonClick = function () {
        if (this.result != null) {
            if ($("#checkbox3").prop('checked')) {
                this.MapInfo.CreateSpotLayer(this.result.spotPolygons, this.result.legendLevels)
                $("#color-unit").attr("style", "display: block")
                this.MapInfo.PlotColor(this.result.legendLevels);
            } else {
                this.MapInfo.PlotColor('');
                $("#color-unit").attr("style", "display: none")
                this.MapInfo.Map.removeLayer(this.MapInfo.layer);
            }
        }
    }
}

$(function () {
    var autodetecion = new Autodetecion();
    autodetecion.Startup();
});