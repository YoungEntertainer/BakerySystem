package za.co.bakerysystem.dao;

import java.time.LocalDate;
import java.util.List;
import za.co.bakerysystem.model.SalesReport;

public interface SalesReportDAO {

    int createSale(SalesReport salesReport);

    void updateSale(SalesReport salesReport);

    List<SalesReport> getSales(LocalDate startDate, LocalDate endDate);

    List<SalesReport> getSalesInRange(LocalDate startDate, LocalDate endDate);

    SalesReport getSale(int saleID);

    List<SalesReport> getSalesLast14Days();

    int getTotalSalesQuantity();

    SalesReport getSaleNoProfit(int saleID);

    List<SalesReport> getSalesDetails(int saleID);

    void deleteSalesDetail(int reportID);

    void deleteSales(List<Integer> salesIDs);
}
