# PhoneGap SoundDialogs plugin

for Android and iOS, from [Sound Software, Inc.](http://www.sound-software.us)

## 1. Description
This is basic phonegap plugin for dialogs.  I may extend it in the future, so it has a generic name, but as of now, it deals with password prompts as I needed one and figured someone else might, too.  

As of this writing it supports android.  I'm going to get iOS up soon.  Once I have those two I'll put up some directions.


## 1.  Installation 

You can install the plugin using the cordova cli. 
```
$ cordova plugin add git@github.com:bryanwokich/sound-dialogs.git
```

## 2. Usage
```
window.plugins.sound.dialogs.passwordPrompt({
    callback: function(results){
      // success callback function 
    }, 
    title: "Title on Prompt", // optional, defaults to "Password"
    message: "Message on prompt", // optional, defaults to "Please provid a password
    buttonLabels: ["Cancel Display Value", "Accept Display Value"], // optional, defaults to ["Cancel", "OK"]
    error: function(){
    //  error callback.  Default to null.
    }
  });
```
Happy Coding!
Bryan 
