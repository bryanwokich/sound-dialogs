function SoundDialogs() {
}




SoundDialogs.prototype.filterRadio = function(options){
    if(!options){
        throw 'Sound filterRadio has been passed empty parameters.'
    }

    if(!options.options){
        throw 'Sound filterRadio requires some options to select from';
    }

    var args = {};
    if(options.title){
        args.title = options.title;
    } else {
        args.title = 'Select One';
    }


    function callback(results){
        if(!options.callback){
            console.log('No callback provided for sound.dialogs.filterRadio.  Acceptable, but not very useful')
            return;
        }
        var callbackData = {
            submit: parseInt(results.submit)
        };

        if(results.selected){
            callbackData.selected = results.selected;
        }

        options.callback(callbackData);

    }

    var error;
    if(options.error){
        error = options.error
    } else {
        error = null;
    }

    args.buttonLabels = typeof options.buttonLabels == 'undefined' ? ["Cancel", "OK"] : options.buttonLabels;
    args.options = options.options;
    cordova.exec(callback, error, 'SoundDialogs', "filterRadio", [args.options, args.buttonLabels, args.title]);
}


SoundDialogs.prototype.passwordPrompt = function (options) {
    if(!options){
        throw 'Sound Password Prompt requires options';
    }

    var args = {};
    args.message = (options.message || "Please provide a password");
    args.title = (options.title || "Password");
    args.buttonLabels = typeof options.buttonLabels == 'undefined' ? ["Cancel", "OK"] : options.buttonLabels;
    //==============================================
    //  I want to be able to test if "submit" as a bool, so I'm going to force an int here
    //  before calling the user provided callback...   - BMW (9/11/14)
    //==============================================
    function callback(results){
        if(!options.callback){
            console.log('No callback provided for sound.dialogs.passwordPrompt.  Acceptable, but not very useful')
            return;
        }

        var callbackData = {
            submit: parseInt(results.submit)
        };

        if(results.password){
            callbackData.password = results.password;
        }

        options.callback(callbackData);

    }

    var error;
    if(options.error){
        error = options.error
    } else {
        error = null;
    }

    cordova.exec(callback, error, 'SoundDialogs', "passwordPrompt", [args.message, args.title, args.buttonLabels]);
};

SoundDialogs.install = function () {
    if (!window.plugins) {
        window.plugins = {};
    }
    if(!window.plugins.sound){
        window.plugins.sound = {};
    }
    window.plugins.sound.dialogs = new SoundDialogs();
    return window.plugins.sound.dialogs;
};

cordova.addConstructor(SoundDialogs.install);