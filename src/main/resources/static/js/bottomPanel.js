var BottomPanel = function (parent) {
    this.parent = parent;
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
            if(data.length == 3)
                notRoutineData.init(data[1],data[2]);
            else
                notRoutineData.init(data[1],null);
            notRoutineData.writeHtml();

            this.Slide();
        }.bind(this))
    }.bind(this)

    this.Slide = function () {
        this.parent.Swiper1.detachEvents();
        this.parent.Swiper1 = new Swiper('.swiper-left', {
            slidesPerView: 5,
            watchOverflow: true,
            nextButton: '.swiper-next-left',
            prevButton: '.swiper-prev-left'
        });

        this.parent.Swiper2.detachEvents();
        this.parent.Swiper2 = new Swiper('.swiper-right', {
            slidesPerView: 5,
            watchOverflow: true,
            nextButton: '.swiper-next-right',
            prevButton: '.swiper-prev-right'
        });

        this.parent.Swiper1.slideTo(7);
        this.parent.Swiper2.slideTo(7);
    };
}