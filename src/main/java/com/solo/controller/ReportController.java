package com.solo.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.collection.SList;
import com.core.collection.Solo;
import com.core.exception.business.SException;
import com.core.util.Jasper.JasperReportUtil;

import net.sf.jasperreports.engine.JRException;

@RestController
public class ReportController {
	
	@PostMapping("/pdf")
	public ResponseEntity<byte[]> pdf() throws JRException, SException, IOException{
		Solo params = new Solo();
		
		SList list = new SList();
		
		for(int i=1 ; i<=10; i++) {
			Solo solo = new Solo();
			solo.setInt("id", i);
			solo.setString("pname", "product " + i);
			solo.setInt("unit", i + 1);
			solo.setString("price", i * 2 + " USD");
			list.add(solo);
		}
		System.out.println(list);
		
		params.set("myCollection", JasperReportUtil.setDataSource(list));
		params.setString("title", "Brojum.com");
		params.setString("name", "Brojum");
		params.setString("age", "18 years old");
		
		return JasperReportUtil.ExportReport(JasperReportUtil.PDF, params, "jasper/test", JasperReportUtil.PDF);
	}
	
	@PostMapping("/html")
	public ResponseEntity<byte[]> html() throws JRException, SException, IOException{
		Solo params = new Solo();
		
		SList list = new SList();
		
		for(int i=1 ; i<=10; i++) {
			Solo solo = new Solo();
			solo.setInt("id", i);
			solo.setString("pname", "product " + i);
			solo.setInt("unit", i + 1);
			solo.setString("price", i * 2 + " USD");
			list.add(solo);
		}
		System.out.println(list);
		
		params.set("myCollection", JasperReportUtil.setDataSource(list));
		params.setString("title", "Brojum.com");
		params.setString("name", "Brojum");
		params.setString("age", "18 years old");

		return JasperReportUtil.ExportReport(JasperReportUtil.HTML, params, "jasper/test", JasperReportUtil.HTML);
	}
	
	@PostMapping("/excel")
	public ResponseEntity<byte[]> excel() throws JRException, SException, IOException{
		Solo params = new Solo();
		
		SList list = new SList();
		
		for(int i=1 ; i<=10; i++) {
			Solo solo = new Solo();
			solo.setInt("id", i);
			solo.setString("pname", "product " + i);
			solo.setInt("unit", i + 1);
			solo.setString("price", i * 2 + " USD");
			list.add(solo);
		}
		System.out.println(list);
		
		params.set("myCollection", JasperReportUtil.setDataSource(list));
		params.setString("title", "Brojum.com");
		params.setString("name", "Brojum");
		params.setString("age", "18 years old");
		
		return JasperReportUtil.ExportReport(JasperReportUtil.EXCEL, params, "jasper/test", JasperReportUtil.EXCEL);
	}
	
	@PostMapping("/word")
	public ResponseEntity<byte[]> word() throws JRException, SException, IOException{
		Solo params = new Solo();
		
		SList list = new SList();
		
		for(int i=1 ; i<=10; i++) {
			Solo solo = new Solo();
			solo.setInt("id", i);
			solo.setString("pname", "product " + i);
			solo.setInt("unit", i + 1);
			solo.setString("price", i * 2 + " USD");
			list.add(solo);
		}
		System.out.println(list);
		
		params.set("myCollection", JasperReportUtil.setDataSource(list));
		params.setString("title", "Brojum.com");
		params.setString("name", "Brojum");
		params.setString("age", "18 years old");
		
		return JasperReportUtil.ExportReport(JasperReportUtil.WORD, params, "jasper/test", JasperReportUtil.WORD);
	}
	
	@PostMapping("/powerpoint")
	public ResponseEntity<byte[]> powerpoint() throws JRException, SException, IOException{
		Solo params = new Solo();
		
		SList list = new SList();
		
		for(int i=1 ; i<=10; i++) {
			Solo solo = new Solo();
			solo.setInt("id", i);
			solo.setString("pname", "product " + i);
			solo.setInt("unit", i + 1);
			solo.setString("price", i * 2 + " USD");
			list.add(solo);
		}
		System.out.println(list);
		
		params.set("myCollection", JasperReportUtil.setDataSource(list));
		params.setString("title", "Brojum.com");
		params.setString("name", "Brojum");
		params.setString("age", "18 years old");
		
		return JasperReportUtil.ExportReport(JasperReportUtil.POWERPOINT, params, "jasper/test", JasperReportUtil.POWERPOINT);
	}

}
