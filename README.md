# PhoneGap SoundDialogs plugin

for Android and iOS, from [Sound Software, Inc.](http://www.sound-software.us)

## 1. Description
This is basic phonegap plugin for dialogs.  I may extend it in the future, so it has a generic name.  As of now, it provides password prompts for Android and iOS


## 2.  Installation 
Currently, you have to pull from the repo and install via 
```
$ cordova plugin add /path/to/SoundDialogs
```

I'm submitting this to the plugin registry.  I hope the following will work soon..

```
$ cordova plugin add https://github.com/bryanwokich/sound-dialogs.git
```

## 3. Usage
Once you get the plugin installed you will be able to use the following....

```
window.plugins.sound.dialogs.passwordPrompt({
    callback: function(result){
        if(result.submit){ // submit return 1 if form submitted, 0 if canceled.
            console.log('Password', result.password); // And your password.
        }
        // success callback function 
    }, 
    title: "Title on Prompt", // optional, defaults to "Password"
    message: "Message on prompt", // optional, defaults to "Please provide a password"
    buttonLabels: ["Cancel Display Value", "Accept Display Value"], // optional, defaults to ["Cancel", "OK"]
    error: function(){
        //  error callback.  Default to null.
    }
});
```
Happy Coding!
Bryan 
