// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapgoCapacitorAndroidInlineInstall",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "CapgoCapacitorAndroidInlineInstall",
            targets: ["AndroidInlineInstallPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0")
    ],
    targets: [
        .target(
            name: "AndroidInlineInstallPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/AndroidInlineInstallPlugin"),
        .testTarget(
            name: "AndroidInlineInstallPluginTests",
            dependencies: ["AndroidInlineInstallPlugin"],
            path: "ios/Tests/AndroidInlineInstallPluginTests")
    ]
)
