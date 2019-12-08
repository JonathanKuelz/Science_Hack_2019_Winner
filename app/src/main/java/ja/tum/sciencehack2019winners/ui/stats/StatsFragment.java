package ja.tum.sciencehack2019winners.ui.stats;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
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
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;

public class StatsFragment extends Fragment {

    private LineChartView lineChartView;
    private PieChartView pieChartView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_stats, container, false);


        // Populate summary
        populatePieChartSummary(root);
        String json_string = loadJSONFromAsset(getActivity(), "CO2_Daten/total_emissions.json");
        try {
            JSONArray jObject = new JSONArray(json_string);
            populateLineChart(root, R.id.line_chart, jObject, Color.BLUE);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Populate transport
        populatePieChartTransport(root);
        String json_string_transport = loadJSONFromAsset(getActivity(), "CO2_Daten/transportation_emission.json");
        try {
            JSONArray jObject = new JSONArray(json_string_transport);
            populateLineChart(root, R.id.line_chart_transport, jObject, Color.BLUE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root;
    }

    public void populatePieChartSummary(View root) {
        PieChartData data;

        pieChartView = root.findViewById(R.id.pie_chart);

        //OPTIONS
        boolean hasLabels = true;
        boolean hasLabelsOutside = false;
        boolean hasCenterCircle = true;
        boolean isExploded = false;
        boolean hasLabelForSelected = false;
        // OPTIONS END

        SliceValue tmpSlice;


        List<SliceValue> values = new ArrayList<SliceValue>();

        tmpSlice = new SliceValue(10, Color.RED);
        tmpSlice.setLabel(("Transportation " + tmpSlice.getValue() + "%" ));
        values.add(tmpSlice);

        tmpSlice = new SliceValue(12, Color.GRAY);
        tmpSlice.setLabel(("Food " + tmpSlice.getValue() + "%" ));
        values.add(tmpSlice);

        tmpSlice = new SliceValue(7, Color.MAGENTA);
        tmpSlice.setLabel(("Consume " + tmpSlice.getValue() + "%" ));
        values.add(tmpSlice);

        tmpSlice = new SliceValue(25, Color.YELLOW);
        tmpSlice.setLabel(("Streaming / devices " + tmpSlice.getValue() + "%" ));
        values.add(tmpSlice);

        tmpSlice = new SliceValue(48, Color.GREEN);
        tmpSlice.setLabel(("Baseline " + tmpSlice.getValue() + "%" ));
        values.add(tmpSlice);

        data = new PieChartData(values);
        data.setHasLabels(hasLabels);
        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data.setHasLabelsOutside(hasLabelsOutside);
        data.setHasCenterCircle(hasCenterCircle);

        if (isExploded) {
            data.setSlicesSpacing(50);
        }

        pieChartView.setPieChartData(data);


    }

    public void populatePieChartTransport(View root) {
        PieChartData data;

        pieChartView = root.findViewById(R.id.pie_chart_transport);

        //OPTIONS
        boolean hasLabels = true;
        boolean hasLabelsOutside = false;
        boolean hasCenterCircle = true;
        boolean isExploded = false;
        boolean hasLabelForSelected = false;
        // OPTIONS END

        SliceValue tmpSlice;


        List<SliceValue> values = new ArrayList<SliceValue>();
        tmpSlice = new SliceValue(16.013f, Color.GRAY);
        tmpSlice.setLabel(("Foot " + tmpSlice.getValue() + "%" ));
        values.add(tmpSlice);

        tmpSlice = new SliceValue(13.315f, Color.RED);
        tmpSlice.setLabel(("Subway and Tram " + tmpSlice.getValue() + "%" ));
        values.add(tmpSlice);

        tmpSlice = new SliceValue(21.069f, Color.YELLOW);
        tmpSlice.setLabel(("Train " + tmpSlice.getValue() + "%" ));
        values.add(tmpSlice);

        tmpSlice = new SliceValue(1.154f, Color.MAGENTA);
        tmpSlice.setLabel(("Car " + tmpSlice.getValue() + "%" ));
        values.add(tmpSlice);

        tmpSlice = new SliceValue(48.449f, Color.GREEN);
        tmpSlice.setLabel(("Bus " + tmpSlice.getValue() + "%" ));
        values.add(tmpSlice);

        data = new PieChartData(values);
        data.setHasLabels(hasLabels);
        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data.setHasLabelsOutside(hasLabelsOutside);
        data.setHasCenterCircle(hasCenterCircle);

        if (isExploded) {
            data.setSlicesSpacing(50);
        }

        pieChartView.setPieChartData(data);


    }

    public void populateLineChart(View root, int obj_id , JSONArray object, int color) {
        lineChartView = root.findViewById(obj_id);

        List<PointValue> values = new ArrayList<>();

        PointValue tempPointValue;
        JSONObject tmpJsonObj;

        DecimalFormat df = new DecimalFormat("#.00");


        float max_y = Float.MIN_VALUE;
//        float min_y = Float.MAX_VALUE;
        float min_y = 0;

        float tmp_y_value;
        String tmp_y_date;

        AxisValue tempAxisValue;

        List<AxisValue> axisValuesForX = new ArrayList<>();
        List<AxisValue> axisValuesForY = new ArrayList<>();


        for (int i = 0; i < object.length(); i++) {
            try {
                tmpJsonObj = object.getJSONObject(i);
                Log.d("ScienceHack", "populateLineChart: " + tmpJsonObj);
                tmp_y_value = (float) tmpJsonObj.getDouble("co2_emission");
                tmp_y_date = tmpJsonObj.getString("Date");

                // Add values to line
                tempPointValue = new PointValue(i, tmp_y_value);
                values.add(tempPointValue);

                // Add labels
                tempAxisValue = new AxisValue(i);
                tempAxisValue.setLabel(tmp_y_date);
                axisValuesForX.add(tempAxisValue);

                // Calc min max
                max_y = Math.max(max_y, tmp_y_value);
//                min_y = Math.min(min_y, tmp_y_value);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        Line line = new Line(values)
                .setColor(color)
                .setCubic(true)
                .setHasPoints(true).setHasLabels(false);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);


//        for (float i = 0; i <= 360.0f; i += 30.0f){
//            tempAxisValue = new AxisValue(i);
//            tempAxisValue.setLabel(i+"\u00b0");
//            axisValuesForX.add(tempAxisValue);
//        }
//
        for (float i = min_y; i <= max_y; i += (max_y - min_y) / 10) {
            tempAxisValue = new AxisValue(i);
            Log.d("ASDFASDF", "populateLineChart: " + i);
            tempAxisValue.setLabel("" + df.format(i));
            axisValuesForY.add(tempAxisValue);
        }

        Axis xAxis = new Axis(axisValuesForX);
        Axis yAxis = new Axis(axisValuesForY);
        data.setAxisXBottom(xAxis);
        data.setAxisYLeft(yAxis);


        Viewport viewport = lineChartView.getMaximumViewport();
        viewport.set(0, 0, 0, 0);
        lineChartView.setMaximumViewport(viewport);

        viewport = lineChartView.getCurrentViewport();
        viewport.set(0, 0,0, 0);
        lineChartView.setCurrentViewport(viewport);


        lineChartView.setLineChartData(data);
    }

    public String loadJSONFromAsset(Context context, String file_path) {
        String json = null;
        try {

            InputStream is = context.getAssets().open(file_path);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}