
    var swiper1 = new Swiper('#swiper-banner', {
        pagination: '.swiper-pagination',//开启分页器
       // paginationHide: true,//这个选项设置为true时点击Swiper会隐藏/显示分页器
        paginationClickable: true,//此参数设置为true时，点击分页器的指示点分页器会控制Swiper切换。
        centeredSlides: true,//设定为true时，活动块会居中，而不是默认状态下的居左。
        autoplay: 2500,//自动播放
        autoplayDisableOnInteraction: false,//当设置为false时，用户操作之后（swipes,arrow以及pagination 点击）autoplay不会被禁掉，用户操作之后每次都会重新启动autoplay
        onTouchMove: function (swiper) {
            if (swiper.isBeginning) {
                swiper.lockSwipeToPrev()
            } else if (swiper.isEnd) {
                swiper.lockSwipeToNext()
            } else {
                swiper.unlockSwipeToNext()
                swiper.unlockSwipeToPrev()
            }
        },
    });
    var swiper2 = new Swiper('#swiper-noticon', {
        centeredSlides: true,//设定为true时，活动块会居中，而不是默认状态下的居左。
        autoplay: 3000,//自动播放
        direction: 'vertical',
        autoplayDisableOnInteraction: false,//当设置为false时，用户操作之后（swipes,arrow以及pagination 点击）autoplay不会被禁掉，用户操作之后每次都会重新启动autoplay
    });


    var swiper3 = new Swiper('#cyber-banner', {
        autoHeight: true,
        pagination: '.swiper-pagination',//开启分页器
        paginationHide: true,//这个选项设置为true时点击Swiper会隐藏/显示分页器
        paginationClickable: true,//此参数设置为true时，点击分页器的指示点分页器会控制Swiper切换。
        centeredSlides: true,//设定为true时，活动块会居中，而不是默认状态下的居左。
        autoplay: 2500,//自动播放
        onTouchMove: function (swiper) {
            if (swiper.isBeginning) {
                swiper.lockSwipeToPrev()
            } else if (swiper.isEnd) {
                swiper.lockSwipeToNext()
            } else {
                swiper.unlockSwipeToNext()
                swiper.unlockSwipeToPrev()
            }
        },
    });

    $(".weic-article-list").children(".weic-article-box:last-child").find(".weic-artrow").removeClass("weic-artrow")
    $(".weic-article-list").children(".weui-cell_swiped:last-child").find(".weic-artrow").removeClass("weic-artrow")
