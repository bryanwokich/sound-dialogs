#import <Cordova/CDV.h>

@interface SoundDialogs : CDVPlugin<UIAlertViewDelegate>{}

- (void)passwordPrompt:(CDVInvokedUrlCommand*)command;

@end

@interface SNDAlertView : UIAlertView {}
@property (nonatomic, copy) NSString* commandId;

@end
