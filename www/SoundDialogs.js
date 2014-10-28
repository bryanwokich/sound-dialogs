function SoundDialogs() {
    this.defaultButtons = ["Cancel", "OK"];
}




SoundDialogs.prototype.selectKVP = function(options){
    console.log('Select KVPs plugin JS', options);
    if(!options){
        throw 'Sound KVP Filter has been passed empty parameters.'
    }

    if(!options.options){
        throw 'Sound KVPFilter requires some options to select from';
    }


    var args = {};
    if(options.title){
        args.title = options.title;
    } else {
        args.title = 'Select One';
    }


    function callback(results){
        if(!options.callback){
            console.log('No callback provided for sound.dialogs.selectKVP.  Acceptable, but not very useful')
            return;
        }

        options.callback(results);

    }

    var error;
    if(options.error){
        error = options.error
    } else {
        error = null;
    }
    args.buttonLabels = (options.buttonLabels || this.defaultButtons);
    args.options = options.options;
    console.log('button values', args.buttonLabels);
    cordova.exec(callback, error, 'SoundDialogs', "selectKVP", [args.options, args.buttonLabels, args.title]);
}


SoundDialogs.prototype.passwordPrompt = function (options) {
    if(!options){
        throw 'Sound Password Prompt requires options';
    }

    var args = {};
    args.message = (options.message || "Please provide a password");
    args.title = (options.title || "Password");
    args.buttonLabels = (options.buttonLabels || this.defaultButtons);
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