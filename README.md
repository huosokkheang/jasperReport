# jasperReport
JasperReport Solo Framework

Doc: https://huosokkheang.github.io/Solo-Framework

Export to PDF, HTML, WORD, EXCEL, POWERPOINT

- https://brojum.com/JasperReport/pdf
- https://brojum.com/JasperReport/image
- https://brojum.com/JasperReport/html
- https://brojum.com/JasperReport/word
- https://brojum.com/JasperReport/excel
- https://brojum.com/JasperReport/powerpoint

<pre>
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
	
	@GetMapping("/pdf")
	public void pdf(HttpServletResponse response) throws JRException, SException, IOException{
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
		params.set("myCollection", JasperReportUtil.setDataSource(list));
		params.setString("title", "Solo Framework");
		params.setString("name", "Solo Name");
		params.setString("age", "18 years old");
		
		byte[] pdf = JasperReportUtil.ExportReport(JasperReportUtil.PDF, params, "jasper/test", "pdfFileName").getBody();
		response.setContentType("application/pdf;charset=UTF-8");
		response.setHeader("Content-length", "" + pdf.length);
		response.setHeader("Content-disposition", "filename=" + "pgg.pdf");
        StreamUtils.copy(pdf, response.getOutputStream());
	}
	
	@GetMapping("/image")
	public void image(HttpServletResponse response) throws JRException, SException, IOException{
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
		params.set("myCollection", JasperReportUtil.setDataSource(list));
		params.setString("title", "Solo Framework");
		params.setString("name", "Solo Name");
		params.setString("age", "18 years old");
		
		byte[] image = JasperReportUtil.ExportReport(JasperReportUtil.IMAGE, params, "jasper/test", "imageFileName").getBody();
		
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(image, response.getOutputStream());
	}
	
	@GetMapping("/html")
	public byte[] html() throws JRException, SException, IOException{
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
		params.set("myCollection", JasperReportUtil.setDataSource(list));
		params.setString("title", "Solo Framework");
		params.setString("name", "Solo Name");
		params.setString("age", "18 years old");

		return JasperReportUtil.ExportReport(JasperReportUtil.HTML, params, "jasper/test", "htmlFileName").getBody();
	}
	
	@GetMapping("/excel")
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
		params.set("myCollection", JasperReportUtil.setDataSource(list));
		params.setString("title", "Solo Framework");
		params.setString("name", "Solo Name");
		params.setString("age", "18 years old");
		
		return JasperReportUtil.ExportReport(JasperReportUtil.EXCEL, params, "jasper/test", "ExcelFileName");
	}
	
	@GetMapping("/word")
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
		params.set("myCollection", JasperReportUtil.setDataSource(list));
		params.setString("title", "Solo Framework");
		params.setString("name", "Solo Name");
		params.setString("age", "18 years old");
		
		return JasperReportUtil.ExportReport(JasperReportUtil.WORD, params, "jasper/test", "wordFileName");
	}
	
	@GetMapping("/powerpoint")
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
		params.set("myCollection", JasperReportUtil.setDataSource(list));
		params.setString("title", "Solo Framework");
		params.setString("name", "Solo Name");
		params.setString("age", "18 years old");
		
		return JasperReportUtil.ExportReport(JasperReportUtil.POWERPOINT, params, "jasper/test", "powerPointFileName");
	}

}
</pre>

![image](https://user-images.githubusercontent.com/35053923/220713972-0f170699-0f37-4625-af6e-ebd30ee9bbf3.png)
