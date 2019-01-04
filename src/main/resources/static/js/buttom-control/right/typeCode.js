var TypeCode = function (name) {
    this.name = name;
    this.arrivals = []

    this.init = function (typeCode) {
        $(typeCode).each(function (index,dataArrivalLitter) {
            var arrival = new Arrival(dataArrivalLitter.time);
            arrival.init(dataArrivalLitter.centerDataArrival);
            this.arrivals.push(arrival);
        }.bind(this))
    }
}