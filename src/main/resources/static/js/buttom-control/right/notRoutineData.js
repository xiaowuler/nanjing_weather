var NotRoutineData = function () {
    
    this.typeCodes = [];
    
    this.init = function (dataOne,dataTwo) {
        $(dataOne).each(function (index,dataState) {
            var typeCode = new DataTypeCode(dataState.productTypeCode);
            typeCode.init(dataState.dataArrivalLitter);
            this.typeCodes.push(typeCode);
        }.bind(this))

        if(dataTwo != null){
            $(dataTwo).each(function (index,dataState) {
                var typeCode = new DataTypeCode(dataState.productTypeCode);
                typeCode.init(dataState.dataArrivalLitter);
                this.typeCodes.push(typeCode);
            }.bind(this))
        }
    }
    
    this.writeHtml = function () {
        var size = 100;
        $(this.typeCodes).each(function (index,typeCode) {
            if(size > typeCode.getSize()){
                size = typeCode.getSize();
            }
        }.bind(this))

        var k = 0;
        var index = 0;
        var textHtml = '<div class="slide-time-txt"><span>非常规数据采集</span></div>';

        for(var i=0;i<this.typeCodes.length;i++){
            if(this.typeCodes[i].getName() != '风廓线'){
                if(k%2 == 0)
                    textHtml += '<div class="slide-txt"><span>{0}</span></div>'.format(this.typeCodes[i].name);
                else
                    textHtml += '<div class="slide-txt slide-txt-blue"><span>{0}</span></div>'.format(this.typeCodes[i].name);
                k++;
            }else {
                index = i;
            }
        }

        textHtml += '<div class="slide-txt"><span class="font-color">时间</span></div><div class="slide-txt slide-txt-blue"><span>{0}</span></div>'.format(this.typeCodes[index].name);
        $("#notRoutine-little").html(textHtml);

        textHtml = '';
        for(var j=0;j<size;j++){
            var centerParm = '';
            for(var i=0;i<this.typeCodes.length;i++){
                if(this.typeCodes[i].getName() != '风廓线'){
                    if(i==0)
                        textHtml += this.typeCodes[i].writeHtml(j,'start');
                    else
                        textHtml += this.typeCodes[i].writeHtml(j,'center');
                }else {
                    centerParm += this.typeCodes[i].writeHtml(j,'end')
                }
            }
            textHtml += centerParm;
            /*console.log("***************************************************")
            console.log(textHtml)
            console.log("***************************************************")*/
        }

        /*console.log(textHtml)*/
        $("#notRoutine-data").html(textHtml);
        this.Slide();
    }

    this.Slide = function () {
        var swiper = new Swiper('.swiper-left', {
            slidesPerView: 6,
            loop: true,
            nextButton: '.swiper-next-left',
            prevButton: '.swiper-prev-left'
        });
        var swiper = new Swiper('.swiper-right', {
            slidesPerView: 6,
            loop: true,
            nextButton: '.swiper-next-right',
            prevButton: '.swiper-prev-right'
        });
    };
}