//
//  ThreadsModule.m
//  ThreadsProject
//
//  Created by LeadDeveloper on 11/2/24.
//
//
#import <Foundation/Foundation.h>
#import "ThreadsModule.h"
#import <React/RCTLog.h>
#import <React/RCTUtils.h>

@implementation ThreadsModule

// js
// import { NativeModules } from "react-native"
// const Threads = NativeModules.Threads
RCT_EXPORT_MODULE(Threads);

RCT_EXPORT_METHOD(callFromJs:(NSString *)someValue
                  secondValue:(NSString *)secondValue
                  callback:(RCTResponseSenderBlock)callback)
{
  RCTLogInfo(@"callFromJs called - someValue: %@ , secondValue: %@", someValue, secondValue);
  
//  typedef void (^RCTResponseSenderBlock)(NSArray *response);
  UIViewController *currentVC = RCTPresentedViewController();
  
//  instance.method()
  //[instance method];
  
  NSString * titleString = [NSString stringWithFormat:@"%@: %@", @"someValue", someValue];
  NSString * bodyString = [NSString stringWithFormat:@"%@: %@", @"secondValue", secondValue];
  
  UIAlertController* alertController = [UIAlertController alertControllerWithTitle:titleString message:bodyString preferredStyle:UIAlertControllerStyleAlert];
  
  UIAlertAction* doneAction = [UIAlertAction actionWithTitle:@"done" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
          NSLog(@"%s, line: %d, %@",__func__, __LINE__, @"done action clicked");
        callback(@[@"test01", @"done"]);
      }];
      
    UIAlertAction* cancelAction = [UIAlertAction actionWithTitle:@"cancel" style:UIAlertActionStyleDestructive handler:^(UIAlertAction * _Nonnull action) {
        NSLog(@"%s, line: %d, %@",__func__, __LINE__, @"cancel action clicked");
        callback(@[[NSNull null], @"cancel"]);
    }];
  
  [alertController addAction:doneAction];
  [alertController addAction:cancelAction];
  
  // true, false
  // YES, NO
  dispatch_async(dispatch_get_main_queue(), ^{
    [currentVC presentViewController:alertController animated:YES completion:^{
        RCTLogInfo(@"done");
    }];
  });
  
  
//  UIViewController
}

RCT_EXPORT_METHOD(shareThreads:(NSString *)someValue)
{
  RCTLogInfo(@"callFro");
   //
   NSString* urlString = @"https://threads.net/oauth/authorize?client_id=521096737301031&redirect_uri=https%3A%2F%2Fhidden-shadow-6351.enfn2001.workers.dev%2Fauth%2F&scope=threads_basic%2Cthreads_content_publish&response_type=code";

   [[UIApplication sharedApplication] openURL: [NSURL URLWithString: urlString]];

  
  
//  UIViewController
}



+(void)someApiCall:(NSString*)someValue completion:(void(^)(NSString*))completion
{
  RCTLogInfo(@"someApiCall - someValue:%@", someValue);
  
  
}

@end
