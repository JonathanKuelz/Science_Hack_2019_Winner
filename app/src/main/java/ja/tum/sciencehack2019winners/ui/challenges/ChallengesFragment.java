package ja.tum.sciencehack2019winners.ui.challenges;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import ja.tum.sciencehack2019winners.R;

public class ChallengesFragment extends Fragment {

    private ChallengesViewModel challengesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        challengesViewModel =
                ViewModelProviders.of(this).get(ChallengesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_challenges, container, false);

        WebView webView = (WebView) root.findViewById(R.id.challengesWebView);
        webView.loadUrl("https://sciencehackbest.herokuapp.com/challenges");

        return root;
    }
}