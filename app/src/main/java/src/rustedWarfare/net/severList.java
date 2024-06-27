package rustedWarfare.net;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rustedWarfare.severList.Main;
public class severList {
 public static int ver;//=176;
 public static String lang;//="zh";
 public static boolean beta;//=false;
 public static String sys;//="android";//pc
 public static String host[]=new String[]{"https://gs1.corrodinggames.com/masterserver/1.4/interface","https://gs4.corrodinggames.net/masterserver/1.4/interface"};
 public static String hostCahce;
 public static String antiAd[]=new String[0];
 public static OkHttpClient okhttp=new OkHttpClient().newBuilder().callTimeout(3,TimeUnit.SECONDS).build();
 public static String getIp(String list[]) {
  String pulic_ip=list[3];
  return list[5].equals("5123") ?pulic_ip: pulic_ip + ':' + list[5];
 }
 public static room pareRoom(String list[]) {
 room room= new room();
  String uid=list[0];
  room.uuid = uid;
  room.sever = list[1].trim();
  room.nat_ip = getIp(list);
  room.ip = list[4];
  room.severName = list[7];
// room.passage=list[8].equals("true");
  String map=list[9];
  room.map = map.startsWith("MOD|")?map:map.substring(map.lastIndexOf('/')+1);
  String map_type=list[10];
  room.map_type = map_type.equals("skirmishMap") ?0: (map_type.equals("customMap") ?1: 2);
  //room.ingame = list[11].equals("ingame");
  room.severVer = list[12].replaceFirst("[\\d.]+","");
  room.players = Integer.parseInt(list[15]);
  room.maxPlayes = Integer.parseInt(list[16]);
  room.mods = list[20];
  room.md5 = Integer.parseInt(list[21]);
  return room;
 }
 public static Object[] readIo(Reader read) throws Exception {
  HashSet reslt=new HashSet();
  BufferedReader buff= new BufferedReader(read);
  try {
   buff.readLine();
   buff.readLine();
   buff.readLine();
   String str;
   tag:
   while ((str = buff.readLine()) != null) {
    String[] list=str.split(",");
    if (Integer.parseInt(list[2]) == ver && list[6].equals("true") && !list[8].equals("true") && !list[11].equals("ingame")) {
     room room=pareRoom(list);
     reslt.add(room);
    }
   }
  } finally {
   buff.close();
  }
  Object[] arr=reslt.toArray();
  Arrays.sort(arr);
  return arr;
 }
  public static Request.Builder setHeader(Request.Builder request) {
  return request.addHeader("User-Agent",new StringBuilder("rw ").append(severList.sys).append(" ").append(severList.ver).append(" ").append(severList.lang).toString()).addHeader("Language",lang);
  }
 public static Object[] getList() {
  for (String src:host) {
  hostCahce=src;
   Request request = setHeader(new Request.Builder().url(new StringBuilder(src).append("?action=list&game_version=").append(ver).append("&game_version_beta=").append(beta).toString())).get().build();
   try (Response response = okhttp.newCall(request).execute()) {
    if(response.code()==200)return readIo(response.body().charStream());
   }catch(Exception e){
   }
  }
  return new Object[0];
 }/*
 public static Object[] getList() {
  try {
   for (String src:host) {
    HttpURLConnection ur= (HttpURLConnection)new URL(src + "?action=list&game_version=" + ver + "&game_version_beta=" + beta).openConnection();
    try {
     hostCahce = src;
     ur.setRequestMethod("GET");
     ur.setUseCaches(true);
     ur.setRequestProperty("Language", lang);
     ur.setRequestProperty("Accept-Encoding", "gzip");
     ur.setRequestProperty("User-Agent", "rw " + sys + " " + ver + " " + lang);
     //ur.setRequestProperty("Connection","Keep-Alive");
     ur.setConnectTimeout(3000);
     ur.setReadTimeout(3000);
     if (ur.getResponseCode() == 200) {
      InputStream in=ur.getInputStream();
      String type=ur.getContentEncoding();
      if ("gzip".equals(type))in = new GZIPInputStream(in);
      return readIo(new InputStreamReader(in));
     }
    } finally {
     ur.disconnect();
    }
   }
  } catch (Exception e) {
  }
  return new Object[0];
 }*/
}
