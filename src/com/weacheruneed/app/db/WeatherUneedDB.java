package com.weacheruneed.app.db;

import java.util.ArrayList;
import java.util.List;

import com.weacheruneed.app.model.Province;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class WeatherUneedDB {
	// 数据库名字
	
	public static final String DB_NAME = "weather_u_need";
	
	//数据库版本
	
	public static final int VERSION = 1;
	
	private static WeatherUneedDB weatherUneedDB;
	
	private SQLiteDatabase db;
	
	//私有化构造方法
	
	private WeatherUneedDB(Context context){
		
		WeatherUneedHelper dbHelper = new WeatherUneedHelper(context, DB_NAME, null, VERSION);
		db =dbHelper.getWritableDatabase();
	}
	
	//获取weatheruneedDB实例
	
	public synchronized static WeatherUneedDB getInstance(Context context){
		if(weatherUneedDB == null){
			weatherUneedDB = new WeatherUneedDB(context);
		}
		return weatherUneedDB;
	}
	
	//添加省类实例到数据库
	
	public void saveProvince(Province province){
		if(province!=null){
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}
	
	//从数据库读取全国省份信息
	
	public List<Province> loadProvinces(){
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.query("Province", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			}while(cursor.moveToNext());
		}
		if(cursor!=null){
			cursor.close();
		}
		return list;
	}
}
