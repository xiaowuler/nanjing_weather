var ProductShow = function (activeProductCallback) {
    this.PlayTimer = null;
    this.LeftScroller = null;
    this.RightScroller = null;
    this.ProductContainer = null;
    this.ActiveProductCallback = activeProductCallback;

    this.Init = function (id) {
        var control = $(id);
        this.LeftScroller = control.find('.arrow-left:first');
        this.RightScroller = control.find('.arrow-right:first');
        this.ProductContainer = control.find('ul.imgeUrl:first');

        this.LeftScroller.off('click').on('click', this.ActivePrevious.bind(this));
        this.LeftScroller.off('mousedown').on('mousedown', this.OnLeftScrollerMouseDown.bind(this));
        this.LeftScroller.off('mouseup').on('mouseup', this.ClearTimer.bind(this));

        this.RightScroller.off('click').on('click', this.ActiveNext.bind(this));
        this.RightScroller.off('mousedown').on('mousedown', this.OnRightScrollerMouseDown.bind(this));
        this.RightScroller.off('mouseup').on('mouseup', this.ClearTimer.bind(this));
    }

    this.OnLeftScrollerMouseDown = function () {
        this.ClearTimer();
        this.StartTimer(this.ActivePrevious.bind(this));
    }

    this.OnRightScrollerMouseDown = function () {
        this.ClearTimer();
        this.StartTimer(this.ActiveNext.bind(this));
    }

    this.ClearTimer = function () {
        if (this.PlayTimer !== null)
            clearInterval(this.PlayTimer);
    }

    this.StartTimer = function (callback) {
        this.ClearTimer();
        this.PlayTimer = setInterval(function () {
            callback();
        }.bind(this), 200);
    }

    this.OnProductClick = function (e) {
        var product = $(e.target).parents('li');
        this.SetActiveProduct(product);
    }

    this.Load = function (products) {
        if (products === null || products.length === 0)
            return;

        // Initialize products
        this.ProductContainer.empty();
        $(products).each(function (index, product) {
            var child = this.CreateProduct(product);
            this.ProductContainer.prepend(child);
        }.bind(this));

        // Active the last product
        var last = products[0];
        this.SetActiveProduct($('#' + last.id));

        // Bind product event
        this.ProductContainer.find('li').on('click', this.OnProductClick.bind(this));

        //if(products.length < 11)
        this.HandlerPicHide(products.length);
    }

    this.HandlerPicHide = function (count) {
        for(var i = 0; i < count-1; i++){
            this.LeftScroller.trigger("click");
        }

        for(var i = 0; i < count-1; i++){
            this.RightScroller.trigger("click");
        }
    }

    this.ActivePrevious = function () {
        var product = this.GetActiveProduct();
        var previous = product.prev().find('img:first');
        if (previous != null)
            previous.trigger('click');
    }

    this.ActiveNext = function () {
        var product = this.GetActiveProduct();
        var next = product.next().find('img:first');
        if (next.length > 0)
            next.trigger('click');
        else
        {
            product = this.GetFirstProduct();
            if (product == null)
                return;

            product.find('img:first').trigger('click');
        }
    }

    this.CreateProduct = function (product) {
        var template = '<li id="{0}"><div><img src="{1}"><p>{2}</p></div></li>';
        return template.format(product.id, product.url, product.dataTime);
    }

    this.GetActiveProduct = function () {
        var elements = this.ProductContainer.find('li.action ');
        return elements.length > 0 ? $(elements[0]) : null;
    }

    this.GetFirstProduct = function () {
        var elements = this.ProductContainer.find('li:first ');
        return elements.length > 0 ? $(elements[0]) : null;
    }

    this.ClearActiveProduct = function () {
        var product = this.GetActiveProduct();
        if (product != null)
            product.removeClass('action');
    }

    this.SetActiveProduct = function (product) {
        this.ClearActiveProduct();
        product.addClass('action');
        this.MakeProductVisible(product);

        if (this.ActiveProductCallback !== null)
            this.ActiveProductCallback(product);
    }

    this.MakeProductVisible = function (product) {
        var productOffset = this.GetElementOffset(product);
        var leftScrollerOffset = this.GetElementOffset(this.LeftScroller);
        var rightScrollerOffset = this.GetElementOffset(this.RightScroller);
        var containerPosition = this.ProductContainer.position();

        if (productOffset.left < leftScrollerOffset.right) {
            var left = containerPosition.left + (leftScrollerOffset.right - productOffset.left);
            this.ProductContainer.css('left', left);
        }
        else if (productOffset.right > rightScrollerOffset.left) {
            var left = containerPosition.left + (rightScrollerOffset.left - productOffset.right);
            this.ProductContainer.css('left', left);
        }
    }

    this.GetElementOffset = function (element) {
        var position = element.offset();
        position.right = position.left + element.width();
        position.bottom = position.top + element.height();
        return position;
    }
}