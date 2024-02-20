package za.co.bakerysystem.service.impl;

import java.time.LocalDate;
import java.util.List;
import za.co.bakerysystem.dao.SalesReportDAO;
import za.co.bakerysystem.dao.impl.SalesReportDAOImpl;
import za.co.bakerysystem.model.SalesReport;
import za.co.bakerysystem.model.SalesReportDetails;
import za.co.bakerysystem.service.SalesReportService;

public class SalesReportServiceImpl implements SalesReportService {

    private SalesReportDAO salesReportDAO;

    public SalesReportServiceImpl() {
        this.salesReportDAO = new SalesReportDAOImpl();
    }

    @Override
    public boolean createSale(SalesReport salesReport) {
        return salesReportDAO.createSale(salesReport);
    }

    @Override
    public boolean updateSale(SalesReport salesReport) {
        return salesReportDAO.updateSale(salesReport);
    }

    @Override
    public int getSaleQuantity(int productID) {
        return salesReportDAO.getSaleQuantity(productID);
    }

    @Override
    public List<SalesReport> getSales(LocalDate startDate, LocalDate endDate) {
        return salesReportDAO.getSales(startDate, endDate);
    }

    @Override
    public List<SalesReport> getSalesInRange(LocalDate startDate, LocalDate endDate) {
        return salesReportDAO.getSalesInRange(startDate, endDate);
    }

    @Override
    public SalesReport getSale(int saleID) {
        return salesReportDAO.getSale(saleID);
    }

    @Override
    public List<SalesReport> getSalesLast14Days() {
        return salesReportDAO.getSalesLast14Days();
    }

    @Override
    public int getTotalSalesQuantity() {
        return salesReportDAO.getTotalSalesQuantity();
    }

    @Override
    public SalesReport getSaleNoProfit(int saleID) {
        return salesReportDAO.getSaleNoProfit(saleID);
    }

    @Override
    public List<SalesReportDetails> getSalesDetails(int saleID) {
        return salesReportDAO.getSalesDetails(saleID);
    }

    @Override
    public boolean deleteSalesDetail(int reportID) {
        return salesReportDAO.deleteSalesDetail(reportID);
    }

    @Override
    public boolean deleteSales(int saleID) {
        return salesReportDAO.deleteSales(saleID);
    }

    //-----------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        SalesReportService salesReportService = new SalesReportServiceImpl();

        // Test createSale method
//        SalesReport salesReportToCreate = new SalesReport(3,"yrtyt");
//        boolean createSaleResult = salesReportService.createSale(salesReportToCreate);
//
//        if (createSaleResult) {
//            System.out.println("Sale created successfully!");
//        } else {
//            System.out.println("Failed to create sale.");
//        }
        // Test getSales method
//        LocalDate startDate = LocalDate.now().minusDays(30);
//        LocalDate endDate = LocalDate.now();
//        List<SalesReport> salesList = salesReportService.getSales(startDate, endDate);
//
//        System.out.println("Sales in the specified date range:");
//        for (SalesReport sale : salesList) {
//            System.out.println(sale);
//        }
        // Test getSalesDetails method
//        int saleIDToFetchDetails = 1;
//        List<SalesReportDetails> salesDetailsList = salesReportService.getSalesDetails(saleIDToFetchDetails);
//
//        System.out.println("Sales details for sale ID " + saleIDToFetchDetails + ":");
//        for (SalesReportDetails details : salesDetailsList) {
//            System.out.println(details); 
//        }
//        // Test deleteSales method
//        int saleIDToDelete = 1;
//        boolean deleteSaleResult = salesReportService.deleteSales(saleIDToDelete);
//
//        if (deleteSaleResult) {
//            System.out.println("Sale deleted successfully!");
//        } else {
//            System.out.println("Failed to delete sale.");
//        }
    }

}
