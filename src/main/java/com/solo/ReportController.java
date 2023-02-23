package com.solo;

import java.io.FileNotFoundException;

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
	
	@PostMapping("/print")
	public ResponseEntity<byte[]> getReport() throws FileNotFoundException, JRException, SException{
		Solo params = new Solo();
		
		//if have parameter
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
		//End
		
		//return JasperReportUtil.ExportReport("excel", params, "test");
		//return JasperReportUtil.ExportReport("html", params, "test");
		return JasperReportUtil.ExportReport("pdf", params, "test");
	}

}
