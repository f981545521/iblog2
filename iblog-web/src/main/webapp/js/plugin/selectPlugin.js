(function ($, window, document, undefined) {

        var mySelects = {};

        var MySelect = function (elem, options) {
            var $this = this;
            $this.domid = Math.floor(Math.random() * 1000);
            $this.elem = elem;
            $this.$elem = $(elem);
            $this.options = $.extend({}, options);
            $this.options = options;
            $this.$win = $(window);
            $this.sections = {};
        };
        MySelect.prototype = {
            defaults: {
                navItems: 'a',
                currentClass: 'current',
                changeHash: false,
                easing: 'swing',
                filter: '',
                scrollSpeed: 750,
                scrollThreshold: 0.5,
                begin: false,
                end: false,
                scrollChange: false
            },
            init: function () {
                var _this = this;
                var eleId = _this.elem.attr("id");
                mySelects[eleId] = _this;
                console.log(_this);
                console.log("调用了init方法" + _this.domid);
                var domTemplate = "<button type='button' id='"+_this.domid+"' class='selectButton'>selectPlugin</button>";
                _this.elem.append(domTemplate);

                //不可以获取 _this，可以通过第三个参数传入：_this
                $(document).on("click", ".selectButton", _this, function (e, b) {
                    console.log(e)
                    _this.sayHello();
                });
                //可以获取 _this
                _this.$elem.on("click", ".selectButton", function () {
                    var $el = $(this);
                    console.log(_this)
                });
                //不可以获取 _this
                $("#" + _this.domid).on("click", function () {
                    console.log("OK")
                });
                _this.destroy();
            },
            destroy: function (e) {
                var $this = this;
                console.log("调用了destroy方法" + $this.options.type)
            },
            sayHello: function () {
                console.log("sayHello 你好！")
            }
        };
        $.fn.mySelect = function (options) {
            var select = new MySelect(this, options);
            select.init();
            return select;
        };
        $.fn.mySelect.getSelectObj = function (eleId) {
            return mySelects[eleId];
        }
    }
)(jQuery, window, document);
