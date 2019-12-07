package ja.tum.sciencehack2019winners.ui.stats;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import ja.tum.sciencehack2019winners.R;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;

public class StatsFragment extends Fragment {

    private LineChartView lineChartView;
    private PieChartView pieChartView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_stats, container, false);

        populateLineChart(root);
        populatePieChart(root);

        return root;
    }

    public void populatePieChart(View root){
        PieChartData data;

        pieChartView = root.findViewById(R.id.pie_chart);

        //OPTIONS
        boolean hasLabels = false;
        boolean hasLabelsOutside = false;
        boolean hasCenterCircle = false;
        boolean hasCenterText1 = false;
        boolean hasCenterText2 = false;
        boolean isExploded = false;
        boolean hasLabelForSelected = false;
        // OPTIONS END


        int numValues = 6;

        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues; ++i) {
            SliceValue sliceValue = new SliceValue((float) Math.random() * 30 + 15, ChartUtils.pickColor());
            values.add(sliceValue);
        }

        data = new PieChartData(values);
        data.setHasLabels(hasLabels);
        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data.setHasLabelsOutside(hasLabelsOutside);
        data.setHasCenterCircle(hasCenterCircle);

        if (isExploded) {
            data.setSlicesSpacing(24);
        }

//        if (hasCenterText1) {
//            data.setCenterText1("Hello!");
//
//            // Get roboto-italic font.
//            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");
//            data.setCenterText1Typeface(tf);
//
//            // Get font size from dimens.xml and convert it to sp(library uses sp values).
//            data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
//                    (int) getResources().getDimension(R.dimen.pie_chart_text1_size)));
//        }
//
//        if (hasCenterText2) {
//            data.setCenterText2("Charts (Roboto Italic)");
//
//            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");
//
//            data.setCenterText2Typeface(tf);
//            data.setCenterText2FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
//                    (int) getResources().getDimension(R.dimen.pie_chart_text2_size)));
//        }

        pieChartView.setPieChartData(data);



    }

    public void populateLineChart(View root){
        lineChartView = root.findViewById(R.id.line_chart);


        List<PointValue> values = new ArrayList<>();

        PointValue tempPointValue;
        for (float i = 0; i <= 360.0; i+= 15.0f) {
            tempPointValue = new PointValue(i, Math.abs((float)Math.sin(Math.toRadians(i))));

            values.add(tempPointValue);
        }

        Line line = new Line(values)
                .setColor(Color.BLUE)
                .setCubic(false)
                .setHasPoints(true).setHasLabels(false);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        List<AxisValue> axisValuesForX = new ArrayList<>();
        List<AxisValue> axisValuesForY = new ArrayList<>();
        AxisValue tempAxisValue;
        for (float i = 0; i <= 360.0f; i += 30.0f){
            tempAxisValue = new AxisValue(i);
            tempAxisValue.setLabel(i+"\u00b0");
            axisValuesForX.add(tempAxisValue);
        }

        for (float i = 0.0f; i <= 1.00f; i += 0.25f){
            tempAxisValue = new AxisValue(i);
            tempAxisValue.setLabel(""+i);
            axisValuesForY.add(tempAxisValue);
        }

        Axis xAxis = new Axis(axisValuesForX);
        Axis yAxis = new Axis(axisValuesForY);
        data.setAxisXBottom(xAxis);
        data.setAxisYLeft(yAxis);


        lineChartView.setLineChartData(data);
    }
}