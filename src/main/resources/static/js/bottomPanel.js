var BottomPanel = function () {

    this.requestPath = '';

    this.Startup = function () {
        this.selectState();
    }

    this.selectState = function () {
        setTimeout(this.selectState, 1000 * 60 * 5);
        $.post(this.requestPath + "dataArrivals/findState", function (data) {

            var routineData = new RoutineData();
            routineData.init(data[0])
            routineData.writeHtml();

            var notRoutineData = new NotRoutineData();
            if(data.size() == 3)
                notRoutineData.init(data[1],data[2]);
            else
                notRoutineData.init(data[1],null);

        }.bind(this))
    }.bind(this)
}