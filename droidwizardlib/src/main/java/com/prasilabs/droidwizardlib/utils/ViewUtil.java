package com.prasilabs.droidwizardlib.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.prasilabs.droidwizardlib.custom.CharacterDrawable;
import com.prasilabs.droidwizardlib.custom.CircleTransform;
import com.prasilabs.droidwizardlib.debug.ConsoleLog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by prasi on 26/5/16.
 */
public class ViewUtil
{
    private static final String TAG = ViewUtil.class.getSimpleName();

    public static int dpAPixeles(int dp, Context contexto) {
        Resources r = contexto.getResources();
        int px = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());

        return px;
    }

    public static void ts(Context context, String message)
    {
        if(context != null && !TextUtils.isEmpty(message))
        {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static void t(Context context, String message)
    {
        if(context != null && !TextUtils.isEmpty(message))
        {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    public static void hideProgressView(Context context, ViewGroup viewGroup)
    {
        showProgressView(context, viewGroup, false, false);
    }

    public static void showProgressView(Context context, ViewGroup viewGroup, boolean isBig)
    {
        showProgressView(context, viewGroup, isBig, true);
    }

    private static void showProgressView(Context context, ViewGroup viewGroup, boolean isBig, boolean isShow)
    {
        if(isShow)
        {
            /*View view = View.inflate(context, R.layout.widget_progress, null);

            ProgressBar bigProgressBar = (ProgressBar) view.findViewById(R.id.big_progress_bar);
            ProgressBar smallProgressBar = (ProgressBar) view.findViewById(R.id.small_progress_bar);

            if(isBig)
            {
                bigProgressBar.setVisibility(View.VISIBLE);
                smallProgressBar.setVisibility(View.GONE);
            }
            else
            {
                bigProgressBar.setVisibility(View.GONE);
                smallProgressBar.setVisibility(View.VISIBLE);
            }


            for (int i = 0; i < viewGroup.getChildCount(); i++)
            {
                viewGroup.getChildAt(i).setVisibility(View.GONE);
            }
            viewGroup.addView(view);*/
        }
        else
        {
            if(viewGroup.getChildCount() > 0)
            {
                if(viewGroup.getChildCount() > 1)
                {
                    for(int i= 0; i<viewGroup.getChildCount() -1; i++)
                    {
                        viewGroup.getChildAt(i).setVisibility(View.VISIBLE);
                    }
                }

                viewGroup.removeView(viewGroup.getChildAt(viewGroup.getChildCount() - 1));
            }
        }
    }

    public static void renderImage(ImageView view, String url, boolean isCircle, String fallBackText)
    {
        if(!TextUtils.isEmpty(url))
        {
            ConsoleLog.i(TAG, "picture url is : " + url);
            try
            {
                Drawable fallBackDrawable;
                if(!TextUtils.isEmpty(fallBackText))
                {
                    fallBackDrawable = new CharacterDrawable(fallBackText, CharacterDrawable.SHAPE_CIRCLE);
                }
                else
                {
                    fallBackDrawable = ActivityCompat.getDrawable(view.getContext(), android.R.drawable.ic_menu_camera);
                }

                RequestCreator requestCreator = Picasso.with(view.getContext()).load(url.trim()).placeholder(fallBackDrawable);
                if(isCircle)
                {
                    requestCreator.transform(new CircleTransform());
                }
                requestCreator.into(view);
            }
            catch (Exception e)
            {
                ConsoleLog.e(e);
            }
        }
    }

    public static void renderImage(ImageView view, int resource, boolean isCircle)
    {
        try
        {
            RequestCreator requestCreator = Picasso.with(view.getContext()).load(resource);
            if(isCircle)
            {
                requestCreator.transform(new CircleTransform());
            }
            requestCreator.into(view);
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);
        }
    }

    public static String toTitleCase(String text) {
        if (!TextUtils.isEmpty(text)) {
            return text.substring(0, 1).toUpperCase() + text.substring(1);
        } else {
            return "";
        }
    }

    public static String toUpperCase(String text) {
        if (!TextUtils.isEmpty(text)) {
            return text.toUpperCase();
        } else {
            return "";
        }
    }

    public static String toLowerCase(String text) {
        if (!TextUtils.isEmpty(text)) {
            return text.toLowerCase();
        } else {
            return "";
        }
    }

    public static String toCamelCase(String text) {
        if (!TextUtils.isEmpty(text)) {
            String[] words = text.split(" ");
            StringBuilder camelText = new StringBuilder();
            for (String word : words) {
                camelText.append(toTitleCase(word));
                camelText.append(" ");
            }
            return camelText.toString().trim();
        } else {
            return "";
        }
    }

    public static void showPrivacyDialog(Context context) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Terms & Conditions");

        final WebView wv = new WebView(context);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl("file:///android_asset/html/terms_conditions.html");

        alert.setView(wv);
        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();
    }


    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static String getRelativeTime(String datetime) {
        long timeInMillis = System.currentTimeMillis();
        if (!TextUtils.isEmpty(datetime))
        {
            // Or a mysql datetime. Hence handling both.
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = null;
            try {
                d = f.parse(datetime);
            } catch (Exception e) {

            }

            try {
                if (d != null) {
                    timeInMillis = d.getTime();
                } else {
                    timeInMillis = Long.valueOf(datetime);
                }
            } catch (NumberFormatException nfe) {
            }
        }
        return getRelativeTime(timeInMillis);
    }

    public static String getRelativeTime(long timeInMillis)
    {
        CharSequence relativeTimeSpanString = null;
        try {
            // Time comes either as millisecond values from search or string from mysql
            timeInMillis = Long.valueOf(timeInMillis);
        }
        catch (NumberFormatException ne)
        {
            return "";
        }
        relativeTimeSpanString = DateUtils.getRelativeTimeSpanString(timeInMillis, System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS, DateUtils.FORMAT_SHOW_TIME);
        if (relativeTimeSpanString != null) {
            return relativeTimeSpanString.toString();
        } else {
            return "";
        }
    }

    public static String getReadableTime(long timeInMillis)
    {
        StringBuilder stringBuilder = new StringBuilder();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);

        if(calendar.getTimeInMillis() < (System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1)))
        {

        }


        return stringBuilder.toString();
    }

    public static Intent getPlayStoreIntent(Context context)
    {
        Intent intent;
        if(isGooglePlayInstalled(context))
        {
            Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
            intent = new Intent(Intent.ACTION_VIEW, uri);
        }
        else
        {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName()));
        }

        return intent;
    }

    public static void openPlayStoreRating(Context context)
    {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    private static boolean isGooglePlayInstalled(Context context) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed = false;
        try
        {
            PackageInfo info = pm.getPackageInfo("com.android.vending", PackageManager.GET_ACTIVITIES);
            String label = (String) info.applicationInfo.loadLabel(pm);
            app_installed = (label != null && !label.equals("Market"));
        }
        catch (PackageManager.NameNotFoundException e)
        {
            app_installed = false;
        }
        return app_installed;
    }

    public static String getTimeFromSecs(long duration)
    {
        return getTimeFromSecs(duration, false);
    }

    public static String getTimeFromSecs(long duration, boolean isCaps)
    {
        long days = TimeUnit.SECONDS.toDays(duration);
        duration -= TimeUnit.DAYS.toSeconds(days);

        long hours = TimeUnit.SECONDS.toHours(duration);
        duration -= TimeUnit.HOURS.toSeconds(hours);

        long minutes = TimeUnit.SECONDS.toMinutes(duration);
        duration -= TimeUnit.MINUTES.toSeconds(minutes);

        long seconds = TimeUnit.SECONDS.toSeconds(duration);

        StringBuilder msg = new StringBuilder( );
        if (days!=0)
        {
            if(isCaps)
            {
                msg.append(days).append("D ");
            }
            else
            {
                msg.append(days).append("d ");
            }
        }
        if (hours!=0)
        {
            if(isCaps)
            {
                msg.append(hours).append("H ");
            }
            else
            {
                msg.append(hours).append("h ");
            }
        }
        if (minutes!=0)
        {
            if(isCaps)
            {
                msg.append(minutes).append("M ");
            }
            else
            {
                msg.append(minutes).append("m ");
            }
        }
        if (seconds!=0)
        {
            if(isCaps)
            {
                msg.append(seconds).append("S");
            }
            else
            {
                msg.append(seconds).append("s");
            }
        }

        return msg.toString();
    }

    public static Bitmap takeScreenshot(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, statusBarHeight, width,
                height - statusBarHeight);
        view.destroyDrawingCache();
        return bitmap2;
    }

    public static void animateTextView(int initialValue, int finalValue, final TextView textview) {
        DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(0.8f);
        int start = Math.min(initialValue, finalValue);
        int end = Math.max(initialValue, finalValue);
        int difference = Math.abs(finalValue - initialValue);
        Handler handler = new Handler();
        for (int count = start; count <= end; count++) {
            int time = Math.round(decelerateInterpolator.getInterpolation((((float) count) / difference)) * 100) * count;
            final int finalCount = ((initialValue > finalValue) ? initialValue - count : count);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textview.setText(String.valueOf(finalCount));
                }
            }, time);
        }
    }



    public static void setBadgeCount(Context context, LayerDrawable icon, int count) {

        /*BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        if(count > 0)
        {
            badge.setAlpha(255);
        }
        else
        {
            badge.setAlpha(0);
        }
        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);*/
    }
}
