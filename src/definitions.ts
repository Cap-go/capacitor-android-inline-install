export interface AndroidInlineInstallPlugin {
  /**
   * Start an inline install flow using the Google Play overlay.
   *
   * Note: Only eligible apps can use Inline Install. See:
   * https://play.google.com/console/about/guides/premium-growth-tools/
   */
  startInlineInstall(options: StartInlineInstallOptions): Promise<StartInlineInstallResult>;
}

export interface StartInlineInstallOptions {
  /** Package name of the app to be installed (target app). */
  id: string;
  /** Referrer string to pass to Play. Optional but recommended. */
  referrer?: string;
  /**
   * Package name of your app (caller). Defaults to the current app package
   * if omitted.
   */
  callerId?: string;
  /** Optional Custom Store Listing ID. */
  csl_id?: string;
  /** Whether to request the Play overlay. Defaults to true. */
  overlay?: boolean;
  /** If true, falls back to full Play Store deep link when overlay unavailable. Defaults to true. */
  fallback?: boolean;
}

export interface StartInlineInstallResult {
  /** True when the inline install intent has been started. */
  started: boolean;
  /** True if a fallback deep link was used instead of inline overlay. */
  fallbackUsed?: boolean;
}
