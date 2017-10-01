/* beta 0.5
這個library需要include Backbone 和 Backbone.Datagrid
*/
(function($) {
    //針對Backbone.Datagrid的修改！
    var abstract_form_cell = {
        default_value: "",
        useTemplate: true,
        cellClassName: "",
        view: function(o) {
            var rule = '';
            if (this.validate) {
                for (var key in this.validate) {
                    rule += key + "=" + this.validate[key] + " ";
                }
            }
            // return $('<input type="text" name="' + this.property + '" placeholder="' + this.title + '" class="form-control text" ' + rule + '>');
            return $('<span class="block input-icon input-icon-right"><input type="text" name="' + this.property + '" placeholder="Please input ' + this.title + '" class="width-100 ' + this.cellClassName + '" ' + rule + '></span>');
        },
        value: function() { //provide value
            return $(this.$el).find('input[name="' + this.property + '"]').val();
        },
        setValue: function(m) { //set the value to this element
            var v = m.get(this.property) || this.default_value;
            $(this.$el).find('input[name="' + this.property + '"]').val(v);
        },
        update: function(m) {

        }
    };

    Backbone.defaultErrorHandler = defaultErrorHandler;
    // finish the overwrite of Backbone.Datagrid

    Backbone.GridView = Backbone.View.extend({
        select: [],
        initialize: function(options) {
            this.options = options;
            var AD = options.AD;
            var that = this;
            // console.log(options);

            //create collection
            this.collection = new AD.Collection();

            //setup template
            this.template = getTplBin(AD.tpl_table || $.config.GridView.table);

            //setup columns
            this.columns = AD.columns;

            var that = this;

            // prototype
            var data = prototype(AD);

            data = _.defaults(data, {
                collection: this.collection,
                paginated: true,
                columns: this.columns,
                rowEditable: false,
                attributes: {
                    "class": "widget-content"
                },
                tableClassName: 'table table-striped table-bordered table-hover dataTable no-footer DTTT_selectable',
                // Miso 2015/03/31
                gridviewTitle: AD.gridviewTitle,
                success: function(model, response, options) {
                    if (response.rtnCode && response.rtnCode < 0) { //if error
                        if (response.rtnCode == -9999)
                            alert(response.rtnMsg);
                        else
                            alert(response.rtnMsg);
                        if (response.rtnCode == -4410 || response.rtnCode == -405)
                            window.location.href = $.config.home_url;
                        else if (response.url)
                            window.location.href = response.url;
                    }
                    that.options.success(model, response, options);
                }
            });
            this.datagrid = new Backbone.Datagrid(data);

            this.$el.html(this.template(this.options.AD));

            this.$el.find('.widget-content').replaceWith(this.datagrid.$el);

            this.$el.unbind();
            this.datagrid.pager.on("change:currentPage", function() {
                (event.preventDefault) ? event.preventDefault(): event.returnValue = false;
                that._clearSelect();
            });

            this.$el.on("click", "input[type='checkbox']", function(event) {
                that._select(event, this);
            });

            this.$el.on("click", ".refresh", function(event) {
                (event.preventDefault) ? event.preventDefault(): event.returnValue = false;
                that.refresh();
            });

            this.$el.on("click", ".remove", function(event) {
                (event.preventDefault) ? event.preventDefault(): event.returnValue = false;
                that.remove();
            });

            this.$el.on("click", ".removeOne", function(event) {
                (event.preventDefault) ? event.preventDefault(): event.returnValue = false;
                if ($(event.target).is(".removeOne"))
                    that.removeOne($(event.target));
                else {
                    var parents = $(event.target).parents(".removeOne");
                    that.removeOne($(parents.first()));
                }

            });

            this.$el.on("change", ".per_page select", function(event) {
                that.per_page(this);
            })

        },
        per_page: function(target) {
            // this.datagrid.page(1);
            this.datagrid.perPage(parseInt($(target).val(), 10));
        },
        removeOne: function(target) {
            var AD = this.options.AD;

            var id = $(target).data("id");

            var idAttr = AD.idAttribute;
            var obj = {};
            obj[idAttr] = id;
            var model = new AD.Model(obj);

            // alert(idAttr);
            // alert(id);
            //var model = this.collection.get(id);
            var that = this;
            model.destroy({
                async: false,
                success: function(model, response, options) {
                    //alert("success?");
                    that.datagrid.refresh();
                    that.select = [];
                    that.trigger("change:select", that.select);
                },
                error: defaultErrorHandler
            });


        },
        remove: function() {
            // alert(this.select);
            var that = this;
            // alert(this.select);
            $.each(this.select, function(i, v) {
                // alert(v);
                var model = that.collection.get(v);
                model.destroy({
                    async: false
                });
            });
            this.select = [];
            this.datagrid.refresh();
            this.trigger("change:select", this.select);
        },
        _clearSelect: function() {
            this.select = [];
            this.trigger("change:select", this.select);
        },

        _select: function(event, target) {
            $t = $(target);
            if ($t.val() === "all") {
                if (typeof $t.attr('checked') === 'undefined') {
                    this.$el.find("input[type='checkbox']").removeAttr('checked');
                } else {
                    this.$el.find("input[type='checkbox']").attr('checked', $t.attr('checked'));
                }
            }

            var array = [];
            this.$el.find("input[type='checkbox'][checked][value!='all']").each(function() {
                array.push($(this).val());
            });

            this.select = array;
            this.trigger("change:select", this.select);
            // alert(that.select);
        },
        refresh: function() {
            this.select = [];
            this.datagrid.filter.clear();
            this.datagrid.sorter.clear();
            this.datagrid.refresh();
        }
    });

    Handlebars.registerHelper('formview_button', function(obj, options) {
        //<i class="ace-icon glyphicon glyphicon-search"></i>查詢
        var cls = obj.class;
        var txt = obj.html;
        var attr = obj.attr;
        var html = '<a class="' + cls + ' btn btn-app btn-xs"';
        if (attr)
            html += attr;
        html += '>' + txt + '</a>';
        // alert(html);
        return html;
    });

    var FromViewConfig = {
        tpl_frame: '<div class="col-xs-4 widget-search-p" style="padding:0"><div class="widget-box widget-search"><div class="widget-header"><h4 class="widget-title">{{{title}}}</h4></div><div class="widget-body"><div class="widget-main"></div></div></div><div class="pull-right">{{#each btns}}{{{formview_button this}}}{{/each}}</div></div>',
        frame_data: {

        },
        tpl_cell: '<div class="form-group has-"><label class="col-xs-4 control-label no-padding-right">{{title}}：</label><div class="col-xs-8 controls edit-cell-content"></div></div>'
    };

    Backbone.FormView = Backbone.View.extend({
        initialize: function(options) {
            var AD = options.AD;
            this.$el = $(this.options.AD.el);
            this.options = options;

            this.template = Handlebars.compile(AD.tpl_frame || FromViewConfig.tpl_frame);

            this.cell_template = Handlebars.compile(AD.tpl_cell || FromViewConfig.tpl_cell);

            this.forms = AD.forms;
            var flen = this.forms.length;
            for (var i = 0; i < flen; i++) {
                spec = this.forms[i];
                _.defaults(spec, abstract_form_cell);
            };

            this.FrameData = AD.frame_data || FromViewConfig.frame_data;

            this._prepare();
        },
        _prepare: function() {
            this.render();
        },
        render: function() {
            this._renderForm(this.model);
            return this;
        },
        _renderForm: function(model) {
            var AD = this.options.AD;

            //先取得template
            var a = this.template(this.FrameData);

            //console.log(this.el);
            //alert($(this.el).length);
            // alert(a);
            this.$el.append(a);
            //產生form
            var $form = $('<form></form>');
            $form.addClass(AD.formClassName || 'form-horizontal'); //硬幹的把className加進去

            //把form加進template中
            this.$el.find(".widget-main").append($form);

            //針對每一個column來進行iteration
            var len = this.forms.length;
            for (var i = 0; i < len; i++) {
                var spec = this.forms[i];
                //用cell_template
                if (spec.useTemplate === true) {
                    var data = {
                        property: spec.property,
                        title: spec.title
                    };
                    $cell = $(this.cell_template(data));
                    $el = spec.view(model); //call external function
                    $cell.find(".edit-cell-content").append($el);
                    $el = $cell;
                } else { //不用cell_template
                    $el = spec.view(model); //call external function
                }
                //把他append到form
                spec.$el = $el;
                $form.append($el);
            }

            // var foot = '<div class="form-actions text-left"><div class="row"><div class="col-md-12">';
            // foot += '<button class="btn btn-primary submit" type="button" > 查詢 </button>';
            // foot += '</div></div>';
            // $form.append(foot);
            // console.log($form);

            // you must add AD.page to anchor tag
            // if (_.isUndefined(AD.page)) {
            //     alert("Bug 修正你的 AD 輸入，FormView必須要有 AD.page");
            //     return;
            // }

            // $form.append('<a href="#' + AD.page + '/grid" class="btn btn-warning">取消並回上一頁</a>');

            //set up default for all property
            for (var i = 0; i < len; i++) {
                var spec = this.forms[i];
                spec.setValue(model);
            }
        }

    });

    Backbone.ProductFormView = Backbone.View.extend({
        initialize: function(options) {
            var AD = options.AD;
            this.options = options;

            this.template = getTplBin(AD.tpl_edit || $.config.FormView.edit);

            this.cell_template = getTplBin(AD.tpl_edit_cell || $.config.FormView.edit_cell);

            this.forms = this.options.AD.forms;
            var flen = this.AD.forms.length;
            for (var i = 0; i < flen; i++) {
                spec = this.forms[i];
                _.defaults(spec, abstract_form_cell);
            };
            this.options = _.defaults(this.options, {
                fromClassName: 'form-horizontal uni',
                Name: 'Form'
            });

            //this._prepare();
        },
        // _prepare: function() {
        //     this.render();
        // },
        render: function() {
            this._renderForm(this.model);
            return this;
        },
        _renderForm: function(model) {

            var AD = this.options.AD;

            this.$el = $(this.el);

            // //產生form
            var $form = $('<form class="form-horizontal uni"></form>');

            var $container = $('<div class="row-fluid"><div class="span12"><div class="widget"></div></div></div>')

            $container.find(".widget").append($form);

            this.$el.append($container);

            var $span;
            var $widget;
            var temp = "";

            //針對每一個column來進行iteration
            var len = this.forms.length;
            var $el;
            for (var i = 0; i < len; i++) {
                var spec = this.forms[i];

                if (_.isString(spec) && /^span/.test(spec) === true) { //span
                    $span = $("<div class='" + spec + "'></div>");
                    $form.append($span);
                } else if (spec["type"] === "widget") { //widget
                    //handle bar job
                    var widget_src = '<div class="widget"><div class="widget-head"><div class="pull-left">{{ title }}</div><div class="clearfix"></div></div><div class="widget-content"><div class="padd"></div></div></div>';
                    var widget_bin = Handlebars.compile(widget_src);

                    $widget = $(widget_bin({
                        title: spec["title"]
                    }));

                    $span.append($widget);
                } else if (_.isObject(spec) && spec.useTemplate === true) { //用cell_template
                    var data = {
                        property: spec.property,
                        title: spec.title
                    };
                    $cell = $(this.cell_template(data));
                    $el = spec.view(model); //call external function
                    $cell.find(".edit-cell-content").append($el);
                    $el = $cell;

                    spec.$el = $el;
                    $widget.find('.padd').append($el);
                } else if (_.isObject(spec) && spec.useTemplate === false) { //不用cell_template
                    $el = spec.view(model); //call external function

                    spec.$el = $el;
                    $widget.find('.padd').append($el);
                }
            }

            var $div = $('<div class="form-actions"></div>');
            $container.append($div);
            $div.append('<a href="#" class="btn btn-info submit">儲存修改</a>');
            $div.append('<a href="#' + AD.page + '/grid" class="btn">取消編輯</a>');

            //set up default for all property
            for (var i = 0; i < len; i++) {
                var spec = this.forms[i];
                if (_.isString(spec)) {
                    continue;
                }

                spec.setValue(model);
            }
        }
    });

    var defaultErrorHandler = function(model, xhr, options) {
        alert("error:" + xhr.responseText);
    };
    /* Router starts */
    Backbone.IGO1 = Backbone.Router.extend({
        render: function() {
            // console.log("render");
            var AD = this.options.AD
            this.navigate("#grid", {
                trigger: true
            });
        },

        initialize: function(options) {
            this.options = options;
            var AD = options.AD;

            this.$gv = $(AD.gv || $.config.IGO1.gv);
            this.$fv = $(AD.fv || $.config.IGO1.fv);
            console.log("initialize");

        },

        show_formview: function(model) {
            console.log("show_formview");
            var that = this;
            var add = model.isNew();
            var AD = this.options.AD;

            this.$fv.empty();
            this.$gv.hide();

            var title = "";

            try {
                if (add == true) {
                    title = AD.addTitle || $.config.IGO1.addTitle
                } else {
                    title = AD.editTitle || $.config.IGO1.editTitle
                }
            } catch (e) {

            }

            this.fv = new Backbone[AD.FormView || $.config.IGO1.FormView]({
                el: this.$fv,
                Name: title,
                model: model, //new Backbone.Model(),
                forms: this.options.AD.forms,
                AD: AD
            });
            // this.fv.render();

            var $form = this.$fv.find('form');
            var foot = '<div class="form-actions text-left">';
            // foot += '<div class="row"><div class="col-md-12">';
            foot += '<button class="btn btn-primary btn-sm submit" type="button"><i class="fa fa-check"></i> 送出</button>';
            foot += ' <button type="button" class="btn btn-default btn-sm" onclick="history.back()"><i class="fa fa-times"></i> 取消</button>';
            // foot += '</div></div>';
            foot += '</div>';
            $form.append(foot);


            this.fv.$el.unbind();
            this.fv.$el.on("click", ".submit", function(event) {
                if ($form.valid()) {
                    if (add == true) {
                        var ret = confirm("Are you sure to add?");
                    } else {
                        var ret = confirm("Are you sure to update?");
                    }

                    if (!ret)
                        return;

                    that.submit(event, model);
                }
            });
            this.$fv.show();

            $form.validate();

        },
        show_add: function() {
            var AD = this.options.AD;
            var model = new AD.Model();
            this.show_formview(model);
        },

        show_edit: function(_id) {
            var that = this;
            var AD = this.options.AD;

            var model = new AD.Model();

            var attrs = {};
            attrs[model.idAttribute] = _id;

            model.set(model.idAttribute, _id);

            model.fetch({
                async: false,
                success: function(model, response, options) {
                    that.show_formview(model);
                },
                error: function(model, response, options) {
                    alert("page not found");
                    window.location.href = $.home_url;
                }
            });
        },

        show_grid: function() {
            // console.log("show_grid");
            $('body,html').animate({
                scrollTop: 0
            }, 500);
            // init GridView
            if (!this.gv) {
                this.gv = new Backbone.GridView({
                    el: this.$gv,
                    AD: this.options.AD

                    // collection: new this.AD.Collection(),
                    // columns: this.AD.columns,
                    // title: this.AD.title,
                    // filter: this.AD.filter,
                    // sort: this.AD.sort
                });
            } else {
                this.gv.refresh();
            }

            this.$fv.hide();
            this.$gv.show();

        },

        submit: function(event, model, $fv, froms) {
            (event.preventDefault) ? event.preventDefault(): event.returnValue = false;

            var AD = this.options.AD;

            var $fv = this.fv.$el;

            var clen = AD.forms.length;

            for (var i = 0; i < clen; i++) {
                var c = AD.forms[i];
                if (_.isObject(c) && !_.isUndefined(c.value)) {
                    var v = c.value();

                    if (_.isObject(v))
                        model.set(v);
                    else
                        model.set(c.property, v);
                }

            }

            if (!_.isUndefined(AD.validate) && !AD.validate(model))
                return;

            var that = this;
            model.save({}, {
                async: false,
                error: AD.error || defaultErrorHandler,
                success: function(model, response, options) {
                    // alert("success?");
                    // console.log(response);
                    if (response.error) {
                        alert(response.remark);
                    } else {
                        for (var i = 0; i < clen; i++) {
                            var c = AD.forms[i];

                            if (_.isObject(c) && !_.isUndefined(c.update)) {
                                var v = c.update(model);
                            }
                        }
                        that.navigate("#grid", {
                            trigger: true
                        });
                    }
                }
            });
        },
        remove: function(_id) {
            // alert("del id=" + _id);
            var AD = this.options.AD;

            var idAttr = AD.idAttribute;
            var model = new AD.Model({
                idAttr: _id
            });
            // alert(model.toJSON);

            var that = this;
            //console.log(this);
            model.destroy({
                async: false,
                success: function(model, response, options) {
                    //alert("success");
                    that.select = [];
                    that.trigger("change:select", that.select);
                },
                error: defaultErrorHandler
            });
            this.render();
        }

    });

    Backbone.View2 = {};
    Backbone.View2.Router = Backbone.Router.extend({
        routes: {
            'add/:_gv': "show_add",
            'edit/:_gv/:_id': 'show_edit',
            'grid': 'show_grid',
            '': "render"
        },

        render: function() {
            this.navigate("#grid", {
                trigger: true
            });
        },

        initialize: function(options) {
            this.AD = options.AD;
            this.options = options;

            this.$gv = $("#gv");
            this.$fv = $("#fv");

        },

        show_add: function(_gv) {
            var that = this;
            this.$fv.empty();
            this.$gv.hide();
            if (_gv == 0)
                var AD = this.AD.upper;
            else
                var AD = this.AD.floor;

            this.fv = new Backbone.FormView({
                el: "#fv",
                Name: "add",
                model: new Backbone.Model(),
                forms: AD.forms
            });
            // this.fv.render();

            var model = new AD.Model();

            this.fv.$el.unbind();
            this.fv.$el.on("click", ".submit", function(event) {
                that.submit(event, model, AD);
            });

            this.$fv.show();

        },

        show_edit: function(_gv, _id) {
            var that = this;
            this.$fv.empty();
            this.$gv.hide();

            if (_gv == 0)
                var AD = this.AD.upper;
            else
                var AD = this.AD.floor;

            // var idAttr=this.AD.Model.idAttribute;
            var attrs = {};
            var model = new AD.Model();
            attrs[model.idAttribute] = _id;

            var collection = new AD.Collection();

            // model.set(model.idAttrib);
            collection.fetch({
                async: false,
                data: {
                    where: attrs
                }
            });

            model = collection.get(_id);

            this.fv = new Backbone.FormView({
                el: "#fv",
                Name: "修改",
                model: model,
                forms: AD.forms
            });
            // this.fv.render();

            this.fv.$el.unbind();
            this.fv.$el.on("click", ".submit", function(event) {
                that.submit(event, model, AD);
            });

            this.$fv.show();
        },

        show_grid: function() {
            $('body,html').animate({
                scrollTop: 0
            }, 500);
            // init GridView
            var that = this;
            var key = this.AD.floor.mapping;
            if (!this.gv) {
                this.gv = [];
                this.gv.push(new Backbone.GridView({
                    el: "#gv0",
                    collection: new this.AD.upper.Collection(),
                    columns: this.AD.upper.columns,
                    title: this.AD.upper.title
                }));
                this.gv[0].$el.find('a[href="#add"]').attr("href", "#add/0");

                this.gv[0].on("change:select", function() {
                    var select = that.gv[0].select;
                    if (select.length == 0)
                        select = [-1];
                    var data = {};
                    data[key] = select;
                    var where_in = new Backbone.Model(data);
                    that.gv[1].datagrid.where_in = where_in;
                    that.gv[1].refresh();
                });

                var data = {};
                data[key] = [-1];
                this.gv.push(new Backbone.GridView({
                    el: "#gv1",
                    collection: new this.AD.floor.Collection(),
                    columns: this.AD.floor.columns,
                    title: this.AD.floor.title,
                    where_in: new Backbone.Model(data)
                }));

                this.gv[1].$el.find('a[href="#add"]').attr("href", "#add/1");
            } else {
                // for (var i = 0; i < this.gv.length; i++)
                //   this.gv[i].refresh();
                this.gv[0].refresh();
                var data = {};
                data[key] = [-1];
                var where_in = new Backbone.Model(data);
                this.gv[1].datagrid.where_in = where_in;
                this.gv[1].refresh();
            }

            this.$fv.hide();
            this.$gv.show();
        },
        submit: function(event, model, AD) {
            (event.preventDefault) ? event.preventDefault(): event.returnValue = false;
            // alert(that.fv.$el);
            var $fv = this.fv.$el;

            var clen = AD.forms.length;

            for (var i = 0; i < clen; i++) {
                var c = AD.forms[i];
                var v = c.value();
                if (_.isObject(v))
                    model.set(v);
                else
                    model.set(c.property, v);
            }

            if (AD.validate && !AD.validate(model))
                return;

            var that = this;
            model.save({}, {
                async: false,
                error: AD.error || defaultErrorHandler,
                success: function(model, response, options) {
                    for (var i = 0; i < clen; i++) {
                        var c = AD.forms[i];
                        var v = c.update(model);
                    }
                    that.navigate("#grid", {
                        trigger: true
                    });
                }
            });
        }
    });

})(jQuery);

(function($) {

    var Router = {
        initialize: function(options) {
            // alert("hello world!");
            // console.log(options);
            this.options = options;
            var AD = options.AD;
            this.$gv = $(AD.gv);
            this.$fv = $(AD.filter.el);
            this.$el = $(AD.el);
            this.$notice = $(AD.notice);
            var that = this;

            if (AD.add && AD.add.el)
                this.$add_el = $(AD.add.el);

            if (AD.edit && AD.edit.el)
                this.$edit_el = $(AD.edit.el);

            // console.log(this.$el);
            // alert(this.hasAdd());
            if (this.hasAdd()) {
                that.$el.unbind('click.igo2.add');
                that.$el.on('click.igo2.add', '.cad-add', function(event) {
                    event.preventDefault();
                    // alert("add!!!!");
                    that.show_add();
                });
            }

            if (this.hasEdit()) {
                that.$el.unbind('click.igo2.edit');
                that.$el.on('click.igo2.edit', '.cad-edit', function(event) {
                    event.preventDefault();
                    var _id = $(this).attr('data-id');
                    // alert("edit!!!!");
                    that.show_edit(_id);
                });
            }
        },
        hideAll: function() {
            if (this.hasAdd())
                this.$add_el.hide();

            if (this.hasEdit())
                this.$edit_el.hide();

            this.$gv.hide();
            this.$fv.hide();
            this.$notice.hide();
        },
        scrollTop: function() {
            $('html, body').animate({ scrollTop: 0 }, 'fast');
        },
        returnFVGV: function() {
            this.hideAll();
            this.$fv.show();
            this.$gv.show();
            if (this.gv)
                this.gv.refresh();
            this.$notice.show();
            this.scrollTop();
        },
        hasAdd: function() {
            return this.$add_el && this.$add_el.length > 0;
        },
        hasEdit: function() {
            return this.$edit_el && this.$edit_el.length > 0;
        },
        show_filter: function() {
            var that = this;
            var AD = this.options.AD

            this.hideAll();

            this.$fv.empty();

            var modelClass = getBBModel(AD);
            var model = new modelClass();

            var viewtype = AD.filter.viewtype;

            var filterAD = _.clone(AD.filter);

            if (!filterAD.frame_data)
                filterAD.frame_data = {};

            var default_frame_data = {
                title: "Search",
                btns: [
                    { class: "cad-search btn-yellow", html: '<i class="ace-icon glyphicon glyphicon-search"></i>Search' }
                ]
            };

            if (this.hasAdd())
                default_frame_data.btns.push({ class: "cad-add btn-success", html: '<i class="ace-icon glyphicon glyphicon-plus"></i>Add' });

            _.defaults(filterAD.frame_data, default_frame_data);

            that.filterFV = new Backbone[viewtype]({
                AD: filterAD,
                model: model
            });

            that.$fv.show();
            this.$notice.show();

            that.filterFV.$el.unbind();
            that.filterFV.$el.on("click", ".cad-search", function(event) {
                event.preventDefault();

                var ret = that.createModelByAD(event, model, that.filterFV.$el, filterAD);

                if (!ret) return;

                that.show_grid(model);
            });

            /*this.$gv.unbind("click.csv");
            this.$gv.on("click.csv", ".csv_submit", function(event) {
                $.ccui.confirm("是否要開啟或儲存這個檔案?", {}, function() {
                    that.csv_submit(event, model);
                });
            });

            this.$fv.unbind("click.csv");
            this.$fv.on("click.csv", ".csv_submit", function(event) {
                $.ccui.confirm("是否要開啟或儲存這個檔案?", {}, function() {
                    that.csv_submit(event, model);
                });
            });*/
        },
        download_excel: function(event, model) {
            (event.preventDefault) ? event.preventDefault(): event.returnValue = false;
            alert("download_excel");
            var AD = this.options.AD;
            var $fv = this.fv.$el;
            var clen = AD.forms.length;

            for (var i = 0; i < clen; i++) {
                var c = AD.forms[i];
                if (_.isObject(c) && !_.isUndefined(c.value)) {
                    var v = c.value();
                    if (_.isObject(v))
                        model.set(v);
                    else
                        model.set(c.property, v);
                }
            }
            if (!_.isUndefined(AD.validate) && !AD.validate(model))
                return;

            var that = this;
            //send backbone collection without pager
            var data = model.toJSON();
            data.order = data.order || AD.order;

            var columns = this.options.AD.columns;
            var coll = new AD.Collection();
            coll.fetch({
                async: false,
                success: function(col) {
                    var data = new Array();
                    var keys = new Array();
                    var n = 0;
                    for (var i = 0; i < columns.length; i++) {
                        if (columns[i].csv != false)
                            keys[i] = columns[i].property;
                    }
                    data[0] = keys;

                    var info = coll.at(coll.length - 1);
                    coll.remove(info);
                    var jcol = col.toJSON();


                    for (var i = 0; i < jcol.length; i++) {
                        // console.log(jcol[i]);
                        var detail = new Array();
                        for (var j = 0; j < columns.length; j++) {
                            if (columns[j].csv != false) {
                                var val = "";
                                try {
                                    if (columns[j].view && columns[j].view.callback) {
                                        val = columns[j].view.callback.call(columns[j], jcol[i]);
                                        var regexp = new RegExp('[,"\\n]');
                                        if (regexp.test(val))
                                            val = '"' + val + '"';


                                    } else {
                                        val = jcol[i][columns[j].property]
                                    }
                                    detail[j] = val;
                                } catch (e) {

                                }
                            }
                        }
                        data[i + 1] = detail;
                        // console.log(detail);
                    }

                    var csvContent = ""; //"data:text/csv;charset=utf-8,";
                    var index = columns.length;
                    data.forEach(function(infoArray, index) {
                        dataString = infoArray.join(",");
                        //console.log(dataString);
                        csvContent += dataString + "\n";
                    });

                    // var encodedUri = encodeURI(csvContent);

                    var blobdata = new Blob([csvContent], {
                        type: "text/excel",
                        encoding: "UTF-8"
                    });

                    var link = document.createElement("a");
                    link.setAttribute("href", window.URL.createObjectURL(blobdata));
                    link.setAttribute("download", "download.xls");
                    link.click(); // This will download the data file named "csvDownload.csv".

                },
                error: function() {
                    alert("csv error");
                },
                data: data
            });

        },
        csv_submit: function(event, model) {
            (event.preventDefault) ? event.preventDefault(): event.returnValue = false;
            alert("csv_submit");
            var AD = this.options.AD;
            var $fv = this.fv.$el;
            var clen = AD.forms.length;

            for (var i = 0; i < clen; i++) {
                var c = AD.forms[i];
                if (_.isObject(c) && !_.isUndefined(c.value)) {
                    var v = c.value();
                    if (_.isObject(v))
                        model.set(v);
                    else
                        model.set(c.property, v);
                }
            }
            if (!_.isUndefined(AD.validate) && !AD.validate(model))
                return;

            var that = this;
            //send backbone collection without pager
            var data = model.toJSON();
            data.order = data.order || AD.order;

            // {
            //     startdate: model.get("startdate").split('-').join(""),
            //     enddate: model.get("enddate").split('-').join(""),
            //     delivery_district_id: model.get("delivery_district_id"),
            //     order: "os_delivery_duedate desc"
            // }

            var columns = this.options.AD.columns;
            var coll = new AD.Collection();
            coll.fetch({
                async: false,
                success: function(col) {
                    var data = new Array();
                    var keys = new Array();
                    var n = 0;
                    for (var i = 0; i < columns.length; i++) {
                        if (columns[i].csv != false)
                            keys[i] = columns[i].property;
                    }
                    data[0] = keys;

                    var info = coll.at(coll.length - 1);
                    coll.remove(info);
                    var jcol = col.toJSON();


                    for (var i = 0; i < jcol.length; i++) {
                        // console.log(jcol[i]);
                        var detail = new Array();
                        for (var j = 0; j < columns.length; j++) {
                            if (columns[j].csv != false) {
                                var val = "";
                                try {
                                    if (columns[j].view && columns[j].view.callback) {
                                        val = columns[j].view.callback.call(columns[j], jcol[i]);
                                        var regexp = new RegExp('[,"\\n]');
                                        if (regexp.test(val))
                                            val = '"' + val + '"';


                                    } else {
                                        val = jcol[i][columns[j].property]
                                    }
                                    detail[j] = val;
                                } catch (e) {

                                }
                            }
                        }
                        data[i + 1] = detail;
                        // console.log(detail);
                    }

                    var csvContent = ""; //"data:text/csv;charset=utf-8,";
                    var index = columns.length;
                    data.forEach(function(infoArray, index) {
                        dataString = infoArray.join(",");
                        //console.log(dataString);
                        csvContent += dataString + "\n";
                    });

                    // var encodedUri = encodeURI(csvContent);

                    var blobdata = new Blob([csvContent], {
                        type: "text/csv",
                        encoding: "UTF-8"
                    });

                    var link = document.createElement("a");
                    link.setAttribute("href", window.URL.createObjectURL(blobdata));
                    link.setAttribute("download", "csvdownload.csv");
                    link.click(); // This will download the data file named "csvDownload.csv".

                },
                error: function() {
                    alert("csv error");
                },
                data: data
            });

        },
        createModelByAD: function(event, model, $fv, AD) {
            (event.preventDefault) ? event.preventDefault(): event.returnValue = false;
            // var AD = this.options.AD;
            // var $fv = this.filterFV.$el;

            var forms = AD.forms;
            var clen = forms.length;
            for (var i = 0; i < clen; i++) {
                var c = forms[i];
                if (_.isObject(c) && !_.isUndefined(c.value)) {
                    var v = c.value();
                    if (c.validateFunction && !c.validateFunction(v)) {
                        return false;
                    }
                    if (_.isObject(v)) { model.set(v); } else
                        model.set(c.property, v);
                }
            }
            if (!_.isUndefined(AD.validate) && !AD.validate(model))
                return false;

            return true;
        },
        show_grid: function(model) {
            var that = this;
            var AD = this.options.AD;
            var columns = this.options.AD.columns;

            var data = model.toJSON();
            data.order = data.order || AD.order;
            var gvAD = {
                    Collection: getBBCollection(AD),
                    data: data,
                    columns: AD.columns,
                    tpl_table: AD.tpl_table
                }
                // 建gv
            this.gv = new Backbone.GridView({
                el: that.$gv,
                AD: gvAD,
                success: function() {
                    that.$fv.show();
                    that.$gv.show();
                    $('#notice').show();
                }
            });
        },
        show_add: function(model) {
            var AD = this.options.AD;
            var modelClass = getBBModel(AD.add.create_api);
            var model = new modelClass();

            var that = this;
            var add = model.isNew();

            this.hideAll();

            this.$add_el.empty();

            var viewtype = AD.add.viewtype;

            var addAD = _.clone(AD.add);

            if (!addAD.frame_data)
                addAD.frame_data = {};

            _.defaults(addAD.frame_data, {
                title: "add",
                btns: [
                    { class: "cad-addedit-cancel btn-warning", html: '<i class="ace-icon fa fa-undo"></i>cancel' },
                    { class: "cad-addedit-submit btn-primary", html: '<i class="ace-icon fa fa-floppy-o"></i>submit' }
                ]
            })

            this.addFV = new Backbone[viewtype]({
                AD: addAD,
                model: model
            });

            this.$add_el.unbind("click.cancel");
            this.$add_el.on("click.cancel", ".cad-addedit-cancel", function(event) {
                event.preventDefault();
                that.returnFVGV();
            });


            this.$add_el.unbind("click.sumbit");
            this.$add_el.on("click.sumbit", ".cad-addedit-submit", function(event) {
                event.preventDefault();

                var ret = that.createModelByAD(event, model, that.$add_el, addAD);

                if (!ret) return;

                var ret = confirm("Are you sure to add?");

                if (!ret) return;

                model.save({}, {
                    async: false,
                    error: AD.error || Backbone.defaultErrorHandler,
                    success: function(model, response, options) {
                        if (response.rtnCode < 0) {
                            alert(response.rtnMsg);
                        } else {
                            var idAttribute = model.idAttribute;
                            alert("add success / id:" + response[idAttribute]);
                            for (var i = 0; i < addAD.forms.length; i++) {
                                var c = addAD.forms[i];

                                if (_.isObject(c) && !_.isUndefined(c.update)) {
                                    var v = c.update(model);
                                }
                            }
                            that.returnFVGV();
                        }
                    }
                });
            });

            this.$add_el.show();
            this.scrollTop();
        },
        show_edit_detail: function(model) {
            var AD = this.options.AD;

            var that = this;

            this.hideAll();

            this.$edit_el.empty();

            var viewtype = AD.edit.viewtype;

            var editAD = _.clone(AD.edit);

            if (!editAD.frame_data)
                editAD.frame_data = {};

            _.defaults(editAD.frame_data, {
                title: "Update",
                btns: [
                    { class: "cad-addedit-cancel btn-warning", html: '<i class="ace-icon fa fa-undo"></i>cancel' },
                    { class: "cad-addedit-submit btn-primary", html: '<i class="ace-icon fa fa-floppy-o"></i>submit' }
                ]
            })

            var _id = model.get(model.idAttribute);
            this.editFV = new Backbone[viewtype]({
                AD: editAD,
                model: model
            });

            this.$edit_el.unbind("click.cancel");
            this.$edit_el.on("click.cancel", ".cad-addedit-cancel", function(event) {
                event.preventDefault();
                that.returnFVGV();
            });


            this.$edit_el.unbind("click.sumbit");
            this.$edit_el.on("click.sumbit", ".cad-addedit-submit", function(event) {
                event.preventDefault();

                var updateModelClass = getBBModel(AD.edit.update_api);
                var updateModel = new updateModelClass();

                updateModel.set(model.idAttribute, _id);

                var ret = that.createModelByAD(event, updateModel, that.$edit_el, editAD);

                if (!ret) return;

                var ret = confirm("Are you sure to update?");

                if (!ret) return;

                updateModel.save({}, {
                    async: false,
                    error: AD.error || Backbone.defaultErrorHandler,
                    success: function(model, response, options) {
                        if (response.rtnCode < 0) {
                            alert(response.rtnMsg);
                        } else {
                            var idAttribute = model.idAttribute;
                            alert("update success / id:" + response[idAttribute]);
                            for (var i = 0; i < editAD.forms.length; i++) {
                                var c = editAD.forms[i];

                                if (_.isObject(c) && !_.isUndefined(c.update)) {
                                    var v = c.update(model);
                                }
                            }
                            that.returnFVGV();
                        }
                    }
                });
            });

            this.$edit_el.show();
            this.scrollTop();
        },
        show_edit: function(_id) {
            var that = this;
            var AD = this.options.AD;

            var modelClass = getBBModel(AD.edit.read_api);
            var model = new modelClass();

            var attrs = {};
            attrs[model.idAttribute] = _id;

            model.set(model.idAttribute, _id);

            model.fetch({
                async: false,
                success: function(model, response, options) {
                    that.show_edit_detail(model);
                },
                error: Backbone.defaultErrorHandler
            });
        }
    }


    Backbone.IGO2 = Backbone.Router.extend(Router);

    Backbone.IGO2.base = Router;

})(jQuery);

(function($) {
    Backbone.IGO3 = Backbone.Router.extend({
        render: function() {
            var AD = this.options.AD
            this.navigate("#" + AD.page + "/grid", {
                trigger: true
            });
        },

        initialize: function(options) {
            this.options = options;
            var AD = options.AD;

            this.$gv1 = $(AD.gv1.gv || $.config.IGO3.gv1);
            this.$gv2 = $(AD.gv2.gv || $.config.IGO3.gv2);

        },

        show_grid: function() {
            $('body,html').animate({
                scrollTop: 0
            }, 500);

            this.$gv1.unbind();
            this.$gv2.unbind();

            // init GridView
            if (!this.gv1) {
                this.gv1 = new Backbone.GridView({
                    el: this.$gv1,
                    AD: this.options.AD.gv1

                });

                this.gv2 = new Backbone.GridView({
                    el: this.$gv2,
                    AD: this.options.AD.gv2
                });

            } else {
                this.gv1.refresh();
                this.gv2.refresh();
            }

            var that = this;


            this.$gv2.on("click", ".addPromo", function(event) {
                var id = $(event.target).data("cid");
                that.addPromo(id);
            })


            this.$gv1.on("click", ".btn-cancel-promo", function(event) {
                var id = $(event.target).data("cid");
                that.del(id);
            });

            this.$gv1.show();
            this.$gv2.show();
        },

        del: function(_id) {
            //alert("del  pp_id=" + _id);

            var AD = this.options.AD;

            var idAttr = AD.gv1.idAttribute;
            var model = new AD.gv1.Model({
                hp_product_id: _id
            });

            var that = this;
            model.destroy({
                async: false,
                success: function(model, response, options) {
                    //alert("success");
                    that.select = [];
                    that.trigger("change:select", that.select);
                },
                error: defaultErrorHandler
            });
            this.gv1.refresh();
            this.gv2.refresh();

        },

        addPromo: function(_id) {
            //alert("addPromo  p_id=" + _id);
            var AD = this.options.AD;

            /*console.log(AD.gv1.url + AD.gv1.idAttribute);
            console.log(new AD.gv1.Collection({}));
            var coll = new Backbone.Datagrid.getCollectionByUrl(AD.gv1.url, AD.gv1.idAttribute);
            coll.fetch({
                async: false,
                data: {
                    "fields": this.fields
                }
            });
            console.log(coll);*/

            var model = new AD.gv2.Model();
            model.save({
                "hp_product_id": _id
            });

            this.gv1.refresh();
            this.gv2.refresh();
        },

        // setWeight: function(_id) {
        //     $("#myModal").modal('show');

        // }

    });
})(jQuery);

(function($) {
    Backbone.UIToolBox = {};
})(jQuery);

(function($) {
    /*
  全部必須
  title: '管理員', //必填
  fields:["MngrID","MngrName","Email"], //必填
  url: ,//必填 抓東西的idAttribute

  idAttribute:,  //option 抓東西的id
  property: 'MngrID', // options..選fields第一個
  label_fields:[], //options...選fields 2~最後
  matcher:["MngrName","Email"], //options，選fields 2~最後
  value_field:"MngrID", //options....選第一個
  name_field:"Name", //options....選第二個
  select, function//options...沒有就空白

  example:

  _.defaults({
      title: '管理員',
      fields: ["MngrID", "MngrName", "Email"],
      url: '../bn/index.php/hotelmngr/gen',
      select: function() {
        try {
          var ret = $.hostel_list.app.gv[0].select;
        } catch(err) {
          return [];
        }
        return ret;
      }
    },Backbone.UIToolBox.autocomp)
 */
    // var template = '';
    // template +='<select multiple="" id="state" name="state" class="select2" data-placeholder="Click to Choose...">';
    // template +='</select>';

    Backbone.UIToolBox.autocomp = {
        view: function(o) {
            // var func = Handlebars.compile(template);
            // var html = func({})
            var html = '<div class="select2 select2-container select2-container-multi"></div>';
            return html;
        },
        setValue: function(m) {
            var that = this;
            var initial_id;
            if (m.get(this.property))
                initial_id = m.get(this.property).ae_user_id;
            this.$el.find(".select2").css("width", '200px');
            //select2
            this.$el.find(".select2").css('width', '200px').css('padding-top', '4px').select2({
                ajax: {
                    url: that.url,
                    dataType: 'json',
                    delay: 0,
                    data: function(term, page) {
                        var ret = {};
                        ret["term"] = term;
                        ret["page"] = page;
                        ret["perPage"] = 10;
                        //alert(term);
                        return ret;
                    },
                    results: function(data) {
                        data.pop();

                        var ret = [];
                        for (var i = 0; i < data.length; i++) {
                            var obj = {};

                            var textarr = [];
                            for (var name in data[i]) {
                                if (data[i].hasOwnProperty(name)) {
                                    if (name.match(/^[A-Za-z]+_id$/i))
                                        obj.id = data[i][name];
                                    else
                                        textarr.push(data[i][name]);
                                }
                            }

                            obj.text = textarr.join('-');
                            ret.push(obj);
                        }

                        return { results: ret };
                    }
                },
                initSelection: function(element, callback) {
                    var initial;
                    initial = initial_id;
                    if (initial !== undefined) {
                        return $.ajax({
                            url: that.url,
                            dataType: "json",
                            data: {
                                term: initial
                            }
                        }).done(function(data) {
                            var results;
                            results = [];
                            results.push({
                                id: data[0].ae_id,
                                text: data[0].ae_user_id + '-' + data[0].ae_name
                            });
                            //console.log(data);
                            callback(results[0]);
                        });
                    } else {
                        var results;
                        results = [];
                        $.login.UserCollection = getBBCollection($.login);
                        var user = new $.login.UserCollection();
                        user.fetch({
                            success: function(collction, response, options) {
                                var tmp = collction.toJSON();
                                $.Session = tmp[0];
                                $.login.admin_emp = tmp[0].admin_emp;
                            }
                        })
                        results.push({
                            id: $.login.admin_emp.ae_id,
                            text: $.login.admin_emp.ae_user_id + '-' + $.login.admin_emp.ae_name
                        });
                        //console.log(data);
                        callback(results[0]);

                    }
                },

                minimumInputLength: 1,
                //placeholder:"請輸入使用者",
                //tags: true,
                allowClear: true,
            }); //.val(initial_id).trigger('change .select2')

        },
        value: function() {
            var v = this.$el.find(".select2").select2('data')['id']; //css('width','200px').css('padding-top','4px').
            return v;
        }
    }
    Backbone.UIToolBox.autoOther = {
        view: function(o) {
            // var func = Handlebars.compile(template);
            // var html = func({})
            var html = '<div class="select2 select2-container select2-container-multi"></div>';
            return html;
        },
        setValue: function(m) {
            var that = this;
            var initial_id;
            if (m.get(this.property))
                initial_id = m.get(this.property);
            this.$el.find(".select2").css("width", '200px');
            //select2
            this.$el.find(".select2").css('width', '200px').css('padding-top', '4px').select2({
                ajax: {
                    url: that.url,
                    dataType: 'json',
                    delay: 0,
                    data: function(term, page) {
                        var ret = {};
                        ret["term"] = term;
                        ret["page"] = page;
                        ret["perPage"] = 10;
                        //alert(term);
                        return ret;
                    },
                    results: function(data) {
                        data.pop();

                        var ret = [];
                        for (var i = 0; i < data.length; i++) {
                            var obj = {};

                            var textarr = [];
                            for (var name in data[i]) {
                                if (data[i].hasOwnProperty(name)) {
                                    if (name.match(/^[A-Za-z]+_id$/i))
                                        obj.id = data[i][name];
                                    else
                                        textarr.push(data[i][name]);
                                }
                            }

                            obj.text = textarr.join('-');
                            ret.push(obj);
                        }

                        return { results: ret };
                    }
                },
                initSelection: function(element, callback) {
                    var initial;
                    initial = initial_id;
                    if (initial !== undefined) {
                        return $.ajax({
                            url: that.url,
                            dataType: "json",
                            data: {
                                term: initial
                            }
                        }).done(function(data) {
                            var results;
                            results = [];
                            results.push({
                                id: data[0].ae_id,
                                text: data[0].ae_user_id + '-' + data[0].ae_name
                            });
                            //console.log(data);
                            callback(results[0]);
                        });
                    } else {//新增時直接取登入人
                        var results;
                        results = [];
                        $.login.UserCollection = getBBCollection($.login);
                        var user = new $.login.UserCollection();
                        user.fetch({
                            success: function(collction, response, options) {
                                var tmp = collction.toJSON();
                                $.Session = tmp[0];
                                $.login.admin_emp = tmp[0].admin_emp;
                            }
                        })
                        results.push({
                            id: $.login.admin_emp.ae_id,
                            text: $.login.admin_emp.ae_user_id + '-' + $.login.admin_emp.ae_name
                        });
                        //console.log(data);
                        callback(results[0]);

                    }
                },

                minimumInputLength: 1,
                //placeholder:"請輸入使用者",
                //tags: true,
                allowClear: true,
            }); //.val(initial_id).trigger('change .select2')

        },
        value: function() {
            var v = this.$el.find(".select2").select2('data')['id']; //css('width','200px').css('padding-top','4px').
            return v;
        }
    }

    // this.$el.find('#select2-multiple-style .btn').on('click', function(e){
    //     var target = $(this).find('input[type=radio]');
    //     var which = parseInt(target.val());
    //     if(which == 2) $('.select2').addClass('tag-input-style');
    //      else $('.select2').removeClass('tag-input-style');
    // });
    // _.defaults({ //form 314
    //     title: '擁有者帳號',
    //     property: 'ar_owner',
    //     url: $.config.server + 'getFKEmp'
    // }, Backbone.UIToolBox.autocomp)

    // public class getFKEmpController : LoggedInAdminApiController
    // {
    //     [HttpGet]
    //     [AdminFunctionFilter(RightsGroupModel.ModulesFlag.FUNCTION_RIGHTS_GROUP_READ)]
    //     public HttpResponseMessage GET()
    //     {
    //         Dictionary<string, string> query = Request.GetQueryNameValuePairs().ToDictionary(x => x.Key, x => x.Value);

    //         InputData input = new InputData();
    //         int RtnCode = input.checkInput(query);

    //         if (RtnCode < 0)
    //         {
    //             ret = getError(RtnCode);
    //             return Request.CreateNoCacheResponse(HttpStatusCode.OK, ret);
    //         }

    //         ret = getSuccess(RightsGroupModel.ModulesCode.OK);

    //         var list = (from c in db.admin_emps
    //                     select new { c.ae_id, c.ae_user_id, c.ae_name }); //linq 10

    //         if (!input.term.IsNullOrEmpty())
    //             list = list.Where(c => c.ae_user_id.Contains(input.term.value) || c.ae_name.Contains(input.term.value));


    //         ret["total_count"] = list.Count();

    //         list = list.Skip(input.page.perPage * (input.page.page - 1)).Take(input.page.perPage);

    //         var l = list.ToList<dynamic>();

    //         l.Add(ret);

    //         return Request.CreateNoCacheResponse(HttpStatusCode.OK, l);

    //     }


    //     #region checkInput
    //     public class InputData
    //     {
    //         public ADString term { get; set; }
    //         public ADPage page { get; set; }

    //         public int checkInput(Dictionary<string, string> query)
    //         {
    //             term = new ADString(query.GetValue("term"));
    //             page = new ADPage(query.GetValue("page"), query.GetValue("perPage"), 1, 10);

    //             return RightsGroupModel.ModulesCode.OK;
    //         }
    //     }
    //     #endregion
    // }
})(jQuery);

(function($) {

    Backbone.UIToolBox.passwordConfirm = {
        pwlen: 8,
        view: function(o) {
            var html = ''
            html += '<input type="password" name="' + this.property + '" placeholder="Please input password " class="password" required>';
            html += '<br>&nbsp;<br>'
            html += '<input type="password" name="' + this.property + 'Again" placeholder="Please input password again " class="password" required>';
            return html;
        },
        default_value: "",
        value: function() { //provide value
            var v = this.$el.find('input[name="' + this.property + '"]').val();

            // alert(v);
            // alert(v ? SHA1(v) : undefined);
            return v ? SHA1(v) : undefined;
        },
        validateFunction: function(m) {
            var pw1 = this.$el.find('input[name="' + this.property + '"]').val();
            var pw2 = this.$el.find('input[name="' + this.property + 'Again"]').val();
            if (pw1.length < this.pwlen) {
                alert("password length too short:" + this.pwlen + " ");
                return false;
            }

            if (pw1 != pw2) {
                alert("different password!")
                return false;
            } else return true;
        },
        setValue: function(o) { //set the value to this element
            this.$el.find('input[name="' + this.property + '"]').val();
        }
    };

    Backbone.UIToolBox.passwordConfirmOptional = _.defaults({
        validateFunction: function(m) {

            var pw1 = this.$el.find('input[name="' + this.property + '"]').val();
            var pw2 = this.$el.find('input[name="' + this.property + 'Again"]').val();

            if (pw1 == "" && pw2 == "")
                return true;

            return Backbone.UIToolBox.passwordConfirm.validateFunction.apply(this, arguments)
        }
    }, Backbone.UIToolBox.passwordConfirm);

})(jQuery);



(function($) {

    Backbone.UIToolBox.twoLevelSelector = {
        useTemplate: false,
            view: function(o) {
                var collClass1 = getBBCollection({
                    url: $.config.server + 'getConsultingType',
                    idAttribute: 'ct_id'
                });

                var coll1 = new collClass1();

                coll1.fetch({ async: false })

                var json1 = coll1.toJSON();

                json1.pop();

                // this.default_value = this.default_value || this.label_value[0].value;

                // var lv = this.label_value;
                var lvlen = json1.length;
                var _html = "";
                _html += '<div class="form-group has-"><label class="col-xs-4 control-label no-padding-right">類別：</label><div class="col-xs-8 controls edit-cell-content">';
                _html += '<select class="form-control ' + this.property + '">';
                if (this.hasAll)
                    _html += '<option value="">全部</option>';

                for (var i = 0; i < lvlen; i++) {
                    _html += '<option value="' + json1[i].value + '">' + json1[i].label + '</option>';
                }
                _html += '</select></div></div>';

                // api
                var collClass2 = getBBCollection({
                    url: $.config.server + 'getConsultingSubType',
                    idAttribute: 'cst_id'
                });

                var coll2 = new collClass2();

                coll2.fetch({
                    data: {
                        id: json1[0].value
                    },
                    async: false
                })

                var json2 = coll2.toJSON();

                json2.pop();

                
                _html += '<div class="form-group has-"><label class="col-xs-4 control-label no-padding-right">類別：</label><div class="col-xs-8 controls edit-cell-content">';
                _html += '<select class="form-control ' + this.property + '_sub">';
                for (var i = 0; i < json2.length; i++) {
                    if (!this.hasAll) {
                        _html += '<option value="' + json2[i].value + '">' + json2[i].label + '</option>';
                    }
                }
                _html += '</select></div></div>';


                var that = this;
                //event handler function
                $(".page-content").unbind("change.abc")
                $(".page-content").on("change.abc", "select." + that.property, function() {
                    // alert($("select." + that.property).val());
                    // console.log($("select." + this.property).val());

                    if ($("select." + that.property).val() !== "") {
                        var collClass = getBBCollection({
                            url: $.config.server + 'getConsultingSubType',
                            idAttribute: 'id'
                        });

                        var coll = new collClass();

                        coll.fetch({
                            data: {
                                id: $("select." + that.property).val().toString()
                            },
                            async: false
                        })

                        var json = coll.toJSON();

                        json.pop();
                    } else {
                        var json = [];
                    }

                    var replaceHtml = "";
                    for (var i = 0; i < json.length; i++) {
                        replaceHtml += '<option value="' + json[i].value + '">' + json[i].label + '</option>';
                    }
                    // replaceHtml += "<option value='1'>1</option>";
                    // replaceHtml += "<option value='2'>2</option>";
                    // alert($( "select."+that.property + '_sub').length);
                    $("select." + that.property + '_sub').html(replaceHtml);
                });

                return $(_html);
            },
            value: function() {
                // console.log($("select." + this.property).val());
                var v1 = $(this.$el).find("select." + this.property).val();

                if (v1 === "")
                    return { c_type: v1 };

                var v2 = $(this.$el).find("select." + this.property + "_sub").val();
                var obj = {
                    c_type: v1,
                    c_sub_type: v2
                }

                return obj;
            },
            setValue: function(o) {
                // var v = o.get(this.property) || this.default_value;

                // //handle boolean problem
                // if( typeof v == "boolean") {
                //     if (v == true)
                //         v = "true";
                //     else
                //         v = "false";
                // }

                // $(this.$el).find("select." + this.property).val(v);
            }
        }
        //     /*
        //      _.defaults({
        //          title: '狀態',
        //          property: 'Verified',
        //          label_value: [ {label:"未驗證",value:"0"},{label:"已驗證",value:"1"}],
        //      },Backbone.UIToolBox.selector)
        //      */
    Backbone.UIToolBox.selector = {
        view: function(o) {
            if (!_.isArray(this.label_value)) {
                console.error("請到系統針對 " + this.property + " 設定 system_collection_map ")
                return;
            }
            this.default_value = this.default_value || this.label_value[0].value;

            var lv = this.label_value;
            var lvlen = lv.length;
            var _html = '<select class="form-control ' + this.property + '">';
            for (var i = 0; i < lvlen; i++) {
                _html += '<option value="' + lv[i].value + '">' + lv[i].label + '</option>';
            }
            _html += '</select>';
            return _html;
        },
        value: function() {
            // console.log($("select." + this.property).val());
            return $(this.$el).find("select." + this.property).val();
        },
        setValue: function(o) {
            var v = o.get(this.property) || this.default_value;

            //handle boolean problem
            if (typeof v == "boolean") {
                if (v == true)
                    v = "true";
                else
                    v = "false";
            }

            $(this.$el).find("select." + this.property).val(v);
        }
    }

    Backbone.UIToolBox.selector2 = { //這個命名沒有偷懶，套件就叫這名字
        view: function(o) {
            if (!_.isArray(this.label_value)) {
                console.error("請到系統針對 " + this.property + " 設定 system_collection_map ")
                return;
            }
            this.default_value = this.default_value || this.label_value[0].value;

            var lv = this.label_value;
            var lvlen = lv.length;
            var _html = '<select class="form-control ' + this.property + '">';
            for (var i = 0; i < lvlen; i++) {
                _html += '<option value="' + lv[i].value + '">' + lv[i].label + '</option>';
            }
            _html += '</select>';
            return _html;
        },
        value: function() {
            // console.log($("select." + this.property).val());
            return $(this.$el).find("select." + this.property).val();
        },
        setValue: function(o) {
            var v = o.get(this.property) || this.default_value;

            //handle boolean problem
            if (typeof v == "boolean") {
                if (v == true)
                    v = "true";
                else
                    v = "false";
            }

            $(this.$el).find("select." + this.property).val(v);
            $(this.$el).find("." + this.property).removeClass('form-control').css('width', '200px').css('padding-top', '4px').select2();

        }
    }

})(jQuery);

(function($) {

    var jqfu_template;
    /*
         _.defaults({
          property:"Product.ProductPhoto",
          idAttribute:"ProductID"
        },Backbone.UIToolBox.photo)
     */
    Backbone.UIToolBox.photo = {
        useTemplate: false,
        view: function(o) {
            var that = this;
            if (!jqfu_template)
                jqfu_template = Handlebars.compile($('#tpl_jqfu').html());
            var tid = o.get(this.idAttribute);
            var data = {}
            if (typeof tid !== 'undefined') {
                data.tid = tid;
                data.name = this.property;
            }
            var html = jqfu_template();
            var $html = $(html);
            $html.ready(function() {
                $html.fileupload({
                    url: '../jqfu/server/php/'
                });
                $html.addClass('fileupload-processing');
                $html.fileupload('option', {
                    disableImageResize: /Android(?!.*Chrome)|Opera/
                        .test(window.navigator.userAgent),
                    maxFileSize: 5000000,
                    acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i
                });

                that.data = {
                    url: $html.fileupload('option', 'url'),
                    dataType: 'json',
                    context: $html[0],
                    data: data
                };
                $.ajax(that.data).always(function() {
                    $(this).removeClass('fileupload-processing');
                }).done(function(result) {
                    $(this).fileupload('option', 'done').call(this, null, {
                        result: result
                    });
                });
            });
            return $html;
        },
        update: function(o) {
            tmp_photos = $("#fileupload").find(".template-download .name a");

            if (tmp_photos.length > 0) {
                var arr = [];
                for (var i = 0; i < tmp_photos.length; i++) {
                    var id = $(tmp_photos[i]).attr("download").replace(/^(\d+)\.[.]+$/, "$1");
                    arr.push(id.replace(/\..+$/g, ""));
                };

                var data = {
                    pids: arr,
                    name: "product.productphoto",
                    id: o.get("id")
                };
                $.ajax({
                    url: '../bn/index.php/photo/addto',
                    type: 'POST',
                    dataType: 'json',
                    data: data,
                    async: false,
                    complete: function(xhr, textStatus) {
                        //called when complete
                        // alert("complete");
                    },
                    success: function(data, textStatus, xhr) {
                        //called when successful
                        // alert("success");
                    },
                    error: function(xhr, textStatus, errorThrown) {
                        //called when there is an error
                        alert("error");
                    }
                });

            }
        }
    };


    //
    Backbone.UIToolBox.photo.copyto = function(old_name, old_id, new_name, new_id) {
        var that = this;
        var data = {
            old_name: old_name,
            old_id: old_id, // this is product id
            new_name: new_name,
            new_id: new_id // this is shelves id
        };

        $.ajax({
            url: '../bn/index.php/photo/copyto',
            type: 'POST',
            dataType: 'json',
            data: data,
            async: false,
            complete: function(xhr, textStatus) {
                // get all photo from database
                $.ajax(that.data).always(function() {
                    $(this).removeClass('fileupload-processing');
                }).done(function(result) {

                    $(this).fileupload('option', 'done')
                        .call(this, null, {
                            result: result
                        });
                });
            },
            success: function(data, textStatus, xhr) {
                //called when successful
                // alert("success");
            },
            error: function(xhr, textStatus, errorThrown) {
                //called when there is an error
                alert("i am error");
            }
        });
    };
})(jQuery);

(function($) {
    /*
    _.defaults({
      title:'權重'
          property:"Weight",
      default_value:50     //optional
        },Backbone.UIToolBox.slider)
     */
    Backbone.UIToolBox.slider = {
        default_value: 50,
        view: function(o) {
            var that = this;

            var v = this.default_value;
            if (_.isNumber(o.get(this.property)))
                v = o.get(this.property);

            var html = '<div style="width:260px"><div id="' + this.property + '-amount">' + v + '</div><div class="slider-yellow slider"></div></div>';
            var $html = $(html);
            $html.ready(function() {
                $html.find(".slider").slider({
                    value: v,
                    orientation: "horizontal",
                    range: "min",
                    animate: true,
                    slide: function(event, ui) {
                        $("#" + that.property + "-amount").html(ui.value);
                    }
                });
            });
            return $html;
        },
        value: function(o) {
            var that = this;
            return ($("#" + that.property + "-amount").html());
        },
        setValue: function(o) {

        }
    }
})(jQuery);


(function($) {
    /*
        _.defaults({
            title: '商品分類',
            property: 'class',
            default_value: "1",
            brFlag: false,
            content_value: [{label_value: [{label: "A", value: "0"}, {label: "B", value: "1"}, {label: "C", value: "2"}]},
                            {label_value: [{label: "D", value: "0"}, {label: "E", value: "1"}, {label: "F", value: "2"}]}
                            ]
        },Backbone.UIToolBox.selector)
    */
    /*
    Backbone.UIToolBox.multiSelector = {
        view: function(o) {
            this.default_value = this.default_value || this.content_value[0].label_value[0].value;

            var cv = this.content_value;
            var cvLen = cv.length;
            var _html = "";
            for (var i = 0; i < cvLen; i++) {
                var lv = cv[i].label_value;
                var lvLen = lv.length;
                _html += '<select class="' + this.property + '">';
                for (var j = 0; j < lvLen; j++) {
                    _html += '<option value="' + lv[j].value + '">' + lv[j].label + '</option>';
                }
                _html += '</select>';
                if (this.brFlag === true) _html += '<br>';
            }
            return _html;
        },
        value: function() {
            return $("select." + this.property).val();
        },
        setValue: function(o) {
            var v = o.get(this.property) || this.default_value;
            $("select." + this.property).val(v);
        }
    }*/
})(jQuery);

(function($) {

    /*
        _.defaults({
            title: '商品分類',
            property: 'class',
            default_value: "1",
            brFlag: false,
            content_value: [{
            _value: [{label: "A", value: "0"}, {label: "B", value: "1"}, {label: "C", value: "2"}]},
                            {label_value: [{label: "D", value: "0"}, {label: "E", value: "1"}, {label: "F", value: "2"}]}
                            ]
        },Backbone.UIToolBox.selector)
    */
    Backbone.UIToolBox.multiSelector = {
        view: function(o) {
            this.default_value = this.default_value || this.content_value[0].label_value[0].value;

            var cv = this.content_value;
            var cvLen = cv.length;
            var _html = "";
            for (var i = 0; i < cvLen; i++) {
                var lv = cv[i].label_value;
                var lvLen = lv.length;
                _html += '<select class="' + this.property + '">';
                for (var j = 0; j < lvLen; j++) {
                    _html += '<option value="' + lv[j].value + '">' + lv[j].label + '</option>';
                }
                _html += '</select>';
                if (this.brFlag === true) _html += '<br>';
            }
            return _html;
        },
        value: function() {
            return $("select." + this.property).val();
        },
        setValue: function(o) {
            var v = o.get(this.property) || this.default_value;
            $("select." + this.property).val(v);
        }
    }
})(jQuery);

(function($) {

    /*
_.defaults({
    title: '分類',
    property: 'ProductCategoryID',
    term: {
        url: '../bn/index.php/news_term/gen'
    },
    set: {
        url: '../bn/index.php/news_termset/gen'
    }
})
 */
    Backbone.UIToolBox.multichoice = {

        default_term: {
            //        url: '../bn/index.php/news_term/gen',
            idAttribute: "ID",
            data: {
                fields: ["ID", "Name"]
            }
        },
        default_set: {
            //        url: '../bn/index.php/news_termset/gen',
            idAttribute: "ID",
            ContentIDMap: {
                "ContentField": "ID",
                "SetField": "ContentID"
            },
            TermIDMap: {
                "TermField": "ID",
                "SetField": "TermID"
            }
        },
        view: function(o) {
            //抓取 term 資料
            var that = this;

            that.set = _.defaults(that.set, that.default_set);
            that.term = _.defaults(that.term, that.default_term);


            that.fetch_term();
            var _html = "";
            var json = that.term_collection.toJSON();
            for (var i = 0; i < json.length; i++) {
                _html += '<input type="checkbox" name="' + this.property + '" value="' + json[i][that.set.TermIDMap.TermField] + '">' + json[i][that.term.data.fields[1]] + '&nbsp';
            }
            return _html;
        },
        fetch_term: function(o) {
            var that = this;
            if (!that.term_collection) {
                that.term_collection = new Backbone.Datagrid.getCollectionByUrl(that.term.url, that.term.idAttribute);
            }

            that.term_collection.fetch({
                async: false,
                data: that.term.data
            });

            that.term_info = that.term_collection.pop();
        },
        fetch_set: function(m) {
            var that = this;
            if (!that.set_collection) {
                that.set_collection = new Backbone.Datagrid.getCollectionByUrl(that.set.url, that.set.idAttribute);
            }

            var id = m.get(that.set.ContentIDMap.ContentField);

            if (!_.isUndefined(id)) {
                var where = {};
                where[that.set.ContentIDMap.SetField] = id;
                // where = _.defaults(where,data.where);
                // data.where = where;

                that.set_collection.fetch({
                    async: false,
                    data: {
                        where: where
                    }
                });

                that.set_info = that.set_collection.pop();
            }
        },
        value: function() {

        },
        setValue: function(m) {
            var that = this;
            that.fetch_set(m);
            var json = that.set_collection.toJSON();
            for (var i = 0; i < json.length; i++) {
                $('input[name="' + this.property + '"][value="' + json[i][that.set.TermIDMap.SetField] + '"]').prop("checked", true);
            }
        },
        update: function(m) {
            var that = this;
            var id = m.get(that.set.ContentIDMap.ContentField);

            that.fetch_set(m);
            // var len = this.collection2.length;
            var len = that.set_collection.length;

            // remove old relation first
            for (var i = 0; i < len; i++) {
                var remove_model = this.set_collection.pop();
                remove_model.destroy({}, {
                    async: false
                });
            };

            //
            var target = $('input[name="' + this.property + '"][type="checkbox"]:checked');

            var update_model = Backbone.Model.extend({
                urlRoot: that.set.url,
                idAttribute: that.set.idAttribute
            });

            for (var i = 0; i < target.length; i++) {
                var v = $(target[i]).val();

                var update = {};
                update[that.set.ContentIDMap.SetField] = id;
                update[that.set.TermIDMap.SetField] = v;

                model = new update_model(update);
                model.save({}, {
                    async: false
                });
            };
        }
    };

})(jQuery);

(function($) {
    my = {
        isCid: function(id) {
            tab = "ABCDEFGHJKLMNPQRSTUVXYWZIO"
            A1 = new Array(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3);
            A2 = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5);
            Mx = new Array(9, 8, 7, 6, 5, 4, 3, 2, 1, 1);

            if (typeof id !== "string" || id.length != 10) return false;

            i = tab.indexOf(id.charAt(0));

            if (i == -1) return false;
            sum = A1[i] + A2[i] * 9;

            for (i = 1; i < 10; i++) {
                v = parseInt(id.charAt(i));
                if (isNaN(v)) return false;
                sum = sum + v * Mx[i];
            }

            if (sum % 10 != 0) return false;
            return true;
        },
        isEmail: function(email) {
            var regex = /^\w+((\-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|\-)[A-Za-z0-9]+)*\.[A-Za-z]+$/;
            return typeof email === 'string' && email.match(regex);
        },
        isPhone: function(phone) {
            var regex = /^[0-9][0-9\-]+[0-9]$/;
            return typeof phone === 'string' && phone.match(regex);
        },
        isBankAccount: function(bankaccount) {
            var regex = /^[0-9][0-9\-]+[0-9]$/;
            return typeof bankaccount === 'string' && bankaccount.match(regex);
        },
        isDigitString: function(digit) {
            var regex = /^\d+$/;
            return typeof digit === 'string' && digit.match(regex);
        },
        isNotEmpty: function(str) {
            return str;
        },
        isEmpty: function(str) {
            return !str;
        }
    };
})(jQuery);

(function($) {
    /*
        _.defaults({
            title: '貨品狀態',
            property: 'state',
            default_value: "0",
            content_value: [{name: "供貨正常", value: "0"}, {name: "待備料", value: "1"}, {name: "下架",value: "2"}]
        },Backbone.UIToolBox.radio)
    */
    Backbone.UIToolBox.radio = {
        view: function(o) {
            // output html;
            var cv = this.content_value;
            var cvLen = cv.length;
            var _html = "";
            for (var i = 0; i < cvLen; i++) {
                _html += '<input type="radio" name="' + this.property + '" placeholder="';
                _html += this.title + '" class="radio" value="' + cv[i].value + '" style="display: inline;"> ' + cv[i].name + ' ';
            }
            //_html
            return $(_html);
        },
        value: function() { //provide value
            //tell the controller value
            return $('input[name="' + this.property + '"]:checked').val();
        },
        setValue: function(m) { //set the value to this element //Backbone.Model
            var v = m.get(this.property) || this.default_value;
            //$('input[name="' + this.property + '"]').get(v).checked = true;//find index
            $('input[name="' + this.property + '"][value="' + v + '"]').attr('checked', true); //find value
        },
        update: function(m) {

        }
    }
})(jQuery);

(function($) {
    Backbone.UIToolBox.radioOpenClose = {
        default_value: "0",
        content_value: [{ name: "關閉", value: "0" }, { name: "開啟", value: "1" }],
        view: function(o) {
            // output html;
            var cv = this.content_value;
            var _html = "";
            for (var i = 0; i < 2; i++) {
                _html += '<input type="radio" name="' + this.property + '" placeholder="';
                _html += this.title + '" class="radio" value="' + cv[i].value + '" style="display: inline;"> ' + cv[i].name + ' ';
            }
            //_html
            return _html;
        },
        value: function() { //provide value
            if ($('input[name="' + this.property + '"]:checked').val() == 0)
                return "N";
            //return 000000000;
            else($('input[name="' + this.property + '"]:checked').val() == 1)
            return "Y";
        },
        setValue: function(m) { //set the value to this element //Backbone.Model
            var v = m.get(this.property) || this.default_value;
            //$('input[name="' + this.property + '"]').get(v).checked = true;//find index
            $('input[name="' + this.property + '"][value="' + v + '"]').attr('checked', true); //find value
        },
        update: function(m) {

        }
    }
})(jQuery);
(function($) {
    /*
      _.defaults({ 
        title: '',
        property: 'ae_status',
    }, Backbone.UIToolBox.radioIsCustomized)
    */
    Backbone.UIToolBox.radioIsCustomized = {
        default_value: "0",
        content_value: [{ name: "預設", value: "0" }, { name: "手動", value: "1" }],
        view: function(o) {
            // output html;
            var _html = "";
            var cv = this.content_value;
            var v = o.get(this.property);
            var customizedV = o.get(this.Customized);

            if (customizedV == "Y") {
                _html += '<input type="radio" name="' + this.property;
                _html += '" class="radio" value="' + cv[0].value;
                _html += '" style="display: inline;"> ' + cv[0].name + ' ';

                _html += '<input type="radio" name="' + this.property;
                _html += '" class="radio" value="' + cv[1].value;
                _html += '" style="display: inline;" checked="checked"> ';

                _html += '<input type="text" name="Customized' + this.property + '" class="width-80 " value="' + v + '">';
            } else {
                _html += '<input type="radio" name="' + this.property;
                _html += '" class="radio" value="' + cv[0].value;
                _html += '" style="display: inline;" checked="checked"> ' + cv[0].name + ' ';

                _html += '<input type="radio" name="' + this.property;
                _html += '" class="radio" value="' + cv[1].value;
                _html += '" style="display: inline;"> ';

                _html += '<input type="text" name="Customized' + this.property + '" class="width-80 ">';

            }


            return $(_html);
        },
        value: function() { //provide value
            var ret;
            var checked = $('input[name="' + this.property + '"]:checked').val();
            if (checked == 0) {
                var obj = {};
                var attr = this.property;
                var attrIsCustom = this.property + "_is_customized";
                obj[attrIsCustom] = "N";
                obj[this.property] = parseFloat(0).toFixed(2);
                return obj;
            }
            //return $('input[name="' + this.property + '"]:checked').val();
            else if (checked == 1) {
                var v = $('input[name="Customized' + this.property + '"]').val();

                var obj = {};
                var attr = this.property;
                var attrIsCustom = this.property + "_is_customized";
                obj[attrIsCustom] = "Y";
                obj[this.property] = parseFloat(v).toFixed(2);
                return obj;
            }

        },
        setValue: function(m) { //set the value to this element //Backbone.Model
            var v = m.get(this.property) || this.default_value;
            //$('input[name="' + this.property + '"]').get(v).checked = true;//find index
            $('input[name="' + this.property + '"][value="' + v + '"]').attr('checked', true); //find value
        },
        update: function(m) {

        }
    }
})(jQuery);

(function($) {
    /*
        _.defaults({
            title: '送貨院區',
            property: 'place',
            default_value: ["1", "2"],
            content_value: [{name: "全部", value: "0"}, {name: "總部-中興院區(新竹)", value: "1"},
                            {name: "光復院區(新竹)", value: "2"}, {name: "六甲院區(台南)", value: "3"}]
        },Backbone.UIToolBox.checkbox)
    */
    Backbone.UIToolBox.checkbox = {
        view: function(o) {
            // output html;
            var cv = this.content_value;
            var cvLen = cv.length;
            var _html = "";
            for (var i = 0; i < cvLen; i++) {
                _html += '<div><input type="checkbox" name="' + this.property; //+ '" placeholder="' //this.title + 
                _html += '" class="" value="' + cv[i].value + '" > ' + cv[i].name + "</div>"; //+ '<br>'
            }
            return _html;
        },
        value: function() { //provide value
            //tell the controller value

            // if($('input[name="' + this.property + '"]:checked').length > 0)
            var arr = [];
            $('input[name="' + this.property + '"]:checked').each(function() {
                arr.push($(this).val());
            })
            var obj = {};
            obj[this.property] = "";
            //   obj[this.property] = JSON.stringify(arr);
            for (var a in arr) {
                if (arr[a] != undefined)
                    obj[this.property] = obj[this.property] + arr[a] + ",";
            }
            return obj;
            // else
            //     return "";
        },
        setValue: function(m) { //set the value to this element //Backbone.Model
            var v = m.get(this.property) || this.default_value;
            var vLen = v.length;
            for (var i = 0; i < vLen; i++) {
                $('input[name="' + this.property + '"][value="' + v[i] + '"]').attr("checked", true);
            }
        },
        update: function(m) {

        }
    }
})(jQuery);


(function($) {
    Backbone.UIToolBox.TitleOnly = {
        //default_value: "標頭顯示用",
        useTemplate: false,
        view: function(m) {
            var v = this.title; //|| this.default_value)
            if (!v) v = "標頭顯示用";
            return "<p style='margin: 30px 30px 20px 20px' align='center'>" + v + "</p>";
        },
        value: function() { //provide value
            return {};
        },
        setValue: function(m) { //set the value to this element

        },
        update: function(m) {

        }
    };
    Backbone.UIToolBox.readonly = {
        default_value: "本欄不能輸入",
        useTemplate: true,
        view: function(m) {
            var v = m.get(this.property); //|| this.default_value)
            // if (v == 0) v = "0";
            //don't move!!!!!
            if (!v) v = ""; //這樣會不顯示0
            return "<p style='margin: 10px 10px 10px 10px ;word-wrap:break-word; word-break:normal; '>" + v + "</p>";
        },
        value: function() { //provide value
            return {};
        },
        setValue: function(m) { //set the value to this element

        },
        update: function(m) {

        }
    };

    Backbone.UIToolBox.twoColumnReadonly = {
        default_value: "本欄不能輸入",
        useTemplate: true,
        view: function(m) {
            var v1 = m.get(this.property[0]);
            var v2 = m.get(this.property[1]);

            if (v1 == "Y" || v1 == true) {
                v1 = "開啟";
            } else {
                v1 = "關閉";
            }

            if (v2 == "Y" || v2 == true) {
                v2 = "開啟";
            } else {
                v2 = "關閉";
            }

            return "<p style='margin: 10px 10px 10px 10px'>" + v1 + "/" + v2 + "</p>";
        },
        value: function() { //provide value
            return {};
        },
        setValue: function(m) { //set the value to this element

        },
        update: function(m) {

        }
    };

    Backbone.UIToolBox.compareColumnReadonly = {
        default_value: "本欄不能輸入",
        useTemplate: true,
        view: function(m) {
            //Detect is customized?
            var v1 = m.get(this.property[0]);
            var v2 = m.get(this.property[1]);

            //Detect is customized?
            var v3 = m.get(this.property[2]);
            var v4 = m.get(this.property[3]);

            if (v1 != "Y") {
                v2 = "預設";
            }

            if (v3 != "Y") {
                v4 = "預設";
            }

            return "<p style='margin: 10px 10px 10px 10px'>" + v2 + "/" + v4 + "</p>";
        },
        value: function() { //provide value
            return {};
        },
        setValue: function(m) { //set the value to this element

        },
        update: function(m) {

        }
    };

})(jQuery);

(function($) {
    Backbone.UIToolBox.inlineText = {
        default_value: "",
        useTemplate: true,
        view: function(m) {
            if (!this.content) return "";
            return '<label class="form-control">' + this.content + '</label>';
        },
        value: function() { //provide value
            return {};
        },
        setValue: function(m) { //set the value to this element

        },
        update: function(m) {

        }
    };
})(jQuery);



(function($) {
    Backbone.UIToolBox.inputRange = {
        default_value: "",
        view: function(m) {
            var _html = '';
            _html += '<span class="block input-icon input-icon-right">';
            _html += '<input type="text" name="' + this.name[0] + '" placeholder="請輸入' + this.title + '下限" class="width-40">';
            _html += '<label class="control-label" style="padding-right: 10px; padding-left: 10px;">到</label>';
            _html += '<input type="text" name="' + this.name[1] + '" placeholder="請輸入' + this.title + '上限" class="width-40">';
            _html += '</span>';
            return _html;
        },
        value: function() { //provide value
            var obj = {};
            obj[this.name[0]] = $('input[name=' + this.name[0] + ']').val();
            obj[this.name[1]] = $('input[name=' + this.name[1] + ']').val();
            return obj;
        },
        setValue: function(m) { //set the value to this element

        },
        update: function(m) {},
        validateFunction: function() {
            var reg = /^\d*$/
            v = this.value();
            if (!reg.test(v[this.name[0]])) {
                alert(this.title + '格式錯誤');
                return false;
            }
            if (!reg.test(v[this.name[1]])) {
                alert(this.title + '格式錯誤');
                return false;
            }
            return true;
        }
    };
})(jQuery);

(function($) {
    Backbone.UIToolBox.datePicker = {
        default_value: "",
        limit: 0,
        view: function(m) {
            var html = '';
            html += '<span class="block input-icon input-icon-right">';
            html += '<div class="input-daterange input-group">';
            html += '<input type="text" class="form-control pickerInput" name="' + this.property + '" readonly>';
            html += '</div>';
            html += '</span>';
            return html;
        },
        value: function() { //provide value
            var v1 = this.$el.find("input[name='" + this.property + "']").val();
            return v1;
        },
        setValue: function(m) { //set the value to this element
            var str = m.get(this.property);
            if (str != null)
                var start_date = new Date(str);
            else
                var start_date = new Date();
            this.$el.find('input[name="' + this.property + '"]').datepicker('update', start_date);
        }
    };
})(jQuery);

// _.defaults({
//     title: '效期起迄',
//     property: '',
//     rename_property: ["beginDate", "endDate"],
//     limit: 60
// }, Backbone.UIToolBox.inputRange),
///////    limit = 7, 30, 31, 60, day    ////////
(function($) {
    Backbone.UIToolBox.dateRange = {
        default_value: "",
        limit: 0,
        view: function(m) {
            var html = '';
            html += '<span class="block input-icon input-icon-right">';
            html += '<div class="input-daterange input-group">';
            html += '<input type="text" class="form-control pickerInput" name="' + this.rename_property[0] + '" readonly>';
            html += '<span class="input-group-addon">';
            html += '<i class="fa fa-long-arrow-right"></i>';
            html += '</span>';
            html += '<input type="text" class="form-control pickerInput" name="' + this.rename_property[1] + '" readonly>';
            html += '</div>';
            html += '</span>';
            return html;
        },
        value: function() { //provide value
            // alert("value");
            var v1 = $("input[name='" + this.rename_property[0] + "']").val();
            var v2 = $("input[name='" + this.rename_property[1] + "']").val();

            var ret = {};
            ret[this.rename_property[0]] = v1;
            ret[this.rename_property[1]] = v2;
            // console.log(ret);
            return ret;
        },
        setValue: function(m) { //set the value to this element
            var start_date = new Date();
            if (this.start) start_date.setDate(start_date.getDate() - this.start);
            else if (!this.start && this.limit == 60) start_date.setMonth(start_date.getMonth() - 2);
            else if (!this.start && this.limit == 30) start_date.setDate(start_date.getDate() - this.limit + 1);
            else if (!this.start && this.limit == 31) start_date.setMonth(start_date.getMonth() - 1);
            else if (!this.start && this.limit == 7) start_date.setDate(start_date.getDate() - this.limit + 1);
            else if (!this.start && this.limit == 3650) start_date.setDate(start_date.getDate() - this.limit + 1);
            else if (!this.start && this.limit) start_date.setDate(start_date.getDate() - this.limit);
            else start_date.setDate(start_date.getDate() - 1);

            $('input[name="' + this.rename_property[0] + '"]').datepicker('update', start_date).datepicker('setEndDate', new Date());; //.datepicker('setEndDate', new Date());
            $('input[name="' + this.rename_property[1] + '"]').datepicker('update', new Date()).datepicker('setEndDate', new Date()); //.datepicker('setEndDate', new Date());
        },
        validateFunction: function(m) {
            try {
                var value = this.value();
                if (isEmptyString(value[this.rename_property[0]])) return false;
                if (isEmptyString(value[this.rename_property[1]])) return false;
                var endDate = new Date(value[this.rename_property[1]]);
                var beginDate = new Date(value[this.rename_property[0]]);

                var beginFloor = new Date(value[this.rename_property[1]]); //"開始日期"的最早時間點受限於limit
                if (this.limit == 60) beginFloor.setMonth(endDate.getMonth() - 2);
                else if (this.limit == 31) beginFloor.setMonth(endDate.getMonth() - 1);
                else if (this.limit == 30) beginFloor.setDate(endDate.getDate() - this.limit + 1);
                else if (this.limit == 7) beginFloor.setDate(endDate.getDate() - this.limit + 1);
                 // else if (this.limit == 3650) beginFloor.setDate(endDate.getDate() - this.limit + 1);
                else if (this.limit != 0) beginFloor.setDate(endDate.getDate() - this.limit + 1);
                else beginFloor.setDate(endDate.getDate() - 9999999);

                var str = "搜尋日期限制";
                if (this.limit == 60) str += "兩個月";
                else if (this.limit == 30) str += "三十天";
                else if (this.limit == 31) str += "一個月";
                else if (this.limit == 7) str += "七天";
                else if (this.limit != 0) str += (this.limit + "天");
                // else if (this.limit == 3650) str tr += "十年";

                if (beginDate < beginFloor) {
                    alert(str);
                    return false;
                } else return true;
            } catch (e) {
                alert("日期格式有誤。");
                return false;
            }
        }
    };
})(jQuery);



//form
//monthRange
// _.defaults({
// title: '效期起迄',
// property: '',
// rename_property: ["beginMonth", "endMonth"],
// limit: 6
// }, Backbone.UIToolBox.monthRange)
///////    limit = 6    ////////
(function($) {
    Backbone.UIToolBox.monthRange = {
        default_value: "",
        limit: 6,
        view: function(m) {
            var html = '';
            html += '<span class="block input-icon input-icon-right">';
            html += '<div class="input-daterange input-group">';
            html += '<input type="text" class="form-control pickerInput" name="' + this.rename_property[0] + '" data-date-format="yyyy-mm" readonly>';
            html += '<span class="input-group-addon">';
            html += '<i class="fa fa-long-arrow-right"></i>';
            html += '</span>';
            html += '<input type="text" class="form-control pickerInput" name="' + this.rename_property[1] + '" data-date-format="yyyy-mm" readonly>';
            html += '</div>';
            html += '</span>';
            return html;
        },
        value: function() { //provide value
            // alert("value");
            var v1 = $("input[name='" + this.rename_property[0] + "']").val();
            var v2 = $("input[name='" + this.rename_property[1] + "']").val();

            var obj = {};
            obj[this.rename_property[0]] = v1;
            obj[this.rename_property[1]] = v2;
            return obj;
        },
        setValue: function(m) { //set the value to this element
            var end_date = new Date();
            var begin_date = new Date();
            begin_date.setMonth(end_date.getMonth() - this.limit + 1);
            $('input[name="' + this.rename_property[1] + '"]').datepicker({ startView: "year", minViewMode: "months" }).datepicker('update', end_date).datepicker('setStartDate', begin_date).datepicker('setEndDate', new Date());
            $('input[name="' + this.rename_property[0] + '"]').datepicker({ startView: "year", minViewMode: "months" }).datepicker('update', begin_date).datepicker('setEndDate', end_date);
        },
        validateFunction: function(m) {
            try {
                var value = this.value();
                if (isEmptyString(value[this.rename_property[0]]) || isEmptyString(value[this.rename_property[1]])) {
                    alert('不可為空白');
                    return false;
                }
                beginDate = new Date(value[this.rename_property[0]]);
                endDate = new Date(value[this.rename_property[1]]);

                var beginLimit = endDate;
                beginLimit.setMonth(endDate.getMonth() - this.limit + 1);

                if (beginDate < beginLimit) {
                    var str = "搜尋區間限制";
                    if (this.limit == 6) str += "六個月";
                    else if (this.limit == 7) str += '七個月';
                    else if (this.limit == 2) str += '兩個月';
                    else str += this.limit + "個月";
                    alert(str);
                    return false;
                } else return true;
            } catch (e) {
                alert("日期格式有誤。");
                return false;
            }
        }
    }
    Backbone.UIToolBox.email = {
        default_value: "",
        stringLength: 120,
        validateFunction: function(m) {
            var emailRule = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z]+$/;
            emailRule=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/; 
            if (this.$el.find('input[name=' + this.property + ']').val().search(emailRule) == -1) {
                alert("email格式有誤");
                this.$el.find('input[name=' + this.property + ']').focus();
                return false;
            } else return true;
        },
        setValue: function(m) { //set the value to this element
            var v1 = m.get(this.property);
            var v = v1 ? v1 : this.default_value;
            this.$el.find('input[name=' + this.property + ']').val(v);
        }
    }
    Backbone.UIToolBox.stringInput = {
        default_value: "",
        stringLength: 120,
        limitLength: 120, //最短長度限制
        // view: function(o) {
        //     var el = '<input type="text" name="' + this.property + '" id="' + this.property + '"  style="width:100%"></input>';
        //     return el;
        // },//<input type="text" name="mm_real_name" placeholder="請輸入會員姓名" class="width-100 ">
        validateFunction: function(m) {
            if (this.$el.find('input[name=' + this.property + ']').val().length > this.stringLength) {
                alert(this.title + '長度過長');
                this.$el.find('input[name=' + this.property + ']').focus();
                return false;
            } else return true;
        },
        setValue: function(m) { //set the value to this element
            var v1 = m.get(this.property);
            var v = v1 ? v1 : this.default_value;
            this.$el.find('input[name=' + this.property + ']').val(v);
        }
    }

    Backbone.UIToolBox.weightInput = {
        default_value: "50",
        start: 0,
        end: 100,
        validateFunction: function(m) {
            var v = $('input[name=' + this.property + ']').val();
            if (!v) {
                alert(this.title + '不可為空');
                return false;
            } else if (!$.isNumeric(v)) {
                alert(this.title + '值必須是數字');
                return false;
            } else if (v < this.start || v > this.end) {
                alert(this.title + '值必須在[' + this.start + ',' + this.end + ']之間')
                return false;
            }
            return true;
        },
        setValue: function(m) { //set the value to this element
            var v1 = m.get(this.property);
            if (typeof v1 === "undefined") {
                var v = this.default_value;
            } else {
                var v = v1;
            }
            $('input[name=' + this.property + ']').val(v);
        }
    }

    Backbone.UIToolBox.htmlEditor = {
        default_value: '',
        view: function(o) {
            var p = this.property;
            var name = p + '_ckcontent';
            var el = '<textarea name="' + name + '" id="' + name + '" rows="10" cols="80"></textarea>';
            return el;
        },
        setValue: function(m) {
            var p = this.property;
            var name = p + '_ckcontent';
            CKEDITOR.replace(name, {});
            var v = m.get(this.property) || this.default_value;
            CKEDITOR.instances[name].setData(v);
        },
        value: function() {
            var p = this.property;
            var name = p + '_ckcontent';
            return CKEDITOR.instances[name].getData();
        }
    }
    Backbone.UIToolBox.textarea = {
        default_value: '',
        view: function(o) {
            var el = '<textarea name="' + this.property + '" id="' + this.property + '" rows="5" cols="46" style="width:100%"></textarea>';
            return el;
        },
        setValue: function(m) {
            var v = m.get(this.property) || this.default_value;
            this.$el.find('textarea[name=' + this.property + ']').val(v);
        },
        value: function() {
            var p = this.property;
            var v = this.$el.find('textarea[name=' + this.property + ']').val();
            return v;
        }
    }
    var fkAPIcache = {}

    var fkHelper = function(url, idAttribute) {
        if (typeof fkAPIcache[url] !== 'undefined')
            return fkAPIcache[url];

        var collClass = getBBCollection({
            url: url,
            idAttribute: idAttribute
        });

        var coll = new collClass();

        coll.fetch({ async: false })

        var json = coll.toJSON();

        json.pop();

        fkAPIcache[url] = json;
        return fkAPIcache[url];
    }
    Backbone.UIToolBox.foreignSelector = {
        helperFunction: function(url, idAttribute) {
            return fkHelper(url, idAttribute);
        },
        allHelperFunction: function(url, idAttribute) {
            return [{ label: "全部", value: "" }].concat(fkHelper(url, idAttribute));
        },
        view: function(o) {

            this.default_value = this.default_value || this.label_value[0].value;

            var lv = this.label_value;
            var lvlen = lv.length;
            var _html = '<select class="form-control ' + this.property + '">';
            for (var i = 0; i < lvlen; i++) {
                _html += '<option value="' + lv[i].value + '">' + lv[i].label + '</option>';
            }
            _html += '</select>';
            return _html;
        },
        value: function() {
            // console.log($("select." + this.property).val());
            return $(this.$el).find("select." + this.property).val();
        },
        setValue: function(o) {
            var v = o.get(this.property) || this.default_value;

            // if( typeof v == "boolean") {
            //     if (v == true)
            //         v = "true";
            //     else
            //         v = "false";
            // }
            // alert(o.get(this.property));
            // v = v.toString();
            // alert(v);
            $(this.$el).find("select." + this.property).val(v);
        }
    }


    Backbone.UIToolBox.colcheckall = function(gv, chk_name) {
        $(gv).unbind("click." + chk_name);
        $(gv).on("click." + chk_name, "." + chk_name, function(event) {
            var $chk = $(gv).find("." + chk_name);

            var value = 0;
            var cnt = 0;
            for (var i = 0; i < $chk.length; i++) {
                if ($($chk[i]).prop("checked") == true) {
                    if (value == 0) value = 1;
                    cnt++;
                } else {
                    break;
                }
            }

            if (cnt == $chk.length && cnt != 0)
                value = 2;

            var $all = $('.' + chk_name + '-all');
            if (value == 0) {
                $all.prop('checked', false);
                $all.prop('indeterminate', false);
            } else if (value == 1) {
                $all.prop('checked', false);
                $all.prop('indeterminate', true);
            } else {
                $all.prop('checked', true);
                $all.prop('indeterminate', false);
            }
        });

        $(gv).unbind('click.' + chk_name + '-all');
        $(gv).on('click.' + chk_name + '-all', '.' + chk_name + '-all', function(event) {
            var v = $(event.target).prop("checked")
            var $chk = $(gv).find("." + chk_name);

            var value = 0;
            var cnt = 0;
            for (var i = 0; i < $chk.length; i++) {
                if (v == true)
                    $($chk[i]).prop("checked", true)
                else
                    $($chk[i]).prop("checked", false)
            }
        });
    }

    Backbone.UIToolBox.getcheckall = function(gv, chk_name) {
        var all = $(gv).find("." + chk_name);

        var ids = [];
        for (var i = 0; i < all.length; i++) {
            if ($(all[i]).prop("checked") == true)
                ids.push($(all[i]).data("id"));
        }

        return ids;
    }



    // console.log(Backbone.UIToolBox);
})(jQuery)
