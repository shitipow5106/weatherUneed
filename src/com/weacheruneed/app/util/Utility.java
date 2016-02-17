package com.weacheruneed.app.util;

import com.weacheruneed.app.db.WeatherUneedDB;
import com.weacheruneed.app.model.City;
import com.weacheruneed.app.model.Country;
import com.weacheruneed.app.model.Province;

import android.R.bool;
import android.text.TextUtils;

public class Utility {
	//打开服务器返回的省级数据
	
	public synchronized static boolean handleProvinceResponse(WeatherUneedDB 
			weatherUneedDB,String response){
		if(!TextUtils.isEmpty(response)){
			String[] allProvinces = response.split(",");
			if(allProvinces !=null &&allProvinces.length>0){
				for(String p:allProvinces){
					String[] array = p.split("\\|");
					Province province = new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					//将解析出来的数据存储到province表
					weatherUneedDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}
	
	//打开服务器返回的市级数据
	
	public static boolean handleCitiesResponse(WeatherUneedDB weatherUneedDB,
			String response,int provinceId){
		if(!TextUtils.isEmpty(response)){
			String[] allCities = response.split(",");
			if(allCities !=null && allCities.length>0){
				for(String c :allCities){
					String[] array = c.split("\\|");
					City city = new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					city.setProvinceId(provinceId);
					//将机械出来的数据存储到city表
					weatherUneedDB.saveCity(city);
				}
				return true;
			}
		}
		return false;
	}
	
	//打开服务器返回的县区级数据
	
	public static boolean handleCountriesResponse(WeatherUneedDB weatherUneedDB,
			String response,int cityId){
		if(!TextUtils.isEmpty(response)){
			String[] allCountries = response.split(",");
			if(allCountries !=null &allCountries.length>0){
				for(String c :allCountries){
					String[] array = c.split("\\|");
					Country country = new Country();
					country.setCountryCode(array[0]);
					country.setCountryName(array[1]);
					country.setCityId(cityId);
					//将解析出来的数据存储到country表
					weatherUneedDB.saveCountry(country);
				}
				return true;
			}
		}
		return false;
	}
}
