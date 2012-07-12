Wijmo = Ember.Namespace.create();

Wijmo.Pager = Em.View.extend(JQ.Widget, {
	namespace: 'wijmo',
	uiType: 'wijpager',
	uiOptions: [ 'pageIndex', 'pageCount', 'pageButtonCount', 'mode' ],
	uiEvents: [ 'pageIndexChanging' ]
});
