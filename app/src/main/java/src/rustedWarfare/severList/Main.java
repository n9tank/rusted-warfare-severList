package rustedWarfare.severList;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Switch;
/*import com.google.android.gms.net.CronetProviderInstaller;
import com.google.net.cronet.okhttptransport.CronetInterceptor;*/
import java.util.ArrayList;
/*import okhttp3.Call;
import okhttp3.OkHttpClient;*/
//import org.chromium.net.CronetEngine;
import rustedWarfare.net.room;
import rustedWarfare.net.severList;
public class Main extends Activity implements OnItemClickListener, Thread.UncaughtExceptionHandler {
 LinearLayout setting;
 Switch sys;
 Switch beta;
 EditText ver;
 EditText lang;
 EditText antiAd;
 EditText packed;
 Object[] data;
 String search[]=new String[0];
 ArrayAdapter arr;
 String pack;
 public static String packname; 
 //public static Call.Factory http3;
 public void finish() {
  moveTaskToBack(true);
 }
  public static void e(Object cla, Throwable e) {
  Log.e(packname, cla.toString(), e);
 }
 public void uncaughtException(Thread thread, Throwable ex) {
  e(thread, ex);
 }
 protected void onCreate(Bundle savedInstanceState) {
  packname=getPackageName();
  Thread.setDefaultUncaughtExceptionHandler(this);
 /* CronetProviderInstaller.installProvider(this);
  CronetEngine engine = new CronetEngine.Builder(this).build();
  http3 = new OkHttpClient.Builder().addInterceptor(CronetInterceptor.newBuilder(engine).build()).build(); */
  StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());    
  StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());  
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  ListView list=findViewById(R.id.list);
  ArrayAdapter ar=new ArrayAdapter(this, android.R.layout.test_list_item, new ArrayList());
  arr = ar;
  list.setAdapter(ar);
  list.setOnItemClickListener(this);
  setting = findViewById(R.id.setting);
  sys = findViewById(R.id.sys);
  beta = findViewById(R.id.beta);
  ver = findViewById(R.id.ver);
  lang = findViewById(R.id.lang);
  antiAd = findViewById(R.id.antiAd);
  packed = findViewById(R.id.pack);
  ((SearchView)findViewById(R.id.search)).setOnQueryTextListener(new OnQueryTextListener(){
    public boolean onQueryTextChange(String newText) {
     search = newText.toLowerCase().split(" ");
     show();
     return false;
    }
    public boolean onQueryTextSubmit(String query) {
     search = query.toLowerCase().split(" ");
     show();
     return false;
    }
   });
  updata();
  data = severList.getList();
  show();
 }
 public void updata() {
  SharedPreferences sh=getSharedPreferences("data", MODE_PRIVATE);
  boolean type=sh.getBoolean("sys", false);
  String str=type ?"pc": "android";
  severList.sys = str;
  sys.setChecked(type);
  type = sh.getBoolean("beta", false);
  severList.beta = type;
  beta.setChecked(type);
  int v=sh.getInt("ver", 176);
  severList.ver = 176;
  ver.setText(String.valueOf(v));
  String la=sh.getString("lang", "zh");
  severList.lang = la;
  lang.setText(la);
  la = sh.getString("antiAd", "");
  if (la.length() > 0)severList.antiAd = la.split(",");
  pack = sh.getString("pack", "com.corrodinggames.rts");
  packed.setText(pack);
 }
 public void show() {
  ArrayAdapter ar=arr;
  ar.clear();
  tag:
  for (Object obj:data) {
   room room=(room)obj;
   String name=room.toString().toLowerCase();
   String arr[]=search;
   if (arr.length > 0) {
    boolean is=false;
    for (String find:arr)
     if (is = !name.contains(find))break;
    if (is)continue tag;
   }
   arr = severList.antiAd;
   for (String anti:arr)
    if (name.contains(anti))
     continue tag;
   ar.add(room);
  }
 }
 public void r(View v) {
  data = severList.getList();
  show();
 }
 public void s(View v) {
  boolean flag;
  if (flag = setting.getVisibility() == 0) {
   SharedPreferences.Editor sh=getSharedPreferences("data", MODE_PRIVATE).edit();
   sh.putBoolean("sys", sys.isChecked());
   sh.putBoolean("beta", beta.isChecked());
   String str=ver.getEditableText().toString();
   if (str.length() > 0)sh.putInt("ver", Integer.parseInt(str));
   str = lang.getEditableText().toString();
   if (str.length() > 0)sh.putString("lang", str);
   str = antiAd.getEditableText().toString().toLowerCase();
   sh.putString("antiAd", str);
   str = packed.getEditableText().toString().toLowerCase();
   if (str.length() > 0)sh.putString("pac", str);
   sh.apply();
   updata();
   show();
  }
  setting.setVisibility(flag ?8: 0);
 }
 public void onItemClick(AdapterView parent, View view, int position, long id) {
  String data=((room)arr.getItem(position)).getRealIp();
  if (data != null) {
   ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
   ClipData mClipData = ClipData.newPlainText("Label", data);
   cm.setPrimaryClip(mClipData);
   Intent intent = getPackageManager().getLaunchIntentForPackage(pack);
   if (intent != null) {
    intent.putExtra("type", "110");
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
   }
  }
 }
}
