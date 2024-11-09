//
//  NSURL+Helpers.m
//  ThreadsProject
//
//  Created by LeadDeveloper on 11/9/24.
//

#import "NSURL+Helpers.h"

@implementation NSURL (Helpers)

- (NSMutableDictionary *)toDictionary
{
  NSMutableDictionary *queryStrings = [[NSMutableDictionary alloc] init];
  for (NSString *qs in [self.query componentsSeparatedByString:@"&"]) {
      // Get the parameter name
      NSString *key = [[qs componentsSeparatedByString:@"="] objectAtIndex:0];
      // Get the parameter value
      NSString *value = [[qs componentsSeparatedByString:@"="] objectAtIndex:1];
      value = [value stringByReplacingOccurrencesOfString:@"+" withString:@" "];
      value = [value stringByReplacingPercentEscapesUsingEncoding:NSUTF8StringEncoding];

      queryStrings[key] = value;
  }
  return queryStrings;
}

//extension URL {
//    func valueOf(_ queryParameterName: String) -> String? {
//        guard let url = URLComponents(string: self.absoluteString) else { return nil }
//        return url.queryItems?.first(where: { $0.name == queryParameterName })?.value
//    }
//}

@end
