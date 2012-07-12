App.UUID = Em.Namespace.create();

/* http://stackoverflow.com/questions/105034/how-to-create-a-guid-uuid-in-javascript */

App.UUID.S4 = function() {
    return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
};

App.UUID.generate = function() {
    return (App.UUID.S4() + App.UUID.S4() + "-" + App.UUID.S4() + "-" + App.UUID.S4() + "-" + App.UUID.S4() + "-" + App.UUID.S4() + App.UUID.S4() + App.UUID.S4());
};