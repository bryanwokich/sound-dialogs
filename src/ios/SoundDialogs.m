#import <Cordova/CDV.h>
#import "SoundDialogs.h"


@implementation SNDAlertView

@synthesize commandId;

@end

@implementation SoundDialogs



- (void)passwordPrompt:(CDVInvokedUrlCommand*)command {


    NSString* callbackId = command.callbackId;
    NSString* message = [command argumentAtIndex:0];
    NSString* title = [command argumentAtIndex:1];
    NSArray* buttons = [command argumentAtIndex:2];

    SNDAlertView *alert = [[SNDAlertView alloc] initWithTitle:title message:message delegate:self cancelButtonTitle:[buttons objectAtIndex:0] otherButtonTitles:[buttons objectAtIndex:1], nil];
    alert.commandId = callbackId;
    alert.alertViewStyle = UIAlertViewStyleSecureTextInput;
    [alert show];

}

- (void)alertView:(UIAlertView *)alertView didDismissWithButtonIndex:(NSInteger)buttonIndex
{
    SNDAlertView* sndAlertView = (SNDAlertView *)alertView;

    NSMutableDictionary *jsonObj = [[NSMutableDictionary alloc] init];


    if (buttonIndex == 0)
    {
        [jsonObj setObject:@"0" forKey:@"submit"];
    }
    else if(buttonIndex == 1)
    {
        NSString *password = [[alertView textFieldAtIndex:0] text];
        [jsonObj setObject:password forKey:@"password"];
        [jsonObj setObject:@"1" forKey:@"submit"];
    }

    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:jsonObj];
    [self writeJavascript:[pluginResult toSuccessCallbackString:sndAlertView.commandId]];

}

@end
/*
CDVAlertView* cdvAlertView = (CDVAlertView*)alertView;
CDVPluginResult* result;

// Determine what gets returned to JS based on the alert view type.
if (alertView.alertViewStyle == UIAlertViewStyleDefault) {
    // For alert and confirm, return button index as int back to JS.
    result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsInt:(int)(buttonIndex + 1)];
} else {
    // For prompt, return button index and input text back to JS.
    NSString* value0 = [[alertView textFieldAtIndex:0] text];
    NSDictionary* info = @{
                           @"buttonIndex":@(buttonIndex + 1),
                           @"input1":(value0 ? value0 : [NSNull null])
                           };
    result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:info];
}
[self.commandDelegate sendPluginResult:result callbackId:cdvAlertView.callbackId];
*/