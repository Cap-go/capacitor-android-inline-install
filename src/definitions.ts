/**
 * Android Inline Install Plugin for triggering Google Play in-app install flows.
 *
 * @since 1.0.0
 */
export interface AndroidInlineInstallPlugin {
  /**
   * Start an inline install flow using the Google Play overlay.
   *
   * Note: Only eligible apps can use Inline Install. See:
   * https://play.google.com/console/about/guides/premium-growth-tools/
   *
   * @param options - Configuration for the inline install
   * @returns Promise that resolves with the install result
   * @throws Error if the inline install fails or is not available
   * @since 1.0.0
   * @example
   * ```typescript
   * const result = await AndroidInlineInstall.startInlineInstall({
   *   id: 'com.example.app',
   *   referrer: 'my-referrer',
   *   overlay: true,
   *   fallback: true
   * });
   *
   * if (result.started) {
   *   console.log('Install flow started');
   *   if (result.fallbackUsed) {
   *     console.log('Using fallback Play Store link');
   *   }
   * }
   * ```
   */
  startInlineInstall(options: StartInlineInstallOptions): Promise<StartInlineInstallResult>;

  /**
   * Get the native Capacitor plugin version.
   *
   * @returns Promise that resolves with the plugin version
   * @throws Error if getting the version fails
   * @since 1.0.0
   * @example
   * ```typescript
   * const { version } = await AndroidInlineInstall.getPluginVersion();
   * console.log('Plugin version:', version);
   * ```
   */
  getPluginVersion(): Promise<{ version: string }>;
}

/**
 * Options for starting an inline install flow.
 *
 * @since 1.0.0
 */
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

/**
 * Result of starting an inline install flow.
 *
 * @since 1.0.0
 */
export interface StartInlineInstallResult {
  /** True when the inline install intent has been started. */
  started: boolean;
  /** True if a fallback deep link was used instead of inline overlay. */
  fallbackUsed?: boolean;
}
