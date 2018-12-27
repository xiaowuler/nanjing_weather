var LeftPanel = function () {

    this.requestPath = '';

    this.laserRadar = null;
    this.windProfile = null;
    this.gpsMet = null;
    this.microwaveRadiation = null;
    this.raindropSpectrum = null;

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

    this.autoChang = function () {

        setInterval(function () {
            if (this.playWheelTime != null)
                clearInterval(this.playWheelTime);
            this.getPicture();
        }.bind(this), 1000 * 60 * 5)

    }
}
