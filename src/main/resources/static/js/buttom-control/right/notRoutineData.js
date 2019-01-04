var NotRoutineData = function () {
    
    this.typeCodes = [];
    
    this.init = function (dataOne,dataTwo) {
        $(dataOne).each(function (index,dataState) {
            var typeCode = new TypeCode(dataState.productTypeCode);
            typeCode.init(dataState.dataArrivalLitter);
            this.typeCodes.push(typeCode);
        }.bind(this))

        if(dataTwo != null){
            $(dataTwo).each(function (index,dataState) {
                var typeCode = new TypeCode(dataState.productTypeCode);
                typeCode.init(dataState.dataArrivalLitter);
                this.typeCodes.push(typeCode);
            }.bind(this))
        }
    }
}