#import <Cordova/CDV.h>
#import "SoundDialogs.h"


@implementation SNDPasswordAlert
    @synthesize commandId;
@end

@interface SNDFilterRadioController()
@property (nonatomic,strong) NSArray *allOptions;
@property (nonatomic,strong) NSMutableArray *filteredOptions;
@property (nonatomic,strong) NSMutableString *filterString;
@property (nonatomic,strong) NSMutableString *selected;
@property (nonatomic,strong) CDVPlugin *plugin;
@property (nonatomic,strong) NSString *callbackId;


@end

@implementation SNDFilterRadioController
static NSString *cellIdentifier;
@synthesize commandId;

-(id)init:(NSArray*)options title:(NSString *)title buttons:(NSArray*)buttons plugin:(CDVPlugin *) plugin callbackId:(NSString *) callbackId
{
    self = [super init];
    if(self){
        NSLog(@"Radio Initialized");
        
        // Here we deal with creating the table view.
        self.allOptions = options;
        self.filteredOptions = [options mutableCopy];
        self.myTableView = [[UITableView alloc] init];
        self.myTableView.dataSource = self;
        self.myTableView.delegate = self;
        self.callbackId = callbackId;
        self.plugin = plugin;
        [self.myTableView reloadData];
        cellIdentifier = @"rowCell";
        [self.myTableView registerClass:[UITableViewCell class] forCellReuseIdentifier:cellIdentifier];
        [self.view addSubview:self.myTableView];

        
        
        // Now deal with the search input
        self.searchInput = [[UISearchBar alloc] init];
        [self.view addSubview:self.searchInput];
        self.searchInput.delegate = self;
        self.view.backgroundColor = [UIColor whiteColor];
        
        // Finally, lets mess with all the navigations...
        self.navBar = [[UINavigationBar alloc] init];
        UINavigationItem *item = [[UINavigationItem alloc] initWithTitle:title];
        item.leftBarButtonItem = self.CancelButton;
        self.CancelButton = [[UIBarButtonItem alloc] initWithTitle:[buttons objectAtIndex:0] style:UIBarButtonItemStyleBordered target:self action:@selector(cancel)];

        self.OKButton = [[UIBarButtonItem alloc] initWithTitle:[buttons objectAtIndex:1] style:UIBarButtonItemStyleBordered target:self action:@selector(selectValue)];

        item.leftBarButtonItem = self.CancelButton;
        item.rightBarButtonItem = self.OKButton;
        self.OKButton.enabled=FALSE;
        //        item.rightBarButtonItem = self.CancelButton;
        [self.navBar pushNavigationItem:item animated:FALSE];
        [self.view addSubview:self.navBar];
        
        [self SetSizes];
        
    }
    
    return self;
}

-(void)searchBar:(UISearchBar *)searchBar textDidChange:(NSString *)searchText
{
    NSString *filterLower = [searchText lowercaseString];
    if([searchText  isEqual: @""]){
        self.filteredOptions = [self.allOptions mutableCopy];
    } else {
        
    [self.filteredOptions removeAllObjects];
    for(int i = 0; i < [self.allOptions count]; i++){
        NSString *current = [self.allOptions objectAtIndex:i];
        if ([[current lowercaseString]  rangeOfString:filterLower].location != NSNotFound) {
            [self.filteredOptions addObject:current];
        }
    }
    }
    [self.myTableView reloadData];
}

-(void)cancel
{
 
    NSMutableDictionary *jsonObj = [[NSMutableDictionary alloc] init];
    [jsonObj setObject:@"0" forKey:@"submit"];
    
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:jsonObj];
    [self.plugin writeJavascript:[pluginResult toSuccessCallbackString:self.callbackId]];
    [self dismissViewControllerAnimated:true completion:NULL];
    
}

-(void)selectValue
{
    NSMutableDictionary *jsonObj = [[NSMutableDictionary alloc] init];
    
    
    [jsonObj setObject:self.selected forKey:@"selected"];
    [jsonObj setObject:@"1" forKey:@"submit"];
    
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:jsonObj];
    [self.plugin writeJavascript:[pluginResult toSuccessCallbackString:self.callbackId]];
    [self dismissViewControllerAnimated:true completion:NULL];
    
}

-(void) SetSizes
{
    self.navBar.frame = CGRectMake(0,0,self.view.bounds.size.width, 50);
    self.searchInput.frame = CGRectMake(0,50,self.view.bounds.size.width, 50);
    self.myTableView.frame = CGRectMake(0, 100,self.view.bounds.size.width, self.view.bounds.size.height - 100);
}


-(void)viewDidLoad{
    [super viewDidLoad];
    [self SetSizes];
}

-(void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
    [self SetSizes];
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return [self.filteredOptions count];
}

-(UITableViewCell *) tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    cell.textLabel.text = [self.filteredOptions objectAtIndex:indexPath.row];
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{

    
    self.selected = [self.filteredOptions objectAtIndex:indexPath.row];
    self.OKButton.enabled = TRUE;
}

@end


@implementation SoundDialogs

- (void)filterRadio:(CDVInvokedUrlCommand*)command {
    
    
    NSString* callbackId = command.callbackId;
    NSArray* options = [command argumentAtIndex: 0];
    NSArray* buttons = [command argumentAtIndex:1];
    NSString* title = [command argumentAtIndex:2];

    SNDFilterRadioController *sfr = [[SNDFilterRadioController alloc] init:options title:title buttons:buttons plugin: self callbackId: callbackId];

    sfr.modalPresentationStyle = UIModalPresentationFormSheet;
    [self.viewController presentViewController:sfr animated:true completion:nil];
    
}





- (void)passwordPrompt:(CDVInvokedUrlCommand*)command {


    NSString* callbackId = command.callbackId;
    NSString* message = [command argumentAtIndex:0];
    NSString* title = [command argumentAtIndex:1];
    NSArray* buttons = [command argumentAtIndex:2];

    SNDPasswordAlert *alert = [[SNDPasswordAlert alloc] initWithTitle:title message:message delegate:self cancelButtonTitle:[buttons objectAtIndex:0] otherButtonTitles:[buttons objectAtIndex:1], nil];
    alert.commandId = callbackId;
    alert.alertViewStyle = UIAlertViewStyleSecureTextInput;
    [alert show];

}

- (void)alertView:(UIAlertView *)alertView didDismissWithButtonIndex:(NSInteger)buttonIndex
{
    SNDPasswordAlert* sndAlertView = (SNDPasswordAlert *)alertView;

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

