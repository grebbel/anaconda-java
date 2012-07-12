var App = Em.Application.create({
    
    rootElement: '#container',

    config: Em.Object.create({

        pageSize: localStorage.getItem('pageSize') || 20,
        
        chartType: localStorage.getItem('chartType') || 'linear',

        settingDidChange: function() {
            localStorage.setItem('pageSize', this.get('pageSize'));
            localStorage.setItem('chartType', this.get('chartType'));
        }.observes('pageSize', 'chartType')

    })

});