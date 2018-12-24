var RightPanel = function () {

    this.result = null;
    this.parmOne = null;
    this.parmTwo = null;
    this.requestValue = null;
    this.requestPath = '';
    this.flag = true;
    this.MapInfo = new MapInfo(this);

    this.Startup = function () {
        this.MapInfo.CreateEasyMap();
        this.MapInfo.Startup();
        this.getInfo();
        this.autoChang();

        $("#checkbox0").on('click',this.layerInfoZeroButtonClick.bind(this));
        $("#checkbox1").on('click',this.layerInfoOneButtonClick.bind(this));
        $("#checkbox2").on('click',this.layerInfoTwoButtonClick.bind(this));
        $("#checkbox4").on('click',this.layerInfoFourButtonClick.bind(this));
        $("#checkbox3").on('click',this.layerInfoThreeButtonClick.bind(this));
        $("#checkbox5").on('click',this.layerInfoFiveButtonClick.bind(this));
        $("#weatherUl").on('click',this.onElementButtonClick.bind(this));
        $("input[type='radio']").on('click',this.onOptionButtonClick.bind(this));
    }

    //定义用户点击图层信息按钮
    this.layerInfoZeroButtonClick = function () {
        if($("#checkbox0").prop('checked')){
            if($("#checkbox1").prop("checked")){
                this.MapInfo.basePoltDirName(this.result.valuePoints);
            }
            if($("#checkbox4").prop("checked")){
                this.MapInfo.basePlotValue(this.result.valuePoints);
            }
            //this.MapInfo.CreateDir(this.result.valuePoints);
        }else {
            this.MapInfo.Map.removeLayer(this.MapInfo.basePoltDirNameValue);
            this.MapInfo.Map.removeLayer(this.MapInfo.basePlotValueValue);
        }
    }
    this.layerInfoFiveButtonClick = function () {
        if($("#checkbox5").prop('checked')){
            if($("#checkbox1").prop("checked")){
                this.MapInfo.encryptionPointName(this.result.valuePoints);
            }
            if($("#checkbox4").prop("checked")){
                this.MapInfo.encryPlotValue(this.result.valuePoints);
            }
            //this.MapInfo.CreateDir(this.result.valuePoints);
        }else{
            this.MapInfo.Map.removeLayer(this.MapInfo.encryptionPointNameValue);
            this.MapInfo.Map.removeLayer(this.MapInfo.encryPlotValueValue);
        }
    }

    this.layerInfoOneButtonClick = function () {
        if($("#checkbox1").prop('checked')){
            /*this.MapInfo.PlotSite(this.result.valuePoints);*/
            if($("#checkbox0").prop('checked')){
                this.MapInfo.basePoltDirName(this.result.valuePoints);
            }
            if($("#checkbox5").prop('checked')){
                this.MapInfo.encryptionPointName(this.result.valuePoints);
            }
            //this.MapInfo.CreateDir(this.result.valuePoints);
        }else {
            this.MapInfo.Map.removeLayer(this.MapInfo.basePoltDirNameValue);
            this.MapInfo.Map.removeLayer(this.MapInfo.encryptionPointNameValue);
        }
    }

    this.layerInfoTwoButtonClick = function(){
        if($("#checkbox2").prop('checked')){
            this.MapInfo.CreateContourLayer(this.result.contourPolylines)
        }else {
            this.MapInfo.Map.removeLayer(this.MapInfo.ContourLayer);
        }
    }

    this.layerInfoFourButtonClick = function(){
        if($("#checkbox4").prop('checked')){
            /*this.MapInfo.PlotSite(this.result.valuePoints);*/
            if($("#checkbox0").prop('checked')){
                this.MapInfo.basePlotValue(this.result.valuePoints);
            }
            if($("#checkbox5").prop('checked')){
                this.MapInfo.encryPlotValue(this.result.valuePoints);
            }
            //this.MapInfo.CreateDir(this.result.valuePoints);
        }else {
            this.MapInfo.Map.removeLayer(this.MapInfo.basePlotValueValue);
            this.MapInfo.Map.removeLayer(this.MapInfo.encryPlotValueValue);
        }
    }

    this.layerInfoThreeButtonClick = function () {
        if($("#checkbox3").prop('checked')){
            this.MapInfo.CreateSpotLayer(this.result.spotPolygons, this.result.legendLevels)
            this.MapInfo.PlotColor(this.result.legendLevels);
        }else {
            this.MapInfo.PlotColor('');
            this.MapInfo.Map.removeLayer(this.MapInfo.layer);
        }
    }

    this.autoChang = function () {
        setInterval(function () {
            if(this.parmOne!=null){
                this.playCode();
            }
        }.bind(this),1000*60*60)
    }

    this.loadClick = function (flag) {
        if(flag){
            $("#map-loading").attr("style","display: block")
        }else {
            $("#map-loading").attr("style","display: none")
        }
    }

    this.getInfo = function () {
        this.requestValue = "temperatures";

        $.ajax({
            type: "POST",
            dataType: 'json',
            url: this.requestPath + "temperatures/findAllByTerm",
            data: {
                parms1: '温度',
                parms2: '≥0'
            },
            beforeSend: function () {
                // show loading...
                this.loadClick(true)
            }.bind(this),
            error: function () {
                // hide loading
                this.flag = true;
                this.loadClick(false)
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
                        if($("#checkbox1").attr("checked")){
                            this.MapInfo.basePoltDirName(this.result.valuePoints);
                        }
                        if($("#checkbox4").prop("checked")){
                            this.MapInfo.basePlotValue(this.result.valuePoints);
                        }
                    }
                    if ($("#checkbox5").prop('checked')) {
                        if($("#checkbox1").prop("checked")){
                            this.MapInfo.encryptionPointName(this.result.valuePoints);
                        }
                        if($("#checkbox4").prop("checked")){
                            this.MapInfo.encryPlotValue(this.result.valuePoints);
                        }
                    }
                    this.MapInfo.writeInfo(this.result,this.requestValue);
                }
            }.bind(this)
        });
    }

    this.playCode = function(){
        if(this.flag){
            $.ajax({
                type: "POST",
                dataType: 'json',
                url: this.requestPath + this.requestValue + "/findAllByTerm",
                data: {
                    parms1: this.parmOne,
                    parms2: this.parmTwo
                },
                beforeSend: function () {
                    // show loading...
                    this.loadClick(true)
                }.bind(this),
                error: function () {
                    // hide loading
                    this.loadClick(false)
                    this.flag = true;
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
                        $("#color-unit").attr("style","display: block")
                        /*if($("#checkbox3").attr('checked')){
                            this.MapInfo.CreateSpotLayer(data.spotPolygons, data.legendLevels)
                            this.MapInfo.PlotColor(data.legendLevels);
                        }
                        if($("#checkbox2").attr('checked')){
                            this.MapInfo.CreateContourLayer(data.contourPolylines);
                        }
                        if($("#checkbox1").attr('checked')){
                            this.MapInfo.PlotSite(data.valuePoints);
                            //this.MapInfo.CreateDir(data.valuePoints);
                        }
                        if ($("#checkbox0").attr('checked')) {
                            this.MapInfo.PlotSite(data.valuePoints);
                            //this.MapInfo.CreateDir(data.valuePoints);
                        }
                        if ($("#checkbox4").attr('checked')) {
                            this.MapInfo.CreatePlotValue(data.valuePoints)
                            //this.MapInfo.CreateDir(data.valuePoints);
                        }*/
                        if ($("#checkbox3").prop('checked')) {
                            this.MapInfo.CreateSpotLayer(data.spotPolygons, data.legendLevels)
                            this.MapInfo.PlotColor(data.legendLevels);
                        }
                        if ($("#checkbox2").prop('checked')) {
                            this.MapInfo.CreateContourLayer(data.contourPolylines);
                        }
                        if ($("#checkbox0").prop('checked')) {
                            if($("#checkbox1").attr("checked")){
                                this.MapInfo.basePoltDirName(this.result.valuePoints);
                            }
                            if($("#checkbox4").prop("checked")){
                                this.MapInfo.basePlotValue(this.result.valuePoints);
                            }
                        }
                        if ($("#checkbox5").prop('checked')) {
                            if($("#checkbox1").prop("checked")){
                                this.MapInfo.encryptionPointName(this.result.valuePoints);
                            }
                            if($("#checkbox4").prop("checked")){
                                this.MapInfo.encryPlotValue(this.result.valuePoints);
                            }
                        }
                        this.MapInfo.writeInfo(this.result,this.requestValue);
                    }
                }.bind(this)
            });
        }else{

        }
    }

    this.checkNull = function () {
        if(this.MapInfo.basePlotValueValue != null){
            this.MapInfo.Map.removeLayer(this.MapInfo.basePlotValueValue);
        }
        if(this.MapInfo.encryPlotValueValue != null){
            this.MapInfo.Map.removeLayer(this.MapInfo.encryPlotValueValue);
        }
        if(this.MapInfo.basePoltDirNameValue != null){
            this.MapInfo.Map.removeLayer(this.MapInfo.basePoltDirNameValue);
        }
        if(this.MapInfo.encryptionPointNameValue != null){
            this.MapInfo.Map.removeLayer(this.MapInfo.encryptionPointNameValue);
        }
        if(this.MapInfo.ContourLayer != null){
            this.MapInfo.Map.removeLayer(this.MapInfo.ContourLayer);
        }
        if(this.MapInfo.layer != null){
            this.MapInfo.Map.removeLayer(this.MapInfo.layer);
        }
        this.MapInfo.PlotColor('');
        $("#color-unit").attr("style","display: none")
    }

    this.onElementButtonClick = function () {
        //this.flag = false;
        this.requestValue = $("#weatherUl").children(".action").attr("val");
        if (this.requestValue == "temperatures") {
            this.parmOne = $("input[checked][name='radio3']").attr("value");
            this.parmTwo = $("input[checked][name='radio8']").attr("value");
        } else if (this.requestValue == "rainfalls") {
            this.parmOne = $("input[checked][name='radio1']").attr("value");
            this.parmTwo = $("input[checked][name='radio2']").attr("value");
        } else if (this.requestValue == "winds") {
            this.parmOne = $("input[checked][name='radio4']").attr("value");
            this.parmTwo = $("input[checked][name='radio9']").attr("value");
        } else if (this.requestValue == "humidities") {
            this.parmOne = $("input[checked][name='radio5']").attr("value");
            this.parmTwo = null;
        } else if (this.requestValue == "pressures") {
            this.parmOne = $("input[checked][name='radio6']").attr("value");
            this.parmTwo = null;
        } else if (this.requestValue == "groundTemperature") {
            this.parmOne = $("input[checked][name='radio7']").attr("value");
            this.parmTwo = null;
        }
        this.playCode();
    }

    this.onOptionButtonClick = function () {
        //this.flag = false;
        if (this.requestValue == "temperatures") {
            this.parmOne = $("input[name=radio3]:checked").attr("value");
            this.parmTwo = $("input[name=radio8]:checked").attr("value");
        } else if (this.requestValue == "rainfalls") {
            this.parmOne = $("input[name=radio1]:checked").attr("value");
            this.parmTwo = $("input[name=radio2]:checked").attr("value");
        } else if (this.requestValue == "winds") {
            this.parmOne = $("input[name=radio4]:checked").attr("value");
            this.parmTwo = $("input[name=radio9]:checked").attr("value");
        } else if (this.requestValue == "humidities") {
            this.parmOne = $("input[name=radio5]:checked").attr("value");
            this.parmTwo = null;
        } else if (this.requestValue == "pressures") {
            this.parmOne = $("input[name=radio6]:checked").attr("value");
            this.parmTwo = null;
        } else if (this.requestValue == "groundTemperature") {
            this.parmOne = $("input[name=radio7]:checked").attr("value");
            this.parmTwo = null;
        }
        this.playCode();
    }
}