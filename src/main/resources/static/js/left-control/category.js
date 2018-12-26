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
            region.Init(productRegion)
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

}