var Category = function (categoryId, regionId, regionImg) {
    this.name = null;
    this.regions = [];
    this.regionPlayIndex = 0;
    this.categoryId = categoryId;
    this.regionId = regionId;
    this.regionImg = regionImg;

    this.setRegionPlayIndex = function (regionIndex, productIndex) {
        if (regionIndex != null) {
            this.regionPlayIndex = regionIndex;
        }
        this.regions[this.regionPlayIndex].setProductPlayIndex(productIndex);
    }

    this.Init = function (category) {

        var textHtml = '';
        this.name = category.categoryCode;

        $(category.productRegion).each(function (index, productRegion) {
            var region = new Region(this.name, this.regionId, this.regionImg);
            region.Init(this.setParmTextCallBack.bind(this),productRegion);
            this.regions.push(region);
            if (category.productRegion.length == 1) {
                if (index == 0) {
                    textHtml += '<a href="javascript:;" class="action theme-border">{0}</a>'.format(productRegion.name)
                } else {
                    textHtml += '<a href="javascript:;" class="theme-border">{0}</a>'.format(productRegion.name)
                }
            } else {
                if (index == 0) {
                    textHtml += '<a href="javascript:;" class="action">{0}</a>'.format(productRegion.name)
                } else {
                    textHtml += '<a href="javascript:;">{0}</a>'.format(productRegion.name)
                }
            }
        }.bind(this))

        $(this.categoryId).html(textHtml);
        this.areaTextclick();
    }

    this.Play = function () {

        this.regions[this.regionPlayIndex].Play(this.RegionPlayCompleteCallback.bind(this));
        $(this.categoryId).parents(".theme-area").find(".theme-area-table a").eq(this.regionPlayIndex).addClass("action").siblings().removeClass("action");
    }

    this.RegionPlayCompleteCallback = function () {

        this.regionPlayIndex++;
        if (this.regionPlayIndex > this.regions.length - 1) {
            this.regionPlayIndex = 0;
        }
        $(this.categoryId).parents(".theme-area").find(".theme-area-table a").eq(this.regionPlayIndex).addClass("action").siblings().removeClass("action");
        this.regions[this.regionPlayIndex].Play(this.RegionPlayCompleteCallback.bind(this));
    }

    this.areaTextclick = function () {
        $("{0} a".format(this.categoryId)).on('click',function (e) {
            $(e.target).addClass("action").siblings().removeClass("action");
            $(e.target).parents(".theme-area").next().find(".theme-area-table a").eq(0).addClass("action").siblings().removeClass("action");
            var index = $(e.target).index();
            this.setRegionPlayIndex(index, 0);
        }.bind(this))
    }
    
    this.setParmTextCallBack = function (time) {
        this.setRegionPlayIndex(this.regionPlayIndex, time);
    }

}