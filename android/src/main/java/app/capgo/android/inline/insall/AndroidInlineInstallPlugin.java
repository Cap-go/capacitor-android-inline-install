package app.capgo.android.inline.insall;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "AndroidInlineInstall")
public class AndroidInlineInstallPlugin extends Plugin {

    private AndroidInlineInstall implementation = new AndroidInlineInstall();

    @PluginMethod
    public void startInlineInstall(PluginCall call) {
        String id = call.getString("id");
        if (id == null || id.trim().isEmpty()) {
            call.reject("Missing required 'id' (package name of the app to install)");
            return;
        }

        String referrer = call.getString("referrer", "");
        String callerId = call.getString("callerId", getContext().getPackageName());
        String cslId = call.getString("csl_id");
        boolean overlay = call.getBoolean("overlay", true);
        boolean fallback = call.getBoolean("fallback", true);

        // Build the deep link URL for inline install overlay
        StringBuilder deepLink = new StringBuilder("https://play.google.com/d?id=")
            .append(id)
            .append("&referrer=")
            .append(Uri.encode(referrer == null ? "" : referrer));
        if (cslId != null && !cslId.trim().isEmpty()) {
            deepLink.append("&listing=").append(Uri.encode(cslId));
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.android.vending");
        intent.setData(Uri.parse(deepLink.toString()));
        intent.putExtra("overlay", overlay);
        intent.putExtra("callerId", callerId);

        PackageManager packageManager = getContext().getPackageManager();
        if (intent.resolveActivity(packageManager) != null) {
            try {
                getActivity().startActivity(intent);
                JSObject ret = new JSObject();
                ret.put("started", true);
                ret.put("fallbackUsed", false);
                call.resolve(ret);
                return;
            } catch (ActivityNotFoundException ex) {
                // fall through to fallback handling
            }
        }

        if (fallback) {
            // Fallback to full Play Store deep link (no package forced)
            StringBuilder storeUrl = new StringBuilder("https://play.google.com/store/apps/details?id=")
                .append(id);
            if (referrer != null && !referrer.isEmpty()) {
                storeUrl.append("&referrer=").append(Uri.encode(referrer));
            }
            Intent fallbackIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(storeUrl.toString()));
            try {
                getActivity().startActivity(fallbackIntent);
                JSObject ret = new JSObject();
                ret.put("started", true);
                ret.put("fallbackUsed", true);
                call.resolve(ret);
                return;
            } catch (ActivityNotFoundException ex) {
                call.reject("Unable to start inline install or fallback Play Store deep link");
                return;
            }
        }

        call.reject("Google Play overlay is unavailable on this device/app (and fallback disabled)");
    }
}
