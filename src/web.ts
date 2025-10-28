import { WebPlugin } from '@capacitor/core';

import type { AndroidInlineInstallPlugin, StartInlineInstallOptions, StartInlineInstallResult } from './definitions';

export class AndroidInlineInstallWeb extends WebPlugin implements AndroidInlineInstallPlugin {
  async startInlineInstall(_options: StartInlineInstallOptions): Promise<StartInlineInstallResult> {
    throw this.unavailable('Inline install is only available on Android.');
  }

  async getPluginVersion(): Promise<{ version: string }> {
    return { version: 'web' };
  }
}
