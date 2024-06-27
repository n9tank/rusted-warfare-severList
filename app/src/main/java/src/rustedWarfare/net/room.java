package rustedWarfare.net;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rustedWarfare.net.severList;
import rustedWarfare.severList.Main;

public class room implements Comparable<room> {
 public String showName;
 public int compareTo(room o) {
  return getShow().compareTo(o.getShow());
 }
 public int hashCode() {
  return getShow().hashCode();
 }
 public boolean equals(Object o) {
  return compareTo((room)o) == 0;
 }
 public String getShow() {
  String str=showName;
  if (str == null)showName = str = severName.concat(map.replaceFirst("\\([^)]+",""));
  return str;
 }
 public String sever;
//public int ver;
 public String severName;
 public String severVer;
 public String mods;
 public String map;
 public int map_type;
 public String uuid;
 public String ip;
 public String nat_ip;
 public int players;
 public int md5;
 //public boolean ingame;
 public int maxPlayes;
 public String public_ip;
 private String toString;
 public String toString() {
  String str=toString;
  if (str == null){
  StringBuilder buff=new StringBuilder(severName);
  buff.append(severVer).append('(').append(players).append('/').append(maxPlayes).append(')').append(map);
 if(mods.length()>0)buff.append(';').append(mods);
 toString=str=buff.toString();   
  }
  return str;
 }
 public String getData() {
  StringBuilder out=new StringBuilder("action=get&game_id=");
  out.append(uuid);
  out.append("&c=");
  out.append(getMd5());
  out.append("&p_hash=");
  return out.toString();
 }
 public String readIo(Reader in) throws IOException {
  BufferedReader buff=new BufferedReader(in);
  try {
   buff.readLine();
   buff.readLine();
   buff.readLine();
   buff.readLine();
   return public_ip = severList.getIp(buff.readLine().split(","));
  } finally {
   buff.close();
  }
 }
 public String getRealIp() {
  String ip=public_ip;
  if (ip != null)return ip;
   Request request = severList.setHeader(new Request.Builder().url(severList.hostCahce))
   .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),getData())).build();
   try (Response response = severList.okhttp.newCall(request).execute()) {
  if(response.code()==200)return readIo(response.body().charStream());
   }catch(Exception e){
   }
  return null;
 }/*
 public String getRealIp() {
  String ip=public_ip;
  if (ip != null)return ip;
  try {
   HttpURLConnection ur = (HttpURLConnection)new URL(severList.hostCahce).openConnection();
   try {
    ur.setRequestMethod("POST");
    ur.setUseCaches(true);
    ur.setRequestProperty("Language", severList.lang);
    ur.setRequestProperty("Accept-Encoding", "gzip");
    ur.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    ur.setRequestProperty("User-Agent", "rw " + severList.sys + " " + severList.ver + " " + severList.lang);
    //ur.setRequestProperty("Connection","Keep-Alive");
    ur.setConnectTimeout(3000);
    ur.setReadTimeout(3000);
    String data=getData();
    ur.setRequestProperty("Content-Length", String.valueOf(data.length()));
    OutputStream ioout=ur.getOutputStream();
    try {
     ioout.write(data.getBytes());
    } finally {
     ioout.close();
    }
    if (ur.getResponseCode() == 200) {
     InputStream in=ur.getInputStream();
     String type=ur.getContentEncoding();
     if ("gzip".equals(type))in = new GZIPInputStream(in);
     return readIo(new InputStreamReader(in));
    }
   } finally {
    ur.disconnect();
   }
  } catch (Exception e) {
  }
  return null;
 }*/
 public static String md5(String str, int len) {
  try {
   byte arr[]=MessageDigest.getInstance("MD5").digest(str.getBytes("utf-8"));
   StringBuilder stringBuilder = new StringBuilder(arr.length * 2);
   int j = Math.min(arr.length, len >> 1);
   for (int i = 0; i < j; i++) {
    int k = arr[i] & 0xFF;
    if (k < 16)stringBuilder.append('0'); 
    stringBuilder.append(Integer.toHexString(k));
   } 
   return stringBuilder.toString();
  } catch (Exception e) {}
  return null;
 }
 public String getMd5() {
  int paramInt=md5;
  if (paramInt < 100000)
   return md5("x".concat(String.valueOf(paramInt)), 10); 
  if (paramInt < 200000)
   return md5("y".concat(String.valueOf(paramInt)), 11); 
  return md5("z".concat(String.valueOf(paramInt)), 12); 
 }
}
