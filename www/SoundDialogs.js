function SoundDialogs() {
}


SoundDialogs.prototype.passwordPrompt = function (options) {
    if(!options){
        throw new Error('Sound Password Prompt requires options');
    }

    var args = {};
    args.message = (options.message || "Prompt message");
    args.title = (options.title || "Prompt");
    args.buttonLabels = (options.buttonLabels || ["OK", "Cancel"]);
    args.defaultText = (options.defaultText || "");
    cordova.exec(options.callback, function(message){
        console.log('Error callback fired', message);
    }, 'SoundDialogs', "passwordPrompt", [args.message, args.title, args.buttonLabels, args.defaultText ]);
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