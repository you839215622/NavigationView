package com.csy.navigationview.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.csy.floatview.FloatViewDemoActivity;
import com.csy.navigationview.R;

import org.apache.http.conn.util.InetAddressUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static String TAG = "111";
    private DrawerLayout mDrawerLayout;
        private NavigationView mNavigationView;
        private TextView tvIP,tvMAC;

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_nav_main);

            mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
            mNavigationView = (NavigationView) findViewById(R.id.id_nv_menu);
            tvIP = (TextView) findViewById(R.id.id_tv_content);
            tvMAC= (TextView) findViewById(R.id.id_tv_mac);
//            //获取外网IP地址
//            String strForeignIP = "null";
//            try {
//                String getIpUrl = "http://www.cz88.net/ip/index.aspx";
//                URL url = new URL(getIpUrl);
//                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
//                String s = "";
//                StringBuffer sb = new StringBuffer("");
//                while ((s = br.readLine()) != null) {
//                    sb.append(s + "\r\n");
//                }
//                br.close();
//                String webContent = "";
//                webContent = sb.toString();
//                String flagofForeignIPString = "IPMessage";
//                int startIP = webContent.indexOf(flagofForeignIPString)+flagofForeignIPString.length() +2;
//                int endIP = webContent.indexOf("</span>",startIP);
//            } catch (Exception e){
//                e.printStackTrace();
//
//            }

            //获取当前本地的IP地址
            try {
                String ipv4 = null;
                List<NetworkInterface> nilist = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface ni :nilist) {
                    List<InetAddress> ialist = Collections.list(ni.getInetAddresses());
                    for (InetAddress address :ialist) {
                        ipv4 = address.getHostAddress();
                        if(!address.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ipv4)){
                             tvIP.setText(ipv4);
                             TAG = ipv4;
                        }
                    }
                }
            } catch (SocketException ex){
                tvIP.setText("0.0.0.0");
            }
            //获取当前机器的mac地址
            String mac_s = "00:00:0 0:00:00:00";
            try {
                byte[] mac;
                String ip = TAG;
                if (!InetAddressUtils.isIPv4Address(ip)) {
                    tvMAC.setText(mac_s);
                }
                InetAddress ipAddress = InetAddress.getByName(ip);
                if (ipAddress == null) {
                    tvMAC.setText(mac_s);
                }
                NetworkInterface ne = NetworkInterface.getByInetAddress(ipAddress);
                mac = ne.getHardwareAddress();
                if (mac.length > 0) {
                    mac_s = byte2mac(mac);
                    tvMAC.setText(mac_s);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                tvMAC.setText(mac_s);
            }



            Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
            setSupportActionBar(toolbar);

            final ActionBar ab = getSupportActionBar();
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);

            setupDrawerContent(mNavigationView);


        }

    private String byte2mac(byte[] b) {
        StringBuffer hs = new StringBuffer(b.length);
        String stmp = "";
        int len = b.length;
        for (int n = 0; n < len; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1) {
                hs = hs.append("0").append(stmp);
            } else {
                hs = hs.append(stmp);
            }
        }
        StringBuffer str = new StringBuffer(hs);
        for (int i = 0; i < str.length(); i++) {
            if (i % 3 == 0) {
                str.insert(i, ':');
            }
        }
        return str.toString().substring(1);
    }


    private void setupDrawerContent(NavigationView navigationView)
    {
        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener()
                {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem)
                    {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main__nav_menu_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == android.R.id.home)
        {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true ;
        }

        if(item.getItemId() == android.R.id.message)
        {
            startActivity(new Intent(MainActivity.this, FloatViewDemoActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}

