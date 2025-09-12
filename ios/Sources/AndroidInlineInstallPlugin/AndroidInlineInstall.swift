import Foundation

@objc public class AndroidInlineInstall: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
