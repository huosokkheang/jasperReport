//package com.solo.controller;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.Map;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.ResourceUtils;
//
//import com.core.collection.SList;
//import com.core.collection.Solo;
//import com.core.exception.business.SException;
//import com.core.util.Jasper.ReportException;
//import com.core.util.date.SDateUtil;
//import com.core.util.file.SFileIOUtil;
//
//import net.sf.jasperreports.engine.JREmptyDataSource;
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JRExporterParameter;
//import net.sf.jasperreports.engine.JRParameter;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperExportManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//import net.sf.jasperreports.engine.export.HtmlExporter;
//import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
//import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
//import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
//import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
//import net.sf.jasperreports.export.Exporter;
//import net.sf.jasperreports.export.ExporterInput;
//import net.sf.jasperreports.export.HtmlExporterConfiguration;
//import net.sf.jasperreports.export.HtmlExporterOutput;
//import net.sf.jasperreports.export.HtmlReportConfiguration;
//import net.sf.jasperreports.export.SimpleExporterInput;
//import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
//import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
//import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
//
///**
// * @author sok.kheanghuo
// *
// */
//public class JasperReportUtil {
//	
//	public static final String PDF = "PDF";
//	public static final String HTML = "HTML";
//	public static final String WORD = "WORD";
//	public static final String EXCEL = "EXCEL";
//	public static final String POWERPOINT = "POWERPOINT";
//
//	/**
//	 * @param reportFormat
//	 * @param param
//	 * @param listRepo
//	 * @param fileNameJasperReport
//	 * @return
//	 * @throws ReportException
//	 * @throws FileNotFoundException
//	 * @throws JRException
//	 */
//	public static ResponseEntity<byte[]> ExportReport(String reportFormat, Map<String, Object> param, SList listRepo,
//			String fileNameJasperReport) throws ReportException, FileNotFoundException, JRException {
//		ResponseEntity<byte[]> response = null;
//		File file = ResourceUtils.getFile("classpath:" + fileNameJasperReport + ".jrxml");
//		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
//
//		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listRepo);
//		if (reportFormat.equalsIgnoreCase("PDF")) {
//			param.put(JRParameter.IS_IGNORE_PAGINATION, false);
//		} else if (reportFormat.equalsIgnoreCase("EXCEL") || reportFormat.equalsIgnoreCase("HTML") || reportFormat.equalsIgnoreCase("POWERPOINT") || reportFormat.equalsIgnoreCase("WORD")) {
//			param.put(JRParameter.IS_IGNORE_PAGINATION, true);
//		}
//		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, dataSource);
//		try {
//			HttpHeaders respHeaders = new HttpHeaders();
//			if (reportFormat.equalsIgnoreCase("HTML")) {
//				byte[] bytes = null;
//				ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
//				Exporter<ExporterInput, HtmlReportConfiguration, HtmlExporterConfiguration, HtmlExporterOutput> exporter;
//				exporter = new HtmlExporter();
//
//				exporter.setExporterOutput(new SimpleHtmlExporterOutput(byteArray));
//				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//				exporter.exportReport();
//				bytes = byteArray.toByteArray();
//
//				respHeaders.setContentType(MediaType.TEXT_HTML);
//				respHeaders.setContentDispositionFormData("filename", fileNameJasperReport + ".html");
//
//				return new ResponseEntity<byte[]>(byteArray.toByteArray(), respHeaders, HttpStatus.OK);
//			}
//			if (reportFormat.equalsIgnoreCase("PDF")) {
//				HttpHeaders headers = new HttpHeaders();
//				// set the PDF format
//				headers.setContentType(MediaType.APPLICATION_PDF);
//				headers.setContentDispositionFormData("filename", fileNameJasperReport + ".pdf");
//				// create the report in PDF format
//				return new ResponseEntity<>(JasperExportManager.exportReportToPdf(jasperPrint), headers, HttpStatus.OK);
//			}
//			if (reportFormat.equalsIgnoreCase("EXCEL")) {
//
//				SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
//				configuration.setOnePagePerSheet(true);
//				configuration.setIgnoreGraphics(false);
//
//				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//				Exporter exporter = new JRXlsxExporter();
//				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
//				exporter.setConfiguration(configuration);
//				exporter.exportReport();
//				respHeaders.setContentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
//				respHeaders.setContentDispositionFormData("attachment", fileNameJasperReport + ".xlsx");
//				respHeaders.setContentLength(byteArrayOutputStream.toByteArray().length);
//
//				return new ResponseEntity<byte[]>(byteArrayOutputStream.toByteArray(), respHeaders, HttpStatus.OK);
//			}
//			if (reportFormat.equalsIgnoreCase("WORD")) {
//				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//				JRDocxExporter exporter = new JRDocxExporter();    
//				exporter.setExporterInput(new SimpleExporterInput(jasperPrint)); 
//				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));    
//				exporter.exportReport();
//				respHeaders.setContentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
//				respHeaders.setContentDispositionFormData("attachment", fileNameJasperReport + ".docx");
//				respHeaders.setContentLength(byteArrayOutputStream.toByteArray().length);
//				return new ResponseEntity<byte[]>(byteArrayOutputStream.toByteArray(), respHeaders, HttpStatus.OK);
//			}
//			if (reportFormat.equalsIgnoreCase("POWERPOINT")) {
//				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//				JRPptxExporter exporter = new JRPptxExporter();
//				exporter.setExporterInput(new SimpleExporterInput(jasperPrint)); 
//				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));    
//				exporter.exportReport();
//				respHeaders.setContentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.presentationml.presentation"));
//				respHeaders.setContentDispositionFormData("attachment", fileNameJasperReport + ".pptx");
//				respHeaders.setContentLength(byteArrayOutputStream.toByteArray().length);
//				return new ResponseEntity<byte[]>(byteArrayOutputStream.toByteArray(), respHeaders, HttpStatus.OK);
//			}
//		} catch (ReportException e) {
//			throw new ReportException(e.getMessage(), e);
//		}
//		return response;
//	}
//	
//	/**
//	 * @param reportFormat
//	 * @param param
//	 * @param fileNameJasperReport
//	 * @return
//	 * @throws ReportException
//	 * @throws FileNotFoundException
//	 * @throws JRException
//	 */
//	public static ResponseEntity<byte[]> ExportReport(String reportFormat, Map<String, Object> param,
//			String fileNameJasperReport) throws ReportException, FileNotFoundException, JRException {
//		ResponseEntity<byte[]> response = null;
//		File file = ResourceUtils.getFile("classpath:" + fileNameJasperReport + ".jrxml");
//		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
//
//		if (reportFormat.equalsIgnoreCase("PDF")) {
//			param.put(JRParameter.IS_IGNORE_PAGINATION, false);
//		} else if (reportFormat.equalsIgnoreCase("EXCEL") || reportFormat.equalsIgnoreCase("HTML") || reportFormat.equalsIgnoreCase("POWERPOINT") || reportFormat.equalsIgnoreCase("WORD")) {
//			param.put(JRParameter.IS_IGNORE_PAGINATION, true);
//		}
//		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, new JREmptyDataSource());
//		try {
//			HttpHeaders respHeaders = new HttpHeaders();
//			if (reportFormat.equalsIgnoreCase("HTML")) {
//				byte[] bytes = null;
//				ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
//				Exporter<ExporterInput, HtmlReportConfiguration, HtmlExporterConfiguration, HtmlExporterOutput> exporter;
//				exporter = new HtmlExporter();
//
//				exporter.setExporterOutput(new SimpleHtmlExporterOutput(byteArray));
//				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//				exporter.exportReport();
//				bytes = byteArray.toByteArray();
//
//				respHeaders.setContentType(MediaType.TEXT_HTML);
//				respHeaders.setContentDispositionFormData("filename", fileNameJasperReport + ".html");
//
//				return new ResponseEntity<byte[]>(byteArray.toByteArray(), respHeaders, HttpStatus.OK);
//			}
//			if (reportFormat.equalsIgnoreCase("PDF")) {
//				HttpHeaders headers = new HttpHeaders();
//				// set the PDF format
//				headers.setContentType(MediaType.APPLICATION_PDF);
//				headers.setContentDispositionFormData("filename", fileNameJasperReport + ".pdf");
//				// create the report in PDF format
//				return new ResponseEntity<>(JasperExportManager.exportReportToPdf(jasperPrint), headers, HttpStatus.OK);
//			}
//			if (reportFormat.equalsIgnoreCase("EXCEL")) {
//
//				SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
//				configuration.setOnePagePerSheet(true);
//				configuration.setIgnoreGraphics(false);
//
//				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//				Exporter exporter = new JRXlsxExporter();
//				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
//				exporter.setConfiguration(configuration);
//				exporter.exportReport();
//				respHeaders.setContentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
//				respHeaders.setContentDispositionFormData("attachment", fileNameJasperReport + ".xlsx");
//				respHeaders.setContentLength(byteArrayOutputStream.toByteArray().length);
//
//				return new ResponseEntity<byte[]>(byteArrayOutputStream.toByteArray(), respHeaders, HttpStatus.OK);
//			}
//			if (reportFormat.equalsIgnoreCase("WORD")) {
//				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//				JRDocxExporter exporter = new JRDocxExporter();    
//				exporter.setExporterInput(new SimpleExporterInput(jasperPrint)); 
//				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));    
//				exporter.exportReport();
//				respHeaders.setContentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
//				respHeaders.setContentDispositionFormData("attachment", fileNameJasperReport + ".docx");
//				respHeaders.setContentLength(byteArrayOutputStream.toByteArray().length);
//				return new ResponseEntity<byte[]>(byteArrayOutputStream.toByteArray(), respHeaders, HttpStatus.OK);
//			}
//			if (reportFormat.equalsIgnoreCase("POWERPOINT")) {
//				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//				JRPptxExporter exporter = new JRPptxExporter();
//				exporter.setExporterInput(new SimpleExporterInput(jasperPrint)); 
//				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));    
//				exporter.exportReport();
//				respHeaders.setContentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.presentationml.presentation"));
//				respHeaders.setContentDispositionFormData("attachment", fileNameJasperReport + ".pptx");
//				respHeaders.setContentLength(byteArrayOutputStream.toByteArray().length);
//				return new ResponseEntity<byte[]>(byteArrayOutputStream.toByteArray(), respHeaders, HttpStatus.OK);
//			}
//		} catch (ReportException e) {
//			throw new ReportException(e.getMessage(), e);
//		}
//		return response;
//	}
//
//
//	/**
//	 * @param reportFormat
//	 * @param param
//	 * @param fileNameJRXML
//	 * @param pathFileOutputLocation
//	 * @param fileNameOutput
//	 * @return
//	 * @throws JRException
//	 * @throws SException
//	 * @throws IOException
//	 */
//	public static String ExportReportToFile(String reportFormat, Solo param, String fileNameJRXML,
//			String pathFileOutputLocation, String fileName)
//			throws JRException, SException, IOException {
//		SFileIOUtil.checkFileNotExists(pathFileOutputLocation);
//		// Load file and compile it
//		File file = ResourceUtils.getFile("classpath:" + fileNameJRXML + ".jrxml");
//		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
//
//		if (reportFormat.equalsIgnoreCase("PDF")) {
//			param.put(JRParameter.IS_IGNORE_PAGINATION, false);
//		} else if (reportFormat.equalsIgnoreCase("EXCEL") || reportFormat.equalsIgnoreCase("HTML") || reportFormat.equalsIgnoreCase("POWERPOINT") || reportFormat.equalsIgnoreCase("WORD")) {
//			param.put(JRParameter.IS_IGNORE_PAGINATION, true);
//		}
//
//		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, new JREmptyDataSource());
//
//		if (reportFormat.equalsIgnoreCase("HTML")) {
//			JasperExportManager.exportReportToHtmlFile(jasperPrint, pathFileOutputLocation + fileName + ".html");
//		}
//
//		if (reportFormat.equalsIgnoreCase("PDF")) {
//			JasperExportManager.exportReportToPdfFile(jasperPrint, pathFileOutputLocation + fileName + ".pdf");
//		}
//
//		if (reportFormat.equalsIgnoreCase("EXCEL")) {
//			JRXlsxExporter exporter = new JRXlsxExporter();
//			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
//			exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, pathFileOutputLocation + fileName + ".xlsx");
//			exporter.exportReport();
//		}
//		if (reportFormat.equalsIgnoreCase("WORD")) {
//			JRDocxExporter exporter = new JRDocxExporter();    
//			exporter.setExporterInput(new SimpleExporterInput(jasperPrint)); 
//			File exportReportFile = new File(pathFileOutputLocation + fileName + ".docx");    
//			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(exportReportFile));    
//			exporter.exportReport();
//		}
//		if (reportFormat.equalsIgnoreCase("POWERPOINT")) {
//			JRPptxExporter exporter = new JRPptxExporter();
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, pathFileOutputLocation + fileName + ".pptx");
//            exporter.exportReport();
//		}
//		
//		return fileName;
//	}
//	
//	/**
//	 * @param reportFormat
//	 * @param param
//	 * @param fileNameJRXML
//	 * @param listRepo
//	 * @param pathFileOutputLocation
//	 * @param fileNameOutput
//	 * @return
//	 * @throws JRException
//	 * @throws SException
//	 * @throws IOException
//	 */
//	public static String ExportReportToFile(String reportFormat, Solo param, String fileNameJRXML, SList listRepo,
//			String pathFileOutputLocation, String fileName)
//			throws JRException, SException, IOException {
//		SFileIOUtil.checkFileNotExists(pathFileOutputLocation);
//		// Load file and compile it
//		File file = ResourceUtils.getFile("classpath:" + fileNameJRXML + ".jrxml");
//		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
//
//		if (reportFormat.equalsIgnoreCase("PDF")) {
//			param.put(JRParameter.IS_IGNORE_PAGINATION, false);
//		} else if (reportFormat.equalsIgnoreCase("EXCEL") || reportFormat.equalsIgnoreCase("HTML") || reportFormat.equalsIgnoreCase("POWERPOINT") || reportFormat.equalsIgnoreCase("WORD")) {
//			param.put(JRParameter.IS_IGNORE_PAGINATION, true);
//		}
//
//		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listRepo);
//		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, dataSource);
//
//		if (reportFormat.equalsIgnoreCase("HTML")) {
//			JasperExportManager.exportReportToHtmlFile(jasperPrint, pathFileOutputLocation + fileName + ".html");
//		}
//
//		if (reportFormat.equalsIgnoreCase("PDF")) {
//			JasperExportManager.exportReportToPdfFile(jasperPrint, pathFileOutputLocation + fileName + ".pdf");
//		}
//
//		if (reportFormat.equalsIgnoreCase("EXCEL")) {
//			JRXlsxExporter exporter = new JRXlsxExporter();
//			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
//			exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, pathFileOutputLocation + fileName + ".xlsx");
//			exporter.exportReport();
//		}
//		if (reportFormat.equalsIgnoreCase("WORD")) {
//			JRDocxExporter exporter = new JRDocxExporter();    
//			exporter.setExporterInput(new SimpleExporterInput(jasperPrint)); 
//			File exportReportFile = new File(pathFileOutputLocation + fileName + ".docx");    
//			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(exportReportFile));    
//			exporter.exportReport();
//		}
//		if (reportFormat.equalsIgnoreCase("POWERPOINT")) {
//			JRPptxExporter exporter = new JRPptxExporter();
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, pathFileOutputLocation + fileName + ".pptx");
//            exporter.exportReport();
//		}
//		
//		return fileName;
//	}
//	
//	/**
//	 * @param listRepo
//	 * @return
//	 */
//	public static JRBeanCollectionDataSource setDataSource(SList listRepo) {
//		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listRepo);
//		return beanCollectionDataSource;
//	}
//}
