package za.co.bakerysystem.service.impl;

import za.co.bakerysystem.dao.SalesReportDetailsDAO;
import za.co.bakerysystem.dao.impl.SalesReportDetailsDAOImpl;
import za.co.bakerysystem.model.SalesReportDetails;
import za.co.bakerysystem.service.SalesReportDetailsService;

import java.util.List;

public class SalesReportDetailsServiceImpl implements SalesReportDetailsService {

    private SalesReportDetailsDAO salesReportDetailsDAO;

    public SalesReportDetailsServiceImpl() {
        this.salesReportDetailsDAO = new SalesReportDetailsDAOImpl();
    }

    @Override
    public boolean createSaleDetail(SalesReportDetails salesReportDetails) {
        return salesReportDetailsDAO.createSaleDetail(salesReportDetails);
    }

    @Override
    public boolean updateSaleDetail(SalesReportDetails salesReportDetails) {
        return salesReportDetailsDAO.updateSaleDetail(salesReportDetails);
    }

    @Override
    public List<SalesReportDetails> getSalesDetails(int saleID) {
        return salesReportDetailsDAO.getSalesDetails(saleID);
    }

    @Override
    public boolean deleteSaleDetail(int reportID) {
        return salesReportDetailsDAO.deleteSaleDetail(reportID);
    }
    
    //------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------
    
    public static void main(String[] args) {
        SalesReportDetailsService salesReportDetailsService = new SalesReportDetailsServiceImpl();
        SalesReportDetails sampleSaleDetail = new SalesReportDetails();

        //
        // Create a sample SalesReportDetails object for testing
        sampleSaleDetail.setSalesReportID(1);
        sampleSaleDetail.setProductID(2);
        sampleSaleDetail.setStartQuantity(50);
        sampleSaleDetail.setQuantitySold(20);
        sampleSaleDetail.setQuantityTrashed(5);
        sampleSaleDetail.setPriceAtSale(10.99);
        sampleSaleDetail.setFoodCostAtSale(5.99);
        sampleSaleDetail.setProfit(45);
        sampleSaleDetail.setRevenue(56);
        sampleSaleDetail.setLost(45);

        // Test createSaleDetail method
//        boolean createResult = salesReportDetailsService.createSaleDetail(sampleSaleDetail);
//        System.out.println("createSaleDetail Result: " + createResult);

//        // Test updateSaleDetail method
//        boolean updateResult = salesReportDetailsService.updateSaleDetail(sampleSaleDetail);
//        System.out.println("updateSaleDetail Result: " + updateResult);
//
        // Test getSalesDetails method
//        int sampleSaleID = 1; // Replace with an actual sale ID for testing
//        List<SalesReportDetails> salesDetailsList = salesReportDetailsService.getSalesDetails(sampleSaleID);
//        System.out.println("Sales Details for Sale ID " + sampleSaleID + ": " + salesDetailsList);

//        // Test deleteSaleDetail method
//        int sampleReportID = 456; // Replace with an actual report ID for testing
//        boolean deleteResult = salesReportDetailsService.deleteSaleDetail(sampleReportID);
//        System.out.println("deleteSaleDetail Result: " + deleteResult);
    }

}
