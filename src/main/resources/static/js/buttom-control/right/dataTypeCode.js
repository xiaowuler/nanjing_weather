var DataTypeCode = function (name) {
    this.name = name;
    this.arrivals = []

    this.init = function (typeCode) {
        $(typeCode).each(function (index,dataArrivalLitter) {
            var arrival = new DataArrival(dataArrivalLitter.time);
            arrival.init(dataArrivalLitter.centerDataArrival);
            this.arrivals.push(arrival);
        }.bind(this))
    }
    
    this.getSize = function () {
        return this.arrivals.length;
    }

    this.getName = function () {
        return this.name;
    }
    
    this.writeHtml = function (size,flag) {
        return this.arrivals[size].writeHtml(flag);
    }
}