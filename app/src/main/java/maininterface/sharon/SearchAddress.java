package maininterface.sharon;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.huawei.hms.site.api.SearchResultListener;
import com.huawei.hms.site.api.SearchService;
import com.huawei.hms.site.api.SearchServiceFactory;
import com.huawei.hms.site.api.model.AddressDetail;
import com.huawei.hms.site.api.model.HwLocationType;
import com.huawei.hms.site.api.model.SearchStatus;
import com.huawei.hms.site.api.model.Site;
import com.huawei.hms.site.api.model.TextSearchRequest;
import com.huawei.hms.site.api.model.TextSearchResponse;

import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Site activity entrance class.
 */
public class SearchAddress  extends AppCompatActivity {
    private static final String TAG = "SearchAddress";

    private SearchService searchService;
    private TextView resultTextView;
    private EditText queryInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_address);

        String api_key = "CwEAAAAA+J5yyj67S+qTyZ5tZvlRryu3SNLH8jGVlRKC6yg3A41814pBH0AFDwMFDsy9XeDqFbczDMMo+RmMnnSQGp/pLSL7puU=";

        try {
            searchService = SearchServiceFactory.create(this, URLEncoder.encode(api_key, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "encode apikey error");
        }

        queryInput = findViewById(R.id.edit_text_text_search_query);
        resultTextView = findViewById(R.id.response_text_search);
    }

    public void search(View view) {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        textSearchRequest.setQuery(queryInput.getText().toString());
        textSearchRequest.setHwPoiType(HwLocationType.PET_STORE);


        searchService.textSearch(textSearchRequest, new SearchResultListener<TextSearchResponse>() {
            @Override
            public void onSearchResult(TextSearchResponse textSearchResponse) {

                StringBuilder response = new StringBuilder("\n");

                int count = 1;
                AddressDetail addressDetail;
                if (null != textSearchResponse) {
                    if (null != textSearchResponse.getSites()) {
                        for (Site site : textSearchResponse.getSites()) {
                            addressDetail = site.getAddress();
                            response
                                    .append(String.format("[%s]  Store Name: %s, Address: %s, Country: %s, Country Code: %s \r\n\n",
                                            "" + (count++), site.getName(), site.getFormatAddress(),
                                            (addressDetail == null ? "" : addressDetail.getCountry()),
                                            (addressDetail == null ? "" : addressDetail.getCountryCode())));
                        }
                    } else {
                        response.append("textSearchResponse.getSites() is null!");
                    }
                } else {
                    response.append("textSearchResponse is null!");
                }
                Log.d(TAG, "search result is : " + response);
                resultTextView.setText(response.toString());
            }

            @Override
            public void onSearchError(SearchStatus searchStatus) {
                Log.e(TAG, "onSearchError is: " + searchStatus.getErrorCode());
            }
        });
    }
}


