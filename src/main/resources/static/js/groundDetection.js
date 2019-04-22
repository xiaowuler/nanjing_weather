var GroundDetection = function () {

    this.result = new ArrayData();
    this.rainFallResult = null;
    this.windResult = null;
    this.temperatureResult = null;
    this.groundTemperatureResult = null;
    this.pressureResult = null;
    this.humidityResult = null;
    this.requestPath = '';
    this.flag = true;
    this.timer = null;
    this.count = 0;
    this.MapInfo = new MapInfoGround(this);
    this.colorControl = new ColorContorl();

    this.Startup = function () {

        this.MapInfo.CreateMap();
        this.MapInfo.Startup();

        this.timeProcessing();

        this.isolineAndSplashMapClick();
        this.optionCheck();

        this.basePlotClick();
        this.encryPlotClick();
        this.plotNameClick();
        this.plotValueClick();

        $('#search').on('click',this.searchClick.bind(this));
        this.searchClick();
    }

    this.searchClick = function () {
        var startTime = $('#start-date').val() +'/'+ $('#start-combobox').combobox('getValue');
        var endTime = $('#end-date').val() +'/'+ $('#end-combobox').combobox('getValue');
        var time = this.timeFormat(startTime,endTime);

        this.clearOverdueDate();

        this.senseRainfallCheck(time);
        this.senseTermperCheck(time);
        this.senseWindCheck(time);
        this.senseGroundTermperCheck(time);
        this.sensePressCheck(time);
        this.senseHumCheck(time);
    }

    this.clearOverdueDate = function () {
        this.rainFallResult = null;
        this.windResult = null;
        this.temperatureResult = null;
        this.groundTemperatureResult = null;
        this.pressureResult = null;
        this.humidityResult = null;
    }

    this.senseRainfallCheck = function (time) {
        if($('.millimeter-select a.action').length > 0){
            var parmOne = $('.millimeter-select a.action').attr('val')
            this.playCode(parmOne,null,time,'rainfalls');
        }
    }

    this.senseTermperCheck = function (time) {
        if($('.variable-select a.action').length > 0){
            var parmOne = $('.variable-select a.action').attr('val');
            var parmTwo = $('.temperature-select a.action').attr('val');
             this.playCode(parmOne,parmTwo,time,'temperatures');
        }
    }

    this.senseWindCheck = function (time) {
        if($('.wind-select a.action').length > 0){
            var parmOne = $('.wind-select a.action').attr('val')
            this.playCode(parmOne,null,time,'winds');
        }
    }
    
    this.senseGroundTermperCheck = function (time) {
        if($('.geothermal-select a.action').length > 0){
            var parmOne = $('.geothermal-select a.action').attr('val');
            this.playCode(parmOne,null,time,'groundTemperature');
        }
    }

    this.sensePressCheck = function (time) {
        if($('.air-pressure-select a.action').length > 0){
            var parmOne = $('.air-pressure-select a.action').attr('val');
            this.playCode(parmOne,null,time,'pressures');
        }
    }
    
    this.senseHumCheck = function (time) {
        if($('.humidity-select a.action').length > 0){
            var parmOne = $('.humidity-select a.action').attr('val');
            this.playCode(parmOne,null,time,'humidities');
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
            if(i == startArray.length - 2){
                startTime += startArray[i] + ' ';
                endTime += endArray[i] + ' ';
            }else {
                startTime += startArray[i] + '/';
                endTime += endArray[i] + '/';
            }
        }
        if (parseFloat(startNum + startHour) > parseFloat(endNum + endHour)) {
            alert("时间选段出错，请调整后查询！！");
            return null;
        }
        startTime += startHour;
        endTime += endHour;
        return startTime + ":" + endTime
    }

    this.timeProcessing = function () {
        var myDate = new Date();
        var hour = myDate.getHours();
        $('#start-combobox').combobox('setValue',hour.toString().PadLeft(2, '0') + '时');
        $('#end-combobox').combobox('setValue','{0}时'.format(new Date().addHours(1).getHours().toString().PadLeft(2, '0')));
        this.SetEndDateBoxValue($('#start-combobox').combobox('getValue'), $('#end-combobox').combobox('getValue'));
    }

    this.SetEndDateBoxValue = function (startTime, endTime) {
        if (startTime.substring(0, 2) >= endTime.substring(0, 2))
            $('#end-date').datebox('setValue',  moment(new Date().addDays(1)).format('YYYY/MM/DD'));
    }.bind(this);

    this.loadClick = function (flag) {
        if (flag) {
            $("#map-loading").attr("style", "display: block")
        } else {
            $("#map-loading").attr("style", "display: none")
        }
    }

    this.playCode = function (parmOne,parmTwo,time,requestValue) {
        if (this.flag) {
            $.ajax({
                type: "POST",
                dataType: 'json',
                url: this.requestPath + requestValue + "/findAllBySomeTerm",
                data: {
                    parmOne: parmOne,
                    parmTwo: parmTwo,
                    time: time
                },
                beforeSend: function () {
                    // show loading...
                    this.loadClick(true);
                }.bind(this),
                error: function () {
                    // hide loading
                    this.loadClick(false);
                    this.flag = true;
                }.bind(this),
                success: function (data) {
                    // hide loading...
                    this.loadClick(false);
                    this.flag = true;

                    if(requestValue == 'temperatures')
                        this.temperatureResult = data;
                    else if(requestValue == 'rainfalls')
                        this.rainFallResult = data;
                    else if(requestValue == 'winds')
                        this.windResult = data;
                    else if(requestValue == 'groundTemperature')
                        this.groundTemperatureResult = data;
                    else if(requestValue == 'pressures')
                        this.pressureResult = data;
                    else if(requestValue == 'humidities')
                        this.humidityResult = data;

                    this.insertData();
                    this.getCheckLayerInfo();
                    this.getCheckLayerOfChart(requestValue,data);

                }.bind(this)
            });
        } else {

        }
    }

    this.insertData = function () {
        var array = [];
        if(this.rainFallResult != null)
            array.push(new Array('rainfalls',this.rainFallResult.valuePoints));
        if (this.temperatureResult != null)
            array.push(new Array('temperatures',this.temperatureResult.valuePoints));
        if (this.windResult != null)
            array.push(new Array('winds',this.windResult.valuePoints));
        if (this.groundTemperatureResult != null)
            array.push(new Array('groundTemperature',this.groundTemperatureResult.valuePoints));
        if (this.pressureResult != null)
            array.push(new Array('pressures',this.pressureResult.valuePoints));
        if (this.humidityResult != null)
            array.push(new Array('humidities',this.humidityResult.valuePoints));
        this.result.addData(array)
    }

    this.basePlotClick = function () {
        $('#basic').on('click',function () {
            this.basePoltSense();
        }.bind(this))
    }

    this.encryPlotClick = function () {
        $('#encrypt').on('click',function () {
            this.encryPlotSense();
        }.bind(this))
    }

    this.plotNameClick = function () {
        $('#station').on('click',function () {
            this.plotNameSense();
        }.bind(this))
    }

    this.plotValueClick = function () {
        $('#value').on('click',function () {
            this.plotValueSense();
        }.bind(this))
    }

    this.plotValueSense = function () {
        if($('#value').prop('checked')){
            if($('#basic').prop('checked')){
                if(this.result != null)
                    this.MapInfo.basePlotValue(this.result.regions);
            }

            if($('#encrypt').prop('checked')){
                if(this.result != null)
                    this.MapInfo.encryPlotValue(this.result.regions);
            }
        }else {
            if (this.MapInfo.basePlotValueValue != null)
                this.MapInfo.Map.removeLayer(this.MapInfo.basePlotValueValue);

            if (this.MapInfo.encryPlotValueValue != null)
                this.MapInfo.Map.removeLayer(this.MapInfo.encryPlotValueValue);

        }
    }

    this.plotNameSense = function () {
        if($('#station').prop('checked')){
            if($('#basic').prop('checked')){
                if(this.result != null)
                    this.MapInfo.basePoltDirName(this.result.regions);
            }

            if($('#encrypt').prop('checked')){
                if(this.result != null)
                    this.MapInfo.encryptionPointName(this.result.regions);
            }
        }else {
            if (this.MapInfo.basePoltDirNameValue != null)
                this.MapInfo.Map.removeLayer(this.MapInfo.basePoltDirNameValue);
            if (this.MapInfo.encryptionPointNameValue != null)
                this.MapInfo.Map.removeLayer(this.MapInfo.encryptionPointNameValue);
        }
    }

    this.basePoltSense = function () {
        if($('#basic').prop('checked')){
            if($('#station').prop('checked')){
                if(this.result != null)
                    this.MapInfo.basePoltDirName(this.result.regions);
            }

            if($('#value').prop('checked')){
                if(this.result != null)
                    this.MapInfo.basePlotValue(this.result.regions);
            }
        }else{
            if (this.MapInfo.basePlotValueValue != null)
                this.MapInfo.Map.removeLayer(this.MapInfo.basePlotValueValue);
            if (this.MapInfo.basePoltDirNameValue != null) {
                this.MapInfo.Map.removeLayer(this.MapInfo.basePoltDirNameValue);
            }
        }
    }

    this.encryPlotSense = function () {
        if($('#encrypt').prop('checked')){
            if($('#station').prop('checked')){
                if(this.result != null)
                    this.MapInfo.encryptionPointName(this.result.regions);
            }

            if($('#value').prop('checked')){
                if(this.result != null)
                    this.MapInfo.encryPlotValue(this.result.regions);
            }
        }else {
            if (this.MapInfo.encryPlotValueValue != null) {
                this.MapInfo.Map.removeLayer(this.MapInfo.encryPlotValueValue);
            }
            if (this.MapInfo.encryptionPointNameValue != null) {
                this.MapInfo.Map.removeLayer(this.MapInfo.encryptionPointNameValue);
            }
        }
    }

    this.getCheckLayerInfo = function () {
        this.plotNameSense();
        this.plotValueSense();
        this.encryPlotSense();
        this.basePoltSense();
        this.getCheckedByLayerText();
    }

    this.getCheckedByLayerText = function () {
        if($('.layer-text a.action').length === 0)
            this.checkNull();
    }

    this.checkNull = function () {
        if (this.MapInfo.ContourLayer != null)
            this.MapInfo.Map.removeLayer(this.MapInfo.ContourLayer);

        if (this.MapInfo.layer != null)
            this.MapInfo.Map.removeLayer(this.MapInfo.layer);

        $(".color-control").attr("style", "display: none")
    }

    this.getCheckLayerOfChart = function (checkValue,result) {
        if($('.layer-text a.action').attr('val') === checkValue){
            if(result != null){
                if($('#contour').prop('checked')){
                    this.MapInfo.CreateContourLayer(result.contourPolylines);
                }else {
                    if (this.MapInfo.ContourLayer != null) {
                        this.MapInfo.Map.removeLayer(this.MapInfo.ContourLayer);
                    }
                }

                if ($("#color-figure").prop('checked')) {
                    this.MapInfo.CreateSpotLayer(result.spotPolygons, result.legendLevels)
                    //this.MapInfo.PlotColor(result.legendLevels);
                    $(".color-control").attr("style", "display: block")
                    this.colorControl.setColor(result.legendLevels[0].type,this.returnColor(result.legendLevels));
                    this.colorControl.Startup();
                }else {
                    if (this.MapInfo.layer != null) {
                        this.MapInfo.Map.removeLayer(this.MapInfo.layer);
                    }
                    $(".color-control").attr("style", "display: none")
                }
            }
        }

    }

    this.returnColor = function (colors) {
        var array = []
        var arrayColor = [];
        var arrayValue = [];
        $(colors).each(function (index,color) {
            if(index == 0){
                //arrayColor.push(new Array(color.Color,color.Color));
                arrayValue.push(color.BeginValue);
                arrayValue.push(color.EndValue);
            }else{
                //arrayColor.push(new Array(colors[index-1].Color,color.Color));
                arrayValue.push(color.EndValue);
            }
        }.bind(this));
        for(var i = colors.length -1 ; i >= 0; i--){
            if(i == 0){
                arrayColor.push(new Array(colors[i].Color,colors[i].Color));
            }else{
                arrayColor.push(new Array(colors[i-1].Color,colors[i].Color));
            }
        }
        array.push(arrayColor);
        array.push(arrayValue);
        return array;
    }

    this.optionCheck = function () {
        $('.layer-text').off('click').on('click',function (e) {
            if (this.MapInfo.ContourLayer != null) {
                this.MapInfo.Map.removeLayer(this.MapInfo.ContourLayer);
            }
            if (this.MapInfo.layer != null) {
                this.MapInfo.Map.removeLayer(this.MapInfo.layer);
            }
            //this.MapInfo.PlotColor('');
            $(".color-control").attr("style", "display: none")

            if($(e.target).hasClass('action')){
                this.defineArray($(e.target).attr('val'));
            }
        }.bind(this))
    }

    this.defineArray = function (value) {
        var arr = ['rainfalls','temperatures','winds','groundTemperature','pressures','humidities'];
        var index = $.inArray(value,arr);
        if(index === 0)
            this.getCheckLayerOfChart(arr[index],this.rainFallResult);
        else if (index === 1)
            this.getCheckLayerOfChart(arr[index],this.temperatureResult);
        else if (index === 2)
            this.getCheckLayerOfChart(arr[index],this.windResult);
        else if (index === 3)
            this.getCheckLayerOfChart(arr[index],this.groundTemperatureResult);
        else if (index === 4)
            this.getCheckLayerOfChart(arr[index],this.pressureResult);
        else if (index === 5)
            this.getCheckLayerOfChart(arr[index],this.humidityResult);
    }

    this.isolineAndSplashMapClick = function () {
        $('#contour').off('click').on('click',function (e) {
            if($(e.target).prop('checked')){
                if($('.layer-text a.action').attr('val') != null){
                    if (this.MapInfo.ContourLayer != null) {
                        this.MapInfo.Map.removeLayer(this.MapInfo.ContourLayer);
                    }
                    this.defineArray($('.layer-text a.action').attr('val'));
                }
            }else{
                if (this.MapInfo.ContourLayer != null)
                    this.MapInfo.Map.removeLayer(this.MapInfo.ContourLayer);
            }
        }.bind(this))

        $('#color-figure').off('click').on('click',function (e) {
            if($(e.target).prop('checked')){
                if($('.layer-text a.action').attr('val') != null)
                    if (this.MapInfo.layer != null) {
                        this.MapInfo.Map.removeLayer(this.MapInfo.layer);
                    }
                //this.MapInfo.PlotColor('');
                $(".color-control").attr("style", "display: none")
                this.defineArray($('.layer-text a.action').attr('val'));
            }else{
                if (this.MapInfo.layer != null)
                    this.MapInfo.Map.removeLayer(this.MapInfo.layer);
                //this.MapInfo.PlotColor('');
                $(".color-control").attr("style", "display: none")
            }
        }.bind(this))
    }
}

$(function () {
    var groundDetection = new GroundDetection();
    groundDetection.Startup();
});