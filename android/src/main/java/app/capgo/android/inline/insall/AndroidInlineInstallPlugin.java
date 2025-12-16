package app.capgo.android.inline.install;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "AndroidInlineInstall")
public class AndroidInlineInstallPlugin extends Plugin {

    private static final String TAG = "AndroidInlineInstallPlugin";
    private final String pluginVersion = "7.5.11";

    @PluginMethod
    public void startInlineInstall(PluginCall call) {
        String id = call.getString("id");
        if (id == null || id.trim().isEmpty()) {
            call.reject("Missing required 'id' (package name of the app to install)");
            return;
        }

        String referrer = "utm_source=heycash&utm_medium=inline";
        String callerId = call.getString("callerId", getContext().getPackageName());
        String cslId = call.getString("csl_id");
        boolean overlay = call.getBoolean("overlay", true);
        boolean fallback = false;

        // Build the deep link URL for inline install overlay
        // Format:
        // https://play.google.com/d?id=<id>&referrer=<referrer>&listing=<csl_id>
        // Note: Trying without referrer first, as it might be causing the PITH error
        StringBuilder deepLink = new StringBuilder("https://play.google.com/d?id=")
                .append(id);
        // Temporarily commenting out referrer to test if it's causing the issue

        if (referrer != null && !referrer.trim().isEmpty()) {
            deepLink.append("&referrer=").append(Uri.encode(referrer));
        }
        if (cslId != null && !cslId.trim().isEmpty()) {
            deepLink.append("&listing=").append(Uri.encode(cslId));
        }

        String deepLinkUrl = deepLink.toString();
        Log.d(TAG, "Attempting inline install with URI: " + deepLinkUrl);
        Log.d(TAG, "callerId: " + callerId + ", overlay: " + overlay);
        Log.d(TAG, "referrer: " + referrer);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.android.vending");
        intent.setData(Uri.parse(deepLinkUrl));
        intent.putExtra("overlay", true);
        intent.putExtra("callerId", callerId);

        PackageManager packageManager = getContext().getPackageManager();
        ComponentName resolvedActivity = intent.resolveActivity(packageManager);
        Log.d(TAG, "resolveActivity returned: " + (resolvedActivity != null ? resolvedActivity.toString() : "null"));
        if (resolvedActivity != null) {
            Log.d(TAG, "Activity resolved, attempting to start inline install");
            try {
                getActivity().startActivityForResult(intent, 0);
                Log.d(TAG, "Inline install activity started successfully");
                JSObject ret = new JSObject();
                ret.put("started", true);
                ret.put("fallbackUsed", false);
                call.resolve(ret);
                return;
            } catch (ActivityNotFoundException ex) {
                Log.w(TAG, "ActivityNotFoundException when starting inline install", ex);
                // fall through to fallback handling
            }
        } else {
            Log.w(TAG, "No activity resolved for inline install intent, will use fallback");
        }

        if (fallback) {
            // Fallback to full Play Store deep link (no package forced)
            StringBuilder storeUrl = new StringBuilder("https://play.google.com/store/apps/details?id=").append(id);
            if (referrer != null && !referrer.isEmpty()) {
                storeUrl.append("&referrer=").append(Uri.encode(referrer));
            }
            String fallbackUrl = storeUrl.toString();
            Log.d(TAG, "Falling back to Play Store URL: " + fallbackUrl);
            Intent fallbackIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fallbackUrl));
            try {
                getActivity().startActivityForResult(fallbackIntent, 0  );
                Log.d(TAG, "Fallback Play Store activity started successfully");
                JSObject ret = new JSObject();
                ret.put("started", true);
                ret.put("fallbackUsed", true);
                call.resolve(ret);
                return;
            } catch (ActivityNotFoundException ex) {
                Log.e(TAG, "ActivityNotFoundException when starting fallback Play Store", ex);
                call.reject("Unable to start inline install or fallback Play Store deep link");
                return;
            }
        }

        call.reject("Google Play overlay is unavailable on this device/app (and fallback disabled)");
    }

    @PluginMethod
    public void getPluginVersion(final PluginCall call) {
        try {
            final JSObject ret = new JSObject();
            ret.put("version", this.pluginVersion);
            call.resolve(ret);
        } catch (final Exception e) {
            call.reject("Could not get plugin version", e);
        }
    }
}
