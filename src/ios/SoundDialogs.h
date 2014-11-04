#import <Cordova/CDV.h>

@interface SoundDialogs : CDVPlugin<UIAlertViewDelegate>{}

- (void)passwordPrompt:(CDVInvokedUrlCommand*)command;
- (void)filterRadio:(CDVInvokedUrlCommand*)command;

@end

@interface SNDPasswordAlert : UIAlertView {}
    @property (nonatomic, copy) NSString* commandId;
@end

@interface SNDFilterRadioController : UIViewController<UITableViewDelegate,UITableViewDataSource, UISearchBarDelegate>
@property (nonatomic, copy) NSString* commandId;
@property (strong, nonatomic)  UITableView *myTableView;
@property (strong, nonatomic)  UISearchBar *searchInput;
@property (strong, nonatomic)  UINavigationBar *navBar;
@property (strong, nonatomic)  UIBarButtonItem *OKButton;
@property (strong, nonatomic)  UIBarButtonItem *CancelButton;
@end