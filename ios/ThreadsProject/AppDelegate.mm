#import "AppDelegate.h"

#import <React/RCTBundleURLProvider.h>

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  self.moduleName = @"ThreadsProject";
  // You can add your custom initial props in the dictionary below.
  // They will be passed down to the ViewController used by React Native.
  self.initialProps = @{};

  return [super application:application didFinishLaunchingWithOptions:launchOptions];
}

- (BOOL)application:(UIApplication *)app openURL:(NSURL *)url options:(NSDictionary<UIApplicationOpenURLOptionsKey,id> *)options
{
  
  NSString* codeValue = [[url toDictionary] objectForKey:@"code"];
  NSLog(@"codeValue : %@", codeValue);
  
  
  NSString * titleString = [NSString stringWithFormat:@"%@: %@", @"codeValue", codeValue];

  
  UIAlertController* alertController = [UIAlertController alertControllerWithTitle:titleString message:nil preferredStyle:UIAlertControllerStyleAlert];
  
  UIAlertAction* doneAction = [UIAlertAction actionWithTitle:@"done" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
          NSLog(@"%s, line: %d, %@",__func__, __LINE__, @"done action clicked");
        
      }];
     
  
  [alertController addAction:doneAction];
  
  
  UIViewController *topController = [UIApplication sharedApplication].keyWindow.rootViewController;

    while (topController.presentedViewController) {
        topController = topController.presentedViewController;
    }
  
  // true, false
  // YES, NO
  dispatch_async(dispatch_get_main_queue(), ^{
    [topController presentViewController:alertController animated:YES completion:^{
        RCTLogInfo(@"done");
    }];
  });
//  guard let url = URLContexts.first?.url else {
//          return
//      }
//
//
//  // https://stackoverflow.com/questions/41421686/get-the-value-of-url-parameters
//  
//  print(#file, #function, #line, "url:")
//  
//  if let code = url.valueOf("code") {
//      print(#file, #function, #line, "url: \(url)")
//      print(#file, #function, #line, "code: \(code)")
////            ApiManager.shared.getAccessToken(code: code, completion: nil)
//      ApiManager.shared.getToken(code: code)
//      
//  }
  return YES;
}

- (NSURL *)sourceURLForBridge:(RCTBridge *)bridge
{
  return [self bundleURL];
}

- (NSURL *)bundleURL
{
#if DEBUG
  return [[RCTBundleURLProvider sharedSettings] jsBundleURLForBundleRoot:@"index"];
#else
  return [[NSBundle mainBundle] URLForResource:@"main" withExtension:@"jsbundle"];
#endif
}

@end


//
//
//extension URL {
//    func valueOf(_ queryParameterName: String) -> String? {
//        guard let url = URLComponents(string: self.absoluteString) else { return nil }
//        return url.queryItems?.first(where: { $0.name == queryParameterName })?.value
//    }
//}
