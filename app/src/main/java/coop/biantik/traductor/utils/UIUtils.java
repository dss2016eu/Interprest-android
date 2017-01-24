package coop.biantik.traductor.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import coop.biantik.traductor.R;

/**
 * Created by Sergio on 18/5/15.
 */
public class UIUtils {


    public final static Snackbar showSnackBarWithAction(View parentView, View.OnClickListener myOnClickListener, int resourceId, int resourceIdAction) {

        Snackbar snackBar = Snackbar.make(parentView, resourceId, Snackbar.LENGTH_INDEFINITE)
                .setAction(resourceIdAction, myOnClickListener)
                .setActionTextColor(ContextCompat.getColor(parentView.getContext(), R.color.primary));
        snackBar.show();
        return snackBar;
    }

    public final static void showSnackBar(View parentView, int resourceId) {

        Snackbar.make(parentView, resourceId, Snackbar.LENGTH_LONG).show();
    }

    public final static void showMessage(Context context, CharSequence text) {

        Toast.makeText(context, text, Toast.LENGTH_LONG).show();

    }

    public final static void showMessage(Context context, int resourceId) {

        showMessage(context, context.getResources().getString(resourceId));

    }

    public final static void showErrorMessage(Context context, CharSequence text) {

        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
        toast.show();

    }

    public final static void showErrorMessage(Context context, int resourceId) {
        showErrorMessage(context, context.getResources().getString(resourceId));
    }

    public final static ProgressDialog showProgressMessage(Context context, int resourceId) {
        SpannableString ss1 = new SpannableString(context.getResources().getString(resourceId));
        ss1.setSpan(new RelativeSizeSpan(0.8f), 0, ss1.length(), 0);

        return ProgressDialog.show(context, "", ss1, true, true);

    }

    public final static void closeKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (view != null)
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }

    public static final Animation blinkAnimation() {
        final Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(1000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
    }

}
