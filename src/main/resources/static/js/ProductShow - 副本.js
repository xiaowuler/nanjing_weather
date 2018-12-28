var App = function (){
    this.length = 0;
    this.dWidth = $('.detail-tab').width();
    this.tabNum = 0;
    this.tabWidth = dWidth / 7;
    this.number = 0;
    this. index = 0;
    this.showNum = 7;

    this.Startup = function () {
        this.overview = $('#overview');
        this.length = overview.find('ul li').length;
        this.tab = $('#tab');
        this. tabNum = tab.find('ul li').length;
        $('#prev').on('click', this.OnPrevButtonClick);
        $('#next').on('click', this.OnNextButtonClick);
    }

  var Product = function (url) {
    this.url = url;
   }

  var Data = function () {
    this.ProductData=[];
    this.Init = function (data) {
        var imgHtml ='';
        var imgHtml2 = '';
        $(data).each(function (index,product) {

            var pro = new Product(product.url);
            this.ProductData.push(pro);

            var url=product.url;
            var arr=url.split("/")[url.split("/").length-1].split(".")[0];
            var time=arr.substr(0,2);
            imgHtml = '<li ><div>'+'<img src="'+url+'" class="width100">'+ '<p>'+time+'时</p></div></li>'+ imgHtml;
            imgHtml2 = '<li ><div><div>'+'<img src="'+url+'">'+'</div></div></li>' + imgHtml2;
        }.bind(this))
        $('.imgeUrl2').html(imgHtml2);
        $('.imgeUrl').html(imgHtml);
    }

}

    this.OnPrevButtonClick = function () {
       this.PrevElement();
    };

    this.OnNextButtonClick = function () {
    this.NextElement();
    };


    this.PrevElement = function () {
    if (number == 0) {
        number = length;
      }
    if (index == 0) {
        index = length
      }
    number--;
    index--;
    this.ElementsSlide(number);
    this.SmallElementsTab(index);
     };

   this.NextElement = function () {
    if (number == length - 1) {
        number = -1;
     }
    if (index == length - 1) {
        index = -1;
     }
      index++;
    this.SmallElementsTab(index);
      number++;
    this.ElementsSlide(number);
    };

 /* this.SmallElementsTab = function (index) {
    var num = index - showNum + 2;
    var tWidth = -(num * tabWidth);
    tab.find('ul li').css('float', 'left');
    if (tabNum > 7) {
        if (index < 6) {
            tWidth = 0;
        }
        if (index == tabNum - 1) {
            tWidth = -(num - 1) * tabWidth;
        }
        tab.find('ul').stop().animate({'left': tWidth}, 600);
    }
  };*/

   /*this.ElementsSlide = function (number) {
    overview.find('ul li').eq(number).css("display", "block").siblings().css("display", "none");
    tab.find('ul li').eq(number).addClass('action').siblings(this).removeClass('action');
     };

  }*/


   var app = null;
   $(document).ready(function() {
    app = new App();
    app.Startup();

  });