package com.michal_stasinski.tabu.Menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.builders.Actions;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.michal_stasinski.tabu.Menu.Adapters.DataForDeliveryAdapter;
import com.michal_stasinski.tabu.Menu.Models.ShopingCardItem;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;
import com.michal_stasinski.tabu.Utils.CustomFont_Avenir_Medium;
import com.michal_stasinski.tabu.Utils.URLProcessing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

import static android.R.attr.path;
import static com.michal_stasinski.tabu.SplashScreen.DATA_FOR_DELIVERY;
import static com.michal_stasinski.tabu.SplashScreen.RESTAURANT_ADDRES;
import static com.michal_stasinski.tabu.SplashScreen.deliveryCostArray;
import static com.michal_stasinski.tabu.SplashScreen.dataDeliveryTextFieldName;

public class DataForDeliveryListView extends SwipeBackActivity {
    private DataForDeliveryAdapter adapter;
    private BounceListView listView;
    private Boolean isClick = false;
    public static int deliveryCost = 0;

    protected Integer[] imgid = {
            R.mipmap.ic_launcher,
            R.mipmap.person_icon,
            R.mipmap.person_icon,
            R.mipmap.email_icon,
            R.mipmap.tel_icon,
            R.mipmap.ic_launcher,
            R.mipmap.miasto_icon,
            R.mipmap.ulica_icon,
            R.mipmap.nr_domu_icon,
            R.mipmap.nr_mieszkania_icon,
            R.mipmap.pietro_icon,
            R.mipmap.firma_icon,
            R.mipmap.ic_launcher,
            R.mipmap.uwagi_icon,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data_for_delivery_list_view);
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);

        listView = (BounceListView) findViewById(R.id.data_delivery_listView);
        adapter = new DataForDeliveryAdapter(this, imgid);

        for (int i = 0; i < dataDeliveryTextFieldName.length; i++) {

            ShopingCardItem produkt = new ShopingCardItem();

            if (i == 0 || i == 5 || i == 12) {
                produkt.setType(0);
            } else {
                produkt.setType(1);
            }
            produkt.setTitle(dataDeliveryTextFieldName[i]);
            adapter.addItem(produkt);
        }

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        Button closeButton = (Button) findViewById(R.id.closeBtn);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = getSharedPreferences(DATA_FOR_DELIVERY, MODE_PRIVATE).edit();

                for (int i = 0; i <= 13; i++) {
                    ShopingCardItem item = (ShopingCardItem) adapter.getItem(i);
                    Log.i("informacja", " prefs.getString(dataDeliveryTextFieldName[i]23 " + dataDeliveryTextFieldName[i]);
                    editor.putString(dataDeliveryTextFieldName[i], item.getTitle().toString());
                }

                editor.putInt("deliveryCost", deliveryCost);
                editor.commit();
                finish();
                overridePendingTransition(R.anim.from_left, R.anim.to_right);
            }
        });

        SharedPreferences prefs = getSharedPreferences(DATA_FOR_DELIVERY, MODE_PRIVATE);


        for (int i = 0; i <= 13; i++) {
            Log.i("informacja", " prefs.getString(dataDeliveryTextFieldName[i] " + dataDeliveryTextFieldName[i]);
            String item = prefs.getString(dataDeliveryTextFieldName[i], null);
            if (item != null & i != 12) {
                ShopingCardItem el = (ShopingCardItem) adapter.getItem(i);
                el.setTitle(item);
                adapter.notifyDataSetChanged();
            }
        }


    }

   /* public LatLng getCoordinatesFromAddresse(Context context, String inputtedAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng resLatLng = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(inputtedAddress, 5);
            if (address == null) {
                return null;
            }

            if (address.size() == 0) {
                return null;
            }

            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            resLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
          //  Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        return resLatLng;
    }*/

    private Address getCoordinatesFromAddresse(String txt) {

        double latitude = 0;
        double longitude = 0;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String textToSearch = txt;
        List<Address> addresses = null;

        Log.i("informacja", " BREAK GEOCODERAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+txt  );
            try {
                addresses = geocoder.getFromLocationName(textToSearch, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }




      /*  final String url = "https://maps.googleapis.com/maps/api/geocode/json?address=Gdynia+Morska,+CA&key=AIzaSyBbP8QjhJBDHtmLF66Inkmeg5uV0PfYOYs";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        if(responseBody!=null){
                            Log.i("informacja", " Bcosss");


                            try {
                                JSONArray array = new JSONArray(URLProcessing.GetUrl(url));


                            }catch (JSONException e) {
                                Log.i("informacja", "lipa");
                                e.printStackTrace();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });*/



        if (addresses != null) {
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                return address;
            } else {
                return null;
            }
        }else{

            return null;
        }
    }

   /* public JSONObject getLocationInfo() {

        HttpGet httpGet = new HttpGet("http://maps.google.com/maps/api/geocode/json?latlng="+lat+","+lng+"&sensor=true");
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {

                String result = data.getStringExtra("edit_text");
                Integer pos = data.getIntExtra("pos", 1);
                ShopingCardItem item = (ShopingCardItem) adapter.getItem(pos);

                if (result.equals("")) {
                    item.setTitle(dataDeliveryTextFieldName[pos]);

                } else {
                    item.setTitle(result);
                }

                adapter.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Address address0 = (Address) getCoordinatesFromAddresse(RESTAURANT_ADDRES);

        ShopingCardItem item0 = (ShopingCardItem) adapter.getItem(6);
        ShopingCardItem item1 = (ShopingCardItem) adapter.getItem(7);
        ShopingCardItem item2 = (ShopingCardItem) adapter.getItem(8);

        String town = item0.getTitle();
        String street = item1.getTitle();
        String nr = item2.getTitle();

        Address address1;

        if (town.toUpperCase().equals("MIASTO")) {
            address1 = null;
        } else if (street.toUpperCase().equals("ULICA")) {
            address1 = null;
        } else if (nr.toUpperCase().equals("NR DOMU")) {
            address1 = (Address) getCoordinatesFromAddresse(town + " " + street);
        } else {
            address1 = (Address) getCoordinatesFromAddresse(town + " " + street + " " + nr);
        }

        CustomFont_Avenir_Medium text_cost_delivery = (CustomFont_Avenir_Medium) findViewById(R.id.data_cost_of_delivery_text);

        if (address1 != null) {

            Location locationA = new Location("point A");

            locationA.setLatitude(address0.getLatitude());
            locationA.setLongitude(address0.getLongitude());

            Location locationB = new Location("point B");

            locationB.setLatitude(address1.getLatitude());
            locationB.setLongitude(address1.getLongitude());

            float distance = locationA.distanceTo(locationB);


            if (((ShopingCardItem) adapter.getItem(6)).getTitle().equals(address1.getLocality()) && ((ShopingCardItem) adapter.getItem(7)).getTitle().equals(address1.getThoroughfare())) {

                if (distance > Integer.parseInt(String.valueOf(deliveryCostArray.get(2).getDistacne())) * 1000) {
                    text_cost_delivery.setText("Nie dowozimy pod wskazany adres");
                    deliveryCost = 0;

                } else if (distance > Integer.parseInt(String.valueOf(deliveryCostArray.get(1).getDistacne())) * 1000) {
                    text_cost_delivery.setText("Koszt dostawy " + deliveryCostArray.get(2).getPrice() + " zł");
                    deliveryCost = Integer.valueOf(deliveryCostArray.get(2).getPrice());

                } else if (distance > Integer.parseInt(String.valueOf(deliveryCostArray.get(0).getDistacne())) * 1000) {

                    text_cost_delivery.setText("Koszt dostawy " + deliveryCostArray.get(1).getPrice() + " zł");
                    deliveryCost = Integer.valueOf(deliveryCostArray.get(1).getPrice());
                } else if (distance <= Integer.parseInt(String.valueOf(deliveryCostArray.get(0).getDistacne())) * 1000) {

                    text_cost_delivery.setText("Koszt dostawy " + deliveryCostArray.get(0).getPrice() + " zł");
                    deliveryCost = Integer.valueOf(deliveryCostArray.get(0).getPrice());
                }
            } else {
                text_cost_delivery.setText("Podany adres nie istnieje");
                deliveryCost = 0;
            }


            ShopingCardItem townEdit = (ShopingCardItem) adapter.getItem(6);
            ShopingCardItem streetEdit = (ShopingCardItem) adapter.getItem(7);
            ShopingCardItem nrEdit = (ShopingCardItem) adapter.getItem(8);

            Log.i("informacja", "address1___________________  " + address1);
            Log.i("informacja", ((ShopingCardItem) adapter.getItem(6)).getTitle() + "address1___________________  " + address1.getLocality());
            Log.i("informacja", ((ShopingCardItem) adapter.getItem(7)).getTitle() + "address1___________________  " + address1.getThoroughfare());
            Log.i("informacja", ((ShopingCardItem) adapter.getItem(8)).getTitle() + "address1___________________  " + address1.getPostalCode());

            if (address1.getLocality() == null || address1.getThoroughfare() == null || address1.getPostalCode() == null) {

                if (((ShopingCardItem) adapter.getItem(6)).getTitle().equals(address1.getLocality()) && ((ShopingCardItem) adapter.getItem(7)).getTitle().equals(address1.getThoroughfare())) {

                } else {
                    text_cost_delivery.setText("Podany adres nie istnieje");
                    deliveryCost = 0;
                }
            }

            adapter.notifyDataSetChanged();
        }
        if (address1 == null) {
            text_cost_delivery.setText("Brak adresu dostawy");
        }
        if (((ShopingCardItem) adapter.getItem(6)).getTitle().equals("") || ((ShopingCardItem) adapter.getItem(7)).getTitle().equals("")) {
            text_cost_delivery.setText("Brak adresu dostawy");
        }
        isClick = false;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterek, View view, int position, long id) {
                if (isClick == false) {

                    Object listItem = listView.getItemAtPosition(position);


                    if (position == 0 || position == 5 || position == 12) {
                    } else {
                        isClick = true;
                        // view.setOnClickListener(null);

                        Intent intent = new Intent(view.getContext(), EditTextPopUp.class);
                        intent.putExtra("title", dataDeliveryTextFieldName[position]);
                        intent.putExtra("position", position);
                        ShopingCardItem item = (ShopingCardItem) adapter.getItem(position);

                        if (item.getTitle().toUpperCase().equals(dataDeliveryTextFieldName[position].toUpperCase())) {
                            intent.putExtra("actualText", "");
                        } else {
                            intent.putExtra("actualText", item.getTitle());
                        }

                        intent.setClass(view.getContext(), EditTextPopUp.class);
                        startActivityForResult(intent, 1);
                    }
                }
            }
        });
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        return Actions.newView("DataForDeliveryListView", "http://[ENTER-YOUR-URL-HERE]");
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        FirebaseUserActions.getInstance().start(getIndexApiAction());
    }

    @Override
    public void onStop() {

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        FirebaseUserActions.getInstance().end(getIndexApiAction());
        super.onStop();
    }
}
