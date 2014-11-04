# PhoneGap SoundDialogs plugin

for Android and iOS, from [Sound Software, Inc.](http://www.sound-software.us)

## 1. Description
This phonegap plugin provides some dialogs for iOS and Android that are not available in the standard cordova plugins.  These include:
* Password Dialog: Collect an obfuscated password.  
* Filtered Radio: Provide a list and the user will get a filterable dialog.  Select one.

## 2.  Installation 

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

```
window.plugins.sound.dialogs.filterRadio({
    callback: function(result){
        if(result.submit){ // submit return 1
            // if form submitted, 0 if canceled.
            alert(result.selected); // And the selected option....
        }
        // success callback function
    },
    title: 'Pick one',
    options: options,
    error: function(error){
        console.log('KVP error!', error);
        //  error callback.  Default to null.
    }
});
```

Happy Coding!
Bryan 
