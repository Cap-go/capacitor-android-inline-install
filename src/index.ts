import { registerPlugin } from '@capacitor/core';

import type { AndroidInlineInstallPlugin } from './definitions';

const AndroidInlineInstall = registerPlugin<AndroidInlineInstallPlugin>('AndroidInlineInstall', {
  web: () => import('./web').then((m) => new m.AndroidInlineInstallWeb()),
});

export * from './definitions';
export { AndroidInlineInstall };
