import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AnimateFinance {

    public static void main(String... args) throws IOException {
        
        HSSFWorkbook workbook = new HSSFWorkbook();
        initExcel(workbook);        

        workbook.write(new FileOutputStream("AnimateFinance"));
        search("T", "AAPL");
        
    }
    
    @SuppressWarnings("resource")
    public static void initExcel(HSSFWorkbook wb) throws FileNotFoundException, IOException {
        
        wb = new HSSFWorkbook();
        HSSFSheet sheet =  wb.createSheet("First Sheet");
        
        HSSFRow legendRow = sheet.createRow(0);
        legendRow.createCell(0).setCellValue("Date");
        legendRow.createCell(1).setCellValue("Ticker");
        legendRow.createCell(2).setCellValue("Name");

        HSSFDataFormat format = wb.createDataFormat();
        CellStyle dateStyle = wb.createCellStyle();
        dateStyle.setDataFormat(format.getFormat("mm.dd.yyyy"));

        HSSFRow row = sheet.createRow(2);
        row.createCell(0).setCellStyle(dateStyle);
        row.getCell(0).setCellValue(new Date() );
    }
    
    public static void search(String ... tickers) {
        
        System.setProperty("webdriver.gecko.driver","/Users/Brendon/Desktop/Selenium/geckodriver");
        WebDriver driver = new FirefoxDriver();

        driver.get("http://fool.com");
        WebDriverWait wait = new WebDriverWait(driver, 5);

        for(String iterate: tickers) {

            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.name("query") ) );
            WebElement searchbar = driver.findElement(By.name("query") );
            searchbar.sendKeys(iterate);
            searchbar.submit();

            WebDriverWait waits = new WebDriverWait(driver, 5);

      //      WebElement v = driver.findElement(By.xpath("/html/body/div/table/tbody/tr[1]/td[1]"));
            System.out.println("iterate");
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        }
    }
}