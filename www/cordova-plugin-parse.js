var ParsePushPlugin = {
    register: function(regParams, successCallback, errorCallback) {
        cordova.exec(
            successCallback, errorCallback,
            'ParsePushPlugin', 'register',
            [regParams]
        );
    },

    getInstallationId: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback, errorCallback,
            'ParsePushPlugin', 'getInstallationId',
            []
        );
    },

    getInstallationObjectId: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback, errorCallback,
            'ParsePushPlugin', 'getInstallationObjectId',
            []
        );
    },

    getSubscriptions: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback, errorCallback,
            'ParsePushPlugin', 'getSubscriptions',
            []
        );
    },
    
    put: function(key, value, successCallback, errorCallback) {
        cordova.exec(
            successCallback,
            errorCallback,
            'ParsePushPlugin',
            'put',
            [{key: key, value: value}]
        );
    },

    subscribe: function(channel, successCallback, errorCallback) {
        cordova.exec(
            successCallback,
            errorCallback,
            'ParsePushPlugin',
            'subscribe',
            [ channel ]
        );
    },

    unsubscribe: function(channel, successCallback, errorCallback) {
        cordova.exec(
            successCallback,
            errorCallback,
            'ParsePushPlugin',
            'unsubscribe',
            [ channel ]
        );
    },
    
    received: function(successCallback, errorCallback) {
        cordova.exec(
            successCallback, errorCallback,
            'ParsePushPlugin', 'received',
            []
        );

    }
};
module.exports = ParsePushPlugin;
