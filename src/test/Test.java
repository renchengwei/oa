package test;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

import com.sun.xml.bind.v2.runtime.unmarshaller.Discarder;

import oa.util.DateUtil;

public class Test {
	public static void main(String[] args) {
//		String s = "15235678756";
//		int index = s.indexOf("(");
//		System.out.println(index);
//		System.out.println(s.substring(0, index));
		String str = "1234567890";
		System.out.println(DigestUtils.md5Hex(str).length());
	}
}
