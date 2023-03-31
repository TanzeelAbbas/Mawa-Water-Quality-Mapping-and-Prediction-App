package com.official.mawa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.official.mawa.ml.MawaWaterQualityPredictionModel;
import com.official.mawa.ml.UpdateWaterQualityPredictionModel;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    TextView address, day, degree, results;
    EditText conductivity, pH, solids, hardness, turbidity,chloramine, sulfate,organic_carbon, trihalomethane;
    float pH1, hardness2, solids3,chloramine4, sulfate5,conductivity6, organic_carbon7, trihalomethane8, turbidity9;
    CardView sanitizer, water, boil, calculate;
    ImageView water1, water2, water3, menu;
    String Day,date;
    int pH2;
    float longitude,latitude;
    int quality, min = 0, max = 100;
    Calendar calendar;
    LocalDateTime now;
    DateTimeFormatter dtf;
    private  final  static int REQUEST_CODE=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utils.blackiconStatus(MainActivity.this,R.color.white);


        //EditText
        pH = findViewById(R.id.pH);
        hardness = findViewById(R.id.Hardness);
        solids = findViewById(R.id.Solids);
        chloramine = findViewById(R.id.Chloramines);
        sulfate = findViewById(R.id.Sulfate);
        conductivity = findViewById(R.id.Conductivity);
        organic_carbon = findViewById(R.id.organic_carbon);
        trihalomethane = findViewById(R.id.Trihalomethanes);
        turbidity = findViewById(R.id.Turbidity);




        //Buttons
        sanitizer = findViewById(R.id.sanitizer);
        water = findViewById(R.id.botel);
        boil = findViewById(R.id.boil);
        calculate = findViewById(R.id.calculate);
        menu = findViewById(R.id.menu);



        //Results
        results = findViewById(R.id.results);
        address = findViewById(R.id.location);
        day = findViewById(R.id.day);
        degree = findViewById(R.id.degree);
        water1 = findViewById(R.id.water1);
        water2 = findViewById(R.id.water2);
        water3 = findViewById(R.id.water3);





        //Figure-out Day
        calendar = Calendar.getInstance();
        Day = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
            dtf = DateTimeFormatter.ofPattern("dd");
            date = dtf.format(now);
        }


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        day.setText(Day);

        //Buttons
        sanitizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, description, token;;
                token = "1";
                name = "Use Sanitization";
                description = "Hand sanitization is a crucial practice to maintain good hand hygiene and prevent the spread of germs and diseases. However, in some areas, the quality of water may not be good enough to use for handwashing, making hand sanitization an even more important method to keep hands clean. In such cases, using an alcohol-based hand sanitizer can effectively kill germs and bacteria on the hands without the need for water.\n" +
                        "\n" +
                        "It is important to note that hand sanitizers are most effective when used correctly. To properly sanitize your hands, apply a coin-sized amount of sanitizer to the palm of one hand and rub it all over the surfaces of both hands until they are completely dry. This should take around 20 seconds. It is important to ensure that you cover all surfaces of your hands, including the backs, between the fingers, and under the nails.\n" +
                        "\n" +
                        "In addition, it is important to choose a hand sanitizer that contains at least 60% alcohol to ensure maximum effectiveness. When using hand sanitizers, it is also important to avoid touching your face or other surfaces before the sanitizer has completely dried on your hands, as this can reduce its effectiveness.\n" +
                        "\n" +
                        "Overall, hand sanitization can be a highly effective way to maintain good hand hygiene, even when the quality of water is not suitable for handwashing. By following proper hand sanitization techniques and using an alcohol-based hand sanitizer with at least 60% alcohol content, individuals can effectively kill germs and reduce the spread of diseases.";
                Intent suggestions = new Intent(MainActivity.this, com.official.mawa.suggestions.class);
                suggestions.putExtra("name", name);
                suggestions.putExtra("description", description);
                suggestions.putExtra("token", token);
                startActivity(suggestions);
            }
        });

        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, description, token;;
                token = "2";
                name = "Use Filtered Water";
                description = "In areas where the quality of water is not good, it is essential to take precautions to ensure that you have access to safe drinking water. One way to do this is by carrying a filtered water bottle with you, such as Nestle or other similar brands. These filtered water bottles are designed to remove impurities and contaminants from water, making it safe to drink.\n" +
                        "\n" +
                        "By carrying a filtered water bottle with you, you can ensure that you have access to clean drinking water at all times, even when you are in areas where the water quality is questionable. This can be especially important when traveling or engaging in outdoor activities where access to clean drinking water may be limited.\n" +
                        "\n" +
                        "When choosing a filtered water bottle, it is important to select one that is designed for the specific contaminants that are present in the water in the area where you will be traveling. Some filtered water bottles are designed to remove only certain types of contaminants, such as bacteria or heavy metals, while others are designed to remove a wider range of impurities.\n" +
                        "\n" +
                        "Overall, carrying a filtered water bottle with you is an excellent way to ensure that you have access to safe drinking water, even when the water quality in the area is not good. By taking this simple precaution, you can protect your health and avoid the negative effects of drinking contaminated water.";
                Intent suggestions = new Intent(MainActivity.this, com.official.mawa.suggestions.class);
                suggestions.putExtra("name", name);
                suggestions.putExtra("description", description);
                suggestions.putExtra("token", token);
                startActivity(suggestions);
            }
        });

        boil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, description, token;;
                token = "3";
                name = "Boil The Water";
                description = "Boiling water is one of the most effective ways to ensure that it is safe for consumption, particularly in areas where water quality may be questionable. Boiling water can effectively kill harmful bacteria, viruses, and other pathogens that may be present in the water, making it safe to drink and use for other purposes.\n" +
                        "\n" +
                        "To properly boil water, bring it to a rolling boil and allow it to continue boiling for at least one minute. This will ensure that all harmful pathogens have been destroyed. It is important to note that boiling water does not remove other contaminants, such as chemicals or heavy metals, that may be present in the water.\n" +
                        "\n" +
                        "Boiled water can also be used for other purposes, such as cooking and cleaning, to ensure that all surfaces and food items are free from harmful bacteria and other pathogens.\n" +
                        "\n" +
                        "In areas where the quality of water is not good, boiling water before use can be an effective and simple solution to ensure that the water is safe for consumption and use. However, it is important to note that boiling water requires a source of heat and may not always be feasible or convenient, particularly in emergency situations. Therefore, it is important to have other backup plans and alternative methods for water purification in case boiling is not an option.";
                Intent suggestions = new Intent(MainActivity.this, com.official.mawa.suggestions.class);
                suggestions.putExtra("name", name);
                suggestions.putExtra("description", description);
                suggestions.putExtra("token", token);
                startActivity(suggestions);
            }
        });

        getLastLocation();


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){


                    fusedLocationProviderClient.getLastLocation()
                            .addOnSuccessListener(new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    if (location !=null){
                                        Geocoder geocoder=new Geocoder(MainActivity.this, Locale.getDefault());
                                        List<Address> addresses= null;
                                        try {
                                            addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                            address.setText(addresses.get(0).getSubLocality() + ", "+ addresses.get(0).getLocality());
                                            longitude = (float) addresses.get(0).getLongitude();
                                            latitude = (float) addresses.get(0).getLatitude();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }
                            });

                }else
                {
                    askPermission();
                }

                // Define a ByteBuffer object and fill it with data
                float[] data = new float[]{longitude, latitude}; // replace 'longitude' and 'latitude' with actual values
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(data.length * 4); // allocate a byte buffer of the appropriate size
                byteBuffer.order(ByteOrder.nativeOrder()); // set the byte order to native
                FloatBuffer floatBuffer = byteBuffer.asFloatBuffer(); // get a float buffer view of the byte buffer
                floatBuffer.put(data); // put the data into the float buffer
                byteBuffer.rewind(); // rewind the byte buffer

                try {
                    MawaWaterQualityPredictionModel model = MawaWaterQualityPredictionModel.newInstance(MainActivity.this);

                    // Creates inputs for reference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 2}, DataType.FLOAT32);
                    inputFeature0.loadBuffer(byteBuffer);

                    // Set the longitude and latitude values
                    float[] inputData = inputFeature0.getFloatArray();
                    inputData[0] = (float) longitude;
                    inputData[1] = (float) latitude;
                    System.out.println("The Inputs are : " + inputFeature0);

                    // Runs model inference and gets result.
                    MawaWaterQualityPredictionModel.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
                    float output = outputFeature0.getFloatArray()[0];
                    System.out.println("This is Output: " + output);
                    Log.d("Model Output", "Water quality prediction: " + output);
                    if(output==1.0){
                        degree.setText("Healthy");
                        water1.setVisibility(View.VISIBLE);
                        water3.setVisibility(View.INVISIBLE);
                    } else{
                        degree.setText("UnHealthy");
                        water1.setVisibility(View.INVISIBLE);
                        water3.setVisibility(View.VISIBLE);
                    }

                    // Releases model resources if no longer used.
                    model.close();
                } catch (IOException e) {
                    // TODO Handle the exception
                }

            }
        });

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){


            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location !=null){
                                Geocoder geocoder=new Geocoder(MainActivity.this, Locale.getDefault());
                                List<Address> addresses= null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                    address.setText(addresses.get(0).getSubLocality() + ", "+ addresses.get(0).getLocality());
                                    longitude = (float) addresses.get(0).getLongitude();
                                    latitude = (float) addresses.get(0).getLatitude();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    });

        }else
        {
            askPermission();
        }

        // Define a ByteBuffer object and fill it with data
        float[] data = new float[]{longitude, latitude}; // replace 'longitude' and 'latitude' with actual values
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(data.length * 4); // allocate a byte buffer of the appropriate size
        byteBuffer.order(ByteOrder.nativeOrder()); // set the byte order to native
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer(); // get a float buffer view of the byte buffer
        floatBuffer.put(data); // put the data into the float buffer
        byteBuffer.rewind(); // rewind the byte buffer

        try {
            MawaWaterQualityPredictionModel model = MawaWaterQualityPredictionModel.newInstance(MainActivity.this);

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 2}, DataType.FLOAT32);
            inputFeature0.loadBuffer(byteBuffer);

            // Set the longitude and latitude values
            float[] inputData = inputFeature0.getFloatArray();
            inputData[0] = (float) longitude;
            inputData[1] = (float) latitude;
            System.out.println("The Inputs are : " + inputFeature0);

            // Runs model inference and gets result.
            MawaWaterQualityPredictionModel.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
            float output = outputFeature0.getFloatArray()[0];
            System.out.println("This is Output: " + output);
            Log.d("Model Output", "Water quality prediction: " + output);
            if(output>=0.5){
                degree.setText("Healthy");
                water1.setVisibility(View.VISIBLE);
                water3.setVisibility(View.INVISIBLE);
            } else{
                degree.setText("UnHealthy");
                water1.setVisibility(View.INVISIBLE);
                water3.setVisibility(View.VISIBLE);
            }

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Floats
                try {
                    pH1 = Float.parseFloat(pH.getText().toString());

                } catch (NumberFormatException e) {
                    // handle the exception
                    e.printStackTrace();
                    pH1 = 0.0f;
                }


                try {
                    hardness2 = Float.parseFloat(hardness.getText().toString());
                } catch (NumberFormatException e) {
                    // handle the exception
                    e.printStackTrace();
                    hardness2 = 0.0f;
                }

                try {
                    solids3 = Float.parseFloat(solids.getText().toString());
                    // use pH1
                } catch (NumberFormatException e) {
                    // handle the exception
                    e.printStackTrace();
                    solids3 = 0.0f;
                }

                try {
                    chloramine4 = Float.parseFloat(chloramine.getText().toString());
                    // use pH1
                } catch (NumberFormatException e) {
                    // handle the exception
                    e.printStackTrace();
                    chloramine4 = 0.0f;
                }

                try {
                    sulfate5 = Float.parseFloat(sulfate.getText().toString());
                    // use pH1
                } catch (NumberFormatException e) {
                    // handle the exception
                    e.printStackTrace();
                    sulfate5 = 0.0f;
                }

                try {
                    conductivity6 = Float.parseFloat(conductivity.getText().toString());
                    // use pH1
                } catch (NumberFormatException e) {
                    // handle the exception
                    e.printStackTrace();
                    conductivity6 = 0.0f;
                }

                try {
                    organic_carbon7 = Float.parseFloat(organic_carbon.getText().toString());
                    // use pH1
                } catch (NumberFormatException e) {
                    // handle the exception
                    e.printStackTrace();
                    organic_carbon7 = 0.0f;
                }

                try {
                    trihalomethane8 = Float.parseFloat(trihalomethane.getText().toString());
                    // use pH1
                } catch (NumberFormatException e) {
                    // handle the exception
                    e.printStackTrace();
                    trihalomethane8 = 0.0f;
                }

                try {
                    turbidity9 = Float.parseFloat(turbidity.getText().toString());
                    // use pH1
                } catch (NumberFormatException e) {
                    // handle the exception
                    e.printStackTrace();
                    turbidity9 = 0.0f;
                }



                if (pH1 == 0.0 && hardness2 == 0.0 && solids3 == 0.0 && chloramine4 == 0.0 && sulfate5 == 0.0 && conductivity6 == 0.0 && organic_carbon7 == 0.0 && trihalomethane8 == 0.0 && turbidity9 == 0.0){
                    Toast.makeText(MainActivity.this, "Requires all fields!", Toast.LENGTH_SHORT).show();
                } else {
                    // Define a ByteBuffer object and fill it with data
                    float[] data = new float[]{pH1,hardness2,solids3,chloramine4,sulfate5,conductivity6,organic_carbon7,trihalomethane8, turbidity9}; // replace 'longitude' and 'latitude' with actual values
                    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(data.length * 4); // allocate a byte buffer of the appropriate size
                    byteBuffer.order(ByteOrder.nativeOrder()); // set the byte order to native
                    FloatBuffer floatBuffer = byteBuffer.asFloatBuffer(); // get a float buffer view of the byte buffer
                    floatBuffer.put(data); // put the data into the float buffer
                    byteBuffer.rewind(); // rewind the byte buffer

                    try {
                        UpdateWaterQualityPredictionModel model = UpdateWaterQualityPredictionModel.newInstance(MainActivity.this);

                        // Creates inputs for reference.
                        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 9}, DataType.FLOAT32);
                        inputFeature0.loadBuffer(byteBuffer);

                        float[] inputData = inputFeature0.getFloatArray();
                        inputData[0] = (float) pH1;
                        inputData[1] = (float) hardness2;
                        inputData[2] = (float) solids3;
                        inputData[3] = (float) chloramine4;
                        inputData[4] = (float) sulfate5;
                        inputData[5] = (float) conductivity6;
                        inputData[6] = (float) organic_carbon7;
                        inputData[7] = (float) trihalomethane8;
                        inputData[8] = (float) turbidity9;



                        // Runs model inference and gets result.
                        UpdateWaterQualityPredictionModel.Outputs outputs = model.process(inputFeature0);
                        TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                        float output = outputFeature0.getFloatArray()[0];
                        Log.d("Model Output", "Water quality prediction 2: " + output);

                        if(output>=0.5){
                            results.setText("Healthy");
                        } else{
                            results.setText("UnHealthy");
                        }

                        //results.setText(pH2);


                        // Releases model resources if no longer used.
                        model.close();
                    } catch (IOException e) {
                        // TODO Handle the exception
                    }
                }

            }
        });



    }

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){


            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location !=null){
                                Geocoder geocoder=new Geocoder(MainActivity.this, Locale.getDefault());
                                List<Address> addresses= null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                    address.setText(addresses.get(0).getSubLocality() + ", "+ addresses.get(0).getLocality());
                                    longitude = (float) addresses.get(0).getLongitude();
                                    latitude = (float) addresses.get(0).getLatitude();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    });

        }else
        {
            askPermission();
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode==REQUEST_CODE){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }
            else {
                Toast.makeText(this, "Required Permission", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void fading(View view){

    }

}