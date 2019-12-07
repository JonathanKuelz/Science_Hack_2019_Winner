package ja.tum.sciencehack2019winners.ui.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import ja.tum.sciencehack2019winners.R;

public class ActivitiesFragment extends Fragment {

    private ActivitiesViewModel activitiesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        activitiesViewModel =
                ViewModelProviders.of(this).get(ActivitiesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_activities, container, false);

        WebView webView = (WebView) root.findViewById(R.id.activitiesWebView);
        webView.loadUrl("https://sciencehackbest.herokuapp.com/activities");

        return root;
    }
}