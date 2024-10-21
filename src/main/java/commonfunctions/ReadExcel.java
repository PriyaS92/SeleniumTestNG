package commonfunctions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	ArrayList<String> test_data = new ArrayList<String>();
	Set<Double> low_prices = new TreeSet<Double>();
	Set<Double> high_prices = new TreeSet<Double>();
	FileInputStream file;
	public ArrayList<String> excel_Read() {
		try {
			file = new FileInputStream(new File(System.getProperty("user.dir")+"/src/main/resources/Company_Names.xlsx"));
			
			//Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			//Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			//Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {

			  Row row = rowIterator.next();
			  
			  //For each row, iterate through all the columns
			  Iterator<Cell> cellIterator = row.cellIterator();
			  
			  if(!(row.getRowNum()==0)) {
				  while (cellIterator.hasNext()) {

					    Cell cell = cellIterator.next();

					    //Check the cell type and format accordingly
					    switch (cell.getCellType()) {
					      case NUMERIC:
					    	low_prices.add(row.getCell(2).getNumericCellValue());
					    	high_prices.add(row.getCell(1).getNumericCellValue());
					    	break;
					      case STRING:
					    	test_data.add(cell.getStringCellValue());
					        break;
						default:
							break;
					    }
					  } 
			  }
			}
			
		}
		catch(FileNotFoundException ex) {
			ex.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return test_data;
	}
	
    public Set<Double> gethighPrices() {
    	return high_prices;
    }
    
    public Set<Double> getlowPrices() {
    	return low_prices;
    }
}
