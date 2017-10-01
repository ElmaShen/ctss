package proj.ctworld.core.lib;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import proj.ctworld.core.*;

@Service
@PropertySource(value = { "classpath:application.properties" })
public class Utils {

	@Autowired
    private Environment environment;

	@Autowired
	private ObjectMapper objectMapper;
	
	public boolean existImg(String filename) {
		if (filename == null)
			return false;
		
		File f = new File(getImgPath() + filename);
		return f.exists() && !f.isDirectory();
	}

	public boolean existImgLocation(String filename) {
		if (filename == null)
			return false;
		
		File f = new File(filename);
		return f.exists() && !f.isDirectory();
	}
	
	public String saveImg(byte[] file, String filename) throws IOException {
		FileOutputStream fos = new FileOutputStream(new File(getImgPath() + filename));
        BufferedOutputStream stream = 
                new BufferedOutputStream(fos);
        stream.write(file);
        stream.close();
        fos.close();
        return getImgPath() + filename;
	}
	
	public String getImgPlaceholder() {
		return environment.getProperty("img.placeholder");
	}
	
	public String getImgPath() {
		return environment.getProperty("img.path");
	}

	public String getProp(String key) {
		return environment.getProperty(key);
	}
	
	public Map objectToMap(Object obj) {
		return objectMapper.convertValue(obj, Map.class);
	}

	String[] checkoutLiss = {"V", "C"};
	public Boolean chkCheckOutType(String str) {
		if (Arrays.asList(checkoutLiss).contains(str))
			return true;
		return false;
	}

	String[] shipAreaLiss = {"T", "H", "M", "C"};
	public Boolean chkShipArea(String str) {
		if (Arrays.asList(shipAreaLiss).contains(str))
			return true;
		return false;
	}

	String[] gengerLiss = {"M", "F"};
	public Boolean chkGender(String str) {
		if (Arrays.asList(gengerLiss).contains(str))
			return true;
		return false;
	}

	String[] residenceLiss = {"N", "W", "S", "E", "O"};
	public Boolean chkResidence(String str) {
		if (Arrays.asList(residenceLiss).contains(str))
			return true;
		return false;
	}

	String[] jobLiss = {"S", "O", "M", "B", "T"};
	public Boolean chkJob(String str) {
		if (Arrays.asList(jobLiss).contains(str))
			return true;
		return false;
	}
	
	
	public static final String FORMAT_DATE = "yyyy-MM-dd";
	public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_DATETIME_ISO = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	
	public DateTimeFormatter dtf_DateTime = DateTimeFormat.forPattern(FORMAT_DATETIME);
	public DateTimeFormatter dtf_Date = DateTimeFormat.forPattern(FORMAT_DATE);
	
	public static final String STATUS_ENABLE = "Y";
	public static final String STATUS_DISABLE = "N";

	public static final String GA_TYPE_COLOR = "C";
	public static final String GA_TYPE_SIZE = "S";
	
//	public enum ENABLE {
//		TRUE(1), FALSE(0);
//		
//		private final int value;
//		private ENABLE(int value) {
//			this.value = value;
//		}
//		public int getValue() {
//			return value;
//		}
//	}
    
    public final String isRelatedTypeWebLanding = "webLanding";
//    public final String isRelatedTypeWebLandingHover = "webLandingHover";
    
    public final static String isRelatedTypeWebBounceWindow = "webBounceWindow";
    public final static String isRelatedTypeWebBounceWindowHover = "webBounceWindowHover";
	
    public final static String isRelatedTypeWebAds = "webAds";
    public final static String isRelatedTypeWebAdsHover = "webAdsHover";
    
    public final static String isRelatedTypeWebSlider = "webSlider";
    public final static String isRelatedTypeWebSliderHover = "webSliderHover";
    
    public final static String isRelatedTypeWebInstagram = "webInstagram";
//    public final String isRelatedTypeWebInstagramHover = "webInstagramHover";
    
    public final static String isRelatedTypeWebInstagramSlider = "webInstagramSlider";
    public final static String isRelatedTypeWebInstagramSliderHover = "webInstagramSliderHover";
    
    public final static String isRelatedTypeWebFooter = "webFooter";
    public final static String isRelatedTypeWebFooterHover = "webFooterHover";
    
    public final static String isRelatedTypeWebDropdown = "webDropdown";
    public final static String isRelatedTypeWebDropdownHover = "webDropdownHover";
    
    public final String isRelatedTypeWebBanner = "webBanner";
    public final String isRelatedTypeWebBannerHover = "webBannerHover";

    public final String isRelatedTypeGoodsCover = "goodsCover";
    public final String isRelatedTypeGoodsCoverHover = "goodsCoverHover";
    public final String isRelatedTypeGoodsImage1 = "goodsImage1";
    public final String isRelatedTypeGoodsImage2 = "goodsImage2";
    public final String isRelatedTypeGoodsImage3 = "goodsImage3";
    public final String isRelatedTypeGoodsImage4 = "goodsImage4";
    public final String isRelatedTypeGoodsImage5 = "goodsImage5";
    public final String isRelatedTypeGoodsImage6 = "goodsImage6";
    public final String isRelatedTypeGoodsImage7 = "goodsImage7";
    public final String isRelatedTypeGoodsImage8 = "goodsImage8";

    public final static String isRelatedTypeGoodsAttribute = "goodsAttribute";
    
	
}
