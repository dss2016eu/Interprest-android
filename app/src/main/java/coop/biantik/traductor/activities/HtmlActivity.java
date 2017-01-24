package coop.biantik.traductor.activities;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import coop.biantik.traductor.R;

public class HtmlActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String html = getIntent().getStringExtra("html");

        TextView htmlTextView = (TextView) findViewById(R.id.html);
        if (html != null) {
            if (html.equals("legal_terms")){
                getSupportActionBar().setTitle(getResources().getString(R.string.legal_terms));
                htmlTextView.setText(Html.fromHtml(getResources().getString(R.string.legal_terms_content)));
            }
            else if (html.equals("privacy")) {
                getSupportActionBar().setTitle(getResources().getString(R.string.privacy));
                htmlTextView.setText(Html.fromHtml(getResources().getString(R.string.privacy_content)));
            }
            else if (html.equals("licences")) {
                getSupportActionBar().setTitle(getResources().getString(R.string.licences));
                htmlTextView.setText(Html.fromHtml(getResources().getString(R.string.licences_content)));
            }
        }
        htmlTextView.setMovementMethod(LinkMovementMethod.getInstance());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_html, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
