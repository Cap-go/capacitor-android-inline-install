import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(AndroidInlineInstallPlugin)
public class AndroidInlineInstallPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "AndroidInlineInstallPlugin"
    public let jsName = "AndroidInlineInstall"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "startInlineInstall", returnType: CAPPluginReturnPromise)
    ]
    private let implementation = AndroidInlineInstall()


    @objc func startInlineInstall(_ call: CAPPluginCall) {
        call.reject("Inline install is only available on Android.")
    }
}
