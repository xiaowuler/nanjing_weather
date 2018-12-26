var LeftPanel = function () {

    this.requestPath = '';

    this.laserRadar = null;
    this.windProfile = null;
    this.gpsMet = null;
    this.microwaveRadiation = null;
    this.raindropSpectrum = null;

    this.result = null;
    this.playWheelTime = null;

    this.Startup = function () {

        this.onLoadGetPicture();
        this.autoChang();

    }

    this.onLoadGetPicture = function () {
        this.getPicture();
    }

    this.getPicture = function () {
        $.post(this.requestPath + "products/findAllByTypeAndArea", function (data) {
            this.result = data;

            var laserRadar = new Category('#laserRadarArea', "#laserRadarParm", "#laserRadarImg");
            laserRadar.Init(data[2]);
            this.laserRadar = laserRadar;

            var microwaveRadiation = new Category('#microwaveRadiationArea', "#microwaveRadiationParm", "#microwaveRadiationImg");
            microwaveRadiation.Init(data[3]);
            this.microwaveRadiation = microwaveRadiation;

            var windProfile = new Category('#windProfileArea', "#windProfileParm", "#windProfileImg");
            windProfile.Init(data[0]);
            this.windProfile = windProfile;

            var raindropSpectrum = new Category('#raindropSpectrumArea', "#raindropSpectrumParm", "#raindropSpectrumImg");
            raindropSpectrum.Init(data[4]);
            this.raindropSpectrum = raindropSpectrum;

            var gpsMet = new Category('#gpsMetArea', "#gpsMetParm", "#gpsMetImg");
            gpsMet.Init(data[1]);
            this.gpsMet = gpsMet;

            this.playWheel();
            this.Tab();
            this.TabChang();

        }.bind(this))
    }

    this.playWheel = function () {

        var time = setInterval(function () {
            this.laserRadar.Play();
            this.gpsMet.Play();
            this.raindropSpectrum.Play();
            this.microwaveRadiation.Play();
            this.windProfile.Play();
        }.bind(this), 5000)

        this.playWheelTime = time;
    }

    this.Tab = function () {
        $(".theme").each(function () {
            $(".theme-area a").on('click', function (e) {
                $(e.target).addClass("action").siblings().removeClass("action");
                $(e.target).parents(".theme-area").next().find(".theme-area-table a").eq(0).addClass("action").siblings().removeClass("action");
                var parentIndex = $(e.target).parents(".theme").index();
                var index = $(e.target).index();

                if (parentIndex == '0')
                    this.laserRadar.setRegionPlayIndex(index, 0);
                else if (parentIndex == '1')
                    this.windProfile.setRegionPlayIndex(index, 0);
                else if (parentIndex == '2')
                    this.gpsMet.setRegionPlayIndex(index, 0);
                else if (parentIndex == '3')
                    this.microwaveRadiation.setRegionPlayIndex(index, 0);
                else if (parentIndex == '4')
                    this.raindropSpectrum.setRegionPlayIndex(index, 0);

            }.bind(this))
        }.bind(this))
    };

    this.TabChang = function () {

        $(".theme").each(function () {
            $(".theme-title a").on('click', function (e) {
                $(e.target).addClass("action").siblings().removeClass("action");
                var time = $(e.target).index();
                var parentIndex = $(e.target).parents(".theme").index();

                if (parentIndex == '0')
                    this.laserRadar.setRegionPlayIndex(null, time);
                else if (parentIndex == '1')
                    this.windProfile.setRegionPlayIndex(null, time);
                else if (parentIndex == '2')
                    this.gpsMet.setRegionPlayIndex(null, time);
                else if (parentIndex == '3')
                    this.microwaveRadiation.setRegionPlayIndex(null, time);
                else if (parentIndex == '4')
                    this.raindropSpectrum.setRegionPlayIndex(null, time);

            }.bind(this))
        }.bind(this))
    };

    this.autoChang = function () {

        setInterval(function () {
            if (this.playWheelTime != null)
                clearInterval(this.playWheelTime);
            this.getPicture();
        }.bind(this), 1000 * 60 * 5)

    }
}
