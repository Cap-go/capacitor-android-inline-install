# @capgo/capacitor-android-inline-install

<a href="https://capgo.app/"><img src='https://raw.githubusercontent.com/Cap-go/capgo/main/assets/capgo_banner.png' alt='Capgo - Instant updates for capacitor'/></a>

<div align="center">
  <h2><a href="https://capgo.app/?ref=plugin_android_inline_install"> ➡️ Get Instant updates for your App with Capgo 🚀</a></h2>
  <h2><a href="https://capgo.app/consulting/?ref=plugin_android_inline_install"> Fix your annoying bug now, Hire a Capacitor expert 💪</a></h2>
</div>

Trigger the Google Play Inline Install overlay from a Capacitor app.

> [!NOTE]
> Inline Install is [only available to certain apps that qualify for Premium Growth Tools](https://developer.android.com/quality/core-value/app-eligibility). See eligibility and program details here: https://play.google.com/console/about/guides/premium-growth-tools/

> [!CAUTION] 
> When using an app id that is published on the Play Store, but might not necessarily be eligible for Premium Growth Tools, the fallback will be used. (No overlay will be shown.)

## Why Android Inline Install?

The only plugin supporting Google Play's **Inline Install API** - no other Capacitor plugin implements this:

- **In-app installations** - Install apps without leaving your app
- **Premium Growth Tool** - Access to Google's exclusive promotion features
- **Seamless UX** - Overlay install experience keeps users engaged
- **Fallback handling** - Automatically falls back to Play Store if overlay unavailable
- **Cross-promotion** - Perfect for app families and ecosystem growth

Essential for apps eligible for Google Play Premium Growth Tools and app ecosystem builders.

## Documentation

The most complete doc is available here: https://capgo.app/docs/plugins/android-inline-install/

## Install

```bash
npm install @capgo/capacitor-android-inline-install
npx cap sync
```

## Usage

```ts
import { AndroidInlineInstall } from '@capgo/capacitor-android-inline-install';

// Start inline install for a target app by package name
await AndroidInlineInstall.startInlineInstall({
  id: 'com.example.targetapp',
  referrer: 'campaign=my-campaign', // optional but recommended
  // callerId defaults to your app's package name
  // csl_id: 'your-custom-store-listing-id',
  overlay: true,   // default true
  fallback: true,  // default true: open full Play Store page if overlay unavailable
});
```

### Behavior

- The plugin attempts to open the Google Play overlay via an intent that targets `com.android.vending` with a deep link of the form `https://play.google.com/d?id=…&referrer=…&listing=…` and extras `overlay` and `callerId`.
- If the overlay is not available on the device or for your app, and `fallback` is true, it opens the full Play Store details page for the target app as a deep link.
- The promise resolves with `{ started: true, fallbackUsed: boolean }` when an intent is started.
- The promise rejects with a descriptive error if neither the overlay nor the fallback can be started, or if required parameters are missing.

### Supported platforms

- Android: Supported, subject to Google Play eligibility and device support.
- iOS/Web: Not supported; the method rejects with an informative error.

## API

<docgen-index>

* [`startInlineInstall(...)`](#startinlineinstall)
* [`getPluginVersion()`](#getpluginversion)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

Android Inline Install Plugin for triggering Google Play in-app install flows.

### startInlineInstall(...)

```typescript
startInlineInstall(options: StartInlineInstallOptions) => Promise<StartInlineInstallResult>
```

Start an inline install flow using the Google Play overlay.

Note: Only eligible apps can use Inline Install. See:
https://play.google.com/console/about/guides/premium-growth-tools/

| Param         | Type                                                                            | Description                            |
| ------------- | ------------------------------------------------------------------------------- | -------------------------------------- |
| **`options`** | <code><a href="#startinlineinstalloptions">StartInlineInstallOptions</a></code> | - Configuration for the inline install |

**Returns:** <code>Promise&lt;<a href="#startinlineinstallresult">StartInlineInstallResult</a>&gt;</code>

**Since:** 1.0.0

--------------------


### getPluginVersion()

```typescript
getPluginVersion() => Promise<{ version: string; }>
```

Get the native Capacitor plugin version.

**Returns:** <code>Promise&lt;{ version: string; }&gt;</code>

**Since:** 1.0.0

--------------------


### Interfaces


#### StartInlineInstallResult

Result of starting an inline install flow.

| Prop               | Type                 | Description                                                      |
| ------------------ | -------------------- | ---------------------------------------------------------------- |
| **`started`**      | <code>boolean</code> | True when the inline install intent has been started.            |
| **`fallbackUsed`** | <code>boolean</code> | True if a fallback deep link was used instead of inline overlay. |


#### StartInlineInstallOptions

Options for starting an inline install flow.

| Prop           | Type                 | Description                                                                                  |
| -------------- | -------------------- | -------------------------------------------------------------------------------------------- |
| **`id`**       | <code>string</code>  | Package name of the app to be installed (target app).                                        |
| **`referrer`** | <code>string</code>  | Referrer string to pass to Play. Optional but recommended.                                   |
| **`callerId`** | <code>string</code>  | Package name of your app (caller). Defaults to the current app package if omitted.           |
| **`csl_id`**   | <code>string</code>  | Optional Custom Store Listing ID.                                                            |
| **`overlay`**  | <code>boolean</code> | Whether to request the Play overlay. Defaults to true.                                       |
| **`fallback`** | <code>boolean</code> | If true, falls back to full Play Store deep link when overlay unavailable. Defaults to true. |

</docgen-api>
