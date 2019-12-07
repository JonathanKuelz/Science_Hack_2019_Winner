package ja.tum.sciencehack2019winners.ui.stats;

import android.graphics.Color;
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
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class StatsFragment extends Fragment {

    private LineChartView lineChartView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_stats, container, false);
        lineChartView = (LineChartView) root.findViewById(R.id.chart);

        String decimalPattern = "#.##";
        DecimalFormat decimalFormat = new DecimalFormat(decimalPattern);

        List<PointValue> values = new ArrayList<PointValue>();

        PointValue tempPointValue;
        for (float i = 0; i <= 360.0; i+= 15.0f) {
            tempPointValue = new PointValue(i, Math.abs((float)Math.sin(Math.toRadians(i))));
            tempPointValue.setLabel(decimalFormat
                    .format(Math.abs((float)Math.sin(Math.toRadians(i)))));
            values.add(tempPointValue);
        }

        Line line = new Line(values)
                .setColor(Color.BLUE)
                .setCubic(false)
                .setHasPoints(true).setHasLabels(true);
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

        return root;
    }
}