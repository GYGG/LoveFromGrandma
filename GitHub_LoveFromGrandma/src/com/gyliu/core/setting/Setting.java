package com.gyliu.core.setting;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
 
/**
 * 
 * @ThreadSafe 
 * @GuardedBy "this"
 */
public class Setting {
	
    private static final String TAG = Setting.class.getSimpleName();
    
    private static final String PACKAGE_NAME = "com.github.kindleliu.lovefromgrandma";
    
    public static final String SETTING = PACKAGE_NAME + "SETTING";
    
    public static final String PlATFORM = PACKAGE_NAME + "android";
    
    public static final String USER_NAME = PACKAGE_NAME + "111113";
    
    public static final String PASSWORD1 = PACKAGE_NAME + "111114";
    
    public static final String HAS_BASEBOARD = PACKAGE_NAME + "111115";
    

    
    private SharedPreferences mSharedPref = null;
    
    private SharedPreferences.Editor mEditor = null; 
    
    private Context mContext;   
    
    private static Setting mInstance = null;
    
    synchronized public static Setting getInstance() {
        if (mInstance == null) {
            mInstance = new Setting();
        }
        return mInstance;
    }
    
//    private synchronized boolean getDefaultBooleanSetting(String key) {
//    	if (null == key) {
//    		return false;
//    	}
//    	String result = ConfigManager.getInstance().getDefaultSetting(key);
//    	if (null != result && result.equals("1")) {
//    		return true;
//    	} else {
//    		return false;
//    	}
//    }
    
    public synchronized void setContext(Context context) {
        mContext = context;
        mSharedPref =  mContext.getSharedPreferences(SETTING, Activity.MODE_PRIVATE);        
        mEditor = mSharedPref.edit();
    }
    
    public synchronized int getSetting(String key) {
        int value = 0;
        try { 
            value = mSharedPref.getInt(key, 0);
        } catch(ClassCastException e) {
            e.printStackTrace();
        }
        return value;
    }
    
    public synchronized int getSetting(String key,int defalut) {
        int value = 0;
        try { 
            value = mSharedPref.getInt(key, defalut);
        } catch(ClassCastException e) {
            e.printStackTrace();
        }
        return value;
    }
    
    public synchronized boolean getBooleanSettings(String key) {
        boolean ret = false;
        if (mSharedPref.contains(key)) {
            ret = getBoolean(key, true);
        }/* else {
            ret = getDefaultBooleanSetting(key);
        }*/
        
        return ret;
    }
    
    /**
     * Special function for SubSettingActivityNewsChannel to call  
     * @param key
     * @return
     */
    public synchronized boolean getBooleanSettingsBeta(String key) {
        boolean ret = true;
        if (mSharedPref.contains(key)) {
            ret = getBoolean(key, true);
        } /*else {
            ret = getDefaultBooleanSetting(key);
        }*/
        
        return ret;
    }
    
    
    public synchronized long getLongSetting(String key) {
        long value = 0;
        try { 
            value = mSharedPref.getLong(key, 0);
        } catch(ClassCastException e) {
            e.printStackTrace();
        }
        return value;
    }
    
    public synchronized String getString(String key, String defaultValue) {
        String value = null;
        try {
            value = mSharedPref.getString(key,defaultValue);
        } catch(Exception e) {
//            DebugLog.d(TAG, "Exception="+e);
        }
        return value;
    }
     
    public synchronized boolean getBoolean(String key, boolean defaultValue) {
        boolean ret = defaultValue;
        try {
            ret = mSharedPref.getBoolean(key, ret);
        } catch(ClassCastException e) {
            e.printStackTrace();
        }
        return ret;
    } 
    
    /**
     * 保存String，要过滤'\0',否则会使XML读取异常
     * MSC引擎返回的String中包含特殊字符
     * @param key
     * @param value
     */
    public synchronized void setSetting(String key, String value) {
        if (null != value){
            value = value.replace("\0", "");
        }
        mEditor.putString(key, value);
        mEditor.commit();
    } 
    
    public synchronized void setSetting(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }
    
    public synchronized void setSetting(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }
    
    public synchronized void setSetting(String key, long value) {
        mEditor.putLong(key, value);
        mEditor.commit();
    }
    
    /**
     * Check is connect net
     * @return
     */
    public synchronized boolean isConnectNet() {
        ConnectivityManager cManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable() && info.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
    
//  public void startWirelessActivity(Activity context) {
//      Intent intent = new Intent();
//      intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
//      context.startActivity(intent);
//  }
    
    /**
     * Save Serializable object
     * @param fileName 
     * @param object
     */
    public synchronized void saveObject(String fileName, Object object) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream bytetOut = new FileOutputStream(file);
            ObjectOutputStream outer = new ObjectOutputStream(bytetOut);
            outer.writeObject(object);
            outer.flush();
            outer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public synchronized void clearObject(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Read Serializable object
     * @param fileName
     * @return
     */
    public synchronized Object readObject(String fileName) {
        Object object = null;
        try {
            File f=new File(fileName);
            FileInputStream byteOut = new FileInputStream(f);
            ObjectInputStream out = new ObjectInputStream(byteOut);
            object = out.readObject();
            out.close();
            byteOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }       
        return object;
    }
    
    public synchronized void enableComponent(Context context, Class<?> cls) {
        ComponentName componentName = new ComponentName(context, cls);
        PackageManager packageManager = context.getPackageManager();
        packageManager.setComponentEnabledSetting(componentName,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP);
    }
    
    public synchronized void disableComponent(Context context, Class<?> cls) {
        ComponentName componentName = new ComponentName(context, cls);
        PackageManager packageManager = context.getPackageManager();
        packageManager.setComponentEnabledSetting(componentName,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP);
    }
}