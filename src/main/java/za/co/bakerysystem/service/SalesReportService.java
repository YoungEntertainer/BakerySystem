package za.co.bakerysystem.service;

import java.time.LocalDate;
import java.util.List;
import za.co.bakerysystem.model.SalesReport;
import za.co.bakerysystem.model.SalesReportDetails;

public interface SalesReportService {

    boolean createSale(SalesReport salesReport);

    boolean updateSale(SalesReport salesReport);

    List<SalesReport> getSales(LocalDate startDate, LocalDate endDate);

    List<SalesReport> getSalesInRange(LocalDate startDate, LocalDate endDate);

    SalesReport getSale(int saleID);

    int getSaleQuantity(int productID);

    List<SalesReport> getSalesLast14Days();

    int getTotalSalesQuantity();

    SalesReport getSaleNoProfit(int saleID);

    List<SalesReportDetails> getSalesDetails(int saleID);

    boolean deleteSalesDetail(int reportID);

    boolean deleteSales(int salesID);
}
