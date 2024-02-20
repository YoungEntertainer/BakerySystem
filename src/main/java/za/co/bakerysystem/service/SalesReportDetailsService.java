package za.co.bakerysystem.service;

import java.util.List;
import za.co.bakerysystem.model.SalesReportDetails;

public interface SalesReportDetailsService {

    boolean createSaleDetail(SalesReportDetails salesReportDetails);

    boolean updateSaleDetail(SalesReportDetails salesReportDetails);

    List<SalesReportDetails> getSalesDetails(int saleID);

    boolean deleteSaleDetail(int reportID);
}
